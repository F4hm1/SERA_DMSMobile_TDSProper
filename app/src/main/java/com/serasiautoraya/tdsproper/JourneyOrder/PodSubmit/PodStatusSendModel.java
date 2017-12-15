package com.serasiautoraya.tdsproper.JourneyOrder.PodSubmit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 19/10/2017.
 * randi.dwinandra@gmail.com
 */

public class PodStatusSendModel extends Model{
    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("ActivityId")
    @Expose
    private String ActivityId;

    public PodStatusSendModel(String personalId, String activityId) {
        PersonalId = personalId;
        ActivityId = activityId;
    }


}
