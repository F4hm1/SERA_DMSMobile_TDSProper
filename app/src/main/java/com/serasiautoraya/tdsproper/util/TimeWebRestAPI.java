package com.serasiautoraya.tdsproper.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.serasiautoraya.tdsproper.CustomListener.ServerCallBack;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.model.ModelTimeRESTResponse;
import com.serasiautoraya.tdsproper.model.VolleyUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

//import android.icu.text.DecimalFormat;
//import android.icu.text.SimpleDateFormat;
//import android.icu.util.Calendar;

/**
 * Created by Randi Dwi Nandra on 18/01/2017.
 */
public class TimeWebRestAPI {
    private static TimeWebRestAPI timeWebRestAPI = null;
    private static Context sContext;
    private static RequestQueue mqueue;
    private static ModelTimeRESTResponse resultResponse = null;



    public static TimeWebRestAPI getInstance(Context context) {
        if (timeWebRestAPI == null) {
            timeWebRestAPI = new TimeWebRestAPI();
            mqueue = VolleyUtil.getRequestQueue();
        }
        sContext = context;
        return timeWebRestAPI;
    }


    public static ModelTimeRESTResponse getResultResponse() {
        return resultResponse;
    }

    public void getResponse(final ServerCallBack callback) {
        String url = HelperUrl.GET_SERVER_LOCALTIME;
        final ProgressDialog loading = ProgressDialog.show(sContext, sContext.getResources().getString(R.string.prog_msg_loadingjam),
                sContext.getResources().getString(R.string.prog_msg_wait), true, false);

        String lat = "";
        String lng = "";

//        if(HelperBridge.gps.canGetLocation()){
//            if(HelperBridge.gps.getLatitude() > 0 || HelperBridge.gps.getLatitude() < 0){
//                lat = HelperBridge.gps.getLatitude()+"";
//                lng = HelperBridge.gps.getLongitude()+"";
//            }
//        }

//        if(lat.equalsIgnoreCase("")){
            if((LocationServiceUtil.getLocationManager(sContext).getLastLocation() != null) &&
                    LocationServiceUtil.getLocationManager(sContext).isGPSEnabled()){
                lat = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLatitude();
                lng = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLongitude();
            }else{
                HelperUtil.showSimpleAlertDialogCustomTitle("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.", sContext, "Perhatian");
                loading.dismiss();
                return;
            }
//        }

//        String lat = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLatitude();
//        String lng = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLongitude();

        ModelTimeRESTResponse result = null;
        HashMap<String, String> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("username", "randinandra");

        GsonRequest<ModelTimeRESTResponse> request = new GsonRequest<ModelTimeRESTResponse>(
                Request.Method.GET,
                url,
                ModelTimeRESTResponse.class,
                null,
                params,
                new Response.Listener<ModelTimeRESTResponse>() {
                    @Override
                    public void onResponse(ModelTimeRESTResponse response) {
                        loading.dismiss();
                        if (response != null) {
                            callback.onSuccessModelTimeRESTResponse(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        HelperUtil.showSimpleAlertDialog(sContext.getResources().getString(R.string.err_msg_general), sContext);
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }

    public static String getTimeZoneID(ModelTimeRESTResponse modelTimeRESTResponse){
        String result = "";
        switch (modelTimeRESTResponse.getGmtOffset()){
            case "7":
                result = "WIB";
                break;
            case "8":
                result = "WITA";
                break;
            case "9":
                result = "WIT";
                break;
        }
        return result;
    }

    public static boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg))
        {
            boolean valid = false;
            //Start Time
            java.util.Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            //Current Time
            java.util.Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            //End Time
            java.util.Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0)
            {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }

            java.util.Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0) && actualTime.before(calendar2.getTime()))
            {
                valid = true;
                return valid;
            } else {
                throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
            }
        }

