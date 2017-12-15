package com.serasiautoraya.tdsproper.ModuleServiceHourHistory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.CustomView.DividerRecycleViewDecoration;
import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.Helper.HelperUtil;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 11/06/2017.
 */

public class ServiceHourHistoryFragment extends TiFragment<ServiceHourHistoryPresenter, ServiceHourHistoryView> implements ServiceHourHistoryView {

    /*
    * TODO Change this
    * */
    @BindView(R.id.recycler_absence_request_history)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    private SimpleAdapterView mSimpleAdapterView;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicehour_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void toggleEmptyInfo(boolean show) {
        if(show){
            mEmptyInfoView.setVisibility(View.VISIBLE);
        }else {
            mEmptyInfoView.setVisibility(View.GONE);
        }
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_attendance);
        mEmptyInfoView.setText("Tidak terdapat riwayat service hour");
        this.initializeRecylerView();
        getPresenter().loadRequestHistoryData("","");
    }

    private void initializeRecylerView() {
        ServiceHourHistoryAdapter simpleListAdapter = new ServiceHourHistoryAdapter();
        mSimpleAdapterView = simpleListAdapter;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(simpleListAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        getPresenter().setAdapter(simpleListAdapter);
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cancel_request),getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, getContext(), Title);
    }

    @NonNull
    @Override
    public ServiceHourHistoryPresenter providePresenter() {
        return new ServiceHourHistoryPresenter(new RestConnection(getContext()));
    }
}
