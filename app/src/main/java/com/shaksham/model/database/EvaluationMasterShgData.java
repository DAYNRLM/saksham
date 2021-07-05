package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class EvaluationMasterShgData {
    @Id(autoincrement = true)
    private Long evaluationMasterId;
    String villageCode;
    String shgCode;
   /* String firstTrainingdate;
    String maximunEvaluationdate;
    String evaluationDate;
    String evaluationStatus;*/



    String evaluationYear;
    String evaluationDate;
    String firstEvaluationDate;
    String secondEvaluationDate;
    String thirdEvaluationDate;
    String fourthEvaluationDate;
    String firstTrainingdate;
    String evaluationStatus; // this status is used for evaluation type
    String maximunEvaluationdate;
    String evaluationdonestatus;


    @Generated(hash = 1731378789)
    public EvaluationMasterShgData(Long evaluationMasterId, String villageCode,
            String shgCode, String evaluationYear, String evaluationDate,
            String firstEvaluationDate, String secondEvaluationDate,
            String thirdEvaluationDate, String fourthEvaluationDate,
            String firstTrainingdate, String evaluationStatus,
            String maximunEvaluationdate, String evaluationdonestatus) {
        this.evaluationMasterId = evaluationMasterId;
        this.villageCode = villageCode;
        this.shgCode = shgCode;
        this.evaluationYear = evaluationYear;
        this.evaluationDate = evaluationDate;
        this.firstEvaluationDate = firstEvaluationDate;
        this.secondEvaluationDate = secondEvaluationDate;
        this.thirdEvaluationDate = thirdEvaluationDate;
        this.fourthEvaluationDate = fourthEvaluationDate;
        this.firstTrainingdate = firstTrainingdate;
        this.evaluationStatus = evaluationStatus;
        this.maximunEvaluationdate = maximunEvaluationdate;
        this.evaluationdonestatus = evaluationdonestatus;
    }
    @Generated(hash = 430701503)
    public EvaluationMasterShgData() {
    }
    public Long getEvaluationMasterId() {
        return this.evaluationMasterId;
    }
    public void setEvaluationMasterId(Long evaluationMasterId) {
        this.evaluationMasterId = evaluationMasterId;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getEvaluationYear() {
        return this.evaluationYear;
    }
    public void setEvaluationYear(String evaluationYear) {
        this.evaluationYear = evaluationYear;
    }
    public String getFirstEvaluationDate() {
        return this.firstEvaluationDate;
    }
    public void setFirstEvaluationDate(String firstEvaluationDate) {
        this.firstEvaluationDate = firstEvaluationDate;
    }
    public String getSecondEvaluationDate() {
        return this.secondEvaluationDate;
    }
    public void setSecondEvaluationDate(String secondEvaluationDate) {
        this.secondEvaluationDate = secondEvaluationDate;
    }
    public String getThirdEvaluationDate() {
        return this.thirdEvaluationDate;
    }
    public void setThirdEvaluationDate(String thirdEvaluationDate) {
        this.thirdEvaluationDate = thirdEvaluationDate;
    }
    public String getFourthEvaluationDate() {
        return this.fourthEvaluationDate;
    }
    public void setFourthEvaluationDate(String fourthEvaluationDate) {
        this.fourthEvaluationDate = fourthEvaluationDate;
    }
    public String getEvaluationDate() {
        return this.evaluationDate;
    }
    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
    public String getFirstTrainingdate() {
        return this.firstTrainingdate;
    }
    public void setFirstTrainingdate(String firstTrainingdate) {
        this.firstTrainingdate = firstTrainingdate;
    }
    public String getEvaluationStatus() {
        return this.evaluationStatus;
    }
    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }
    public String getMaximunEvaluationdate() {
        return this.maximunEvaluationdate;
    }
    public void setMaximunEvaluationdate(String maximunEvaluationdate) {
        this.maximunEvaluationdate = maximunEvaluationdate;
    }
    public String getEvaluationdonestatus() {
        return this.evaluationdonestatus;
    }
    public void setEvaluationdonestatus(String evaluationdonestatus) {
        this.evaluationdonestatus = evaluationdonestatus;
    }

}
