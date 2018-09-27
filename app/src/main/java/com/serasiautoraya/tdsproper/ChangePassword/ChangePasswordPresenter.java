package com.serasiautoraya.tdsproper.ChangePassword;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public class ChangePasswordPresenter extends TiPresenter<ChangePasswordView> {

    private SharedPrefsModel mSharedPrefsModel;
    private RestConnection mRestConnection;
    private ChangePasswordSendModel mChangePasswordSendModel;

    public ChangePasswordPresenter(SharedPrefsModel sharedPrefsModel, RestConnection mRestConnection) {
        this.mSharedPrefsModel = sharedPrefsModel;
        this.mRestConnection = mRestConnection;
    }

    @Override
    protected void onAttachView(@NonNull final ChangePasswordView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void onSubmitClicked(String newPassword) {
        if (getView().getValidationForm(mSharedPrefsModel.get(HelperKey.KEY_PASSWORD, ""))) {
            mChangePasswordSendModel = new ChangePasswordSendModel(
                    HelperBridge.sModelLoginResponse.getPersonalId(),
                    mSharedPrefsModel.get(HelperKey.KEY_PASSWORD, ""),
                    newPassword,
                    newPassword
            );
            getView().showConfirmationDialog();
        }
    }

    public void onRequestSubmitted() {
        getView().toggleLoading(true);
        mRestConnection.putData(
                HelperBridge.sModelLoginResponse.getTransactionToken(),
                HelperUrl.PUT_CHANGE_PASSWORD,
                mChangePasswordSendModel.getHashMapType(),
                new RestCallbackInterfaceJSON() {
                    @Override
                    public void callBackOnSuccess(JSONObject response) {
                        try {
                            getView().toggleLoading(false);
                            getView().showStandardDialog(response.getString("responseText"), "Berhasil");
                            mSharedPrefsModel.apply(HelperKey.KEY_PASSWORD, mChangePasswordSendModel.getNewPassword());
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
                        getView().showStandardDialog("Gagal melakukan penggantian kata sandi, silahkan periksa koneksi Anda kemudian coba kembali", "Perhatian");
                    }
                });
    }
}
