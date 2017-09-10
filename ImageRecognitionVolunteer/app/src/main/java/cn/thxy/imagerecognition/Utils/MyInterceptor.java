package cn.thxy.imagerecognition.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.content.Context.MODE_PRIVATE;



public class MyInterceptor implements okhttp3.Interceptor{
    private Context mContext;
    public String cookies;
    private SharedPreferences sp;

    public MyInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    public  MyInterceptor LoginPost() {
        MyInterceptor interceptor = new MyInterceptor(mContext) {
            public Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            sp= mContext.getSharedPreferences("data", MODE_PRIVATE);
            cookies=sp.getString("Cookie",null);
            String cookie="jeesite.session.id="+cookies;
           // Log.e("从本地获取到的Cookie",cookie);
            builder.header("Cookie",cookie).build();
            //Log.e("提交到服务器的Cookie",cookie);
            Request.Builder requestBuilder =builder.method(originalRequest.method(), originalRequest.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
        };
        return interceptor;
    }

    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
