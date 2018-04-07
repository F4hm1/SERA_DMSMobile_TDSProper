package com.serasiautoraya.tdsproper.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Fahmi Hakim on 06/04/2018.
 * for SERA
 */

public class ExpenseCheckingTripSendModel  extends Model {


    @SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;


    public ExpenseCheckingTripSendModel(String assignmentId) {
        this.AssignmentId = assignmentId;

    }


    public String getAssignmentId() {
        return AssignmentId;
    }

}
