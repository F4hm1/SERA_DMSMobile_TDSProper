package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.JourneyOrder.AcknowledgeOrderSendModel;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailActivity;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailResponseModel;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailSendModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class PlanOrderPresenter extends TiPresenter<PlanOrderView> {

    private SimpleAdapterModel mSimpleAdapterModel;
    private RestConnection mRestConnection;
    private HashMap<String, AssignedOrderResponseModel> ackOrderMap = new HashMap<>();
    private PlanOrderView mView = null;

    public PlanOrderPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    @Override
    protected void onAttachView(@NonNull final PlanOrderView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        mView = view;
    }

    public void loadOrdersdata() {
        getView().setTextEmptyInfoStatus(HelperBridge.sListOrderRetrievalSuccess);
        getView().toggleEmptyInfo(true);
        if (!HelperBridge.sPlanOutstandingOrdersList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        }
        mSimpleAdapterModel.setItemList(this.getSortedOrderByETD(HelperBridge.sPlanOutstandingOrdersList));

        for (AssignedOrderResponseModel assignedOrderResponseModel : HelperBridge.sPlanOutstandingOrdersList) {
            if (assignedOrderResponseModel.getStatus() == HelperTransactionCode.ASSIGNED_ORDER_INACK_CODE_IN) {
                String[] dateEtdServerFormatSplitted = assignedOrderResponseModel.getETD().split(" ");
                String dateEtdUserFormat = HelperUtil.getUserFormDate(dateEtdServerFormatSplitted[0]) + "\nPukul " + dateEtdServerFormatSplitted[1];

                String[] dateEtaServerFormatSplitted = assignedOrderResponseModel.getETA().split(" ");
                String dateEtaUserFormat = HelperUtil.getUserFormDate(dateEtaServerFormatSplitted[0]) + "\nPukul " + dateEtaServerFormatSplitted[1];
                ackOrderMap.put(assignedOrderResponseModel.getOrderID(), assignedOrderResponseModel);
                getView().showAcknowledgeDialog(
                        assignedOrderResponseModel.getOrderID(),
                        assignedOrderResponseModel.getAssignmentId(),
                        getSeparatedDestination(assignedOrderResponseModel.getDestination()),
                        assignedOrderResponseModel.getOrigin(),
                        dateEtdUserFormat,
                        dateEtaUserFormat,
                        assignedOrderResponseModel.getCustomer()
                );
                break;
            }
        }

        getView().refreshRecyclerView();
    }

    public void onItemClicked(int position) {

        /*
        * TODO change the way to access id/code order list, code is the title of the list? Pass it to detail driver activity and retrieve data from API based on that.
        * Selection if status is waiting ACK or Waiting to Start
        * Flow : onCreate activity get bundle data/ordercode -> save to local field -> onAttachView call initialize -> initialize call getdata in presenter ->
        * presenter call & get data from API & show progress dialog -> when done, call getview.setDataValue
        * */

        AssignedOrderResponseModel assignedOrderResponseModel = (AssignedOrderResponseModel) mSimpleAdapterModel.getItem(position);
        int statusOrder = assignedOrderResponseModel.getStatus();

        if (statusOrder == HelperTransactionCode.ASSIGNED_ORDER_INACK_CODE_IN) {
            String[] dateEtdServerFormatSplitted = assignedOrderResponseModel.getETD().split(" ");
            String dateEtdUserFormat = HelperUtil.getUserFormDate(dateEtdServerFormatSplitted[0]) + "\nPukul " + dateEtdServerFormatSplitted[1];

            String[] dateEtaServerFormatSplitted = assignedOrderResponseModel.getETA().split(" ");
            String dateEtaUserFormat = HelperUtil.getUserFormDate(dateEtaServerFormatSplitted[0]) + "\nPukul " + dateEtaServerFormatSplitted[1];
            ackOrderMap.put(assignedOrderResponseModel.getOrderID(), assignedOrderResponseModel);
            getView().showAcknowledgeDialog(
                    assignedOrderResponseModel.getOrderID(),
                    assignedOrderResponseModel.getAssignmentId(),
                    getSeparatedDestination(assignedOrderResponseModel.getDestination()),
                    assignedOrderResponseModel.getOrigin(),
                    dateEtdUserFormat,
                    dateEtaUserFormat,
                    assignedOrderResponseModel.getCustomer()
            );
        } else {
            Integer assignmentId = assignedOrderResponseModel.getAssignmentId();
            HelperBridge.sPlanOrderPositionClicked = position;
            HelperBridge.isClickedFromPlanOrder = true;
            loadDetailOrder(assignedOrderResponseModel.getOrderID(), assignmentId, assignedOrderResponseModel);
        }
    }

    public void onAcknowledgeOrder(final String orderCode, final Integer assignmentId, final String eTd, final String eTa) {
        /*
        * TODO Post ACK order and refresh updated assigned order
        * */
        final String fOrderCode = orderCode;
        mView.toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
                String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                String date = timeSplit[0];
                String time = timeSplit[1];

                getView().toggleLoading(false);
                AssignedOrderResponseModel selectedOrder = ackOrderMap.get(orderCode);
                AcknowledgeOrderSendModel acknowledgeOrderSendModel = new AcknowledgeOrderSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        assignmentId,
                        date,
                        time,
                        selectedOrder.getETA(),
                        selectedOrder.getETD());
                submitAcknowledgeOrder(acknowledgeOrderSendModel);
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });

    }

    private void submitAcknowledgeOrder(AcknowledgeOrderSendModel acknowledgeOrderSendModel) {
        getView().toggleLoading(true);
        final PlanOrderView planOrderView = getView();
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_ACKNOWLEDGE_ORDER, acknowledgeOrderSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    planOrderView.toggleLoading(false);
                    planOrderView.showToast(response.getString("responseText"));
                    planOrderView.refreshAllData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                planOrderView.toggleLoading(false);
                planOrderView.showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                planOrderView.toggleLoading(false);
                planOrderView.showStandardDialog("Gagal melakukan ack order, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    private void loadDetailOrder(final String orderId, Integer assignmentId, final AssignedOrderResponseModel assignedOrderResponseModel) {
        ActivityDetailSendModel activityDetailSendModel =
                new ActivityDetailSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(), orderId, assignmentId);

        getView().toggleLoading(true);
        final PlanOrderView planOrderView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_ACTIVITY, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                HelperBridge.sActivityDetailResponseModel = Model.getModelInstance(response.getData()[0], ActivityDetailResponseModel.class);
                HelperBridge.sTempSelectedOrderCode = orderId;
                HelperBridge.sAssignedOrderResponseModel = assignedOrderResponseModel;
                planOrderView.changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, HelperBridge.sActivityDetailResponseModel.getAssignmentId() + "", ActivityDetailActivity.class);
                planOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                planOrderView.showToast(response);
                planOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                planOrderView.showToast("Terjadi Kesalahan: " + error.toString());
                planOrderView.toggleLoading(false);
            }
        });
    }

    private String[] getSeparatedDestination(String destination) {
        if (destination.equalsIgnoreCase("")) {
            String[] resZero = new String[1];
            resZero[0] = "-";
            return resZero;
        } else {
            return destination.split(HelperKey.SEPARATOR_PIPE);
        }
    }

    private List<AssignedOrderResponseModel> getSortedOrderByETD(List<AssignedOrderResponseModel> listOrder) {
        Collections.sort(listOrder, new Comparator<AssignedOrderResponseModel>() {
            @Override
            public int compare(AssignedOrderResponseModel o1, AssignedOrderResponseModel o2) {
                SimpleDateFormat sdfOM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date etdOrder1 = sdfOM.parse(o1.getETD());
                    Date etdOrder2 = sdfOM.parse(o2.getETD());

                    if (etdOrder1.before(etdOrder2)) {
                        return -1;
                    } else {
                        return 1;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    return 1;
                }
            }
        });
        return listOrder;
    }

}
