package com.serasiautoraya.tdsproper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 30/11/2016.
 */
public class ModelArrayData {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("data")
    @Expose
    private Object[] data;

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
