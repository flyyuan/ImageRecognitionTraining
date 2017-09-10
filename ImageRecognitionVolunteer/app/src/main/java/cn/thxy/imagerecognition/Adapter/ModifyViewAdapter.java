package cn.thxy.imagerecognition.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import cn.thxy.imagerecognition.ModifyLabelsActivity;
import cn.thxy.imagerecognition.Bean.historyLabels;
import cn.thxy.imagerecognition.R;
import cn.thxy.imagerecognition.Utils.ConnectMethod;


public class ModifyViewAdapter extends RecyclerView.Adapter<ModifyViewAdapter.MyViewHolder> {
    final Context context;
    public List<historyLabels> list;
    private String picid,label,targetlabel;
    private String inputName;
    private int itemPosition;

    public ModifyViewAdapter(Context context, List<historyLabels> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_modifylabel, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        for(int i=0;i<list.size();i++){
        historyLabels historylabels=list.get(position);
        holder.tv_label.setText(historylabels.getLabel());
        holder.tv_picId.setText(historylabels.getId());
        holder.tv_modifyTime.setText(historylabels.getUpdateDate());
        String url="http://39.108.69.214:8080/pic/image/"+historylabels.getUrl();
        Glide.with(context)
                .load(url)
                .error(R.drawable.geterror)            //加载失败的显示的图片
                .thumbnail(0.1f)                        //会先加载缩略图 然后在加载全图
                .fitCenter()                               //图片居中
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存图片
                .into(holder.iv_iamge);
        }

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picid=holder.tv_picId.getText().toString();
                label=holder.tv_label.getText().toString();
                deleteItem();
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());

            }
        });
        holder.tv_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picid=holder.tv_picId.getText().toString();
                label=holder.tv_label.getText().toString();
                inputDialog(context,picid,label);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_picId,tv_label,tv_modifyTime;
        private ImageView iv_iamge;
        private Button btn_delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_label=(TextView)itemView.findViewById(R.id.tv_modifylabel);
            tv_picId=(TextView)itemView.findViewById(R.id.tv_modifypic);
            tv_modifyTime=(TextView)itemView.findViewById(R.id.tv_modifytime);
            btn_delete=(Button)itemView.findViewById(R.id.btn_deletelabel);
            iv_iamge=(ImageView)itemView.findViewById(R.id.iv_modifyimage);

        }
    }


    public void deleteItem(){
        DeleteLabelThread deleteLabelThread=new DeleteLabelThread();
        Thread deletethread =new Thread(deleteLabelThread);
        deletethread.start();
    }
    public class UpdataLabelThread implements Runnable{

        @Override
        public void run() {
            ConnectMethod modify=new ConnectMethod(context);
            modify.UpdataHistoryWriteLabel(picid,label,inputName);
            if (modify.canUpdateNum==null){
                System.out.println("++++canUpdateNum is null");
            }else if(modify.updateStatus.equals("1")){
                Looper.prepare();
                ModifyLabelsActivity activity=new ModifyLabelsActivity();
                Toast.makeText(context,"修改成功,剩余修改次数为"+modify.canUpdateNum,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,ModifyLabelsActivity.class);
                context.startActivity(intent);
                activity.finish();
                Looper.loop();
            }else {
                Looper.prepare();
                Toast.makeText(context,"修改失败",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

        }
    }

    public class DeleteLabelThread implements Runnable{

        @Override
        public void run() {
            ConnectMethod delete=new ConnectMethod(context);
            delete.DeleteHistoryWriteLabel(picid,label);
            System.out.println("+++ deleteStatus "+delete.deleteStatus);
            if (delete.deleteStatus.equals("1")){
                Looper.prepare();
               Toast.makeText(context,"已删除当前标签",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

        }
    }



    public  void inputDialog(final Context context, final String picid, final String label) {
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
                            Toast.makeText(context,"输入为空",Toast.LENGTH_SHORT).show();
                        }else {
                            UpdataLabelThread updataLabelThread=new UpdataLabelThread();
                            Thread thread1=new Thread(updataLabelThread);
                            thread1.start();
                        }
                    }
                });
        builder.show();

    }


}
