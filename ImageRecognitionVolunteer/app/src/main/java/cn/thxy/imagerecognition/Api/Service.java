package cn.thxy.imagerecognition.Api;



import cn.thxy.imagerecognition.Bean.deleteWrite;
import cn.thxy.imagerecognition.Bean.findAllPicCat;

import cn.thxy.imagerecognition.Bean.modifyPwdReturn;
import cn.thxy.imagerecognition.Bean.integralDataList;
import cn.thxy.imagerecognition.Bean.loginData;
import cn.thxy.imagerecognition.Bean.pushPicture;

import cn.thxy.imagerecognition.Bean.returnDelete;
import cn.thxy.imagerecognition.Bean.returnInsert;

import cn.thxy.imagerecognition.Bean.updateInfoByMobile;
import cn.thxy.imagerecognition.Bean.updatewritelabel;
import cn.thxy.imagerecognition.Bean.userinfo;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    //登录
    @FormUrlEncoded
    @POST("/xxzx/a/login?__ajax=true")
    Call<loginData> login(@Field("username") String username,
                          @Field("password") String password,
                          @Field("mobileLogin") boolean mobileLogin);

    //获取图片信息
    @GET("/xxzx/a/tpsb/pushPicture")
    Call<pushPicture> getpicture();

    //注销
    @GET("/xxzx/a/logout")
    Call<ResponseBody> quit();

    //自动登录
    @GET("/xxzx/a?login")
    Call<loginData> autologin();

    //提交第一层label信息
    @FormUrlEncoded
    @POST("/xxzx/a/tpsb/addPictureLabelByUser")
    Call<ResponseBody> postlabel(@Field("picId") String picId,
                                      @Field("labels") String labels,
                                      @Field("url") String url,
                                      @Field("errorLabel") String errorLabel,
                                      @Field("picCatId") String picCatId);

    //提交第二层标签信息
    @FormUrlEncoded
    @POST("/xxzx/a/tpsb/addTwoCandidateLabel")
    Call<ResponseBody> posttwolabel(@Field("picId") String picId,
                                    @Field("oneLabelId") String oneLabelId,
                                    @Field("twoCandidateLabel") String twoCandidateLabel);

    //注册
    @FormUrlEncoded
    @POST("/xxzx/a/sys/user/save")
    Call<ResponseBody> register(@Field("company.id") String companyId,
                                @Field("office.id") String officeId,
                                @Field("roleIdList") String roleIdList,
                                @Field("no") String phonenum,
                                @Field("name") String name,
                                @Field("loginName") String username,
                                @Field("newPassword") String password,
                                @Field("confirmNewPassword") String cpassword,
                                @Field("email") String email,
                                @Field("mobile") String mobile,
                                @Field("phone") String phone);

    //积分
    @GET("xxzx/a/tpsb/userIntegral/getIntegral")
    Call<integralDataList> getintegra();

    //获取个人详细信息
    @GET("xxzx/a/sys/user/infoByMobile")
    Call<userinfo> getuserinfo();

    //修改个人详细信息
    @FormUrlEncoded
    @POST("xxzx/a/sys/user/updateInfoByMobile")
    Call<updateInfoByMobile> updateinfo(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("phone") String phone,
                                        @Field("mobile") String mobile);

    //修改密码
    @FormUrlEncoded
    @POST("xxzx/a /sys/user/modifyPwdByMobile")
    Call<modifyPwdReturn> alterpassword(@Field("oldPassword") String oldPassword,
                                        @Field("newPassword") String newPassword);

    //删除用户历史选择的标签
    @FormUrlEncoded
    @POST("xxzx/a/tpsb/deleteWrite")
    Call<deleteWrite> deletewritelabel(@Field("picId") String picId,
                                       @Field("label") String label);

    //修改用户历史填过的标签
    @FormUrlEncoded
    @POST("/xxzx/a/tpsb/updateWrite")
    Call<updatewritelabel> updatewritelabel(@Field("picId") String picId,
                                            @Field("label") String label,
                                            @Field("targetLabel") String targetLabel);

    //获取用户历史填过的标签
    @GET("xxzx/a/tpsb/findHistoryWriteLabels")
    Call<ResponseBody> findhistorywritelabel();


    //获取所有兴趣分类
    @GET("xxzx/a/tpsb/tpsbCat/findAllPicCat")
    Call<findAllPicCat> findallpiccat();

    //设置用户兴趣
    @FormUrlEncoded
    @POST("/xxzx/a/tpsb/userCat/insert")
    Call<returnInsert> inserthobby(@Field("catList") String catList);

    //获取当前用户的兴趣
    @GET("/xxzx/a/tpsb/userCat/getUserCat")
    Call<ResponseBody> getuserhobby();

    //删除用户兴趣
    @FormUrlEncoded
    @POST("/xxzx/a/tpsb/userCat/delete")
    Call<returnDelete> deletehobby(@Field("catId") String catId);

}
