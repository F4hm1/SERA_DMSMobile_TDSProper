package com.serasiautoraya.tdsproper.WsInOutHistory;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by randi on 24/07/2017.
 */

public class WsInOutPresenter extends TiPresenter<WsInOutView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;
    public List<WsInOutResponseModel> wsInOutResponseModels;

    public WsInOutPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    @Override
    protected void onAttachView(@NonNull final WsInOutView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }


    public void initialWsInOutHistoryDatePeriod() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(com.serasiautoraya.tdsproper.util.HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        Calendar calendarMulai = Calendar.getInstance(TimeZone.getDefault());
        calendarMulai.set(Calendar.DAY_OF_MONTH, 1);
        String startdate = dateFormatter.format(calendarMulai.getTime());

        Calendar calendarAkhir = Calendar.getInstance(TimeZone.getDefault());
        String enddate = dateFormatter.format(calendarAkhir.getTime());

        getView().setTextStartDate(startdate);
        getView().setTextEndDate(enddate);
    }

    public void onItemClicked(int position) {

        WsInOutResponseModel itemSelected = (WsInOutResponseModel) mSimpleAdapterModel.getItem(position);
        String clockInDate = HelperUtil.getUserFormDate(itemSelected.getClockIn().split(" ")[0]);
        String clockInTime = itemSelected.getClockIn().split(" ")[1];

        String clockOutDate = HelperUtil.getUserFormDate(itemSelected.getClockOut().split(" ")[0]);
        String clockOutTime = itemSelected.getClockOut().split(" ")[1];
        getView().showDetailDialog(
                "Rekap Kehadiran " + HelperUtil.getUserFormDate(itemSelected.getDate()),
                itemSelected.getScheduleIn().equalsIgnoreCase("") ? "-" : itemSelected.getScheduleIn(),
                itemSelected.getScheduleOut().equalsIgnoreCase("") ? "-" : itemSelected.getScheduleOut(),
                itemSelected.getClockIn().equalsIgnoreCase("") || itemSelected.getClockIn().split("-")[0].equalsIgnoreCase("0001") ? "-" : clockInDate + " " + clockInTime,
                itemSelected.getClockOut().equalsIgnoreCase("") || itemSelected.getClockOut().split("-")[0].equalsIgnoreCase("0001")? "-" : clockOutDate + " " + clockOutTime,
                itemSelected.getAbsence().equalsIgnoreCase("0") ? "-" : "Tidak Hadir"
        );

    }

    public void loadWsInOutHistoryData(String startDate, String endDate) {
        startDate = HelperUtil.getServerFormDate(startDate);
        endDate = HelperUtil.getServerFormDate(endDate);

        wsInOutResponseModels = new ArrayList<>();
        if (!wsInOutResponseModels.isEmpty()) {
            getView().toggleEmptyInfo(false);
        } else {
            getView().toggleEmptyInfo(true);
        }
        mSimpleAdapterModel.setItemList(wsInOutResponseModels);
        getView().refreshRecyclerView();

        getView().toggleLoading(true);

        final WsInOutView orderHistoryView = getView();
        WsInOutSendModel assignedOrderSendModel = new WsInOutSendModel(HelperBridge.sModelLoginResponse.getPersonalId(), startDate, endDate);
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_WSINOUT_HISTORY, assignedOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                List<WsInOutResponseModel> wsInOutResponseModelsTemp = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    wsInOutResponseModelsTemp.add(Model.getModelInstance(response.getData()[i], WsInOutResponseModel.class));
                }

                if (!wsInOutResponseModelsTemp.isEmpty()) {
                    orderHistoryView.toggleEmptyInfo(false);
                } else {
                    orderHistoryView.toggleEmptyInfo(true);
                }
                wsInOutResponseModels = wsInOutResponseModelsTemp;
                mSimpleAdapterModel.setItemList(wsInOutResponseModels);
                orderHistoryView.refreshRecyclerView();
                orderHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                orderHistoryView.toggleEmptyInfo(true);
                orderHistoryView.showToast("FAILLLLSSS: " + response);
                orderHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                orderHistoryView.toggleEmptyInfo(true);
                orderHistoryView.showToast("FAIL: " + error.toString());
                orderHistoryView.toggleLoading(false);
            }
        });

    }

    private void setDummyData() {
        List<WsInOutResponseModel> wsInOutResponseModelsTemp = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            if (i % 3 == 0) {
        wsInOutResponseModelsTemp.add(new WsInOutResponseModel("22" + "-07-2017", "08:" + "00", "17:" + "00", "07:" + "56", "18:" + "23", "", "", "", "", ""));
//            } else if (i % 2 == 0) {
        wsInOutResponseModelsTemp.add(new WsInOutResponseModel("19" + "-07-2017", "08:" + "00", "17:" + "00", "07:" + "38", "", "", "", "", "", ""));
//            } else {
        wsInOutResponseModelsTemp.add(new WsInOutResponseModel("09" + "-07-2017", "08:" + "00", "17:" + "00", "", "", "09-07-2017", "", "", "", ""));
//            }
//        }
        wsInOutResponseModels = wsInOutResponseModelsTemp;
        mSimpleAdapterModel.setItemList(wsInOutResponseModels);
        getView().refreshRecyclerView();
        getView().toggleLoading(false);

    }
}
