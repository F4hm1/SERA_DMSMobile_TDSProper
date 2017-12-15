package com.serasiautoraya.tdsproper.JourneyOrder.Assigned;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailActivity;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailResponseModel;
import com.serasiautoraya.tdsproper.JourneyOrder.Activity.ActivityDetailSendModel;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class ActiveOrderPresenter extends TiPresenter<ActiveOrderView> {

    private SimpleAdapterModel mSimpleAdapterModel;
    private RestConnection mRestConnection;

    public ActiveOrderPresenter(RestConnection mRestConnection){
        this.mRestConnection = mRestConnection;
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel){
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    @Override
    protected void onAttachView(@NonNull final ActiveOrderView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void loadOrdersdata(){
        if (!HelperBridge.sActiveOrdersList.isEmpty()) {
            getView().toggleEmptyInfo(false);
            Log.d("EMPTY_INFO", "FALSE Clicked");
        } else {
            getView().setTextEmptyInfoStatus(HelperBridge.sListOrderRetrievalSuccess);
            getView().toggleEmptyInfo(true);
            Log.d("EMPTY_INFO", "TRUE Clicked");
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sActiveOrdersList);
        getView().refreshRecyclerView();
    }

    public void onItemClicked(int position){

        /*
        * TODO change the way to access id/code order list, code is the title of the list? Pass it to detail driver activity and retrieve data from API based on that.
        * Flow : onCreate activity get bundle data/ordercode -> save to local field -> onAttachView call initialize -> initialize call getdata in presenter ->
        * presenter call & get data from API & show progress dialog -> when done, call getview.setDataValue (not practice)
        *
        * TODO change the way to 2nd alternative, call get activity detail here -> Parse the model value to static class -> if success, change to
         * detail activity view -> initialize all view/field wtith the model from static class before
        * */

        final AssignedOrderResponseModel assignedOrderResponseModel = (AssignedOrderResponseModel) mSimpleAdapterModel.getItem(position);
//        String orderCode = assignedOrderResponseModel.getOrderID();
        final String orderCode = assignedOrderResponseModel.getOrderID();
//        setdummydata(orderCode);

        /*
        * TODO uncomment this to connect API
        * */
        ActivityDetailSendModel activityDetailSendModel =
                new ActivityDetailSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(), orderCode, assignedOrderResponseModel.getAssignmentId());
        getView().toggleLoading(true);
        final ActiveOrderView activeOrderView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_ACTIVITY, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                HelperBridge.sActivityDetailResponseModel = Model.getModelInstance(response.getData()[0], ActivityDetailResponseModel.class);
                HelperBridge.sTempSelectedOrderCode = orderCode;
                HelperBridge.sAssignedOrderResponseModel = assignedOrderResponseModel;
                HelperBridge.isClickedFromPlanOrder = false;
                activeOrderView.changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, HelperBridge.sActivityDetailResponseModel.getAssignmentId()+"", ActivityDetailActivity.class);
                activeOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                activeOrderView.showToast(response);
                activeOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                activeOrderView.showToast("ERROR: " + error.toString());
                activeOrderView.toggleLoading(false);
            }
        });

    }

/*    private void setdummydata(String ordercode) {
        HelperBridge.sActivityDetailResponseModel = new ActivityDetailResponseModel(
          ordercode, "acticode:" + ordercode, "Loading Muatan", "#1976D2", "Loading", "1", "0", "0", "1", "242526",
                "14.55 WIB (Senin, 15 Mei 2017)", "17.55 WIB (Senin, 15 Mei 2017)", "-", "-6.3595777,107.2890902", "Pool HMS Karawang",
                "Pool HMS Rungkut", "Pool HMS Karawang", "14.55 WIB (Senin, 15 Mei 2017)", "-", "PIC HM Sampoerna", "Raw Material",
                "HINO 665SX", "B 1916 TOW", "ePOD", "Pool HMS Karawang", "14.55 WIB (Senin, 15 Mei 2017)"
        );
        getView().changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, HelperBridge.sActivityDetailResponseModel.getAssignmentId(), ActivityDetailActivity.class);
    }*/
}