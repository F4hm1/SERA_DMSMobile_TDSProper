package com.serasiautoraya.tdsproper.NotificatonList;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 11/04/2017.
 */

public interface NotificationListView extends BaseViewInterface {

    void refreshRecyclerView();

    void showDeleteConfirmationDialog(String notificationDate);

    void toggleEmptyInfo(boolean show);
}
