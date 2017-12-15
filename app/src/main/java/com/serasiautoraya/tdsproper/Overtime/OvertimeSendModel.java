package com.serasiautoraya.tdsproper.Overtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OvertimeSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("PersonalCode")
    @Expose
    private String PersonalCode;

    @SerializedName("WFStatus")
    @Expose
    private String WFStatus;

    @SerializedName("OvertimeType")
    @Expose
    private String OvertimeType;

    @SerializedName("OvertimeDate")
    @Expose
    private String OvertimeDate;

    @SerializedName("OvertimeStart")
    @Expose
    private String OvertimeStart;

    @SerializedName("OvertimeEnd")
    @Expose
    private String OvertimeEnd;

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

    @SerializedName("IdCico")
    @Expose
    private String IdCico;

    public OvertimeSendModel(String personalId, String personalCode, String WFStatus, String overtimeType, String overtimeDate, String overtimeStart, String overtimeEnd, String reason, String addBy, String submitType, String personalApprovalId, String personalApprovalEmail, String personalCoordinatorId, String personalCoordinatorEmail, String idCico) {
        PersonalId = personalId;
        PersonalCode = personalCode;
        this.WFStatus = WFStatus;
        OvertimeType = overtimeType;
        OvertimeDate = overtimeDate;
        OvertimeStart = overtimeStart;
        OvertimeEnd = overtimeEnd;
        Reason = reason;
        AddBy = addBy;
        SubmitType = submitType;
        PersonalApprovalId = personalApprovalId;
        PersonalApprovalEmail = personalApprovalEmail;
        PersonalCoordinatorId = personalCoordinatorId;
        PersonalCoordinatorEmail = personalCoordinatorEmail;
        IdCico = idCico;
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

    public String getOvertimeType() {
        return OvertimeType;
    }

    public String getOvertimeDate() {
        return OvertimeDate;
    }

    public String getOvertimeStart() {
        return OvertimeStart;
    }

    public String getOvertimeEnd() {
        return OvertimeEnd;
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

    public String getIdCico() {
        return IdCico;
    }
}
