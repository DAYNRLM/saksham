package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class TrainingModuleInfo {
    private String trainingId;
    private String shgCode;
    private String moduleCode;
    private String syncStatus;
    @Generated(hash = 237403189)
    public TrainingModuleInfo(String trainingId, String shgCode, String moduleCode,
            String syncStatus) {
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.moduleCode = moduleCode;
        this.syncStatus = syncStatus;
    }
    @Generated(hash = 481469777)
    public TrainingModuleInfo() {
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
    public String getModuleCode() {
        return this.moduleCode;
    }
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
    public String getSyncStatus() {
        return this.syncStatus;
    }
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

}
