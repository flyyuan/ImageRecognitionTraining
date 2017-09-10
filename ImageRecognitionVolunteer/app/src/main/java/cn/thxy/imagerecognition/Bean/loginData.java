package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class loginData {
    @SerializedName("id")
    private String id;
    @SerializedName("loginName")
    private String loginName;
    @SerializedName("name")
    private String name;
    @SerializedName("mobileLogin")
    private boolean mobileLogin;
    @SerializedName("sessionid")
    private String sessionid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(boolean mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
