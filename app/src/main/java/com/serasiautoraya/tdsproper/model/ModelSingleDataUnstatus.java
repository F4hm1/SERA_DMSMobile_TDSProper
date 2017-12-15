package com.serasiautoraya.tdsproper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 25/01/2017.
 */
public class ModelSingleDataUnstatus {
    @SerializedName("data")
    @Expose
    private String data;

    public String getData() {
        return data;
    }
}
