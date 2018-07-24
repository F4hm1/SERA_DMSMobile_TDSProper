package com.serasiautoraya.tdsproper.Dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.serasiautoraya.tdsproper.Absence.AbsenceRequestFragment;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.ChangePassword.ChangePasswordActivity;
import com.serasiautoraya.tdsproper.CiCo.CiCoFragment;
import com.serasiautoraya.tdsproper.CiCo.CiCoRequestFragment;
import com.serasiautoraya.tdsproper.ExpensesRequest.ExpenseRequestFragment;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.JourneyOrder.Assigned.AssignedFragment;
import com.serasiautoraya.tdsproper.NotificatonList.NotificationListActivity;
import com.serasiautoraya.tdsproper.OLCTrip.OLCTripFragment;
import com.serasiautoraya.tdsproper.OLCTripByOrder.OLCTripByOrderFragment;
import com.serasiautoraya.tdsproper.OrderHistory.OrderHistoryFragment;
import com.serasiautoraya.tdsproper.Overtime.OvertimeRequestFragment;
import com.serasiautoraya.tdsproper.RequestHistory.RequestHistoryFragment;
import com.serasiautoraya.tdsproper.WsInOutHistory.WsInOutFragment;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperBridge;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.CustomListener.TextViewTouchListener;
import com.serasiautoraya.tdsproper.util.LocationServiceUtil;
import com.serasiautoraya.tdsproper.util.NetworkChangeReceiver;
import com.squareup.picasso.Picasso;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public class DashboardActivity extends TiActivity<DashboardPresenter, DashboardView> implements DashboardView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private TextView mTextViewNavNama;
    private TextView mTextViewNavPosisi;
    private ImageView mImageViewNavImg;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.rel_nointernet)
    RelativeLayout relNoInternet;

    private Handler mHandler;
    private int mFragmentSelectedID;
    private View mNavHeader;

    private String mExpenseStatus;

    NetworkChangeReceiver networkChangeReceiver;
    Snackbar snackbarNetworkChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        snackbarNetworkChange = Snackbar.make(getWindow().getDecorView(), "Tidak terdapat koneksi internet", Snackbar.LENGTH_INDEFINITE);
        View view = snackbarNetworkChange.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        networkChangeReceiver = new NetworkChangeReceiver() {
            @Override
            protected void onDisconnect() {
//                snackbarNetworkChange.show();
                relNoInternet.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onConnect() {
//                snackbarNetworkChange.dismiss();
                relNoInternet.setVisibility(View.GONE);
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public void initialize() {
        this.initializeNavigationView();
        LocationServiceUtil.getLocationManager(DashboardActivity.this);
    }

    @Override
    public void toggleLoading(boolean isLoading) {

    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public DashboardPresenter providePresenter() {
        return new DashboardPresenter(new SharedPrefsModel(DashboardActivity.this));
    }


    /*
    * Non standard method (available only for this class but still override from View interface)
    * */

    @Override
    public void initializeMenuAccess() {
        /*
        * TODO give menu access (hide or show menu item) based on module access code
        * */
    }

    @Override
    public void changeFragment(final Fragment targetFragment) {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = targetFragment;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_main_content, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    @Override
    public void changeActivity(Class targetActivity) {
        Intent intent = new Intent(DashboardActivity.this, targetActivity);
        startActivity(intent);
    }

    @Override
    public Fragment getActiveFragment(int idNavItem) {
        switch (idNavItem) {
            case R.id.nav_active_order:
                AssignedFragment assignedFragment = new AssignedFragment();
                mNavigationView.setCheckedItem(R.id.nav_active_order);
                return assignedFragment;
            case R.id.nav_cico_request:
                CiCoFragment ciCoFragment = new CiCoFragment();
                mNavigationView.setCheckedItem(R.id.nav_cico_request);
                return ciCoFragment;
            case R.id.nav_absence_request:
                AbsenceRequestFragment absenceRequestFragment = new AbsenceRequestFragment();
                mNavigationView.setCheckedItem(R.id.nav_absence_request);
                return absenceRequestFragment;
            case R.id.nav_olctrip_request:
                OLCTripFragment olcTripFragment = new OLCTripFragment();
                mNavigationView.setCheckedItem(R.id.nav_olctrip_request);
                return olcTripFragment;
            case R.id.nav_overtime_request:
                OvertimeRequestFragment overtimeRequestFragment = new OvertimeRequestFragment();
                mNavigationView.setCheckedItem(R.id.nav_overtime_request);
                return overtimeRequestFragment;
            case R.id.nav_expense_request:
                ExpenseRequestFragment expenseRequestFragment = new ExpenseRequestFragment();
                mNavigationView.setCheckedItem(R.id.nav_expense_request);
                return expenseRequestFragment;
            case R.id.nav_ws_history:
                WsInOutFragment wsInOutFragment = new WsInOutFragment();
                mNavigationView.setCheckedItem(R.id.nav_ws_history);
                return wsInOutFragment;
            case R.id.nav_attendance_history:
                RequestHistoryFragment attendanceHistoryFragment = new RequestHistoryFragment();
                mNavigationView.setCheckedItem(R.id.nav_attendance_history);
                return attendanceHistoryFragment;
            case R.id.nav_order_history:
                OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
                mNavigationView.setCheckedItem(R.id.nav_order_history);
                return orderHistoryFragment;
            case R.id.nav_olctrip_request_from_api:
                OLCTripByOrderFragment olcTripByOrderFragment = new OLCTripByOrderFragment();
                //mNavigationView.setCheckedItem(R.id.nav_olctrip_request_from_api);
                return olcTripByOrderFragment;
            default:
                    mNavigationView.setCheckedItem(R.id.nav_active_order);
                    return new AssignedFragment();

        }
    }

    @Override
    public Class getTargetActivityClass(int idNavItem) {
        /*
        * TODO change and normalize this
        * */

        switch (idNavItem) {
            case R.id.nav_change_pass:
                return ChangePasswordActivity.class;
//                return QuestionnaireActivity.class;
//                return PODCaptureActivity.class;
//                return PodSubmitActivity.class;
//                return FatigueActivity.class;
            case R.id.nav_notification_list:
                return NotificationListActivity.class;
            default:
                return ChangePasswordActivity.class;
        }
    }

    @Override
    public void exitApplication() {

    }

    @Override
    public void logout() {
        getPresenter().logout();
        this.finish();
    }


    /*
    * Below is override methods from super class (AppCompatActivity)
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        this.mFragmentSelectedID = id;
        if (id != R.id.nav_change_pass && id != R.id.nav_logout && id != R.id.nav_notification_list) {
            getPresenter().onNavigationItemSelectedForFragment(id);
        } else {
            if (id == R.id.nav_logout) {
                logout();
            } else {
                getPresenter().onNavigationItemSelectedForActivity(id);
            }
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    * Non overriding methods
    * */
    public void initializeNavigationView() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();


        mNavigationView.setNavigationItemSelectedListener(this);

        mHandler = new Handler();
        mNavigationView.setCheckedItem(R.id.nav_active_order);



        mNavHeader = mNavigationView.getHeaderView(0);

        mImageViewNavImg = (ImageView) mNavHeader.findViewById(R.id.nav_header_img);
        mTextViewNavNama = (TextView) mNavHeader.findViewById(R.id.nav_header_name);
        mTextViewNavPosisi = (TextView) mNavHeader.findViewById(R.id.nav_header_posisi);
        mImageViewNavImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().loadDetailProfile();
            }
        });
        mTextViewNavNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().loadDetailProfile();
            }
        });
        mTextViewNavPosisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().loadDetailProfile();
            }
        });
        mTextViewNavNama.setOnTouchListener(new TextViewTouchListener(this, R.color.colorPrimary, R.color.colorTextIcon));
        mTextViewNavPosisi.setOnTouchListener(new TextViewTouchListener(this, R.color.colorPrimary, R.color.colorTextIcon));
        mImageViewNavImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mImageViewNavImg.setColorFilter(Color.argb(90, 255, 87, 34));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mImageViewNavImg.setColorFilter(Color.argb(0, 0, 0, 0));
                }
                return false;
            }
        });
    }


    @Override
    public void onProfileDetailClicked() {
        getPresenter().loadDetailProfile();
    }

    @Override
    public void setDrawerProfile(String name, String position, String urlPhoto) {
        if(urlPhoto != null){
            if(!urlPhoto.equalsIgnoreCase("")){
                Picasso.with(DashboardActivity.this).load(urlPhoto).into(mImageViewNavImg);
            }
        }
        mTextViewNavNama.setText(name);
        mTextViewNavPosisi.setText(position);
    }

    @Override
    public void toggleMenu(boolean requestCiCo, boolean reportCiCo, boolean requestAbsence, boolean reportAbsence, boolean requestOLCTrip, boolean reportOLCTrip, boolean requestOvertime, boolean reportOvertime, boolean reportServiceHour, boolean requestExpense) {
        Menu navMenu = mNavigationView.getMenu();
        navMenu.findItem(R.id.nav_cico_request).setVisible(requestCiCo);
        navMenu.findItem(R.id.nav_absence_request).setVisible(requestAbsence);
        navMenu.findItem(R.id.nav_olctrip_request).setVisible(requestOLCTrip);
        navMenu.findItem(R.id.nav_overtime_request).setVisible(requestOvertime);
        navMenu.findItem(R.id.nav_expense_request).setVisible(requestExpense);
        navMenu.findItem(R.id.nav_olctrip_request_from_api).setVisible(false);

        if (reportAbsence == false && reportCiCo == false && reportOLCTrip == false && reportOvertime == false) {
            navMenu.findItem(R.id.nav_attendance_history).setVisible(false);
        }else {
            navMenu.findItem(R.id.nav_attendance_history).setVisible(true);
        }
        /*
        * TODO delete this (expense nav only disappear for phase 1 in 1 november 2017)
        * */
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitApp();
        }
    }

    private void exitApp() {
        HelperUtil.showConfirmationAlertDialog(getResources().getString(R.string.warn_msg_exit),
                DashboardActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
    }
}
