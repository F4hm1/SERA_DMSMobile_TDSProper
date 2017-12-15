package com.serasiautoraya.tdsproper.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseAvailableResponseModel {

    @SerializedName("TripExpenseCategory")
    @Expose
    private String ExpenseTypeCode;

    @SerializedName("TripExpenseCategoryName")
    @Expose
    private String ExpenseTypeName;


    public String getExpenseTypeCode() {
        return ExpenseTypeCode;
    }

    public String getExpenseTypeName() {
        return ExpenseTypeName;
    }

}
