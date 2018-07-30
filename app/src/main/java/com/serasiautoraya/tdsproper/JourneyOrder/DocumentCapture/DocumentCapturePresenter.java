package com.serasiautoraya.tdsproper.JourneyOrder.DocumentCapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BuildConfig;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.tdsproper.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.Helper.PermissionsHelper;
import com.serasiautoraya.tdsproper.JourneyOrder.StatusUpdateSendModel;
import com.serasiautoraya.tdsproper.RestClient.LocationModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Randi Dwi Nandra on 18/04/2017.
 */

public class DocumentCapturePresenter extends TiPresenter<DocumentCaptureView> {

    private RestConnection mRestConnection;
    private PermissionsHelper mPermissionsHelper;
    private StatusUpdateSendModel mStatusUpdateSendModel;
    private Uri mImageUri;
    private int selectedPhotoId;
    private Bitmap[] mBitmapPhoto;
    private Bitmap mBitmapSignature;

    public DocumentCapturePresenter(RestConnection restConnection, PermissionsHelper mPermissionsHelper) {
        this.mRestConnection = restConnection;
        this.mPermissionsHelper = mPermissionsHelper;
        mBitmapPhoto = new Bitmap[3];
        mBitmapPhoto[0] = null;
        mBitmapPhoto[1] = null;
        mBitmapPhoto[2] = null;
    }

    @Override
    protected void onAttachView(@NonNull final DocumentCaptureView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        getView().initializeFormContent(
                HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getPODGuide()
        );
        getView().setSubmitText(HelperBridge.sActivityDetailResponseModel.getActivityName());
    }

    public void pickImage(int id) {
        selectedPhotoId = id;
        boolean grantedAccess = mPermissionsHelper.isAllPermissionsGranted();
        if (grantedAccess) {
            getView().showPhotoPickerSourceDialog();
        } else {
            mPermissionsHelper.requestLocationPermission();
        }
    }

    public void capturePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try {
            photo = this.createTemporaryFile("temp_bukti_sli", ".jpg");
            photo.delete();
        } catch (Exception e) {
            getView().showToast("Harap cek memory anda! Tidak bisa melakukan pengambilan gambar!");
            return;
        }

        if (Build.VERSION.SDK_INT >= 24) {
            mImageUri = FileProvider.getUriForFile(
                    getView().returnContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    photo);
        } else {
            mImageUri = Uri.fromFile(photo);
        }
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

//    private File createTemporaryFileSDK24(String part, String ext) throws Exception {
//        File imagePath = new File(Context.getFilesDir(), "images");
//        File newFile = new File(imagePath, "default_image.jpg");
//        Uri contentUri = getUriForFile(getContext(), "com.mydomain.fileprovider", newFile);
//
//        File tempDir = Environment.getExternalStorageDirectory();
//        tempDir = new File(tempDir.getPath() + "/.temp/");
//        if (!tempDir.exists()) {
//            tempDir.mkdirs();
//        }
//        File appStorageDir = Environment.getExternalStorageDirectory();
//        appStorageDir = new File(appStorageDir.getPath() + "/" + com.serasiautoraya.tdsproper.util.HelperUrl.DIRECTORY_NAME + "/");
//        if (!appStorageDir.exists()) {
//            appStorageDir.mkdirs();
//        }
//        return File.createTempFile(part, ext, tempDir);
//    }


