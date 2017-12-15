package com.serasiautoraya.tdsproper.CustomDialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 07/12/2016.
 */
public class TimePickerToEditTextDialog {

    private EditText editText;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;

    private boolean isBeforeCurrentTime;

    public TimePickerToEditTextDialog(EditText editText, Context context){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
        this.editText = editText;
        this.context = context;
        editText.setInputType(InputType.TYPE_NULL);
        editText.requestFocus();
        setTimeField();
    }

    private void setTimeField() {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this.context, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String zeroMinute = minute < 10? "0":"";
                String zeroHour = hourOfDay < 10? "0":"";
                editText.setText(zeroHour+hourOfDay+":"+zeroMinute+minute);
                isBeforeCurrentTime = checkBeforeCurrentTime(hourOfDay, minute);
            }

        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);
    }

    private boolean checkBeforeCurrentTime(int hourOfDay, int minute) {
        Calendar newCalendar = Calendar.getInstance();
        boolean result = false;
        if(newCalendar.get(Calendar.HOUR_OF_DAY) > hourOfDay){
            result = true;
        }else if(newCalendar.get(Calendar.HOUR_OF_DAY) == hourOfDay){
            if(newCalendar.get(Calendar.MINUTE) >= minute){
                result = true;
            }
        }
        return result;
    }

    public boolean isBeforeCurrentTime() {
        return isBeforeCurrentTime;
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
//        TextView tv = (TextView) getActivity().findViewById(R.id.edittext_cico_time);
//        tv.setText(hourOfDay+":"+minute);
    }

}
