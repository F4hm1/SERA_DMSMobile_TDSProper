package com.serasiautoraya.tdsproper.RequestHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class RequestHistoryResponseModel extends Model {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("TransType")
    @Expose
    private String TransType;

    @SerializedName("DateStart")
    @Expose
    private String DateStart;

    @SerializedName("DateEnd")
    @Expose
    private String DateEnd;

    @SerializedName("TimeStart")
    @Expose
    private String TimeStart;

    @SerializedName("TimeEnd")
    @Expose
    private String TimeEnd;

    @SerializedName("OvertimeType")
    @Expose
    private String OvertimeType;

    @SerializedName("AbsenceType")
    @Expose
    private String AbsenceType;

    @SerializedName("TripCount")
    @Expose
    private String TripCount;

    @SerializedName("OLCStatus")
    @Expose
    private String OLCStatus;

    @SerializedName("RequestDate")
    @Expose
    private String RequestDate;

    @SerializedName("RequestStatus")
    @Expose
    private String RequestStatus;

    @SerializedName("ApprovalBy")
    @Expose
    private String ApprovalBy;

    /*
    * TODO Delete this constructor and SET method (not needed)
    * */

    public RequestHistoryResponseModel(String id, String transType, String dateStart, String dateEnd, String timeStart, String timeEnd, String overtimeType, String absenceType, String tripCount, String OLCStatus, String requestDate, String requestStatus, String approvalBy) {
        Id = id;
        TransType = transType;
        DateStart = dateStart;
        DateEnd = dateEnd;
        TimeStart = timeStart;
        TimeEnd = timeEnd;
        OvertimeType = overtimeType;
        AbsenceType = absenceType;
        TripCount = tripCount;
        this.OLCStatus = OLCStatus;
        RequestDate = requestDate;
        RequestStatus = requestStatus;
        ApprovalBy = approvalBy;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getId() {
        return Model.getNonNullable(Id);
    }

    public String getTransType() {
        return Model.getNonNullable(TransType);
    }

    public String getDateStart() {
        return Model.getNonNullable(DateStart);
    }

    public String getDateEnd() {
        return Model.getNonNullable(DateEnd);
    }

    public String getTimeStart() {
        return Model.getNonNullable(TimeStart);
    }

    public String getTimeEnd() {
        return Model.getNonNullable(TimeEnd);
    }

    public String getOvertimeType() {
        return Model.getNonNullable(OvertimeType);
    }

    public String getAbsenceType() {
        return Model.getNonNullable(AbsenceType);
    }

    public String getTripCount() {
        return Model.getNonNullable(TripCount);
    }

    public String getOLCStatus() {
        return Model.getNonNullable(OLCStatus);
    }

    public String getRequestDate() {
        return Model.getNonNullable(RequestDate);
    }

    public String getRequestStatus() {
        return Model.getNonNullable(RequestStatus);
    }

    public String getApprovalBy() {
        return Model.getNonNullable(ApprovalBy);
    }

}
