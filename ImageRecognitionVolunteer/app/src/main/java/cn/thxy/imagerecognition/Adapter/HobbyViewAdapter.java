package cn.thxy.imagerecognition.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.thxy.imagerecognition.Bean.findAllPicCat;
import cn.thxy.imagerecognition.Bean.historyLabels;
import cn.thxy.imagerecognition.Bean.picCatList;
import cn.thxy.imagerecognition.ModifyLabelsActivity;
import cn.thxy.imagerecognition.R;
import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class HobbyViewAdapter extends RecyclerView.Adapter<HobbyViewAdapter.MyViewHolder> {
    final Context context;
    public List<picCatList> list;
    private String id,parentId,name,sortOrder,isParent;
    private Boolean isNewRecord;
    private boolean btn_status;
    public List<String> nameList,idList;
    public List<picCatList> HobbyList;
    private boolean [] status;
    public HobbyViewAdapter(Context context, List<picCatList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hobby, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        picCatList piccatlist=list.get(position);
        idList=new ArrayList<>();
        status=new boolean[position];
        //idList.clear();
        holder.tv_hobbyId.setText(piccatlist.getId());
        holder.tv_habit.setText(piccatlist.getName());

        holder.tv_habit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:
                        if (!status[position]){
                            status[position]=true;
                            holder.tv_habit.setBackgroundColor(Color.parseColor("#70b5ed"));
                            holder.tv_habit.setTextColor(Color.parseColor("#ffffff"));
                            idList.add(holder.tv_hobbyId.getText().toString());
                        }else {
                            status[position]=false;
                            holder.tv_habit.setBackgroundColor(Color.parseColor("#ffffff"));
                            holder.tv_habit.setTextColor(Color.parseColor("#70b5ed"));
                            idList.remove(holder.tv_hobbyId.getText().toString());
                        }
                        break;
                    default:
                        if (!status[position-1]){
                            status[position-1]=true;
                            holder.tv_habit.setBackgroundColor(Color.parseColor("#70b5ed"));
                            holder.tv_habit.setTextColor(Color.parseColor("#ffffff"));
                            idList.add(holder.tv_hobbyId.getText().toString());
                        }else {
                            status[position-1]=false;
                            holder.tv_habit.setBackgroundColor(Color.parseColor("#ffffff"));
                            holder.tv_habit.setTextColor(Color.parseColor("#70b5ed"));
                            idList.remove(holder.tv_hobbyId.getText().toString());
                        }
                        break;
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_habit,tv_hobbyId;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_habit=(TextView)itemView.findViewById(R.id.tv_habit);
            tv_hobbyId=(TextView)itemView.findViewById(R.id.tv_hobbyid);

        }
    }







}
