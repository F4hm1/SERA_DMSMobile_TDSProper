package com.serasiautoraya.tdsproper.OrderHistory.OrderHistoryDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 23/05/2017.
 */

public class OrderHistoryDetailSendModel extends Model {

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;

    public OrderHistoryDetailSendModel(String orderCode, String assignmentId) {
        OrderCode = orderCode;
        AssignmentId = assignmentId;
    }
}
