package com.serasiautoraya.tdsproper.Overtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OvertimeAvailableSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("DateStart")
    @Expose
    private String DateStart;

    @SerializedName("DateEnd")
    @Expose
    private String DateEnd;

    public OvertimeAvailableSendModel(String personalId, String dateStart, String dateEnd) {
        PersonalId = personalId;
        DateStart = dateStart;
        DateEnd = dateEnd;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getDateStart() {
        return DateStart;
    }

    public String getDateEnd() {
        return DateEnd;
    }
}
