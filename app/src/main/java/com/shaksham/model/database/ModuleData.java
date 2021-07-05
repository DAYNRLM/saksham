package com.shaksham.model.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ModuleData {
    private String moduleCode,moduleName,languageId;

    @Generated(hash = 1151722739)
    public ModuleData(String moduleCode, String moduleName, String languageId) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.languageId = languageId;
    }

    @Generated(hash = 1336090373)
    public ModuleData() {
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }



}
