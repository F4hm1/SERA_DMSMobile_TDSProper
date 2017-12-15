package com.serasiautoraya.tdsproper.BaseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class TimeRESTResponseModel {
    @SerializedName("sunrise")
    @Expose
    private String sunrise;

    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("countryCode")
    @Expose
    private String countryCode;

    @SerializedName("gmtOffset")
    @Expose
    private String gmtOffset;

    @SerializedName("rawOffset")
    @Expose
    private String rawOffset;

    @SerializedName("sunset")
    @Expose
    private String sunset;

    @SerializedName("timezoneId")
    @Expose
    private String timezoneId;

    @SerializedName("dstOffset")
    @Expose
    private String dstOffset;

    @SerializedName("countryName")
    @Expose
    private String countryName;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("lat")
    @Expose
    private String lat;

    public String getSunrise() {
        return sunrise;
    }

    public String getLng() {
        return lng;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getGmtOffset() {
        return gmtOffset;
    }

    public String getRawOffset() {
        return rawOffset;
    }

    public String getSunset() {
        return sunset;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public String getDstOffset() {
        return dstOffset;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getTime() {
        return time;
    }

    public String getLat() {
        return lat;
    }
}
