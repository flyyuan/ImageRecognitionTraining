package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class updatewritelabel {
    //还可以修改次数
    @SerializedName("canUpdateNum")
    private String canUpdateNum;
    //修改是否成功
    @SerializedName("updateStatus")
    private String updateStatus;

    public String getCanUpdateNum() {
        return canUpdateNum;
    }

    public void setCanUpdateNum(String canUpdateNum) {
        this.canUpdateNum = canUpdateNum;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }
}
