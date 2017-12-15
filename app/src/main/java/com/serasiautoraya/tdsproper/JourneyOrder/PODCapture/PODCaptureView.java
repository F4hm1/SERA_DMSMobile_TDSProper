package com.serasiautoraya.tdsproper.JourneyOrder.PODCapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by randi on 15/08/2017.
 */

public interface PODCaptureView extends BaseViewInterface{

    void onClickAddPhoto(View view);

    void startActivityCapture(Intent intent);

    void setImageThumbnail(Bitmap bitmap, int targetIvID, boolean isPOD);

    void onClickSubmit(View view);

    void onClickClose(View view);

    void hideThumbnail(Integer ibId);

    void showConfirmationDialog(String activityName);

    void setSubmitText(String text);

    void showProgressBar(Integer containerId);

    void hideProgressBar(Integer containerId);

    void showConfirmationSuccess(String message, String title);
}
