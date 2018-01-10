package com.serasiautoraya.tdsproper.WsInOutHistory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import java.util.List;

/**
 * Created by randi on 24/07/2017.
 */

public class WsInOutHistoryListAdapter extends RecyclerView.Adapter<WsInOutSingleViewHolder>
        implements SimpleAdapterModel<WsInOutResponseModel>, SimpleAdapterView {

    private List<WsInOutResponseModel> mSimpleSingleLists;

    @Override
    public WsInOutSingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_wsinout, parent, false);
        return new WsInOutSingleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WsInOutSingleViewHolder holder, int position) {
        WsInOutResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        holder.getDate().setText(HelperUtil.getUserFormDate(simpleSingleList.getDate()));
        String keterangan = "";
        String clockInActual = simpleSingleList.getClockIn();
        String clockOutActual = simpleSingleList.getClockOut();

        holder.getWsin().setText("Clock In      : -");
        if (!clockInActual.equalsIgnoreCase("")) {
            String clockInDate = clockInActual.split("-")[0];
            if (!clockInDate.equalsIgnoreCase("0001")) {
                keterangan += " Clock In |";

                String clockInTimeVal = clockInActual.split(" ")[1];
                holder.getWsin().setText("Clock In      : " + clockInTimeVal);
            }
        }

        holder.getWsout().setText("Clock Out   : -");
        if (!clockOutActual.equalsIgnoreCase("")) {
            String clockOutDate = clockOutActual.split("-")[0];
            if (!clockOutDate.equalsIgnoreCase("0001")) {
                keterangan += " Clock Out |";

                String clockOutTimeVal = clockOutActual.split(" ")[1];
                holder.getWsout().setText("Clock Out   : "+ clockOutTimeVal);
            }
        }

//        if (!simpleSingleList.getOLC().equalsIgnoreCase("")) {
//            keterangan += " OLC |";
//        }
//
//        if (!simpleSingleList.getTrip().equalsIgnoreCase("")) {
//            keterangan += " Trip |";
//        }
//
//        if (!simpleSingleList.getOvertime().equalsIgnoreCase("")) {
//            keterangan += " Overtime |";
//        }

        if (!simpleSingleList.getAbsence().equalsIgnoreCase("0")) {
            keterangan += " Tidak Hadir";
        }

        holder.getKeterangan().setText(keterangan);
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
    }

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public Model getItem(int position) {
        return mSimpleSingleLists.get(position);
    }

    @Override
    public Model remove(int position) {
        return mSimpleSingleLists.remove(position);
    }

    @Override
    public void setItemList(List<WsInOutResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(WsInOutResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }
}
