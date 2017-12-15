package com.serasiautoraya.tdsproper.CiCo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class CiCoRequestSendModel extends Model {
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

    @SerializedName("SalesOffice")
    @Expose
    private String SalesOffice;

    @SerializedName("TerminalID")
    @Expose
    private String TerminalID;

    public CiCoRequestSendModel(String addBy, String personalId, String personalCode, String WFStatus, String cicoType, String date, String time, String reason, String submitType, String personalApprovalId, String personalApprovalEmail, String personalCoordinatorId, String personalCoordinatorEmail, String salesOffice, String terminalID) {
        AddBy = addBy;
        PersonalId = personalId;
        PersonalCode = personalCode;
        this.WFStatus = WFStatus;
        CicoType = cicoType;
        Date = date;
        Time = time;
        Reason = reason;
        SubmitType = submitType;
        PersonalApprovalId = personalApprovalId;
        PersonalApprovalEmail = personalApprovalEmail;
        PersonalCoordinatorId = personalCoordinatorId;
        PersonalCoordinatorEmail = personalCoordinatorEmail;
        SalesOffice = salesOffice;
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

    public String getSalesOffice() {
        return SalesOffice;
    }

    public String getTerminalID() {
        return TerminalID;
    }
}
