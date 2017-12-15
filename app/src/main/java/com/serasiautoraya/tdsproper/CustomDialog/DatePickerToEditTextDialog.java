package com.serasiautoraya.tdsproper.CustomDialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.serasiautoraya.tdsproper.util.HelperBridge;
import com.serasiautoraya.tdsproper.util.HelperKey;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 23/11/2016.
 */
public class DatePickerToEditTextDialog {
    //UI References
    private EditText editText;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat serverDateFormatter;
    private Context context;
    private String dateServerFormat;
    private static String dateFormat = HelperKey.USER_DATE_FORMAT;

    private boolean isBeforeToday, isInMaxRequest, isToday, isMaxDateToday, isNormalDate;

    public DatePickerToEditTextDialog(EditText editText, Context context, boolean isMaxDateToday, boolean isNormalDate){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
        this.editText = editText;
        this.context = context;
        this.isMaxDateToday = isMaxDateToday;
        this.isNormalDate = isNormalDate;
        serverDateFormatter = new SimpleDateFormat(HelperKey.SERVER_DATE_FORMAT, Locale.getDefault());
        dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        editText.setInputType(InputType.TYPE_NULL);
        editText.requestFocus();
        setDateTimeField();
    }

    public DatePickerToEditTextDialog(EditText editText, Context context, boolean isMaxDateToday, boolean isNormalDate, int minDateShowDiff){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
        this.editText = editText;
        this.context = context;
        this.isMaxDateToday = isMaxDateToday;
        this.isNormalDate = isNormalDate;
        serverDateFormatter = new SimpleDateFormat(HelperKey.SERVER_DATE_FORMAT, Locale.getDefault());
        dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        editText.setInputType(InputType.TYPE_NULL);
        editText.requestFocus();
        setDateTimeField(minDateShowDiff);
    }

    private void setDateTimeField() {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this.context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date = dateFormatter.format(newDate.getTime());
                dateServerFormat = serverDateFormatter.format(newDate.getTime());
                editText.setText(date);
                isBeforeToday = checkBeforeToday(date);
                isInMaxRequest = checkInMaxRequest(date);
                isToday = checkIsToday(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        if(!isNormalDate){
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - (HelperBridge.maxRequest*24*60*60*1000));
            if(isMaxDateToday){
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            }
        }

    }

    private void setDateTimeField(int minDateShowDiff) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this.context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date = dateFormatter.format(newDate.getTime());
                dateServerFormat = serverDateFormatter.format(newDate.getTime());
                editText.setText(date);
                isBeforeToday = checkBeforeToday(date);
                isInMaxRequest = checkInMaxRequest(date);
                isToday = checkIsToday(date);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        if(!isNormalDate){
//            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - (HelperBridge.maxRequest*24*60*60*1000));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (minDateShowDiff*24*60*60*1000));
            if(isMaxDateToday){
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            }
        }
    }


    private boolean checkIsToday(String date) {
        try {
            Date strDate = dateFormatter.parse(date);
            return HelperUtil.isToday(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkInMaxRequest(String date) {
        try {
            Date nowDate = new Date();
            Date targetDate = dateFormatter.parse(date);
            long days = HelperUtil.getDaysBetween(targetDate, nowDate);

            if(days <= HelperBridge.maxRequest){
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isInMaxRequest() {
        return isInMaxRequest;
    }

    private boolean checkBeforeToday(String date){
        try {
            Date nowDate = new Date();
            Date targetDate = dateFormatter.parse(date);
            long days = HelperUtil.getDaysBetween(nowDate, targetDate);

            if(days <= 0){
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBeforeToday() {
        return isBeforeToday;
    }

    public void setMinDateHistory(String sInitialDate) throws ParseException {
        Date initialDate = dateFormatter.parse(sInitialDate);
        Calendar cal = HelperUtil.getCalendarVersion(initialDate);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        editText.setText("");
    }

    public void setMaxDateHistory(String sInitialDate) throws ParseException {
        Date initialDate = dateFormatter.parse(sInitialDate);
        Calendar cal = HelperUtil.getCalendarVersion(initialDate);
        cal.set(Calendar.MONTH, cal.get( Calendar.MONTH ) + 1 );
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        editText.setText("");
//        setDateTimeField();
    }

    public void addMinDate(int days){
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (days*24*60*60*1000));
    }

    public String getDateServerFormat() {
        return dateServerFormat;
    }
}
