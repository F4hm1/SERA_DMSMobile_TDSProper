package com.serasiautoraya.tdsproper.OLCTrip;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.Dashboard.DashboardActivity;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.CustomDialog.DatePickerToEditTextDialog;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public class OLCTripFragment extends TiFragment<OLCTripPresenter, OLCTripView> implements OLCTripView {

    @BindView(R.id.olctrip_spinner_olc)
    Spinner mSpinnerOLC;
    @BindView(R.id.olctrip_edittext_date)
    EditText mEtDate;
    @BindView(R.id.olctrip_edittext_tripamount)
    EditText mEtTripAmount;
    @BindView(R.id.olctrip_edittext_reason)
    EditText mEtReason;
    @BindView(R.id.olctrip_btn_submit)
    Button mButtonSubmit;

    private DatePickerToEditTextDialog mDatePickerToEditTextDialog;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_olctrip_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        this.initializeSpinnersContent();
        this.initializePickerDialog();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_olctrip),getResources().getString(R.string.prog_msg_wait),true,false);
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
    public OLCTripPresenter providePresenter() {
        return new OLCTripPresenter(new RestConnection(getContext()));
    }

    @Override
    public boolean getValidationForm() {
        boolean result = true;
        String errText = "";
        View focusView = null;

        if(TextUtils.isEmpty(mEtDate.getText().toString())){
            focusView = mEtDate;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_date);
            showToast(errText);
            result = false;
        }else if(!mDatePickerToEditTextDialog.isInMaxRequest() || !mDatePickerToEditTextDialog.isBeforeToday()){
            focusView = mEtDate;
            errText = getResources().getString(R.string.err_msg_wrong_olctrip_date);
            showToast(errText);
            result = false;
        }

        if(TextUtils.isEmpty(mEtTripAmount.getText().toString())){
            focusView = mEtReason;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_trip);
            mEtReason.setError(errText);
            result = false;
        }

        if(mSpinnerOLC.getSelectedItem().toString().equalsIgnoreCase("Pilih")){
            focusView = mSpinnerOLC;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_olc);
            showToast(errText);
            result = false;
        }

        if(TextUtils.isEmpty(mEtReason.getText().toString())){
            focusView = mEtReason;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_keterangan);
            mEtReason.setError(errText);
            result = false;
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah Anda yakin akan melakukan pengajuan "+
                "<b>"+"OLC/Trip"+"</b>"+" pada "+
                "<b>"+ mEtDate.getText().toString()+"</b>"+"?");
        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.olctrip_btn_submit)
    public void onSubmitClicked(View view) {
        if(getValidationForm()){
            String isOLC = HelperUtil.getValueStringArrayXML(
                    getResources().getStringArray(R.array.olctrip_olc_array),
                    getResources().getStringArray(R.array.olctrip_olc_array_val),
                    mSpinnerOLC.getSelectedItem().toString());
            getPresenter().onSubmitClicked(
                    mDatePickerToEditTextDialog.getDateServerFormat(),
                    isOLC,
                    mEtTripAmount.getText().toString(),
                    mEtReason.getText().toString());
        }
    }

    @Override
    public void changeFragment() {
        HelperBridge.sTempFragmentTarget = R.id.nav_active_order;
        ((DashboardActivity)getActivity()).changeFragment(((DashboardActivity) getActivity()).getActiveFragment(R.id.nav_active_order));
    }

    private void initializeSpinnersContent(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.olctrip_olc_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOLC.setAdapter(adapter);
    }

    private void initializePickerDialog(){
        mDatePickerToEditTextDialog = new DatePickerToEditTextDialog(mEtDate, getContext(), true, false);
    }
}
