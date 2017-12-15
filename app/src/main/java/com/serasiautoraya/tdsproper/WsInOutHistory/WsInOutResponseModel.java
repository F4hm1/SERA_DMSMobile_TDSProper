package com.serasiautoraya.tdsproper.WsInOutHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by randi on 24/07/2017.
 */

public class WsInOutResponseModel extends Model {

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("ScheduleIn")
    @Expose
    private String ScheduleIn;

    @SerializedName("ScheduleOut")
    @Expose
    private String ScheduleOut;

    @SerializedName("ClockIn")
    @Expose
    private String ClockIn;

    @SerializedName("ClockOut")
    @Expose
    private String ClockOut;

    @SerializedName("Absence")
    @Expose
    private String Absence;

    @SerializedName("Day")
    @Expose
    private String Day;

    @SerializedName("Overtime")
    @Expose
    private String Overtime;

    @SerializedName("OLC")
    @Expose
    private String OLC;

    @SerializedName("Trip")
    @Expose
    private String Trip;

    public WsInOutResponseModel(String date, String scheduleIn, String scheduleOut, String clockIn, String clockOut, String absence, String day, String overtime, String olc, String trip) {
        Date = date;
        ScheduleIn = scheduleIn;
        ScheduleOut = scheduleOut;
        ClockIn = clockIn;
        ClockOut = clockOut;
        Absence = absence;
        Day = day;
        Overtime = overtime;
        OLC = olc;
        Trip = trip;
    }

    public String getDate() {
        return Date;
    }

    public String getScheduleIn() {
        return ScheduleIn;
    }

    public String getScheduleOut() {
        return ScheduleOut;
    }

    public String getClockIn() {
        return ClockIn;
    }

    public String getClockOut() {
        return ClockOut;
    }

    public String getAbsence() {
        return Absence;
    }

    public String getDay() {
        return Day;
    }

    public String getOvertime() {
        return Overtime;
    }

    public String getOLC() {
        return OLC;
    }

    public String getTrip() {
        return Trip;
    }
}
