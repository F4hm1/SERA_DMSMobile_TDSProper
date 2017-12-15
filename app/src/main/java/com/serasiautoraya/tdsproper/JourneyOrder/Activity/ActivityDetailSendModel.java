package com.serasiautoraya.tdsproper.JourneyOrder.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class ActivityDetailSendModel extends Model{

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("OrderId")
    @Expose
    private String OrderId;

    @SerializedName("AssignmentId")
    @Expose
    private Integer AssignmentId;

    public ActivityDetailSendModel(String personalId, String orderId, Integer assignmentId) {
        PersonalId = personalId;
        OrderId = orderId;
        AssignmentId = assignmentId;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }
}
