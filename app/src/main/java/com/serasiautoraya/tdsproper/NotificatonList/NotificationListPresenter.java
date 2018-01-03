package com.serasiautoraya.tdsproper.NotificatonList;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.SQLIte.DBHelper;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 11/04/2017.
 */

public class NotificationListPresenter extends TiPresenter<NotificationListView> {

    private DBHelper mDbHelper;
    private SimpleAdapterModel mSimpleAdapterModel;
    private NotificationResponseModel mNotificationResponseModel;

    public NotificationListPresenter(DBHelper dbHelper) {
        this.mDbHelper = dbHelper;
    }

    @Override
    protected void onAttachView(@NonNull final NotificationListView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public void loadNotificationListData() {
        Cursor res = mDbHelper.runRawQuery("select * from " + DBHelper.NOTIFICATION_TABLE_NAME + " ORDER BY " + DBHelper.NOTIFICATION_COLUMN_ID + " DESC");
        List<NotificationResponseModel> notificationResponseModels = new ArrayList<>();
        while (res.isAfterLast() == false) {
            NotificationResponseModel visitorModel = new NotificationResponseModel(
                    res.getString(res.getColumnIndex(DBHelper.NOTIFICATION_COLUMN_ID)),
                    res.getString(res.getColumnIndex(DBHelper.NOTIFICATION_COLUMN_TITLE)),
                    res.getString(res.getColumnIndex(DBHelper.NOTIFICATION_COLUMN_DATE)),
                    res.getString(res.getColumnIndex(DBHelper.NOTIFICATION_COLUMN_MESSAGE))
            );
            notificationResponseModels.add(visitorModel);
            res.moveToNext();
        }

        if (!notificationResponseModels.isEmpty()) {
            getView().toggleEmptyInfo(false);
        } else {
            getView().toggleEmptyInfo(true);
        }
        mSimpleAdapterModel.setItemList(notificationResponseModels);
        getView().refreshRecyclerView();

    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void onDeleteClicked(NotificationResponseModel notificationResponseModel) {
        this.mNotificationResponseModel = notificationResponseModel;
        getView().showDeleteConfirmationDialog(notificationResponseModel.getDate());
    }

    public void onDeleteSubmitted() {
        mDbHelper.runRawQuery("delete from " + DBHelper.NOTIFICATION_TABLE_NAME
                + " where " + DBHelper.NOTIFICATION_COLUMN_ID + "=" + mNotificationResponseModel.getNotificationId());
        this.loadNotificationListData();
    }
}
