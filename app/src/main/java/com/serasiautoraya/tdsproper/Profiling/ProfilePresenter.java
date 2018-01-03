package com.serasiautoraya.tdsproper.Profiling;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
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
import com.serasiautoraya.tdsproper.JourneyOrder.Assigned.PlanOrderView;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public class ProfilePresenter extends TiPresenter<ProfileView> {

    private RestConnection mRestConnection;

    public ProfilePresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    @Override
    protected void onAttachView(@NonNull final ProfileView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public void loadProfileData() {
        getView().setProfileContent(
                HelperBridge.sModelLoginResponse.getFullname(),
                "Transporter",
                HelperBridge.sModelLoginResponse.getCompany(),
                HelperBridge.sModelLoginResponse.getPoolName(),
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperBridge.sModelLoginResponse.getFullname(),
                HelperBridge.sModelLoginResponse.getPersonalApprovalName(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorName(),
                HelperBridge.sModelLoginResponse.getKtpEndDate().equalsIgnoreCase("") ? "-" : HelperUtil.getUserFormDate(HelperBridge.sModelLoginResponse.getKtpEndDate()),
                HelperBridge.sModelLoginResponse.getSIMEndDate().equalsIgnoreCase("") ? "-" : HelperUtil.getUserFormDate(HelperBridge.sModelLoginResponse.getSIMEndDate()),
                "Tanggal Berakhir " + HelperBridge.sModelLoginResponse.getSimType()
        );
        getView().setProfilePhoto(HelperBridge.sModelLoginResponse.getPhotoFront());
        getDriverStatus();
    }

    public void toggleDriverStatus(final boolean isOn) {
        getView().toggleProgressDriverStatusUpdate(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                String dateTime = timeRESTResponseModel.getTime();

                DriverStatusSendModel driverStatusSendModel = new DriverStatusSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        isOn ? HelperTransactionCode.TRUE_BINARY : HelperTransactionCode.FALSE_BINARY,
                        dateTime
                );
                toggleDriverStatus(driverStatusSendModel, isOn);
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleProgressDriverStatusUpdate(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });

    }

    public void toggleDriverStatus(DriverStatusSendModel driverStatusSendModel, final boolean currentStatus) {
        if (getView() != null) {
            getView().toggleProgressDriverStatusUpdate(true);
        }
        final ProfileView profileView = getView();
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_DRIVER_STATUS, driverStatusSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    profileView.toggleProgressDriverStatusUpdate(false);
                    profileView.toggleDriverStatus(currentStatus);
                    profileView.showToast(response.getString("responseText"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                profileView.toggleProgressDriverStatusUpdate(false);
                profileView.showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                profileView.toggleProgressDriverStatusUpdate(false);
                profileView.showStandardDialog("Gagal melakukan perubahan status, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void getDriverStatus() {
        HashMap<String, String> sendMap = new HashMap<>();
        sendMap.put("PersonalId", HelperBridge.sModelLoginResponse.getPersonalId());
        final ProfileView profileView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_DRIVER_STATUS, sendMap, new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                if (response.getResponse().equalsIgnoreCase(HelperKey.RESPONSE_STATUS_SUCCESS_CODE)) {
                    profileView.toggleProgressDriverStatusUpdate(false);
                    DriverStatusResponseModel driverStatusResponseModel = Model.getModelInstance(response.getData()[0], DriverStatusResponseModel.class);
                    if (driverStatusResponseModel.getStatus().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
                        profileView.toggleDriverStatus(true);
                    } else {
                        profileView.toggleDriverStatus(false);
                    }
                } else {
                    profileView.showToast(response.getResponseText());
                    profileView.toggleProgressDriverStatusUpdate(false);
                }
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                profileView.showToast(response);
                profileView.toggleProgressDriverStatusUpdate(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                profileView.showToast("Terjadi Kesalahan: " + error.toString());
                profileView.toggleProgressDriverStatusUpdate(false);
            }
        });
    }

}
