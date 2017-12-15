package com.serasiautoraya.tdsproper.ExpensesRequest;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseAvailableOrderAdapter {
    private ExpenseAvailableOrderResponseModel expenseAvailableOrderResponseModel;

    public ExpenseAvailableOrderAdapter(ExpenseAvailableOrderResponseModel expenseAvailableOrderResponseModel) {
        this.expenseAvailableOrderResponseModel = expenseAvailableOrderResponseModel;
    }

    public ExpenseAvailableOrderResponseModel getExpenseAvailableOrderResponseModel() {
        return expenseAvailableOrderResponseModel;
    }

    @Override
    public String toString() {
        return expenseAvailableOrderResponseModel.getOrderCode();
    }
}
