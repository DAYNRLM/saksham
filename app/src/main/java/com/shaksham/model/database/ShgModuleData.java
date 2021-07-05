package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShgModuleData {
    @Id(autoincrement = true)
    private Long id=null;
    private String villgeCode;
    private String shgCodeforModule;
    private String moduleId;
    private String moduleName;
    private String moduleCount;
    private String languageId;
    @Generated(hash = 1725506203)
    public ShgModuleData(Long id, String villgeCode, String shgCodeforModule,
            String moduleId, String moduleName, String moduleCount,
            String languageId) {
        this.id = id;
        this.villgeCode = villgeCode;
        this.shgCodeforModule = shgCodeforModule;
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCount = moduleCount;
        this.languageId = languageId;
    }
    @Generated(hash = 623650145)
    public ShgModuleData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVillgeCode() {
        return this.villgeCode;
    }
    public void setVillgeCode(String villgeCode) {
        this.villgeCode = villgeCode;
    }
    public String getShgCodeforModule() {
        return this.shgCodeforModule;
    }
    public void setShgCodeforModule(String shgCodeforModule) {
        this.shgCodeforModule = shgCodeforModule;
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
    public String getLanguageId() {
        return this.languageId;
    }
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
