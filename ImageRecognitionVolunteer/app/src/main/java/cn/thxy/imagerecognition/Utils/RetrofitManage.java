package cn.thxy.imagerecognition.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

import cn.thxy.imagerecognition.Api.Service;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class RetrofitManage {
    public String cookies,Cookies;
    private SharedPreferences sp,picturesp;
    private Context mContext;
    public Service service;
    public RetrofitManage(Context mContext) {
        this.mContext = mContext;
    }

    public void myRetrofit() {
        sp = mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
        cookies = sp.getString("Cookie", null);
        MyInterceptor myInterceptor = new MyInterceptor(mContext);
        HttpLoggingInterceptor logging = myInterceptor.getHttpLoggingInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(myInterceptor.LoginPost())
                .connectTimeout(10, TimeUnit.SECONDS)    //超时设置
                .readTimeout(20, TimeUnit.SECONDS)      //超时设置
                .retryOnConnectionFailure(true)         //错误重连
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://39.108.69.214:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        service = retrofit.create(Service.class);

    }
    public void loginRetrofit() {
        MyInterceptor myInterceptor = new MyInterceptor(mContext);
        HttpLoggingInterceptor logging = myInterceptor.getHttpLoggingInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                //.addInterceptor(myInterceptor.LoginPost())
                .connectTimeout(10, TimeUnit.SECONDS)    //超时设置
                .readTimeout(20, TimeUnit.SECONDS)      //超时设置
                .retryOnConnectionFailure(true)         //错误重连
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://39.108.69.214:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        service = retrofit.create(Service.class);

    }

}
