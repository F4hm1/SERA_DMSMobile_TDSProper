package com.serasiautoraya.tdsproper.OrderHistory;

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
 * Created by Randi Dwi Nandra on 22/05/2017.
 */

public class OrderHistoryListAdapter extends RecyclerView.Adapter<SimpleListViewHolder>
        implements SimpleAdapterModel<OrderHistoryResponseModel>, SimpleAdapterView {

    private List<OrderHistoryResponseModel> mSimpleSingleLists;

    @Override
    public SimpleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general, parent, false);
        return new SimpleListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleListViewHolder holder, int position) {
        OrderHistoryResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        holder.getTitle().setText("Order "+simpleSingleList.getOrderCode());
        holder.getInformation().setText(simpleSingleList.getOrigin() +" - "+ simpleSingleList.getDestination());
        holder.getStatus().setText(simpleSingleList.getStatus());
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
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
    public void setItemList(List<OrderHistoryResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(OrderHistoryResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }
}
