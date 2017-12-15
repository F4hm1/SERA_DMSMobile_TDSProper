package com.serasiautoraya.tdsproper.BaseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.serasiautoraya.tdsproper.R;

import butterknife.BindView;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class SimpleListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.single_list_his_title)
    TextView title;

    @BindView(R.id.single_list_his_information)
    TextView information;

    @BindView(R.id.single_list_his_status)
    TextView status;

    public SimpleListViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.single_list_his_title);
        information = (TextView) view.findViewById(R.id.single_list_his_information);
        status = (TextView) view.findViewById(R.id.single_list_his_status);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getInformation() {
        return information;
    }

    public TextView getStatus() {
        return status;
    }
}