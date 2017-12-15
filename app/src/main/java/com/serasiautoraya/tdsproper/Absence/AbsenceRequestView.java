package com.serasiautoraya.tdsproper.Absence;

import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface AbsenceRequestView extends BaseViewInterface, FormViewInterface{

    void initializeDatePickerMinRequest(int dayMinRequest);

    void showConfirmationDialog();

    void onSubmitClicked(View view);

}
