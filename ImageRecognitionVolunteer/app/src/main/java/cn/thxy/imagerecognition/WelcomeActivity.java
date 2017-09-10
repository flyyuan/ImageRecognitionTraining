package cn.thxy.imagerecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private String sessionid;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initState();

    }
    protected void onStart(){
        super.onStart();
        ImageView i1=(ImageView) findViewById(R.id.welcome_image);
        sp=WelcomeActivity.this.getSharedPreferences("data",MODE_PRIVATE);
        sessionid=sp.getString("Cookie",null);
        Glide.with(this)
                .load(R.drawable.loading).asGif()
                .fitCenter()                               //图片居中
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存图片
                .into(i1);
        sessionid=null;
        if (sessionid!=null){
            autoLoginThread autologinthread=new autoLoginThread();
            Thread thread=new Thread(autologinthread);
            thread.start();
        }else {
            Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();
        }
    }
    class autoLoginThread implements Runnable{

        @Override
        public void run() {
            ConnectMethod connectMethod=new ConnectMethod(WelcomeActivity.this);
            connectMethod.autoLogin();
            boolean isAutoIsLogin=connectMethod.autoIslogin;
            if (isAutoIsLogin){
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.finish();
            }else {
                Looper.prepare();
                Log.e("tip","账号密码已失效，请重新登陆");
                Toast.makeText(WelcomeActivity.this,"账号密码已失效，请重新登陆",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.finish();
                Looper.loop();
            }
        }
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
