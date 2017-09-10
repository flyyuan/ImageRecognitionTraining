package cn.thxy.imagerecognition.Bean;

import com.google.gson.annotations.SerializedName;



public class returnUserHobby {
    @SerializedName("catId")
    private String catId;
    @SerializedName("name")
    private String name;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
