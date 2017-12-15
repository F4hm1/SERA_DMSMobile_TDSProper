package com.serasiautoraya.tdsproper.BaseInterface;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public interface BaseViewInterface extends TiView {

    void initialize();

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoading(boolean isLoading);

    void showToast(String text);

    void showStandardDialog(String message, String Title);
}
