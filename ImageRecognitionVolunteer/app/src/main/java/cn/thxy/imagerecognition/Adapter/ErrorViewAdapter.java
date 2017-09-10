package cn.thxy.imagerecognition.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.thxy.imagerecognition.R;


public class ErrorViewAdapter extends RecyclerView.Adapter<ErrorViewAdapter.MyViewHolder> {
        final Context context;
        public List<String> list;
        public static List<String> errorLabelList;
        private boolean [] status;


        public ErrorViewAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_error, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.btn_error.setText(list.get(position));
            errorLabelList=new ArrayList<>();
            errorLabelList.clear();
            status=new boolean[position];
            holder.btn_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:
                            System.out.println("----"+position+"  "+status.length);
                            if (!status[0]){
                                status[0]=true;
                                errorLabelList.add(holder.btn_error.getText().toString());
                                holder.btn_error.setTextColor(Color.parseColor("#ffffff"));
                                holder.btn_error.setBackgroundColor(Color.parseColor("#ed4848"));
                            }else {
                                status[0]=false;
                                errorLabelList.remove(holder.btn_error.getText().toString());
                                holder.btn_error.setTextColor(Color.parseColor("#000000"));
                                holder.btn_error.setBackgroundColor(Color.parseColor("#e2d6d6"));
                            }
                            break;
                        default:
                            System.out.println("----"+position+"  "+status.length);
                           if (!status[position-1]){
                                status[position-1]=true;
                                errorLabelList.add(holder.btn_error.getText().toString());
                                holder.btn_error.setTextColor(Color.parseColor("#ffffff"));
                                holder.btn_error.setBackgroundColor(Color.parseColor("#ed4848"));
                           }else {
                                status[position-1]=false;
                                errorLabelList.remove(holder.btn_error.getText().toString());
                                holder.btn_error.setTextColor(Color.parseColor("#000000"));
                                holder.btn_error.setBackgroundColor(Color.parseColor("#e2d6d6"));
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
            private Button btn_error;
            public MyViewHolder(View itemView) {
                super(itemView);
                btn_error = ((Button) itemView.findViewById(R.id.btn_1));

            }
        }
    }
