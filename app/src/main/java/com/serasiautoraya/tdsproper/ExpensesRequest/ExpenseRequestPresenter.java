package com.serasiautoraya.tdsproper.ExpensesRequest;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.JourneyOrder.Assigned.AssignedOrderResponseModel;
import com.serasiautoraya.tdsproper.JourneyOrder.Assigned.AssignedOrderSendModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseRequestPresenter extends TiPresenter<ExpenseRequestView> {

    private RestConnection mRestConnection;
    private ExpenseRequestSendModel mExpenseRequestSendModel;
    ExpenseAvailableResponseModel expenseAvailableResponseModel;
    private String selectedOrderCode;
    private int curentTotalAmount = 0;

    private ArrayList<ExpenseAvailableOrderAdapter> expenseAvailableOrderAdapterList;

    public ExpenseRequestPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final ExpenseRequestView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

//    public void onSearchClicked(String orderCode) {
//        selectedOrderCode = orderCode;
//        getView().toggleLoadingSearchingOrder(true);
//        final ExpenseAvailableSendModel expenseAvailableSendModel = new ExpenseAvailableSendModel(
//                HelperBridge.sModelLoginResponse.getPersonalId(),
//                orderCode
//        );
//
//        final ExpenseRequestView expenseRequestView = getView();
//            mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_EXPENSE_AVAILABLE, expenseAvailableSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
//                @Override
//                public void callBackOnSuccess(BaseResponseModel response) {
//                    expenseAvailableResponseModel = Model.getModelInstance(response.getData()[0], ExpenseAvailableResponseModel.class);
//                    generateExpenseInputValue(expenseAvailableResponseModel);
//                    expenseRequestView.toggleLoadingSearchingOrder(false);
//                }
//
//                @Override
//                public void callBackOnFail(String response) {
//                    /*
//                    * TODO change this!
//                    * */
//                    expenseRequestView.showToast(response);
//                    expenseRequestView.toggleLoadingSearchingOrder(false);
//                }
//
//                @Override
//                public void callBackOnError(VolleyError error) {
//                    /*
//                    * TODO change this!
//                    * */
//                    expenseRequestView.showToast("FAIL: " + error.toString());
//                    expenseRequestView.toggleLoadingSearchingOrder(false);
//                }
//            });
//    }

    private void generateExpenseInputValue(ExpenseAvailableResponseModel expenseAvailableResponseModel) {
        String[] typeCode = expenseAvailableResponseModel.getExpenseTypeCode().split(";");
        String[] typeName = expenseAvailableResponseModel.getExpenseTypeName().split(";");

        HashMap<String, ExpenseInputModel> expenseInputList = new HashMap<>();

        for (int i = 0; i < typeCode.length; i++) {
            expenseInputList.put(typeCode[i], new ExpenseInputModel(typeCode[i], typeName[i], "0"));
        }

        getView().setExpenseInputForm(expenseInputList, typeCode);

    }

    public void onSubmitClicked(HashMap<String, String> expenseResult) {
        String exType = "";
        String exAmount = "";
        for (Map.Entry<String, String> entry : expenseResult.entrySet()) {
            String expenseCode = entry.getKey();
            String amount = entry.getValue();
            exType += expenseCode + ";";
            exAmount += amount + ";";
        }

        exType = exType.substring(0, exType.length() - 1);
        exAmount = exAmount.substring(0, exAmount.length() - 1);

        final String expenseTypes = exType;
        final String expenseAmounts = exAmount;

        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                mExpenseRequestSendModel = new ExpenseRequestSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        selectedOrderCode,
                        expenseTypes,
                        expenseAmounts,
                        timeRESTResponseModel.getTime()
                );
                getView().toggleLoading(false);
                getView().showConfirmationDialog();
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });
    }

    public void onRequestSubmitted() {
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_EXPENSE, mExpenseRequestSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
//                    getView().showStandardDialog(response.getString("responseText"), "Berhasil");
                    getView().showConfirmationSuccess(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().toggleLoading(false);
                getView().showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().toggleLoading(false);
                getView().showStandardDialog("Gagal menyimpan data expense, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void loadAvailableExpenseData() {
        getView().toggleLoadingInitialLoad(true);
        ExpenseAvailableOrderSendModel expenseAvailableOrderSendModel = new ExpenseAvailableOrderSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId()
        );

        final ExpenseRequestView expenseRequestView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_EXPENSE_AVAILABLE_ORDER, expenseAvailableOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                expenseAvailableOrderAdapterList = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    ExpenseAvailableOrderResponseModel expenseAvailableOrderResponseModel = Model.getModelInstance(response.getData()[i], ExpenseAvailableOrderResponseModel.class);
                    expenseAvailableOrderAdapterList.add(new ExpenseAvailableOrderAdapter(expenseAvailableOrderResponseModel));
                }

                if (expenseAvailableOrderAdapterList.isEmpty()) {
                    expenseRequestView.setNoAvailableExpense();
                } else {
                    expenseRequestView.initializeOvertimeDates(expenseAvailableOrderAdapterList);
                }
                expenseRequestView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                expenseRequestView.setNoAvailableExpense();
                expenseRequestView.showToast(response);
                expenseRequestView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                expenseRequestView.setNoAvailableExpense();
                expenseRequestView.showToast("ERROR: " + error.toString());
                expenseRequestView.toggleLoadingInitialLoad(false);
            }
        });
    }

    public void onOrderSelected(ExpenseAvailableOrderAdapter expenseAvailableOrderAdapter) {
        getView().toggleLoadingSearchingOrder(true);
        final ExpenseAvailableSendModel expenseAvailableSendModel = new ExpenseAvailableSendModel(
                expenseAvailableOrderAdapter.getExpenseAvailableOrderResponseModel().getAssignmentId()
        );

//        final String orderCode = expenseAvailableOrderAdapter.getExpenseAvailableOrderResponseModel().getOrderCode();
        final String orderCode = expenseAvailableOrderAdapter.getExpenseAvailableOrderResponseModel().getAssignmentId()+"";
        final ExpenseRequestView expenseRequestView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_EXPENSE_INFO, expenseAvailableSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                if(response.getData().length > 0) {
                    expenseAvailableResponseModel = Model.getModelInstance(response.getData()[0], ExpenseAvailableResponseModel.class);
                    generateExpenseInputValue(expenseAvailableResponseModel);
                    selectedOrderCode = orderCode;
                }else{
                    getView().showToast("Data Expense tidak ditemukan");
                }
                expenseRequestView.toggleLoadingSearchingOrder(false);
            }

            @Override
            public void callBackOnFail(String response) {
                    /*
                    * TODO change this!
                    * */
                expenseRequestView.showToast(response);
                expenseRequestView.toggleLoadingSearchingOrder(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                    /*
                    * TODO change this!
                    * */
                expenseRequestView.showToast("FAIL: " + error.toString());
                expenseRequestView.toggleLoadingSearchingOrder(false);
            }
        });
    }

    public void loadNoActualExpense() {
        curentTotalAmount = 0;
        getView().toggleLoading(true);
        final ExpenseRequestView expenseRequestView = getView();
        AssignedOrderSendModel assignedOrderSendModel = new AssignedOrderSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperTransactionCode.ASSIGNED_REQUEST_CLOSEDEXPENSE,
                "1900-01-01",
                "2100-01-01"
        );

        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ASSIGNED_ORDER, assignedOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                expenseAvailableOrderAdapterList = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    AssignedOrderResponseModel assignedOrderResponseModel = Model.getModelInstance(response.getData()[i], AssignedOrderResponseModel.class);
                    ExpenseAvailableOrderResponseModel expenseAvailableOrderResponseModel = new ExpenseAvailableOrderResponseModel(
                            assignedOrderResponseModel.getAssignmentId(),
                            assignedOrderResponseModel.getOrderID()
                    );
                    expenseAvailableOrderAdapterList.add(new ExpenseAvailableOrderAdapter(expenseAvailableOrderResponseModel));
                }

                if (expenseAvailableOrderAdapterList.isEmpty()) {
                    expenseRequestView.setNoAvailableExpense();
                } else {
                    expenseRequestView.initializeOvertimeDates(expenseAvailableOrderAdapterList);
                }

//                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false));
                expenseRequestView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
//                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false));
                expenseRequestView.showToast(response);
                expenseRequestView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
//                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false));
                expenseRequestView.showToast("ERROR: " + error.toString());
                expenseRequestView.toggleLoading(false);
            }
        });
    }

    public void calculateAmount(CharSequence charSequence) {
        try {
            int addedAmount = Integer.valueOf(charSequence.toString());
            curentTotalAmount += addedAmount;
            getView().setTotalExpense(curentTotalAmount + "");
        } catch (Exception ex) {
            getView().setTotalExpense("Angka yang anda masukan salah");
        }
    }
}
