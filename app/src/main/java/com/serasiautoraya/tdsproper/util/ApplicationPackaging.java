package com.serasiautoraya.tdsproper.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.serasiautoraya.tdsproper.BuildConfig;
/**
 * Created by Fahmi Hakim on 13/04/2018.
 * for SERA
 */

public class ApplicationPackaging {

    public static String getPackageInfo(){
        return BuildConfig.VERSION_NAME;
    }

    public static int getPackageInfoInt(){
        return BuildConfig.VERSION_CODE;
    }
}
