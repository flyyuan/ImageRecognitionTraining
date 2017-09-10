package cn.thxy.imagerecognition.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.thxy.imagerecognition.Bean.picCatList;
import cn.thxy.imagerecognition.Bean.returnUserHobby;
import cn.thxy.imagerecognition.R;
import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class UserHobbyAdapter extends RecyclerView.Adapter<UserHobbyAdapter.MyViewHolder> {
    final Context context;
    public List<returnUserHobby> list;
    private String name,catid;


    public UserHobbyAdapter(Context context, List<returnUserHobby> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_userhobby, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        returnUserHobby returnuserhobby=list.get(position);
        name=returnuserhobby.getName();
        catid=returnuserhobby.getCatId();
        holder.tv_userhobby.setText(name);
        holder.tv_userhobbyId.setText(catid);
        holder.iv_deletehobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catid=holder.tv_userhobbyId.getText().toString();
                DeleteHobbyThread deleteHobbyThread=new DeleteHobbyThread();
                Thread thread=new Thread(deleteHobbyThread);
                thread.start();
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    private class DeleteHobbyThread implements Runnable{
        @Override
        public void run() {
            ConnectMethod connectMethod=new ConnectMethod(context);
            connectMethod.DeleteHobby(catid);
            if (connectMethod.deleteHobbyStatus.equals("1")){
                Looper.prepare();
                Toast.makeText(context,"已删除",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_userhobby,tv_userhobbyId;
        private ImageView iv_deletehobby;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_userhobby=(TextView)itemView.findViewById(R.id.tv_userhabit);
            tv_userhobbyId=(TextView)itemView.findViewById(R.id.tv_userhobbyid);
            iv_deletehobby=(ImageView)itemView.findViewById(R.id.iv_deletehobby);

        }
    }







}
