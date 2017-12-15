package com.serasiautoraya.tdsproper.RequestHistory.CiCo;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public interface CiCoRequestHistoryView extends BaseViewInterface{

    void refreshRecyclerView();

    void showCancelConfirmationDialog(String requestDate);

    void toggleEmptyInfo(boolean show);

    void showDetailDialog(
            String transType,
            String dateCiCo,
            String requestDate,
            String requestStatus,
            String approvalBy
    );
}
