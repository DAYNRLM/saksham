package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ViewReportMonthData {
    @Id(autoincrement = true)
    private Long id = null;
    String monthCode;
    String year;
    String workingDays;
    @Generated(hash = 68708027)
    public ViewReportMonthData(Long id, String monthCode, String year,
            String workingDays) {
        this.id = id;
        this.monthCode = monthCode;
        this.year = year;
        this.workingDays = workingDays;
    }
    @Generated(hash = 1970980542)
    public ViewReportMonthData() {
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
    public String getWorkingDays() {
        return this.workingDays;
    }
    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }
}
