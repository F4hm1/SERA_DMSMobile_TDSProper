package com.serasiautoraya.tdsproper.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 30/08/2017.
 * randi.dwinandra@gmail.com
 */

public class ExpenseAvailableOrderSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    public ExpenseAvailableOrderSendModel(String personalId) {
        PersonalId = personalId;
    }

    public String getPersonalId() {
        return PersonalId;
    }
}
