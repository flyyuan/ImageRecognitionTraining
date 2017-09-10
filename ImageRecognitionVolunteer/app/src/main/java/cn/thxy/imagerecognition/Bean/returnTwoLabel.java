package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;


public class returnTwoLabel {
    @SerializedName("addErrorLabelStatus")
    private String addErrorLabelStatus;

    public String getAddErrorLabelStatus() {
        return addErrorLabelStatus;
    }

    public void setAddErrorLabelStatus(String addErrorLabelStatus) {
        this.addErrorLabelStatus = addErrorLabelStatus;
    }
}
