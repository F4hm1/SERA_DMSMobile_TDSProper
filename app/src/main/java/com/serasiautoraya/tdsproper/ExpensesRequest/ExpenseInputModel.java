package com.serasiautoraya.tdsproper.ExpensesRequest;

/**
 * Created by randi on 04/07/2017.
 */

public class ExpenseInputModel {
    private String key;
    private String nameType;
    private String amount;

    public ExpenseInputModel(String key, String nameType, String amount) {
        this.key = key;
        this.nameType = nameType;
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public String getNameType() {
        return nameType;
    }

    public String getAmount() {
        return amount;
    }
}
