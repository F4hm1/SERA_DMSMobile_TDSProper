package com.serasiautoraya.tdsproper.util;

import android.os.Environment;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class HelperUrl {
    public static final String DIRECTORY_NAME = "TDS-Proper";
    public static final String SAVE_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/"+DIRECTORY_NAME+"/";


    /**
    * Host
    */
    private static String HOST = "https://damira.sera.astra.co.id/DMSAPI/";


    /**
     * Data Retrieval
     */
    public static String LOGIN = HOST + "RestAPIFront_Login/login/";
    public static String GET_ATTENDANCE_HISTORY = HOST + "RestAPIFront_Report/report_driver/";
    public static String GET_REPORT_REQUEST_HISTORY = HOST + "RestAPIFront_Report/report_driver_request/";
    public static String GET_SERVER_LOCALTIME = "http://api.geonames.org/timezoneJSON";

    /**
    * Data Post
    */
    public static String CICO = HOST + "RestAPIFront_Trxcico/create/";
    public static String CICO_REQUEST = HOST + "RestAPIFront_Trxcico/create_temp/";
    public static String ABSENCE = HOST + "RestAPIFront_Trxabsence/create/";
    public static String CHANGE_PASSWORD = HOST + "RestAPIFront_Changepassword/changepassword/";

    /**
     * Data Delete
     */
    public static String DELETE_REQUEST_CICO = HOST + "RestAPIFront_Trxcico/delete/";
    public static String DELETE_REQUEST_ABSENCE = HOST + "restapifront_trxabsence/delete/";



    /*
    * TODO
    * [DONE] Ganti URL delete request absence, dan test ulang
    * [DONE] Ganti date format jadi indonesiable dd-mm-yyyy, atau dd-mmmm-yyyy
        > [DONE] Membuat method convert date format dari (user form -> server form)
        > [DONE] Membuat method convert date format dari (server form -> user form)
        > [DONE] Mengganti algoritma display local date (u/k konfirmasi) jadi langsung ditampilkan sesuai user format (tanpa di split, dll)
          dan ganti date format menjadi server form sebelum dikirim ke API
    * [DONE] Ganti bahasa pickup kalender jadi bahasa indonesia
    * [DONE] Ganti Id Equipment jadi Username
    * [DONE] Ganti DMS jadi TDS
    * */


}
