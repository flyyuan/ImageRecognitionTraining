package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/25 0025.
 */

public class returnInsert {
    @SerializedName("insertStatus")
    private String insertStatus;

    public String getInsertStatus() {
        return insertStatus;
    }

    public void setInsertStatus(String insertStatus) {
        this.insertStatus = insertStatus;
    }
}
