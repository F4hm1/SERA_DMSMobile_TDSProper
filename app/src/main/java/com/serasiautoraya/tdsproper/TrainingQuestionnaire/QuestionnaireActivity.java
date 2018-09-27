package com.serasiautoraya.tdsproper.TrainingQuestionnaire;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.tdsproper.RestClient.RestConnection;
import com.serasiautoraya.tdsproper.R;
import com.serasiautoraya.tdsproper.util.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by randi on 07/08/2017.
 */

public class QuestionnaireActivity extends TiActivity<QuestionnairePresenter, QuestionnaireView> implements QuestionnaireView {

    @BindView(R.id.questionnaire_lin_container)
    LinearLayout mLinContainer;

    @BindView(R.id.questionnaire_btn_submit)
    Button mBtnSubmit;

    @BindView(R.id.questionnaire_sv_container)
    ScrollView mSvContainer;

    private ProgressDialog mProgressDialog;
    private HashMap<String, RadioGroup> mHashAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_questionnaire);
        ButterKnife.bind(this);
    }

    @Override
    public boolean getValidationForm() {
        for (Map.Entry<String, RadioGroup> entry : mHashAnswers.entrySet()) {
            RadioGroup radioGroup = entry.getValue();
            if(radioGroup.getCheckedRadioButtonId() == -1){
                return false;
            }
        }

        /*
        * TODO Change this
        * */
        return true;
    }

    @Override
    public void initialize() {
        mSvContainer.setVisibility(View.GONE);
        getPresenter().initializeQuestionnaire();
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(QuestionnaireActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(QuestionnaireActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, QuestionnaireActivity.this, Title);
    }

    @NonNull
    @Override
    public QuestionnairePresenter providePresenter() {
        return new QuestionnairePresenter(new RestConnection(QuestionnaireActivity.this));
    }

    @Override
    public void showConfirmationDialog() {
        CharSequence textMsg = Html.fromHtml("Apakah Anda yakin untuk mengirim hasil kuesioner ini?");
        HelperUtil.showConfirmationAlertDialog(textMsg, QuestionnaireActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onAnswersSubmitted();
            }
        });
    }

    @Override
    public void showSuccessDialog() {

    }

    @Override
    @OnClick(R.id.questionnaire_btn_submit)
    public void onClickSubmit() {
        if(getValidationForm()){
            HashMap<String, String> questionnaireResult = new HashMap<>();
            for (Map.Entry<String, RadioGroup> entry : mHashAnswers.entrySet()) {
                String questionCode = entry.getKey();
                String answerOptionCode = getAnswerOptionCode(entry.getValue().getCheckedRadioButtonId());
                questionnaireResult.put(questionCode, answerOptionCode);
            }
            getPresenter().onSubmitClicked(questionnaireResult);
        }else{
            Snackbar.make(findViewById(android.R.id.content), "Mohon jawab semua pertanyaan.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setQuestionnaireForm(HashMap<String, QuestionnaireInputModel> questionnaireInputModelHashMap, String[] questionnaireCodes) {
        mHashAnswers = new HashMap<>();
        if (questionnaireInputModelHashMap.size() > 0) {
            mSvContainer.setVisibility(View.VISIBLE);
        } else {
            mSvContainer.setVisibility(View.GONE);
        }

        if (mLinContainer.getChildCount() > 0) {
            mLinContainer.removeAllViews();
        }

        for (int i = 0; i < questionnaireCodes.length; i++) {
            View v = LayoutInflater.from(QuestionnaireActivity.this).inflate(R.layout.single_training_questionnaire, null);
            TextView tvQuestion = (TextView) v.findViewById(R.id.questionnaire_tv_question);
            String currentQuestionCode = questionnaireCodes[i];
            RadioGroup rgAnswers = (RadioGroup) v.findViewById(R.id.questionnaire_rg_answers);
            RadioButton rbOptionA = (RadioButton) v.findViewById(R.id.questionnaire_rb_option_1);
            RadioButton rbOptionB = (RadioButton) v.findViewById(R.id.questionnaire_rb_option_2);
            RadioButton rbOptionC = (RadioButton) v.findViewById(R.id.questionnaire_rb_option_3);
            RadioButton rbOptionD = (RadioButton) v.findViewById(R.id.questionnaire_rb_option_4);

            tvQuestion.setText(questionnaireInputModelHashMap.get(currentQuestionCode).getQuestion());
            rbOptionA.setText(questionnaireInputModelHashMap.get(currentQuestionCode).getOption1());
            rbOptionB.setText(questionnaireInputModelHashMap.get(currentQuestionCode).getOption2());
            rbOptionC.setText(questionnaireInputModelHashMap.get(currentQuestionCode).getOption3());
            rbOptionD.setText(questionnaireInputModelHashMap.get(currentQuestionCode).getOption4());

            mHashAnswers.put(currentQuestionCode, rgAnswers);
            mLinContainer.addView(v);
        }
    }

    private String getAnswerOptionCode(int rbId){
        switch (rbId){
            case R.id.questionnaire_rb_option_1:
                return "A";
            case R.id.questionnaire_rb_option_2:
                return "B";
            case R.id.questionnaire_rb_option_3:
                return "C";
            case R.id.questionnaire_rb_option_4:
                return "D";
            default:
                return "";
        }
    }

}


