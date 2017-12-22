package com.serasiautoraya.tdsproper.Profiling;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.squareup.picasso.Picasso;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public class ProfileActivity extends TiActivity<ProfilePresenter, ProfileView> implements ProfileView {

    @BindView(R.id.text_profile_name_fp)
    TextView mTvProfileNameFp;
    @BindView(R.id.text_profile_posisi_fp)
    TextView mTvProfilePosisiFp;
    @BindView(R.id.text_profile_company_fp)
    TextView mTvProfileCompanyFp;
    @BindView(R.id.text_profile_poolname_fp)
    TextView mTvProfilePoolNameFp;
    @BindView(R.id.text_profile_nrp)
    TextView mTvProfileNrp;
    @BindView(R.id.text_profile_fullname)
    TextView mTvProfileFullname;
    //        @BindView(R.id.text_profile_training) TextView mTvProfileTraining;
    @BindView(R.id.text_profile_usercostumer)
    TextView mTvProfileUsercostumer;
    @BindView(R.id.text_profile_doo)
    TextView mTvProfileDoo;
    @BindView(R.id.text_profile_ktpexp)
    TextView mTvProfileKTPExp;
    @BindView(R.id.text_profile_simexp)
    TextView mTvProfileSIMExp;
    @BindView(R.id.text_title_sim)
    TextView mTvTitleSIMExp;
    @BindView(R.id.img_profile_photo)
    ImageView mIvProfilePhoto;

    private Switch mOnOffSwitch;
    private ProgressBar mPbUpdateDriverStatus;
    private boolean onOffSwitchedByApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_switch_onoff, menu);

        MenuItem itemSwitch = menu.findItem(R.id.menu_status_onoff);
        itemSwitch.setActionView(R.layout.component_switch_onoffdriver);

        MenuItem itemProgress = menu.findItem(R.id.menu_progress_onoff);
        itemProgress.setActionView(R.layout.component_progress_circular);

        mOnOffSwitch = (Switch) itemSwitch.getActionView().findViewById(R.id.switch_status_onoff);
        mPbUpdateDriverStatus = (ProgressBar) itemProgress.getActionView().findViewById(R.id.progress_circular_indeterminate);

        mOnOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isOn) {
//                if (!onOffSwitchedByApps) {
                    getPresenter().toggleDriverStatus(isOn);
//                }else{
//                    onOffSwitchedByApps = false;
//                }
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    @Override
    public void initialize() {
        this.initializeActionBar();
        this.initiailzeTvListener();
        getPresenter().loadProfileData();
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
    public ProfilePresenter providePresenter() {
        return new ProfilePresenter(new RestConnection(this));
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Profile");
    }

    private void initiailzeTvListener() {
        mTvProfileNameFp.setKeyListener(null);
        mTvProfilePosisiFp.setKeyListener(null);
        mTvProfileNrp.setKeyListener(null);
        mTvProfileFullname.setKeyListener(null);
//        mTvProfileTraining.setKeyListener(null);
        mTvProfileUsercostumer.setKeyListener(null);
        mTvProfileDoo.setKeyListener(null);
        mTvProfileKTPExp.setKeyListener(null);
        mTvProfileSIMExp.setKeyListener(null);
        mTvProfileCompanyFp.setKeyListener(null);
        mTvProfilePoolNameFp.setKeyListener(null);
    }

    @Override
    public void setProfileContent(String nameFp, String posisiFp, String companyFp, String poolNameFp, String nrp, String fullname, String userCostumer, String doo, String kTPExp, String sIMExp, String sIMType) {
        mTvProfileNameFp.setText(nameFp);
        mTvProfilePosisiFp.setText(posisiFp);
        mTvProfileCompanyFp.setText(companyFp);
        mTvProfilePoolNameFp.setText(poolNameFp);
        mTvProfileNrp.setText(nrp);
        mTvProfileFullname.setText(fullname);
        mTvProfileUsercostumer.setText(userCostumer);
        mTvProfileDoo.setText(doo);
        mTvProfileKTPExp.setText(kTPExp);
        mTvProfileSIMExp.setText(sIMExp);
        mTvTitleSIMExp.setText(sIMType);
    }

    @Override
    public void setProfilePhoto(String url) {
        if (url != null) {
            if (!url.equalsIgnoreCase("")) {
                Picasso.with(ProfileActivity.this).load(url).into(mIvProfilePhoto);
            }
        }
    }

    @Override
    public void toggleDriverStatus(boolean isOn) {
        onOffSwitchedByApps = true;
        mOnOffSwitch.setChecked(isOn);
        if (isOn) {
            mOnOffSwitch.setText("Status On");
        } else {
            mOnOffSwitch.setText("Status Off");
        }
    }

    @Override
    public void toggleProgressDriverStatusUpdate(boolean isUpdate) {
        if (isUpdate) {
            mOnOffSwitch.setVisibility(View.GONE);
            mPbUpdateDriverStatus.setVisibility(View.VISIBLE);
        } else {
            mOnOffSwitch.setVisibility(View.VISIBLE);
            mPbUpdateDriverStatus.setVisibility(View.GONE);
        }
    }
}
