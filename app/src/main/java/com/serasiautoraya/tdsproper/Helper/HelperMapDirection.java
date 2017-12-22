package com.serasiautoraya.tdsproper.Helper;

import android.graphics.Color;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by randidwinandra on 15/12/17.
 */

public class HelperMapDirection extends AsyncTask<LatLng, Void, List<LatLng>> {

    String DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json";
    RestConnection mRestConnection;

//    origin=75+9th+Ave+New+York,+NY&
//    destination=MetLife+Stadium+1+MetLife+Stadium+Dr+East+Rutherford,+NJ+07073&
//    departure_time=1541202457&
//    traffic_model=best_guess&
//    key=YOUR_API_KEY


    public HelperMapDirection(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<LatLng> doInBackground(LatLng... params) {
        LatLng start = params[0];
        LatLng end = params[1];

        HashMap<String, String> points = new HashMap<>();
        points.put("origin", start.latitude + "," + start.longitude);
        points.put("destination", end.latitude + "," + end.longitude);
        points.put("key", "AIzaSyBYo0H6af5hRPxoO7pZUr6aWh929-t3MGY");

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                DIRECTIONS_URL,
                new JSONObject(points),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                List<LatLng> list = null;
                                JSONArray routeArray = response.getJSONArray("routes");
                                JSONObject routes = routeArray.getJSONObject(0);
                                JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
                                String encodedString = overviewPolylines.getString("points");
                                list = decodePoly(encodedString);
//                                return list;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

//        if (obj == null) return null;

//        try {
//            List<LatLng> list = null;
//
////            JSONArray routeArray = obj.getJSONArray("routes");
//            JSONObject routes = routeArray.getJSONObject(0);
//            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
//            String encodedString = overviewPolylines.getString("points");
//            list = decodePoly(encodedString);
//
//            return list;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return null;
    }

    @Override
    protected void onPostExecute(List<LatLng> pointsList) {

//        if (pointsList == null) return;
//
//        if (line != null) {
//            line.remove();
//        }
//
//        PolylineOptions options = new PolylineOptions().width(5).color(Color.MAGENTA).geodesic(true);
//        for (int i = 0; i < pointsList.size(); i++) {
//            LatLng point = pointsList.get(i);
//            options.add(point);
//        }
//        line = mMap.addPolyline(options);

    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
