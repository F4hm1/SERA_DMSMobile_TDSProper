package com.serasiautoraya.tdsproper.CiCo;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public interface CiCoRealtimeView extends BaseViewInterface{
    void onClickClockIn();

    void onClickClockOut();

    void showConfirmationDialog(String type, String timeZone, String dateMessage, String monthMessage, String yearMessage, String timeMessage);

    void changeActivity(Class cls);

    @DistinctUntilChanged
    @CallOnMainThread
    void toggleLoadingCiCo(boolean isLoading);
}
