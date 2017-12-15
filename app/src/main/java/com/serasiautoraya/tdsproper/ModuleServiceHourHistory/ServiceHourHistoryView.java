package com.serasiautoraya.tdsproper.ModuleServiceHourHistory;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

/**
 * Created by Randi Dwi Nandra on 11/06/2017.
 */

public interface ServiceHourHistoryView  extends BaseViewInterface {

    void refreshRecyclerView();

    @DistinctUntilChanged
    void toggleEmptyInfo(boolean show);
}
