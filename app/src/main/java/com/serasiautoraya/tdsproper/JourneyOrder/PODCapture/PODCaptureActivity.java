package com.serasiautoraya.tdsproper.JourneyOrder.PODCapture;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.CustomView.SquareImageView;
import com.serasiautoraya.tdsproper.CustomView.SquareRelativeLayout;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by randi on 11/08/2017.
 */

public class PODCaptureActivity extends TiActivity<PODCapturePresenter, PODCaptureView> implements PODCaptureView {

    @BindView(R.id.pod_btn_submit)
    Button mBtnSubmit;

//    @BindView(R.id.pod_pb_progress_1)
//    ProgressBar mPbProgress1;
//
//    @BindView(R.id.pod_pb_progress_2)
//    ProgressBar mPbProgress2;
//
//    @BindView(R.id.pod_pb_progress_3)
//    ProgressBar mPbProgress3;
//
//    @BindView(R.id.pod_pb_progress_4)
//    ProgressBar mPbProgress4;
//
//    @BindView(R.id.pod_pb_progress_5)
//    ProgressBar mPbProgress5;


//    @BindView(R.id.pod_tv_progress_1)
//    TextView mTvProgress1;
//
//    @BindView(R.id.pod_tv_progress_2)
//    TextView mTvProgress2;
//
//    @BindView(R.id.pod_tv_progress_3)
//    TextView mTvProgress3;
//
//    @BindView(R.id.pod_tv_progress_4)
//    TextView mTvProgress4;
//
//    @BindView(R.id.pod_tv_progress_5)
//    TextView mTvProgress5;

    @BindView(R.id.pod_card_container_1)
    CardView mCardContainer1;

    @BindView(R.id.pod_card_container_2)
    CardView mCardContainer2;

    @BindView(R.id.pod_card_container_3)
    CardView mCardContainer3;

    @BindView(R.id.pod_card_container_4)
    CardView mCardContainer4;

    @BindView(R.id.pod_card_container_5)
    CardView mCardContainer5;

    @BindView(R.id.pod_card_container_6)
    CardView mCardContainer6;

    @BindView(R.id.pod_card_container_7)
    CardView mCardContainer7;

    @BindView(R.id.pod_card_container_8)
    CardView mCardContainer8;

    @BindView(R.id.pod_card_container_9)
    CardView mCardContainer9;

    @BindView(R.id.pod_card_container_10)
    CardView mCardContainer10;


    @BindView(R.id.pod_grid_content)
    GridLayout mGl;
    int totalView = 10;
    int currentTotalViewShown = 0;
    SquareRelativeLayout[] squareRelativeLayoutArr;
    private boolean isFromCameraActivity = false;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_podcapture);
        ButterKnife.bind(this);
        squareRelativeLayoutArr = new SquareRelativeLayout[totalView];
