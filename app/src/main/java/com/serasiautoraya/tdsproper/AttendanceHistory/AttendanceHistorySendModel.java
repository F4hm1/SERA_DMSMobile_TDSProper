package com.serasiautoraya.tdsproper.AttendanceHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AttendanceHistorySendModel extends Model{

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("StartDate")
    @Expose
    private String StartDate;

    @SerializedName("EndDate")
    @Expose
    private String EndDate;

    public AttendanceHistorySendModel(String personalId, String startDate, String endDate) {
        PersonalId = personalId;
        StartDate = startDate;
        EndDate = endDate;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }
}
