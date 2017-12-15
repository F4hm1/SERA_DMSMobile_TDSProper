package com.serasiautoraya.tdsproper.Fatigue;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 12/04/2017.
 */

public class FatigueActivity extends TiActivity<FatiguePresenter, FatigueView> implements FatigueView {

    private ProgressDialog mProgressDialog;
    private Dialog mConfirmationDialog;

    @BindView(R.id.rg_fi_quest1) RadioGroup mRgQuestion1;
    @BindView(R.id.rg_fi_quest2) RadioGroup mRgQuestion2;
    @BindView(R.id.rg_fi_quest3) RadioGroup mRgQuestion3;
    @BindView(R.id.rg_fi_quest4) RadioGroup mRgQuestion4;
    @BindView(R.id.rg_fi_quest5) RadioGroup mRgQuestion5;
    @BindView(R.id.btn_fi_submit) Button mButtonSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fatigue_interview);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        showToast("Anda harus menjawab semua pertanyaan fatigue ini terlebih dahulu.");
    }

    @Override
    public void initialize() {
        this.initializeActionBar();
        this.initializeConfirmationDialog();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.progress_msg_fatigue), getResources().getString(R.string.prog_msg_wait), true, false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(FatigueActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
//        HelperUtil.showSimpleAlertDialogCustomTitle(message, FatigueActivity.this, Title);

    }

    @Override
    public boolean getValidationForm() {
        return true;
    }

    @NonNull
    @Override
    public FatiguePresenter providePresenter() {
        return new FatiguePresenter(new RestConnection(FatigueActivity.this), new SharedPrefsModel(FatigueActivity.this));
    }

    private void initializeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Fatigue Interview");
    }

    private void initializeConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FatigueActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_fatigue_confirmation, null))
                .setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getPresenter().onRequestSubmitted();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        mConfirmationDialog = builder.create();
    }

    @Override
    @OnClick(R.id.btn_fi_submit)
    public void onClickSubmit() {
        getPresenter().onSubmitClicked(
                (mRgQuestion1.getCheckedRadioButtonId() != R.id.rb_fi_quest1_tidak)? HelperTransactionCode.FATIGUE_YES_ANSWER_CODE:getResources().getString(R.string.fi_quest_1),
                (mRgQuestion2.getCheckedRadioButtonId() != R.id.rb_fi_quest2_tidak)? HelperTransactionCode.FATIGUE_YES_ANSWER_CODE:getResources().getString(R.string.fi_quest_2),
                (mRgQuestion3.getCheckedRadioButtonId() != R.id.rb_fi_quest3_tidak)? HelperTransactionCode.FATIGUE_YES_ANSWER_CODE:getResources().getString(R.string.fi_quest_3),
                (mRgQuestion4.getCheckedRadioButtonId() != R.id.rb_fi_quest4_tidak)? HelperTransactionCode.FATIGUE_YES_ANSWER_CODE:getResources().getString(R.string.fi_quest_4),
                (mRgQuestion5.getCheckedRadioButtonId() != R.id.rb_fi_quest5_tidak)? HelperTransactionCode.FATIGUE_YES_ANSWER_CODE:getResources().getString(R.string.fi_quest_5)
        );
    }

    @Override
    public void showConfirmationDialog() {
        mConfirmationDialog.show();
        TextView textViewAns1 = (TextView) mConfirmationDialog.findViewById(R.id.dialog_fi_ans1);
        TextView textViewAns2 = (TextView) mConfirmationDialog.findViewById(R.id.dialog_fi_ans2);
        TextView textViewAns3 = (TextView) mConfirmationDialog.findViewById(R.id.dialog_fi_ans3);
        TextView textViewAns4 = (TextView) mConfirmationDialog.findViewById(R.id.dialog_fi_ans4);
        TextView textViewAns5 = (TextView) mConfirmationDialog.findViewById(R.id.dialog_fi_ans5);

        textViewAns1.setText(((RadioButton)findViewById(mRgQuestion1.getCheckedRadioButtonId())).getText().toString());
        textViewAns2.setText(((RadioButton)findViewById(mRgQuestion2.getCheckedRadioButtonId())).getText().toString());
        textViewAns3.setText(((RadioButton)findViewById(mRgQuestion3.getCheckedRadioButtonId())).getText().toString());
        textViewAns4.setText(((RadioButton)findViewById(mRgQuestion4.getCheckedRadioButtonId())).getText().toString());
        textViewAns5.setText(((RadioButton)findViewById(mRgQuestion5.getCheckedRadioButtonId())).getText().toString());
    }

    @Override
    public void showSuccessDialog(String message, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(FatigueActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finishActivity();
            }
        });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

}
