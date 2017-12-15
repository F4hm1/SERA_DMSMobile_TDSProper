package com.serasiautoraya.tdsproper.Overtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OvertimeAvailableResponseModel {

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("IdCico")
    @Expose
    private String IdCico;

    @SerializedName("ScheduleIn")
    @Expose
    private String ScheduleIn;

    @SerializedName("ScheduleOut")
    @Expose
    private String ScheduleOut;

    @SerializedName("OvertimeStart")
    @Expose
    private String OvertimeStart;

    @SerializedName("OvertimeEnd")
    @Expose
    private String OvertimeEnd;

    @SerializedName("Freeday")
    @Expose
    private String Freeday;

    @SerializedName("OvertimeTypeCode")
    @Expose
    private String OvertimeTypeCode;

    @SerializedName("OvertimeTypeName")
    @Expose
    private String OvertimeTypeName;

    public String getDate() {
        return Date;
    }

    public String getIdCico() {
        return IdCico;
    }

    public String getScheduleIn() {
        return ScheduleIn;
    }

    public String getScheduleOut() {
        return ScheduleOut;
    }

    public String getOvertimeStart() {
        return OvertimeStart;
    }

    public String getOvertimeEnd() {
        return OvertimeEnd;
    }

    public String getFreeday() {
        return Freeday;
    }

    public String getOvertimeTypeCode() {
        return OvertimeTypeCode;
    }

    public String getOvertimeTypeName() {
        return OvertimeTypeName;
    }

    /*
        * TODO delete this constructor
        * */
    public OvertimeAvailableResponseModel(String date, String idCico, String scheduleIn, String scheduleOut, String overtimeStart, String overtimeEnd, String freeday, String overtimeTypeCode, String overtimeTypeName) {
        Date = date;
        IdCico = idCico;
        ScheduleIn = scheduleIn;
        ScheduleOut = scheduleOut;
        OvertimeStart = overtimeStart;
        OvertimeEnd = overtimeEnd;
        Freeday = freeday;
        OvertimeTypeCode = overtimeTypeCode;
        OvertimeTypeName = overtimeTypeName;
    }

    @Override
    public String toString() {
        return this.Date;
    }
}
