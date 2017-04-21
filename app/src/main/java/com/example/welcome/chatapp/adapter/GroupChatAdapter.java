package com.example.welcome.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.welcome.chatapp.Model.GroupMessage;
import com.example.welcome.chatapp.Model.Message;
import com.example.welcome.chatapp.R;
import com.example.welcome.chatapp.Utils.AppController;
import com.example.welcome.chatapp.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 20-04-2017.
 */

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.MyViewHolder> {

    Context mContext;
    public IndividualRecAdapter adapter;
    public List<GroupMessage> chatHistory=new ArrayList<>();
    public GroupChatAdapter(Context mContext,List<GroupMessage> chatHistory) {
        this.chatHistory=chatHistory;
        this.mContext=mContext;
    }


    @Override
    public GroupChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_chat_item, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupChatAdapter.MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return chatHistory.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TextView senderName;
        private TextView message;
        private View view;
        private LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            senderName=(TextView)view.findViewById(R.id.name);
            message=(TextView)view.findViewById(R.id.message);
            //view=(View)view.findViewById(R.id.viewId);
            linearLayout=(LinearLayout)view.findViewById(R.id.linearLayout);
        }
        //bind data to view here
        void bindData(int position)
        {
            senderName.setText(chatHistory.get(position).getName());
            message.setText(chatHistory.get(position).getMessage());
            if(TextUtils.equals(chatHistory.get(position).getId(), AppController.prefs.getString(Constants.loggedInKey,"")))
            {
                linearLayout.setGravity(Gravity.RIGHT);
                message.setTextColor(mContext.getResources().getColor(R.color.btn_logut_bg));

            }
            else
            {
                linearLayout.setGravity(Gravity.LEFT);
                message.setTextColor(mContext.getResources().getColor(R.color.blue));

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
