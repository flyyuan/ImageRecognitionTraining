package cn.thxy.imagerecognition.Bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class twoCandidateLabels {
    @SerializedName("picId")
    private String picId;
    @SerializedName("oneLabelId")
    private String oneLabelId;
    @SerializedName("twoCandidateLabel")
    private List<String> twoCandidateLabel;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getOneLabelId() {
        return oneLabelId;
    }

    public void setOneLabelId(String oneLabelId) {
        this.oneLabelId = oneLabelId;
    }

    public List<String> getTwoCandidateLabel() {
        return twoCandidateLabel;
    }

    public void setTwoCandidateLabel(List<String> twoCandidateLabel) {
        this.twoCandidateLabel = twoCandidateLabel;
    }
}
