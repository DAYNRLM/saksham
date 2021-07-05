package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BlockData {
    private String blockCode,blockName;

    @Generated(hash = 551765486)
    public BlockData(String blockCode, String blockName) {
        this.blockCode = blockCode;
        this.blockName = blockName;
    }

    @Generated(hash = 2142907853)
    public BlockData() {
    }

    public String getBlockCode() {
        return this.blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBlockName() {
        return this.blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }


}
