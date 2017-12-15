package com.serasiautoraya.tdsproper.CustomView;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.serasiautoraya.tdsproper.R;

/**
 * Created by Randi Dwi Nandra on 23/05/2017.
 */

public class EmptyInfoView extends PercentRelativeLayout {

    public EmptyInfoView(Context context) {
        super(context);
    }

    public EmptyInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setIcon(int resId){
        ImageView imageView = (ImageView) this.findViewById(R.id.empty_info_image);
        imageView.setImageResource(resId);
    }

    public void setText(String text){
        TextView textView = (TextView) this.findViewById(R.id.empty_info_message);
        textView.setText(text);
    }

}
