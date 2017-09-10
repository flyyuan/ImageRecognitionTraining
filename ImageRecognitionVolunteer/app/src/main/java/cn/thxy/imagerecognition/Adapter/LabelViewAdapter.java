package cn.thxy.imagerecognition.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.thxy.imagerecognition.R;


public class LabelViewAdapter extends RecyclerView.Adapter<LabelViewAdapter.MyViewHolder> /*implements View.OnClickListener,View.OnTouchListener*/{
        final Context context;
        public List<String> list;
        public static List<String> writeLabelList;
        private boolean [] status;

        public LabelViewAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_label, parent, false);
            writeLabelList=new ArrayList<>();
            writeLabelList.clear();
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.btn_label.setText(list.get(position));
            status=new boolean[list.size()];
            for (int i=0;i<=position;i++){
                status[i]=false;
            }
            holder.btn_label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        default:
                            if (status[position]){
                                status[position]=false;
                                writeLabelList.add(holder.btn_label.getText().toString());
                                holder.btn_label.setTextColor(Color.parseColor("#000000"));
                                holder.btn_label.setBackgroundColor(Color.parseColor("#70b5ed"));
                                System.out.println("++++ 确定");
                            }else {
                                status[position]=true;
                                writeLabelList.remove(holder.btn_label.getText().toString());
                                holder.btn_label.setBackgroundColor(Color.parseColor("#1295DA"));
                                holder.btn_label.setTextColor(Color.parseColor("#ffffff"));
                                System.out.println("++++ 取消");
                            }  System.out.println("-----"+holder.btn_label.getText()+" onClick "+position);
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
            private Button btn_label;

            public MyViewHolder(View itemView) {
                super(itemView);
                btn_label = ((Button) itemView.findViewById(R.id.btn_label1));

            }
        }
    }
