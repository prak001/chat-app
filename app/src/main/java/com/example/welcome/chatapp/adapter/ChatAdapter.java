package com.example.welcome.chatapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.welcome.chatapp.Fragments.ChatWithOtherUser;
import com.example.welcome.chatapp.Model.Message;
import com.example.welcome.chatapp.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 19-04-2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    Context mContext;
    public IndividualRecAdapter adapter;
    public List<Message> chatHistory=new ArrayList<>();
    public ChatAdapter(Context mContext,List<Message> chatHistory) {
        this.chatHistory=chatHistory;
        this.mContext=mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return chatHistory.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TextView userHistoryText;
        private TextView dateTime;
        private LinearLayout linearLayout;
        private View view;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            userHistoryText = (TextView) view.findViewById(R.id.userNameText);
            dateTime=(TextView)view.findViewById(R.id.dateTime);
            linearLayout=(LinearLayout)view.findViewById(R.id.linearLayout);
        }
        //bind data to view here
        void bindData(int position)
        {
            userHistoryText.setText(chatHistory.get(position).getMessage());
            dateTime.setText(chatHistory.get(position).getDateTime());
            if(chatHistory.get(position).getType()==1) {
                linearLayout.setGravity(Gravity.RIGHT);
                userHistoryText.setTextColor(mContext.getResources().getColor(R.color.btn_logut_bg));
            }
            else if(chatHistory.get(position).getType()==2)
            {
                userHistoryText.setTextColor(mContext.getResources().getColor(R.color.blue));
                linearLayout.setGravity(Gravity.LEFT);
            }
        }
        //set click listener here
        @Override
        public void onClick(View v) {

        }
    }
    public void notifyDataChanged(){
        notifyDataSetChanged();
    }
}
