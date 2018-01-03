package com.serasiautoraya.tdsproper.ExpensesRequest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.serasiautoraya.tdsproper.CustomView.EmptyInfoView;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseRequestFragment extends TiFragment<ExpenseRequestPresenter, ExpenseRequestView> implements ExpenseRequestView {

    @BindView(R.id.expense_lin_requestgroup)
    LinearLayout mLinRequestGroup;

    @BindView(R.id.expense_lin_inputgroup)
    LinearLayout mLinInputGroup;

    @BindView(R.id.expense_lin_availablegroup)
    LinearLayout mLinAvailableGroup;

    @BindView(R.id.expense_spinner_available)
    Spinner mSpinnerAvailable;

    @BindView(R.id.expense_btn_submit)
    Button mButtonSubmit;

    @BindView(R.id.layout_empty_info)
    EmptyInfoView mEmptyInfoView;

    @BindView(R.id.expense_sv_container)
    ScrollView mSvContainer;

    @BindView(R.id.expense_total_et)
    EditText mEtTotal;


    private ProgressDialog mProgressDialog;
    private ArrayAdapter<ExpenseAvailableOrderAdapter> mAvailableOrderAdapterArrayAdapter;
    private HashMap<String, EditText> mHashEtAmount;

    List<EditText> etList = new ArrayList<>();
    private Long curentTotalAmount = 0L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        mEmptyInfoView.setIcon(R.drawable.ic_empty_expense);
        mEmptyInfoView.setText("Tidak terdapat expense yang dapat diajukan");
        mEmptyInfoView.setVisibility(View.GONE);
        mLinRequestGroup.setVisibility(View.GONE);
        mLinAvailableGroup.setVisibility(View.GONE);
        mSvContainer.setVisibility(View.VISIBLE);
        this.resetAmountView();
        getPresenter().loadNoActualExpense();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_expense_submit), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
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
    public void toggleLoadingSearchingOrder(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_expense), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin jumlah yang anda masukan sudah benar?");
        HelperUtil.showConfirmationAlertDialog(textMsg, getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitted();
            }
        });
    }

    @Override
    @OnClick(R.id.expense_btn_submit)
    public void onSubmitClicked(View view) {
        HashMap<String, String> expenseResult = new HashMap<>();
        for (Map.Entry<String, EditText> entry : mHashEtAmount.entrySet()) {
            String expenseCode = entry.getKey();
            String amount = entry.getValue().getText().toString().replace(".", "");
            expenseResult.put(expenseCode, amount);
        }
        getPresenter().onSubmitClicked(expenseResult);
    }

    @Override
    public void setExpenseInputForm(HashMap<String, ExpenseInputModel> expenseInputList, String[] typeCodeList) {
        mHashEtAmount = new HashMap<>();
        if (typeCodeList.length > 0) {
            mSvContainer.setVisibility(View.VISIBLE);
            mLinRequestGroup.setVisibility(View.VISIBLE);
        } else {
            mLinRequestGroup.setVisibility(View.GONE);
        }

        if (mLinInputGroup.getChildCount() > 0) {
            mLinInputGroup.removeAllViews();
        }
        for (int i = 0; i < typeCodeList.length; i++) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.single_list_expenseinput, null);
            EditText etAmount = (EditText) v.findViewById(R.id.expense_input_et);
            etAmount.setText(expenseInputList.get(typeCodeList[i]).getAmount());
            etList.add(etAmount);
            TextView tvInputLabel = (TextView) v.findViewById(R.id.expense_input_label);
            tvInputLabel.setText(expenseInputList.get(typeCodeList[i]).getNameType());
            mHashEtAmount.put(typeCodeList[i], etAmount);
            mLinInputGroup.addView(v);
        }

        for (final EditText et : etList) {
            RxTextView
                    .textChanges(et)
                    .debounce(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<CharSequence>() {
                        @Override
                        public void call(CharSequence charSequence) {
                            curentTotalAmount = 0L;
                            for (EditText et : etList) {
                                try {
                                    String strIntAble = et.getText().toString().replace(".", "");
                                    Long addedAmount = Long.valueOf(strIntAble);
                                    curentTotalAmount += addedAmount;
                                    String yourFormattedString = String.format("%,d", curentTotalAmount);
                                    setTotalExpense("Rp." + yourFormattedString);
                                } catch (Exception ex) {
                                    setTotalExpense("Angka yang anda masukan salah");
                                }
                            }
                        }
                    });

            final TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    et.removeTextChangedListener(this);
                    try {
                        String strIntAble = et.getText().toString().replace(".", "");
                        String yourFormattedString = String.format("%,d", Long.parseLong(strIntAble));
                        et.setText(yourFormattedString);
                        et.setSelection(et.getText().length());
                    } catch (Exception ex) {
                        Log.d("RXTEXT", ex.getMessage());
                    }
                    et.addTextChangedListener(this);
                }
            };

            et.addTextChangedListener(textWatcher);
        }
    }

    @Override
    public void toggleLoadingInitialLoad(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setNoAvailableExpense() {
        mSvContainer.setVisibility(View.GONE);
        mEmptyInfoView.setVisibility(View.VISIBLE);
        mLinRequestGroup.setVisibility(View.GONE);
        mLinAvailableGroup.setVisibility(View.GONE);
    }

    @Override
    public void initializeOvertimeDates(ArrayList<ExpenseAvailableOrderAdapter> expenseAvailableOrderResponseModelList) {
        mSvContainer.setVisibility(View.VISIBLE);
        mLinAvailableGroup.setVisibility(View.VISIBLE);
        if (mAvailableOrderAdapterArrayAdapter != null) {
            mAvailableOrderAdapterArrayAdapter.clear();
        }

        mAvailableOrderAdapterArrayAdapter = new ArrayAdapter<ExpenseAvailableOrderAdapter>(getContext(), android.R.layout.simple_spinner_item, expenseAvailableOrderResponseModelList);
        mAvailableOrderAdapterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAvailable.setAdapter(mAvailableOrderAdapterArrayAdapter);
        mSpinnerAvailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hideRequestGroupInput();
                resetAmountView();
                getPresenter().onOrderSelected((ExpenseAvailableOrderAdapter) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAvailableOrderAdapterArrayAdapter.setNotifyOnChange(true);
        mAvailableOrderAdapterArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void showConfirmationSuccess(String message, String title) {
        HelperUtil.showSimpleAlertDialogCustomTitleAction(message, this.getContext(), title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initialize();
                    }
                },
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        initialize();
                    }
                });
    }

    @NonNull
    @Override
    public ExpenseRequestPresenter providePresenter() {
        return new ExpenseRequestPresenter(new RestConnection((this.getContext())));
    }

    @Override
    public boolean getValidationForm() {
        return true;
    }

    @Override
    public void setTotalExpense(String total) {
        mEtTotal.setText(total);
    }

    @Override
    public void hideRequestGroupInput() {
        mLinRequestGroup.setVisibility(View.GONE);
    }

    private void resetAmountView() {
        for (final EditText et : etList) {
            et.setText("0");
        }
        curentTotalAmount = 0L;
        setTotalExpense("Rp.0");
    }

}
