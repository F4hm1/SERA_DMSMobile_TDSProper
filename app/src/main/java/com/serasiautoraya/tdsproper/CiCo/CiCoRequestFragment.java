package com.serasiautoraya.tdsproper.CiCo;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.CustomDialog.DatePickerToEditTextDialog;
import com.serasiautoraya.tdsproper.CustomDialog.TimePickerToEditTextDialog;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoRequestFragment extends TiFragment<CiCoRequestPresenter, CiCoRequestView> implements CiCoRequestView {

    @BindView(R.id.spinner_cico_transaction_type) Spinner mSpinnerTransactionType;
    @BindView(R.id.spinner_cico_reason_choice) Spinner mSpinnerReason;
    @BindView(R.id.edittext_cico_date) EditText mEtDate;
    @BindView(R.id.edittext_cico_time) EditText mEtTime;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialog;
    private TimePickerToEditTextDialog mTimePickerToEditTextDialog;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment_request_cico, container, false);
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
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cico),getResources().getString(R.string.prog_msg_wait),true,false);
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

    @Override
    public boolean getValidationForm() {
        boolean result = true;
        String errText = "";
        View focusView = null;

        if(mSpinnerReason.getSelectedItem().toString().equalsIgnoreCase("Pilih")){
            focusView = mSpinnerReason;
            errText = getResources().getString(R.string.err_msg_empty_cico_reason);
            showToast(errText);
            result = false;
        }

        if(mSpinnerTransactionType.getSelectedItem().toString().equalsIgnoreCase("Pilih")){
            focusView = mSpinnerTransactionType;
            errText = getResources().getString(R.string.err_msg_empty_cico_transactiontype);
            showToast(errText);
            result = false;
        }

        if(TextUtils.isEmpty(mEtDate.getText().toString())){
            focusView = mEtDate;
            errText = getResources().getString(R.string.err_msg_empty_cico_date);
            showToast(errText);
            result = false;
        }else if(!mDatePickerToEditTextDialog.isInMaxRequest() || !mDatePickerToEditTextDialog.isBeforeToday()){
            focusView = mEtDate;
            errText = getResources().getString(R.string.err_msg_wrong_cico_date);
            showToast(errText);
            result = false;
        }

        if(TextUtils.isEmpty(mEtTime.getText().toString())){
            focusView = mEtTime;
            errText = getResources().getString(R.string.err_msg_empty_cico_time);
            showToast(errText);
            result = false;
        }else if(mDatePickerToEditTextDialog.isToday() && !mTimePickerToEditTextDialog.isBeforeCurrentTime()){
            focusView = mEtTime;
            errText = getResources().getString(R.string.err_msg_wrong_cico_time);
            showToast(errText);
            result = false;
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }

    @NonNull
    @Override
    public CiCoRequestPresenter providePresenter() {
        return new CiCoRequestPresenter(new RestConnection(getContext()));
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan pengajuan "+
                "<b>"+mSpinnerTransactionType.getSelectedItem().toString()+"</b>"+" pada "+
                "<b>"+ mEtDate.getText().toString()+", pukul "+ mEtTime.getText()+"</b>"+"?");
        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.btn_cico_submit)
    public void onSubmitClicked(View view) {
        if(getValidationForm()){
            String requestCicoCode = HelperUtil.getValueStringArrayXML(
                    getResources().getStringArray(R.array.cico_tipe_array),
                    getResources().getStringArray(R.array.cico_tipe_array_val),
                    mSpinnerTransactionType.getSelectedItem().toString());
            getPresenter().onSubmitClicked(
                    mDatePickerToEditTextDialog.getDateServerFormat(),
                    mEtTime.getText().toString(),
                    mSpinnerReason.getSelectedItem().toString(),
                    requestCicoCode);
        }
    }

    private void initializeSpinnersContent(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.cico_tipe_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTransactionType.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterReason = ArrayAdapter.createFromResource(this.getContext(),
                R.array.cico_reason_choice, android.R.layout.simple_spinner_item);
        adapterReason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerReason.setAdapter(adapterReason);
    }

    private void initializePickerDialog(){
        mDatePickerToEditTextDialog = new DatePickerToEditTextDialog(mEtDate, getContext(), true, false);
        mTimePickerToEditTextDialog = new TimePickerToEditTextDialog(mEtTime, getContext());
    }

}
