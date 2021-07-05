package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AddedTrainingShgData {
    @Id(autoincrement = true)
    private Long id=null;
    private String trainingId;
    private String shgCode;
    private String evaluationStatusForShg;
    private String shgMemberInTraining;
    @Generated(hash = 1273524686)
    public AddedTrainingShgData(Long id, String trainingId, String shgCode,
            String evaluationStatusForShg, String shgMemberInTraining) {
        this.id = id;
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.evaluationStatusForShg = evaluationStatusForShg;
        this.shgMemberInTraining = shgMemberInTraining;
    }
    @Generated(hash = 1505835077)
    public AddedTrainingShgData() {
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
    public String getEvaluationStatusForShg() {
        return this.evaluationStatusForShg;
    }
    public void setEvaluationStatusForShg(String evaluationStatusForShg) {
        this.evaluationStatusForShg = evaluationStatusForShg;
    }
    public String getShgMemberInTraining() {
        return this.shgMemberInTraining;
    }
    public void setShgMemberInTraining(String shgMemberInTraining) {
        this.shgMemberInTraining = shgMemberInTraining;
    }

}
