package com.serasiautoraya.tdsproper.OLCTripByOrder;

import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

/**
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public interface OLCTripByOrderView extends BaseViewInterface, FormViewInterface{

    void showConfirmationDialog(String orderCode);

    void onSubmitClicked(View view);

    void changeActivityAction(String[] key, String[] value, Class targetActivity);

}
