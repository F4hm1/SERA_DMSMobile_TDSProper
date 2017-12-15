package com.serasiautoraya.tdsproper.ChangePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 24/03/2017.
 */

public class ChangePasswordSendModel extends Model {

    @SerializedName("PersonalId")
    @Expose
    private String PersonalId;

    @SerializedName("OldPassword")
    @Expose
    private String OldPassword;

    @SerializedName("NewPassword")
    @Expose
    private String NewPassword;

    @SerializedName("ConfirmNewPassword")
    @Expose
    private String ConfirmNewPassword;

    public ChangePasswordSendModel(String personalId, String oldPassword, String newPassword, String confirmNewPassword) {
        PersonalId = personalId;
        OldPassword = oldPassword;
        NewPassword = newPassword;
        ConfirmNewPassword = confirmNewPassword;
    }

    public String getPersonalId() {
        return PersonalId;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public String getConfirmNewPassword() {
        return ConfirmNewPassword;
    }
}
