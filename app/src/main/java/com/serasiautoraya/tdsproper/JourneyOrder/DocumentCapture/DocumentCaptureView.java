package com.serasiautoraya.tdsproper.JourneyOrder.DocumentCapture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 18/04/2017.
 */

public interface DocumentCaptureView extends BaseViewInterface, FormViewInterface {

    void onClickPhotoCapture1(View view);

    void onClickPhotoCapture2(View view);

    void onClickPhotoCapture3(View view);

    boolean onTouchSignatureCapture(ImageView view, MotionEvent motionEvent);

    void onClickSignatureCapture(View view);

    void startActivityCapture(Intent intent);

    void startActivityOpenGallery(Intent intent);

    void startActivitySignature();

    void setImageThumbnail(Bitmap bitmap, int targetIvID, boolean isPOD);

    void setImageSign(Bitmap bitmap);

    void onClickSubmit(View view);

    void showConfirmationDialog(String activityName);

    void initializeFormContent(boolean isPhoto, boolean isSignature, boolean isVerificationCode, boolean isPOD, String pODGuide);

    void setSubmitText(String text);

    void showConfirmationSuccess(String message, String title);

    void showPhotoPickerSourceDialog();

    Context returnContext();
}
