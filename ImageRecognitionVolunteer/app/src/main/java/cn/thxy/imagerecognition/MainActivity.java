package cn.thxy.imagerecognition;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.thxy.imagerecognition.Adapter.ErrorViewAdapter;
import cn.thxy.imagerecognition.Adapter.LabelViewAdapter;
import cn.thxy.imagerecognition.Utils.ConnectMethod;

import cn.thxy.imagerecognition.ratio.DynamicAvatarView;
import cn.thxy.imagerecognition.ratio.RatioLayout;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,View.OnTouchListener{
    private DynamicAvatarView mDynamic;
    private RatioLayout mRatio;
     public PhotoView mainImageView;
    private String labelValue;
    private SharedPreferences sp,lablesp,quitsp,picturesp;
    private static SharedPreferences languagesp;
    private long exitTime = 0;
    private String url;
    private String picID;
    private String labels;
    private String picCatId;
    private int labelListSize;
    private View inputLabel;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private EditText et_label1,et_label2;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private String l1,l2,l3,l4,l5,l6;
    private String labelStatus;
    private TextView tv_twolabel1,tv_twolabel2;
    private EditText et_twolabel1,et_twolabel2;
    private View layout_recyclerview;
    private View categoryView;
    private boolean statue1=true,statue2=true,statue3=true,statue4=true,statue5=true,statue6=true;
    private List<String> ss,mylist;
    private RecyclerView mRecyclerView,writeRecyclerView;
    private Button btn_c1,btn_c2,btn_c3,btn_c4,btn_c5,btn_c6;
    private ImageView nav_iamgeview;
    private TextView nav_username;
    private String photoUrl,userName;
    private ConnectMethod connectMethod;
    private List<String> postLabelList;
    private List<String> postpicMapList;
    private List<String> postKeyList;
    private List<String> postErrorList;
    private List picIdList;
    private String mainaddLabelsStatus;
    private List getOneLabel;
    private String errorLabels;
    private List oneLabelMapValueLsit,oneLabelMapKeyLsit;
    private Boolean [] status;
    private Map oneLabelMap;
    private View ll_error;
    private TextView tv_changemode,tv_error;
    private Boolean isErrorStatus,isChangeModeStatus;
    private  List<String> postWriteLabelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp= MainActivity.this.getSharedPreferences("addLabel", MainActivity.MODE_PRIVATE);
        picturesp=MainActivity.this.getSharedPreferences("pictureData",MODE_PRIVATE);
        lablesp=MainActivity.this.getSharedPreferences("labelData", MainActivity.MODE_PRIVATE);
        sp.edit().putString("labelName", null).apply();
        /**
         * 沉浸式状态栏
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        postLabelList=new ArrayList<String>();
        postpicMapList=new ArrayList<String>();
        postKeyList=new ArrayList<String>();
        postErrorList=new ArrayList<String>();
        postWriteLabelList=new ArrayList<String>();
        initView();
        connectMethod=new ConnectMethod(MainActivity.this);
        inputLabel.setVisibility(View.GONE);
        mDynamic.setVisibility(View.GONE);
        mRatio.setVisibility(View.GONE);
        layout_recyclerview.setVisibility(View.GONE);
        mainImageView.setVisibility(View.VISIBLE);

        //加载Loading图片
        Glide.with(MainActivity.this)
                .load(R.drawable.loading)
                .asGif()                               //加载Gif
                .crossFade()                            //加载渐显动画
                .fitCenter()                               //图片居中
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存图片
                .into(mainImageView);
        PictureThread pictureThread=new PictureThread();
        Thread t1=new Thread(pictureThread);
        t1.start();

    }
    protected void onStart(){
        mRatio.enterBubble();
        super.onStart();

    }


    //初始化recycView
    private void initRecyclerview(){

        layout_recyclerview.setVisibility(View.VISIBLE);
        writeRecyclerView.setVisibility(View.GONE);
        if (mylist.size()!=0) {
        RecyclerView.Adapter adapter =new ErrorViewAdapter(MainActivity.this,mylist);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,6));
        mRecyclerView.setAdapter(adapter);
        LabelViewAdapter labelViewAdapter=new LabelViewAdapter(MainActivity.this,mylist);
        writeRecyclerView.setLayoutManager(new GridLayoutManager(this,6));
        writeRecyclerView.setAdapter(labelViewAdapter);
        }

    }

    //全部清空
    private void cleanLabel(){
        labelValue = sp.getString("labelName", null);
        et_label1.setText("");
        et_label2.setText("");
        l1=null;
        l2=null;
        l3=null;
        l4=null;
        l5=null;
        l6=null;
        btn_c1.setBackgroundColor(Color.parseColor("#70b5ed"));
        btn_c2.setBackgroundColor(Color.parseColor("#70b5ed"));
        btn_c3.setBackgroundColor(Color.parseColor("#70b5ed"));
        btn_c4.setBackgroundColor(Color.parseColor("#70b5ed"));
        btn_c5.setBackgroundColor(Color.parseColor("#70b5ed"));
        btn_c6.setBackgroundColor(Color.parseColor("#70b5ed"));
        postLabelList.clear();
        postpicMapList.clear();
        postKeyList.clear();
        postErrorList.clear();
        postWriteLabelList.clear();
        if (!labelStatus.equals("0")){
           cleanViewThread cleanViewThread=new cleanViewThread();
           Thread cleanviewthread=new Thread(cleanViewThread);
           cleanviewthread.start();
        }

    }

    //刷新
    private void refresh() {
        cleanLabel();
        sp.edit().putString("labelName", null).apply();
        mRatio.exitBubble();
        mRatio.destry();
        PictureThread pictureThread=new PictureThread();
        Thread getpicthread=new Thread(pictureThread);
        getpicthread.start();
    }

    //提交数据
    public void postLabelData(){
        labels="";
        if (picturesp.getString("labelStatus","0").equals("0")){
            l1=et_label1.getText().toString().trim();
            l2=et_label2.getText().toString().trim();
            //整理字符
            if (!l1.equals("")){
                postLabelList.add(l1);}
            if (!l2.equals("")){
                postLabelList.add(l2);}
            if (postLabelList.size()==0){
                Toast.makeText(MainActivity.this,"请至少输入一个标签",Toast.LENGTH_SHORT).show();
            }else if (postpicMapList.size()==0){
                Toast.makeText(MainActivity.this,"请至少输入顶层分类标签",Toast.LENGTH_SHORT).show();
            } else{
                    labels=ArrangeData(postLabelList);
                    picIdList=new ArrayList();
                    for (int i=0;i<postpicMapList.size();i++){
                        int index=Integer.valueOf(postpicMapList.get(i));
                        if (index==5) {
                            picIdList.add("");
                        }else {
                            picIdList.add(postKeyList.get(index));
                        }
                        System.out.println("+++ postpicMapList.get(i)"+postpicMapList.get(i));

                    }
                    System.out.println("+++ picIdList "+picIdList.toString());
                    picCatId=ArrangeData(picIdList);

                    PostLabelThread postLabelThread=new PostLabelThread();
                    Thread postthread=new Thread(postLabelThread);
                    postthread.start();

                }
            } else if (!isChangeModeStatus){
            if (LabelViewAdapter.writeLabelList==null||LabelViewAdapter.writeLabelList.size()==0){
                Toast.makeText(MainActivity.this,"请至少选择一个标签",Toast.LENGTH_SHORT).show();
            }else {
                writeRecyclerView.setVisibility(View.VISIBLE);
            labels=ArrangeData(LabelViewAdapter.writeLabelList);
                PostLabelThread postLabelThread=new PostLabelThread();
                Thread postthread=new Thread(postLabelThread);
                postthread.start();
            }
        }else {
            if (!(null==l1)){
                postLabelList.add(l1);}
            if (!(null==l2)){
                postLabelList.add(l2);}
            if (!(null==l3)){
                postLabelList.add(l3);}
            if (!(null==l4)){
                postLabelList.add(l4);}
            if (!(null==l5)){
                postLabelList.add(l5);}
            if (!(null==l6)){
                postLabelList.add(l6);}
            //判断是否输入错误标签
            postErrorList=ErrorViewAdapter.errorLabelList;

            if (postErrorList!=null&&postErrorList.size()!=0){
                errorLabels=ArrangeData(postErrorList);
            }else {
                errorLabels="";
            }
            labels=ArrangeData(postLabelList);
        PostLabelThread postLabelThread=new PostLabelThread();
        Thread postthread=new Thread(postLabelThread);
        postthread.start();

        }
    }

    //整理List标签数据
    public static String ArrangeData(List list){
        String data=list.toString();
        int index=data.indexOf("]");
        String finalData=data.substring(1,index);
        return finalData;
    }

    //获取类别数据
    public void getCategoryData(){
       /* ConnectMethod connectMethod=new ConnectMethod(MainActivity.this);
        for (int i=0;i< connectMethod.valueList.size();i++){
            System.out.println("+++++"+connectMethod.valueList.get(i));
        }*/
       int valueListSize=picturesp.getInt("valueListSize",0);
       int keyListSize=picturesp.getInt("keyListSize",0);
        List valueList=new ArrayList();
        if (valueListSize!=0){
            for (int i=0;i<valueListSize;i++){
                String value= picturesp.getString("value"+String.valueOf(i),null);
                valueList.add(value);}
            SharedPreferences languagesp=MainActivity.this.getSharedPreferences("language",MODE_PRIVATE);
            Locale locale=Locale.getDefault();
            switch (languagesp.getInt("locale",0)){
                case 0:
                    btn_c1.setText(String.valueOf(valueList.get(0)));
                    btn_c2.setText(String.valueOf(valueList.get(1)));
                    btn_c3.setText(String.valueOf(valueList.get(2)));
                    btn_c4.setText(String.valueOf(valueList.get(3)));
                    btn_c5.setText(String.valueOf(valueList.get(4)));
                    btn_c6.setText(this.getString(R.string.cate_other));
                    break;
                case 1: locale=Locale.ENGLISH;
                    btn_c1.setText("design");
                    btn_c2.setText("art");
                    btn_c3.setText("city");
                    btn_c4.setText("aminal");
                    btn_c5.setText("item");
                    btn_c6.setText(this.getString(R.string.cate_other));
                    break;
                case 2: locale=Locale.JAPANESE;
                    btn_c1.setText("デザイン");
                    btn_c2.setText("トラフィック");
                    btn_c3.setText("アート");
                    btn_c4.setText("金融と経済学");
                    btn_c5.setText("都市の景観");
                    btn_c6.setText(this.getString(R.string.cate_other));
                    break;
            }
            //switchLanguage(locale,getResources());
            /*btn_c1.setText(String.valueOf(valueList.get(0)));
            btn_c2.setText(String.valueOf(valueList.get(1)));
            btn_c3.setText(String.valueOf(valueList.get(2)));
            btn_c4.setText(String.valueOf(valueList.get(3)));
            btn_c5.setText(String.valueOf(valueList.get(4)));
            btn_c6.setText(this.getString(R.string.cate_other));*/
        }
        if (keyListSize!=0){
            for (int i=0;i<keyListSize;i++){
                postKeyList.add( picturesp.getString("key"+String.valueOf(i),null));
            }
        }
    }

    private void getOneLabelMap(Map map){
        Iterator iter1=map.values().iterator();
        Iterator iter2=map.keySet().iterator();
        tv_twolabel1=(TextView)findViewById(R.id.tv_twolabel1);
        tv_twolabel2=(TextView)findViewById(R.id.tv_twolabel2);
        oneLabelMapValueLsit=new ArrayList<>();
        oneLabelMapKeyLsit=new ArrayList<>();
        while(iter1.hasNext()){
            oneLabelMapValueLsit.add(iter1.next());
        }
        while(iter2.hasNext()){
            oneLabelMapKeyLsit.add(iter2.next());
        }
        System.out.println("++++"+oneLabelMapKeyLsit.toString());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //设置栏的点击处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_language) {
            LanguageDialog(MainActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //侧边栏的点击处理
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_data:
                Intent data_intent=new Intent(MainActivity.this,UserInfoActivity.class);
                startActivity(data_intent);

                break;
            case R.id.nav_modify:
                Intent modifylabel_intent=new Intent(MainActivity.this,ModifyLabelsActivity.class);
                startActivity(modifylabel_intent);

                break;
            case R.id.nav_integral:
                Intent integral_intent=new Intent(MainActivity.this,IntegralActivity.class);
                startActivity(integral_intent);
                break;
            case R.id.nav_habit:
                Intent habit_intent=new Intent(MainActivity.this,UserHobbyActivity.class);
                startActivity(habit_intent);

                break;
            case R.id.nav_feedback:
                Toast.makeText(this,getResources().getString(R.string.toast_feedback),Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_quit:
                QuitThread quitThread=new QuitThread();
                Thread thread=new Thread(quitThread);
                thread.start();
                break;

            default:break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.tv_c1:
                ButtonTouch((Button)findViewById(view.getId()),motionEvent);
                break;
            case R.id.tv_c2:
                ButtonTouch((Button)findViewById(view.getId()),motionEvent);
                break;
            case R.id.tv_c3:
                ButtonTouch((Button)findViewById(view.getId()),motionEvent);
                break;
            case R.id.tv_c4:
                ButtonTouch((Button)findViewById(view.getId()),motionEvent);
                break;
            case R.id.tv_c5:
                ButtonTouch((Button)findViewById(view.getId()),motionEvent);
                break;
            case R.id.tv_c6:
                ButtonTouch((Button)findViewById(view.getId()),motionEvent);
                break;
            case R.id.ll_category:
                LayoutTouch(motionEvent);
                break;

        }
        return true;
    }

    //获取图片信息的线程
    class PictureThread implements Runnable{
        @Override
        public void run() {
            connectMethod.Picture();
            System.out.println("+++ 获取图片信息中");
            //System.out.println("获取的图片为"+connectMethod.pictureList.size());

            connectMethod.GetUserInfo();
            photoUrl=connectMethod.user_photo;
            userName=connectMethod.user_name;
            oneLabelMap=connectMethod.oneLabelLists;

            System.out.println("+++ photo url "+photoUrl);
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);  }


    }

    //注销的线程
    class QuitThread implements Runnable{
        @Override
        public void run() {
            connectMethod.Quit();
            quitsp= MainActivity.this.getSharedPreferences("data", MainActivity.MODE_PRIVATE);
            quitsp.edit().remove("Cookie").apply();
            Intent quit_intent=new Intent(MainActivity.this,LoginActivity.class);
            MainActivity.this.startActivity(quit_intent);
            MainActivity.this.finish();
        }
    }

    //提交标签数据的线程
    class PostLabelThread implements Runnable{
        @Override
        public void run() {
            picID=picturesp.getString("id",null);
            url=picturesp.getString("url",null);
            connectMethod.PostLabel(picID,labels,url,errorLabels,picCatId);
            if (connectMethod.addLabelsStatus.equals("1")){
                mainaddLabelsStatus="1";
            }else if (connectMethod.addLabelsStatus.equals("-1")) {
                mainaddLabelsStatus="-1";
            }else {
                mainaddLabelsStatus="0";
                }

            Message msg = new Message();
            msg.what = 4;
            mHandler.sendMessage(msg);

        }
    }

    //刷新UI的线程
    class refreshThread implements Runnable{
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 2;
            mHandler.sendMessage(msg);
        }
    }

    //清空气泡背景的线程
    class cleanViewThread implements Runnable{
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 3;
            mHandler.sendMessage(msg);
        }
    }

    //对话框刷新的线程
    public void refreshDialog(){
        refreshThread refreshthread=new refreshThread();
        Thread t1=new Thread(refreshthread);
        t1.start();
    }

    //初始化控件
    public void initView(){
        inputLabel=(View)findViewById(R.id.ll_inputlabel);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tv_twolabel1=(TextView)findViewById(R.id.tv_twolabel1);
        tv_twolabel2=(TextView)findViewById(R.id.tv_twolabel2);
        et_twolabel1=(EditText)findViewById(R.id.et_twolabel1);
        et_twolabel2=(EditText)findViewById(R.id.et_twolabel2);
        layout_recyclerview = (View) findViewById(R.id.ll_myreview);
        mRecyclerView=(RecyclerView)findViewById(R.id.myreview);
        writeRecyclerView=(RecyclerView)findViewById(R.id.re_writelabel);
        mRatio = (RatioLayout)findViewById(R.id.ratio);
        mDynamic = (DynamicAvatarView) findViewById(R.id.dynamic);
        categoryView=(View)findViewById(R.id.ll_category);
        btn_c1=(Button) findViewById(R.id.tv_c1);
        btn_c2=(Button) findViewById(R.id.tv_c2);
        btn_c3=(Button) findViewById(R.id.tv_c3);
        btn_c4=(Button) findViewById(R.id.tv_c4);
        btn_c5=(Button) findViewById(R.id.tv_c5);
        btn_c6=(Button) findViewById(R.id.tv_c6);
        btn_c1.setOnTouchListener(this);
        btn_c2.setOnTouchListener(this);
        btn_c3.setOnTouchListener(this);
        btn_c4.setOnTouchListener(this);
        btn_c5.setOnTouchListener(this);
        btn_c6.setOnTouchListener(this);
        categoryView.setOnTouchListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mainImageView=(PhotoView) findViewById(R.id.main_iamgeview);
        et_label1=(EditText)findViewById(R.id.et_label1);
        et_label2=(EditText)findViewById(R.id.et_label2);
        tv_error=(TextView)findViewById(R.id.tv_error);
        tv_changemode=(TextView)findViewById(R.id.tv_changemode);
        ll_error=(View)findViewById(R.id.ll_error);
        isErrorStatus=true;
        isChangeModeStatus=true;
        // 启用图片缩放功能
        mainImageView.setVisibility(View.VISIBLE);
        mainImageView.enable();
        // 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)
        //mainImageView.disenable();
     /*   Info info=mainImageView.getInfo();
        // 从一张图片信息变化到现在的图片，用于图片点击后放大浏览
        mainImageView.animaFrom(info);*/



        tv_changemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChangeModeStatus){
                    isChangeModeStatus=false;
                Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_mode1),Toast.LENGTH_SHORT).show();
                writeRecyclerView.setVisibility(View.VISIBLE);
                mRatio.setVisibility(View.GONE);
                postWriteLabelList.clear();
                }else {
                    isChangeModeStatus=true;
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_mode2),Toast.LENGTH_SHORT).show();
                    writeRecyclerView.setVisibility(View.GONE);
                    mRatio.setVisibility(View.VISIBLE);
                    cleanLabel();
                }
            }
        });
        ll_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isErrorStatus){
                    isErrorStatus=false;
                    tv_error.setText(getResources().getString(R.string.main_error));
                    mRecyclerView.setVisibility(View.VISIBLE);
                }else {
                    isErrorStatus=true;
                    tv_error.setText(getResources().getString(R.string.main_iserror));
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        //touch监听
        status=new Boolean[5];
        for(int i=0;i<5;i++){
            status[i]=true;
        }

        //在TouchMode下面使用这个监听器
      /*  et_label1.setFocusableInTouchMode(true);
        et_label2.setFocusableInTouchMode(true);
        et_label1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("+++++ onclick");
                mRatio.exitBubble();
                mRatio.setVisibility(View.GONE);
            }
        });
        et_label1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("+++all l1获取焦点");
                if(hasFocus){//获得焦点
                    mRatio.setVisibility(View.GONE);
                    System.out.println("+++ l1获取焦点");
                }else{//失去焦点
                    System.out.println("+++ l1失去焦点");
                }
            }
        });
        et_label2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRatio.setVisibility(View.GONE);
                System.out.println("+++ l2获取焦点");
            }
        });*/

        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("+++btn 提交");
                postLabelData();
                Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_labelinfo)+labels,Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("+++btn 刷新");
                refresh();
                /*Snackbar.make(view, "实现你的Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("+++btn 清空");
                cleanLabel();
            }
        });
        //建议标签按钮点击事件

        btn_c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statue1){
                    statue1=false;
                    btn_c1.setBackgroundColor(Color.parseColor("#1295DA"));
                    postpicMapList.add("0");

                }else {
                    statue1=true;
                    btn_c1.setBackgroundColor(Color.parseColor("#70b5ed"));
                    postpicMapList.remove("0");

                }
            }
        });

        btn_c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statue2){
                    statue2=false;
                    btn_c2.setBackgroundColor(Color.parseColor("#1295DA"));
                    postpicMapList.add("1");

                }else {
                    statue2=true;
                    btn_c2.setBackgroundColor(Color.parseColor("#70b5ed"));
                    postpicMapList.remove("1");

                }
            }
        });

        btn_c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statue3){
                    statue3=false;
                    btn_c3.setBackgroundColor(Color.parseColor("#1295DA"));
                    postpicMapList.add("2");

                }else {
                    statue3=true;
                    btn_c3.setBackgroundColor(Color.parseColor("#70b5ed"));
                    postpicMapList.remove("2");
                }
            }
        });

        btn_c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statue4){
                    statue4=false;
                    btn_c4.setBackgroundColor(Color.parseColor("#1295DA"));
                    postpicMapList.add("3");

                }else {
                    statue4=true;
                    btn_c4.setBackgroundColor(Color.parseColor("#70b5ed"));
                    postpicMapList.remove("3");
                }
            }
        });

        btn_c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(statue5){
                    statue5=false;
                    btn_c5.setBackgroundColor(Color.parseColor("#1295DA"));
                    postpicMapList.add("4");
                }else {
                    statue5=true;
                    btn_c5.setBackgroundColor(Color.parseColor("#70b5ed"));
                    postpicMapList.remove("4");                }
            }
        });

        btn_c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statue6){
                    statue6=false;
                    btn_c6.setBackgroundColor(Color.parseColor("#1295DA"));
                    postpicMapList.add("5");
                }else {
                    statue6=true;
                    btn_c6.setBackgroundColor(Color.parseColor("#70b5ed"));
                    postpicMapList.remove("5");
                }
            }
        });

    }

    private void ButtonTouch(Button btn,MotionEvent motionEvent){
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                btn.callOnClick();
                System.out.println("-----"+btn.getText()+" onClick"+motionEvent.getRawX());
                break;
            case MotionEvent.ACTION_MOVE:
                int x=(int)motionEvent.getRawX();
                if (x<=120){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn1 onClick"+motionEvent.getRawX());
                    btn_c1.callOnClick();
                    break;
                }else
                if (x<=280){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn2 onClick"+motionEvent.getRawX());
                    btn_c2.callOnClick();
                    break;
                }else
                if (x<=510){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn3 onClick"+motionEvent.getRawX());
                    btn_c3.callOnClick();
                    break;
                }else
                if (x<=680){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn4 onClick"+motionEvent.getRawX());
                    btn_c4.callOnClick();
                    break;
                }else
                if (x<=850){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn5 onClick"+motionEvent.getRawX());
                    btn_c5.callOnClick();
                    break;
                }
                else
                if (x<=1050){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn6 onClick"+motionEvent.getRawX());
                    btn_c6.callOnClick();
                    break;
                }
                break;
        }
    }

    private void LayoutTouch(MotionEvent motionEvent){
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_MOVE:
                int x=(int)motionEvent.getRawX();
                if (x<=120){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn1 onClick"+motionEvent.getRawX());
                    btn_c1.callOnClick();
                    break;
                }else
                if (x<=280){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn2 onClick"+motionEvent.getRawX());
                    btn_c2.callOnClick();
                    break;
                }else
                if (x<=510){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn3 onClick"+motionEvent.getRawX());
                    btn_c3.callOnClick();
                    break;
                }else
                if (x<=680){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn4 onClick"+motionEvent.getRawX());
                    btn_c4.callOnClick();
                    break;
                }else
                if (x<=850){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn5 onClick"+motionEvent.getRawX());
                    btn_c5.callOnClick();
                    break;
                }
                else
                if (x<=1050){
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-----"+"btn6 onClick"+motionEvent.getRawX());
                    btn_c6.callOnClick();
                    break;
                }
                break;
        }
    }


    //加载气泡,添加标签动画
    public void Animationview(){
        labelValue = sp.getString("labelName", null);

        if (labelStatus.equals("0")){
            mDynamic.setVisibility(View.GONE);
            mRatio.setVisibility(View.GONE);
            inputLabel.setVisibility(View.VISIBLE);
            categoryView.setVisibility(View.VISIBLE);
            layout_recyclerview.setVisibility(View.GONE);
        }else {
            //定义List获取数据
            mylist = new ArrayList();

            for (int i=0;i<labelListSize;i++){
                String label= picturesp.getString("label"+String.valueOf(i),"");
                if(label.equals("广州 站")){
                    System.out.println("+*+");
                }else {
                mylist.add(label);}
               // mylist.add(label);
            }

            if (labelValue != null) {
                mylist.add(labelValue);
                mylist.remove(mylist.get(5));
            }
            //list类型转换为String[]
            final String[] texts = (String[]) mylist.toArray(new String[mylist.size()]);
            mRatio.exitBubble();
            mRatio.destry();
            mRatio.addText(texts);
            initRecyclerview();
            //mRatio.changeTextBackground(bgs);

            mRatio.setInnerCenterListener(new RatioLayout.InnerCenterListener() {
                                              @Override
                   public void innerCenterHominged(int position, String text) {
                    //texts[position] = addNumber(texts[position]);
                   mRatio.changeText(texts);
                   text = changText(text);
                   //lablesp.edit().clear().apply();
                   switch (position) {
                       case 0:
                           l1 = text;
                           lablesp.edit().putString("Label1", text).apply();
                           break;
                       case 1:
                           l2 = text;
                           lablesp.edit().putString("Label2", text).apply();
                           break;
                       case 2:
                           l3 = text;
                           lablesp.edit().putString("Label3", text).apply();
                           break;
                       case 3:
                           l4 = text;
                           lablesp.edit().putString("Label4", text).apply();
                           break;
                       case 4:
                           l5 = text;
                           lablesp.edit().putString("Label5", text).apply();
                           break;
                       case 5:
                           l6 = text;
                           lablesp.edit().putString("Label6", text).apply();
                           break;
                   }
                   }

                //调整显示text的格式
                public String changText(String text) {
                    int index = text.length();
                    String head = "";
                    String body = "";
                    if (index >= 11) {
                        Toast.makeText(MainActivity.this, "请输入1-7位字符的标签", Toast.LENGTH_SHORT).show();
                        inputTitleDialog(MainActivity.this);
                    } else {
                        if (index <= 3) {
                            head = text.substring(0, index).trim();
                        } else {
                            head = text.substring(0, 3).trim();
                            int indexstop = text.indexOf("\n");
                            body = text.substring(indexstop + 1, index - indexstop + 2);
                        }
                    }
                    text = head + body;
                    return text;
                }

                @Override
                public void innerCenter(int position, String text) {
                    //播放心的动画，背景颜色变化
                    mRatio.setPlayLoveXin(true);
                    /* if(position % 2 == 0){
                       mRatio.setPlayLoveXin(true);
                        }else{
                        mRatio.setPlayLoveXin(false);
                     }*/
                                              }
            }
            );

            mRatio.setVisibility(View.VISIBLE);
            //设置动画透明度
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.6f, 0.0f);
            //设置动画播放时长
            alphaAnimation.setDuration(3000);
            //设置动画重复方式
            alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
            //设置动画重复次数
            alphaAnimation.setRepeatCount(AlphaAnimation.INFINITE);

            mDynamic.startAnimation(alphaAnimation);

            inputLabel.setVisibility(View.GONE);
            mDynamic.setVisibility(View.VISIBLE);
            mRatio.setVisibility(View.VISIBLE);
            layout_recyclerview.setVisibility(View.VISIBLE);
            categoryView.setVisibility(View.GONE);
            mRatio.enterBubble();
        }

    }

    //清空气泡标签背景
    public void newView(){
        mRatio.exitBubble();
        mRatio.destry();
        labelValue = sp.getString("labelName", null);

            //定义List获取数据
            List mylist = new ArrayList();
                    for (int i=0;i<labelListSize;i++){
                        String label= picturesp.getString("label"+String.valueOf(i),"");
                        if(label.equals("广州 站")){
                            System.out.println("+*+");
                        }else {
                            mylist.add(label);}
                    }
                if (labelValue != null) {
                    mylist.add(labelValue);
                }

                //list类型转换为String[]
                final String[] texts = (String[]) mylist.toArray(new String[mylist.size()]);
                Log.e("tip list size",String.valueOf(mylist.size()));
                Log.e("tip text.length",String.valueOf(texts.length)+" "+texts.toString());

                mRatio.addText(texts);
                mRatio.setInnerCenterListener(new RatioLayout.InnerCenterListener() {
                                                  @Override
                                                  public void innerCenterHominged(int position, String text) {
                                                      //texts[position] = addNumber(texts[position]);
                                                      mRatio.changeText(texts);
                                                      text = changText(text);
                                                      //lablesp.edit().clear().apply();
                                                      switch (position) {
                                                          case 0:
                                                              l1 = text;
                                                              lablesp.edit().putString("Label1", text).apply();
                                                              break;
                                                          case 1:
                                                              l2 = text;
                                                              lablesp.edit().putString("Label2", text).apply();
                                                              break;
                                                          case 2:
                                                              l3 = text;
                                                              lablesp.edit().putString("Label3", text).apply();
                                                              break;
                                                          case 3:
                                                              l4 = text;
                                                              lablesp.edit().putString("Label4", text).apply();
                                                              break;
                                                          case 4:
                                                              l5 = text;
                                                              lablesp.edit().putString("Label5", text).apply();
                                                              break;
                                                          case 5:
                                                              l6 = text;
                                                              lablesp.edit().putString("Label6", text).apply();
                                                              break;
                                                      }
                                                  }

                                                  //调整显示text的格式
                                                  public String changText(String text) {
                                                      int index = text.length();
                                                      String head = "";
                                                      String body = "";
                                                      if (index >= 11) {
                                                          Toast.makeText(MainActivity.this, "请输入1-7位字符的标签", Toast.LENGTH_SHORT).show();
                                                          inputTitleDialog(MainActivity.this);
                                                      } else {
                                                          if (index <= 3) {
                                                              head = text.substring(0, index).trim();
                                                          } else {
                                                              head = text.substring(0, 3).trim();
                                                              int indexstop = text.indexOf("\n");
                                                              body = text.substring(indexstop + 1, index - indexstop + 2);
                                                          }
                                                      }
                                                      text = head + body;
                                                      return text;
                                                  }

                                                  @Override
                                                  public void innerCenter(int position, String text) {
                                                      //播放心的动画，背景颜色变化
                                                      mRatio.setPlayLoveXin(true);
                                                  }
                                              }
                );
                mRatio.setVisibility(View.VISIBLE);
                //设置动画透明度
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.6f, 0.0f);
                //设置动画播放时长
                alphaAnimation.setDuration(3000);
                //设置动画重复方式
                alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
                //设置动画重复次数
                alphaAnimation.setRepeatCount(AlphaAnimation.INFINITE);

                mDynamic.startAnimation(alphaAnimation);

                inputLabel.setVisibility(View.GONE);
                mDynamic.setVisibility(View.VISIBLE);
                mRatio.setVisibility(View.VISIBLE);
                layout_recyclerview.setVisibility(View.VISIBLE);
                categoryView.setVisibility(View.GONE);
                mRatio.enterBubble();


    }

    //Glide加载图片
    public void initGlide(String photourl,ImageView view){

        //Glide加载图片
        Glide.with(MainActivity.this)
                .load(photourl)
                //.asGif()                               //加载Gif
                .placeholder(R.drawable.refresh)     //加载中的显示图片
                .error(R.drawable.geterror)            //加载失败的显示的图片
                .thumbnail(0.1f)                        //会先加载缩略图 然后在加载全图
                .crossFade()                            //加载渐显动画
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存图片
                 //.override(200,500)                     //设置加载尺寸
                .fitCenter()                               //图片居中
                //.centerCrop()                               //  填充满Imageview
                .into(view);


    }


    //退出时的动画
    public void exit(View view) {
        //mDynamic.exitAnim();
        mRatio.exitBubble();
    }

    //进入时的动画
    public void enter(View view) {
        Log.e("tip","进入动画执行");
        //mDynamic.enterAnim();
        mRatio.enterBubble();
    }

    public static void LanguageDialog(final Context context) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.dialog_title))
                //.setIcon(R.drawable.ic_menu_camera)
                .setSingleChoiceItems(new String[] {"中文简体","English","日本语"}, 0,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int i) {
                                //dialog.dismiss();
                                Locale locale=Locale.getDefault();
                                int num=0;
                                switch (i){
                                    case 0: locale=Locale.SIMPLIFIED_CHINESE;
                                        num=0;
                                        break;
                                    case 1: locale=Locale.ENGLISH;
                                        num=1;
                                        break;
                                    case 2: locale=Locale.JAPANESE;
                                        num=2;
                                        break;
                                }
                                languagesp=context.getSharedPreferences("language",MODE_PRIVATE);
                                languagesp.edit().putInt("locale",num).apply();
                                //Toast.makeText(context, Locale.getDefault().getLanguage()+" "+locale,Toast.LENGTH_SHORT).show();
                                switchLanguage(locale,context.getResources());
                                Intent intent=new Intent(context,MainActivity.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }
                        }
                );
        builder.show();

    }

    public static void switchLanguage(Locale locale ,Resources resources) {
        //Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale; // 简体中文
        resources.updateConfiguration(config, dm);
    }

    public static void inputTitleDialog(final Context context) {
        final EditText inputServer = new EditText(context);
        inputServer.setFocusable(true);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("自定义标签")
                //.setIcon(R.drawable.ic_menu_camera)
                .setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int
                            which) {
                        String inputName = inputServer.getText
                                ().toString();
                        SharedPreferences sp= context.getSharedPreferences("addLabel", Context.MODE_PRIVATE);
                        if (inputName.equals("")){
                            sp.edit().putString("labelName",null).apply();
                            Toast.makeText(context,"输入标签为空",Toast.LENGTH_SHORT).show();
                                    Log.e("tip","标签为空，不用刷新页面");
                        }else {
                            MainActivity activity=new MainActivity();
                            sp.edit().putString("labelName",inputName).apply();
                            Log.e("main tip","标签为:"+inputName+"，显示数据，刷新页面");
                            activity.Animationview();
                            activity.enter(activity.getWindow().getDecorView());

                        }
                    }
                });
        builder.show();

    }
    public void initOneMoapData(){
        if (oneLabelMap!=null){
            getOneLabelMap(oneLabelMap);
            System.out.println("++++ onemap"+oneLabelMap.toString());
            if (oneLabelMapKeyLsit!=null) {
                if (oneLabelMapKeyLsit.size() < 2) {
                    tv_twolabel1.setText(String.valueOf(oneLabelMapValueLsit.get(0))+":");
                    tv_twolabel2.setText("无:");
                } else if (oneLabelMapKeyLsit.size() >= 2) {
                    tv_twolabel1.setText(String.valueOf(oneLabelMapValueLsit.get(0))+":");
                    tv_twolabel2.setText(String.valueOf(oneLabelMapValueLsit.get(1))+":");
                }
            }else {
                tv_twolabel1.setText("无:");
                tv_twolabel2.setText("无:");
            }
        }
    }



    //再按一次返回键退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toast_quit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                onDestroy();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    protected void onResume(){

        super.onResume();
        //System.out.println("**************************获取焦点");

    }

    protected void onPause(){
        mRatio.exitBubble();
        super.onPause();
        //System.out.println("**************************暂停");
    }

    protected void onStop(){
        super.onStop();
        //System.out.println("**************************停止");
    }

    //销毁时的动画
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRatio.destry();
        //System.out.println("**************************销毁");
    }



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initOneMoapData();
                    url=picturesp.getString("url",null);
                    System.out.println("+++ url"+url);
                    labelListSize=picturesp.getInt("labelListSize",0);
                    labelStatus=picturesp.getString("labelStatus",null);
                    picID=picturesp.getString("picName",null);
                    nav_iamgeview=(ImageView)findViewById(R.id.nav_imageView);
                    nav_username=(TextView)findViewById(R.id.nav_username);
                    photoUrl="http://39.108.69.214:8080/"+photoUrl;
                    //用户头像加载
                    initGlide(photoUrl,nav_iamgeview);
                    SharedPreferences datasp=MainActivity.this.getSharedPreferences("data",MODE_PRIVATE);
                    nav_username.setText(datasp.getString("LoginName",null));
                    nav_username.setText("tanzhiliang");
                    //标注图片加载
                    initGlide(url,mainImageView);
                    Animationview();
                    getCategoryData();
                    break;
                case 2:
                    initOneMoapData();
                    initGlide(url,mainImageView);
                    Animationview();
                    break;
                case 3:
                    initOneMoapData();
                    initGlide(url,mainImageView);
                    newView();
                    break;
                case 4:
                    if (mainaddLabelsStatus.equals("1")){
                        Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_labelsucess),Toast.LENGTH_SHORT).show();
                        refresh();
                    }else if(mainaddLabelsStatus.equals("-1")) {
                        Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_labelerror),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_labelfail),Toast.LENGTH_SHORT).show();

                    }
                    break;
                default:
                    break;
            }
        }
    };


}
