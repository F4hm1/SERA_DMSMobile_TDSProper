package com.serasiautoraya.tdsproper.Absence;

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

import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.CustomDialog.DatePickerToEditTextDialog;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class AbsenceRequestFragment extends TiFragment<AbsenceRequestPresenter, AbsenceRequestView>
        implements AbsenceRequestView {

    @BindView(R.id.spinner_absence_tipeabsen) Spinner mSpinnerAbsenceType;
    @BindView(R.id.edittext_absence_datemulai) EditText mEtDateStart;
    @BindView(R.id.edittext_absence_dateberakhir) EditText mEtDateEnd;
    @BindView(R.id.edittext_absence_reason) EditText mEtReason;
    @BindView(R.id.btn_absence_submit) Button mButtonSubmit;

    private DatePickerToEditTextDialog mDatePickerToEditTextDialogStart;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogEnd;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_absence_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        this.initializeSpinnersContent();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_absence),getResources().getString(R.string.prog_msg_wait),true,false);
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
        String absenceType = HelperUtil.getValueStringArrayXML(
                getResources().getStringArray(R.array.absence_tipe_array),
                getResources().getStringArray(R.array.absence_tipe_array_val),
                mSpinnerAbsenceType.getSelectedItem().toString());

        if(TextUtils.isEmpty(mEtReason.getText().toString())){
            focusView = mEtReason;
            mEtReason.setError(getResources().getString(R.string.err_msg_empty_absent_keterangan));
            result = false;
        }

        if(absenceType.equalsIgnoreCase("-1")){
            focusView = mSpinnerAbsenceType;
            errText = getResources().getString(R.string.err_msg_empty_absent_absenttype);
            showToast(errText);
            result = false;
        }

        if(TextUtils.isEmpty(mEtDateEnd.getText().toString())){
            focusView = mEtDateEnd;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_empty_absent_dateberakhir), getContext());
            result = false;
        }else if(!mDatePickerToEditTextDialogEnd.isInMaxRequest()){
            focusView = mEtDateEnd;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_absent_dateberakhir), getContext());
            result = false;
        }

        if(TextUtils.isEmpty(mEtDateStart.getText().toString())){
            focusView = mEtDateStart;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_empty_absent_datemulai), getContext());
            result = false;
        }else if(!mDatePickerToEditTextDialogStart.isInMaxRequest()){
            focusView = mEtDateStart;
            HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_absent_datemulai), getContext());
            result = false;
        }

        if(result == true){
            if(HelperUtil.isDateBefore(mEtDateStart.getText().toString(), mEtDateEnd.getText().toString())){
                focusView = mEtDateStart;
                HelperUtil.showSimpleToast(getResources().getString(R.string.err_msg_wrong_absent_date), getContext());
                result = false;
            }
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan pengajuan ketidakhadiran pada "+
                "<b>"+mEtDateStart.getText().toString()+" - "+mEtDateEnd.getText().toString()+"</b>"+"?");

        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.btn_absence_submit)
    public void onSubmitClicked(View view) {
        if(getValidationForm()){
            String absenceType = HelperUtil.getValueStringArrayXML(
                    getResources().getStringArray(R.array.absence_tipe_array),
                    getResources().getStringArray(R.array.absence_tipe_array_val),
                    mSpinnerAbsenceType.getSelectedItem().toString());
            getPresenter().onSubmitClicked(
                    mDatePickerToEditTextDialogStart.getDateServerFormat(),
                    mDatePickerToEditTextDialogEnd.getDateServerFormat(),
                    mEtReason.getText().toString(),
                    absenceType
            );
        }
    }

    @NonNull
    @Override
    public AbsenceRequestPresenter providePresenter() {
        return new AbsenceRequestPresenter(new RestConnection(getContext()));
    }

    private void initializeSpinnersContent(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.absence_tipe_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAbsenceType.setAdapter(adapter);
    }

    @Override
    public void initializeDatePickerMinRequest(int dayMinRequest) {
        mDatePickerToEditTextDialogStart = new DatePickerToEditTextDialog(mEtDateStart, getContext(), false, false, dayMinRequest);
        mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, false, dayMinRequest);
    }
}
