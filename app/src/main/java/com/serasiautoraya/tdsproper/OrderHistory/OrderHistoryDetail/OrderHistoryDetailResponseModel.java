package com.serasiautoraya.tdsproper.OrderHistory.OrderHistoryDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 23/05/2017.
 */

public class OrderHistoryDetailResponseModel {

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("ActivityCode")
    @Expose
    private String ActivityCode;

    @SerializedName("ActivityName")
    @Expose
    private String ActivityName;

    @SerializedName("ActivityType")
    @Expose
    private String ActivityType;

    @SerializedName("TimeTarget")
    @Expose
    private String TimeTarget;

    @SerializedName("TimeBaseline")
    @Expose
    private String TimeBaseline;

    @SerializedName("TimeActual")
    @Expose
    private String TimeActual;

    @SerializedName("LocationTargetCoordinates")
    @Expose
    private String LocationTargetCoordinates;

    @SerializedName("LocationTargetText")
    @Expose
    private String LocationTargetText;

    @SerializedName("CargoType")
    @Expose
    private String CargoType;

    @SerializedName("UnitModel")
    @Expose
    private String UnitModel;

    @SerializedName("UnitNumber")
    @Expose
    private String UnitNumber;

    @SerializedName("Sequence")
    @Expose
    private String Sequence;

    public OrderHistoryDetailResponseModel(String orderCode, String activityCode, String activityName, String activityType, String timeTarget, String timeBaseline, String timeActual, String locationTargetCoordinates, String locationTargetText, String cargoType, String unitModel, String unitNumber, String sequence) {
        OrderCode = orderCode;
        ActivityCode = activityCode;
        ActivityName = activityName;
        ActivityType = activityType;
        TimeTarget = timeTarget;
        TimeBaseline = timeBaseline;
        TimeActual = timeActual;
        LocationTargetCoordinates = locationTargetCoordinates;
        LocationTargetText = locationTargetText;
        CargoType = cargoType;
        UnitModel = unitModel;
        UnitNumber = unitNumber;
        Sequence = sequence;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getActivityCode() {
        return ActivityCode;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public String getActivityType() {
        return ActivityType;
    }

    public String getTimeTarget() {
        return TimeTarget;
    }

    public String getTimeBaseline() {
        return TimeBaseline;
    }

    public String getTimeActual() {
        return TimeActual;
    }

    public String getLocationTargetCoordinates() {
        return LocationTargetCoordinates;
    }

    public String getLocationTargetText() {
        return LocationTargetText;
    }

    public String getCargoType() {
        return CargoType;
    }

    public String getUnitModel() {
        return UnitModel;
    }

    public String getUnitNumber() {
        return UnitNumber;
    }

    public String getSequence() {
        return Sequence;
    }
}
