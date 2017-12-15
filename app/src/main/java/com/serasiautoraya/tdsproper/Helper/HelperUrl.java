package com.serasiautoraya.tdsproper.Helper;

/**
 * Created by Randi Dwi Nandra on 22/03/2017.
 */

public class HelperUrl {

    /**
     * OCP Apim
     */

    //Production
//    public static final String OCP_APIM_KEY = "8c25a9e79a3d4ba3a1e52ccbff541bc8";

    //UAT Test
    public static final String OCP_APIM_KEY = "29d4c537dd0840eeb72dfbb99fa7e43d";

    /**
     * Host
     */
            //UAT
//    private static final String HOST = "http://seradmapimanagementdev.azure-api.net/";

            //UAT-Test
//    private static final String HOST = "https://drivermanagementapimdev.azure-api.net/";

            //Production
//    private static final String HOST = "https://dmapimanagement.azure-api.net/";

            //Development
    private static final String HOST = "http://drivermanagementapidev.azurewebsites.net/";

    public static final String GET_SERVER_LOCALTIME = "http://api.geonames.org/timezoneJSON";

    public static final String POST_LOGIN = HOST + "auth/login/";

    public static final String GET_ATTENDANCE_HISTORY = HOST + "attendance/history/";

    public static final String GET_REQUEST_HISTORY = HOST + "attendance/requesthistory/";

    public static final String POST_CICO_REALTIME = HOST + "attendance/cicorealtime/";

    public static final String POST_CICO_REQUEST = HOST + "attendance/cico/";

    public static final String DELETE_CANCEL_CICO = HOST + "attendance/cico/";

    public static final String POST_ABSENCE_TEMPORARY = HOST + "attendance/absencetemp/";

    public static final String POST_ABSENCE = HOST + "attendance/absence/";

    public static final String DELETE_ABSENCE = HOST + "attendance/absencetrx/";

    public static final String PUT_CHANGE_PASSWORD = HOST + "auth/password/";

    public static final String GET_ASSIGNED_ORDER = HOST + "order/assigned/";

    public static final String PUT_ACKNOWLEDGE_ORDER = HOST + "order/acknowledge/";

    public static final String PUT_LOCATION_UPDATE = HOST + "auth/location/";

    public static final String GET_ORDER_ACTIVITY = HOST + "order/activity/";

    public static final String PUT_STATUS_UPDATE = HOST + "order/statusupdate/";

    public static final String POST_FATIGUE_INTERVIEW = HOST + "fatigue/interview/";

    public static final String POST_OVERTIME = HOST + "attendance/overtime/";

    public static final String GET_OVERTIME_AVAILABLE = HOST + "attendance/overtimechecking/";

    public static final String DELETE_OVERTIME = HOST + "attendance/overtime/";

    public static final String POST_OLCTRIP = HOST + "attendance/olctrip/";

    public static final String DELETE_OLCTRIP = HOST + "attendance/olctrip/";

    public static final String GET_ORDER_HISTORY = HOST + "order/history/";

    public static final String GET_WSINOUT_HISTORY = HOST + "attendance/reportrecap/";

    public static final String GET_ORDER_HISTORY_DETAIL = HOST + "order/detailhistory/";

    public static final String GET_EXPENSE_AVAILABLE = HOST + "order/expensechecking/";

    public static final String GET_EXPENSE_AVAILABLE_ORDER = HOST + "order/expensechecking/";

    public static final String POST_EXPENSE = HOST + "order/expense/";

    public static final String GET_EXPENSE_INFO = HOST + "order/expenseinfo/";

    public static final String GET_QUESTIONNAIRE = HOST + "training/questionnaire/";

    public static final String POST_QUESTIONNAIRE_ANSWER = HOST + "training/questionnaireanswer/";

    public static final String POST_POD_PHOTO = HOST + "order/uploadpod/";

    public static final String PUT_POD = HOST + "order/updatepod";

    public static final String GET_POD_STATUS = HOST + "order/statuspod";

    public static final String GET_SERVICE_HOUR_HISTORY = HOST + "order/servicehour/";

    public static final String PUT_DRIVER_STATUS = HOST + "personal/drivertoggle/";

    public static final String GET_DRIVER_STATUS = HOST + "personal/drivertoggle/";
}
