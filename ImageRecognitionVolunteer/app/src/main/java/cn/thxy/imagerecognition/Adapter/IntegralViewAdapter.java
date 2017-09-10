package cn.thxy.imagerecognition.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.thxy.imagerecognition.Bean.integralData;
import cn.thxy.imagerecognition.IntegralActivity;
import cn.thxy.imagerecognition.MainActivity;
import cn.thxy.imagerecognition.R;
import cn.thxy.imagerecognition.Utils.ConnectMethod;

import static android.content.Context.MODE_PRIVATE;


public class IntegralViewAdapter extends RecyclerView.Adapter<IntegralViewAdapter.MyViewHolder> {
    final Context context;
    public List<integralData> list;
    private String label,createDate,integral,url;

    public IntegralViewAdapter(Context context, List<integralData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_integral, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        for(int i=0;i<list.size();i++){
        integralData integraldata=list.get(position);
        holder.tv_label.setText(integraldata.getLabel());
        holder.tv_createDate.setText(integraldata.getCreateDate());

        holder.tv_integral.setText(("+" + integraldata.getIntegral()));
        Glide.with(context)
                .load(integraldata.getPicUrl())
                .placeholder(R.drawable.refresh)     //加载中的显示图片
                .error(R.drawable.geterror)            //加载失败的显示的图片
                .thumbnail(0.1f)                        //会先加载缩略图 然后在加载全图
                .fitCenter()                               //图片居中
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存图片
                .into(holder.show_image);}
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_label,tv_createDate,tv_integral;
        private ImageView show_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_label=(TextView)itemView.findViewById(R.id.integral_label);
            tv_createDate=(TextView)itemView.findViewById(R.id.integral_time);
            tv_integral=(TextView)itemView.findViewById(R.id.integral_add);
            show_image=(ImageView)itemView.findViewById(R.id.integral_image);
        }
    }
}
