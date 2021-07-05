package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class AddedTrainingsData {
@Id(autoincrement = true)
    private Long id=null;
    private String trainingId;
    private String blockcode;
    private String gpCode;
    private String villageCode;
    private String trainingEvaluationDate;
    private String trainingAddedDateDate;
    private String trainingSyncDateTime;
    private String evaluationStatusForTraining;
    private String subTotalShgMember;
    private String otherParticipant;
    private String totalParticipant;
    private String evaluationMaximumDate;
    @Generated(hash = 1877375737)
    public AddedTrainingsData(Long id, String trainingId, String blockcode,
            String gpCode, String villageCode, String trainingEvaluationDate,
            String trainingAddedDateDate, String trainingSyncDateTime,
            String evaluationStatusForTraining, String subTotalShgMember,
            String otherParticipant, String totalParticipant,
            String evaluationMaximumDate) {
        this.id = id;
        this.trainingId = trainingId;
        this.blockcode = blockcode;
        this.gpCode = gpCode;
        this.villageCode = villageCode;
        this.trainingEvaluationDate = trainingEvaluationDate;
        this.trainingAddedDateDate = trainingAddedDateDate;
        this.trainingSyncDateTime = trainingSyncDateTime;
        this.evaluationStatusForTraining = evaluationStatusForTraining;
        this.subTotalShgMember = subTotalShgMember;
        this.otherParticipant = otherParticipant;
        this.totalParticipant = totalParticipant;
        this.evaluationMaximumDate = evaluationMaximumDate;
    }
    @Generated(hash = 229916886)
    public AddedTrainingsData() {
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
    public String getBlockcode() {
        return this.blockcode;
    }
    public void setBlockcode(String blockcode) {
        this.blockcode = blockcode;
    }
    public String getGpCode() {
        return this.gpCode;
    }
    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getTrainingEvaluationDate() {
        return this.trainingEvaluationDate;
    }
    public void setTrainingEvaluationDate(String trainingEvaluationDate) {
        this.trainingEvaluationDate = trainingEvaluationDate;
    }
    public String getTrainingAddedDateDate() {
        return this.trainingAddedDateDate;
    }
    public void setTrainingAddedDateDate(String trainingAddedDateDate) {
        this.trainingAddedDateDate = trainingAddedDateDate;
    }
    public String getTrainingSyncDateTime() {
        return this.trainingSyncDateTime;
    }
    public void setTrainingSyncDateTime(String trainingSyncDateTime) {
        this.trainingSyncDateTime = trainingSyncDateTime;
    }
    public String getEvaluationStatusForTraining() {
        return this.evaluationStatusForTraining;
    }
    public void setEvaluationStatusForTraining(String evaluationStatusForTraining) {
        this.evaluationStatusForTraining = evaluationStatusForTraining;
    }
    public String getSubTotalShgMember() {
        return this.subTotalShgMember;
    }
    public void setSubTotalShgMember(String subTotalShgMember) {
        this.subTotalShgMember = subTotalShgMember;
    }
    public String getOtherParticipant() {
        return this.otherParticipant;
    }
    public void setOtherParticipant(String otherParticipant) {
        this.otherParticipant = otherParticipant;
    }
    public String getTotalParticipant() {
        return this.totalParticipant;
    }
    public void setTotalParticipant(String totalParticipant) {
        this.totalParticipant = totalParticipant;
    }
    public String getEvaluationMaximumDate() {
        return this.evaluationMaximumDate;
    }
    public void setEvaluationMaximumDate(String evaluationMaximumDate) {
        this.evaluationMaximumDate = evaluationMaximumDate;
    }

}
