package com.serasiautoraya.tdsproper.WsInOutHistory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.serasiautoraya.tdsproper.R;

import butterknife.BindView;

/**
 * Created by randi on 24/07/2017.
 */

public class WsInOutSingleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.single_wsinout_date)
    TextView date;

    @BindView(R.id.single_wsinout_keterangan)
    TextView keterangan;

    @BindView(R.id.single_wsinout_wsin)
    TextView wsin;

    @BindView(R.id.single_wsinout_wsout)
    TextView wsout;


    public WsInOutSingleViewHolder(View view) {
        super(view);
        date = (TextView) view.findViewById(R.id.single_wsinout_date);
        keterangan = (TextView) view.findViewById(R.id.single_wsinout_keterangan);
        wsin = (TextView) view.findViewById(R.id.single_wsinout_wsin);
        wsout = (TextView) view.findViewById(R.id.single_wsinout_wsout);
    }

    public TextView getDate() {
        return date;
    }

    public TextView getKeterangan() {
        return keterangan;
    }

    public TextView getWsin() {
        return wsin;
    }

    public TextView getWsout() {
        return wsout;
    }

}
