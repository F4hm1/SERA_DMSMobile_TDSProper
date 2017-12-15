package com.serasiautoraya.tdsproper.CiCo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class CiCoRealtimeSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("PersonalCode")
    @Expose
    private String PersonalCode;

    @SerializedName("WFStatus")
    @Expose
    private String WFStatus;

    @SerializedName("CicoType")
    @Expose
    private String CicoType;

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("Time")
    @Expose
    private String Time;

    @SerializedName("Reason")
    @Expose
    private String Reason;

    @SerializedName("AddBy")
    @Expose
    private String AddBy;

    @SerializedName("SubmitType")
    @Expose
    private String SubmitType;

    @SerializedName("PersonalApprovalId")
    @Expose
    private String PersonalApprovalId;

    @SerializedName("PersonalApprovalEmail")
    @Expose
    private String PersonalApprovalEmail;

    @SerializedName("PersonalCoordinatorId")
    @Expose
    private String PersonalCoordinatorId;

    @SerializedName("PersonalCoordinatorEmail")
    @Expose
    private String PersonalCoordinatorEmail;

    @SerializedName("Latitude")
    @Expose
    private String Latitude;

    @SerializedName("Longitude")
    @Expose
    private String Longitude;

    @SerializedName("Address")
    @Expose
    private String Address;

    @SerializedName("SalesOffice")
    @Expose
    private String SalesOffice;

    @SerializedName("IsMobile")
    @Expose
    private String IsMobile;

    @SerializedName("TerminalID")
    @Expose
    private String TerminalID;

    public CiCoRealtimeSendModel(String personalId, String personalCode, String WFStatus, String cicoType, String date, String time, String reason, String addBy, String submitType, String personalApprovalId, String personalApprovalEmail, String personalCoordinatorId, String personalCoordinatorEmail, String latitude, String longitude, String address, String salesOffice, String isMobile, String terminalID) {
        PersonalId = personalId;
        PersonalCode = personalCode;
        this.WFStatus = WFStatus;
        CicoType = cicoType;
        Date = date;
        Time = time;
        Reason = reason;
        AddBy = addBy;
        SubmitType = submitType;
        PersonalApprovalId = personalApprovalId;
        PersonalApprovalEmail = personalApprovalEmail;
        PersonalCoordinatorId = personalCoordinatorId;
        PersonalCoordinatorEmail = personalCoordinatorEmail;
        Latitude = latitude;
        Longitude = longitude;
        Address = address;
        SalesOffice = salesOffice;
        IsMobile = isMobile;
        TerminalID = terminalID;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getPersonalCode() {
        return PersonalCode;
    }

    public String getWFStatus() {
        return WFStatus;
    }

    public String getCicoType() {
        return CicoType;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getReason() {
        return Reason;
    }

    public String getAddBy() {
        return AddBy;
    }

    public String getSubmitType() {
        return SubmitType;
    }

    public String getPersonalApprovalId() {
        return PersonalApprovalId;
    }

    public String getPersonalApprovalEmail() {
        return PersonalApprovalEmail;
    }

    public String getPersonalCoordinatorId() {
        return PersonalCoordinatorId;
    }

    public String getPersonalCoordinatorEmail() {
        return PersonalCoordinatorEmail;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getAddress() {
        return Address;
    }

    public String getSalesOffice() {
        return SalesOffice;
    }

    public String getIsMobile() {
        return IsMobile;
    }

    public String getTerminalID() {
        return TerminalID;
    }
}
