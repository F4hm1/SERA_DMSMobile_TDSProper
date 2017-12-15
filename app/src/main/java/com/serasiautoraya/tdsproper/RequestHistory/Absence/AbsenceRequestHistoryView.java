package com.serasiautoraya.tdsproper.RequestHistory.Absence;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface AbsenceRequestHistoryView extends BaseViewInterface {

    void refreshRecyclerView();

    void showCancelConfirmationDialog(String requestDate);

    void refreshAllData();

    void toggleEmptyInfo(boolean show);

    void showDetailDialog(
            String transType,
            String dateStart,
            String dateEnd,
            String absenceType,
            String requestDate,
            String requestStatus,
            String approvalBy
    );
}
