package com.serasiautoraya.tdsproper.NotificatonList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.serasiautoraya.tdsproper.R;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 11/04/2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<SimpleListViewHolder>
        implements SimpleAdapterModel<NotificationResponseModel>, SimpleAdapterView {

    private List<NotificationResponseModel> mSimpleSingleLists;

    private CustomPopUpItemClickListener<NotificationResponseModel> customPopUpItemClickListener;

    public NotificationListAdapter(CustomPopUpItemClickListener<NotificationResponseModel> customPopUpItemClickListener) {
        this.customPopUpItemClickListener = customPopUpItemClickListener;
    }

    @Override
    public SimpleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_general_withmenu, parent, false);
        return new SimpleListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleListViewHolder holder, int position) {
        NotificationResponseModel simpleSingleList = mSimpleSingleLists.get(position);
        final int itemPosition = position;

        holder.getTitle().setText(simpleSingleList.getTitle());
        holder.getInformation().setText(simpleSingleList.getMessage());
        holder.getStatus().setText(simpleSingleList.getDate());

        final ImageButton imageButton = (ImageButton) holder.getTitle().getRootView().findViewById(R.id.single_list_his_option);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(imageButton, itemPosition);
            }
        });
    }

    private void showPopupMenu(View view, int position) {
        final int itemPosition = position;
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_notification_list, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return customPopUpItemClickListener.startAction(getItem(itemPosition), menuItem.getItemId());
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mSimpleSingleLists.size();
    }

    @Override
    public NotificationResponseModel getItem(int position) {
        return mSimpleSingleLists.get(position);
    }

    @Override
    public NotificationResponseModel remove(int position) {
        return mSimpleSingleLists.remove(position);
    }

    @Override
    public void setItemList(List<NotificationResponseModel> simpleSingleLists) {
        this.mSimpleSingleLists = simpleSingleLists;
    }

    @Override
    public void addItem(NotificationResponseModel simpleSingleList) {
        mSimpleSingleLists.add(simpleSingleList);
    }

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }
}
