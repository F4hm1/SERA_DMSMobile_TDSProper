package com.serasiautoraya.tdsproper.CiCo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoRealtimeFragment extends TiFragment<CiCoRealtimePresenter, CiCoRealtimeView> implements CiCoRealtimeView {

    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_realtime_cico, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_loadingjam), getResources().getString(R.string.prog_msg_wait), true, false);
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
    public CiCoRealtimePresenter providePresenter() {
        return new CiCoRealtimePresenter(new RestConnection(getContext()));
    }

    @Override
    @OnClick(R.id.btn_rel_clockin_req)
    public void onClickClockIn() {
        getPresenter().onClockIn();
    }

    @Override
    @OnClick(R.id.btn_rel_clockout_req)
    public void onClickClockOut() {
        getPresenter().onClockOut();
    }

    @Override
    public void showConfirmationDialog(String type, String timeZone, String dateMessage, String monthMessage, String yearMessage, String timeMessage) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan "+
                "<b>"+type+"</b>"+" pada "+
                "<b>"+dateMessage + " " + HelperUtil.getMonthName(monthMessage, getContext()) + " " + yearMessage+"</b>"+" pukul "+
                "<b>"+timeMessage+" "+timeZone+"</b>"+"?");
        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onSubmitCiCo();
            }
        });
    }

    @Override
    public void changeActivity(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    @Override
    public void toggleLoadingCiCo(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cico), getResources().getString(R.string.prog_msg_wait), true, false);
        }else{
            mProgressDialog.dismiss();
        }
    }
}
