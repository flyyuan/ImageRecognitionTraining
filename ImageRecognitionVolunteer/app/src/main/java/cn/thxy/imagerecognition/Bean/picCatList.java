package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/25 0025.
 */

public class picCatList {
    @SerializedName("id")
    private String id;
    @SerializedName("isNewRecord")
    private Boolean isNewRecord;
    @SerializedName("parentId")
    private String parentId;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("deleteStatus")
    private String sortOrder;
    @SerializedName("isParent")
    private String isParent;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
}
