package com.serasiautoraya.tdsproper.CustomListener;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Randi Dwi Nandra on 08/12/2016.
 */
public class TextViewTouchListener implements View.OnTouchListener {
    private Context mContext;
    private int mIdColorUp, mIdColorDown;

    public TextViewTouchListener(Context context, int mIdColorDown, int mIdColorUp) {
        this.mContext = context;
        this.mIdColorDown = mIdColorDown;
        this.mIdColorUp = mIdColorUp;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ((TextView) v).setTextColor(ContextCompat.getColor(mContext, mIdColorDown));
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                ((TextView) v).setTextColor(ContextCompat.getColor(mContext, mIdColorUp));
                break;
        }
        return false;
    }
}

