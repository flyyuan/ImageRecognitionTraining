package cn.thxy.imagerecognition.Utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.thxy.imagerecognition.Bean.deleteWrite;
import cn.thxy.imagerecognition.Bean.findAllPicCat;
import cn.thxy.imagerecognition.Bean.historyLabels;
import cn.thxy.imagerecognition.Bean.historyWriteLabels;
import cn.thxy.imagerecognition.Bean.modifyPwdReturn;
import cn.thxy.imagerecognition.Bean.integralData;
import cn.thxy.imagerecognition.Bean.integralDataList;
import cn.thxy.imagerecognition.Bean.loginData;
import cn.thxy.imagerecognition.Bean.picCatList;
import cn.thxy.imagerecognition.Bean.picture;
import cn.thxy.imagerecognition.Bean.pushPicture;


import cn.thxy.imagerecognition.Bean.returnDelete;
import cn.thxy.imagerecognition.Bean.returnInsert;
import cn.thxy.imagerecognition.Bean.returnTwoLabel;
import cn.thxy.imagerecognition.Bean.returnUserHobby;
import cn.thxy.imagerecognition.Bean.returnmodifylabel;
import cn.thxy.imagerecognition.Bean.updateInfoByMobile;
import cn.thxy.imagerecognition.Bean.updatewritelabel;
import cn.thxy.imagerecognition.Bean.userinfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.content.Context.MODE_PRIVATE;



public class ConnectMethod {
    private Context mContext;
    public String cookies,Cookies;
    private SharedPreferences sp,picturesp,integralsp;
    public boolean IsLogin,autoIslogin;
    public List<String> integralList;
    public List<integralData> integraldatalist;
    public List valueList,keyList;
    public float sum=0.0f;
    public String user_name,user_email,user_phone,user_photo;
    public static String modifyPwdStatus;
    public  String updateInfoStatus;
    public String modify_picid,modify_time,modify_label,modify_url;
    public ArrayList<historyLabels> historyLabelsList;
    public  String canUpdateNum,updateStatus;
    public String deleteStatus;
    public List<picture> pictureList;
    public String addLabelsStatus ;
    public String deleteHobbyStatus;
    public String insertHobbyStatus;
    public List<picCatList> allPicCatList;
    public List<String> hobbyNameList;
    public List<String> hobbyIdList;
    public String catId,catName;
    public String addTwoStatus;
    public List<returnUserHobby> returnUserHobbyList;
    public Map oneLabelLists;






    public ConnectMethod(Context mContext) {
        this.mContext = mContext;
    }

    public void Login(String Username,String Password){
        sp = mContext.getSharedPreferences("data", mContext.MODE_PRIVATE);
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.loginRetrofit();
        Call<loginData> call=retrofitManage.service.login(Username,Password,true);

       //同步处理
        try {
            Response<loginData> response = call.execute();
            if(response.body().getId()!=null){
                IsLogin=true;
                Cookies=response.body().getSessionid();
                sp.edit().putString("Cookie",Cookies).apply();
                sp.edit().putString("LoginName",response.body().getLoginName()).apply();
            } else {
                IsLogin=false;
            }
            //sp.edit().putBoolean("IsLogin",IsLogin).apply();
            Log.e("登录信息","用户名:"+Username+",密码:"+Password+",Cookie:"+ Cookies);
        } catch (IOException e) {
            e.printStackTrace();
            Looper.prepare();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Toast.makeText(mContext,"网络状况不好，请稍后登录",Toast.LENGTH_SHORT).show();
            Log.e("tip","网络连接超时，请稍后登录");
            Looper.loop();
           /* if(e.getCause().equals(SocketTimeoutException.class))//如果超时并未超过指定次数，则重新连接
            {
                Toast.makeText(mContext,"网络连接超时，请重新重新登录",Toast.LENGTH_SHORT).show();
                Log.e("tip","网络连接超时，请重新重新登录");
            }*/
        }

    }

    public void autoLogin(){
        sp=mContext.getSharedPreferences("data", MODE_PRIVATE);
        cookies=sp.getString("Cookie",null);

        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<loginData> call=retrofitManage.service.autologin();
        try {
            Response<loginData> response = call.execute();
            if(response.body().getId()!=null){
                autoIslogin=true;
            } else {
                autoIslogin=false;
            }
            Log.e("autologin loginname",response.body().getLoginName());
            sp.edit().putBoolean("Auto",autoIslogin).apply();
            sp.edit().putString("LoginName",response.body().getLoginName()).apply();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("tip","Cookie失效");
        }

    }

