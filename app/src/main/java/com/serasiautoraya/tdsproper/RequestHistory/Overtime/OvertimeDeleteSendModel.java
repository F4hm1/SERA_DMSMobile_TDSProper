package com.serasiautoraya.tdsproper.RequestHistory.Overtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OvertimeDeleteSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    public OvertimeDeleteSendModel(String personalId, String requestId) {
        PersonalId = personalId;
        RequestId = requestId;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getRequestId() {
        return RequestId;
    }
}
