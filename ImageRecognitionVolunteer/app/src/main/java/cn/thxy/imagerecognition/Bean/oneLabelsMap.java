package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class oneLabelsMap {
    @SerializedName("picId")
    private String picId;
    @SerializedName("url")
    private String url;
    @SerializedName("errorLabel")
    private String errorLabel;
    @SerializedName("labels")
    private List<String> labels;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErrorLabel() {
        return errorLabel;
    }

    public void setErrorLabel(String errorLabel) {
        this.errorLabel = errorLabel;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
