package com.serasiautoraya.tdsproper.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class ModelPersonalData {
    @SerializedName("PersonalId")
    @Expose
    private String personalId;

    @SerializedName("PersonalCode")
    @Expose
    private String personalCode;

    @SerializedName("FullName")
    @Expose
    private String fullName;

    @SerializedName("JoinDate")
    @Expose
    private String joinDate;

    @SerializedName("PhotoFront")
    @Expose
    private String photoFront;

    @SerializedName("Position")
    @Expose
    private String position;

    @SerializedName("CompanyCode")
    @Expose
    private String companyCode;

    @SerializedName("CompanyName")
    @Expose
    private String companyName;

    @SerializedName("BranchCode")
    @Expose
    private String branchCode;

    @SerializedName("BranchName")
    @Expose
    private String branchName;

    @SerializedName("PlacementCode")
    @Expose
    private String placementCode;

    @SerializedName("PlacementName")
    @Expose
    private String placementName;

    @SerializedName("PoolCode")
    @Expose
    private String poolCode;

    @SerializedName("PoolName")
    @Expose
    private String poolName;

    @SerializedName("UserIdLevel1Name")
    @Expose
    private String userIdLevel1Name;

    @SerializedName("UserIdLevel2Name")
    @Expose
    private String userIdLevel2Name;

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getPhotoFront() {
        return photoFront;
    }

    public void setPhotoFront(String photoFront) {
        this.photoFront = photoFront;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPlacementCode() {
        return placementCode;
    }

    public void setPlacementCode(String placementCode) {
        this.placementCode = placementCode;
    }

    public String getPlacementName() {
        return placementName;
    }

    public void setPlacementName(String placementName) {
        this.placementName = placementName;
    }

    public String getPoolCode() {
        return poolCode;
    }

    public void setPoolCode(String poolCode) {
        this.poolCode = poolCode;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getUserIdLevel1Name() {
        return userIdLevel1Name;
    }

    public void setUserIdLevel1Name(String userIdLevel1Name) {
        this.userIdLevel1Name = userIdLevel1Name;
    }

    public String getUserIdLevel2Name() {
        return userIdLevel2Name;
    }

    public void setUserIdLevel2Name(String userIdLevel2Name) {
        this.userIdLevel2Name = userIdLevel2Name;
    }
}
