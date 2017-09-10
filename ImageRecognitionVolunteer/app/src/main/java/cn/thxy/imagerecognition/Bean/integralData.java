package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/4 0004.
 */

public class integralData {
    @SerializedName("isNewRecord")
    private boolean isNewRecord;
    @SerializedName("createDate")
    private String createDate;
    @SerializedName("updateDate")
    private String updateDate;
    @SerializedName("picid")
    private String picid;
    @SerializedName("userid")
    private String userid;
    @SerializedName("label")
    private String label;
    @SerializedName("integral")
    private String integral;
    @SerializedName("picUrl")
    private String picUrl;
    @SerializedName("integralSum")
    private int integralSum;

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getPicid() {
        return picid;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getIntegralSum() {
        return integralSum;
    }

    public void setIntegralSum(int integralSum) {
        this.integralSum = integralSum;
    }
}
