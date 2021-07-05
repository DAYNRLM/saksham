package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class ViewReportData {
    @Id(autoincrement = true)
    private Long id = null;
    String monthCode;
    String year;
    String baselineDone;
    String evaluationDone;
    String shgUniquelyDone;
    @Keep
    @Generated(hash = 1441542841)
    public ViewReportData(Long id, String monthCode, String year,
            String baselineDone, String evaluationDone, String shgUniquelyDone,
            String workinDays) {
        this.id = id;
        this.monthCode = monthCode;
        this.year = year;
        this.baselineDone = baselineDone;
        this.evaluationDone = evaluationDone;
        this.shgUniquelyDone = shgUniquelyDone;

    }
    @Generated(hash = 2057810339)
    public ViewReportData() {
    }
    @Generated(hash = 891710998)
    public ViewReportData(Long id, String monthCode, String year,
            String baselineDone, String evaluationDone, String shgUniquelyDone) {
        this.id = id;
        this.monthCode = monthCode;
        this.year = year;
        this.baselineDone = baselineDone;
        this.evaluationDone = evaluationDone;
        this.shgUniquelyDone = shgUniquelyDone;
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
    public String getBaselineDone() {
        return this.baselineDone;
    }
    public void setBaselineDone(String baselineDone) {
        this.baselineDone = baselineDone;
    }
    public String getEvaluationDone() {
        return this.evaluationDone;
    }
    public void setEvaluationDone(String evaluationDone) {
        this.evaluationDone = evaluationDone;
    }
    public String getShgUniquelyDone() {
        return this.shgUniquelyDone;
    }
    public void setShgUniquelyDone(String shgUniquelyDone) {
        this.shgUniquelyDone = shgUniquelyDone;
    }
}
