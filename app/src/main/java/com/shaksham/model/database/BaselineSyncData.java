package com.shaksham.model.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BaselineSyncData {
    @Id(autoincrement = true)
    private Long id=null;
    String shgCode;
    String basLineStatus;//sync status of baseline
    String enterMemberValue;
    String villageCode;
    String userSelectedDate;
    String todayDate;
    @Generated(hash = 1101917918)
    public BaselineSyncData(Long id, String shgCode, String basLineStatus,
            String enterMemberValue, String villageCode, String userSelectedDate,
            String todayDate) {
        this.id = id;
        this.shgCode = shgCode;
        this.basLineStatus = basLineStatus;
        this.enterMemberValue = enterMemberValue;
        this.villageCode = villageCode;
        this.userSelectedDate = userSelectedDate;
        this.todayDate = todayDate;
    }
    @Generated(hash = 18229412)
    public BaselineSyncData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getBasLineStatus() {
        return this.basLineStatus;
    }
    public void setBasLineStatus(String basLineStatus) {
        this.basLineStatus = basLineStatus;
    }
    public String getEnterMemberValue() {
        return this.enterMemberValue;
    }
    public void setEnterMemberValue(String enterMemberValue) {
        this.enterMemberValue = enterMemberValue;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getUserSelectedDate() {
        return this.userSelectedDate;
    }
    public void setUserSelectedDate(String userSelectedDate) {
        this.userSelectedDate = userSelectedDate;
    }
    public String getTodayDate() {
        return this.todayDate;
    }
    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

}
