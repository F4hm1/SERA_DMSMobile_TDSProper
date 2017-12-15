package com.serasiautoraya.tdsproper.BaseInterface;

import com.serasiautoraya.tdsproper.BaseModel.TimeRESTResponseModel;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public interface TimeRestCallBackInterface {

    void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address);

    void callBackOnFail(String message);
}
