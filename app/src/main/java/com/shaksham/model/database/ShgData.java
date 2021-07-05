package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class ShgData {
    @Id(autoincrement = true)
    private Long id=null;
    private String shgCode;
    private String shgName, villageCode, baselineStatus, villageStatus;
    @Generated(hash = 706832373)
    public ShgData(Long id, String shgCode, String shgName, String villageCode,
            String baselineStatus, String villageStatus) {
        this.id = id;
        this.shgCode = shgCode;
        this.shgName = shgName;
        this.villageCode = villageCode;
        this.baselineStatus = baselineStatus;
        this.villageStatus = villageStatus;
    }
    @Generated(hash = 1243324880)
    public ShgData() {
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
    public String getShgName() {
        return this.shgName;
    }
    public void setShgName(String shgName) {
        this.shgName = shgName;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getBaselineStatus() {
        return this.baselineStatus;
    }
    public void setBaselineStatus(String baselineStatus) {
        this.baselineStatus = baselineStatus;
    }
    public String getVillageStatus() {
        return this.villageStatus;
    }
    public void setVillageStatus(String villageStatus) {
        this.villageStatus = villageStatus;
    }

}
