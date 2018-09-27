package com.serasiautoraya.tdsproper.TrainingQuestionnaire;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.tdsproper.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.tdsproper.BaseModel.BaseResponseModel;
import com.serasiautoraya.tdsproper.BaseModel.Model;
import com.serasiautoraya.tdsproper.Helper.HelperBridge;
import com.serasiautoraya.tdsproper.Helper.HelperUrl;
import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by randi on 07/08/2017.
 */

public class QuestionnairePresenter extends TiPresenter<QuestionnaireView> {

    private RestConnection mRestConnection;
    private QuestionnaireSendModel mQuestionnaireSendModel;
    private QuestionnaireResponseModel mQuestionnaireResponseModel;
    private QuestionnaireResultSendModel mQuestionnaireResultSendModel;

    public QuestionnairePresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    @Override
    protected void onAttachView(@NonNull final QuestionnaireView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void initializeQuestionnaire() {
        /*
        * TODO Change this send model creation
        * */

        mQuestionnaireSendModel = new QuestionnaireSendModel();

        final QuestionnaireView questionnaireView = getView();
        questionnaireView.toggleLoading(true);
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_QUESTIONNAIRE, mQuestionnaireSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                mQuestionnaireResponseModel = Model.getModelInstance(response.getData()[0], QuestionnaireResponseModel.class);
                generateQuestionnaireForm(mQuestionnaireResponseModel);
                questionnaireView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                    /*
                    * TODO change this!
                    * */
                questionnaireView.showToast(response);
                questionnaireView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                    /*
                    * TODO change this!
                    * */
                questionnaireView.showToast("ERROR: " + error.toString());
                questionnaireView.toggleLoading(false);
            }
        });


        /*
        * TODO Change this dummy logic
        * */

        HashMap<String, QuestionnaireInputModel> questionnaireInputModelHashMap = new HashMap<>();
        String[] questionCode = {"A-01", "A-02", "A-03", "A-04", "A-05", "A-06", "A-07", "A-08"};

        for (int i = 0; i < questionCode.length; i++) {
            questionnaireInputModelHashMap.put(questionCode[i], new QuestionnaireInputModel(
                    questionCode[i],
                    "Ini adalah pertanyaan ke-" + i,
                    "Jawaban " + i + " opsi ke A",
                    "Jawaban " + i + " opsi ke B",
                    "Jawaban " + i + " opsi ke C",
                    "Jawaban " + i + " opsi ke D"
            ));
        }

        getView().setQuestionnaireForm(questionnaireInputModelHashMap, questionCode);
    }

    private void generateQuestionnaireForm(QuestionnaireResponseModel questionnaireResponseModel) {

    }

    public void onSubmitClicked(HashMap<String, String> questionnaireResult) {
        /*
        * TODO Change this to real Model creation
        * */
        
        String logAnwers = "";
        mQuestionnaireResultSendModel = new QuestionnaireResultSendModel();
        for (Map.Entry<String, String> entry : questionnaireResult.entrySet()) {
            String questionCode = entry.getKey();
            String answerOptionCode = entry.getValue();
            logAnwers += "(" + questionCode + " :: " + answerOptionCode + ")";
        }
        getView().showConfirmationDialog();
        Log.d("QUESTIONNAIRE", logAnwers);
    }

    public void onAnswersSubmitted(){
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_QUESTIONNAIRE_ANSWER, mQuestionnaireResultSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(response.getString("responseText"), "Berhasil");
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
                getView().showStandardDialog("Gagal menyimpan hasil kuesioner, silahkan periksa koneksi Anda kemudian coba kembali", "Perhatian");
            }
        });
    }

}
