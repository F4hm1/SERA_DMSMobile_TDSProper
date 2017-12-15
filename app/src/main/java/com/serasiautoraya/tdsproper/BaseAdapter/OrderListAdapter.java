package com.serasiautoraya.tdsproper.BaseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serasiautoraya.tdsproper.R;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {
    private List<OrderSingleList> orderList;

    public class OrderListViewHolder extends RecyclerView.ViewHolder {
        public TextView mSingleListCode, mSingleListHub, mSingleListNextdriver,
                mSingleListOrigin, mSingleListEtd, mSingleListEta,
                mSingleListCustomer, mSingleListUnit, mSingleListDest;

        public OrderListViewHolder(View view) {
            super(view);
            mSingleListCode = (TextView) view.findViewById(R.id.acknowledge_dialog_ordercode);
            mSingleListHub = (TextView) view.findViewById(R.id.acknowledge_dialog_hub);
            mSingleListNextdriver = (TextView) view.findViewById(R.id.acknowledge_dialog_nextdriver);
            mSingleListOrigin = (TextView) view.findViewById(R.id.acknowledge_dialog_origin);
            mSingleListEtd = (TextView) view.findViewById(R.id.acknowledge_dialog_etd);
            mSingleListEta = (TextView) view.findViewById(R.id.acknowledge_dialog_eta);
            mSingleListCustomer = (TextView) view.findViewById(R.id.acknowledge_dialog_customer);
            mSingleListUnit = (TextView) view.findViewById(R.id.acknowledge_dialog_unit);
            mSingleListDest = (TextView) view.findViewById(R.id.single_list_dest);
        }
    }

    public OrderListAdapter(List<OrderSingleList> orderList) {
        this.orderList = orderList;
    }

    public OrderSingleList getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_order, parent, false);
        return new OrderListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderListViewHolder holder, int position) {
        OrderSingleList generalSingleList = orderList.get(position);
        holder.mSingleListCode.setText("Order Code "+generalSingleList.getmSingleListCode());
        holder.mSingleListHub.setText(generalSingleList.getmSingleListHub());
        holder.mSingleListNextdriver.setText(generalSingleList.getmSingleListNextdriver());
        holder.mSingleListOrigin.setText(generalSingleList.getmSingleListOrigin());
        holder.mSingleListEtd.setText(generalSingleList.getmSingleListEtd());
        holder.mSingleListEta.setText(generalSingleList.getmSingleListEta());
        holder.mSingleListCustomer.setText(generalSingleList.getmSingleListCustomer());
        holder.mSingleListUnit.setText(generalSingleList.getmSingleListUnit());
        holder.mSingleListDest.setText("Destination: "+generalSingleList.getmSingleListDest());
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }
}