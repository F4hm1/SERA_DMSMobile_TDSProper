package com.serasiautoraya.tdsproper.RequestHistory;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.serasiautoraya.tdsproper.BaseAdapter.CustomPopUpItemClickListener;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterView;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleListViewHolder;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class RequestHistoryAdapter extends RecyclerView.Adapter<SimpleListViewHolder>
        implements SimpleAdapterModel<RequestHistoryResponseModel>, SimpleAdapterView {

    private List<RequestHistoryResponseModel> mSimpleSingleLists;
    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener;

    public RequestHistoryAdapter(PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    private CustomPopUpItemClickListener customPopUpItemClickListener;

    public RequestHistoryAdapter(CustomPopUpItemClickListener customPopUpItemClickListener) {
        this.customPopUpItemClickListener = customPopUpItemClickListener;
    }


    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public RequestHistoryResponseModel getItem(int position) {
        return mSimpleSingleLists.get(position);
    }

    @Override
    public RequestHistoryResponseModel remove(int position) {
        return mSimpleSingleLists.remove(position);
    }

    @Override
    public void setItemList(List<RequestHistoryResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(RequestHistoryResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }

    @Override
    public SimpleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general_withmenu, parent, false);
        return new SimpleListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleListViewHolder holder, int position) {
        final RequestHistoryResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        final int itemPosition = position;

        holder.getTitle().setText("Pengajuan " + simpleSingleList.getRequestDate());
        holder.getInformation().setText(simpleSingleList.getDateStart() + " - " + simpleSingleList.getDateEnd());
        holder.getStatus().setText(simpleSingleList.getRequestStatus());

        switch (simpleSingleList.getTransType()) {
            case HelperTransactionCode.REQUEST_HISTORY_ABSENCE_CODE: {
                holder.getTitle().setText("Pengajuan " + HelperUtil.getUserFormDate(simpleSingleList.getRequestDate()));
                holder.getInformation().setText(HelperUtil.getUserFormDate(simpleSingleList.getDateStart()) + " - " + HelperUtil.getUserFormDate(simpleSingleList.getDateEnd()));
                holder.getStatus().setText(simpleSingleList.getRequestStatus());
                break;
            }
            case HelperTransactionCode.REQUEST_HISTORY_CICO_CODE: {
                holder.getTitle().setText("Pengajuan " + HelperUtil.getUserFormDate(simpleSingleList.getRequestDate()));
                holder.getInformation().setText(HelperUtil.getUserFormDate(simpleSingleList.getDateStart() + " : " + HelperUtil.getUserFormDate(simpleSingleList.getTimeStart())));
                holder.getStatus().setText(simpleSingleList.getRequestStatus());
                break;
            }
            case HelperTransactionCode.REQUEST_HISTORY_OLCTRIP_CODE: {
                holder.getTitle().setText("Pengajuan " + HelperUtil.getUserFormDate(simpleSingleList.getRequestDate()));
                holder.getInformation().setText(HelperUtil.getUserFormDate(simpleSingleList.getDateStart() + " : " + HelperUtil.getUserFormDate(simpleSingleList.getTimeStart())));
                holder.getStatus().setText(simpleSingleList.getRequestStatus());
                break;
            }
            case HelperTransactionCode.REQUEST_HISTORY_OVERTIME_CODE: {
                holder.getTitle().setText("Pengajuan " + HelperUtil.getUserFormDate(simpleSingleList.getRequestDate()));
                holder.getInformation().setText(HelperUtil.getUserFormDate(simpleSingleList.getDateStart()) + "(" + HelperUtil.getUserFormDate(simpleSingleList.getTimeStart()) + ")" + " - "
                        + simpleSingleList.getDateEnd() + "(" + simpleSingleList.getTimeEnd() + ")");
                holder.getStatus().setText(simpleSingleList.getRequestStatus());
                break;
            }
        }

        final ImageButton imageButton = (ImageButton) holder.getTitle().getRootView().findViewById(R.id.single_list_his_option);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ANCURRR", simpleSingleList.getRequestStatus());
                String status = simpleSingleList.getRequestStatus().replace(" ", "");
                showPopupMenu(imageButton, itemPosition, status.equalsIgnoreCase(HelperTransactionCode.REQUEST_WAITING_APPROVAL_STATUS));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
    }

    private void showPopupMenu(View view, int position, boolean isCancelable) {
        final int itemPosition = position;
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        Menu menu = popup.getMenu();
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_request_history, menu);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return customPopUpItemClickListener.startAction(getItem(itemPosition), menuItem.getItemId());
            }
        });
        popup.show();
        if(!isCancelable){
            MenuItem menuItem =  menu.findItem(R.id.menu_popup_cancel_request);
            menuItem.setVisible(false);
        }
    }

}