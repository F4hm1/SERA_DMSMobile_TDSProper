package com.serasiautoraya.tdsproper.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 30/08/2017.
 * randi.dwinandra@gmail.com
 */

public class ExpenseAvailableOrderResponseModel {

    @SerializedName("AssignmentId")
    @Expose
    private Integer AssignmentId;

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    public ExpenseAvailableOrderResponseModel(Integer assignmentId, String orderCode) {
        AssignmentId = assignmentId;
        OrderCode = orderCode;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }

    public String getOrderCode() {
        return OrderCode;
    }
}
