package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;



public class deleteWrite {
    @SerializedName("deleteStatus")
    private String deleteStatus;

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
