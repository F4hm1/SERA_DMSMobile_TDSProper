package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.widget.TableLayout;

import com.serasiautoraya.tdsproper.BaseInterface.BaseViewInterface;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public interface PlanOrderView extends BaseViewInterface{

    void refreshRecyclerView();

    void changeActivityAction(String key, String value, Class targetActivity);

    void showAcknowledgeDialog(
            String ordercode,
            Integer assignmentId,
            String origin,
            String etd,
            String eta,
            String passangerName,
            String totalPassanger,
            String destinationDuration,
            boolean isTimeBased
    );

    void refreshAllData();

    void toggleEmptyInfo(boolean show);

    void setTextEmptyInfoStatus(boolean success);

    void generateDestination(String[] arrDestination, TableLayout tableLayout);
}
