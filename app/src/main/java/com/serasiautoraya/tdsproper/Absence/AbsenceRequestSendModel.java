package com.serasiautoraya.tdsproper.Absence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

class AbsenceRequestSendModel extends Model {

    /*
    * This model class is used by ABSENCE_TEMP request API and ABSENCE request API
    * */

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("PersonalCode")
    @Expose
    private String PersonalCode;

    @SerializedName("WFStatus")
    @Expose
    private String WFStatus;

    @SerializedName("AbsenceType")
    @Expose
    private String AbsenceType;

    @SerializedName("DateStart")
    @Expose
    private String DateStart;

    @SerializedName("DateEnd")
    @Expose
    private String DateEnd;

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

    public AbsenceRequestSendModel(String personalId, String personalCode, String WFStatus, String absenceType, String dateStart, String dateEnd, String reason, String addBy, String submitType, String personalApprovalId, String personalApprovalEmail, String personalCoordinatorId, String personalCoordinatorEmail, String salesOffice) {
        PersonalId = personalId;
        PersonalCode = personalCode;
        this.WFStatus = WFStatus;
        AbsenceType = absenceType;
        DateStart = dateStart;
        DateEnd = dateEnd;
        Reason = reason;
        AddBy = addBy;
        SubmitType = submitType;
        PersonalApprovalId = personalApprovalId;
        PersonalApprovalEmail = personalApprovalEmail;
        PersonalCoordinatorId = personalCoordinatorId;
        PersonalCoordinatorEmail = personalCoordinatorEmail;
        SalesOffice = salesOffice;
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

    public String getAbsenceType() {
        return AbsenceType;
    }

    public String getDateStart() {
        return DateStart;
    }

    public String getDateEnd() {
        return DateEnd;
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
}
