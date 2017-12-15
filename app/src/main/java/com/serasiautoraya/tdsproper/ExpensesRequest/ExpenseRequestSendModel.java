package com.serasiautoraya.tdsproper.ExpensesRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseRequestSendModel extends Model {

    @SerializedName("DriverCode")
    @Expose
    private String DriverCode;

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("Type")
    @Expose
    private String Type;

    @SerializedName("Amount")
    @Expose
    private String Amount;

    /*
    * Formatnya yyyy-MM-dd hh:mm:ss
    * */
    @SerializedName("InputOn")
    @Expose
    private String TimeStamp;

    public ExpenseRequestSendModel(String driverCode, String orderCode, String type, String amount, String timeStamp) {
        DriverCode = driverCode;
        OrderCode = orderCode;
        Type = type;
        Amount = amount;
        TimeStamp = timeStamp;
    }

    public String getDriverCode() {
        return DriverCode;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getType() {
        return Type;
    }

    public String getAmount() {
        return Amount;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }
}
