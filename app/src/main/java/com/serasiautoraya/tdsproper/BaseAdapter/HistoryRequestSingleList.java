package com.serasiautoraya.tdsproper.BaseAdapter;

/**
 * Created by Randi Dwi Nandra on 09/01/2017.
 */
public class HistoryRequestSingleList {

    private String id, date, information, status;

    public HistoryRequestSingleList(String id, String date, String information, String status) {
        this.id = id;
        this.date = date;
        this.information = information;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getInformation() {
        return information;
    }

    public String getStatus() {
        return status;
    }
}
