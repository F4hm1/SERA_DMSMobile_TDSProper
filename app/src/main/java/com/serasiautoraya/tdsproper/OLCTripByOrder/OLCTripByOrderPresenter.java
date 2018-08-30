package com.serasiautoraya.tdsproper.OLCTripByOrder;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.tdsproper.ExpensesRequest.ExpenseRequestView;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailActivity;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailResponseModel;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailSendModel;
import com.serasiautoraya.tdsproper.JourneyOrder.DocumentCapture.DocumentCaptureActivity;
import com.serasiautoraya.tdsproper.JourneyOrder.StatusUpdateSendModel;
import com.serasiautoraya.tdsproper.RestClient.LocationModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public class OLCTripByOrderPresenter extends TiPresenter<OLCTripByOrderView> {

    private RestConnection mRestConnection;
    private OLCTripSendModel mOlcTripSendModel;

    @Override
    protected void onAttachView(@NonNull final OLCTripByOrderView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public OLCTripByOrderPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }


    private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = new Date();
        return dateFormat.format(date);
/*        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);*/
    }



    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public void onSubmitClicked(final List<OLCTripSendModel.OlcTripModel> olcTripModelList){
        //JSONArray jsArray = new JSONArray(olcTripModelList);

        final LocationModel locationModel = mRestConnection.getCurrentLocation();
        if (locationModel.getLongitude().equalsIgnoreCase("null")) {
            getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
        } else {
            getView().toggleLoading(true);
            mRestConnection.getServerTime(new TimeRestCallBackInterface() {
                @Override
                public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                    String[] timeSplitServer = timeRESTResponseModel.getTime().split(" ");
                    String[] timeSplitActivity = HelperBridge.sAssignedOrderResponseModel.getETD().split(" ");
                    String dateServer = timeSplitServer[0];
                    String dateActivity = timeSplitActivity[0];
                    if (HelperUtil.isDateBeforeOrEqual(HelperUtil.getUserFormDate(dateServer), HelperUtil.getUserFormDate(dateActivity))) {
                        onActionDateValid(olcTripModelList);
                    } else {
                        getView().showStandardDialog("Waktu server error", "Perhatian");
                    }
                    getView().toggleLoading(false);
                }

                @Override
                public void callBackOnFail(String message) {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(message, "Perhatian");
                }
            });
        }

    }



    private void onActionDateValid(final List<OLCTripSendModel.OlcTripModel> olcTripModelList) {

        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
//                        String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
//                        String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
//                        String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
//                        String date = timeSplit[0];
//                        String time = timeSplit[1];
//                        String dateMessage = dateSplit[2];
//                        String monthMessage = dateSplit[1];
//                        String yearMessage = dateSplit[0];

                mOlcTripSendModel = new OLCTripSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperBridge.sModelLoginResponse.getPersonalCode(),
                        HelperTransactionCode.WFSTATUS_PENDING,
                        HelperBridge.sAssignedOrderResponseModel.getOrderID(),
                        olcTripModelList,
                        HelperBridge.sAssignedOrderResponseModel.getETA(),
                        HelperBridge.sAssignedOrderResponseModel.getETD(),
                        getDateTime(),
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperTransactionCode.SUBMIT_TYPE_REQUEST_MOBILE,
                        HelperBridge.sAssignedOrderResponseModel.getPassengerEmail(),
                        HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                        HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                        HelperBridge.sAssignedOrderResponseModel.getPassengerName(),
                        timeRESTResponseModel.getTime(),
                        RestConnection.getUTCTimeStamp(timeRESTResponseModel),
                        HelperBridge.sActivityDetailResponseModel.getTimeActualCurrent()
                );
                Log.d("OLCTRIP", "APPROVAL: "+ HelperBridge.sModelLoginResponse.getPersonalApprovalId()+" \n"+
                        HelperBridge.sModelLoginResponse.getPersonalApprovalEmail()+" \n"+
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorId()+" \n"+
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail());
                getView().toggleLoading(false);
                getView().showConfirmationDialog(HelperBridge.sAssignedOrderResponseModel.getOrderID());
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });

    }


    public void onRequestSubmitted(){
        getView().toggleLoading(true);
        Log.e("OLCPOST", mOlcTripSendModel.getHashMapTypeList().toString());
        mRestConnection.postDataList(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_OLCTRIP, mOlcTripSendModel.getHashMapTypeList(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().toggleLoading(false);
                getView().showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().toggleLoading(false);
                getView().showStandardDialog("Gagal melakukan pengajuan OLC/Trip, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }


    public void loadDetailOrder() {

        ActivityDetailSendModel activityDetailSendModel =
                new ActivityDetailSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(), HelperBridge.sTempSelectedOrderCode, Integer.valueOf(HelperBridge.sActivityDetailResponseModel.getAssignmentId()));

        getView().toggleLoading(true);
        final OLCTripByOrderView olcTripByOrderView = getView();
        Log.e("POST PARAMS OLC", activityDetailSendModel.getHashMapType().toString());
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_ACTIVITY, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                HelperBridge.sActivityDetailResponseModel = Model.getModelInstance(response.getData()[0], ActivityDetailResponseModel.class);
                String[] keywords = {HelperKey.KEY_INTENT_ASSIGNMENTID, HelperKey.KEY_INTENT_ORDERCODE,  HelperKey.KEY_INTENT_IS_EXPENSE, HelperKey.KEY_INTENT_IS_TRIP_OLC};
                String[] values = {HelperBridge.sActivityDetailResponseModel.getAssignmentId()+"", HelperBridge.sAssignedOrderResponseModel.getOrderID(),  HelperBridge.sActivityDetailResponseModel.getIsExpense()+"", HelperBridge.sActivityDetailResponseModel.getTripOLC()+""};
                //planOrderView.changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, HelperBridge.sActivityDetailResponseModel.getAssignmentId() + "", ActivityDetailActivity.class);
                olcTripByOrderView.toggleLoading(false);
                olcTripByOrderView.changeActivityAction(keywords, values, ActivityDetailActivity.class);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                olcTripByOrderView.showToast(response);
                olcTripByOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                olcTripByOrderView.showToast("Terjadi Kesalahan: " + error.toString());
                olcTripByOrderView.toggleLoading(false);
            }
        });
    }


}
