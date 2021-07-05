package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BaslineQuestionSyncData {
    @Id(autoincrement = true)
    private Long id=null;
    String shgCode;
    String baslineStatus;
    String questionId;
    String answerForQuestion;
    @Generated(hash = 1495738085)
    public BaslineQuestionSyncData(Long id, String shgCode, String baslineStatus,
            String questionId, String answerForQuestion) {
        this.id = id;
        this.shgCode = shgCode;
        this.baslineStatus = baslineStatus;
        this.questionId = questionId;
        this.answerForQuestion = answerForQuestion;
    }
    @Generated(hash = 1604650211)
    public BaslineQuestionSyncData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getBaslineStatus() {
        return this.baslineStatus;
    }
    public void setBaslineStatus(String baslineStatus) {
        this.baslineStatus = baslineStatus;
    }
    public String getQuestionId() {
        return this.questionId;
    }
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public String getAnswerForQuestion() {
        return this.answerForQuestion;
    }
    public void setAnswerForQuestion(String answerForQuestion) {
        this.answerForQuestion = answerForQuestion;
    }

}
