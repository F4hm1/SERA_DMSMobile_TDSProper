package com.serasiautoraya.tdsproper.BaseAdapter;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class GeneralSingleList {
    private String tittle, information, status;

    public GeneralSingleList(String tittle, String information, String status){
        this.tittle = tittle;
        this.information = information;
        this.status = status;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
