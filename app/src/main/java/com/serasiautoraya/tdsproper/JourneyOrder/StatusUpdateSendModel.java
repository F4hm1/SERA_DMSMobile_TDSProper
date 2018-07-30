package com.serasiautoraya.tdsproper.JourneyOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class StatusUpdateSendModel extends Model {

    @SerializedName("ActivityCode")
    @Expose
    private String ActivityCode;

    @SerializedName("OrderCode")
    @Expose
    private String OrderCode;

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("LocationRealCoordinates")
    @Expose
    private String LocationRealCoordinates;

    @SerializedName("LocationRealText")
    @Expose
    private String LocationRealText;

    @SerializedName("Photo1")
    @Expose
    private String Photo1;

    @SerializedName("Photo2")
    @Expose
    private String Photo2;

    @SerializedName("Photo3")
    @Expose
    private String Photo3;

    @SerializedName("CodeVerification")
    @Expose
    private String CodeVerification;

    @SerializedName("TimeStamp")
    @Expose
    private String TimeStamp;

    @SerializedName("LocalTimeStamp")
    @Expose
    private String LocalTimeStamp;

    @SerializedName("Signature")
    @Expose
    private String Signature;

    @SerializedName("Reason")
    @Expose
    private String Reason;

    @SerializedName("JourneyId")
    @Expose
    private String JourneyId;

    @SerializedName("IsOLC")
    @Expose
    private String IsOLC;

    public StatusUpdateSendModel(String activityCode, String orderCode, String personalId, String locationRealCoordinates, String locationRealText, String photo1, String photo2, String photo3, String codeVerification, String timeStamp, String signature, String reason, String journeyId, String localTimeStamp, String isOLC) {
        ActivityCode = activityCode;
        OrderCode = orderCode;
        PersonalId = personalId;
        LocationRealCoordinates = locationRealCoordinates;
        LocationRealText = locationRealText;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        CodeVerification = codeVerification;
        TimeStamp = timeStamp;
        Signature = signature;
        Reason = reason;
        JourneyId = journeyId;
        LocalTimeStamp = localTimeStamp;
        IsOLC = isOLC;
    }

    public String getIsOLC() {
        return IsOLC;
    }

    public String getLocalTimeStamp() {
        return LocalTimeStamp;
    }

    public String getJourneyId() {
        return JourneyId;
    }

    public String getActivityCode() {
        return ActivityCode;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getLocationRealCoordinates() {
        return LocationRealCoordinates;
    }

    public String getLocationRealText() {
        return LocationRealText;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public String getPhoto3() {
        return Photo3;
    }

    public String getCodeVerification() {
        return CodeVerification;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public String getSignature() {
        return Signature;
    }

    public String getReason() {
        return Reason;
    }
}
