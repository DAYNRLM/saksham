package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class TrainingShgAndMemberData {
    private String trainingId;
    private String shgCode;
    private String shgMemberCode;
    private String syncStatus;
    @Generated(hash = 1907975916)
    public TrainingShgAndMemberData(String trainingId, String shgCode,
            String shgMemberCode, String syncStatus) {
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.shgMemberCode = shgMemberCode;
        this.syncStatus = syncStatus;
    }
    @Generated(hash = 1462779002)
    public TrainingShgAndMemberData() {
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
    public String getShgMemberCode() {
        return this.shgMemberCode;
    }
    public void setShgMemberCode(String shgMemberCode) {
        this.shgMemberCode = shgMemberCode;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

}
