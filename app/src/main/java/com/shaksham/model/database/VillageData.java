package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class VillageData {

    private String villageCode,getVillageName,gpCode;

    @Generated(hash = 81050491)
    public VillageData(String villageCode, String getVillageName, String gpCode) {
        this.villageCode = villageCode;
        this.getVillageName = getVillageName;
        this.gpCode = gpCode;
    }

    @Generated(hash = 1867084313)
    public VillageData() {
    }

    public String getVillageCode() {
        return this.villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getGetVillageName() {
        return this.getVillageName;
    }

    public void setGetVillageName(String getVillageName) {
        this.getVillageName = getVillageName;
    }

    public String getGpCode() {
        return this.gpCode;
    }

    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }

}
