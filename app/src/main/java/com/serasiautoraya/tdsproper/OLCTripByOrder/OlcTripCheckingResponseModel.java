package com.serasiautoraya.tdsproper.OLCTripByOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Fahmi Hakim on 21/05/2018.
 * for SERA
 */

public class OlcTripCheckingResponseModel extends Model{

    @SerializedName("OLCAvailability")
    @Expose
    private String OLCAvailability;

    public OlcTripCheckingResponseModel(String olcAvailability) {
        OLCAvailability = olcAvailability;
    }

    public String getCheckingStatus() {
        return OLCAvailability;
    }
}
