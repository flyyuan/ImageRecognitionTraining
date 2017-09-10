package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;



public class updateInfoByMobile {
    @SerializedName("updateInfoStatus")
    private String updateInfoStatus;

    public String getUpdateInfoStatus() {
        return updateInfoStatus;
    }

    public void setUpdateInfoStatus(String updateInfoStatus) {
        this.updateInfoStatus = updateInfoStatus;
    }
}
