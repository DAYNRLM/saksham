package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GpData {
    private String blockCode,gpCode,gpName;

    @Generated(hash = 1823163234)
    public GpData(String blockCode, String gpCode, String gpName) {
        this.blockCode = blockCode;
        this.gpCode = gpCode;
        this.gpName = gpName;
    }

    @Generated(hash = 1048622974)
    public GpData() {
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

    public String getGpName() {
        return this.gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }


}
