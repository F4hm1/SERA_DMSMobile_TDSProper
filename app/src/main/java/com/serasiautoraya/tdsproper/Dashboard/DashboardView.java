package com.serasiautoraya.tdsproper.Dashboard;

import android.support.v4.app.Fragment;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public interface DashboardView extends BaseViewInterface {

    void initializeMenuAccess();

    void changeFragment(Fragment targetFragment);

    void changeActivity(Class targetActivity);

    Fragment getActiveFragment(int idNavItem);

    Class getTargetActivityClass(int idNavItem);

    void exitApplication();

    void logout();

    void onProfileDetailClicked();

    void setDrawerProfile(String name, String position, String urlPhoto);

    void toggleMenu(
            boolean requestCiCo,
            boolean reportCiCo,
            boolean requestAbsence,
            boolean reportAbsence,
            boolean requestOLCTrip,
            boolean reportOLCTrip,
            boolean requestOvertime,
            boolean reportOvertime,
            boolean reportServiceHour,
            boolean requestExpense,
            boolean orderActive
    );
}

