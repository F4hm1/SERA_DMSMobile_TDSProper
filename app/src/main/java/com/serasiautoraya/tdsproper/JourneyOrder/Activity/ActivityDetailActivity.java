package com.serasiautoraya.tdsproper.JourneyOrder.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.serasiautoraya.tdsproper.CustomView.MapJourneyOrder;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailView;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public class ActivityDetailActivity extends TiActivity<ActivityDetailPresenter, ActivityDetailView> implements ActivityDetailView {

    @BindView(R.id.order_title_code_head)
    TextView mTvOrderCodeHead;
    @BindView(R.id.order_title_code)
    TextView mTvOrderCode;
    @BindView(R.id.order_title_activityname)
    TextView mTvOrderActivityName;
    @BindView(R.id.order_title_origin)
    TextView mTvOrderOrigin;
    @BindView(R.id.order_title_dest)
    TextView mTvOrderDest;
    @BindView(R.id.order_title_etd)
    TextView mTvOrderEtd;
    @BindView(R.id.order_title_eta)
    TextView mTvOrderEta;
    @BindView(R.id.order_title_customer)
    TextView mTvOrderCustomer;
    @BindView(R.id.order_title_locationtarget)
    TextView mTvOrderLocationTarget;
    @BindView(R.id.order_title_locationtargetaddress)
    TextView mTvOrderAddressTarget;
    @BindView(R.id.order_title_timetarget)
    TextView mTvOrderTimeTarget;
    //    @BindView(R.id.order_title_timebaseline)
//    TextView mTvOrderTimeBaseline;
//    @BindView(R.id.order_title_timeactual)
//    TextView mTvOrderTimeActual;
    @BindView(R.id.order_title_assignment)
    TextView mTvOrderAssignment;
    @BindView(R.id.order_title_cargotype)
    TextView mTvOrderCargotype;
    @BindView(R.id.order_title_unitmodel)
    TextView mTvOrderUnitModel;
    @BindView(R.id.order_title_unitnumber)
    TextView mTvOrderUnitNumber;
    @BindView(R.id.order_title_documentneed)
    TextView mTvOrderDocumentneed;
    @BindView(R.id.order_title_nextlocationtarget)
    TextView mTvOrderNextActivity;
    @BindView(R.id.order_title_nextlocationaddress)
    TextView mTvOrderNextActivityAddress;

    @BindView(R.id.order_title_cargodesc)
    TextView mTvOrderCargoDesc;
    @BindView(R.id.order_title_notes)
    TextView mTvOrderNotes;

    @BindView(R.id.order_title_dest_label)
    TextView mTvOrderDestLabel;

    @BindView(R.id.order_tl_destination)
    TableLayout mTlOrderDestination;


    @BindView(R.id.order_button_action)
    Button mTvButtonAction;
    @BindView(R.id.order_button_noaction)
    Button mTvButtonNoAction;

    private String mOrderCode, mIsExpense, mIsTripOLC;
    private Integer mAssignmentId;
    private ProgressDialog mProgressDialog;
    private MapJourneyOrder mMapJourneyOrder;
    private String mPhoneNumber = "";

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_actionactivity);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mAssignmentId = Integer.valueOf(bundle.getString(HelperKey.KEY_INTENT_ASSIGNMENTID));
            mOrderCode = bundle.getString(HelperKey.KEY_INTENT_ORDERCODE);
            mIsExpense = bundle.getString(HelperKey.KEY_INTENT_IS_EXPENSE);
            mIsTripOLC = bundle.getString(HelperKey.KEY_INTENT_IS_TRIP_OLC);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_activitydetail);
        mMapJourneyOrder = new MapJourneyOrder(mapFragment, this);
    }

    @Override
    public void initialize() {
        getPresenter().loadDetailOrderData(mOrderCode, bundle);
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(ActivityDetailActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_call_passanger, menu);

        MenuItem itemSwitch = menu.findItem(R.id.menu_call_passanger);
        itemSwitch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Uri call = Uri.parse("tel:" + mPhoneNumber);
                Intent surfDialCall = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surfDialCall);
                return true;
            }
        });
        return true;
    }


    @Override
    public void showToast(String text) {
        Toast.makeText(ActivityDetailActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, ActivityDetailActivity.this, Title);
    }


    @NonNull
    @Override
    public ActivityDetailPresenter providePresenter() {
        return new ActivityDetailPresenter(new RestConnection(ActivityDetailActivity.this));
    }

    @Override
    @OnClick(R.id.order_button_action)
    public void onActionClicked(View view) {
        getPresenter().onActionClicked(mAssignmentId, mOrderCode, mIsExpense, mIsTripOLC);
    }

    @Override
    public void setDetailData(String codeHead, String code, String activityName,
                              String activityType, String origin, String[] destination,
                              String etd, String eta, String customer,
                              String locationTarget, String timeTarget, String timeBaseline,
                              String timeActual, String assignmentId, String cargoType,
                              String unitModel, String unitnumber, String docNeed,
                              String nextActivity,
                              String nextActivityAddress,
                              String addressTarget,
                              String notes,
                              String cargoDescription) {
        mTvOrderCodeHead.setText(codeHead);
        mTvOrderCode.setText(code);
        mTvOrderActivityName.setText(activityName);
        mTvOrderOrigin.setText(origin);
//        mTvOrderDest.setText(destination);
        mTvOrderEtd.setText(etd);
        mTvOrderEta.setText(eta);
        mTvOrderCustomer.setText(customer);
        mTvOrderLocationTarget.setText(locationTarget);
        mTvOrderTimeTarget.setText(timeBaseline);
//        mTvOrderTimeBaseline.setText(timeBaseline);
//        mTvOrderTimeActual.setText(timeActual);
        mTvOrderAssignment.setText(assignmentId);
        mTvOrderCargotype.setText(cargoType);
        mTvOrderUnitModel.setText(unitModel);
        mTvOrderUnitNumber.setText(unitnumber);
        mTvOrderDocumentneed.setText(docNeed);
        mTvOrderNextActivity.setText(nextActivity);
        mTvOrderNextActivityAddress.setText(nextActivityAddress);
        mTvOrderAddressTarget.setText(addressTarget);
        mTvOrderNotes.setText(notes);
        mTvOrderCargoDesc.setText(cargoDescription);
        this.generateDestination(destination);
    }

    @Override
    public void setButtonText(String text) {
        mTvButtonAction.setText(text);
    }

    @Override
    public void setButtonColor(String hexaCode) {
        mTvButtonAction.setBackgroundColor(Color.parseColor(hexaCode));
    }

    @Override
    public void changeActivity(Class cls) {
        HelperBridge.sCurrentDetailActivity = this;
        Intent i = new Intent(ActivityDetailActivity.this, cls);
        startActivity(i);
    }

    @Override
    public void showConfirmationDialog(String title, String activity) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan melakukan aktifitas " +
                "<b>" + activity.toString() + "</b>" + "?");
        HelperUtil.showConfirmationAlertDialog(textMsg, ActivityDetailActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitActivity();
            }
        });
    }

    @Override
    public void showOLCDialog(String message, String title) {
        HelperUtil.showOLCAlertDialogCustomTitle(message, ActivityDetailActivity.this, title, new HelperUtil.OlcCallback() {
            @Override
            public void OK() {
                getPresenter().showOLC(mOrderCode, mAssignmentId);
            }
        });
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void showConfirmationSuccess(String message, String title) {
        HelperUtil.showSimpleAlertDialogCustomTitleAction(message, ActivityDetailActivity.this, title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishActivity();
                    }
                },
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finishActivity();
                    }
                });
    }

    @Override
    public void toggleButtonAction(boolean show) {
        if (show) {
            mTvButtonAction.setVisibility(View.VISIBLE);
            mTvButtonNoAction.setVisibility(View.GONE);
        } else {
            mTvButtonAction.setVisibility(View.GONE);
            mTvButtonNoAction.setVisibility(View.VISIBLE);
        }
    }

    /*
    * For SLI Last Mile only, generate stores into list
    * */
    @Override
    public void generateDestination(String[] arrDestination) {
        for (int i = 0; i < arrDestination.length; i++) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TableRow rowView;
            if (i == (arrDestination.length - 1)) {
                rowView = (TableRow) inflater.inflate(R.layout.row_destination_activity_borderless, null);
            } else {
                rowView = (TableRow) inflater.inflate(R.layout.row_destination_activity, null);
            }
            TextView tv = (TextView) rowView.findViewById(R.id.destactivity_text);
            tv.setText(arrDestination[i]);
            mTlOrderDestination.addView(rowView);
        }
    }

    @Override
    public void setMapFromData(LatLng fromLatLng, LatLng toLatLng, String fromAddress, String toAddress) {
        mMapJourneyOrder.generateRoute(fromLatLng, toLatLng, fromAddress, toAddress);
    }

    @Override
    public void setPhoneNumber(String phoneNumber){
        if (phoneNumber!=null){
            mPhoneNumber = phoneNumber;
        }
    }

    @Override
    public void setDestinationDuration(boolean isTimeBased, String textDest){
        if(isTimeBased){
            mTvOrderDestLabel.setText("Durasi");
        }else{
            mTvOrderDestLabel.setText("Tujuan");
        }
        mTvOrderDest.setText(textDest);
    }

    @Override
    public void setTempFragmentTarget(int id) {
        HelperBridge.sTempFragmentTarget = id;
        Log.d("DASHBOARDSS", "ORHISTO: "+HelperBridge.sTempFragmentTarget);
        finishActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTempFragmentTarget(R.id.nav_active_order);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
