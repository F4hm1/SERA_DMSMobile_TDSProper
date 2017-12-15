package com.serasiautoraya.tdsproper.BaseAdapter;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class OrderSingleList {

    private String mSingleListCode, mSingleListHub, mSingleListNextdriver,
            mSingleListOrigin, mSingleListEtd, mSingleListEta,
            mSingleListCustomer, mSingleListUnit, mSingleListDest;

    public OrderSingleList(String mSingleListCode, String mSingleListHub,
                           String mSingleListNextdriver, String mSingleListOrigin,
                           String mSingleListEtd, String mSingleListEta,
                           String mSingleListCustomer, String mSingleListUnit,
                           String mSingleListDest) {
        this.mSingleListCode = mSingleListCode;
        this.mSingleListHub = mSingleListHub;
        this.mSingleListNextdriver = mSingleListNextdriver;
        this.mSingleListOrigin = mSingleListOrigin;
        this.mSingleListEtd = mSingleListEtd;
        this.mSingleListEta = mSingleListEta;
        this.mSingleListCustomer = mSingleListCustomer;
        this.mSingleListUnit = mSingleListUnit;
        this.mSingleListDest = mSingleListDest;
    }

    public String getmSingleListCode() {
        return mSingleListCode;
    }

    public void setmSingleListCode(String mSingleListCode) {
        this.mSingleListCode = mSingleListCode;
    }

    public String getmSingleListHub() {
        return mSingleListHub;
    }

    public void setmSingleListHub(String mSingleListHub) {
        this.mSingleListHub = mSingleListHub;
    }

    public String getmSingleListNextdriver() {
        return mSingleListNextdriver;
    }

    public void setmSingleListNextdriver(String mSingleListNextdriver) {
        this.mSingleListNextdriver = mSingleListNextdriver;
    }

    public String getmSingleListOrigin() {
        return mSingleListOrigin;
    }

    public void setmSingleListOrigin(String mSingleListOrigin) {
        this.mSingleListOrigin = mSingleListOrigin;
    }

    public String getmSingleListEtd() {
        return mSingleListEtd;
    }

    public void setmSingleListEtd(String mSingleListEtd) {
        this.mSingleListEtd = mSingleListEtd;
    }

    public String getmSingleListEta() {
        return mSingleListEta;
    }

    public void setmSingleListEta(String mSingleListEta) {
        this.mSingleListEta = mSingleListEta;
    }

    public String getmSingleListCustomer() {
        return mSingleListCustomer;
    }

    public void setmSingleListCustomer(String mSingleListCustomer) {
        this.mSingleListCustomer = mSingleListCustomer;
    }

    public String getmSingleListUnit() {
        return mSingleListUnit;
    }

    public void setmSingleListUnit(String mSingleListUnit) {
        this.mSingleListUnit = mSingleListUnit;
    }

    public String getmSingleListDest() {
        return mSingleListDest;
    }

    public void setmSingleListDest(String mSingleListDest) {
        this.mSingleListDest = mSingleListDest;
    }
}
