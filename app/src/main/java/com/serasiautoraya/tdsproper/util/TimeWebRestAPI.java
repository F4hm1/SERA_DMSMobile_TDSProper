package com.serasiautoraya.tdsproper.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUrl;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.CustomListener.ServerCallBack;
import com.serasiautoraya.tdsproper.model.ModelTimeRESTResponse;
import com.serasiautoraya.tdsproper.model.VolleyUtil;

import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 18/01/2017.
 */
public class TimeWebRestAPI {
    private static TimeWebRestAPI timeWebRestAPI = null;
    private static Context sContext;
    private static RequestQueue mqueue;
    private static ModelTimeRESTResponse resultResponse = null;

    public static TimeWebRestAPI getInstance(Context context) {
        if (timeWebRestAPI == null) {
            timeWebRestAPI = new TimeWebRestAPI();
            mqueue = VolleyUtil.getRequestQueue();
        }
        sContext = context;
        return timeWebRestAPI;
    }


    public static ModelTimeRESTResponse getResultResponse() {
        return resultResponse;
    }

    public void getResponse(final ServerCallBack callback) {
        String url = HelperUrl.GET_SERVER_LOCALTIME;
        final ProgressDialog loading = ProgressDialog.show(sContext, sContext.getResources().getString(R.string.prog_msg_loadingjam),
                sContext.getResources().getString(R.string.prog_msg_wait), true, false);

        String lat = "";
        String lng = "";

//        if(HelperBridge.gps.canGetLocation()){
//            if(HelperBridge.gps.getLatitude() > 0 || HelperBridge.gps.getLatitude() < 0){
//                lat = HelperBridge.gps.getLatitude()+"";
//                lng = HelperBridge.gps.getLongitude()+"";
//            }
//        }

//        if(lat.equalsIgnoreCase("")){
            if((LocationServiceUtil.getLocationManager(sContext).getLastLocation() != null) &&
                    LocationServiceUtil.getLocationManager(sContext).isGPSEnabled()){
                lat = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLatitude();
                lng = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLongitude();
            }else{
                HelperUtil.showSimpleAlertDialogCustomTitle("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.", sContext, "Perhatian");
                loading.dismiss();
                return;
            }
//        }

//        String lat = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLatitude();
//        String lng = ""+ LocationServiceUtil.getLocationManager(sContext).getLastLocation().getLongitude();

        ModelTimeRESTResponse result = null;
        HashMap<String, String> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("username", "randinandra");

        GsonRequest<ModelTimeRESTResponse> request = new GsonRequest<ModelTimeRESTResponse>(
                Request.Method.GET,
                url,
                ModelTimeRESTResponse.class,
                null,
                params,
                new Response.Listener<ModelTimeRESTResponse>() {
                    @Override
                    public void onResponse(ModelTimeRESTResponse response) {
                        loading.dismiss();
                        if (response != null) {
                            callback.onSuccessModelTimeRESTResponse(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        HelperUtil.showSimpleAlertDialog(sContext.getResources().getString(R.string.err_msg_general), sContext);
                    }
                }
        );
        request.setShouldCache(false);
        mqueue.add(request);
    }

    public static String getTimeZoneID(ModelTimeRESTResponse modelTimeRESTResponse){
        String result = "";
        switch (modelTimeRESTResponse.getGmtOffset()){
            case "7":
                result = "WIB";
                break;
            case "8":
                result = "WITA";
                break;
            case "9":
                result = "WIT";
                break;
        }
        return result;
    }
}
