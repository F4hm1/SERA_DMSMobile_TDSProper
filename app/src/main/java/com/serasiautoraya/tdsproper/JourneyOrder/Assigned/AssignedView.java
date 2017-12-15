package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public interface AssignedView extends BaseViewInterface{

    void initializeTabs(boolean isAnyOrderActive, boolean isUpdateLocationActive);

    void onRefreshClicked(View view);

    void refreshAssignedList();

}
