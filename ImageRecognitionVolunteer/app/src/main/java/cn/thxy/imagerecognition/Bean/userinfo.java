package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class userinfo {
    @SerializedName("id")
    private String id;
    @SerializedName("isNewRecord")
    private Boolean isNewRecord;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("createDta")
    private String createDta;
    @SerializedName("updateDate")
    private String updateDate;
    @SerializedName("loginName")
    private String loginName;
    @SerializedName("no")
    private String no;
    @SerializedName("name")
    private String name;
    @SerializedName("userType")
    private String userType;
    @SerializedName("loginIp")
    private String loginIp;
    @SerializedName("loginDate")
    private String loginDate;
    @SerializedName("loginFlag")
    private String loginFlag;
    @SerializedName("photo")
    private String photo;
    @SerializedName("oldLoginIp")
    private String oldLoginIp;
    @SerializedName("oldLoginDate")
    private String oldLoginDate;
    @SerializedName("admin")
    private Boolean admin;
    @SerializedName("roleNames")
    private String roleNames;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("mobile")
    private String moblie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(Boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDta() {
        return createDta;
    }

    public void setCreateDta(String createDta) {
        this.createDta = createDta;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOldLoginIp() {
        return oldLoginIp;
    }

    public void setOldLoginIp(String oldLoginIp) {
        this.oldLoginIp = oldLoginIp;
    }

    public String getOldLoginDate() {
        return oldLoginDate;
    }

    public void setOldLoginDate(String oldLoginDate) {
        this.oldLoginDate = oldLoginDate;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }
}
