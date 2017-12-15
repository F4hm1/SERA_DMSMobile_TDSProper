package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.CustomListener.ClickListener;
import com.serasiautoraya.tdsproper.CustomListener.RecyclerTouchListener;
import com.serasiautoraya.tdsproper.util.DividerRecycleViewDecoration;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class PlanOrderFragment extends TiFragment<PlanOrderPresenter, PlanOrderView> implements PlanOrderView {

    @BindView(R.id.recycler_plan_orders)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    private SimpleAdapterView mSimpleAdapterView;
    private ProgressDialog mProgressDialog;
    private Activity mParentActivity = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_plan_orders, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mParentActivity = (Activity) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null){
            mParentActivity = getActivity();
        }
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_order);
        mEmptyInfoView.setText("Tidak terdapat order terencana yang belum aktif");
        if (mParentActivity != null) {
            this.initializeRecylerView();
            this.initializeRecylerListener();
            getPresenter().loadOrdersdata();
        }
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            if (mParentActivity != null) {
                mProgressDialog = ProgressDialog.show(mParentActivity, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
            }
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        if (mParentActivity != null) {
            Toast.makeText(mParentActivity, text, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        if (mParentActivity != null) {
            HelperUtil.showSimpleAlertDialogCustomTitle(message, mParentActivity, Title);
        }
    }

    @Override
    public void refreshRecyclerView() {
        mSimpleAdapterView.refresh();
    }

    @Override
    public void changeActivityAction(String key, String value, Class targetActivity) {
        if (mParentActivity != null) {
            Intent intent = new Intent(mParentActivity, targetActivity);
            intent.putExtra(key, value);
            startActivity(intent);
        }
    }

    @Override
    public void showAcknowledgeDialog(String ordercode, final Integer assignmentId, String[] destination, String origin, final String etd, final String eta, String customer) {
        final String fOrderCode = ordercode;
        final Integer fAssignmentId = assignmentId;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_order_acknowledge, null))
                .setPositiveButton("Diterima", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getPresenter().onAcknowledgeOrder(fOrderCode, assignmentId, etd, eta);
                    }
                });
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        TextView tvOrderCode = (TextView) dialog.findViewById(R.id.acknowledge_dialog_ordercode);
        tvOrderCode.setText(ordercode);

        TextView tvDestination = (TextView) dialog.findViewById(R.id.acknowledge_dialog_destination);
        tvDestination.setText(destination[0]);

        TableLayout tlDestination = (TableLayout) dialog.findViewById(R.id.acknowledge_tl_destination);
        this.generateDestination(destination, tlDestination);

        TextView tvOrigin= (TextView) dialog.findViewById(R.id.acknowledge_dialog_origin);
        tvOrigin.setText(origin);

        TextView tvEtd= (TextView) dialog.findViewById(R.id.acknowledge_dialog_etd);
        tvEtd.setText(etd);

        TextView tvEta= (TextView) dialog.findViewById(R.id.acknowledge_dialog_eta);
        tvEta.setText(eta);

        TextView tvCustomer = (TextView) dialog.findViewById(R.id.acknowledge_dialog_customer);
        tvCustomer.setText(customer);
    }

    @Override
    public void refreshAllData() {
        AssignedView assignedView = (AssignedView) getParentFragment();
//        assignedView.initialize();
        assignedView.refreshAssignedList();
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
    public void setTextEmptyInfoStatus(boolean success) {
        if(success){
            mEmptyInfoView.setIcon(R.drawable.ic_empty_order);
            mEmptyInfoView.setText("Tidak terdapat order terencana yang belum aktif");
        }else{
            mEmptyInfoView.setIcon(R.drawable.ic_close_grey);
            mEmptyInfoView.setText("Gagal mengambil daftar order, silahkan tekan tombol \"ulangi\" dibawah untuk mencoba kembali");
        }
    }

    @NonNull
    @Override
    public PlanOrderPresenter providePresenter() {
        return new PlanOrderPresenter(new RestConnection(getContext()));
    }

    private void initializeRecylerView(){
        AssignedOrderAdapter simpleListAdapter = new AssignedOrderAdapter();
        mSimpleAdapterView = simpleListAdapter;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mParentActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerRecycleViewDecoration(mParentActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(simpleListAdapter);
        getPresenter().setAdapter(simpleListAdapter);
    }

    private void initializeRecylerListener(){
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(mParentActivity, mRecyclerView, new ClickListener() {
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
    public void generateDestination(String[] arrDestination, TableLayout tableLayout) {
        for (int i = 0; i < arrDestination.length; i++) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TableRow rowView;
            if(i == (arrDestination.length - 1)){
                rowView = (TableRow)inflater.inflate(R.layout.row_destination_activity_borderless, null);
            }else{
                rowView = (TableRow)inflater.inflate(R.layout.row_destination_activity, null);
            }
            TextView tv = (TextView) rowView.findViewById(R.id.destactivity_text);
            tv.setText(arrDestination[i]);
            tableLayout.addView(rowView);
        }
    }

}
