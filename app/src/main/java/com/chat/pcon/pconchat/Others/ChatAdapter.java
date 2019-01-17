package com.chat.pcon.pconchat.Others;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chat.pcon.pconchat.Activities.ProfileActivity;
import com.chat.pcon.pconchat.Models.MessageInfo;
import com.chat.pcon.pconchat.R;
import java.text.SimpleDateFormat;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<MessageInfo> infos;
    Context context;

    public ChatAdapter(List<MessageInfo> infos){
        this.infos = infos;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        this.context = viewGroup.getContext();
        if(viewType==0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg_send, viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg_receive,viewGroup,false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        final MessageInfo info = infos.get(position);

        myViewHolder.name.setText(info.name);
        myViewHolder.msgBody.setText(info.msg);

//        myViewHolder.timestamp.setText(getDateTime(info.timestamp));

        myViewHolder.msgHead.setText(String.valueOf(info.name.charAt(0)).toUpperCase());
        GradientDrawable drawable = (GradientDrawable) myViewHolder.msgHead.getBackground();
        drawable.setColor(Color.parseColor(info.color));

        myViewHolder.msgHead.setTextColor(Color.WHITE);
        myViewHolder.msgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("uid", info.uid);
                context.startActivity(intent);
            }
        });

    }
//    String getDateTime(Timestamp timestamp){
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
//
//        String date = dateFormat.format(timestamp.toDate());
//        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
//        String time = timeFormat.format(timestamp.toDate().getTime());
//        String datetime = date+"  "+time;
//        return datetime;
//    }


    @Override
    public int getItemCount() {
        return infos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(infos.get(position).isReceived == false)
            return 0;
        else
            return 1;

    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView msgHead,name,msgBody,timestamp;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        msgHead = itemView.findViewById(R.id.msg_head);
        msgBody = itemView.findViewById(R.id.msg_body);
        name = itemView.findViewById(R.id.msg_name);
        timestamp = itemView.findViewById(R.id.msg_timestamp);
    }
}