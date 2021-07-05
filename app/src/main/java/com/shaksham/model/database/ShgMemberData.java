package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShgMemberData {
    private String shgMemberCode,shgMemberName,shgCode;

    @Generated(hash = 1948803964)
    public ShgMemberData(String shgMemberCode, String shgMemberName,
            String shgCode) {
        this.shgMemberCode = shgMemberCode;
        this.shgMemberName = shgMemberName;
        this.shgCode = shgCode;
    }

    @Generated(hash = 1743983872)
    public ShgMemberData() {
    }

    public String getShgMemberCode() {
        return this.shgMemberCode;
    }

    public void setShgMemberCode(String shgMemberCode) {
        this.shgMemberCode = shgMemberCode;
    }

    public String getShgMemberName() {
        return this.shgMemberName;
    }

    public void setShgMemberName(String shgMemberName) {
        this.shgMemberName = shgMemberName;
    }

    public String getShgCode() {
        return this.shgCode;
    }

    public void setShgCode(String shgCode) {
        this.shgCode = shgCode;
    }



}
