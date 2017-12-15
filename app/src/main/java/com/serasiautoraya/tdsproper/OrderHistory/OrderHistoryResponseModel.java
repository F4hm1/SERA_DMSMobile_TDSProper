package com.serasiautoraya.tdsproper.OrderHistory;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 22/05/2017.
 */

public class OrderHistoryResponseModel extends Model {


    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("AssignmentId")
    @Expose
    private String AssignmentId;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Customer")
    @Expose
    private String Customer;

    @SerializedName("Origin")
    @Expose
    private String Origin;

    @SerializedName("Destination")
    @Expose
    private String Destination;

    @SerializedName("ETA")
    @Expose
    private String ETA;

    @SerializedName("ETD")
    @Expose
    private String ETD;

    public OrderHistoryResponseModel(String orderCode, String status, String customer, String origin, String destination, String ETA, String ETD) {
        OrderCode = orderCode;
        Status = status;
        Customer = customer;
        Origin = origin;
        Destination = destination;
        this.ETA = ETA;
        this.ETD = ETD;
    }

    public String getAssignmentId() {
        return AssignmentId;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getStatus() {
        return Status;
    }

    public String getCustomer() {
        return Customer;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getDestination() {
        return Destination;
    }

    public String getETA() {
        return ETA;
    }

    public String getETD() {
        return ETD;
    }
}
