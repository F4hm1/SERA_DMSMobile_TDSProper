package com.serasiautoraya.tdsproper.RequestHistory.Absence;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RequestHistory.RequestHistoryResponseModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class AbsenceRequestHistoryPresenter extends TiPresenter<AbsenceRequestHistoryView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;
    private AbsenceDeleteSendModel mAbsenceDeleteSendModel;

    public AbsenceRequestHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final AbsenceRequestHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void loadRequestHistoryData() {
        getView().toggleEmptyInfo(true);
        if (!HelperBridge.sAbsenceRequestHistoryList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sAbsenceRequestHistoryList);
        getView().refreshRecyclerView();
    }

    public void onCancelClicked(RequestHistoryResponseModel requestHistoryResponseModel) {
        mAbsenceDeleteSendModel = new AbsenceDeleteSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                requestHistoryResponseModel.getId());
        getView().showCancelConfirmationDialog(requestHistoryResponseModel.getRequestDate());
    }

    public void onCancelationSubmitted() {
        getView().toggleLoading(true);
        mRestConnection.deleteData(
                HelperBridge.sModelLoginResponse.getTransactionToken(),
                HelperUrl.DELETE_ABSENCE,
                mAbsenceDeleteSendModel.getHashMapType(),
                new RestCallBackInterfaceModel() {
                    @Override
                    public void callBackOnSuccess(BaseResponseModel response) {
                        getView().toggleLoading(false);
                        getView().showStandardDialog(response.getResponseText(), "Berhasil");
                        getView().refreshAllData();
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
                        getView().showStandardDialog("Gagal membatalkan pengajuan ketidakhadiran, silahkan periksa koneksi Anda kemudian coba kembali", "Perhatian");
                    }
                }
        );
    }

    public void onDetailClicked(RequestHistoryResponseModel requestHistoryResponseModel) {
        getView().showDetailDialog(
                requestHistoryResponseModel.getTransType(),
                HelperUtil.getUserFormDate(requestHistoryResponseModel.getDateStart()),
                HelperUtil.getUserFormDate(requestHistoryResponseModel.getDateEnd()),
                requestHistoryResponseModel.getAbsenceType(),
                "Pengajuan " + HelperUtil.getUserFormDate(requestHistoryResponseModel.getRequestDate()),
                requestHistoryResponseModel.getRequestStatus(),
                requestHistoryResponseModel.getApprovalBy()
        );
    }

}
