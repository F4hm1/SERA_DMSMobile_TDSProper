package com.serasiautoraya.tdsproper.OrderHistory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.CustomDialog.DatePickerToEditTextDialog;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.CustomListener.ClickListener;
import com.serasiautoraya.tdsproper.CustomListener.RecyclerTouchListener;
import com.serasiautoraya.tdsproper.util.DividerRecycleViewDecoration;

import net.grandcentrix.thirtyinch.TiFragment;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by Randi Dwi Nandra on 22/05/2017.
 */

public class OrderHistoryFragment extends TiFragment<OrderHistoryPresenter, OrderHistoryView> implements OrderHistoryView {

    @BindView(R.id.edittext_order_history_startdate)
    EditText mEtDateStart;
    @BindView(R.id.edittext_order_history_enddate)
    EditText mEtDateEnd;

    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;


    @BindView(R.id.recycler_order_history)
    RecyclerView mRecyclerView;

    private DatePickerToEditTextDialog mDatePickerToEditTextDialogStart;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogEnd;
    private SimpleAdapterView mSimpleAdapterView;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_orderhistory);
        mEmptyInfoView.setText("Tidak terdapat riwayat order");
        this.initializePickerDialog();
        this.initializeRecycleView();
        this.initializeRecylerListener();
//        getPresenter().initialOrderHistoryDatePeriod();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initializeOrderHistory();
    }

    private void initializeOrderHistory(){
        getPresenter().initialOrderHistoryDatePeriod();
    }

    private void initializeRecycleView() {
        OrderHistoryListAdapter simpleListAdapter = new OrderHistoryListAdapter();
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
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.progress_msg_loaddata),getResources().getString(R.string.prog_msg_wait),true,false);
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
    public OrderHistoryPresenter providePresenter() {
        return new OrderHistoryPresenter(new RestConnection(getContext()));
    }

    private void initializeRecylerListener(){
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                getPresenter().onItemClicked(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void changeActivityAction(String key, String value, Class targetActivity) {
        Intent intent = new Intent(getActivity(), targetActivity);
        intent.putExtra(key, value);
        startActivity(intent);
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
    public void setTextStartDate(String startDate) {
        mEtDateStart.setText(startDate);
    }

    @Override
    public void setTextEndDate(String endDate) {
        mEtDateEnd.setText(endDate);
    }

    private void initializePickerDialog(){
        mDatePickerToEditTextDialogStart = new DatePickerToEditTextDialog(mEtDateStart, getContext(), false, true);
        mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, true);
    }

    @Override
    @OnTextChanged(value = R.id.edittext_order_history_startdate, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextStartDateChangeAfter(Editable editable) {
        try {
            mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, true);
            mDatePickerToEditTextDialogEnd.setMaxDateHistory(mEtDateStart.getText().toString());
            mDatePickerToEditTextDialogEnd.setMinDateHistory(mEtDateStart.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    @OnTextChanged(value = R.id.edittext_order_history_enddate, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextEndDateChangeAfter(Editable editable) {
        if (!mEtDateEnd.getText().toString().equalsIgnoreCase("")) {
            getPresenter().loadOrderHistoryData(mEtDateStart.getText().toString(), mEtDateEnd.getText().toString());
        }
    }

}
