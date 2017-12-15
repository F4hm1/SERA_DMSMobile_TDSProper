package com.serasiautoraya.tdsproper.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseAvailableSendModel extends Model {

    @SerializedName("AssignmentId")
    @Expose
    private Integer AssignmentId;

    public ExpenseAvailableSendModel(Integer assignmentId) {
        AssignmentId = assignmentId;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }
}
