package com.serasiautoraya.tdsproper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 28/02/2017.
 */

public class ModelActivityJourney {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("information")
    @Expose
    private String information;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
