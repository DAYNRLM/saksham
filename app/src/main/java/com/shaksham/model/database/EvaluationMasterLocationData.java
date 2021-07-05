package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvaluationMasterLocationData {
    String blockCode;
    String gpCode;
    String villageCode;
    @Generated(hash = 813440579)
    public EvaluationMasterLocationData(String blockCode, String gpCode,
            String villageCode) {
        this.blockCode = blockCode;
        this.gpCode = gpCode;
        this.villageCode = villageCode;
    }
    @Generated(hash = 1904330709)
    public EvaluationMasterLocationData() {
    }
    public String getBlockCode() {
        return this.blockCode;
    }
    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }
    public String getGpCode() {
        return this.gpCode;
    }
    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }


}
