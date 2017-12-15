package com.serasiautoraya.tdsproper.OLCTrip;

import android.support.annotation.NonNull;
import android.util.Log;

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
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public class OLCTripPresenter extends TiPresenter<OLCTripView> {

    private RestConnection mRestConnection;
    private OLCTripSendModel mOlcTripSendModel;

    @Override
    protected void onAttachView(@NonNull final OLCTripView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public OLCTripPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    public void onSubmitClicked(String date, String olc, String trip, String reason){
        mOlcTripSendModel = new OLCTripSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperBridge.sModelLoginResponse.getPersonalCode(),
                HelperTransactionCode.WFSTATUS_PENDING,
                date,
                olc,
                trip,
                reason,
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperTransactionCode.SUBMIT_TYPE_REQUEST_MOBILE,
                HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail()
        );
        Log.d("OLCTRIP", "APPROVAL: "+ HelperBridge.sModelLoginResponse.getPersonalApprovalId()+" \n"+
                HelperBridge.sModelLoginResponse.getPersonalApprovalEmail()+" \n"+
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorId()+" \n"+
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail());
        getView().showConfirmationDialog();
    }

    public void onRequestSubmitted(){
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_OLCTRIP, mOlcTripSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
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

}
