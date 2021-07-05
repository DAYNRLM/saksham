package com.shaksham.model.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvaluationMasterTrainingData {
    String shgCode;
    String trainingid;
    @Generated(hash = 400485018)
    public EvaluationMasterTrainingData(String shgCode, String trainingid) {
        this.shgCode = shgCode;
        this.trainingid = trainingid;
    }
    @Generated(hash = 1191365353)
    public EvaluationMasterTrainingData() {
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getTrainingid() {
        return this.trainingid;
    }
    public void setTrainingid(String trainingid) {
        this.trainingid = trainingid;
    }


}