    public void setBitmapCaptured() {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT >= 24) {
            bitmap = HelperUtil.saveScaledBitmapSDK24(getView().returnContext().getContentResolver(), mImageUri, HelperUtil.getFirstImageName());
        } else {
            bitmap = HelperUtil.saveScaledBitmap(mImageUri.getPath(), HelperUtil.getFirstImageName());
        }
        final Bitmap bitmapScaled = bitmap;
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId, HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY));
    }

    public void setBitmapCapturedByGallery(String path) {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(path, HelperUtil.getFirstImageName());
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId, HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY));
    }

    public void setBitmapCapturedByGallerySDK24(Uri uri) {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmapSDK24(getView().returnContext().getContentResolver(), uri, HelperUtil.getFirstImageName());
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId, HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY));
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        getView().startActivityOpenGallery(intent);
    }

    public void captureSignature() {
        getView().startActivitySignature();
    }

    public void setSignCaptured() {
        if (HelperBridge.sBitmapSignature != null) {
            mBitmapSignature = HelperBridge.sBitmapSignature;
            getView().setImageSign(mBitmapSignature);
        }
    }

    public void onClickSubmit(String verificationCode, final String pODReason, final String exFuel, final String exTollPark, final String exEscort, final String exASDP, final String exPortal, final String exBMSPSI, String[] pODReasonList, String[] pODReasonListValue) {
        Log.d("Activiti", "Photo: " + HelperBridge.sActivityDetailResponseModel.getIsPhoto() + "\n" +
                "POD: " + HelperBridge.sActivityDetailResponseModel.getIsPOD() + "\n" +
                "Verification: " + HelperBridge.sActivityDetailResponseModel.getIsCodeVerification() + "\n" +
                "Signature: " + HelperBridge.sActivityDetailResponseModel.getIsSignature());

        String errText = "";
        boolean resultValidation = true;
        final String verificationCodeSplitted = verificationCode.replace("-", "");

        if (HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            resultValidation = false;
            errText = "Harap mengambil minimal 1 foto terkait aktifitas ini";
            for (int i = 0; i < mBitmapPhoto.length; i++) {
                if (mBitmapPhoto[i] != null) {
                    resultValidation = true;
                    errText = "";
                }
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            if (mBitmapSignature == null) {
                resultValidation = false;
                errText = "Harap meminta tanda tangan digital kepada PIC terkait";
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            if (verificationCodeSplitted.equalsIgnoreCase("")) {
                resultValidation = false;
                errText = "Harap isi kode verifikasi terkait aktifitas ini";
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            resultValidation = false;
            errText = "Harap mengambil minimal 1 foto terkait aktifitas ini";
            for (int i = 0; i < mBitmapPhoto.length; i++) {
                if (mBitmapPhoto[i] != null) {
                    resultValidation = true;
                    errText = "";
                }
            }

            if (!resultValidation) {
                String reason = HelperUtil.getValueStringArrayXML(pODReasonList, pODReasonListValue, pODReason);
            }

        }

//        if(HelperBridge.sActivityDetailResponseModel.getIsExpenseForm().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)){
//            boolean isExpenseValid = false;
////            if(!verificationCode.equalsIgnoreCase("")){
//            isExpenseValid = true;
////            }
//        }

        if (!resultValidation) {
            getView().showToast(errText);
        } else {
            final DocumentCaptureView documentCaptureView = getView();
            final LocationModel locationModel = mRestConnection.getCurrentLocation();
            if (locationModel.getLongitude().equalsIgnoreCase("null")) {
                documentCaptureView.showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
            } else {
                documentCaptureView.toggleLoading(true);
                mRestConnection.getServerTime(new TimeRestCallBackInterface() {

                    @Override
                    public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                        String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
                        String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                        String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                        String date = timeSplit[0];
                        String time = timeSplit[1];
                        String dateMessage = dateSplit[2];
                        String monthMessage = dateSplit[1];
                        String yearMessage = dateSplit[0];

                        mStatusUpdateSendModel = new StatusUpdateSendModel(
                                HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                                HelperBridge.sTempSelectedOrderCode,
                                HelperBridge.sModelLoginResponse.getPersonalId(),
                                locationModel.getLatitude() + ", " + locationModel.getLongitude(),
                                locationModel.getAddress(),
                                mBitmapPhoto[0] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[0]),
                                mBitmapPhoto[1] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[1]),
                                mBitmapPhoto[2] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[2]),
                                verificationCodeSplitted,
                                RestConnection.getUTCTimeStamp(timeRESTResponseModel),
//                                timeRESTResponseModel.getTime(),
                                mBitmapSignature == null ? "" : HelperUtil.encodeTobase64(mBitmapSignature),
                                pODReason,
                                HelperBridge.sActivityDetailResponseModel.getJourneyId() + "",
                                timeRESTResponseModel.getTime(),
                                String.valueOf(HelperBridge.sActivityDetailResponseModel.getTripOLC() ? 1 : 0)
                        );
                        documentCaptureView.toggleLoading(false);
                        documentCaptureView.showConfirmationDialog(HelperBridge.sActivityDetailResponseModel.getActivityName());
                    }

                    @Override
                    public void callBackOnFail(String message) {
                        documentCaptureView.toggleLoading(false);
                        documentCaptureView.showStandardDialog(message, "Perhatian");
                    }
                });
            }
        }
    }

    public void onRequestSubmitActivity() {
        getView().toggleLoading(true);
//        Log.d("DOCUMENT_CAPTURE", mStatusUpdateSendModel.getHashMapType().toString());
//        Log.d("DOCUMENT_CAPTURE", "Photo 1: " + mStatusUpdateSendModel.getPhoto1().toString());
//        Log.d("DOCUMENT_CAPTURE", "Photo 2: " + mStatusUpdateSendModel.getPhoto2().toString());
//        Log.d("DOCUMENT_CAPTURE", "Photo 3: " + mStatusUpdateSendModel.getPhoto3().toString());
        final DocumentCaptureView documentCaptureView = getView();
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_STATUS_UPDATE, mStatusUpdateSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    documentCaptureView.toggleLoading(false);
                    documentCaptureView.showConfirmationSuccess(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                documentCaptureView.toggleLoading(false);
                documentCaptureView.showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                documentCaptureView.toggleLoading(false);
                documentCaptureView.showStandardDialog("Gagal melakukan update status, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void finishCurrentDetailActivity() {
        if (HelperBridge.sCurrentDetailActivity != null) {
            HelperBridge.sCurrentDetailActivity.finish();
        }
    }

    public void validateData(String verificationCode) {
        if (HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            boolean isPhotosValid = false;
            for (int i = 0; i < mBitmapPhoto.length; i++) {
                if (mBitmapPhoto[i] != null) {
                    isPhotosValid = true;
                }
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            boolean isSignatureValid = false;
            if (mBitmapSignature != null) {
                isSignatureValid = true;
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            boolean isVerificationCodeValid = false;
            if (!verificationCode.equalsIgnoreCase("")) {
                isVerificationCodeValid = true;
            }
        }

//        if(HelperBridge.sActivityDetailResponseModel.getIsExpenseForm().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)){
//            boolean isExpenseValid = false;
////            if(!verificationCode.equalsIgnoreCase("")){
//                isExpenseValid = true;
////            }
//        }
    }
}