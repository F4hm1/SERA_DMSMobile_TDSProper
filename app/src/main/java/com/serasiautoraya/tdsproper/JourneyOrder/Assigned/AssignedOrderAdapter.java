package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleListViewHolder;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.R;

import java.util.List;


/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class AssignedOrderAdapter extends RecyclerView.Adapter<SimpleListViewHolder>
        implements SimpleAdapterModel<AssignedOrderResponseModel>, SimpleAdapterView {
    private List<AssignedOrderResponseModel> mSimpleSingleLists;

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public AssignedOrderResponseModel getItem(int position) {
        return mSimpleSingleLists.get(position);
    }

    @Override
    public AssignedOrderResponseModel remove(int position) {
        return mSimpleSingleLists.remove(position);
    }

    @Override
    public void setItemList(List<AssignedOrderResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(AssignedOrderResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }

    @Override
    public SimpleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general, parent, false);
        return new SimpleListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleListViewHolder holder, int position) {
        AssignedOrderResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        holder.getTitle().setText("Order " + simpleSingleList.getOrderID());
//        holder.getInformation().setText(simpleSingleList.getOrigin() + " - " + getLastDestination(simpleSingleList.getDestination()));
        holder.getInformation().setText(getInformationHTML(simpleSingleList.getOrigin(), simpleSingleList.getDestination()));
        holder.getStatus().setText(simpleSingleList.getCurrentActivity());
    }

    private String getLastDestination(String destination) {
        if (destination.equalsIgnoreCase("")) {
            return "-";
        } else {
            String[] splitted = destination.split(HelperKey.SEPARATOR_PIPE);
            return splitted[splitted.length - 1];
        }
    }

    private Spanned getInformationHTML(String origin, String destination) {
        if (destination.equalsIgnoreCase("")) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                return Html.fromHtml(origin + "--", Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(origin + "--");
            }
        } else {
            String resHtml = origin + " <b>---</b> ";
            String[] splitted = destination.split(HelperKey.SEPARATOR_PIPE);
            for (int i = 0; i < splitted.length; i++) {
                if (i != splitted.length - 1) {
                    resHtml += "<b>" + splitted[i] + "</b> >> ";
                } else {
                    resHtml += "<b>" + splitted[i] + "</b>";
                }
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                return Html.fromHtml(resHtml, Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(resHtml);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
    }
}