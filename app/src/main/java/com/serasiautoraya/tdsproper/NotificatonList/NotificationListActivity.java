package com.serasiautoraya.tdsproper.NotificatonList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.serasiautoraya.tdsproper.BaseAdapter.CustomPopUpItemClickListener;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.SQLIte.DBHelper;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.DividerRecycleViewDecoration;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 11/04/2017.
 */

public class NotificationListActivity extends TiActivity<NotificationListPresenter, NotificationListView> implements NotificationListView{

    @BindView(R.id.recycler_notification_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    private SimpleAdapterView mSimpleAdapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notification_list);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }


    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_notification);
        mEmptyInfoView.setText("Tidak terdapat riwayat notifikasi");
        this.initializeActionBar();
        this.initializeRecylerView();
        getPresenter().loadNotificationListData();
    }

    @Override
    public void toggleLoading(boolean isLoading) {

    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public NotificationListPresenter providePresenter() {
        return new NotificationListPresenter(DBHelper.getInstance(NotificationListActivity.this));
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Riwayat Notifikasi");

    }

    private void initializeRecylerView() {
        NotificationListAdapter simpleListAdapter = new NotificationListAdapter(new CustomPopUpItemClickListener<NotificationResponseModel>() {
            @Override
            public boolean startAction(NotificationResponseModel model, int menuId) {
                switch (menuId) {
                    case R.id.menu_popup_delete_notification:
                        getPresenter().onDeleteClicked(model);
                        return true;
                    default:
                }
                return false;
            }
        });

        mSimpleAdapterView = simpleListAdapter;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NotificationListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerRecycleViewDecoration(NotificationListActivity.this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(simpleListAdapter);
        getPresenter().setAdapter(simpleListAdapter);
    }

    @Override
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void showDeleteConfirmationDialog(String notificationDate) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan <b>menghapus riwayat notifikasi</b>"+
                " pada <b>" + notificationDate + "</b>?");

        HelperUtil.showConfirmationAlertDialog(textMsg, NotificationListActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onDeleteSubmitted();
            }
        });
    }

    @Override
    public void toggleEmptyInfo(boolean show) {
        if(show){
            mEmptyInfoView.setVisibility(View.VISIBLE);
        }else {
            mEmptyInfoView.setVisibility(View.GONE);
        }
    }
}
