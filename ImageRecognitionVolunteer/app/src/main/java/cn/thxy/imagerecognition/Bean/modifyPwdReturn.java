package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;



public class modifyPwdReturn {
    @SerializedName("modifyPwdStatus")
    private String modifyPwdStatus;

    public String getModifyPwdStatus() {
        return modifyPwdStatus;
    }

    public void setModifyPwdStatus(String modifyPwdStatus) {
        this.modifyPwdStatus = modifyPwdStatus;
    }
}
