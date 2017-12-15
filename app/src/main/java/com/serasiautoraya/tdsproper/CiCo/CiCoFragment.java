package com.serasiautoraya.tdsproper.CiCo;

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

import com.serasiautoraya.tdsproper.R;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoFragment extends TiFragment<CiCoPresenter, CiCoView> implements CiCoView {

    @BindView(R.id.viewpager_cico)
    ViewPager mViewPager;
    @BindView(R.id.tabs_cico)
    TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cico_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
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

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CiCoRealtimeFragment(), "CiCo");
        adapter.addFragment(new CiCoRequestFragment(), "Pengajuan");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_realtime_cico_white_24dp);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_request_cico_white_24dp);
    }


    @NonNull
    @Override
    public CiCoPresenter providePresenter() {
        return new CiCoPresenter();
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
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}