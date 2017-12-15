package com.serasiautoraya.tdsproper.Profiling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by randidwinandra on 14/12/17.
 */

public class DriverStatusSendModel extends Model{

    @SerializedName("PersonalId")
    @Expose
    private String personalId;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("StatusDate")
    @Expose
    private String statusDate;

    public DriverStatusSendModel(String personalId, String status, String statusDate) {
        this.personalId = personalId;
        this.status = status;
        this.statusDate = statusDate;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

}
