package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class pushPicture {
    @SerializedName("picture")
    private List<picture> list;

    public List<picture> getList() {
        return list;
    }

    public void setList(List<picture> list) {
        this.list = list;
    }
}
