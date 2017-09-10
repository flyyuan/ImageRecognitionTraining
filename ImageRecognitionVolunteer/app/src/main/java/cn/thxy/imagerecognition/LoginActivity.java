package cn.thxy.imagerecognition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import cn.thxy.imagerecognition.Utils.ConnectMethod;

public class LoginActivity extends Activity {
    private EditText et_username,et_password;
    private String Username,Password;
    private SharedPreferences sp,rsp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences languagesp=LoginActivity.this.getSharedPreferences("language",MODE_PRIVATE);
        Locale locale=Locale.getDefault();
        switch (languagesp.getInt("locale",0)){
            case 0: locale=Locale.SIMPLIFIED_CHINESE;
                break;
            case 1: locale=Locale.ENGLISH;
                break;
            case 2: locale=Locale.JAPANESE;
                break;
        }
        switchLanguage(locale,getResources());
        setContentView(R.layout.login);


        /**
         * 沉浸式状态栏
         */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //透明导航栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }

        sp = LoginActivity.this.getSharedPreferences("data", LoginActivity.MODE_PRIVATE);

        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        et_username.setText(sp.getString("USER_NAME", ""));
        et_password.setText(sp.getString("PASSWORD", ""));

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username=et_username.getText().toString().trim();
                Password=et_password.getText().toString().trim();
              /*  Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();*/
                if (Username.equals("")||Password.equals("")){
                    Toast.makeText(LoginActivity.this,"请输入用户名或密码",Toast.LENGTH_SHORT).show();
                }else {
                    LoginThread loginThread=new LoginThread();
                    Thread thread=new Thread(loginThread);
                    thread.start();
                }
            }
        });

        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();

            }
        });
    }

    class LoginThread implements Runnable{

        @Override
        public void run() {
            ConnectMethod connectMethod=new ConnectMethod(LoginActivity.this);
            connectMethod.Login(Username,Password);
            boolean IsLogin=connectMethod.IsLogin;
            Username=et_username.getText().toString().trim();
            Password=et_password.getText().toString().trim();

            if (IsLogin){
                sp.edit().putString("USER_NAME",Username).apply();
                sp.edit().putString("PASSWORD",Password).apply();
                Log.e("用户名", sp.getString("USER_NAME",""));
                Log.e("密码",sp.getString("PASSWORD",""));
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }else{
                Looper.prepare();
                Log.e("tip","账号密码错误，请重新输入");
                Toast.makeText(LoginActivity.this,"账号密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                et_username.findFocus();
                Looper.loop();
            }
        }
    }


    public static void switchLanguage(Locale locale ,Resources resources) {
        //Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale; // 简体中文
        resources.updateConfiguration(config, dm);
    }
}
