package com.serasiautoraya.tdsproper.WsInOutHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by randi on 24/07/2017.
 */

public class WsInOutSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("StartDate")
    @Expose
    private String StartDate;

    @SerializedName("EndDate")
    @Expose
    private String EndDate;

    public WsInOutSendModel(String personalId, String startDate, String endDate) {
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
