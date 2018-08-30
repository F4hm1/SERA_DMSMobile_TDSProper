package com.serasiautoraya.tdsproper.OLCTripByOrder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.CustomDialog.DatePickerToEditTextDialog;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 02/06/2017.
 */

public class OLCTripByOrderFragment extends TiFragment<OLCTripByOrderPresenter, OLCTripByOrderView> implements OLCTripByOrderView {

    @BindView(R.id.olctrip_spinner_olc_cust)
    Spinner mSpinnerCustOLC;
    /*@BindView(R.id.olctrip_spinner_olc_ops)
    Spinner mSpinnerOpsOLC;*/
    /*@BindView(R.id.documents_et_olc_cust)
    EditText mEtOlcAmountCust;*/
    @BindView(R.id.documents_et_trip_cust)
    EditText mEtTripAmountCust;
    @BindView(R.id.documents_et_olc_ops)
    EditText mEtOlcAmountOps;
    @BindView(R.id.documents_et_trip_ops)
    EditText mEtTripAmountOps;
    @BindView(R.id.olctrip_btn_submit)
    Button mButtonSubmit;

    @BindView(R.id.textview_desc_cust)
    TextView tvDescCust;
    @BindView(R.id.textview_desc_ops)
    TextView tvDescOps;


    private DatePickerToEditTextDialog mDatePickerToEditTextDialog;
    private ProgressDialog mProgressDialog;
    private Activity mParentActivity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_olctrip_request_by_order, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        initializeSpinnersContent();
       /* mEtOlcAmountCust.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double doubleValue = 0;
                if (s != null) {
                    try {
                        doubleValue = Double.parseDouble(s.toString().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        //Error
                    }
                }
            }
        });*/
        mEtTripAmountCust.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double doubleValue = 0;
                if (s != null) {
                    try {
                        doubleValue = Double.parseDouble(s.toString().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        //Error
                    }
                }
            }
        });
        mEtOlcAmountOps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double doubleValue = 0;
                if (s != null) {
                    try {
                        doubleValue = Double.parseDouble(s.toString().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        //Error
                    }
                }
            }
        });
        mEtTripAmountOps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double doubleValue = 0;
                if (s != null) {
                    try {
                        doubleValue = Double.parseDouble(s.toString().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        //Error
                    }
                }
            }
        });
      //  this.initializeSpinnersContent();
        //this.initializePickerDialog();
    }


    private void initializeSpinnersContent(){
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.olctrip_olc_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayList<CharSequence> olcValue = new ArrayList<>();
        try {
            for (Integer i = 0; i <= HelperBridge.sActivityDetailResponseModel.getMaxOLC(); i++) {
                olcValue.add(i+"");
            }
            adapter.addAll(olcValue);
        } catch (Exception e){}
        //mSpinnerOpsOLC.setAdapter(adapter);
        mSpinnerCustOLC.setAdapter(adapter);
        //mSpinnerOpsOLC.setSelection(olcValue.size() - 1 );
        mSpinnerCustOLC.setSelection(olcValue.size() - 1);
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
        //HelperUtil.showSimpleAlertDialogCustomTitle(message, getContext(), Title);

        HelperUtil.showSimpleAlertDialogCustomTitleAction(message, this.getContext(), Title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().loadDetailOrder();
                    }
                },
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //getPresenter().loadDetailOrder();
                    }
                });
    }

    @NonNull
    @Override
    public OLCTripByOrderPresenter providePresenter() {
        return new OLCTripByOrderPresenter(new RestConnection(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mParentActivity = (Activity) context;
        }
    }

    @Override
    public boolean getValidationForm() {
        boolean result = true;
        String errText = "";
        View focusView = null;

        /*if(TextUtils.isEmpty(mEtOlcAmountCust.getText().toString())){
            focusView = mEtOlcAmountCust;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_olcamount_cust);
            showToast(errText);
            result = false;
        } else if(TextUtils.isEmpty(mEtOlcAmountOps.getText().toString())){
            focusView = mEtOlcAmountOps;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_olcamount_ops);
            showToast(errText);
            result = false;
        }*/

        if(TextUtils.isEmpty(mEtTripAmountCust.getText().toString())){
            focusView = mEtTripAmountCust;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_tripamount_cust);
            showToast(errText);
            result = false;
        } else if(TextUtils.isEmpty(mEtOlcAmountOps.getText().toString())){
            focusView = mEtOlcAmountOps;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_olcamount_ops);
            showToast(errText);
            result = false;
        } else if(TextUtils.isEmpty(mEtTripAmountOps.getText().toString())){
            focusView = mEtTripAmountOps;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_tripamount_ops);
            showToast(errText);
            result = false;
        } else if(mSpinnerCustOLC.getSelectedItem().toString().equalsIgnoreCase("Pilih")){
            focusView = mSpinnerCustOLC;
            errText = getResources().getString(R.string.err_msg_empty_olctrip_olc);
            showToast(errText);
            result = false;
        }


        /*if(TextUtils.isEmpty(mEtDate.getText().toString())){
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
        }*/

        if(result == false){
            focusView.requestFocus();
        }

        return result;
    }

    @Override
    public void showConfirmationDialog(String ordercode) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan pengajuan "+
                "<b>"+"OLC/Trip"+"</b>"+" pada "+
                "<b>"+ ordercode +"</b>"+"?");
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
            /*String isOLC = HelperUtil.getValueStringArrayXML(
                    getResources().getStringArray(R.array.olctrip_olc_array),
                    getResources().getStringArray(R.array.olctrip_olc_array_val),
                    mSpinnerOLC.getSelectedItem().toString());
            getPresenter().onSubmitClicked(
                    mDatePickerToEditTextDialog.getDateServerFormat(),
                    isOLC,
                    mEtTripAmount.getText().toString(),
                    mEtReason.getText().toString());*/
            List<OLCTripSendModel.OlcTripModel> olcTripModelList = new ArrayList<>();
            olcTripModelList.add(new OLCTripSendModel.OlcTripModel(getString(R.string.olc_code_customer), mSpinnerCustOLC.getSelectedItem().toString(), mEtTripAmountCust.getText().toString()));
            olcTripModelList.add(new OLCTripSendModel.OlcTripModel(getString(R.string.olc_code_operation), mEtOlcAmountOps.getText().toString(), mEtTripAmountOps.getText().toString()));
            getPresenter().onSubmitClicked(
                    //mDatePickerToEditTextDialog.getDateServerFormat(),
                    olcTripModelList);
        }
    }

    @Override
    public void changeActivityAction(String[] key, String[] value, Class targetActivity) {
        HelperBridge.sAutoProcessActivity = true;
        if (mParentActivity != null) {
            Intent intent = new Intent(mParentActivity, targetActivity);
            for (int i = 0; i < key.length; i++) {
                intent.putExtra(key[i], value[i]);
            }
            startActivity(intent);
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    /*private void initializeSpinnersContent(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.olctrip_olc_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOLC.setAdapter(adapter);
    }*/

    /*private void initializePickerDialog(){
        mDatePickerToEditTextDialog = new DatePickerToEditTextDialog(mEtDate, getContext(), true, false);
    }*/
}
