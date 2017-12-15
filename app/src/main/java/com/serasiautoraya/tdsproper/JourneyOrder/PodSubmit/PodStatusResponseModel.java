package com.serasiautoraya.tdsproper.JourneyOrder.PodSubmit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 19/10/2017.
 * randi.dwinandra@gmail.com
 */

public class PodStatusResponseModel {

    @SerializedName("AcitivityId")
    @Expose
    public String AcitivityId;

    @SerializedName("StatusCode")
    @Expose
    public String StatusCode;

    @SerializedName("StatusName")
    @Expose
    public String StatusName;

    @SerializedName("CoordinatorName")
    @Expose
    public String CoordinatorName;

    @SerializedName("ListOfURLPODPicture")
    @Expose
    public String[] ListOfURLPODPicture;

    public String getAcitivityId() {
        return AcitivityId;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public String getStatusName() {
        return StatusName;
    }

    public String getCoordinatorName() {
        return CoordinatorName;
    }

    public String[] getListOfURLPODPicture() {
        return ListOfURLPODPicture;
    }
}
