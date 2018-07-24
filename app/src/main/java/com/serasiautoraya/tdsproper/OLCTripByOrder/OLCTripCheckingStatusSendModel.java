package com.serasiautoraya.tdsproper.OLCTripByOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OLCTripCheckingStatusSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;


    public OLCTripCheckingStatusSendModel(String personalId, String orderCode) {
        PersonalId = personalId;
        OrderCode = orderCode;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getOrderCode() {
        return OrderCode;
    }

}
