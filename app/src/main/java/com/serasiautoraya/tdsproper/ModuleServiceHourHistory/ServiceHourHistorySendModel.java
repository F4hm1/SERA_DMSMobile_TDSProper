package com.serasiautoraya.tdsproper.ModuleServiceHourHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 11/06/2017.
 */

public class ServiceHourHistorySendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("StartDate")
    @Expose
    private String StartDate;

    @SerializedName("EndDate")
    @Expose
    private String EndDate;

    public ServiceHourHistorySendModel(String personalId, String startDate, String endDate) {
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
