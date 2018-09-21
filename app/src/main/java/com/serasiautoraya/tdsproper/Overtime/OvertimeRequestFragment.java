package com.serasiautoraya.tdsproper.Overtime;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.TimeWebRestAPI;

import net.grandcentrix.thirtyinch.TiFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import android.icu.text.DateFormat;
//import android.icu.text.SimpleDateFormat;

/**
 * Created by Randi Dwi Nandra on 03/06/2017.
 */

public class OvertimeRequestFragment extends TiFragment<OvertimeRequestPresenter, OvertimeRequestView> implements OvertimeRequestView {

    @BindView(R.id.overtime_spinner_datechoice)
    Spinner mSpinnerDateChoice;
    @BindView(R.id.overtime_spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.overtime_edittext_endtime)
    EditText mEtEndTime;
    @BindView(R.id.overtime_edittext_starttime)
    EditText mEtStartTime;
    @BindView(R.id.overtime_edittext_reason)
    EditText mEtReason;
    @BindView(R.id.textil_layout_overtime_reason)
    TextInputLayout llOvertime;
    @BindView(R.id.overtime_btn_submit)
    Button mButtonSubmit;

    /*@BindView(R.id.overtime_btn_test)
    Button mButtonTest;*/

    @BindView(R.id.overtime_lin_timerange)
    LinearLayout mLinearTimeRange;

    @BindView(R.id.overtime_spinner_type_label)
    TextView mTvLabelType;

    @BindView(R.id.overtime_spinner_datechoice_label)
    TextView mTvLabelDatechoice;

    private ProgressDialog mProgressDialog;

