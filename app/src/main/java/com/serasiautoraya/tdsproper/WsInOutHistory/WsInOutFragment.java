package com.serasiautoraya.tdsproper.WsInOutHistory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
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
 * Created by randi on 24/07/2017.
 */

public class WsInOutFragment extends TiFragment<WsInOutPresenter, WsInOutView> implements WsInOutView {

    @BindView(R.id.wsinout_et_startdate)
    EditText mEtDateStart;
    @BindView(R.id.wsinout_et_enddate)
    EditText mEtDateEnd;

    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;


    @BindView(R.id.wsinout_recycler)
    RecyclerView mRecyclerView;

    private DatePickerToEditTextDialog mDatePickerToEditTextDialogStart;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogEnd;
    private SimpleAdapterView mSimpleAdapterView;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wsinout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void changeActivityAction(String key, String value, Class targetActivity) {

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

    @Override
    @OnTextChanged(value = R.id.wsinout_et_enddate, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextEndDateChangeAfter(Editable editable) {
        if (!mEtDateEnd.getText().toString().equalsIgnoreCase("")) {
            getPresenter().loadWsInOutHistoryData(mEtDateStart.getText().toString(), mEtDateEnd.getText().toString());
        }
    }

    @Override
    @OnTextChanged(value = R.id.wsinout_et_startdate, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
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
    public void showDetailDialog(String date, String wsIn, String wsOut, String clockIn, String clockOut, String absence) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_detail_ws, null))
                .setPositiveButton("TUTUP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        TextView tvOrderCode = (TextView) dialog.findViewById(R.id.ws_dialog_date);
        tvOrderCode.setText(date);

        TextView tvDestination = (TextView) dialog.findViewById(R.id.ws_dialog_wsin);
        tvDestination.setText(wsIn);

        TextView tvOrigin= (TextView) dialog.findViewById(R.id.ws_dialog_wsout);
        tvOrigin.setText(wsOut);

        TextView tvEtd= (TextView) dialog.findViewById(R.id.ws_dialog_clockin);
        tvEtd.setText(clockIn);

        TextView tvEta= (TextView) dialog.findViewById(R.id.ws_dialog_clockout);
        tvEta.setText(clockOut);

        TextView tvCustomer = (TextView) dialog.findViewById(R.id.ws_dialog_absence);
        tvCustomer.setText(absence);
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_attendance);
        mEmptyInfoView.setText("Tidak terdapat riwayat jadwal masuk/keluar");
        this.initializePickerDialog();
        this.initializeRecycleView();
        this.initializeRecylerListener();
        getPresenter().initialWsInOutHistoryDatePeriod();
    }

    private void initializeRecylerListener() {
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

    private void initializeRecycleView() {
        WsInOutHistoryListAdapter simpleListAdapter = new WsInOutHistoryListAdapter();
        mSimpleAdapterView = simpleListAdapter;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(simpleListAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        getPresenter().setAdapter(simpleListAdapter);
    }

    private void initializePickerDialog() {
        mDatePickerToEditTextDialogStart = new DatePickerToEditTextDialog(mEtDateStart, getContext(), false, true);
        mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, true);
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
    public WsInOutPresenter providePresenter() {
        return new WsInOutPresenter(new RestConnection(getContext()));
    }
}
