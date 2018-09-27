package com.serasiautoraya.tdsproper.RequestHistory.Overtime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseAdapter.CustomPopUpItemClickListener;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.RequestHistory.RequestHistoryAdapter;
import com.serasiautoraya.tdsproper.RequestHistory.RequestHistoryResponseModel;
import com.serasiautoraya.tdsproper.RequestHistory.RequestHistoryView;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.DividerRecycleViewDecoration;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class OvertimeRequestHistoryFragment extends TiFragment<OvertimeRequestHistoryPresenter, OvertimeRequestHistoryView>
        implements OvertimeRequestHistoryView {

    @BindView(R.id.recycler_overtime_request_history)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    private SimpleAdapterView mSimpleAdapterView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overtime_request_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_attendance);
        mEmptyInfoView.setText("Tidak terdapat riwayat pengajuan overtime");
        this.initializeRecylerView();
        getPresenter().loadRequestHistoryData();
    }

    @Override
    public void toggleLoading(boolean isLoading) {

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public OvertimeRequestHistoryPresenter providePresenter() {
        return new OvertimeRequestHistoryPresenter(new RestConnection(getContext()));
    }

    private void initializeRecylerView() {
        RequestHistoryAdapter simpleListAdapter = new RequestHistoryAdapter(new CustomPopUpItemClickListener<RequestHistoryResponseModel>() {
            @Override
            public boolean startAction(RequestHistoryResponseModel requestHistoryResponseModel, int menuId) {
                switch (menuId) {
                    case R.id.menu_popup_detail_request:
                        getPresenter().onDetailClicked(requestHistoryResponseModel);
                        return true;
                    case R.id.menu_popup_cancel_request:
                        getPresenter().onCancelClicked(requestHistoryResponseModel);
                        return true;
                    default:
                }
                return false;
            }
        });

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
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void showCancelConfirmationDialog(String requestDate) {
                                /*
        * TODO Change format date to user format
        * */
        CharSequence textMsg = Html.fromHtml("Apakah Anda yakin akan <b>membatalkan pengajuan Overtime</b>" +
                " pada <b>" + HelperUtil.getUserFormDate(requestDate) + "</b>?");

        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onCancelationSubmitted();
            }
        });
    }

    @Override
    public void refreshAllData() {
        RequestHistoryView requestHistoryView = (RequestHistoryView) getParentFragment();
        requestHistoryView.initialize();
    }

    @Override
    public void toggleEmptyInfo(boolean show) {
        if (show) {
            mEmptyInfoView.setVisibility(View.VISIBLE);
        } else {
            mEmptyInfoView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showDetailDialog(String transType, String dateTimeStart, String dateTimeEnd, String overtimeType, String requestDate, String requestStatus, String approvalBy) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_detail_overtimehistory, null))
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

        TextView tvTransType = (TextView) dialog.findViewById(R.id.history_detail_transtype);
        tvTransType.setText(transType);

        TextView tvDateTimeStart = (TextView) dialog.findViewById(R.id.history_detail_overtimestart);
        tvDateTimeStart.setText(dateTimeStart);

        TextView tvDateTimeEnd = (TextView) dialog.findViewById(R.id.history_detail_overtimeend);
        tvDateTimeEnd.setText(dateTimeEnd);

        TextView tvOvertimeType = (TextView) dialog.findViewById(R.id.history_detail_overtimetype);
        tvOvertimeType.setText(overtimeType);

        TextView tvRequestDate = (TextView) dialog.findViewById(R.id.history_detail_requestdate);
        tvRequestDate.setText(requestDate);

        TextView tvRequestStatus = (TextView) dialog.findViewById(R.id.history_detail_requeststatus);
        tvRequestStatus.setText(requestStatus);

        TextView tvApprovalBy = (TextView) dialog.findViewById(R.id.history_detail_approvalby);
        tvApprovalBy.setText(approvalBy);
    }


}
