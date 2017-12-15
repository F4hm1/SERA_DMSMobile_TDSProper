package com.serasiautoraya.tdsproper.ModuleServiceHourHistory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleListViewHolder;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.R;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 14/06/2017.
 */

public class ServiceHourHistoryAdapter extends RecyclerView.Adapter<SimpleListViewHolder>
        implements SimpleAdapterModel<ServiceHourHistoryResponseModel>, SimpleAdapterView {

    private List<ServiceHourHistoryResponseModel> mSimpleSingleLists;

    @Override
    public SimpleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general, parent, false);
        return new SimpleListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleListViewHolder holder, int position) {
        ServiceHourHistoryResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        holder.getTitle().setText(simpleSingleList.getOrderCode());
        holder.getInformation().setText(simpleSingleList.getServiceHourStart() + " - " + simpleSingleList.getServiceHourEnd());
        holder.getStatus().setText(simpleSingleList.getServiceHourDate());
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
    public void setItemList(List<ServiceHourHistoryResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(ServiceHourHistoryResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }
}
