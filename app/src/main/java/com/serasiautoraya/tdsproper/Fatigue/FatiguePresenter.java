package com.serasiautoraya.tdsproper.Fatigue;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseModel.SharedPrefsModel;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperKey;
import com.serasiautoraya.tdsproper.Helper.HelperTransactionCode;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Randi Dwi Nandra on 12/04/2017.
 */

public class FatiguePresenter extends TiPresenter<FatigueView> {

    private RestConnection mRestConnection;
    private SharedPrefsModel mSharedPrefsModel;
    private FatigueSubmitSendModel mFatigueSubmitSendModel;

    public FatiguePresenter(RestConnection mRestConnection, SharedPrefsModel sharedPrefsModel) {
        this.mRestConnection = mRestConnection;
        this.mSharedPrefsModel = sharedPrefsModel;
    }

    @Override
    protected void onAttachView(@NonNull final FatigueView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void onRequestSubmitted() {
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_FATIGUE_INTERVIEW, mFatigueSubmitSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    HelperBridge.sModelLoginResponse.setIsNeedFatigueInterview("0");
                    getView().showSuccessDialog(response.getString("responseText"), "Berhasil");

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HelperKey.SERVER_DATE_FORMAT);
                    String dateToday = simpleDateFormat.format(calendar.getTime());
                    mSharedPrefsModel.apply(HelperKey.KEY_LAST_FATIGUE_INTERVIEW, dateToday + "");

//                    getView().finishActivity();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().toggleLoading(false);
                getView().showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().toggleLoading(false);
                getView().showStandardDialog("Gagal mengirim data fatigue interview, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void onSubmitClicked(
            String resultQuestion1,
            String resultQuestion2,
            String resultQuestion3,
            String resultQuestion4,
            String resultQuestion5) {

        boolean result = false;
        String notFatigueReason = "Alasan: \n";
        String resultQuestions[] = {resultQuestion1, resultQuestion2, resultQuestion3, resultQuestion4, resultQuestion5};

        for (String resultQuestion :
                resultQuestions) {
            if (!resultQuestion.equalsIgnoreCase(HelperTransactionCode.FATIGUE_YES_ANSWER_CODE)) {
                notFatigueReason += resultQuestion + " (Jawaban: Tidak) - \n";
                result = true;
            }
        }

        mFatigueSubmitSendModel = new FatigueSubmitSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                result ? HelperTransactionCode.FATIGUE_HARD_CODE : HelperTransactionCode.FATIGUE_FREE_CODE,
                notFatigueReason
        );
        getView().showConfirmationDialog();
    }

}
