package com.serasiautoraya.tdsproper.JourneyOrder.PODCapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.tdsproper.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RestClient.LocationModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by randi on 15/08/2017.
 */

public class PODCapturePresenter extends TiPresenter<PODCaptureView> {

    private RestConnection mRestConnection;
    private int mSelectedPhotoId;
    private Bitmap[] mBitmapPhoto;
    private Uri mImageUri;

    private ArrayList<String> uploadedPhotos = new ArrayList<>();
    private HashMap<Integer, PODPhotoSendModel> mPodPhotoSendModelHashMap;

    public PODCapturePresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
        mPodPhotoSendModelHashMap = new HashMap<>();
        mBitmapPhoto = new Bitmap[10];
    }

    @Override
    protected void onAttachView(@NonNull final PODCaptureView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        getView().setSubmitText(HelperBridge.sActivityDetailResponseModel.getActivityName());
    }

    public void capturePhoto(int id) {
        mSelectedPhotoId = id;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try {
            photo = this.createTemporaryFile("temp_pod_sli", ".jpg");
            photo.delete();
        } catch (Exception e) {
            getView().showToast("Harap cek memory anda! Tidak bisa melakukan pengambilan gambar!");
            return;
        }

        mImageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        getView().startActivityCapture(intent);
    }

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getPath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        File appStorageDir = Environment.getExternalStorageDirectory();
        appStorageDir = new File(appStorageDir.getPath() + "/" + com.serasiautoraya.tdsproper.util.HelperUrl.DIRECTORY_NAME + "/");
        if (!appStorageDir.exists()) {
            appStorageDir.mkdirs();
        }

        return File.createTempFile(part, ext, tempDir);
    }

    public void setBitmapCaptured() {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(mImageUri.getPath(), HelperUtil.getFirstImageName());
//        mBitmapPhoto[mSelectedPhotoId - 1] = bitmapScaled;
        mPodPhotoSendModelHashMap.put(
                mSelectedPhotoId,
                new PODPhotoSendModel(
                        HelperUtil.encodeTobase64(bitmapScaled),
                        HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                        HelperBridge.sTempSelectedOrderCode,
                        HelperBridge.sModelLoginResponse.getPersonalId()
                )
        );
        getView().setImageThumbnail(bitmapScaled, mSelectedPhotoId, true);
    }

    public void onRequestSubmitted() {
        for (Map.Entry<Integer, PODPhotoSendModel> entry : mPodPhotoSendModelHashMap.entrySet()) {
            Integer containerId = entry.getKey();
            getView().showProgressBar(containerId);
        }
        for (Map.Entry<Integer, PODPhotoSendModel> entry : mPodPhotoSendModelHashMap.entrySet()) {
            Integer containerId = entry.getKey();
            PODPhotoSendModel value = entry.getValue();
            postPhoto(value, containerId);
        }

    }

    private void postPhoto(PODPhotoSendModel podPhotoSendModel, final Integer containerId) {
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_POD_PHOTO, podPhotoSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().showToast(response.getString("responseText"));
                    uploadedPhotos.add(containerId + "");
                    getView().hideProgressBar(containerId);
                    Log.d("PODES", "Success: " + response.getString("responseText"));
                    if (uploadedPhotos.size() == mPodPhotoSendModelHashMap.size()) {
                        getView().showConfirmationDialog(HelperBridge.sActivityDetailResponseModel.getActivityName());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().showToast(response);
                getView().hideProgressBar(containerId);
                Log.d("PODES", "Fail: " + response);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().showToast("Gagal melakukan pengajuan ketidakhadiran, silahkan periksa koneksi anda kemudian coba kembali");
                getView().hideProgressBar(containerId);
                Log.d("PODES", "Error: " + error.getMessage());
            }
        });
    }

    public void onRequestSubmitActivity() {
        getView().toggleLoading(true);
        final LocationModel locationModel = mRestConnection.getCurrentLocation();
        if (locationModel.getLongitude().equalsIgnoreCase("null")) {
            getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
        } else {
            getView().toggleLoading(true);
            mRestConnection.getServerTime(new TimeRestCallBackInterface() {
                @Override
                public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                    String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                    String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                    String date = timeSplit[0];
                    String time = timeSplit[1];

                    PODUpdateSendModel podUpdateSendModel = new PODUpdateSendModel(
                            HelperBridge.sModelLoginResponse.getPersonalId(),
                            HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                            "Reason: -",
//                            date + " " + time,
                            timeRESTResponseModel.getTime(),
                            HelperBridge.sActivityDetailResponseModel.getJourneyId()+"",
                            locationModel.getLatitude() + ", " + locationModel.getLongitude(),
                            locationModel.getAddress(),
                            RestConnection.getUTCTimeStamp(timeRESTResponseModel)
                    );

                    submitPOD(podUpdateSendModel);
                }

                @Override
                public void callBackOnFail(String message) {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(message, "Perhatian");
                }
            });
        }
    }

    private void submitPOD(PODUpdateSendModel podUpdateSendModel) {
        getView().toggleLoading(true);
        final PODCaptureView podCaptureView = getView();
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_POD, podUpdateSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    podCaptureView.toggleLoading(false);
                    podCaptureView.showConfirmationSuccess(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                podCaptureView.toggleLoading(false);
                podCaptureView.showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                podCaptureView.toggleLoading(false);
                podCaptureView.showStandardDialog("Gagal melakukan ack order, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void onThumbnailClosed(Integer ibId, Integer cardId) {
        mPodPhotoSendModelHashMap.remove(cardId);
        getView().hideThumbnail(ibId);
    }

    public void finishCurrentDetailActivity() {
        if (HelperBridge.sCurrentDetailActivity != null) {
            HelperBridge.sCurrentDetailActivity.finish();
        }
    }
}
