package cn.thxy.imagerecognition;


import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;


import java.util.List;

import cn.thxy.imagerecognition.Adapter.ModifyViewAdapter;
import cn.thxy.imagerecognition.Utils.ConnectMethod;



public class ModifyLabelsActivity extends AppCompatActivity {
    private ConnectMethod connectMethod;

    private RecyclerView rv_modifyView;
    private ModifyViewAdapter modifyViewAdapter;
    private List modifyLabelList;
    private String inputName;
    private String modifyId,modifyLabel,deleteId,deleteLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifylabel);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.labeltoolbar);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ModifyLabelsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        intiView();
        connectMethod=new ConnectMethod(ModifyLabelsActivity.this);
        modifyLabelsThread modifylabelsthread=new modifyLabelsThread();
        Thread thread=new Thread(modifylabelsthread);
        thread.start();
    }
    private  void intiView(){
        rv_modifyView=(RecyclerView)findViewById(R.id.re_modifylabel);
        rv_modifyView.setLayoutManager(new LinearLayoutManager(this));

    }



    public  class modifyLabelsThread implements Runnable{
        @Override
        public void run() {
            connectMethod.FindHistoryWriteLabel();
            while (connectMethod.historyLabelsList==null){
                try {
                    Thread.sleep(500);
                    System.out.println("++++++++ sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            modifyLabelList=connectMethod.historyLabelsList;
            modifyViewAdapter=new ModifyViewAdapter(ModifyLabelsActivity.this,connectMethod.historyLabelsList);
            Looper.prepare();
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);
            Looper.loop();
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    rv_modifyView.setAdapter(modifyViewAdapter);
                    break;
                default:
                    break;
            }

        }
    };
}
