package com.serasiautoraya.tdsproper.RestClient;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.cache.DiskBasedCache;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.GsonRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.util.LocationServiceUtil;
import com.serasiautoraya.tdsproper.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class RestConnection<F> {

    private static RestConnection mRestConnection;
    private RequestQueue mRequestQueue;
    private int mStatusCode;
    private Context mContext;

    public RestConnection(Context context) {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(context);
            this.mContext = context;
        }
    }

    int MAX_SERIAL_THREAD_POOL_SIZE = 1;
    final int MAX_CACHE_SIZE = 2 * 1024 * 1024; //2 MB

    private RequestQueue prepareSerialRequestQueue(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), MAX_CACHE_SIZE);
        Network network = getNetwork();
        return new RequestQueue(cache, network, MAX_SERIAL_THREAD_POOL_SIZE);
    }

    private static Network getNetwork() {
        HttpStack stack;
        String userAgent = "volley/0";
        if (Build.VERSION.SDK_INT >= 9) {
            stack = new HurlStack();
        } else {
            stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }
        return new BasicNetwork(stack);
    }

    public void postDataSerial(String transactionToken, String url, HashMap<String, String> params, RestCallbackInterfaceJSON restCallback) {
        final RestCallbackInterfaceJSON restcall = restCallback;
        final String token = transactionToken;
        Log.d("POST_TAGS", params.toString());
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                        } else {
                            try {
                                restcall.callBackOnFail(response.getString("responseText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
                if (!token.equalsIgnoreCase("")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        RequestQueue requestQueueSerail = this.prepareSerialRequestQueue(mContext);
        requestQueueSerail.add(request);
    }

    public void postData(String transactionToken, String url, HashMap<String, String> params, RestCallbackInterfaceJSON restCallback) {
        final RestCallbackInterfaceJSON restcall = restCallback;
        final String token = transactionToken;
        Log.d("POST_TAGS", params.toString());
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                            Log.d("FAIL_API", "SUCCESS JSON");
                        } else {
                            try {
                                restcall.callBackOnFail(response.getString("responseText"));
                                Log.d("FAIL_API", "FAIL" + response.getString("responseText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("FAIL_API", "Catch:" + e.getMessage());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                        Log.d("FAIL_API", "ERROR:" + error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
                if (!token.equalsIgnoreCase("")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

    public void postLoginData(String transactionToken, String url, HashMap<String, String> params, RestCallbackInterfaceJSON restCallback) {
        final RestCallbackInterfaceJSON restcall = restCallback;
        final String token = transactionToken;
        Log.d("POST_TAGS", params.toString());
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                            Log.d("FAIL_API", "SUCCESS JSON");
                        } else {
                            try {
                                restcall.callBackOnFail(response.getString("responseText"));
                                Log.d("FAIL_API", "FAIL" + response.getString("responseText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("FAIL_API", "Catch:" + e.getMessage());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                        Log.d("FAIL_API", "ERROR:" + error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String versionNotValid = "";
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        JSONArray jsonArr = jsonResponse.getJSONArray("data");
                        versionNotValid = jsonArr.getJSONObject(0).getString("IsVersionValidate");
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (versionNotValid.equals("1")){
                        VolleyError error = new VolleyError(responseText+":1");
                        volleyError = error;
                    } else {
                        VolleyError error = new VolleyError(responseText+":"+versionNotValid);
                        volleyError = error;
                    }
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
                if (!token.equalsIgnoreCase("")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        mRequestQueue.add(request);
    }


    public void postDataList(String transactionToken, String url, HashMap<String, Object> params, RestCallbackInterfaceJSON restCallback) {
        final RestCallbackInterfaceJSON restcall = restCallback;
        final String token = transactionToken;
        Log.d("POST_TAGS", params.toString());
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                            Log.d("FAIL_API", "SUCCESS JSON");
                        } else {
                            try {
                                restcall.callBackOnFail(response.getString("responseText"));
                                Log.d("FAIL_API", "FAIL" + response.getString("responseText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("FAIL_API", "Catch:" + e.getMessage());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                        Log.d("FAIL_API", "ERROR:" + error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
                if (!token.equalsIgnoreCase("")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

    public void getServerTime(TimeRestCallBackInterface restCallback) {
        final TimeRestCallBackInterface restcall = restCallback;
        String tempLatitude = "";
        String tempLongitude = "";
        String tempAddress = "";
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }

        if ((LocationServiceUtil.getLocationManager(mContext).getLastLocation() != null) &&
                LocationServiceUtil.getLocationManager(mContext).isGPSEnabled()) {
            tempLatitude = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocation().getLatitude();
            tempLongitude = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocation().getLongitude();
            tempAddress = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocationName();
        } else {
            restCallback.callBackOnFail("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
            return;
        }

        final String latitude = tempLatitude;
        final String longitude = tempLongitude;
        final String address = tempAddress;

        HashMap<String, String> params = new HashMap<>();
        params.put("lat", tempLatitude);
        params.put("lng", tempLongitude);
        params.put("username", "randinandra");

        GsonRequest<TimeRESTResponseModel> request = new GsonRequest<TimeRESTResponseModel>(
                Request.Method.GET,
                HelperUrl.GET_SERVER_LOCALTIME,
                TimeRESTResponseModel.class,
                null,
                params,
                new Response.Listener<TimeRESTResponseModel>() {
                    @Override
                    public void onResponse(TimeRESTResponseModel response) {
                        if (response != null) {

                            restcall.callBackOnSuccess(response, latitude, longitude, address);
                        } else {
                            restcall.callBackOnFail("Terjadi kesalahan, harap periksa koneksi Anda, kemudian coba kembali");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail("Terjadi kesalahan, harap periksa koneksi Anda, kemudian coba kembali");
                    }
                }
        );
        request.setShouldCache(false);
        mRequestQueue.add(request);
    }


    public void getExpenseChecking(String transactionToken, String url, HashMap<String, String> params, RestCallBackInterfaceModel restCallBackInterfaceModel) {
        final RestCallBackInterfaceModel restcall = restCallBackInterfaceModel;
//        final String token = transactionToken;
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
        if (!transactionToken.equalsIgnoreCase("")) {
            headers.put("Authorization", transactionToken);
        }

        GsonRequest<BaseResponseModel> request = new GsonRequest<BaseResponseModel>(
                Request.Method.GET,
                url,
                BaseResponseModel.class,
                headers,
                params,
                new Response.Listener<BaseResponseModel>() {
                    @Override
                    public void onResponse(BaseResponseModel response) {
                        if (mStatusCode == 200) {
                            if(response !=null) {
                                restcall.callBackOnSuccess(response);
                                Log.d("FAIL_API", "SUCCESS:" + response.getResponseText());
                            }
                        } else {
                            restcall.callBackOnFail(response.getResponseText());
                            Log.d("FAIL_API", "FAIL:" + response.getResponseText());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                        Log.d("FAIL_API", "ERROR:" + error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }


            @Override
            protected Response<BaseResponseModel> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                Gson gson = new Gson();
                BaseResponseModel baseResponseModel = null;
                try {
                    obj = new JSONObject(jsonString);
                    baseResponseModel = Model.getModelInstanceFromString(jsonString, BaseResponseModel.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(baseResponseModel, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setShouldCache(false);
        mRequestQueue.add(request);
    }


    public void getOlcTripChecking(String transactionToken, String url, HashMap<String, String> params, RestCallBackInterfaceModel restCallBackInterfaceModel) {
        final RestCallBackInterfaceModel restcall = restCallBackInterfaceModel;
//        final String token = transactionToken;
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
        if (!transactionToken.equalsIgnoreCase("")) {
            headers.put("Authorization", transactionToken);
        }

        GsonRequest<BaseResponseModel> request = new GsonRequest<BaseResponseModel>(
                Request.Method.GET,
                url,
                BaseResponseModel.class,
                headers,
                params,
                new Response.Listener<BaseResponseModel>() {
                    @Override
                    public void onResponse(BaseResponseModel response) {
                        if (mStatusCode == 200) {
                            if(response !=null) {
                                restcall.callBackOnSuccess(response);
                                Log.d("FAIL_API", "SUCCESS:" + response.getResponseText());
                            }
                        } else {
                            restcall.callBackOnFail(response.getResponseText());
                            Log.d("FAIL_API", "FAIL:" + response.getResponseText());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                        Log.d("FAIL_API", "ERROR:" + error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }


            @Override
            protected Response<BaseResponseModel> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                Gson gson = new Gson();
                BaseResponseModel baseResponseModel = null;
                try {
                    obj = new JSONObject(jsonString);
                    baseResponseModel = Model.getModelInstanceFromString(jsonString, BaseResponseModel.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(baseResponseModel, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setShouldCache(false);
        mRequestQueue.add(request);
    }


    public void getData(String transactionToken, String url, HashMap<String, String> params, RestCallBackInterfaceModel restCallBackInterfaceModel) {
        final RestCallBackInterfaceModel restcall = restCallBackInterfaceModel;
//        final String token = transactionToken;
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
        if (!transactionToken.equalsIgnoreCase("")) {
            headers.put("Authorization", transactionToken);
        }

        GsonRequest<BaseResponseModel> request = new GsonRequest<BaseResponseModel>(
                Request.Method.GET,
                url,
                BaseResponseModel.class,
                headers,
                params,
                new Response.Listener<BaseResponseModel>() {
                    @Override
                    public void onResponse(BaseResponseModel response) {
                        if (mStatusCode == 200) {
                            if(response !=null) {
                                restcall.callBackOnSuccess(response);
                                Log.d("FAIL_API", "SUCCESS:" + response.getResponseText());
                            }
                        } else {
                            restcall.callBackOnFail(response.getResponseText());
                            Log.d("FAIL_API", "FAIL:" + response.getResponseText());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                        Log.d("FAIL_API", "ERROR:" + error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
//                if (!transactionToken.equalsIgnoreCase("")) {
//                    headers.put("Authorization", transactionToken);
//                }
//                return headers;
//            }

            @Override
            protected Response<BaseResponseModel> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                Gson gson = new Gson();
                BaseResponseModel baseResponseModel = null;
                try {
                    obj = new JSONObject(jsonString);
                    baseResponseModel = Model.getModelInstanceFromString(jsonString, BaseResponseModel.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(baseResponseModel, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

    public void deleteData(String transactionToken, String url, HashMap<String, String> params, RestCallBackInterfaceModel restCallBackInterfaceModel) {
        url += "?";
        for (String key : params.keySet()) {
            url += key + "=" + params.get(key) + "&";
        }
        url = url.substring(0, url.length() - 1);

        final RestCallBackInterfaceModel restcall = restCallBackInterfaceModel;
        HashMap<String, String> params2 = new HashMap<>();
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
        if (!transactionToken.equalsIgnoreCase("")) {
            headers.put("Authorization", transactionToken);
        }

        GsonRequest<BaseResponseModel> request = new GsonRequest<BaseResponseModel>(
                Request.Method.DELETE,
                url,
                BaseResponseModel.class,
                headers,
                params2,
                new Response.Listener<BaseResponseModel>() {
                    @Override
                    public void onResponse(BaseResponseModel response) {
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                            Log.d("FAIL_API", "SUCCESS:" + response.getResponseText());
                        } else {
                            Log.d("FAIL_API", "FAIL:" + response.getResponseText());
                            restcall.callBackOnFail(response.getResponseText());
                        }
//                        if (response != null) {
//                            if(mStatusCode)
//                            restcall.callBackOnSuccess(response);
//                        } else {
//                            restcall.callBackOnFail(response.getResponseText());
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ANJIRRRRS", "errors: " + error.getMessage());
                        Log.d("FAIL_API", "ERROR:" + error.getMessage());
                        restcall.callBackOnFail(error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

            @Override
            protected Response<BaseResponseModel> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String json = new String(response.data);
                return Response.success(
                        Model.getModelInstanceFromString(json, BaseResponseModel.class), HttpHeaderParser.parseCacheHeaders(response)
                );
            }
        };
        request.setShouldCache(false);
        mRequestQueue.add(request);


//        final RestCallbackInterfaceJSON restcall = restCallBackInterfaceModel;
//        final String transactionTokenVar = transactionToken;
//        final HashMap<String, String> paramsVar = params;
//
//        HttpStack httpStack;
//        httpStack = new OverrideHurlStack();
//
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext, httpStack);
//
//        StringRequest request = new StringRequest(
//                Request.Method.DELETE,
//                url,
//                new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonResponse = new JSONObject(response);
//                    if(mStatusCode == 200){
//                        restcall.callBackOnSuccess(jsonResponse);
//                    }else {
//                        try {
//                            restcall.callBackOnFail(jsonResponse.getString("responseText"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                restcall.callBackOnFail(error.getMessage());
//            }
//        })
//        {
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                mStatusCode = response.statusCode;
//                String jsonString = new String(response.data);
//                return  Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
//            }
//
//            @Override
//            public String getBodyContentType()
//            {
//                return "application/json";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//
//                headers.put("Content-Type", "application/json");
//                headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
//                if(!transactionTokenVar.equalsIgnoreCase("")){
//                    headers.put("Authorization", transactionTokenVar);
//                }
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams()
//            {
//                return paramsVar;
//            }
//
//            @Override
//            protected VolleyError parseNetworkError(VolleyError volleyError) {
//                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
//                    JSONObject jsonResponse = null;
//                    String responseText = "Terjadi Kesalahan";
//                    try {
//                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data, HttpHeaderParser.parseCharset(volleyError.networkResponse.headers)));
//                        responseText = jsonResponse.getString("responseText");
//                    } catch (UnsupportedEncodingException e) {
//                        return new VolleyError(e.getMessage());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    VolleyError error = new VolleyError(responseText);
//                    volleyError = error;
//                }
//                return volleyError;
//            }
//        };
//
//        request.setShouldCache(false);
//        requestQueue.add(request);
    }

    public void putData(String transactionToken, String url, HashMap<String, String> params, RestCallbackInterfaceJSON restCallback) {
        final RestCallbackInterfaceJSON restcall = restCallback;
        final String token = transactionToken;
        if (NetworkUtil.LAST_CONNECTION_NETWORK_STATUS == false) {
            restcall.callBackOnFail("Pastikan terdapat koneksi internet, kemudian silahkan coba kembali");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ANJIRRRRS", "code: " + mStatusCode);
                        if (mStatusCode == 200) {
                            restcall.callBackOnSuccess(response);
                            Log.d("FAIL_API", "Success:" + response.toString());
                        } else {
                            try {
                                restcall.callBackOnFail(response.getString("responseText"));
                                Log.d("FAIL_API", response.getString("responseText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("FAIL_API", "Catch:" + e.getMessage());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        restcall.callBackOnFail(error.getMessage());
                        Log.d("FAIL_API", "Success:" + error.getMessage());
                    }
                }
        ) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    JSONObject jsonResponse = null;
                    String responseText = "Terjadi Kesalahan";
                    try {
                        jsonResponse = new JSONObject(new String(volleyError.networkResponse.data));
                        responseText = jsonResponse.getString("responseText");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VolleyError error = new VolleyError(responseText);
                    volleyError = error;
                }
                return volleyError;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Key", HelperUrl.OCP_APIM_KEY);
                if (!token.equalsIgnoreCase("")) {
                    headers.put("Authorization", token);
                }
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                String jsonString = new String(response.data);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("FAIL_API", "code-1: " + mStatusCode);
                Log.d("FAIL_API", "code-2: " + obj.toString());
                return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        mRequestQueue.add(request);
    }

    public LocationModel getCurrentLocation() {

        String tempLatitude;
        String tempLongitude;
        String tempAddress;

        if ((LocationServiceUtil.getLocationManager(mContext).getLastLocation() != null) && LocationServiceUtil.getLocationManager(mContext).isGPSEnabled()) {
            tempLatitude = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocation().getLatitude();
            tempLongitude = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocation().getLongitude();
            tempAddress = "" + LocationServiceUtil.getLocationManager(mContext).getLastLocationName();
        } else {
            tempLatitude = "null";
            tempLongitude = "null";
            tempAddress = "null";
        }

        return new LocationModel(tempLongitude, tempLatitude, tempAddress);
    }

    public static String getTimeZoneID(TimeRESTResponseModel timeRESTResponseModel) {
        String result = "";
        switch (timeRESTResponseModel.getGmtOffset()) {
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

    public static String getTimeZoneIdByString(String offset) {
        String result = "";
        switch (offset) {
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

    public static String getUTCTimeStamp(TimeRESTResponseModel timeRESTResponseModel) {
        try {
            Date dateBase = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeRESTResponseModel.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String result = sdf.format(dateBase);
            Log.d("TIMEUTC", result);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return timeRESTResponseModel.getTime();
        }
    }

    public static String getCustomTimeZoneTimeStamp(String omServerDate, String timeZoneRaw, String offset) {
        try {
            SimpleDateFormat sdfUtc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdfUtc.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date dateBase = sdfUtc.parse(omServerDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone(timeZoneRaw));
            String result = sdf.format(dateBase);
            return result + " " + getTimeZoneIdByString(offset);
        } catch (ParseException e) {
            e.printStackTrace();
            return omServerDate + " GMT+00";
        }
    }

}

