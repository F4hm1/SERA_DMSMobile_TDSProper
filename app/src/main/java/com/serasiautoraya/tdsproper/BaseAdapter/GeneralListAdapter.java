package com.serasiautoraya.tdsproper.BaseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serasiautoraya.tdsproper.R;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class GeneralListAdapter extends RecyclerView.Adapter<GeneralListAdapter.GeneralListViewHolder> {
    private List<GeneralSingleList> generalList;

    public class GeneralListViewHolder extends RecyclerView.ViewHolder {
        public TextView title, status, information;

        public GeneralListViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.single_list_his_title);
            information = (TextView) view.findViewById(R.id.single_list_his_information);
            status = (TextView) view.findViewById(R.id.single_list_his_status);
        }
    }

    public GeneralListAdapter(List<GeneralSingleList> generalList) {
        this.generalList = generalList;
    }

    public GeneralSingleList getItem(int position) {
        return generalList.get(position);
    }

    @Override
    public GeneralListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general, parent, false);
        return new GeneralListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GeneralListViewHolder holder, int position) {
        GeneralSingleList generalSingleList = generalList.get(position);
        holder.title.setText(generalSingleList.getTittle());
        holder.information.setText(generalSingleList.getInformation());
        holder.status.setText(generalSingleList.getStatus());
    }

    @Override
    public int getItemCount() {
        return generalList.size();
    }
}
