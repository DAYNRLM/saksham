package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvaluationSyncQuestionData {

    @Id(autoincrement = true)
    private Long id=null;
    private String trainingId;
    private String shgCode;
    private String questionCode;
    private String answerValue;
    private String evaluationSyncStatus;
    @Generated(hash = 531509460)
    public EvaluationSyncQuestionData(Long id, String trainingId, String shgCode,
            String questionCode, String answerValue, String evaluationSyncStatus) {
        this.id = id;
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.questionCode = questionCode;
        this.answerValue = answerValue;
        this.evaluationSyncStatus = evaluationSyncStatus;
    }
    @Generated(hash = 388536766)
    public EvaluationSyncQuestionData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTrainingId() {
        return this.trainingId;
    }
    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getQuestionCode() {
        return this.questionCode;
    }
    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }
    public String getAnswerValue() {
        return this.answerValue;
    }
    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }
    public String getEvaluationSyncStatus() {
        return this.evaluationSyncStatus;
    }
    public void setEvaluationSyncStatus(String evaluationSyncStatus) {
        this.evaluationSyncStatus = evaluationSyncStatus;
    }

}
