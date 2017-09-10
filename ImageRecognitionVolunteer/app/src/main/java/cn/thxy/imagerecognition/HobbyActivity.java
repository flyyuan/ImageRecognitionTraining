package cn.thxy.imagerecognition;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cn.thxy.imagerecognition.Adapter.HobbyViewAdapter;
import cn.thxy.imagerecognition.Bean.findAllPicCat;
import cn.thxy.imagerecognition.Bean.picCatList;
import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class HobbyActivity extends AppCompatActivity {
    private View view;
    private Button btn_submit;
    private boolean status;
    private ConnectMethod connectMethod;
    private List<picCatList> allPicCatList;
    private HobbyViewAdapter hobbyViewAdapter;
    private RecyclerView re_hobby;
    private String insertHobbyStatus;
    private String idList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hobby);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.habittoolbar);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HobbyActivity.this,UserHobbyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        connectMethod=new ConnectMethod(HobbyActivity.this);
        intiView();
        FindAllPicCatThread findAllPicCatThread=new FindAllPicCatThread();
        Thread thread=new Thread(findAllPicCatThread);
        thread.start();

    }

    class FindAllPicCatThread implements Runnable{
        @Override
        public void run() {
            connectMethod.FindAllPicCat();
            allPicCatList=connectMethod.allPicCatList;
            hobbyViewAdapter=new HobbyViewAdapter(HobbyActivity.this,allPicCatList);
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    }
    class SubmitHobbyThread implements Runnable{

        @Override
        public void run() {
            idList=MainActivity.ArrangeData(hobbyViewAdapter.idList);
            connectMethod.InsertHobby(idList);
            insertHobbyStatus=connectMethod.insertHobbyStatus;
            Message msg = new Message();
            msg.what = 2;
            mHandler.sendMessage(msg);
        }
    }


    private void intiView(){
        btn_submit=(Button)findViewById(R.id.btn_submitlabel);
        re_hobby=(RecyclerView)findViewById(R.id.re_habit);
        re_hobby.setLayoutManager(new GridLayoutManager(this,3));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitHobbyThread submitHobbyThread=new SubmitHobbyThread();
                Thread thread=new Thread(submitHobbyThread);
                thread.start();
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    re_hobby.setAdapter(hobbyViewAdapter);
                    break;
                case 2:
                    if (insertHobbyStatus.equals("0")){
                        Toast.makeText(HobbyActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(HobbyActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(HobbyActivity.this,UserHobbyActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
