package com.serasiautoraya.tdsproper.OLCTrip;

import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public interface OLCTripView extends BaseViewInterface, FormViewInterface{

    void showConfirmationDialog();

    void onSubmitClicked(View view);

    void changeFragment();

}