//        generateViewsThumbnail();
        initializeThumbnails();
    }

    private void initializeThumbnails() {
//        View view = (View) mCardContainer1.getParent();
        mCardContainer1.setVisibility(View.VISIBLE);

//        View view2 = (View) mCardContainer2.getParent();
        mCardContainer2.setVisibility(View.GONE);

//        View view3 = (View) mCardContainer3.getParent();
        mCardContainer3.setVisibility(View.GONE);

        View view4 = (View) mCardContainer4.getParent();
        view4.setVisibility(View.GONE);

        View view5 = (View) mCardContainer5.getParent();
        view5.setVisibility(View.GONE);

        View view6 = (View) mCardContainer6.getParent();
        view6.setVisibility(View.GONE);

        View view7 = (View) mCardContainer7.getParent();
        view7.setVisibility(View.GONE);

        View view8 = (View) mCardContainer8.getParent();
        view8.setVisibility(View.GONE);

        View view9 = (View) mCardContainer9.getParent();
        view9.setVisibility(View.GONE);

        View view10 = (View) mCardContainer10.getParent();
        view10.setVisibility(View.GONE);

    }


    @OnClick(R.id.pod_btn_submit)
    public void onSubmit(View view) {
        getPresenter().onRequestSubmitted();
//        int total = 10;
//        int column = 3;
//        int row = total / column;
//        mGl.setColumnCount(column);
//        mGl.setRowCount(row + 1);
//
//        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
//
//            if (c == column) {
//                c = 0;
//                r++;
//            }
//            ImageView oImageView = new ImageView(this);
//            oImageView.setImageResource(R.drawable.ic_empty_attendance);
//
//            oImageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
//
//            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
//            GridLayout.Spec colspan = GridLayout.spec(GridLayout.UNDEFINED, 1);
//            if (r == 0 && c == 0) {
//
//                colspan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//                rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//            }
//
//            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colspan);
//
//            mGl.addView(oImageView, gridParam);
//        }

//        View photoThumbnail = LayoutInflater.from(this).inflate(R.layout.single_pod_thumbnail, null);
//        mGl.addView(photoThumbnail);


//        addThumbnail();


//        View viewLay = LayoutInflater.from(PODCaptureActivity.this).inflate(R.layout.single_pod_thumbnail, null);
//        SquareRelativeLayout squareRelativeLayout = (SquareRelativeLayout)viewLay.findViewById(R.id.squareRelLayout);
////        squareRelativeLayout.refreshSize();
//
//        for (int i = 0; i <= totalView; i++) {
//            mGl.addView(squareRelativeLayout);
//        }
//        totalView++;
//        mGl.invalidate();
//        squareRelativeLayout.refreshSize();

//        ImageView oImageView = new ImageView(this);
//        oImageView.setImageResource(R.drawable.ic_empty_attendance);
//        oImageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
//        mGl.addView(oImageView);

//        int progress1 = 0;
//        while (progress1 < 100) {
//            progress1 += 4;
//            if (progress1 >= 100) {
//                progress1 = 100;
//            }
//            mPbProgress1.setProgress(progress1);
//            mTvProgress1.setText(progress1 + "%");
//        }
//
//        int progress2 = 0;
//        while (progress2 < 100) {
//            progress2 += 3;
//            if (progress2 >= 100) {
//                progress2 = 100;
//            }
//            mPbProgress2.setProgress(progress2);
//            mTvProgress2.setText(progress2 + "%");
//        }
//
//        int progress3 = 0;
//        while (progress3 < 100) {
//            progress3 += 2;
//            if (progress3 >= 100) {
//                progress3 = 100;
//            }
//            mPbProgress3.setProgress(progress3);
//            mTvProgress3.setText(progress3 + "%");
//        }
//
//        int progress4 = 0;
//        while (progress4 < 100) {
//            progress4 += 1;
//            if (progress4 >= 100) {
//                progress4 = 100;
//            }
//            mPbProgress4.setProgress(progress4);
//            mTvProgress4.setText(progress4 + "%");
//        }
    }

    private void addThumbnail() {
        if (currentTotalViewShown < 10) {
            squareRelativeLayoutArr[currentTotalViewShown].setVisibility(View.VISIBLE);
            currentTotalViewShown++;
        }
    }

    private void generateViewsThumbnail() {
        for (int i = 0; i < totalView; i++) {
            View viewLay = LayoutInflater.from(PODCaptureActivity.this).inflate(R.layout.single_pod_thumbnail, null);
            SquareRelativeLayout squareRelativeLayout = (SquareRelativeLayout) viewLay.findViewById(R.id.squareRelLayout);
            squareRelativeLayout.setLayoutParams(new GridLayout.LayoutParams());
            squareRelativeLayout.invalidate();
            squareRelativeLayout.requestLayout();
            mGl.addView(squareRelativeLayout);
            mGl.invalidate();
            mGl.requestLayout();
            squareRelativeLayout.invalidate();
            squareRelativeLayout.requestLayout();
            squareRelativeLayoutArr[i] = squareRelativeLayout;
            Snackbar.make(mGl, "Created : " + i, Snackbar.LENGTH_SHORT);
//            if (i < 3) {
//                if (i == 0) {
//                    squareRelativeLayout.setVisibility(View.VISIBLE);
//                    currentTotalViewShown++;
//                } else {
//                    squareRelativeLayout.setVisibility(View.INVISIBLE);
//                }
//            } else {
//                squareRelativeLayout.setVisibility(View.GONE);
//            }
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(PODCaptureActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(PODCaptureActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, PODCaptureActivity.this, Title);
    }

    @NonNull
    @Override
    public PODCapturePresenter providePresenter() {
        return new PODCapturePresenter(new RestConnection(PODCaptureActivity.this));
    }

    @Override
    @OnClick(
            {
                    R.id.pod_card_container_1, R.id.pod_card_container_2, R.id.pod_card_container_3, R.id.pod_card_container_4, R.id.pod_card_container_5,
                    R.id.pod_card_container_6, R.id.pod_card_container_7, R.id.pod_card_container_8, R.id.pod_card_container_9, R.id.pod_card_container_10
            }
    )
    public void onClickAddPhoto(View view) {
        getPresenter().capturePhoto(view.getId());
    }

    @Override
    public void startActivityCapture(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE);
    }

    @Override
    public void setImageThumbnail(Bitmap bitmap, int targetIvID, boolean isPOD) {
        CardView cardView = (CardView) findViewById(targetIvID);
        int count = cardView.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = cardView.getChildAt(i);
            if (v instanceof SquareImageView) {
                SquareImageView squareImageView = (SquareImageView) v;
                squareImageView.setImageBitmap(bitmap);
                toggleNextThumbnail(targetIvID);
            }

            if (v instanceof ImageButton) {
                ImageButton imageButton = (ImageButton) v;
                imageButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void toggleNextThumbnail(int targetIvID) {
        CardView targetCardView = getNextThumbnail(targetIvID);
        if(targetIvID == R.id.pod_card_container_1 || targetIvID == R.id.pod_card_container_2){
            targetCardView.setVisibility(View.VISIBLE);
        }else {
            View viewParentContainer = (View) targetCardView.getParent();
            viewParentContainer.setVisibility(View.VISIBLE);
        }
    }

    private CardView getNextThumbnail(int currentId){
        switch (currentId){
            case R.id.pod_card_container_1:{
                return mCardContainer2;
            }
            case R.id.pod_card_container_2:{
                return mCardContainer3;
            }
            case R.id.pod_card_container_3:{
                return mCardContainer4;
            }
            case R.id.pod_card_container_4:{
                return mCardContainer5;
            }
            case R.id.pod_card_container_5:{
                return mCardContainer6;
            }
            case R.id.pod_card_container_6:{
                return mCardContainer7;
            }
            case R.id.pod_card_container_7:{
                return mCardContainer8;
            }
            case R.id.pod_card_container_8:{
                return mCardContainer9;
            }
            case R.id.pod_card_container_9:{
                return mCardContainer10;
            }
            default:{
                return mCardContainer10;
            }
        }
    }
    @Override
    public void onClickSubmit(View view) {

    }

    @Override
    @OnClick(
            {
                    R.id.pod_ib_close_2, R.id.pod_ib_close_3, R.id.pod_ib_close_4, R.id.pod_ib_close_5,
                    R.id.pod_ib_close_6, R.id.pod_ib_close_7, R.id.pod_ib_close_8, R.id.pod_ib_close_9, R.id.pod_ib_close_10
            }
    )
    public void onClickClose(View view) {
        View viewCard = (View) view.getParent();
        getPresenter().onThumbnailClosed(view.getId(), viewCard.getId());
    }

    @Override
    public void hideThumbnail(Integer ibId) {
        View view = (View) findViewById(ibId).getParent();
        resetThumbnail(view.getId());

        CardView cardView = getNextThumbnail(view.getId());
        if(ibId == R.id.pod_ib_close_2 || ibId == R.id.pod_ib_close_3){
            cardView.setVisibility(View.GONE);
        }else {
            View viewParentContainer = (View) cardView.getParent();
            viewParentContainer.setVisibility(View.GONE);
        }
    }

    private void resetThumbnail(int targetIvID){
        CardView cardView = (CardView) findViewById(targetIvID);
        int count = cardView.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = cardView.getChildAt(i);
            if (v instanceof SquareImageView) {
                SquareImageView squareImageView = (SquareImageView) v;
                squareImageView.setImageResource(R.drawable.add_foto_pod);
            }
            if (v instanceof ImageButton) {
                ImageButton imageButton = (ImageButton) v;
                imageButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showConfirmationDialog(String activityName) {
        CharSequence textMsg = Html.fromHtml("Apakah Anda yakin semua dokumen telah terpenuhi dan akan melakukan proses " +
                "<b>" + activityName + "</b>" + "?");

        HelperUtil.showConfirmationAlertDialog(textMsg, PODCaptureActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitActivity();
            }
        });
    }

    @Override
    public void showConfirmationSuccess(String message, String title) {
        HelperUtil.showSimpleAlertDialogCustomTitleAction(message, PODCaptureActivity.this, title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().finishCurrentDetailActivity();
                        finish();
                    }
                },
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getPresenter().finishCurrentDetailActivity();
                        finish();
                    }
                });
    }

    @Override
    public void setSubmitText(String text) {
        mBtnSubmit.setText(text);
    }

    @Override
    public void showProgressBar(Integer containerId) {
        CardView cardView = (CardView) findViewById(containerId);
        int count = cardView.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = cardView.getChildAt(i);
            if (v instanceof ProgressBar) {
                ProgressBar progressBar = (ProgressBar) v;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                return;
            }
        }

    }

    @Override
    public void hideProgressBar(Integer containerId) {
        CardView cardView = (CardView) findViewById(containerId);
        int count = cardView.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = cardView.getChildAt(i);
            if (v instanceof ProgressBar) {
                ProgressBar progressBar = (ProgressBar) v;
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.GONE);
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case HelperKey.ACTIVITY_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    isFromCameraActivity = true;
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFromCameraActivity) {
            getPresenter().setBitmapCaptured();
            isFromCameraActivity = false;
        }
    }
}
