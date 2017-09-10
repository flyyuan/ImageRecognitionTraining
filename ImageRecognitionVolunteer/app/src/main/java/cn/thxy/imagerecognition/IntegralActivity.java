package cn.thxy.imagerecognition;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.thxy.imagerecognition.Adapter.ErrorViewAdapter;
import cn.thxy.imagerecognition.Adapter.IntegralViewAdapter;
import cn.thxy.imagerecognition.Bean.integralData;
import cn.thxy.imagerecognition.Utils.ConnectMethod;

public class IntegralActivity extends AppCompatActivity {

    private RecyclerView re_intergral;
    private View ll_tip,ll_turnData;
    private IntegralViewAdapter intergralAdapter;
    private SharedPreferences integralsp;
    private int integralListSize;
    public List<integralData> integralList;
    private View ll_recyclerView;
    private SharedPreferences sp;
    private TextView tv_userName,tv_sum;
    private String photo;
    private ImageView iamge;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.integraltoolbar);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntegralActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        intiView();


        IntegralThread integralThread=new IntegralThread();
        Thread thread=new Thread(integralThread);
        thread.start();

    }
    private void intiView(){
        ll_tip=(View)findViewById(R.id.ll_tip);
        ll_recyclerView=(View)findViewById(R.id.ll_recyclerview);
        ll_turnData=(View)findViewById(R.id.ll_turnData);
        re_intergral=(RecyclerView)findViewById(R.id.re_integral);
        tv_userName=(TextView)findViewById(R.id.integral_uesrname);
        tv_sum=(TextView)findViewById(R.id.integral_sum);
        iamge=(ImageView)findViewById(R.id.Circleview);
        ll_turnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IntegralActivity.this,UserInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        re_intergral.setLayoutManager(new LinearLayoutManager(this));
    }

   class IntegralThread implements Runnable{
        @Override
        public void run() {
            ConnectMethod connectMethod=new ConnectMethod(IntegralActivity.this);
            connectMethod.GetIntegral();
            integralList=connectMethod.integraldatalist;
            connectMethod.GetUserInfo();
            photo="http://39.108.69.214:8080"+connectMethod.user_photo;
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);

        }
    }

    public void intiGlide(Context context,String photourl, ImageView view){

        //Glide加载图片
        Glide.with(context)
                .load(photourl)
                .error(R.drawable.geterror)            //加载失败的显示的图片
                .thumbnail(0.1f)                        //会先加载缩略图 然后在加载全图
                .crossFade()                            //加载渐显动画
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
                    sp=IntegralActivity.this.getSharedPreferences("data",MODE_PRIVATE);
                    integralsp=IntegralActivity.this.getSharedPreferences("IntegralData",MODE_PRIVATE);
                    tv_userName.setText(sp.getString("LoginName",null));
                    tv_userName.setText("tanzhiliang");
                    tv_sum.setText(String.valueOf(integralsp.getFloat("integralsum",0)));
                    intiGlide(IntegralActivity.this,photo,iamge);
                    if (integralList.size()==0||integralList==null){
                        ll_tip.setVisibility(View.VISIBLE);
                        ll_recyclerView.setVisibility(View.GONE);
                    }else {
                        ll_tip.setVisibility(View.GONE);
                        ll_recyclerView.setVisibility(View.VISIBLE);
                        intergralAdapter=new IntegralViewAdapter(IntegralActivity.this,integralList);
                    }
                    re_intergral.setAdapter(intergralAdapter);
                    break;
                default:
                    break;
            }
        }
    };
}
