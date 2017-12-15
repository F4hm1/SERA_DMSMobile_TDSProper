package com.serasiautoraya.tdsproper.Login;

import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 20/03/2017.
 */

public interface LoginView extends BaseViewInterface, FormViewInterface {

    void onSubmitButtonClicked(View view);

    void changeActivity(Class targetActivity);

    void startInitializeLocation();

    void setCachedFormLogin(String username, String password);
}
