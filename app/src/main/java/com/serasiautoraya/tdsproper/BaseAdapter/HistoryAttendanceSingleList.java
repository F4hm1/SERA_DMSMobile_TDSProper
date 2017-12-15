package com.serasiautoraya.tdsproper.BaseAdapter;

/**
 * Created by Randi Dwi Nandra on 30/11/2016.
 */
public class HistoryAttendanceSingleList {
    private String tanggal, absenceType, jadwalIn, jadwalOut, clockIn, clockOut;

    public HistoryAttendanceSingleList(String tanggal, String absenceType, String jadwalIn, String jadwalOut, String clockIn, String clockOut) {
        this.tanggal = tanggal;
        this.absenceType = absenceType;
        this.jadwalIn = jadwalIn;
        this.jadwalOut = jadwalOut;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(String absenceType) {
        this.absenceType = absenceType;
    }

    public String getJadwalIn() {
        return jadwalIn;
    }

    public void setJadwalIn(String jadwalIn) {
        this.jadwalIn = jadwalIn;
    }

    public String getJadwalOut() {
        return jadwalOut;
    }

    public void setJadwalOut(String jadwalOut) {
        this.jadwalOut = jadwalOut;
    }

    public String getClockIn() {
        return clockIn;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    public void setClockOut(String clockOut) {
        this.clockOut = clockOut;
    }
}
