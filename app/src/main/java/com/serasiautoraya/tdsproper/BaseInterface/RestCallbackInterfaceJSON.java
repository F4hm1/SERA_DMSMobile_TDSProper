package com.serasiautoraya.tdsproper.BaseInterface;

import com.android.volley.error.VolleyError;

import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public interface RestCallbackInterfaceJSON {

    void callBackOnSuccess(JSONObject response);

    void callBackOnFail(String response);

    void callBackOnError(VolleyError error);

}
