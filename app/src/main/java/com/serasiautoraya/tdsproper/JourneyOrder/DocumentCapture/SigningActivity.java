package com.serasiautoraya.tdsproper.JourneyOrder.DocumentCapture;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUrl;
import com.serasiautoraya.tdsproper.util.SignatureView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Randi Dwi Nandra on 03/05/2017.
 */

public class SigningActivity extends AppCompatActivity {

    private LinearLayout mContent;
    private Button mClear;
    private Button mGetSign;

    private SignatureView mSignature;
    private View view;
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = HelperUrl.SAVE_DIRECTORY + "ttd_" +pic_name + ".jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignActionBar();
        assignView();
    }

    private void assignActionBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signature);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;

    }


    private void assignView() {
        mContent = (LinearLayout) findViewById(R.id.linear_forcanvas);
        mClear = (Button) findViewById(R.id.clear);
        mGetSign = (Button) findViewById(R.id.getsign);

        mSignature = new SignatureView(getApplicationContext(), null, mContent, mGetSign);
        mSignature.setBackgroundColor(ContextCompat.getColor(SigningActivity.this, R.color.colorDeepOn));
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mGetSign.setEnabled(false);
        view = mContent;

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
        });
        mGetSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                view.setDrawingCacheEnabled(true);
                mSignature.save(view, StoredPath);
                recreate();
                onBackPressed();
            }
        });
    }

}
