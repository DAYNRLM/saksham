package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class TrainingInfoData {
    private String trainingId;
    private String shgCode;
    private String syncStatus;
    @Generated(hash = 1057939824)
    public TrainingInfoData(String trainingId, String shgCode, String syncStatus) {
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.syncStatus = syncStatus;
    }
    @Generated(hash = 1055897972)
    public TrainingInfoData() {
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
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

}
