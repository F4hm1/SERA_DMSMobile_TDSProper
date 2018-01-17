package com.serasiautoraya.tdsproper.CiCo;

import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public interface CiCoRequestView extends BaseViewInterface, FormViewInterface {

    void showConfirmationDialog();

    void onSubmitClicked(View view);

    void initializePickerDialog(int dayMinRequest);

}
