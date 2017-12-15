package com.serasiautoraya.tdsproper.ModuleServiceHourHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 11/06/2017.
 */

public class ServiceHourHistoryResponseModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("FullName")
    @Expose
    private String FullName;

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("ServiceHourDate")
    @Expose
    private String ServiceHourDate;

    @SerializedName("ServiceHourStart")
    @Expose
    private String ServiceHourStart;

    @SerializedName("ServiceHourEnd")
    @Expose
    private String ServiceHourEnd;


    public String getPersonalId() {
        return PersonalId;
    }

    public String getFullName() {
        return FullName;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getServiceHourDate() {
        return ServiceHourDate;
    }

    public String getServiceHourStart() {
        return ServiceHourStart;
    }

    public String getServiceHourEnd() {
        return ServiceHourEnd;
    }
}
