package com.serasiautoraya.tdsproper.Fatigue;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 12/04/2017.
 */

public interface FatigueView extends BaseViewInterface, FormViewInterface{

    void onClickSubmit();

    void showConfirmationDialog();

    void showSuccessDialog(String message, String title);

    void finishActivity();

}
