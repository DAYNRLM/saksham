package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AddedTrainingShgModuleData {
    @Id(autoincrement = true)
    private Long id=null;
    private String trainingId;
    private String shgCode;
    private String moduleId;
    private String moduleName;
    private String moduleCount;
    @Generated(hash = 1241888226)
    public AddedTrainingShgModuleData(Long id, String trainingId, String shgCode,
            String moduleId, String moduleName, String moduleCount) {
        this.id = id;
        this.trainingId = trainingId;
        this.shgCode = shgCode;
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCount = moduleCount;
    }
    @Generated(hash = 1087621528)
    public AddedTrainingShgModuleData() {
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
    public String getModuleId() {
        return this.moduleId;
    }
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public String getModuleName() {
        return this.moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getModuleCount() {
        return this.moduleCount;
    }
    public void setModuleCount(String moduleCount) {
        this.moduleCount = moduleCount;
    }


}
