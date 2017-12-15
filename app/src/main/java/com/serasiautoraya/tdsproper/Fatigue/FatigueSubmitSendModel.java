package com.serasiautoraya.tdsproper.Fatigue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class FatigueSubmitSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("Result")
    @Expose
    private String Result;

    @SerializedName("Reason")
    @Expose
    private String Reason;

    public FatigueSubmitSendModel(String personalId, String result, String reason) {
        PersonalId = personalId;
        Result = result;
        Reason = reason;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getResult() {
        return Result;
    }

    public String getReason() {
        return Reason;
    }
}
