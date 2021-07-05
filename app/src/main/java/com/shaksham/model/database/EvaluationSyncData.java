package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvaluationSyncData {
    @Id(autoincrement = true)
    private Long id=null;
    private String trainingId;
    private String shgCode;
    private String evaluationDate;
    private String evaluationSyncStatus;
    @Generated(hash = 760125702)
    public EvaluationSyncData(Long id, String trainingId, String shgCode,
            String evaluationDate, String evaluationSyncStatus) {
        this.id = id;
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.evaluationDate = evaluationDate;
        this.evaluationSyncStatus = evaluationSyncStatus;
    }
    @Generated(hash = 1164814637)
    public EvaluationSyncData() {
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
    public String getEvaluationDate() {
        return this.evaluationDate;
    }
    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
    public String getEvaluationSyncStatus() {
        return this.evaluationSyncStatus;
    }
    public void setEvaluationSyncStatus(String evaluationSyncStatus) {
        this.evaluationSyncStatus = evaluationSyncStatus;
    }

}
