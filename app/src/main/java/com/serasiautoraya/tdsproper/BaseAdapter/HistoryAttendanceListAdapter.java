package com.serasiautoraya.tdsproper.BaseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serasiautoraya.tdsproper.R;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 30/11/2016.
 */
public class HistoryAttendanceListAdapter extends RecyclerView.Adapter<HistoryAttendanceListAdapter.HistoryAttendanceListViewHolder> {
    private List<HistoryAttendanceSingleList> historyAttendanceSingleLists;

    public class HistoryAttendanceListViewHolder extends RecyclerView.ViewHolder {
//        private TextView tanggal, absenceType, jadwalIn, jadwalOut, clockIn, clockOut;
        private TextView tanggal, absenceType, clockIn, clockOut;

        public HistoryAttendanceListViewHolder(View view) {
            super(view);
            tanggal = (TextView) view.findViewById(R.id.single_list_atthis_tanggal);
            absenceType = (TextView) view.findViewById(R.id.single_list_atthis_absence);
//            jadwalIn = (TextView) view.findViewById(R.id.single_list_atthis_jadwalin);
//            jadwalOut = (TextView) view.findViewById(R.id.single_list_atthis_jadwalout);
            clockIn = (TextView) view.findViewById(R.id.single_list_atthis_clockin);
            clockOut = (TextView) view.findViewById(R.id.single_list_atthis_clockout);
        }
    }

    public HistoryAttendanceListAdapter(List<HistoryAttendanceSingleList> historyAttendanceSingleLists) {
        this.historyAttendanceSingleLists = historyAttendanceSingleLists;
    }

    public HistoryAttendanceSingleList getItem(int position) {
        return historyAttendanceSingleLists.get(position);
    }

    @Override
    public HistoryAttendanceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_historyattendance, parent, false);
        return new HistoryAttendanceListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryAttendanceListViewHolder holder, int position) {
        HistoryAttendanceSingleList historyCicoSingleList = historyAttendanceSingleLists.get(position);
        holder.tanggal.setText(historyCicoSingleList.getTanggal());
        holder.absenceType.setText(historyCicoSingleList.getAbsenceType());
//        holder.jadwalIn.setText(historyCicoSingleList.getJadwalIn());
//        holder.jadwalOut.setText(historyCicoSingleList.getJadwalOut());
        holder.clockIn.setText(historyCicoSingleList.getClockIn());
        holder.clockOut.setText(historyCicoSingleList.getClockOut());
    }

    @Override
    public int getItemCount() {
        return historyAttendanceSingleLists.size();
    }
}
