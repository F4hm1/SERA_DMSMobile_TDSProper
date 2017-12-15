package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AssignedOrderSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("RequestType")
    @Expose
    private String RequestType;

    @SerializedName("AssignmentStart")
    @Expose
    private String AssignmentStart;

    @SerializedName("AssignmentEnd")
    @Expose
    private String AssignmentEnd;

    public AssignedOrderSendModel(String personalId, String requestType, String assignmentStart, String assignmentEnd) {
        PersonalId = personalId;
        RequestType = requestType;
        AssignmentStart = assignmentStart;
        AssignmentEnd = assignmentEnd;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getRequestType() {
        return RequestType;
    }

    public String getAssignmentStart() {
        return AssignmentStart;
    }

    public String getAssignmentEnd() {
        return AssignmentEnd;
    }
}
