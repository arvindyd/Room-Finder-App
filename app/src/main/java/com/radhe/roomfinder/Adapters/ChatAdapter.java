package com.radhe.roomfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;

import com.radhe.roomfinder.Models.Chats;
import com.radhe.roomfinder.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Chats> messageModel;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<Chats> messageModel, Context context) {
        this.messageModel = messageModel;
        this.context = context;
    }

//    public ChatAdapter(ArrayList<Messages> messageModel, Context context, String recId) {
//        this.messageModel = messageModel;
//        this.context = context;
//        this.recId = recId;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver, parent, false);
            return new ReciverViewHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (messageModel.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {

            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Chats messages = messageModel.get(position);


        if (holder.getClass() == SenderViewHolder.class) {

            ((SenderViewHolder) holder).sendertxt.setText(messages.getMessage());

            String time = TimeAgo.using(messages.getTimestamp());
            ((SenderViewHolder) holder).senderTim.setText(time);

        } else {
            ((ReciverViewHolder) holder).recivertxt.setText(messages.getMessage());

            String time = TimeAgo.using(messages.getTimestamp());
            ((ReciverViewHolder) holder).reciverTime.setText(time);

        }

    }

    @Override
    public int getItemCount() {
        return messageModel.size();
    }


    public class ReciverViewHolder extends RecyclerView.ViewHolder {

        TextView recivertxt, reciverTime;

        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);

            recivertxt = itemView.findViewById(R.id.reciverTxt);
            reciverTime = itemView.findViewById(R.id.reciverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView sendertxt, senderTim;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderTim = itemView.findViewById(R.id.sendTim);
            sendertxt = itemView.findViewById(R.id.sendertxt);
        }
    }
}
