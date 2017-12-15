package com.serasiautoraya.tdsproper.LocationUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class LocationUpdateSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;


    @SerializedName("Latitude")
    @Expose
    private String Latitude;


    @SerializedName("Longitude")
    @Expose
    private String Longitude;


    @SerializedName("Location")
    @Expose
    private String Location;

    public LocationUpdateSendModel(String personalId, String latitude, String longitude, String location) {
        PersonalId = personalId;
        Latitude = latitude;
        Longitude = longitude;
        Location = location;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLocation() {
        return Location;
    }
}
