package com.serasiautoraya.tdsproper.OLCTripByOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class OLCTripSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("PersonalCode")
    @Expose
    private String PersonalCode;

    @SerializedName("WFStatus")
    @Expose
    private String WFStatus;

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;


    @SerializedName("ListOfActualOLC")
    @Expose
    private List<OlcTripModel> ListOfActualOLC;

    @SerializedName("ETA")
    @Expose
    private String ETA;

    @SerializedName("ETD")
    @Expose
    private String ETD;


    @SerializedName("OLCTripDate")
    @Expose
    private String OLCTripDate;

    @SerializedName("AddBy")
    @Expose
    private String AddBy;

    @SerializedName("SubmitType")
    @Expose
    private String SubmitType;

    @SerializedName("CustomerEmail")
    @Expose
    private String CustomerEmail;

    @SerializedName("PersonalApprovalId")
    @Expose
    private String PersonalApprovalId;

    @SerializedName("PersonalApprovalEmail")
    @Expose
    private String PersonalApprovalEmail;

    @SerializedName("PersonalCoordinatorId")
    @Expose
    private String PersonalCoordinatorId;

    @SerializedName("PersonalCoordinatorEmail")
    @Expose
    private String PersonalCoordinatorEmail;

    @SerializedName("PassengerName")
    @Expose
    private String PassengerName;

    @SerializedName("OLCMobileTimeStamp")
    @Expose
    private String OLCMobileTimeStamp;

    @SerializedName("OLCMobileTimeStampUTC")
    @Expose
    private String OLCMobileTimeStampUTC;


    /*@SerializedName("Reason")
    @Expose
    private String Reason;*/

    public OLCTripSendModel(String personalId, String personalCode, String WFStatus, String orderCode, List<OlcTripModel> listOfActualOLC, String ETA, String ETD, String date, String addBy, String submitType, String emailCustomer, String personalApprovalId, String personalApprovalEmail, String personalCoordinatorId, String personalCoordinatorEmail, String passengerName, String olcMobileTime, String localToUTC) {
        PersonalId = personalId;
        PersonalCode = personalCode;
        this.WFStatus = WFStatus;
        OrderCode = orderCode;
        ListOfActualOLC = listOfActualOLC;
        this.ETA = ETA;
        this.ETD = ETD;
        this.OLCTripDate = date;
        AddBy = addBy;
        SubmitType = submitType;
        CustomerEmail = emailCustomer;
        PersonalApprovalId = personalApprovalId;
        PersonalApprovalEmail = personalApprovalEmail;
        PersonalCoordinatorId = personalCoordinatorId;
        PersonalCoordinatorEmail = personalCoordinatorEmail;
        PassengerName = passengerName;
        OLCMobileTimeStamp = olcMobileTime;
        OLCMobileTimeStampUTC = localToUTC;
    }


    public String getOLCMobileTimeStampUTC() {
        return OLCMobileTimeStampUTC;
    }

    public String getOLCMobileTimeStamp() {
        return OLCMobileTimeStamp;
    }

    public String getOLCTripDate() {
        return OLCTripDate;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getPersonalCode() {
        return PersonalCode;
    }

    public String getWFStatus() {
        return WFStatus;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public List<OlcTripModel> getListOfActualOLC() {
        return ListOfActualOLC;
    }

    public String getETA() {
        return ETA;
    }

    public String getETD() {
        return ETD;
    }


    public String getAddBy() {
        return AddBy;
    }

    public String getSubmitType() {
        return SubmitType;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public String getPersonalApprovalId() {
        return PersonalApprovalId;
    }

    public String getPersonalApprovalEmail() {
        return PersonalApprovalEmail;
    }

    public String getPersonalCoordinatorId() {
        return PersonalCoordinatorId;
    }

    public String getPersonalCoordinatorEmail() {
        return PersonalCoordinatorEmail;
    }

    public String getPassengerName() {
        return PassengerName;
    }


    static class OlcTripModel extends Model {


        @SerializedName("PICTypeCode")
        @Expose
        private String PICTypeCode;

        @SerializedName("OLC")
        @Expose
        private String OLC;

        @SerializedName("Trip")
        @Expose
        private String Trip;


        public OlcTripModel(String picTypeCode, String olc, String trip) {
            PICTypeCode = picTypeCode;
            OLC = olc;
            Trip = trip;
        }

        public String getPICTypeCode() {
            return PICTypeCode;
        }

        public String getOLC() {
            return OLC;
        }

        public String getTrip() {
            return Trip;
        }
    }

}

