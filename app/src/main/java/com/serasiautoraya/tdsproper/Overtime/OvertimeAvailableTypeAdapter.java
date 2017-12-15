package com.serasiautoraya.tdsproper.Overtime;

/**
 * Created by Randi Dwi Nandra on 07/06/2017.
 */

public class OvertimeAvailableTypeAdapter {

    private OvertimeAvailableResponseModel overtimeAvailableResponseModel;

    public OvertimeAvailableTypeAdapter(OvertimeAvailableResponseModel overtimeAvailableResponseModel) {
        this.overtimeAvailableResponseModel = overtimeAvailableResponseModel;
    }

    public OvertimeAvailableResponseModel getOvertimeAvailableResponseModel() {
        return overtimeAvailableResponseModel;
    }

    @Override
    public String toString() {
        return overtimeAvailableResponseModel.getOvertimeTypeName();
    }
}
