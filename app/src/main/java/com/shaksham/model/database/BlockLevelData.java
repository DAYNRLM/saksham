package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class BlockLevelData {
  /*  (@Id )
    private Long autoId;*/
    private String blockName;
    private String blockCode;
    private String gpCode;
    private String gpName;
    private String villageName;
    private String villageCode;
    private String shgName;
    private String shgCode;
    private String baseLineStatus;
    @Generated(hash = 1832287548)
    public BlockLevelData(String blockName, String blockCode, String gpCode,
            String gpName, String villageName, String villageCode, String shgName,
            String shgCode, String baseLineStatus) {
        this.blockName = blockName;
        this.blockCode = blockCode;
        this.gpCode = gpCode;
        this.gpName = gpName;
        this.villageName = villageName;
        this.villageCode = villageCode;
        this.shgName = shgName;
        this.shgCode = shgCode;
        this.baseLineStatus = baseLineStatus;
    }
    @Generated(hash = 2139438458)
    public BlockLevelData() {
    }
    public String getBlockName() {
        return this.blockName;
    }
    public void setBlockName(String blockName) {
        this.blockName = blockName;
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
    public String getVillageName() {
        return this.villageName;
    }
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
    public String getVillageCode() {
        return this.villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getShgName() {
        return this.shgName;
    }
    public void setShgName(String shgName) {
        this.shgName = shgName;
    }
    public String getShgCode() {
        return this.shgCode;
    }
    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }
    public String getBaseLineStatus() {
        return this.baseLineStatus;
    }
    public void setBaseLineStatus(String baseLineStatus) {
        this.baseLineStatus = baseLineStatus;
    }

}
