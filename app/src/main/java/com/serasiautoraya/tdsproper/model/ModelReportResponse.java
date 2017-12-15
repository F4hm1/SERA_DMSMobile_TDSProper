package com.serasiautoraya.tdsproper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 13/12/2016.
 */
public class ModelReportResponse {
    @SerializedName("Hari")
    @Expose
    private String hari;

    @SerializedName("Tanggal")
    @Expose
    private String tanggal;

    @SerializedName("WsIn")
    @Expose
    private String wsIn;

    @SerializedName("WsOut")
    @Expose
    private String wsOut;

    @SerializedName("Libur")
    @Expose
    private String libur;

    @SerializedName("Ci")
    @Expose
    private String ci;

    @SerializedName("Co")
    @Expose
    private String co;

    @SerializedName("Absence")
    @Expose
    private String absence;

    @SerializedName("Trip")
    @Expose
    private String trip;

    @SerializedName("OLC")
    @Expose
    private String oLC;

    @SerializedName("OvtAwal")
    @Expose
    private String ovtAwal;

    @SerializedName("OvtAkhir")
    @Expose
    private String ovtAkhir;

    @SerializedName("OvtLibur")
    @Expose
    private String ovtLibur;


    public String getHari() {
        return hari;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWsIn() {
        return wsIn;
    }

    public String getWsOut() {
        return wsOut;
    }

    public String getLibur() {
        return libur;
    }

    public String getCi() {
        return ci;
    }

    public String getCo() {
        return co;
    }

    public String getAbsence() {
        return absence;
    }

    public String getTrip() {
        return trip;
    }

    public String getoLC() {
        return oLC;
    }

    public String getOvtAwal() {
        return ovtAwal;
    }

    public String getOvtAkhir() {
        return ovtAkhir;
    }

    public String getOvtLibur() {
        return ovtLibur;
    }
}