    public void Register(final String phoneNum, final String name, final String loginName, final String newPassword, final String confirmNewPassword, final String email){
        System.out.println("+++++ 注册信息 "+loginName+" "+phoneNum+" "+name+" "+newPassword+" "+confirmNewPassword+" "+email);

        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();

        Call<ResponseBody> call=retrofitManage.service.register("17","71f0edd8528046719bade2f0974a95d1","6",
        phoneNum,name,loginName,newPassword,confirmNewPassword,email,phoneNum,phoneNum);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("状态","注册成功，用户名:"+loginName+",密码:"+newPassword);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("+++ register 连接失败");
            }
        });


    }

    public void Quit(){

        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<ResponseBody> call=retrofitManage.service.quit();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    public void Picture(){

        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<pushPicture> call=retrofitManage.service.getpicture();
        try {
            Response<pushPicture> response = call.execute();
            pictureList=response.body().getList();
            picturesp=mContext.getSharedPreferences("pictureData",MODE_PRIVATE);
            for (picture picturedata:pictureList)
            {
                picturesp.edit().putString("id",picturedata.getId()).apply();
                picturesp.edit().putBoolean("isNewRecord",picturedata.getNewRecord()).apply();
                picturesp.edit().putString("tagStatus",picturedata.getTagStatus()).apply();
                picturesp.edit().putString("picName",picturedata.getPicName()).apply();
                picturesp.edit().putString("labelStatus",picturedata.getLabelStatus()).apply();
                picturesp.edit().putString("url",picturedata.getUrl()).apply();

                List<String> labelList=picturedata.getList();
                if (labelList!=null){
                picturesp.edit().putInt("labelListSize",labelList.size()).apply();

                for (int i=0;i <labelList.size();i++){
                    picturesp.edit().putString("label"+String.valueOf(i),labelList.get(i)).apply();
                }
                }
                oneLabelLists=picturedata.getOneLabelsMap();
                List<String> candidateLabelLists=picturedata.getTwoCandidateLabels();
                Map picCatMap=picturedata.getPicCatMap();
                Iterator iter1=(Iterator)picCatMap.values().iterator();
                Iterator iter2=(Iterator)picCatMap.keySet().iterator();
                //将Map的数据放到List;
                valueList=new ArrayList();
                keyList=new ArrayList();
                while(iter1.hasNext()){
                    valueList.add(iter1.next());}
                while(iter2.hasNext()){
                    keyList.add(iter2.next());}

                for (int i=0;i <valueList.size();i++){
                    picturesp.edit().putString("value"+String.valueOf(i),String .valueOf(valueList.get(i))).apply();
                }
                for (int i=0;i <keyList.size();i++){
                    picturesp.edit().putString("key"+String.valueOf(i),String .valueOf(keyList.get(i))).apply();
                }

                picturesp.edit().putInt("valueListSize",valueList.size()).apply();
                picturesp.edit().putInt("keyListSize",keyList.size()).apply();

                if ((picturedata.getOneLabelsMap())!=null){
                }
                for (int i=0;i <candidateLabelLists.size();i++){
                   // picturesp.edit().putString("candidatelabel"+String.valueOf(i),candidateLabelLists.get(i)).apply();

                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PostLabel(String picId,String labels,String url,String errorLabel,String picCatId){

        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();

        Call<ResponseBody> call=retrofitManage.service.postlabel(picId,labels,url,errorLabel,picCatId);

        try {
            Response<ResponseBody> response = call.execute();
            addLabelsStatus="1";
           /* Gson gson = new Gson();
            List<returnmodifylabel> addLabelsStatusList= gson.fromJson(response.body().string(), new TypeToken<List<returnmodifylabel>>() {}.getType());
           for(returnmodifylabel returnstatus:addLabelsStatusList){
               addLabelsStatus=returnstatus.getAddLabelsStatus();
           }*/

        } catch (IOException e) {
            addLabelsStatus="0";
            e.printStackTrace();
        }
    }

    public void PostTwoLabel(String picId,String oneLabelId,String twoCandidateLabel){

        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();

        Call<ResponseBody> call=retrofitManage.service.posttwolabel(picId,oneLabelId,twoCandidateLabel);
        try {
            Response<ResponseBody> response = call.execute();
            Gson gson = new Gson();
            List<returnTwoLabel> returnTwoLabelList= gson.fromJson(response.body().string(), new TypeToken<List>() {}.getType());
            for(returnTwoLabel returntwolabel:returnTwoLabelList){
                addTwoStatus=returntwolabel.getAddErrorLabelStatus();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void GetIntegral(){
        integralsp=mContext.getSharedPreferences("IntegralData",MODE_PRIVATE);
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<integralDataList> call=retrofitManage.service.getintegra();
        try {
            Response<integralDataList> response = call.execute();
            integraldatalist=response.body().getIntegralList();
            for(integralData integraldata:integraldatalist){
                sum+=Float.valueOf(integraldata.getIntegral());
            }
            integralsp.edit().putFloat("integralsum",sum).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GetUserInfo(){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<userinfo> call=retrofitManage.service.getuserinfo();

        try {
            Response<userinfo> response = call.execute();
            user_email=response.body().getEmail();
            user_name=response.body().getName();
            user_phone=response.body().getMoblie();
            user_photo=response.body().getPhoto();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ModifyPasswd(String oldpasswd,String newpasswd){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<modifyPwdReturn> call=retrofitManage.service.alterpassword(oldpasswd,newpasswd);
            try {
                Response<modifyPwdReturn> response = call.execute();
                modifyPwdStatus=response.body().getModifyPwdStatus();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    public void UpdateUserInfo(String name,String email,String phone,String mobile){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<updateInfoByMobile> call=retrofitManage.service.updateinfo(name,email,phone,mobile);
        try {
            Response<updateInfoByMobile> response=call.execute();
            updateInfoStatus=response.body().getUpdateInfoStatus();
            System.out.println("*+++* success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void FindHistoryWriteLabel(){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<ResponseBody> call=retrofitManage.service.findhistorywritelabel();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Gson gson = new Gson();
                try {
                    historyLabelsList = gson.fromJson(response.body().string(), new TypeToken<List<historyLabels>>() {}.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("++++++++connect fail");
            }
        });
    }

    public  void UpdataHistoryWriteLabel(String picId,String label,String targetLabel){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<updatewritelabel> call=retrofitManage.service.updatewritelabel(picId,label,targetLabel);
        System.out.println("+++ picid "+picId+" "+label+" "+targetLabel);
        try {
            Response<updatewritelabel> response=call.execute();
            if (response!=null){
            if (response.body().getUpdateStatus().equals("")
                ||response.body().getUpdateStatus()==null
                ||response.body().getUpdateStatus().isEmpty()) {
                updateStatus="0";
                canUpdateNum="0";
            }else {
                canUpdateNum=response.body().getCanUpdateNum();
                updateStatus=response.body().getUpdateStatus();

            }
            }else {
                System.out.println("+++ ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void DeleteHistoryWriteLabel(String picId,String label){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<deleteWrite> call=retrofitManage.service.deletewritelabel(picId,label);
        try {
            Response<deleteWrite> response=call.execute();
            deleteStatus=response.body().getDeleteStatus();
            System.out.println("+++getDeleteStatus "+response.body().getDeleteStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void FindAllPicCat(){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<findAllPicCat> call=retrofitManage.service.findallpiccat();
        try {
            Response<findAllPicCat> response=call.execute();
            allPicCatList=response.body().getPiccatList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InsertHobby(String catList){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<returnInsert> call=retrofitManage.service.inserthobby(catList);
        try {
            Response<returnInsert> response=call.execute();
            insertHobbyStatus=response.body().getInsertStatus();
            System.out.println("++++"+insertHobbyStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GetUsreHobby(){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<ResponseBody> call=retrofitManage.service.getuserhobby();
        try {
            Response<ResponseBody> response=call.execute();
            Gson gson = new Gson();
            try {
                returnUserHobbyList= gson.fromJson(response.body().string(), new TypeToken<List<returnUserHobby>>() {}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteHobby(String catId){
        RetrofitManage retrofitManage=new RetrofitManage(mContext);
        retrofitManage.myRetrofit();
        Call<returnDelete> call=retrofitManage.service.deletehobby(catId);
        try {
            Response<returnDelete> response=call.execute();
            System.out.println("+++++"+response.body().getDeleteStatus());
            deleteHobbyStatus=response.body().getDeleteStatus();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
