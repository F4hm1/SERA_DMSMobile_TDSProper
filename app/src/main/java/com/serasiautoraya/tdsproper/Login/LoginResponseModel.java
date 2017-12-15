package com.serasiautoraya.tdsproper.Login;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class LoginResponseModel {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("PersonalCode")
    @Expose
    private String PersonalCode;

    @SerializedName("Fullname")
    @Expose
    private String Fullname;

    @SerializedName("SalesOffice")
    @Expose
    private String SalesOffice;

    @SerializedName("Company")
    @Expose
    private String Company;

    @SerializedName("PersonalArea")
    @Expose
    private String PersonalArea;

    @SerializedName("PersonalSubArea")
    @Expose
    private String PersonalSubArea;

    @SerializedName("PoolName")
    @Expose
    private String PoolName;

    @SerializedName("PoolCode")
    @Expose
    private String PoolCode;

    @SerializedName("PhotoFront")
    @Expose
    private String PhotoFront;

    @SerializedName("KtpId")
    @Expose
    private String KtpId;

    @SerializedName("KtpEndDate")
    @Expose
    private String KtpEndDate;

    @SerializedName("SimType")
    @Expose
    private String SimType;

    @SerializedName("SIMNumber")
    @Expose
    private String SIMNumber;

    @SerializedName("SIMEndDate")
    @Expose
    private String SIMEndDate;

    @SerializedName("PersonalCoordinatorId")
    @Expose
    private String PersonalCoordinatorId;

    @SerializedName("PersonalCoordinatorEmail")
    @Expose
    private String PersonalCoordinatorEmail;

    @SerializedName("PersonalCoordinatorName")
    @Expose
    private String PersonalCoordinatorName;

    @SerializedName("PersonalApprovalId")
    @Expose
    private String PersonalApprovalId;

    @SerializedName("PersonalApprovalEmail")
    @Expose
    private String PersonalApprovalEmail;

    @SerializedName("PersonalApprovalName")
    @Expose
    private String PersonalApprovalName;

    @SerializedName("TransactionToken")
    @Expose
    private String TransactionToken;

    @SerializedName("AttendanceCico")
    @Expose
    private String AttendanceCico;

    @SerializedName("AttendanceOvertime")
    @Expose
    private String AttendanceOvertime;

    @SerializedName("AttendanceAbsence")
    @Expose
    private String AttendanceAbsence;

    @SerializedName("AttendanceOLCTrip")
    @Expose
    private String AttendanceOLCTrip;

    @SerializedName("MaxHariRequestDriver")
    @Expose
    private String MaxHariRequestDriver;

    @SerializedName("MinHariRequestAbsence")
    @Expose
    private String MinHariRequestAbsence;

    @SerializedName("IsNeedFatigueInterview")
    @Expose
    private String IsNeedFatigueInterview;

    @SerializedName("LocationUpdateInterval")
    @Expose
    private String LocationUpdateInterval;

    @SerializedName("RequestCiCo")
    @Expose
    private String RequestCiCo;

    @SerializedName("ReportCiCo")
    @Expose
    private String ReportCiCo;

    @SerializedName("RequestAbsence")
    @Expose
    private String RequestAbsence;

    @SerializedName("ReportAbsence")
    @Expose
    private String ReportAbsence;

    @SerializedName("RequestOLCTrip")
    @Expose
    private String RequestOLCTrip;

    @SerializedName("ReportOLCTrip")
    @Expose
    private String ReportOLCTrip;

    @SerializedName("RequestOvertime")
    @Expose
    private String RequestOvertime;

    @SerializedName("ReportOvertime")
    @Expose
    private String ReportOvertime;

    @SerializedName("ReportServiceHour")
    @Expose
    private String ReportServiceHour;


    public String getRequestCiCo() {
        return RequestCiCo;
    }

    public String getReportCiCo() {
        return ReportCiCo;
    }

    public String getRequestAbsence() {
        return RequestAbsence;
    }

    public String getReportAbsence() {
        return ReportAbsence;
    }

    public String getRequestOLCTrip() {
        return RequestOLCTrip;
    }

    public String getReportOLCTrip() {
        return ReportOLCTrip;
    }

    public String getRequestOvertime() {
        return RequestOvertime;
    }

    public String getReportOvertime() {
        return ReportOvertime;
    }

    public String getReportServiceHour() {
        return ReportServiceHour;
    }

    public String getLocationUpdateInterval() {
        return LocationUpdateInterval;
    }

    public void setIsNeedFatigueInterview(String isNeedFatigueInterview) {
        IsNeedFatigueInterview = isNeedFatigueInterview;
    }

    public String getIsNeedFatigueInterview() {
        return IsNeedFatigueInterview;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getPersonalCode() {
        return PersonalCode;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getSalesOffice() {
        return SalesOffice;
    }

    public String getCompany() {
        return Company;
    }

    public String getPersonalArea() {
        return PersonalArea;
    }

    public String getPersonalSubArea() {
        return PersonalSubArea;
    }

    public String getPoolName() {
        return PoolName;
    }

    public String getPoolCode() {
        return PoolCode;
    }

    public String getPhotoFront() {
        return PhotoFront;
    }

    public String getKtpId() {
        return KtpId;
    }

    public String getKtpEndDate() {
        return KtpEndDate;
    }

    public String getSimType() {
        return SimType;
    }

    public String getSIMNumber() {
        return SIMNumber;
    }

    public String getSIMEndDate() {
        return SIMEndDate;
    }

    public String getPersonalCoordinatorId() {
        return PersonalCoordinatorId;
    }

    public String getPersonalCoordinatorEmail() {
        return PersonalCoordinatorEmail;
    }

    public String getPersonalCoordinatorName() {
        return PersonalCoordinatorName;
    }

    public String getPersonalApprovalId() {
        return PersonalApprovalId;
    }

    public String getPersonalApprovalEmail() {
        return PersonalApprovalEmail;
    }

    public String getPersonalApprovalName() {
        return PersonalApprovalName;
    }

    public String getTransactionToken() {
        Log.d("LOGGING ORDER", TransactionToken);
        return TransactionToken;
    }

    public String getAttendanceCico() {
        return AttendanceCico;
    }

    public String getAttendanceOvertime() {
        return AttendanceOvertime;
    }

    public String getAttendanceAbsence() {
        return AttendanceAbsence;
    }

    public String getAttendanceOLCTrip() {
        return AttendanceOLCTrip;
    }

    public String getMaxHariRequestDriver() {
        return MaxHariRequestDriver;
    }

    public String getMinHariRequestAbsence() {
        return MinHariRequestAbsence;
    }
}