        return false;
    }


    public static boolean isTimeBetweenTwoTimeSecondMethode(String argStartTime,
                                               String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }

    }


    public static boolean compare(String system_time, String currentTime, String endtimes) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            Date startime = simpleDateFormat.parse("19:25:00");
            Date endtime = simpleDateFormat.parse("20:30:00");

            //current time
            Date current_time = simpleDateFormat.parse("20:00:00");

            if (current_time.after(startime) && current_time.before(endtime)) {
                System.out.println("Yes");
                return true;
            }
            else if (current_time.after(startime) && current_time.after(endtime)) {
                return true; //overlap condition check
            }
            else {
                System.out.println("No");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public class getTimeDiff{
    /*
    * Execution
    * */
        /*Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
        int minute = now.get(Calendar.MINUTE);

        Date date = parseDate(hour + ":" + minute);
        Date dateCompareOne = parseDate("08:00");
        Date dateCompareTwo = parseDate("20:00");

        if (dateCompareOne.before( date ) && dateCompareTwo.after(date)) {
            //your logic
        }*/

        private Date parseDate(String date) {

            final String inputFormat = "HH:mm";
            SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
            try {
                return inputParser.parse(date);
            } catch (java.text.ParseException e) {
                return new Date(0);
            }
        }

    }


    public static String getDifference(Date d1, Date d2) {
        String result = null;

        /** in milliseconds */
        long diff = d2.getTime() - d1.getTime();

        /** remove the milliseconds part */
        diff = diff / 1000;

        long days = diff / (24 * 60 * 60);
        long hours = diff / (60 * 60) % 24;
        long minutes = diff / 60 % 60;
        long seconds = diff % 60;

        result = days + " days, ";
        result += hours + " hours, ";
        result += minutes + " minutes, ";
        result += seconds + " seconds.";

        return result;
    }

    public static boolean dateIsInBeetween(String dateStart, String dateEnd, String dateUser){
        try {
            /*String string1 = dateStart;
            String[] splited1 = string1.split("\\s+");*/
            Date time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStart);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);

            /*String string2 = dateEnd;
            String[] splited2 = string2.split("\\s+");*/
            Date time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateEnd);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

//            String[] splited3 = dateUser.split("\\s+");
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateUser);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();

            if (( x.after(calendar1.getTime()) || x.equals(calendar1.getTime()) ) && ( x.before(calendar2.getTime()) || x.equals(calendar2.getTime()) ) ) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class OvertimePickDatetime{
        private String date_time = "";
        private int mYear;
        private int mMonth;
        private int mDay;

        private int mHour;
        private int mMinute;

        private String date;
        private Activity mActivity;
        private OnStatusCallback callback;

        public OvertimePickDatetime(Activity activity, String dateOvertime, OnStatusCallback callback) {
            mActivity = activity;
            date = dateOvertime;
            this.callback = callback;
        }


        public void datePicker(){

            final java.util.Calendar c = java.util.Calendar.getInstance();
            mYear = c.get(java.util.Calendar.YEAR);
            mMonth = c.get(java.util.Calendar.MONTH);
            mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            DecimalFormat df = new DecimalFormat("00");
                            date_time = year + "-" + df.format(monthOfYear + 1) + "-" + dayOfMonth ;
                            timePicker();
                        }
                    }, mYear, mMonth, mDay);
            String[] currentServerDate = date.split("[-\\s]");
            datePickerDialog.updateDate(Integer.parseInt(currentServerDate[0]), Integer.parseInt((currentServerDate[1].equals("01") ? "13" : currentServerDate[1])) - 1, Integer.parseInt(currentServerDate[2]));
            datePickerDialog.show();
        }

        private void timePicker(){
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(java.util.Calendar.HOUR_OF_DAY);
            mMinute = c.get(java.util.Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            mHour = hourOfDay;
                            mMinute = minute;
                            callback.showResult(date_time+" "+ hourOfDay + ":" + String.format("%02d", minute));
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }




        public interface OnStatusCallback{
            void showResult(String date);
        }


    }


}
