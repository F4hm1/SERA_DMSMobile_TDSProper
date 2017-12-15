package com.serasiautoraya.tdsproper.JourneyOrder.DocumentCapture;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.PermissionsHelper;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Randi Dwi Nandra on 18/04/2017.
 */

public class DocumentCaptureActivity extends TiActivity<DocumentCapturePresenter, DocumentCaptureView> implements DocumentCaptureView {

    @BindView(R.id.documents_card_photos)
    CardView mCardDocumentsPhotos;
    @BindView(R.id.documents_card_expenses)
    CardView mCardDocumentsExpenses;
    @BindView(R.id.documents_card_signature)
    CardView mCardDocumentsSignature;
    @BindView(R.id.documents_card_verificationcode)
    CardView mCardDocumentsVerificationCode;
    @BindView(R.id.documents_card_POD)
    CardView mCardDocumentsPOD;


    @BindView(R.id.documents_iv_capture1)
    ImageView mEtDocumentsIvCapture1;
    @BindView(R.id.documents_iv_capture2)
    ImageView mEtDocumentsIvCapture2;
    @BindView(R.id.documents_iv_capture3)
    ImageView mEtDocumentsIvCapture3;

    @BindView(R.id.documents_iv_signature)
    ImageView mEtDocumentsIvSignature;

    @BindView(R.id.documents_et_fuel)
    EditText mEtDocumentsFuel;
    @BindView(R.id.documents_et_tollparking)
    EditText mEtDocumentsTollparking;
    @BindView(R.id.documents_et_escort)
    EditText mEtDocumentsEscort;
    @BindView(R.id.documents_et_asdp)
    EditText mEtDocumentsAsdp;
    @BindView(R.id.documents_et_portal)
    EditText mEtDocumentsPortal;
    @BindView(R.id.documents_et_bmspsi)
    EditText mEtDocumentsBmspsi;

    @BindView(R.id.documents_et_verificationcode)
    EditText mEtDocumentsVerificationCode;


    @BindView(R.id.documents_iv_capPOD1)
    ImageView mEtDocumentsIvCapturePOD1;
    @BindView(R.id.documents_iv_capPOD2)
    ImageView mEtDocumentsIvCapturePOD2;
    @BindView(R.id.documents_iv_capPOD3)
    ImageView mEtDocumentsIvCapturePOD3;
    @BindView(R.id.documents_tv_podguide)
    TextView mTvDocumentsPODGuide;
    @BindView(R.id.documents_spinner_podreason)
    Spinner mSpinnerDocumentsPODReason;

    @BindView(R.id.documents_btn_submit)
    Button mBtnDocumentsSubmit;


