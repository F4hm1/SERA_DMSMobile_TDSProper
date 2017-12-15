package com.serasiautoraya.tdsproper.Overtime;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by randi on 07/06/2017.
 */

public class OvertimeAvailableAdapter extends ArrayAdapter<OvertimeAvailableResponseModel> {

    private Context context;
    private OvertimeAvailableResponseModel[] values;

    public OvertimeAvailableAdapter(Context context, int textViewResourceId,
                                    OvertimeAvailableResponseModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }


    public int getCount(){
        return values.length;
    }

    public OvertimeAvailableResponseModel getItem(int position){
        return values[position];
    }

    public long getItemId(int position){
        return position;
    }



}
