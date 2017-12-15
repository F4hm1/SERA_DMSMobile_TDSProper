package com.serasiautoraya.tdsproper.Profiling;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by randidwinandra on 14/12/17.
 */

public class DriverStatusResponseModel {

    @SerializedName("Status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
