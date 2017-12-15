package com.serasiautoraya.tdsproper.RestClient;

/**
 * Created by Randi Dwi Nandra on 08/05/2017.
 */

public class LocationModel {

    private String longitude;
    private String latitude;
    private String address;

    public LocationModel(String longitude, String latitude, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }
}
