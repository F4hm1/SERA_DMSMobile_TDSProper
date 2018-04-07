package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.Dashboard.DashboardActivity;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
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

public class ActiveOrderFragment extends TiFragment<ActiveOrderPresenter, ActiveOrderView> implements ActiveOrderView {

    @BindView(R.id.recycler_active_orders)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    private SimpleAdapterView mSimpleAdapterView;
    private ProgressDialog mProgressDialog;
    private Activity mParentActivity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_active_orders, container, false);
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
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_order);
        mEmptyInfoView.setText("Tidak terdapat order yang aktif");
        this.initializeRecylerView();
        this.initializeRecylerListener();
        getPresenter().loadOrdersdata();
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
            Toast.makeText(mParentActivity , text, Toast.LENGTH_LONG).show();
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
    public void changeActivityAction(String[] key, String[] value, Class targetActivity) {
        if (mParentActivity != null) {
            Intent intent = new Intent(mParentActivity, targetActivity);
            for (int i = 0; i < key.length; i++) {
                intent.putExtra(key[i], value[i]);
            }
            startActivity(intent);
        }
    }

    @Override
    public void toggleEmptyInfo(boolean show) {
        if (show) {
            mEmptyInfoView.setVisibility(View.VISIBLE);
        } else {
            mEmptyInfoView.setVisibility(View.GONE);
        }
        Log.d("EMPTY_INFO", mEmptyInfoView.getVisibility() == View.GONE ? "GONE" : "VISIBLE" + " -- " + mEmptyInfoView.getVisibility());
    }

    @Override
    public void setTextEmptyInfoStatus(boolean success) {
        if(success){
            mEmptyInfoView.setIcon(R.drawable.ic_empty_order);
            mEmptyInfoView.setText("Tidak terdapat order yang aktif");
        }else{
            mEmptyInfoView.setIcon(R.drawable.ic_close_grey);
            mEmptyInfoView.setText("Gagal mengambil daftar order, silahkan tekan tombol \"ulangi\" dibawah untuk mencoba kembali ");
        }
    }

    @Override
    public void changeFragment() {
        ((DashboardActivity)getActivity()).changeFragment(((DashboardActivity) getActivity()).getActiveFragment(R.id.nav_cico_request));
    }


    @NonNull
    @Override
    public ActiveOrderPresenter providePresenter() {
        return new ActiveOrderPresenter(new RestConnection(getContext()));
    }

    private void initializeRecylerView() {
        AssignedOrderAdapter simpleListAdapter = new AssignedOrderAdapter();
        mSimpleAdapterView = simpleListAdapter;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerRecycleViewDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(simpleListAdapter);
        getPresenter().setAdapter(simpleListAdapter);
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
}
