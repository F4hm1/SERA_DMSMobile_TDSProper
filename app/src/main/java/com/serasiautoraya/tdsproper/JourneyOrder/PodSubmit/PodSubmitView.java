package com.serasiautoraya.tdsproper.JourneyOrder.PodSubmit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public interface PodSubmitView extends BaseViewInterface {

    void startActivityCapture(Intent intent);

    void startActivityOpenGallery(Intent intent);

    void setImageThumbnail(Bitmap bitmapScaled, int mCurrentSelectedPosition, boolean b, boolean isPreview);

    void onClickSubmit(View view);

    void toggleProgressBar(int position, boolean show);

    void showConfirmationDialog(String activityName);

    void setSubmitText(String activityName);

    void showPhotosRequiredAlert();

    void setGuideText(String text);

    void showPhotoPickerSourceDialog();

    void toggleSubmitButton(boolean show);

    void showConfirmationSuccess(String message, String title);

    Context returnContext();
}
