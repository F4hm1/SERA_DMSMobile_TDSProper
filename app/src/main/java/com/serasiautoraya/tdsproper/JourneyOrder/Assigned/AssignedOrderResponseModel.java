package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class AssignedOrderResponseModel extends Model{

    @SerializedName("OrderID")
    @Expose
    public String OrderID;
    @SerializedName("AssignmentID")
    @Expose
    public Integer AssignmentId;
    @SerializedName("Status")
    @Expose
    public Integer Status;
    @SerializedName("Customer")
    @Expose
    public String Customer;
    @SerializedName("Origin")
    @Expose
    public String Origin;
    @SerializedName("Destination")
    @Expose
    public String Destination;
    @SerializedName("ETA")
    @Expose
    public String ETA;
    @SerializedName("ETD")
    @Expose
    public String ETD;
    @SerializedName("CurrentActivity")
    @Expose
    public String CurrentActivity;
    @SerializedName("Active")
    @Expose
    public String Active;
    @SerializedName("Acknowledge")
    @Expose
    public String Acknowledge;

    @SerializedName("Notes")
    @Expose
    public String Notes;

    @SerializedName("CargoDescription")
    @Expose
    public String CargoDescription;

    @SerializedName("PassengerName")
    @Expose
    public String PassengerName;

    @SerializedName("PhoneNumber")
    @Expose
    public String PassengerNoHP;

    @SerializedName("TotalPassenger")
    @Expose
    public Integer TotalPassenger;

    @SerializedName("Email")
    @Expose
    public String PassengerEmail;

    @SerializedName("JourneyId")
    @Expose
    public String JourneyId;

    @SerializedName("FirstActivityId")
    @Expose
    public Integer FirstActivityId;

    @SerializedName("LastActivityId")
    @Expose
    public Integer LastActivityId;

    @SerializedName("ProductType")
    @Expose
    public String ProductType;

    @SerializedName("RentDuration")
    @Expose
    public Integer RentDuration;

    @SerializedName("UOM")
    @Expose
    public String Uom;

    public AssignedOrderResponseModel(String orderID, Integer assignmentId, Integer status, String customer, String origin, String destination, String ETA, String ETD, String currentActivity, String active, String acknowledge, String notes, String cargoDescription) {
        OrderID = orderID;
        AssignmentId = assignmentId;
        Status = status;
        Customer = customer;
        Origin = origin;
        Destination = destination;
        this.ETA = ETA;
        this.ETD = ETD;
        CurrentActivity = currentActivity;
        Active = active;
        Acknowledge = acknowledge;
        Notes = notes;
        CargoDescription = cargoDescription;
    }


    public String getOrderID() {
        return OrderID;
    }

    public Integer getAssignmentId() {
        return AssignmentId;
    }

    public Integer getStatus() {
        return Status;
    }

    public String getCustomer() {
        return Customer;
    }

    public String getOrigin() {
        if(Origin == null){
            return "-";
        }else {
            return Origin;
        }
    }

    public String getDestination() {
        if(Origin == null){
            return "-";
        }else {
            return Destination;
        }
    }

    public String getETA() {
        return ETA;
    }

    public String getETD() {
        return ETD;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public void setAssignmentId(Integer assignmentId) {
        AssignmentId = assignmentId;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setETA(String ETA) {
        this.ETA = ETA;
    }

    public void setETD(String ETD) {
        this.ETD = ETD;
    }

    public String getCurrentActivity() {
//        if(CurrentActivity == null){
//            return "null";
//        }else{
            return CurrentActivity;
//        }
    }

    public String getActive() {
        return Active;
    }

    public String getAcknowledge() {
        return Acknowledge;
    }

    public void setCurrentActivity(String currentActivity) {
        CurrentActivity = currentActivity;
    }

    public void setActive(String active) {
        Active = active;
    }

    public void setAcknowledge(String acknowledge) {
        Acknowledge = acknowledge;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getCargoDescription() {
        return CargoDescription;
    }

    public void setCargoDescription(String cargoDescription) {
        CargoDescription = cargoDescription;
    }

    public String getPassengerName() {
        return PassengerName;
    }

    public void setPassengerName(String passengerName) {
        PassengerName = passengerName;
    }

    public String getPassengerNoHP() {
        return PassengerNoHP;
    }

    public void setPassengerNoHP(String passengerNoHP) {
        PassengerNoHP = passengerNoHP;
    }

    public Integer getTotalPassenger() {
        return TotalPassenger;
    }

    public void setTotalPassenger(Integer totalPassenger) {
        TotalPassenger = totalPassenger;
    }

    public String getPassengerEmail() {
        return PassengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        PassengerEmail = passengerEmail;
    }

    public String getJourneyId() {
        return JourneyId;
    }

    public void setJourneyId(String journeyId) {
        JourneyId = journeyId;
    }

    public Integer getFirstActivityId() {
        return FirstActivityId;
    }

    public void setFirstActivityId(Integer firstActivityId) {
        FirstActivityId = firstActivityId;
    }

    public Integer getLastActivityId() {
        return LastActivityId;
    }

    public void setLastActivityId(Integer lastActivityId) {
        LastActivityId = lastActivityId;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public Integer getRentDuration() {
        return RentDuration;
    }

    public void setRentDuration(Integer rentDuration) {
        RentDuration = rentDuration;
    }

    public String getUom() {
        if(Uom == null){
            return "";
        }
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }
}
