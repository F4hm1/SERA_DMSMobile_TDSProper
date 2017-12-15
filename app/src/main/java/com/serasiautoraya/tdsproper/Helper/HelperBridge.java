package com.serasiautoraya.tdsproper.Helper;


import android.graphics.Bitmap;

import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailResponseModel;
import com.serasiautoraya.tdsproper.JourneyOrder.Assigned.AssignedOrderResponseModel;
import com.serasiautoraya.tdsproper.JourneyOrder.PodSubmit.PodStatusResponseModel;
import com.serasiautoraya.tdsproper.Login.LoginResponseModel;
import com.serasiautoraya.tdsproper.OrderHistory.OrderHistoryDetail.OrderHistoryDetailResponseModel;
import com.serasiautoraya.tdsproper.OrderHistory.OrderHistoryResponseModel;
import com.serasiautoraya.tdsproper.RequestHistory.RequestHistoryResponseModel;

import net.grandcentrix.thirtyinch.TiActivity;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public class HelperBridge {

    public static LoginResponseModel sModelLoginResponse;

    public static List<AssignedOrderResponseModel> sActiveOrdersList;
    public static List<AssignedOrderResponseModel> sPlanOutstandingOrdersList;

    public static List<RequestHistoryResponseModel> sCiCoRequestHistoryList;
    public static List<RequestHistoryResponseModel> sOLCRequestHistoryList;
    public static List<RequestHistoryResponseModel> sOvertimeRequestHistoryList;
    public static List<RequestHistoryResponseModel> sAbsenceRequestHistoryList;

    public static List<OrderHistoryResponseModel> sOderHistoryList;

    public static Bitmap sBitmapSignature = null;

    public static ActivityDetailResponseModel sActivityDetailResponseModel;

//    public static ActivityDetailResponseModel sActivityDetailResponseModel = new ActivityDetailResponseModel(
//            63,
//            12,
//            "Photo Capture",
//            "LT66",
//            HelperTransactionCode.TRUE_BINARY,
//            "23-12-2018",
//            "23-12-2018",
//            "",
//            "-6.12314",
//            "112.12314",
//            "Jakarta",
//            HelperTransactionCode.FALSE_BINARY,
//            HelperTransactionCode.FALSE_BINARY,
//            "",
//            null,
//            "Unit Models",
//            "B76765TH",
//            12,
//            "-6.234234",
//            "112.234234",
//            "Location textreal",
//            7,
//            7,
//            "23-12-2018",
//            HelperTransactionCode.FALSE_BINARY,
//            "POD Guide",
//            "Rawamangun Jakarta",
//            "UNJ Jakarta"
//    );

    public static PodStatusResponseModel sPodStatusResponseModel;

    public static String sTempSelectedOrderCode = "";

    public static AssignedOrderResponseModel sAssignedOrderResponseModel;

    public static List<OrderHistoryDetailResponseModel> sOrderHistoryDetailActivityList;

    public static int sUpdateLocationInterval;

    public static int sTempFragmentTarget = 0;

    public static TiActivity sCurrentDetailActivity = null;

    public static boolean sListOrderRetrievalSuccess = true;

    public static int sPlanOrderPositionClicked = -1;

    public static boolean isClickedFromPlanOrder;
}
