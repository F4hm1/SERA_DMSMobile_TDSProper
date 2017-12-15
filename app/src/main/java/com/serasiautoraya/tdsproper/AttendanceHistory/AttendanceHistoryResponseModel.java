package com.serasiautoraya.tdsproper.AttendanceHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AttendanceHistoryResponseModel {
    @SerializedName("Day")
    @Expose
    private String Day;

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

    @SerializedName("Overtime")
    @Expose
    private String Overtime;

    @SerializedName("OLC")
    @Expose
    private String OLC;

    @SerializedName("Trip")
    @Expose
    private String Trip;

    @SerializedName("Absence")
    @Expose
    private String Absence;

    public String getDay() {
        return Day;
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

    public String getOvertime() {
        return Overtime;
    }

    public String getOLC() {
        return OLC;
    }

    public String getTrip() {
        return Trip;
    }

    public String getAbsence() {
        return Absence;
    }
}
