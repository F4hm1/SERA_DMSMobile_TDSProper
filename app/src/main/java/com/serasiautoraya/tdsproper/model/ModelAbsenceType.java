package com.serasiautoraya.tdsproper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class ModelAbsenceType {
    @SerializedName("AbsenceCode")
    @Expose
    private String[] absenceCode;

    @SerializedName("AbsenceName")
    @Expose
    private String[] absenceName;

    public String[] getAbsenceCode() {
        return absenceCode;
    }

    public void setAbsenceCode(String[] absenceCode) {
        this.absenceCode = absenceCode;
    }

    public String[] getAbsenceName() {
        return absenceName;
    }

    public void setAbsenceName(String[] absenceName) {
        this.absenceName = absenceName;
    }
}
