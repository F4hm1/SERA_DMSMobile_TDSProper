package com.serasiautoraya.tdsproper.BaseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class BaseResponseModel {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("responseText")
    @Expose
    private String responseText;

    @SerializedName("data")
    @Expose
    private Object[] data;

    public String getResponse() {
        return response;
    }

    public String getResponseText() {
        return responseText;
    }

    public Object[] getData() {
        return data;
    }

}
