package com.serasiautoraya.tdsproper.Helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Randi Dwi Nandra on 26/04/2017.
 */

public class CurrencyTextWatcher implements TextWatcher {

    private EditText mEditText;
    private String mCurrentVal = "";

    public CurrencyTextWatcher(EditText mEditText) {
        this.mEditText = mEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String s = mEditText.getText().toString();
        if(!s.toString().equals(mCurrentVal)){

            mEditText.removeTextChangedListener(this);

            String cleanString = s.toString().replace("R", "").replace("p", "").replace("-", "").replace(",", "").replace(".", "");

            double parsed = 0;

            try{
                parsed = Double.parseDouble(cleanString);
            }catch (Exception ex){
                parsed = 0;
            }

            Locale locale = new Locale("id");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
            formatter.setCurrency(Currency.getInstance("IDR"));
            formatter.setMinimumFractionDigits(0);

            String stringFormatted = formatter.format(parsed);

            mCurrentVal = stringFormatted;
            mEditText.setText(stringFormatted+",-");
            mEditText.setSelection(stringFormatted.length());
            mEditText.addTextChangedListener(this);
        }
    }
}
