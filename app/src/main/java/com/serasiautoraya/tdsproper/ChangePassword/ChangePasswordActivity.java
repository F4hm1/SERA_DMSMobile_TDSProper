package com.serasiautoraya.tdsproper.ChangePassword;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public class ChangePasswordActivity extends TiActivity<ChangePasswordPresenter, ChangePasswordView> implements ChangePasswordView {

    private ProgressDialog mProgressDialog;
    @BindView(R.id.edittext_cp_old) EditText mEtPasswordOld;
    @BindView(R.id.edittext_cp_pass) EditText mEtPasswordPass;
    @BindView(R.id.edittext_cp_passconf) EditText mEtPasswordPassConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

    @Override
    public void initialize() {
        this.initializeActionBar();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(ChangePasswordActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, ChangePasswordActivity.this, Title);
    }

    @NonNull
    @Override
    public ChangePasswordPresenter providePresenter() {
        return new ChangePasswordPresenter(new SharedPrefsModel(ChangePasswordActivity.this), new RestConnection(ChangePasswordActivity.this));
    }

    private void initializeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Ganti Kata Sandi");
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah Anda yakin akan "+
                "<b>"+" mengganti kata sandi"+"</b>"+"?");

        HelperUtil.showConfirmationAlertDialog(textMsg, ChangePasswordActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.btn_cp_submit)
    public void onSubmitClicked(View view) {
        /*
        * The validation of this request method called in presenter, because need data from model (shared preferences)
        * */
        getPresenter().onSubmitClicked(mEtPasswordPass.getText().toString());
    }

    @Override
    public boolean getValidationForm(String savedPassword) {
        boolean result = true;
        View focusView = null;

        if(TextUtils.isEmpty(mEtPasswordPassConf.getText().toString())){
            focusView = mEtPasswordPassConf;
            mEtPasswordPassConf.setError(getResources().getString(R.string.err_msg_empty_cp_confpass));
            result = false;
        }

        if(TextUtils.isEmpty(mEtPasswordPass.getText().toString())){
            focusView = mEtPasswordPass;
            mEtPasswordPass.setError(getResources().getString(R.string.err_msg_empty_cp_pass));
            result = false;
        }

        if(TextUtils.isEmpty(mEtPasswordOld.getText().toString())){
            focusView = mEtPasswordOld;
            mEtPasswordOld.setError(getResources().getString(R.string.err_msg_empty_cp_oldpass));
            result = false;
        }

        if(result == true){
            if(!mEtPasswordOld.getText().toString().equals(savedPassword)){
                focusView = mEtPasswordOld;
                mEtPasswordOld.setError(getResources().getString(R.string.err_msg_wrong_cp_passlamasalah));
                result = false;
            }

            if(!mEtPasswordPass.getText().toString().equals(mEtPasswordPassConf.getText().toString())){
                focusView = mEtPasswordPassConf;
                mEtPasswordPassConf.setError(getResources().getString(R.string.err_msg_wrong_cp_passbarunotmatch));
                result = false;
            }
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }
}
