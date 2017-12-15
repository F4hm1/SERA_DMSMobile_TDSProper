package com.serasiautoraya.tdsproper.ModuleServiceHourHistory;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.Helper.HelperUtil;
import com.serasiautoraya.tdsproper.RequestHistory.Absence.AbsenceDeleteSendModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 11/06/2017.
 */

public class ServiceHourHistoryPresenter extends TiPresenter<ServiceHourHistoryView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;
    private AbsenceDeleteSendModel mAbsenceDeleteSendModel;

    public ServiceHourHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final ServiceHourHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel){
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void loadRequestHistoryData(String startDate, String endDate) {
        startDate = HelperUtil.getServerFormDate(startDate);
        endDate = HelperUtil.getServerFormDate(endDate);

        HelperBridge.sAbsenceRequestHistoryList = new ArrayList<>();
        HelperBridge.sCiCoRequestHistoryList = new ArrayList<>();
        HelperBridge.sOvertimeRequestHistoryList = new ArrayList<>();
        HelperBridge.sOLCRequestHistoryList = new ArrayList<>();

        getView().toggleLoading(true);
        ServiceHourHistorySendModel requestHistorySendModel = new ServiceHourHistorySendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                startDate,
                endDate);

        final ServiceHourHistoryView requestHistoryView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_SERVICE_HOUR_HISTORY, requestHistorySendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                List<ServiceHourHistoryResponseModel> requestHistoryResponseModels = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    requestHistoryResponseModels.add(Model.getModelInstance(response.getData()[i], ServiceHourHistoryResponseModel.class));
                }
                requestHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                requestHistoryView.showToast("FAILLLLSSS: " + response);
                requestHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                requestHistoryView.showToast("FAIL: " + error.toString());
                requestHistoryView.toggleLoading(false);
            }
        });
    }

}
