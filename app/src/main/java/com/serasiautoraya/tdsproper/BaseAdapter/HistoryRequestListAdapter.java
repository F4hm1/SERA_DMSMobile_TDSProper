package com.serasiautoraya.tdsproper.BaseAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;
import com.serasiautoraya.tdsproper.CustomListener.CancelableRequest;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 09/01/2017.
 */
public class HistoryRequestListAdapter extends RecyclerView.Adapter<HistoryRequestListAdapter.HistoryRequestViewHolder> {

    private List<HistoryRequestSingleList> historyRequestSingleLists;
    private Context context;
    private CancelableRequest cancelableRequest;

    public class HistoryRequestViewHolder extends RecyclerView.ViewHolder {
        public TextView mDate, mInformation, mStatus, mCancel;

        public HistoryRequestViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.text_singleout_tanggal);
            mInformation = (TextView) itemView.findViewById(R.id.text_singleout_request);
            mStatus = (TextView) itemView.findViewById(R.id.text_singleout_status);
            mCancel = (TextView) itemView.findViewById(R.id.text_singleout_cancel);
        }
    }

    public HistoryRequestListAdapter(List<HistoryRequestSingleList> historyRequestSingleLists, Context context) {
        this.historyRequestSingleLists = historyRequestSingleLists;
        this.context = context;
        this.cancelableRequest = null;
    }

    public HistoryRequestListAdapter(List<HistoryRequestSingleList> historyRequestSingleLists, Context context, CancelableRequest cancelableRequest) {
        this.historyRequestSingleLists = historyRequestSingleLists;
        this.context = context;
        this.cancelableRequest = cancelableRequest;
    }

    public HistoryRequestSingleList getItem(int position) {
        return historyRequestSingleLists.get(position);
    }

    @Override
    public HistoryRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_outstandingrequest, parent, false);
        return new HistoryRequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryRequestViewHolder holder, int position) {
        final HistoryRequestSingleList historyRequestSingleList = historyRequestSingleLists.get(position);
        holder.mDate.setText(historyRequestSingleList.getDate());
        holder.mInformation.setText(historyRequestSingleList.getInformation());
        holder.mStatus.setText(historyRequestSingleList.getStatus());
        holder.mCancel.setVisibility(View.GONE);
        holder.mCancel.setVisibility(View.VISIBLE);
        holder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan <b>membatalkan pengajuan " +
                        historyRequestSingleList.getInformation() + "</b> pada tanggal <b>" + historyRequestSingleList.getDate() + "</b>?");

                HelperUtil.showConfirmationAlertDialog(textMsg, context, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelableRequest != null) {
                            cancelableRequest.cancelRequest(historyRequestSingleList.getId());
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyRequestSingleLists.size();
    }


}
