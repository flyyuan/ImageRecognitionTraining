package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class returnmodifylabel {
    @SerializedName("addLabelsStatus")
    private String addLabelsStatus;

    public String getAddLabelsStatus() {
        return addLabelsStatus;
    }

    public void setAddLabelsStatus(String addLabelsStatus) {
        this.addLabelsStatus = addLabelsStatus;
    }
}
