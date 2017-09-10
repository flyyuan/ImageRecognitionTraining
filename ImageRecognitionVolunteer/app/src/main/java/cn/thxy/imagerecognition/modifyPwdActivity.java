package cn.thxy.imagerecognition;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class modifyPwdActivity extends AppCompatActivity {
    private EditText et_oldpasswd,et_newpasswd,et_comfirmpasswd;
    private Button btn_submit;
    private String oldpasswd,newpasswd,comfirmpasswd;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifypwd);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        et_oldpasswd=(EditText) findViewById(R.id.et_oldpassword);
        et_newpasswd=(EditText) findViewById(R.id.et_newpassword);
        et_comfirmpasswd=(EditText) findViewById(R.id.et_comfirmpasword);
        btn_submit=(Button)findViewById(R.id.submit_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.modifypwdtoolbar);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(modifyPwdActivity.this,UserInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intidata();
            }
        });
    }

    private void intidata(){
        oldpasswd=et_oldpasswd.getText().toString().trim();
        newpasswd=et_newpasswd.getText().toString().trim();
        comfirmpasswd=et_comfirmpasswd.getText().toString().trim();
        if (oldpasswd.isEmpty()||newpasswd.isEmpty()||comfirmpasswd.isEmpty()){
            Toast.makeText(modifyPwdActivity.this,"请输入全部数据",Toast.LENGTH_SHORT).show();
        }else if(newpasswd.equals(comfirmpasswd)){
            modifyPwdThread modifypwdthread=new modifyPwdThread();
            Thread thread=new Thread(modifypwdthread);
            thread.start();
            System.out.println("tanzhiliang  上传密码");
        }else {
            Toast.makeText(modifyPwdActivity.this,"两次输入的密码不正确",Toast.LENGTH_SHORT).show();
        }
    }


    class modifyPwdThread implements Runnable{
        @Override
        public void run() {
            Looper.prepare();
            ConnectMethod connectmethod=new ConnectMethod(modifyPwdActivity.this);
            connectmethod.ModifyPasswd(oldpasswd,newpasswd);
            System.out.println("tanzhiliang  修改密码  "+connectmethod.modifyPwdStatus);
            if (connectmethod.modifyPwdStatus.equals("0")){
                Toast.makeText(modifyPwdActivity.this,"旧密码错误,修改密码失败，",Toast.LENGTH_SHORT).show();

                System.out.println("tanzhiliang  修改密码错误");
            }else {
                System.out.println("tanzhiliang  修改密码成功");
                Toast.makeText(modifyPwdActivity.this,"修改密码成功",Toast.LENGTH_LONG).show();
                connectmethod.Quit();
                Intent intent=new Intent(modifyPwdActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            Looper.loop();
        }

        }
    }

}
