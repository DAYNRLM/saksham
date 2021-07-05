package com.shaksham.model.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvaluationSyncShgData {
    @Id(autoincrement = true)
    private Long id=null;
    private String trainingCode;
    private String shgCode;
    private String totalMember;
    private String evaluationSyncStatus;
    private String evaluationDate;
    private String villageCode;
    private String evaluationYear;
    private String evaluationType;
    private String latLong;
    private String EvaluationMemberCount;
    @Generated(hash = 1004065190)
    public EvaluationSyncShgData(Long id, String trainingCode, String shgCode,
            String totalMember, String evaluationSyncStatus, String evaluationDate,
            String villageCode, String evaluationYear, String evaluationType,
            String latLong, String EvaluationMemberCount) {
        this.id = id;
        this.trainingCode = trainingCode;
        this.shgCode = shgCode;
        this.totalMember = totalMember;
        this.evaluationSyncStatus = evaluationSyncStatus;
        this.evaluationDate = evaluationDate;
        this.villageCode = villageCode;
        this.evaluationYear = evaluationYear;
        this.evaluationType = evaluationType;
        this.latLong = latLong;
        this.EvaluationMemberCount = EvaluationMemberCount;
    }
    @Generated(hash = 210327741)
    public EvaluationSyncShgData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTrainingCode() {
        return this.trainingCode;
    }
    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getTotalMember() {
        return this.totalMember;
    }
    public void setTotalMember(String totalMember) {
        this.totalMember = totalMember;
    }
    public String getEvaluationSyncStatus() {
        return this.evaluationSyncStatus;
    }
    public void setEvaluationSyncStatus(String evaluationSyncStatus) {
        this.evaluationSyncStatus = evaluationSyncStatus;
    }
    public String getEvaluationDate() {
        return this.evaluationDate;
    }
    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getEvaluationYear() {
        return this.evaluationYear;
    }
    public void setEvaluationYear(String evaluationYear) {
        this.evaluationYear = evaluationYear;
    }
    public String getEvaluationType() {
        return this.evaluationType;
    }
    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }
    public String getLatLong() {
        return this.latLong;
    }
    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }
    public String getEvaluationMemberCount() {
        return this.EvaluationMemberCount;
    }
    public void setEvaluationMemberCount(String EvaluationMemberCount) {
        this.EvaluationMemberCount = EvaluationMemberCount;
    }

}
