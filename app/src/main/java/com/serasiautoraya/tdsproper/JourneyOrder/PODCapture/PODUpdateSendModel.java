package com.serasiautoraya.tdsproper.JourneyOrder.PODCapture;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by randi on 15/08/2017.
 */

public class PODUpdateSendModel extends Model{

    @SerializedName("PersonalId")
    @Expose
    public String personalId;
    @SerializedName("ActivityCode")
    @Expose
    public String activityCode;
    @SerializedName("Reason")
    @Expose
    public String reason;
    @SerializedName("TimeStamp")
    @Expose
    public String timeStamp;

    @SerializedName("JourneyId")
    @Expose
    public String journeyId;

    /*
    * LocationRealCoordinates(lat,long)
    * */
    @SerializedName("LocationRealCoordinates")
    @Expose
    public String locationRealCoordinates;

    @SerializedName("LocationRealText")
    @Expose
    public String locationRealText;

    @SerializedName("TimeStampUTC")
    @Expose
    public String timeStampUTC;

    /**
     * No args constructor for use in serialization
     *
     */
    public PODUpdateSendModel() {
    }

    /**
     *
     * @param activityCode
     * @param timeStamp
     * @param reason
     * @param personalId
     */
    public PODUpdateSendModel(String personalId, String activityCode, String reason, String timeStamp, String journeyId, String locationRealCoordinates, String locationRealText, String timeStampUTC) {
        this.personalId = personalId;
        this.activityCode = activityCode;
        this.reason = reason;
        this.timeStamp = timeStamp;
        this.journeyId = journeyId;
        this.locationRealCoordinates = locationRealCoordinates;
        this.locationRealText = locationRealText;
        this.timeStampUTC = timeStampUTC;
    }
}
