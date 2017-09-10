package cn.thxy.imagerecognition;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class RegisterActivity extends AppCompatActivity {
    private EditText et_phonenum,et_loginname,et_name,et_newpassword,et_confirmnewpassword,et_email;
    private String phonenum,loginname,name,newpassword,confirmnewpassword,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
/**
 * 沉浸式状态栏
 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        findViewById(R.id.register_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skipintent=new Intent(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(skipintent);
                RegisterActivity.this.finish();
            }
        });
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intiview();
                RegisterThread registerThread=new RegisterThread();
                Thread thread=new Thread(registerThread);
                thread.start();

            }
        });

    }

    private void intiview(){
        et_phonenum=(EditText) findViewById(R.id.et_phoneNum);
        et_loginname=(EditText) findViewById(R.id.et_loginName);
        et_name=(EditText) findViewById(R.id.et_name);
        et_newpassword=(EditText) findViewById(R.id.et_newPassword);
        et_confirmnewpassword=(EditText) findViewById(R.id.et_confirmNewPassword);
        et_email=(EditText)findViewById(R.id.et_email);

    }

    private class RegisterThread implements Runnable{

        @Override
        public void run() {
            phonenum=et_phonenum.getText().toString().trim();
            loginname=et_loginname.getText().toString().trim();
            name=et_name.getText().toString().trim();
            newpassword=et_newpassword.getText().toString().trim();
            confirmnewpassword=et_confirmnewpassword.getText().toString().trim();
            email=et_email.getText().toString().trim();
            Looper.prepare();
            System.out.println("++++ "+phonenum+" "+loginname+" "+name+" "+newpassword+" "+email);
            if (phonenum.equals("")||loginname.equals("")||name.equals("")
                    ||newpassword.equals("")||confirmnewpassword.equals("")||email.equals("")){
                Toast.makeText(RegisterActivity.this,"请输入全部信息",Toast.LENGTH_SHORT).show();
            }else if(newpassword.equals(confirmnewpassword)){
                ConnectMethod connectMethod=new ConnectMethod(RegisterActivity.this);
                connectMethod.Quit();
                connectMethod.Login("superadmin","1234");
                connectMethod.Register(phonenum,name,loginname,newpassword,confirmnewpassword,email);
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                connectMethod.Quit();
                RegisterActivity.this.finish();
            }else {
                Toast.makeText(RegisterActivity.this,"两次输入的密码不一致,请重新输入",Toast.LENGTH_SHORT).show();
            }
            Looper.loop();
        }
    }
}
