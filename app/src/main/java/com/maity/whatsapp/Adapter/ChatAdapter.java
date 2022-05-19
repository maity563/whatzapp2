package com.maity.whatsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.maity.whatsapp.Models.MessageModel;
import com.maity.whatsapp.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messagemodel;
    Context context;
    int sender=1;
    int reciever=2;
    String recId;

    public ChatAdapter(ArrayList<MessageModel> messagemodel, Context context) {
        this.messagemodel = messagemodel;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messagemodel, Context context, String recId) {
        this.messagemodel = messagemodel;
        this.context = context;

        this.recId = recId;
    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==sender)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }

    }

    public int getItemViewType(int position)
    {
        if(messagemodel.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
        {
            return sender;
        }
        else
        {
            return reciever;
        }


    }
    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel=messagemodel.get(position);

        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).sendermsg.setText(messageModel.getMessage());
        }
        else
        {
            ((RecieverViewHolder)holder).recievermsg.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messagemodel.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder
    {

        TextView recievermsg,recievertime;
        public RecieverViewHolder(@NonNull  View itemView) {
            super(itemView);
            recievermsg=itemView.findViewById(R.id.recieverText);
            recievertime=itemView.findViewById(R.id.recievertime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder
    {
        TextView sendermsg,sendertime;
        public SenderViewHolder(@NonNull  View itemView) {
            super(itemView);
            sendermsg=itemView.findViewById(R.id.sender_text);
            sendertime=itemView.findViewById(R.id.sender_time);
        }
    }




}
