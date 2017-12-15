package com.serasiautoraya.tdsproper.util;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class HelperKey {
    //    Shared Activity (Bundle Intent code)
    public enum TransitionType {
        Explode, Slide, Fade
    }

    public static final String KEY_TRANSITION_TYPE = "TRANSITION_TYPE";
    public static final String KEY_TITLE_ANIM = "TRANSITION_TITLE";

//    Constant Value
    public static final int SAVED_IMAGE_DESIRED_WITDH = 768;

    //Permission Code
    public static final int STORAGE_PERMISSION_GRANTED_CODE = 1001;
    public static final int LOCATION_PERMISSION_GRANTED_CODE = 1002;
    public static final int SYSWINDOWS_PERMISSION_GRANTED_CODE = 1003;
    public static final int VIBRATE_PERMISSION_GRANTED_CODE = 1004;

    //SharedPreferences Code
    public static final String HAS_LOGIN = "LOGGED_IN";
    public static String KEY_USERNAME = "KEY_USERNAME";
    public static String KEY_PASSWORD = "KEY_PASSWORD";
    public static String KEY_NOTIF_ID = "KEY_NOTIF_ID";
    public static String KEY_LOC_LAT = "KEY_LOC_LAT";
    public static String KEY_LOC_LONG = "KEY_LOC_LONG";
    public static String KEY_LOC_ADDRESS = "KEY_LOC_ADDRESS";

    //Activity Code
    public static final int ACTIVITY_PROVE = 1001;
    public static final int ACTIVITY_FIRST_IMAGE_CAPTURE = 1002;
    public static final int ACTIVITY_SECOND_IMAGE_CAPTURE = 1003;
    public static final int ACTIVITY_IMAGE_CAPTURE = 1002;
    public static final int ACTIVITY_IMAGE_PICKER = 1004;
    public static final int ACTIVITY_RESULT_EXPENSES = 1005;

    //Date format
    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";
    public static final String USER_DATE_FORMAT = "dd MMMM yyyy";
    public static final String USER_DATE_FORMAT_SEPARATOR = "-";
    public static final String USER_TIME_FORMAT = "kk:mm:ss";

    //Activity Title
    public static final String EXTRA_KEY_TITLE = "TITLE";
    public static final String TITLE_ACTIVITY_LOADING = "Bukti Loading";
    public static final String TITLE_ACTIVITY_UNLOADING = "Bukti Unloading";
    public static final String TITLE_ACTIVITY_CHECKPOINT= "Bukti Checkpoint";
    public static final String TITLE_ACTIVITY_END_ORDER= "Bukti End Order";
    public static final String TITLE_ACTIVITY_CHANGE_PASS= "Ganti Password";
    public static final String TITLE_ACTIVITY_FATIGUE_INTERVIEW= "Fatigue Interview";
    public static final String TITLE_ACTIVITY_PROFILE= "Profil Transporter";

    //API - Key
    public static String API_KEY = "8d7ca5b010c22997b1f6910b111c8273417ec35a";

    //HTTP Status
    public static final int HTTP_STAT_OK = 200;
    public static final int HTTP_STAT_BADREQ = 400;

    //Generic Status
    public static final String STATUS_SUKSES = "1";
    public static final String STATUS_SUKSES_STRING = "success";
    public static final String STATUS_GAGAL = "0";
    public static final int AUTHORIZED_ACCESS = 1;

    //Static API Code
    public static final String MOBILE_CODE = "1";
    public static final String CLOCK_IN_CODE = "TRXCC_01";
    public static final String CLOCK_OUT_CODE = "TRXCC_02";
    public static final String WFSTATUS_APPROVED = "WFSTS_10";
    public static final String WFSTATUS_PENDING = "WFSTS_01";
    public static final String WFSTATUS_REQUEST_ABSENCE = "WFSTS_02";
    public static final String SUBMIT_TYPE_DEFAULT = "CCSMT_04";
    public static final String SUBMIT_TYPE_ABSENCE = "CCSMT_02";
    public static final String APPROVED_AUTO = "Automatic Approved";

    public static final String WAITING_ACK_CODE = "Waiting ACK";


}
