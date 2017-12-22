package com.serasiautoraya.tdsproper.CustomView;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.serasiautoraya.tdsproper.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by randidwinandra on 18/12/17.
 */

public class MapJourneyOrder implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private GoogleMap mMap;
    private Polyline line;
    private RequestQueue mRequestQueue;
    private LatLngBounds bounds;
    private LatLng mToLatLng;
    private LatLng mFromLatLng;
    private SupportMapFragment mapFragment;
    private Context context;

    public MapJourneyOrder(SupportMapFragment mapFragment, Context context) {
        this.context = context;
        this.mapFragment = mapFragment;
        this.mapFragment.getMapAsync(this);
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(this.context);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        generateRoute(
//                new LatLng(-6.1498806, 106.8829404),
//                new LatLng(-6.1951641, 106.8175655),
//                "Grha SERA, Jl. Mitra Sunter Bulevard No.90",
//                "Grand Indonesia, Jalan M.H. Thamrin No.1, Menteng, Kebon Melati"
//        );
        mMap.setOnMapLoadedCallback(this);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //TODO request permission here
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }

    public void generateRoute(LatLng fromLatlng, LatLng toLatlng, String fromTitle, String toTitle) {
        mToLatLng = toLatlng;
        mFromLatLng = fromLatlng;
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(mToLatLng)
                .title(toTitle)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.addMarker(new MarkerOptions()
                .position(mFromLatLng)
                .title(fromTitle)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        generateDirection(mFromLatLng, mToLatLng);
    }

    private void generateDirection(LatLng start, LatLng end) {
        String DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json";
        final LatLng fStart = start;
        final LatLng fEnd = end;
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                DIRECTIONS_URL,
                null,
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
                                showRoutes(list);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("MAPMAP", "success");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MAPMAP", "err: " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> reqParams = new HashMap<>();
                reqParams.put("origin", fStart.latitude + "," + fStart.longitude);
                reqParams.put("destination", fEnd.latitude + "," + fEnd.longitude);
                reqParams.put("key", "AIzaSyDCvwRmK8GBHiwmRdF7QsTJJJbT0poDUkc");
                return reqParams;
            }
        };
        getRequest.setShouldCache(false);
        mRequestQueue.add(getRequest);
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

    private void showRoutes(List<LatLng> pointsList) {

        if (pointsList == null) return;

        if (line != null) {
            line.remove();
        }

        PolylineOptions options = new PolylineOptions().width(13).color(ContextCompat.getColor(context, R.color.colorPrimary)).geodesic(true);
        for (int i = 0; i < pointsList.size(); i++) {
            LatLng point = pointsList.get(i);
            options.add(point);
        }
        line = mMap.addPolyline(options);
        if (mFromLatLng != null && mFromLatLng != null) {
            zoomToBounds();
        }
    }

    @Override
    public void onMapLoaded() {

    }

    private void zoomToBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(mFromLatLng);
        builder.include(mToLatLng);
        bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
