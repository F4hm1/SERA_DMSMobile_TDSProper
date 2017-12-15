package com.serasiautoraya.tdsproper.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.cache.plus.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Randi Dwi Nandra on 28/11/2016.
 */
public class VolleyUtil {
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;


    private VolleyUtil() {
        // no instances
    }

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public static ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }
}
