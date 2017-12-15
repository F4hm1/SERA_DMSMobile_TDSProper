package com.serasiautoraya.tdsproper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.util.HelperUtil;

/**
 * Created by Randi Dwi Nandra on 13/12/2016.
 */
public class ModelLoginResponse  {
    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("IdPersonalData")
    @Expose
    private String idPersonalData;

    @SerializedName("AccessApp")
    @Expose
    private String accessApp;

    @SerializedName("AccessB")
    @Expose
    private String accessB;

    @SerializedName("AccessCustomer")
    @Expose
    private String accessCustomer;

    @SerializedName("AccessF")
    @Expose
    private String accessF;

    @SerializedName("AccessM")
    @Expose
    private int accessM;

    @SerializedName("AccessPersonnelSubArea")
    @Expose
    private String accessPersonnelSubArea;

    @SerializedName("HOAdmin")
    @Expose
    private String hOAdmin;

    @SerializedName("SuperAdmin")
    @Expose
    private String superAdmin;

    @SerializedName("Deleted")
    @Expose
    private String deleted;

    @SerializedName("Username")
    @Expose
    private String username;

    @SerializedName("Position")
    @Expose
    private String position;

    @SerializedName("Job")
    @Expose
    private String job;

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("FullName")
    @Expose
    private String fullName;

    @SerializedName("IdUpLevel_1")
    @Expose
    private String idUpLevel_1;

    @SerializedName("IdUpLevel_2")
    @Expose
    private String idUpLevel_2;

    @SerializedName("TypeEmployee")
    @Expose
    private String typeEmployee;

    @SerializedName("EmailLvl_1")
    @Expose
    private String emailLvl_1;

    @SerializedName("EmailLvl_2")
    @Expose
    private String emailLvl_2;

    @SerializedName("FullNamelLvl_1")
    @Expose
    private String fullNamelLvl_1;

    @SerializedName("FullNamelLvl_2")
    @Expose
    private String fullNamelLvl_2;

    @SerializedName("MaxHariRequestDriver")
    @Expose
    private int maxHariRequestDriver;

    @SerializedName("MaxHariRequestAdmin")
    @Expose
    private String maxHariRequestAdmin;

    @SerializedName("MinHariRequestAbsence")
    @Expose
    private String minHariRequestAbsence;

    @SerializedName("PhotoFront")
    @Expose
    private String photoFront;

    @SerializedName("Pool")
    @Expose
    private String pool;

    @SerializedName("Code")
    @Expose
    private String code;

    @SerializedName("CompanyName")
    @Expose
    private String companyName;

    @SerializedName("PoolName")
    @Expose
    private String poolName;

    @SerializedName("SIMEndDate")
    @Expose
    private String sIMEndDate;

    @SerializedName("SIMNumber")
    @Expose
    private String sIMNumber;

    @SerializedName("KTPEndDate")
    @Expose
    private String kTPEndDate;

    @SerializedName("KTPNumber")
    @Expose
    private String kTPNumber;

    @SerializedName("SIMType")
    @Expose
    private String sIMType;

    public String getsIMType() {
        return sIMType;
    }

    public String getMinHariRequestAbsence() {
        return minHariRequestAbsence;
    }

    public String getsIMEndDate() {
        if(sIMEndDate != null){
            if(sIMEndDate.equalsIgnoreCase("")){
                return sIMEndDate;
            }else {
                return HelperUtil.getUserFormDate(sIMEndDate);
            }
        }else {
            return "";
        }
    }

    public String getsIMNumber() {
        return sIMNumber;
    }

    public String getkTPEndDate() {
        if(kTPEndDate != null){
            if(kTPEndDate.equalsIgnoreCase("") || kTPEndDate.equalsIgnoreCase("null")){
                return kTPEndDate;
            }else {
                return HelperUtil.getUserFormDate(kTPEndDate);
            }
        }else {
            return "";
        }
    }

    public String getkTPNumber() {
        return kTPNumber;
    }

    public String getPoolName() {
        return poolName;
    }

    public String getCode() {
        return code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPool() {
        return pool;
    }

    public String getPhotoFront() {
        return photoFront;
    }

    public void setPhotoFront(String photoFront) {
        this.photoFront = photoFront;
    }

    public String getUid() {
        return uid;
    }

    public String getIdPersonalData() {
        return idPersonalData;
    }

    public String getAccessApp() {
        return accessApp;
    }

    public String getAccessB() {
        return accessB;
    }

    public String getAccessCustomer() {
        return accessCustomer;
    }

    public String getAccessF() {
        return accessF;
    }

    public int getAccessM() {
        return accessM;
    }

    public String getAccessPersonnelSubArea() {
        return accessPersonnelSubArea;
    }

    public String gethOAdmin() {
        return hOAdmin;
    }

    public String getSuperAdmin() {
        return superAdmin;
    }

    public String getDeleted() {
        return deleted;
    }

    public String getUsername() {
        return username;
    }

    public String getPosition() {
        if(position.equalsIgnoreCase("Driver")){
            return "Transporter";
        }else {
            return position;
        }
    }

    public String getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdUpLevel_1() {
        return idUpLevel_1;
    }

    public String getIdUpLevel_2() {
        return idUpLevel_2;
    }

    public String getTypeEmployee() {
        return typeEmployee;
    }

    public String getEmailLvl_1() {
        return emailLvl_1;
    }

    public String getEmailLvl_2() {
        return emailLvl_2;
    }

    public String getFullNamelLvl_1() {
        return fullNamelLvl_1;
    }

    public String getFullNamelLvl_2() {
        return fullNamelLvl_2;
    }

    public int getMaxHariRequestDriver() {
        return maxHariRequestDriver;
    }

    public String getMaxHariRequestAdmin() {
        return maxHariRequestAdmin;
    }
}
