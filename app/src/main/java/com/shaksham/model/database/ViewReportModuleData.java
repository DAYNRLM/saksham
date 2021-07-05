package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ViewReportModuleData {
    @Id(autoincrement = true)
    private Long id = null;
    String monthCode;
    String year;
    String tringngCode;
    String moduleId;
    @Generated(hash = 1675048375)
    public ViewReportModuleData(Long id, String monthCode, String year,
            String tringngCode, String moduleId) {
        this.id = id;
        this.monthCode = monthCode;
        this.year = year;
        this.tringngCode = tringngCode;
        this.moduleId = moduleId;
    }
    @Generated(hash = 941752839)
    public ViewReportModuleData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMonthCode() {
        return this.monthCode;
    }
    public void setMonthCode(String monthCode) {
        this.monthCode = monthCode;
    }
    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getTringngCode() {
        return this.tringngCode;
    }
    public void setTringngCode(String tringngCode) {
        this.tringngCode = tringngCode;
    }
    public String getModuleId() {
        return this.moduleId;
    }
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }


}
