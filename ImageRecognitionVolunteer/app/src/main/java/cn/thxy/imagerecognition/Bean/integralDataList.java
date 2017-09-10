package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4 0004.
 */

public class integralDataList {
    @SerializedName("integralList")
    private List<integralData> integralList;

    public List<integralData> getIntegralList() {
        return integralList;
    }

    public void setIntegralList(List<integralData> integralList) {
        this.integralList = integralList;
    }
}
