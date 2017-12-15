package com.serasiautoraya.tdsproper.Profiling;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public interface ProfileView extends BaseViewInterface{

    void setProfileContent(
            String nameFp,
            String posisiFp,
            String companyFp,
            String poolNameFp,
            String nrp,
            String fullname,
//          String training,
            String userCostumer,
            String doo,
            String kTPExp,
            String sIMExp,
            String sIMType
    );

    void setProfilePhoto(String url);

    void toggleDriverStatus(boolean isOn);

    void toggleProgressDriverStatusUpdate(boolean isUpdate);
}
