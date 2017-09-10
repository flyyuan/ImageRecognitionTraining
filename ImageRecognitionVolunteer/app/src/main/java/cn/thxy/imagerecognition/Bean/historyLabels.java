package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;



public class historyLabels {
    @SerializedName("id")
    private String id;
    @SerializedName("updateDate")
    private String updateDate;
    @SerializedName("label")
    private String label;
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
