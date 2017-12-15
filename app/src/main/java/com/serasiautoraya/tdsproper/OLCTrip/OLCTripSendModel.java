package com.serasiautoraya.tdsproper.OLCTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OLCTripSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("PersonalCode")
    @Expose
    private String PersonalCode;

    @SerializedName("WFStatus")
    @Expose
    private String WFStatus;

    @SerializedName("OLCTripDate")
    @Expose
    private String OLCTripDate;

    @SerializedName("OLC")
    @Expose
    private String OLC;

    @SerializedName("Trip")
    @Expose
    private String Trip;

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

    public OLCTripSendModel(String personalId, String personalCode, String WFStatus, String OLCTripDate, String OLC, String trip, String reason, String addBy, String submitType, String personalApprovalId, String personalApprovalEmail, String personalCoordinatorId, String personalCoordinatorEmail) {
        PersonalId = personalId;
        PersonalCode = personalCode;
        this.WFStatus = WFStatus;
        this.OLCTripDate = OLCTripDate;
        this.OLC = OLC;
        Trip = trip;
        Reason = reason;
        AddBy = addBy;
        SubmitType = submitType;
        PersonalApprovalId = personalApprovalId;
        PersonalApprovalEmail = personalApprovalEmail;
        PersonalCoordinatorId = personalCoordinatorId;
        PersonalCoordinatorEmail = personalCoordinatorEmail;
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

    public String getOLCTripDate() {
        return OLCTripDate;
    }

    public String getOLC() {
        return OLC;
    }

    public String getTrip() {
        return Trip;
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
}
