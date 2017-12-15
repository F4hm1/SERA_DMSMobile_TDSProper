package com.serasiautoraya.tdsproper.JourneyOrder.PODCapture;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by randi on 15/08/2017.
 */

public class PODPhotoSendModel extends Model{

    @SerializedName("Photo")
    @Expose
    public String photo;
    @SerializedName("ActivityCode")
    @Expose
    public String activityCode;
    @SerializedName("OrderCode")
    @Expose
    public String orderCode;
    @SerializedName("PersonalId")
    @Expose
    public String personalId;

    /**
     * No args constructor for use in serialization
     *
     */
    public PODPhotoSendModel() {
    }

    /**
     *
     * @param activityCode
     * @param orderCode
     * @param personalId
     * @param photo
     */
    public PODPhotoSendModel(String photo, String activityCode, String orderCode, String personalId) {
        super();
        this.photo = photo;
        this.activityCode = activityCode;
        this.orderCode = orderCode;
        this.personalId = personalId;
    }

}
