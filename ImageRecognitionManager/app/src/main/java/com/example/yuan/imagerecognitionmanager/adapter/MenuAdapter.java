/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.yuan.imagerecognitionmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuan.imagerecognitionmanager.R;
import com.example.yuan.imagerecognitionmanager.javaBean.User;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {

    Context mContext;

    private List<User> mUserList;

    private OnItemClickListener mOnItemClickListener;

    public MenuAdapter(List<User> userList , Context context){
        mContext = context;
        mUserList = userList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
    }

    @Override
    public MenuAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.DefaultViewHolder holder, int position ) {
        User user = mUserList.get(position);
        String status = null;
        switch(user.getJurisdicStatus()){
            case 1:
                status = "："+mContext.getResources().getString(R.string.Fill_in_first);
                break;
            case 2:
                status = "："+mContext.getResources().getString(R.string.First_choice);
                break;
            case 3:
                status ="："+mContext.getResources().getString(R.string.No_fill_in);
                break;
            case 4:
                 status = "："+mContext.getResources().getString(R.string.forbid_choice);
                break;
            case 5:
                status = "："+mContext.getResources().getString(R.string.forbid_choice_fill);
                break;
            case 6:
                status = "："+mContext.getResources().getString(R.string.normal);
        }
        Log.d("----------", "onBindViewHolder: " + user.getName() + status);
        //获取用户当前状态

        int writeNum = user.getWriteNum();
        int selectNum = user.getSelectNum();
        if (writeNum == 0){writeNum = 1;}
        if (selectNum == 0){selectNum = 1;}
        float writeSuccRate = (user.getWriteSuccNum()/writeNum)*100;
        float selectSuccRate = (user.getSelectSuccNum()/selectNum)*100;
        //计算用户当前正确率

        holder.tvTitle.setText(user.getName() + status );
        holder.tvState_select.setText(mContext.getResources().getString(R.string.choice_accuracy)+selectSuccRate + "%");
        holder.tvState_write.setText(mContext.getResources().getString(R.string.fill_accuracy)+writeSuccRate + "%");

        //加载头像
        Glide.with(mContext)
                .load("http://39.108.69.214:8080" + user.getPhoto())
                .placeholder(R.drawable.nothing)
                .into(holder.ivPhoto);
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivPhoto;
        TextView tvTitle;
        TextView tvState_select;
        TextView tvState_write;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivPhoto = (ImageView) itemView.findViewById(R.id.photo);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_name);
            tvState_select = (TextView) itemView.findViewById(R.id.tv_state_select);
            tvState_write = (TextView) itemView.findViewById(R.id.tv_state_write);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
