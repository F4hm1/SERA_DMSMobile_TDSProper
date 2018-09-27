package com.serasiautoraya.tdsproper.Absence;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class AbsenceRequestPresenter extends TiPresenter<AbsenceRequestView> {

    private RestConnection mRestConnection;
    private AbsenceRequestSendModel mAbsenceRequestSendModel;

    @Override
    protected void onAttachView(@NonNull final AbsenceRequestView view) {
        super.onAttachView(view);
        getView().initialize();
        int daysMinRequest = 0;
        daysMinRequest = Math.round(Float.valueOf(HelperBridge.sModelLoginResponse.getMinHariRequestAbsence()));
        getView().initializeDatePickerMinRequest(daysMinRequest);
    }

    public AbsenceRequestPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    public void onSubmitClicked(String dateStart, String dateEnd, String reason, String absenceType){
        mAbsenceRequestSendModel = new AbsenceRequestSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperBridge.sModelLoginResponse.getPersonalCode(),
                HelperTransactionCode.WFSTATUS_PENDING,
                absenceType,
                dateStart,
                dateEnd,
                reason,
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperTransactionCode.SUBMIT_TYPE_REQUEST_MOBILE,
                HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                HelperBridge.sModelLoginResponse.getSalesOffice()
        );

        getView().showConfirmationDialog();
    }
    

    public void onRequestSubmitted(){
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_ABSENCE, mAbsenceRequestSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
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
                getView().showStandardDialog("Gagal melakukan pengajuan ketidakhadiran, silahkan periksa koneksi Anda kemudian coba kembali", "Perhatian");
            }
        });
    }

}
