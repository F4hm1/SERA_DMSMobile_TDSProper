package com.serasiautoraya.tdsproper.RequestHistory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.serasiautoraya.tdsproper.RequestHistory.Absence.AbsenceRequestHistoryFragment;
import com.serasiautoraya.tdsproper.RequestHistory.CiCo.CiCoRequestHistoryFragment;
import com.serasiautoraya.tdsproper.RequestHistory.OLCTrip.OLCTripRequestHistoryFragment;
import com.serasiautoraya.tdsproper.RequestHistory.Overtime.OvertimeRequestHistoryFragment;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.CustomDialog.DatePickerToEditTextDialog;

import net.grandcentrix.thirtyinch.TiFragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class RequestHistoryFragment extends TiFragment<RequestHistoryPresenter, RequestHistoryView>
        implements  RequestHistoryView{

    @BindView(R.id.edittext_attendance_history_datemulai) EditText mEtDateStart;
    @BindView(R.id.edittext_attendance_history_dateakhir) EditText mEtDateEnd;

    @BindView(R.id.tabs_request_report)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager_request_report)
    ViewPager mViewPager;

    private DatePickerToEditTextDialog mDatePickerToEditTextDialogStart;
    private DatePickerToEditTextDialog mDatePickerToEditTextDialogEnd;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initialize() {
        this.initializePickerDialog();
        getPresenter().initialRequestHistoryData();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.prog_msg_cancel_request),getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public RequestHistoryPresenter providePresenter() {
        return new RequestHistoryPresenter(new RestConnection(getContext()));
    }

    @Override
    @OnTextChanged(value = R.id.edittext_attendance_history_datemulai, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextStartDateChangeAfter(Editable editable) {
        try {
            mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, true);
            mDatePickerToEditTextDialogEnd.setMaxDateHistory(mEtDateStart.getText().toString());
            mDatePickerToEditTextDialogEnd.setMinDateHistory(mEtDateStart.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTextEndDate(String textEndDate) {
        mEtDateEnd.setText(textEndDate);
    }

    @Override
    public void setTextStartDate(String textStartDate) {
        mEtDateStart.setText(textStartDate);
    }

    @Override
    public void toggleLoadingInitialLoad(boolean isLoading) {
        if(isLoading){
            mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.progress_msg_loaddata),getResources().getString(R.string.prog_msg_wait),true,false);
        }else{
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void initializeTabs(boolean isCicoAllowed, boolean isAbsenceAllowed, boolean isOlcTripAllowed, boolean isOvertimeAllowed) {
        this.setupViewPager(
                mViewPager,
                isCicoAllowed,
                isAbsenceAllowed,
                isOlcTripAllowed,
                isOvertimeAllowed
        );
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    @OnTextChanged(value = R.id.edittext_attendance_history_dateakhir, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextEndDateChangeAfter(Editable editable) {
        if (!mEtDateEnd.getText().toString().equalsIgnoreCase("")) {
            getPresenter().loadRequestHistoryData(mEtDateStart.getText().toString(), mEtDateEnd.getText().toString());
        }
    }

    private void setupViewPager(final ViewPager viewPager, boolean isCicoAllowed, boolean isAbsenceAllowed, boolean isOlcTripAllowed, boolean isOvertimeAllowed) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        if(isCicoAllowed){
            adapter.addFragment(new CiCoRequestHistoryFragment(), "CiCo");
        }

        if(isAbsenceAllowed){
            adapter.addFragment(new AbsenceRequestHistoryFragment(), "Ketidakhadiran");
        }

        if(isOlcTripAllowed){
            adapter.addFragment(new OLCTripRequestHistoryFragment(), "OLC/Trip");
        }

        if(isOvertimeAllowed){
            adapter.addFragment(new OvertimeRequestHistoryFragment(), "Overtime");
        }

        viewPager.setAdapter(adapter);
    }

    private void initializePickerDialog(){
        mDatePickerToEditTextDialogStart = new DatePickerToEditTextDialog(mEtDateStart, getContext(), false, true);
        mDatePickerToEditTextDialogEnd = new DatePickerToEditTextDialog(mEtDateEnd, getContext(), false, true);
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
