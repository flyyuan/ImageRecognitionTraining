package cn.thxy.imagerecognition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.thxy.imagerecognition.Adapter.UserHobbyAdapter;
import cn.thxy.imagerecognition.Bean.returnUserHobby;
import cn.thxy.imagerecognition.Utils.ConnectMethod;

public class UserHobbyActivity extends AppCompatActivity {
    private RecyclerView re_userHobby;
    private UserHobbyAdapter userHobbyAdapter;
    private SharedPreferences sp;
    private ImageView ig_user;
    private TextView tv_username,tv_change;
    private String image,username;
    private List<returnUserHobby> returnUserHobbyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhobby);
        /**
         * 沉浸式状态栏
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.userhobbytoolbar);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserHobbyActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        intiView();
        GetUserHobbyThread getUserHobbyThread=new GetUserHobbyThread();
        Thread thread=new Thread(getUserHobbyThread);
        thread.start();

    }
    private void intiView(){
        re_userHobby=(RecyclerView)findViewById(R.id.re_userhobby);
        ig_user=(ImageView)findViewById(R.id.hobby_cv) ;
        tv_username=(TextView)findViewById(R.id.tv_hobby_name);
        re_userHobby.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.tv_changehobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserHobbyActivity.this,HobbyActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    class GetUserHobbyThread implements Runnable{

        @Override
        public void run() {
            ConnectMethod connectMethod=new ConnectMethod(UserHobbyActivity.this);
            connectMethod.GetUsreHobby();
            returnUserHobbyList=connectMethod.returnUserHobbyList;
            userHobbyAdapter=new UserHobbyAdapter(UserHobbyActivity.this,returnUserHobbyList);
            connectMethod.GetUserInfo();
            username=connectMethod.user_name;
            image="http://39.108.69.214:8080"+connectMethod.user_photo;
            Message msg = new Message();
            msg.what = 1 ;
            mHandler.sendMessage(msg);
        }
    }
    public void intiGlide(Context context, String photourl, ImageView view){

        //Glide加载图片
        Glide.with(context)
                .load(photourl)
                //.asGif()                               //加载Gif
                .error(R.drawable.geterror)            //加载失败的显示的图片
                .thumbnail(0.1f)                        //会先加载缩略图 然后在加载全图
                //.override(200,500)                     //设置加载尺寸
                .fitCenter()                               //图片居中
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存图片
                .into(view);


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    intiGlide(UserHobbyActivity.this,image,ig_user);
                    re_userHobby.setAdapter(userHobbyAdapter);
                    tv_username.setText(username);
                    break;
                default:
                    break;
            }
        }
    };
}
