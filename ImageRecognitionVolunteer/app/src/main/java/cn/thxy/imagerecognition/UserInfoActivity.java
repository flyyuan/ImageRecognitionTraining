package cn.thxy.imagerecognition;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class UserInfoActivity extends AppCompatActivity {
    private TextView tv_username,tv_useremail,tv_userphone,tv_userloginname;
    private static String username,useremail,userphone,userphoto;
    private ImageView iv_userphoto;
    private View ll_updatapassword;
    private  String inputName;
    private  ConnectMethod connectMethod;
    private static int btnStatus;
   protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.userinfo);
       /**
        * 沉浸式状态栏
        */
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           //透明状态栏
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           //透明导航栏
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
       }
       Toolbar toolbar = (Toolbar) findViewById(R.id.userinfotoolbar);
       setSupportActionBar(toolbar);
       //关键下面两句话，设置了回退按钮，及点击事件的效果
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(UserInfoActivity.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       });
        connectMethod=new ConnectMethod(UserInfoActivity.this);
        tv_useremail=(TextView)findViewById(R.id.tv_useremail);
        tv_username=(TextView)findViewById(R.id.tv_username);
        tv_userphone=(TextView)findViewById(R.id.tv_userphone);
        iv_userphoto=(ImageView)findViewById(R.id.Circleview);
        tv_userloginname=(TextView)findViewById(R.id.tv_userloginname);
        ll_updatapassword=(View)findViewById(R.id.ll_updatepassword);
        getUserInfoThread getuserinfothread=new getUserInfoThread();
        Thread userinfothread=new Thread(getuserinfothread);
        userinfothread.start();
        myOnClick();

   }
   public void myOnClick(){

       tv_useremail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               btnStatus=1;
               inputDialog(UserInfoActivity.this);

           }
       });

       tv_username.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             Toast.makeText(UserInfoActivity.this,"姓名不可以更改哦",Toast.LENGTH_SHORT).show();
           }
       });

       tv_userphone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               btnStatus=2;
               inputDialog(UserInfoActivity.this);

           }
       });


       ll_updatapassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent modifypwd_intent=new Intent(UserInfoActivity.this,modifyPwdActivity.class);
               UserInfoActivity.this.startActivity(modifypwd_intent);
               UserInfoActivity.this.finish();
           }
       });

   }
    public void intiGlide(String url){
        //Glide加载图片

        Glide.with(UserInfoActivity.this)
                .load("http://39.108.69.214:8080"+url)
                .error(R.drawable.geterror)            //加载失败的显示的图片
                .thumbnail(0.1f)                        //会先加载缩略图 然后在加载全图
                .crossFade()                            //加载渐显动画
                .fitCenter()                               //图片居中
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存图片
                .into(iv_userphoto);
    }

   class getUserInfoThread implements Runnable{
       @Override
       public void run() {
           connectMethod.GetUserInfo();
           username=connectMethod.user_name;
           useremail=connectMethod.user_email;
           userphone=connectMethod.user_phone;
           userphoto=connectMethod.user_photo;
           System.out.print("****"+useremail);

           Message msg = new Message();
           msg.what = 1;
           mHandler.sendMessage(msg);
       }
   }

   class modifyInfoThread implements  Runnable{
       @Override
       public void run() {
           connectMethod.UpdateUserInfo(username,useremail,userphone,userphone);
           Looper.prepare();
           if(connectMethod.updateInfoStatus.equals("1")){
               Toast.makeText(UserInfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(UserInfoActivity.this,"修改失败，请稍后",Toast.LENGTH_SHORT).show();
           }
           Message msg = new Message();
           msg.what = 2;
           mHandler.sendMessage(msg);
           Looper.loop();

       }
   }


    public  void inputDialog(final Context context) {
        final EditText inputServer = new EditText(context);
        inputServer.setFocusable(true);
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("请输入信息")
                //.setIcon(R.drawable.ic_menu_camera)
                .setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        inputName = inputServer.getText().toString();
                        if (inputName.equals("")){
                            Toast.makeText(context,"输入标签为空",Toast.LENGTH_SHORT).show();
                        }else {
                             switch(btnStatus) {
                                 case 1:useremail=inputName;
                                     break;
                                 case 2:userphone=inputName;
                                     break;
                                 default:break;
                            }
                            modifyInfoThread modifynfothread=new modifyInfoThread();
                            Thread modify_thread=new Thread(modifynfothread);
                            modify_thread.start();

                        }
                    }
                });
        builder.show();

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    intiGlide(userphoto);
                    tv_useremail.setText(useremail);
                    tv_username.setText(username);
                    tv_userphone.setText(userphone);
                    SharedPreferences sp=UserInfoActivity.this.getSharedPreferences("data",MODE_PRIVATE);
                    tv_userloginname.setText(sp.getString("LoginName",null));
                    break;
                case 2:
                    getUserInfoThread getuserinfothread=new getUserInfoThread();
                    Thread userinfothread=new Thread(getuserinfothread);
                    userinfothread.start();
                    break;
                default:
                    break;
            }
        }
    };

}
