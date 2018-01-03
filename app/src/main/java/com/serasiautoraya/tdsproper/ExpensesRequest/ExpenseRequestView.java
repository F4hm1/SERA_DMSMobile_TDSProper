package com.serasiautoraya.tdsproper.ExpensesRequest;

import android.view.View;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;
import com.serasiautoraya.tdsproper.BaseInterface.FormViewInterface;

import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;
import net.grandcentrix.thirtyinch.distinctuntilchanged.DistinctUntilChanged;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by randi on 03/07/2017.
 */

public interface ExpenseRequestView extends BaseViewInterface, FormViewInterface{

    void showConfirmationDialog();

    void onSubmitClicked(View view);

    void setExpenseInputForm(HashMap<String, ExpenseInputModel> expenseInputList, String[] typeCodeList);

    void setNoAvailableExpense();

    void initializeOvertimeDates(ArrayList<ExpenseAvailableOrderAdapter> expenseAvailableOrderResponseModelList);

    void showConfirmationSuccess(String message, String title);

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoadingInitialLoad(boolean isLoading);

    @CallOnMainThread
    @DistinctUntilChanged
    void toggleLoadingSearchingOrder(boolean isLoading);

    void setTotalExpense(String total);

    void hideRequestGroupInput();
}
