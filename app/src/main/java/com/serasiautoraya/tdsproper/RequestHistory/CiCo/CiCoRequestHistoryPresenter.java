package com.serasiautoraya.tdsproper.RequestHistory.CiCo;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RequestHistory.RequestHistoryResponseModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class CiCoRequestHistoryPresenter extends TiPresenter<CiCoRequestHistoryView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;
    private CiCoDeleteSendModel mCiCoDeleteSendModel;

    public CiCoRequestHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final CiCoRequestHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void loadRequestHistoryData() {
        getView().toggleEmptyInfo(true);
        if (!HelperBridge.sCiCoRequestHistoryList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sCiCoRequestHistoryList);
        getView().refreshRecyclerView();
    }

    public void onCancelClicked(RequestHistoryResponseModel requestHistoryResponseModel) {
        mCiCoDeleteSendModel = new CiCoDeleteSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                requestHistoryResponseModel.getId());
        getView().showCancelConfirmationDialog(requestHistoryResponseModel.getRequestDate());
    }

    public void onCancelationSubmitted() {
        getView().toggleLoading(true);
        mRestConnection.deleteData(
                HelperBridge.sModelLoginResponse.getTransactionToken(),
                HelperUrl.DELETE_CANCEL_CICO,
                mCiCoDeleteSendModel.getHashMapType(),
                new RestCallBackInterfaceModel() {
                    @Override
                    public void callBackOnSuccess(BaseResponseModel response) {
                        getView().toggleLoading(false);
                        getView().showStandardDialog(response.getResponseText(), "Berhasil");
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
                        getView().showStandardDialog("Gagal membatalkan pengajuan cico, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
                    }
                });
    }

    public void onDetailClicked(RequestHistoryResponseModel requestHistoryResponseModel) {
        getView().showDetailDialog(
                requestHistoryResponseModel.getTransType(),
                requestHistoryResponseModel.getDateStart(),
                "Pengajuan Tanggal" + requestHistoryResponseModel.getRequestDate(),
                requestHistoryResponseModel.getRequestStatus(),
                requestHistoryResponseModel.getApprovalBy()
        );
    }
}
