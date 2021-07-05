package com.shaksham.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class LoginInfo {
    @Id(autoincrement = true)
    private Long autogenratedLogInInfo;
    private String loginId;
    private String password;
    private String mobileNo;
    private String serverTimeStamp;
    private String appVersion;
    private String logoutDays;
    private String languageId;
    @Generated(hash = 2054237248)
    public LoginInfo(Long autogenratedLogInInfo, String loginId, String password,
            String mobileNo, String serverTimeStamp, String appVersion,
            String logoutDays, String languageId) {
        this.autogenratedLogInInfo = autogenratedLogInInfo;
        this.loginId = loginId;
        this.password = password;
        this.mobileNo = mobileNo;
        this.serverTimeStamp = serverTimeStamp;
        this.appVersion = appVersion;
        this.logoutDays = logoutDays;
        this.languageId = languageId;
    }
    @Generated(hash = 1911824992)
    public LoginInfo() {
    }
    public Long getAutogenratedLogInInfo() {
        return this.autogenratedLogInInfo;
    }
    public void setAutogenratedLogInInfo(Long autogenratedLogInInfo) {
        this.autogenratedLogInInfo = autogenratedLogInInfo;
    }
    public String getLoginId() {
        return this.loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMobileNo() {
        return this.mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getServerTimeStamp() {
        return this.serverTimeStamp;
    }
    public void setServerTimeStamp(String serverTimeStamp) {
        this.serverTimeStamp = serverTimeStamp;
    }
    public String getAppVersion() {
        return this.appVersion;
    }
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
    public String getLogoutDays() {
        return this.logoutDays;
    }
    public void setLogoutDays(String logoutDays) {
        this.logoutDays = logoutDays;
    }
    public String getLanguageId() {
        return this.languageId;
    }
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

}
