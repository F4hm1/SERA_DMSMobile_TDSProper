package com.serasiautoraya.tdsproper.JourneyOrder.PodSubmit;

import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.serasiautoraya.tdsproper.CustomView.SquareImageView;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodItemModel {
    private Bitmap bitmap;
    private SquareImageView squareImageView;
    private ImageButton imageButton;
    private ProgressBar progressBar;
    private int adapterIndex;
    private boolean isCloseButtonAppear = true;

    public PodItemModel(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setSquareImageView(SquareImageView squareImageView) {
        this.squareImageView = squareImageView;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public SquareImageView getSquareImageView() {
        return squareImageView;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setAdapterIndex(int adapterIndex) {
        this.adapterIndex = adapterIndex;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public int getAdapterIndex() {
        return adapterIndex;
    }

    public boolean isCloseButtonAppear() {
        return isCloseButtonAppear;
    }

    public void setCloseButtonAppear(boolean closeButtonAppear) {
        isCloseButtonAppear = closeButtonAppear;
    }
}
