package com.serasiautoraya.tdsproper.Overtime;

import android.content.Context;
import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import java.util.ArrayList;

/**
 * Created by Randi Dwi Nandra on 03/06/2017.
 */

public interface OvertimeRequestView extends BaseViewInterface, FormViewInterface{

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoadingInitialLoad(boolean isLoading);

    void showConfirmationDialog();

    void onSubmitClicked(View view);

    void onPickDateTimeStarttimeClicked(View view);

    void onPickDateTimeEndtimeClicked(View view);

    void initializeOvertimeDates(ArrayList<OvertimeAvailableResponseModel> arrayList);

    void initializeOvertimeTypes(ArrayList<OvertimeAvailableTypeAdapter> arrayList);

    void initializeOvertimeTimes(String startTime, String endTime);

    void setNoOvertime();

}
