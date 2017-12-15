package com.serasiautoraya.tdsproper.util;

import android.graphics.Bitmap;

import com.serasiautoraya.tdsproper.BaseAdapter.GeneralSingleList;
import com.serasiautoraya.tdsproper.BaseAdapter.OrderSingleList;
import com.serasiautoraya.tdsproper.model.ModelActivityJourney;
import com.serasiautoraya.tdsproper.model.ModelLoginResponse;
import com.serasiautoraya.tdsproper.model.ModelReportResponse;
import com.serasiautoraya.tdsproper.model.ModelRequestReportResponse;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class HelperBridge {
    //Model
    public static int maxRequest; //temporary, nanti di parsing dari model Login

    //List
    public static OrderSingleList ORDER_CLICKED;
    public static GeneralSingleList HISTORY_ORDER_CLICKED;
    public static List<GeneralSingleList> sActiveOrdersList;
    public static List<GeneralSingleList> sPlanOrdersList;

    //Bitmap
    public static Bitmap sFirstBitmap = null;
    public static Bitmap sSecondBitmap = null;
    public static Bitmap sTtdBitmap = null;

    //Temporary model data
    public static ModelActivityJourney MODEL_ACTIVITY_SELECTED;
    public static ModelLoginResponse MODEL_LOGIN_DATA;
    public static ModelReportResponse[] MODEL_REPORT_ARRAY;
    public static ModelRequestReportResponse[] MODEL_REQUEST_CICO_REPORT_ARRAY;
    public static ModelRequestReportResponse[] MODEL_REQUEST_ABSENCE_REPORT_ARRAY;
    public static Bitmap sProfilePhoto;

    //Permission Status
    public static boolean sLocationGranted = false;
    public static GPSTracker gps;
}
