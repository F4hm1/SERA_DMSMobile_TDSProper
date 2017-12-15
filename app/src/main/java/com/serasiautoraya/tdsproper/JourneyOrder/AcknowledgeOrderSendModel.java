package com.serasiautoraya.tdsproper.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AcknowledgeOrderSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String personalId;
    @SerializedName("AssignmentId")
    @Expose
    private Integer assignmentId;
    @SerializedName("DateAck")
    @Expose
    private String dateAck;
    @SerializedName("TimeAck")
    @Expose
    private String timeAck;
    @SerializedName("ETA")
    @Expose
    private String eTA;
    @SerializedName("ETD")
    @Expose
    private String eTD;

    public AcknowledgeOrderSendModel(String personalId, Integer assignmentId, String dateAck, String timeAck, String eTA, String eTD) {
        this.personalId = personalId;
        this.assignmentId = assignmentId;
        this.dateAck = dateAck;
        this.timeAck = timeAck;
        this.eTA = eTA;
        this.eTD = eTD;
    }

    public String getPersonalId() {
        return personalId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public String getDateAck() {
        return dateAck;
    }

    public String getTimeAck() {
        return timeAck;
    }

    public String geteTA() {
        return eTA;
    }

    public String geteTD() {
        return eTD;
    }
}
