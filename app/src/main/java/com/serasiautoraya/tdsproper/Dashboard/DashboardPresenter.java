package com.serasiautoraya.tdsproper.Dashboard;

import android.support.annotation.NonNull;

import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Fatigue.FatigueActivity;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.Login.LoginActivity;
import com.serasiautoraya.tdsproper.Profiling.ProfileActivity;
import com.serasiautoraya.tdsproper.R;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public class DashboardPresenter extends TiPresenter<DashboardView> {

    private SharedPrefsModel mSharedPrefsModel;

    public DashboardPresenter(SharedPrefsModel sharedPrefsModel) {
        this.mSharedPrefsModel = sharedPrefsModel;
    }

    @Override
    protected void onAttachView(@NonNull final DashboardView view) {
        super.onAttachView(view);
        getView().initialize();
        getView().toggleMenu(
                HelperBridge.sModelLoginResponse.getRequestCiCo().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportCiCo().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getRequestAbsence().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportAbsence().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getRequestOLCTrip().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportOLCTrip().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getRequestOvertime().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportOvertime().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getReportServiceHour().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getRequestExpense().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sModelLoginResponse.getOrderActive().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)
        );
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HelperKey.SERVER_DATE_FORMAT);
//        String dateToday = simpleDateFormat.format(calendar.getTime());
//        if(mSharedPrefsModel.get(HelperKey.KEY_LAST_CLOCKIN, "").equalsIgnoreCase(dateToday)
//                && !mSharedPrefsModel.get(HelperKey.KEY_LAST_FATIGUE_INTERVIEW, "").equalsIgnoreCase(dateToday)){
        //getView().changeFragment(getView().getActiveFragment(HelperBridge.sTempFragmentTarget));
        /*if (HelperBridge.sModelLoginResponse.getOrderActive().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)){
            getView().changeFragment(getView().getActiveFragment(R.id.nav_cico_request));
        } else {
            getView().changeFragment(getView().getActiveFragment(HelperBridge.sTempFragmentTarget));
        }*/

        getView().changeFragment(getView().getActiveFragment(HelperBridge.sModelLoginResponse.getOrderActive().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ? R.id.nav_cico_request : HelperBridge.sTempFragmentTarget));



        HelperBridge.sTempFragmentTarget = HelperBridge.sModelLoginResponse.getOrderActive().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ? R.id.nav_cico_request : 0;

        getView().setDrawerProfile(HelperBridge.sModelLoginResponse.getFullname(), HelperBridge.sModelLoginResponse.getCompany(), HelperBridge.sModelLoginResponse.getPhotoFront());
        if (!HelperBridge.sModelLoginResponse.getIsNeedFatigueInterview().equalsIgnoreCase("0")) {
            getView().changeActivity(FatigueActivity.class);
        }
//        }
    }

    public void onNavigationItemSelectedForFragment(int id) {
        getView().changeFragment(getView().getActiveFragment(id));
        HelperBridge.sTempFragmentTarget = 0;
    }

    public void onNavigationItemSelectedForActivity(int id) {
        getView().changeActivity(getView().getTargetActivityClass(id));
    }

    public void logout() {
        mSharedPrefsModel.clearAll();
        getView().changeActivity(LoginActivity.class);
        getView().showToast("Berhasil keluar");
    }

    public void loadDetailProfile() {
        getView().changeActivity(ProfileActivity.class);
    }

}
