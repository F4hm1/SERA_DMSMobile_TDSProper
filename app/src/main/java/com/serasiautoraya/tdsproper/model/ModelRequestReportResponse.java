package com.serasiautoraya.tdsproper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 24/01/2017.
 */
public class ModelRequestReportResponse {

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("TypeRequest")
    @Expose
    private String typeRequest;

    @SerializedName("TimeRequest")
    @Expose
    private String timeRequest;

    public String getId() {
        return id;
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public String getTimeRequest() {
        return timeRequest;
    }
}
