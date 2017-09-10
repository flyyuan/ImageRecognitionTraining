package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class picture {
    @SerializedName("id")
    private String id;
    @SerializedName("isNewRecord")
    private Boolean isNewRecord;
    @SerializedName("tagStatus")
    private String tagStatus;
    @SerializedName("url")
    private String url;
    @SerializedName("picName")
    private String picName;
    @SerializedName("labelStatus")
    private String labelStatus;
    @SerializedName("labels")
    private List<String> list;
    @SerializedName("oneLabelsMap")
    private Map oneLabelsMap;
    @SerializedName("twoCandidateLabels")
    private List<String> twoCandidateLabels;
    @SerializedName("picCatMap")
    private Map picCatMap;




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

    public String getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(String tagStatus) {
        this.tagStatus = tagStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map getOneLabelsMap() {
        return oneLabelsMap;
    }

    public void setOneLabelsMap(Map oneLabelsMap) {
        this.oneLabelsMap = oneLabelsMap;
    }

    public List<String> getTwoCandidateLabels() {
        return twoCandidateLabels;
    }

    public void setTwoCandidateLabels(List<String> twoCandidateLabels) {
        this.twoCandidateLabels = twoCandidateLabels;
    }

    public Map getPicCatMap() {
        return picCatMap;
    }

    public void setPicCatMap(Map picCatMap) {
        this.picCatMap = picCatMap;
    }
}