    private String mCurrentVerificationCodeVal = "";
    private boolean isFromCameraActivity = false;
    private boolean isFromSigningActivity = false;
    private ProgressDialog mProgressDialog;
    private boolean isFromGalleryActivity = false;
    private Uri temporaryUriGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_documents_capture);
        ButterKnife.bind(this);
    }

    @Override
    @OnClick({R.id.documents_iv_capture1, R.id.documents_iv_capPOD1})
    public void onClickPhotoCapture1(View view) {
        getPresenter().pickImage(1);
    }

    @Override
    @OnClick({R.id.documents_iv_capture2, R.id.documents_iv_capPOD2})
    public void onClickPhotoCapture2(View view) {
        getPresenter().pickImage(2);
    }

    @Override
    @OnClick({R.id.documents_iv_capture3, R.id.documents_iv_capPOD3})
    public void onClickPhotoCapture3(View view) {
        getPresenter().pickImage(3);
    }

    @Override
    @OnTouch(R.id.documents_iv_signature)
    public boolean onTouchSignatureCapture(ImageView view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
                view.getDrawable().clearColorFilter();
                view.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL: {
                view.getDrawable().clearColorFilter();
                view.invalidate();
                break;
            }
        }
        return false;
    }

    @Override
    @OnClick(R.id.documents_iv_signature)
    public void onClickSignatureCapture(View view) {
        getPresenter().captureSignature();
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
    public void startActivitySignature() {
        Intent intent = new Intent(DocumentCaptureActivity.this, SigningActivity.class);
        startActivityForResult(intent, HelperKey.ACTIVITY_SIGNATURE_CAPTURE);
    }

    @Override
    public void setImageThumbnail(Bitmap bitmap, int targetIvID, boolean isPOD) {
        final Bitmap scaledBitmap = bitmap;
        ImageView imageView;
        switch (targetIvID) {
            case 1:
                imageView = isPOD ? mEtDocumentsIvCapturePOD1 : mEtDocumentsIvCapture1;
                break;
            case 2:
                imageView = isPOD ? mEtDocumentsIvCapturePOD2 : mEtDocumentsIvCapture2;
                break;
            case 3:
                imageView = isPOD ? mEtDocumentsIvCapturePOD3 : mEtDocumentsIvCapture3;
                break;
            default:
                imageView = isPOD ? mEtDocumentsIvCapturePOD1 : mEtDocumentsIvCapture1;
                break;
        }

        imageView.setImageBitmap(scaledBitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPreviewImage(scaledBitmap);
                return true;
            }
        });
    }

    @Override
    public void setImageSign(Bitmap bitmap) {
        Bitmap ttdBitmap = HelperUtil.rotateBitmap(bitmap, 90f);
        mEtDocumentsIvSignature.setImageBitmap(ttdBitmap);
        mEtDocumentsIvSignature.setBackgroundColor(Color.TRANSPARENT);
        mEtDocumentsIvSignature.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mEtDocumentsIvSignature.setAdjustViewBounds(true);
    }

    @Override
    @OnClick(R.id.documents_btn_submit)
    public void onClickSubmit(View view) {
        if (getValidationForm()) {
            getPresenter().onClickSubmit(
                    mEtDocumentsVerificationCode.getText().toString(),
                    mSpinnerDocumentsPODReason.getSelectedItem().toString(),
                    mEtDocumentsFuel.getText().toString(),
                    mEtDocumentsTollparking.getText().toString(),
                    mEtDocumentsEscort.getText().toString(),
                    mEtDocumentsAsdp.getText().toString(),
                    mEtDocumentsPortal.getText().toString(),
                    mEtDocumentsBmspsi.getText().toString(),
                    getResources().getStringArray(R.array.documents_podreason_array),
                    getResources().getStringArray(R.array.documents_podreason_array_val)
            );
        }
    }

    @Override
    public void showConfirmationDialog(String activityName) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin semua dokumen telah terpenuhi, dan akan melakukan proses " +
                "<b>" + activityName + "</b>" + "?");

        HelperUtil.showConfirmationAlertDialog(textMsg, DocumentCaptureActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitActivity();
            }
        });
    }

    @Override
    public void initializeFormContent(boolean isPhoto, boolean isSignature, boolean isVerificationCode, boolean isPOD, String pODGuide) {
        mCardDocumentsPhotos.setVisibility(View.GONE);
        mCardDocumentsExpenses.setVisibility(View.GONE);
        mCardDocumentsSignature.setVisibility(View.GONE);
        mCardDocumentsVerificationCode.setVisibility(View.GONE);
        mCardDocumentsPOD.setVisibility(View.GONE);
        mTvDocumentsPODGuide.setText("");

        if (isPhoto) {
            mCardDocumentsPhotos.setVisibility(View.VISIBLE);
        }

        if (isSignature) {
            mCardDocumentsSignature.setVisibility(View.VISIBLE);
        }

        if (isVerificationCode) {
            mCardDocumentsVerificationCode.setVisibility(View.VISIBLE);
        }

        if (isPOD) {
            mCardDocumentsPOD.setVisibility(View.VISIBLE);
            mTvDocumentsPODGuide.setText(pODGuide);
        }
    }

    @Override
    public void setSubmitText(String text) {
        mBtnDocumentsSubmit.setText(text);
    }

    @Override
    public void showConfirmationSuccess(String message, String title) {
        HelperUtil.showSimpleAlertDialogCustomTitleAction(message, DocumentCaptureActivity.this, title,
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
    public Context returnContext() {
        return this;
    }

    @Override
    public void initialize() {
//        mEtDocumentsFuel.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsFuel));
//        mEtDocumentsTollparking.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsTollparking));
//        mEtDocumentsEscort.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsEscort));
//        mEtDocumentsAsdp.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsAsdp));
//        mEtDocumentsPortal.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsPortal));
//        mEtDocumentsBmspsi.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsBmspsi));
        mEtDocumentsVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = mEtDocumentsVerificationCode.getText().toString();

                if (!s.toString().equals(mCurrentVerificationCodeVal)) {
                    String separator = "-";
                    mEtDocumentsVerificationCode.removeTextChangedListener(this);
                    InputFilter[] filters = editable.getFilters();
                    editable.setFilters(new InputFilter[]{});

                    String cleanString = editable.toString().replace(separator, "");
                    String resultString = "";
                    char[] splittedChar = cleanString.toCharArray();
                    String[] splittedString = new String[splittedChar.length];

                    for (int i = 0; i < splittedString.length; i++) {
                        if (i != (splittedString.length - 1)) {
                            splittedString[i] = splittedChar[i] + separator;
                        } else {
                            splittedString[i] = splittedChar[i] + "";
                        }
                        resultString += splittedString[i];
                    }

                    mCurrentVerificationCodeVal = resultString;
                    mEtDocumentsVerificationCode.setText(resultString);
                    mEtDocumentsVerificationCode.setSelection(resultString.length());
                    editable.setFilters(filters);
                    mEtDocumentsVerificationCode.addTextChangedListener(this);
                }

            }
        });
        this.initializeSpinnersContent();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(DocumentCaptureActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(DocumentCaptureActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, DocumentCaptureActivity.this, Title);
    }

    @NonNull
    @Override
    public DocumentCapturePresenter providePresenter() {
        return new DocumentCapturePresenter(
                new RestConnection(DocumentCaptureActivity.this),
                PermissionsHelper.getInstance(this, this)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFromCameraActivity) {
            getPresenter().setBitmapCaptured();
            isFromCameraActivity = false;
        } else if (isFromSigningActivity) {
            getPresenter().setSignCaptured();
            isFromSigningActivity = false;
        }else if(isFromGalleryActivity){
            String imagePath = this.getPathFromUriGallery(temporaryUriGallery);
            if(!imagePath.equalsIgnoreCase("")){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case HelperKey.ACTIVITY_SIGNATURE_CAPTURE: {
                isFromSigningActivity = true;
                break;
            }
            case HelperKey.ACTIVITY_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    isFromCameraActivity = true;
                }
                break;
            }
            case HelperKey.ACTIVITY_IMAGE_CAPTURE_GALLERY:{
                if (resultCode == RESULT_OK) {
                    isFromGalleryActivity = true;
                    temporaryUriGallery = data.getData();
                }
            }
        }
    }

    private void showPreviewImage(Bitmap bitmap) {
        View view = getLayoutInflater().inflate(R.layout.dialog_image_preview, null);

        ImageView postImage = (ImageView) view.findViewById(R.id.iv_container);
        postImage.setImageBitmap(bitmap);

        Dialog builder = new Dialog(DocumentCaptureActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        builder.setContentView(view);
        builder.show();
    }

    private void initializeSpinnersContent() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                DocumentCaptureActivity.this,
                R.array.documents_podreason_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDocumentsPODReason.setAdapter(adapter);
    }

    private String getPathFromUriGallery(Uri uri){
        try{
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }catch (Exception ex){
            showToast("Gagal mengambil foto dari galeri, silahkan coba kembali");
            return "";
        }
    }

    @Override
    public boolean getValidationForm() {

//        Verify verification code
//        Verify photo input minimum 1
//        Verify signature input


        return true;
    }


}