    private ArrayAdapter<OvertimeAvailableResponseModel> mArrayAdapterDatesChoice;
    private ArrayAdapter<OvertimeAvailableTypeAdapter> mArrayAdapterOvertimeTypes;

    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overtime_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_overtime);
        mEmptyInfoView.setText("Anda tidak memiliki lembur untuk diajukan");
        mEmptyInfoView.setVisibility(View.GONE);
        this.initializeSpinners();
        this.hideHideableView();
        getPresenter().initialRequestHistoryData();
    }

    private void hideHideableView() {
        mTvLabelDatechoice.setVisibility(View.GONE);
        mSpinnerDateChoice.setVisibility(View.GONE);
        mTvLabelType.setVisibility(View.GONE);
        mSpinnerType.setVisibility(View.GONE);
        llOvertime.setVisibility(View.GONE);
        mLinearTimeRange.setVisibility(View.GONE);
        mEtReason.setVisibility(View.GONE);
        mButtonSubmit.setVisibility(View.GONE);
    }

    private void initializeSpinners() {
//        String[] tanggalOvertime = {"Tidak terdapat overtime"};
//        mArrayAdapterDatesChoice = new ArrayAdapter<OvertimeAvailableResponseModel>(getContext(), android.R.layout.simple_spinner_item, tanggalOvertime);
//        mArrayAdapterDatesChoice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinnerDateChoice.setAdapter(mArrayAdapterDatesChoice);
//        mSpinnerDateChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                getPresenter().onDateSelected(adapterView.getSelectedItem().toString(), i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        mArrayAdapterDatesChoice = new ArrayAdapter<OvertimeAvailableResponseModel>(getContext(), android.R.layout.simple_spinner_item);
        mArrayAdapterOvertimeTypes = new ArrayAdapter<OvertimeAvailableTypeAdapter>(getContext(), android.R.layout.simple_spinner_item);
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_overtime),getResources().getString(R.string.prog_msg_wait),true,false);
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
        HelperUtil.showSimpleAlertDialogFromOvertime(message, getContext(), Title, new HelperUtil.CallbackListener() {
            @Override
            public void clickOk(String msg) {
                if (msg.equals(HelperBridge.sStatusOvertimeApproved)) setNoOvertime();
            }
        });
    }

    @NonNull
    @Override
    public OvertimeRequestPresenter providePresenter() {
        return new OvertimeRequestPresenter(new RestConnection((this.getContext())));
    }

    @Override
    public boolean getValidationForm() {
        boolean result = true;
        String errText = "";
        View focusView = null;

        if(TextUtils.isEmpty(mEtReason.getText().toString())){
            focusView = mEtReason;
            mEtReason.setError(getResources().getString(R.string.err_msg_empty_overtime_keterangan));
            result = false;
        }

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }

    @Override
    public void toggleLoadingInitialLoad(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.progress_msg_loaddata),getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan pengajuan "+
                "<b>"+mSpinnerType.getSelectedItem().toString()+"</b>"+" pada "+
                "<b>"+ mSpinnerDateChoice.getSelectedItem().toString()+"</b>"+"?");
        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.overtime_btn_submit)
    public void onSubmitClicked(View view) {
        if(getValidationForm()){
            getPresenter().onSubmitClicked(
                    mSpinnerType.getSelectedItem(),
                    mEtStartTime.getText().toString().split("\\s")[1],
                    mEtEndTime.getText().toString().split("\\s")[1],
                    mEtReason.getText().toString());
        }
    }




    @Override
    @OnClick(R.id.overtime_edittext_starttime)
    public void onPickDateTimeStarttimeClicked(View view) {

        final String dateStart = mEtStartTime.getText().toString();
        final String dateEnd = mEtEndTime.getText().toString();
        new TimeWebRestAPI.OvertimePickDatetime(getActivity(), dateStart, new TimeWebRestAPI.OvertimePickDatetime.OnStatusCallback() {
            @Override
            public void showResult(String dateUser) {
                //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                //String dateUser = df.format(date);
                try {
                    if (TimeWebRestAPI.dateIsInBeetween(HelperBridge.startDateTime, HelperBridge.endDateTime, dateUser)
                            && timeIsValid(HelperBridge.startDateTime, HelperBridge.endDateTime, dateUser) ){
                        mEtStartTime.setText(dateUser);
                        mEtStartTime.setTextColor(Color.BLACK);
                        showToast("Setting waktu BERHASIL");
                    } else {
                        showToast("Anda tidak dapat melakukan request melebihi batas waktu");
                        mEtStartTime.setText(HelperBridge.startDateTime);
                        mEtStartTime.setTextColor(Color.GRAY);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }).datePicker();

    }

    private boolean timeIsValid(String dateStart, String dateEnd, String dateUser) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        if (!TimeWebRestAPI.getDifference(df.parse(dateStart), df.parse(dateUser)).contains("-") && !TimeWebRestAPI.getDifference(df.parse(dateUser), df.parse(dateEnd)).contains("-")) return true;
        return false;
    }

    @Override
    @OnClick(R.id.overtime_edittext_endtime)
    public void onPickDateTimeEndtimeClicked(View view) {
        final String dateStart = mEtStartTime.getText().toString();
        final String dateEnd = mEtEndTime.getText().toString();
        new TimeWebRestAPI.OvertimePickDatetime(getActivity(), dateEnd, new TimeWebRestAPI.OvertimePickDatetime.OnStatusCallback() {
            @Override
            public void showResult(String dateUser) {
                //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                //String dateUser = df.format(date);
                try {
                    if (TimeWebRestAPI.dateIsInBeetween(HelperBridge.startDateTime, HelperBridge.endDateTime, dateUser)
                            && timeIsValid(HelperBridge.startDateTime, HelperBridge.endDateTime, dateUser) ){
                        mEtEndTime.setText(dateUser);
                        mEtEndTime.setTextColor(Color.BLACK);
                        showToast("Setting waktu BERHASIL");
                    } else {
                        showToast("Anda tidak dapat melakukan request melebihi batas waktu");
                        mEtEndTime.setTextColor(Color.GRAY);
                        mEtEndTime.setText(HelperBridge.endDateTime);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }).datePicker();
    }




    @Override
    public void initializeOvertimeDates(ArrayList<OvertimeAvailableResponseModel> arrayList) {
        mTvLabelDatechoice.setVisibility(View.VISIBLE);
        mSpinnerDateChoice.setVisibility(View.VISIBLE);
        mArrayAdapterDatesChoice.clear();
        mArrayAdapterDatesChoice = new ArrayAdapter<OvertimeAvailableResponseModel>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        mArrayAdapterDatesChoice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDateChoice.setAdapter(mArrayAdapterDatesChoice);
        mSpinnerDateChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getPresenter().onDateSelected((OvertimeAvailableResponseModel) adapterView.getSelectedItem(), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mArrayAdapterDatesChoice.setNotifyOnChange(true);
        mArrayAdapterDatesChoice.notifyDataSetChanged();
    }

    @Override
    public void initializeOvertimeTypes(ArrayList<OvertimeAvailableTypeAdapter> arrayList) {
        mTvLabelType.setVisibility(View.VISIBLE);
        mSpinnerType.setVisibility(View.VISIBLE);
        mArrayAdapterOvertimeTypes.clear();

        mArrayAdapterOvertimeTypes = new ArrayAdapter<OvertimeAvailableTypeAdapter>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        mArrayAdapterOvertimeTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerType.setAdapter(mArrayAdapterOvertimeTypes);
        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getPresenter().onTypeSelected((OvertimeAvailableTypeAdapter) adapterView.getSelectedItem(), i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mArrayAdapterOvertimeTypes.setNotifyOnChange(true);
        mArrayAdapterOvertimeTypes.notifyDataSetChanged();
    }

    //String startTime, endTime;

    @Override
    public void initializeOvertimeTimes(String startTime, String endTime) {
        //this.startTime = startTime;
        //this.endTime = endTime;
        HelperBridge.startDateTime = startTime;
        HelperBridge.endDateTime = endTime;
        mLinearTimeRange.setVisibility(View.VISIBLE);
        mEtStartTime.setText(startTime);
        mEtEndTime.setText(endTime);
        llOvertime.setVisibility(View.VISIBLE);
        mEtReason.setVisibility(View.VISIBLE);
        mButtonSubmit.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNoOvertime() {
        mEmptyInfoView.setVisibility(View.VISIBLE);
        hideHideableView();
    }

}
