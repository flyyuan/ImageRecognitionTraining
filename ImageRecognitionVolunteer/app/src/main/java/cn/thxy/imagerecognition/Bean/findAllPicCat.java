package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/6/25 0025.
 */

public class findAllPicCat {
    @SerializedName("picCatList")
    private List<picCatList> piccatList;

    public List<picCatList> getPiccatList() {
        return piccatList;
    }

    public void setPiccatList(List<picCatList> piccatList) {
        this.piccatList = piccatList;
    }
}
