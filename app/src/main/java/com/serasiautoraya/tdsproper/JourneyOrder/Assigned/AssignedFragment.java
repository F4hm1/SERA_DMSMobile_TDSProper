package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.util.GPSTrackerService;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class AssignedFragment extends TiFragment<AssignedPresenter, AssignedView> implements AssignedView {

    @BindView(R.id.tabs_planactive_orders)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager_planactive_orders)
    ViewPager mViewPager;

    private ProgressDialog mProgressDialog;
    private Activity mParentActivity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planactive_orders, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mParentActivity = (Activity) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().loadOrdersData();
    }

    @Override
    public void initialize() {
//        getPresenter().loadOrdersData();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            if (mParentActivity != null) {
                mProgressDialog = ProgressDialog.show(mParentActivity, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
            }
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        if (mParentActivity != null) {
            Toast.makeText(mParentActivity, text, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        if (mParentActivity != null) {
            HelperUtil.showSimpleAlertDialogCustomTitle(message, mParentActivity, Title);
        }
    }

    @NonNull
    @Override
    public AssignedPresenter providePresenter() {
        return new AssignedPresenter(new RestConnection(getContext()), new SharedPrefsModel(getContext()));
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ActiveOrderFragment(), "Order Aktif");
        adapter.addFragment(new PlanOrderFragment(), "Rencana Order");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void initializeTabs(boolean isAnyOrderActive, boolean isUpdateLocationActive) {
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);

        /*
        * TODO uncomment this
        * */
        if (isAnyOrderActive) {
            if (!isUpdateLocationActive) {
                if (mParentActivity != null) {
                    mParentActivity.startService(new Intent(mParentActivity, GPSTrackerService.class));
                    getPresenter().setUpdateLocationActive(true);
                }
            }
        } else {
            if (isUpdateLocationActive) {
                if (mParentActivity != null) {
                    mParentActivity.stopService(new Intent(mParentActivity, GPSTrackerService.class));
                    getPresenter().setUpdateLocationActive(false);
                }
            }
        }
    }

    @Override
    @OnClick(R.id.fab_planactive_order)
    public void onRefreshClicked(View view) {
        getPresenter().loadOrdersData();
    }

    @Override
    public void refreshAssignedList() {
        getPresenter().loadOrdersData();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            try {
                super.finishUpdate(container);
            } catch (NullPointerException nullPointerException) {
                System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
