package com.serasiautoraya.tdsproper.JourneyOrder.PodSubmit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.CustomView.SquareImageView;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.PermissionsHelper;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodSubmitActivity extends TiActivity<PodSubmitPresenter, PodSubmitView> implements PodSubmitView {
    @BindView(R.id.pod_card_submit)
    CardView mCardSubmit;

    @BindView(R.id.pod_card_container)
    CardView mCardContainer;

    @BindView(R.id.pod_gv_container)
    GridView mGvContainer;

    @BindView(R.id.pod_btn_submit)
    Button mBtnSubmit;

//    @BindView(R.id.pod_spinner_reason)
//    Spinner mSpinnerPodReason;

    @BindView(R.id.pod_tv_guide)
    TextView mTvPodGuide;

    private ProgressDialog mProgressDialog;
    private PodListAdapter mPodListAdapter;
    private boolean isFromCameraActivity = false;
    private RelativeLayout.LayoutParams mParamBtnSubmitNormal;
    private RelativeLayout.LayoutParams mParamBtnSubmitBottom;
    private boolean isFromGalleryActivity = false;
    private Uri temporaryUriGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_capture);
        ButterKnife.bind(this);
        initializePodAdapter();
        initializeDynamicLayoutParams();
//        initializeSpinnerReason();

    }

//    private void initializeSpinnerReason() {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PodSubmitActivity.this, R.array.documents_podreason_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinnerPodReason.setAdapter(adapter);
//        mSpinnerPodReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                if (position == 0) {
//                    mCardContainer.setVisibility(View.VISIBLE);
//                    mCardSubmit.setLayoutParams(mParamBtnSubmitBottom);
//                } else {
//                    mCardContainer.setVisibility(View.GONE);
//                    mCardSubmit.setLayoutParams(mParamBtnSubmitNormal);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//    }

    private void initializeDynamicLayoutParams() {
        mParamBtnSubmitNormal = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParamBtnSubmitNormal.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        mParamBtnSubmitNormal.addRule(RelativeLayout.BELOW, R.id.pod_card_reason);
        mParamBtnSubmitBottom = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParamBtnSubmitBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

    }

    private void initializePodAdapter() {
        mPodListAdapter = new PodListAdapter(this, new PodItemOnClickListener() {
            @Override
            public void onCapturePhoto(int position, SquareImageView squareImageView) {
                getPresenter().pickImage(position);
            }

            @Override
            public void onCloseThumbnail(int position, ImageButton imageButton) {
                imageButton.setVisibility(View.GONE);
                mPodListAdapter.remove(position);
                if (mPodListAdapter.getCount() < 1) {
                    mPodListAdapter.addItem(new PodItemModel(null));
                }
                mPodListAdapter.notifyDataSetChanged();
            }
        });
        mPodListAdapter.addItem(new PodItemModel(null));
        mGvContainer.setAdapter(mPodListAdapter);
        mPodListAdapter.notifyDataSetChanged();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(PodSubmitActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(PodSubmitActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, PodSubmitActivity.this, Title);
    }

    @NonNull
    @Override
    public PodSubmitPresenter providePresenter() {
        return new PodSubmitPresenter(
                new RestConnection(PodSubmitActivity.this),
                PermissionsHelper.getInstance(this, this)
        );
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
            case HelperKey.ACTIVITY_IMAGE_CAPTURE_GALLERY: {
                if (resultCode == RESULT_OK) {
                    isFromGalleryActivity = true;
                    temporaryUriGallery = data.getData();
                }
            }
        }
    }

    @Override
    public void startActivityCapture(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE);
    }

    @Override
    public void startActivityOpenGallery(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE_GALLERY);
    }

    @Override
    public void setImageThumbnail(Bitmap bitmapScaled, int mCurrentSelectedPosition, boolean isPod, boolean isPreview) {
        if (mPodListAdapter.getItem(mCurrentSelectedPosition).getBitmap() == null) {
            if (mPodListAdapter.getCount() < 10) {
                mPodListAdapter.addItem(new PodItemModel(null));
            }
        }
        mPodListAdapter.getItem(mCurrentSelectedPosition).setBitmap(bitmapScaled);
        mPodListAdapter.getItem(mCurrentSelectedPosition).setCloseButtonAppear(isPreview);
        mPodListAdapter.notifyDataSetChanged();
    }

    @Override
    @OnClick(R.id.pod_btn_submit)
    public void onClickSubmit(View view) {
//        if(mSpinnerPodReason.getSelectedItemPosition() == 0){
        getPresenter().onRequestSubmitted(mPodListAdapter.getPodItemModels());
//        }else{
//            getPresenter().onConfirmActivity();
//        }
    }

    @Override
    public void toggleProgressBar(int position, boolean show) {
        mPodListAdapter.getItem(position).getProgressBar().setIndeterminate(show);
        mPodListAdapter.getItem(position).getProgressBar().setVisibility(show ? View.VISIBLE : View.GONE);
//        Log.d("PONDEX", "PONDEX: " + position + ": " + show);
//        mPodListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFromCameraActivity) {
            getPresenter().setBitmapCaptured();
            isFromCameraActivity = false;
        } else if (isFromGalleryActivity) {
            String imagePath = this.getPathFromUriGallery(temporaryUriGallery);
            if (!imagePath.equalsIgnoreCase("")) {
                if (Build.VERSION.SDK_INT >= 24) {
                    getPresenter().setBitmapCapturedByGallerySDK24(temporaryUriGallery);
                }else {
                    getPresenter().setBitmapCapturedByGallery(imagePath);
                }
            }
            isFromGalleryActivity = false;
        }
    }

    @Override
    public void showConfirmationDialog(String activityName) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin semua dokumen telah terpenuhi dan akan melakukan proses " +
                "<b>" + activityName + "</b>" + "?");

        HelperUtil.showConfirmationAlertDialog(textMsg, PodSubmitActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitActivity("-");
            }
        });
    }

    @Override
    public void setSubmitText(String activityName) {
        mBtnSubmit.setText(activityName);
    }

    @Override
    public void showPhotosRequiredAlert() {
        Snackbar.make(mGvContainer, "Harap mengambil minimal 1 foto POD", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setGuideText(String text) {
        mTvPodGuide.setText(text);
    }

    @Override
    public void showPhotoPickerSourceDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_capture_photopickerselection, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        Button btnCamera = (Button) promptView.findViewById(R.id.capture_dialog_btncamera);
        Button btnGallery = (Button) promptView.findViewById(R.id.capture_dialog_btngallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getPresenter().capturePhoto();
                alertD.dismiss();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getPresenter().openGallery();
                alertD.dismiss();
            }
        });

        alertD.setView(promptView);
        alertD.show();
    }

    @Override
    public void toggleSubmitButton(boolean show) {
        mBtnSubmit.setEnabled(show);
    }

    private String getPathFromUriGallery(Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } catch (Exception ex) {
            showToast("Gagal mengambil foto dari galeri, silahkan coba kembali");
            return "";
        }
    }

    @Override
    public void showConfirmationSuccess(String message, String title) {
        HelperUtil.showSimpleAlertDialogCustomTitleAction(message, PodSubmitActivity.this, title,
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
    public Context returnContext() {
        return this;
    }

}
