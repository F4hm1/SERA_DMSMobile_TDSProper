package com.serasiautoraya.tdsproper.OrderHistory.OrderHistoryDetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 23/05/2017.
 */

public class OrderHistoryDetailActivity extends TiActivity<OrderHistoryDetailPresenter, OrderHistoryDetailView> implements OrderHistoryDetailView {

    @BindView(R.id.order_history_detail_linear)
    LinearLayout mLinParentView;
    private ProgressDialog mProgressDialog;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_history_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initialize() {
        this.initializeActionBar();
        getPresenter().setTempFragmentTarget(R.id.nav_order_history);
        getPresenter().loadActivitiesOrderData();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(OrderHistoryDetailActivity.this, getResources().getString(R.string.prog_msg_cancel_request), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(OrderHistoryDetailActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, OrderHistoryDetailActivity.this, Title);
    }

    @NonNull
    @Override
    public OrderHistoryDetailPresenter providePresenter() {
        return new OrderHistoryDetailPresenter();
    }

    @Override
    public void addActivityData(String activityTitle, String activityCode, String activityType, String timeTarget, String timeBaseline, String timeActual, String locationTargetText) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.single_list_order_history_activity, null);

        TextView tvActivityTitle = (TextView) cardView.findViewById(R.id.order_history_detail_activity_title);
        TextView tvActivityCode = (TextView) cardView.findViewById(R.id.order_history_detail_activity_code);
        TextView tvActivityType = (TextView) cardView.findViewById(R.id.order_history_detail_activity_type);
        TextView tvTimeTarget = (TextView) cardView.findViewById(R.id.order_history_detail_time_target);
//        TextView tvTimeBaseline = (TextView) cardView.findViewById(R.id.order_history_detail_time_baseline);
        TextView tvTimeActual = (TextView) cardView.findViewById(R.id.order_history_detail_time_actual);
        TextView tvLocationTargetText = (TextView) cardView.findViewById(R.id.order_history_detail_location_target);

        tvActivityTitle.setText(activityTitle);
        tvActivityCode.setText(activityCode);
        tvActivityType.setText(activityType);
        tvTimeTarget.setText(timeBaseline);
//        tvTimeBaseline.setText(timeBaseline);
        tvTimeActual.setText(timeActual);
        tvLocationTargetText.setText(locationTargetText);

        mLinParentView.addView(cardView);
    }

    private void initializeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Detail Aktifitas Order");
    }
}
