package com.serasiautoraya.tdsproper.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Helper.PermissionsHelper;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.FCMMessageService;
import com.serasiautoraya.tdsproper.util.LocationServiceUtil;
import com.serasiautoraya.tdsproper.util.NetworkChangeReceiver;

import net.grandcentrix.thirtyinch.TiActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 20/03/2017.
 */

public class LoginActivity extends TiActivity<LoginPresenter, LoginView> implements LoginView {

    private ProgressDialog mProgressDialog;
    @BindView(R.id.input_password) EditText mEtPassword;
    @BindView(R.id.input_username) EditText mEtUsername;

    NetworkChangeReceiver networkChangeReceiver;
    Snackbar snackbarNetworkChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        snackbarNetworkChange = Snackbar.make(getWindow().getDecorView(), "Tidak terdapat koneksi internet", Snackbar.LENGTH_INDEFINITE);
        networkChangeReceiver = new NetworkChangeReceiver() {
            @Override
            protected void onDisconnect() {
                snackbarNetworkChange.show();
            }

            @Override
            protected void onConnect() {
                snackbarNetworkChange.dismiss();
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @NonNull
    @Override
    public LoginPresenter providePresenter() {
        return new LoginPresenter(
                new SharedPrefsModel(LoginActivity.this),
                new RestConnection(LoginActivity.this),
                PermissionsHelper.getInstance(this, this),
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)
        );
    }

    @Override
    public void initialize() {
        this.setLocaleDefault();
        this.startFCMServices();
        getPresenter().initializePermissions();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, LoginActivity.this, Title);
    }


    private void setLocaleDefault(){
        Locale locale = new Locale("id");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void startFCMServices() {
        startService(new Intent(this, FCMMessageService.class));
    }

    @Override
    public void startInitializeLocation() {
        LocationServiceUtil.getLocationManager(LoginActivity.this);
    }

    @Override
    public void setCachedFormLogin(String username, String password) {
        mEtUsername.setText(username);
        mEtPassword.setText(password);
    }


    @Override
    public boolean getValidationForm() {
        mEtUsername.setError(null);
        mEtPassword.setError(null);

        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            focusView = mEtPassword;
            mEtPassword.setError(getResources().getString(R.string.err_msg_empty_password));
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            focusView = mEtUsername;
            mEtUsername.setError(getResources().getString(R.string.err_msg_empty_username));
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            return true;
        }
        return false;
    }


    @Override
    @OnClick(R.id.btn_login)
    public void onSubmitButtonClicked(View view) {
        if(getValidationForm()){
            getPresenter().onLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
        }
    }

    @Override
    public void changeActivity(Class targetActivity) {
        Intent intent = new Intent(LoginActivity.this, targetActivity);
        startActivity(intent);
        this.finish();
    }
}
