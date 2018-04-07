package com.serasiautoraya.tdsproper.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Fahmi Hakim on 05/04/2018.
 * for SERA
 */

public class ExpenseCheckingSendModel  extends Model{

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    /*@SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;*/


    public ExpenseCheckingSendModel(String orderCode) {
      //  this.AssignmentId = AssignmentId;
        this.OrderCode = orderCode;

    }


    public String getOrderCode() {
        return OrderCode;
    }


    /*public String getAssignmentId() {
        return AssignmentId;
    }*/

}
