package com.serasiautoraya.tdsproper.RequestHistory.Overtime;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface OvertimeRequestHistoryView extends BaseViewInterface{

    void refreshRecyclerView();

    void showCancelConfirmationDialog(String requestDate);

    void refreshAllData();

    void toggleEmptyInfo(boolean show);

    void showDetailDialog(
            String transType,
            String dateTimeStart,
            String dateTimeEnd,
            String overtimeType,
            String requestDate,
            String requestStatus,
            String approvalBy
    );
}
