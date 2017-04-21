package com.example.welcome.chatapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.welcome.chatapp.Fragments.ChatWithGroup;
import com.example.welcome.chatapp.Fragments.ChatWithOtherUser;
import com.example.welcome.chatapp.Fragments.GroupChat;
import com.example.welcome.chatapp.Model.Group;
import com.example.welcome.chatapp.Model.User;
import com.example.welcome.chatapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 20-04-2017.
 */

public class GroupRecAdapter extends RecyclerView.Adapter<GroupRecAdapter.MyViewHolder> {

    Context mContext;
    public IndividualRecAdapter adapter;
    public List<Group> groupListNames=new ArrayList<>();
    public GroupRecAdapter(Context mContext,List<Group> groupListNames) {
        this.groupListNames=groupListNames;
        this.mContext=mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_individual_chat, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return groupListNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TextView userNameText;
        ImageView imgText;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            userNameText = (TextView) view.findViewById(R.id.userNameText);
            imgText=(ImageView)view.findViewById(R.id.img_profile);
        }
        //bind data to view here
        void bindData(int position)
        {
           // userNameText.setText(groupListNames.get(position).getName());
            userNameText.setText(groupListNames.get(position).getName());
           // TextDrawable drawable=TextDrawable.builder().buildRound(groupListNames.get(position).getName().charAt(0)+"",R.color.colorPrimary);
            TextDrawable drawable=TextDrawable.builder().buildRound(groupListNames.get(position).getName().charAt(0)+"",R.color.colorPrimary);
            imgText.setImageDrawable(drawable);
        }
        //set click listener here
        @Override
        public void onClick(View v) {
            //on click call chat fragment

            //get fragment manager
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();

            String name=groupListNames.get(getAdapterPosition()).getName();
            String id=groupListNames.get(getAdapterPosition()).getKey();
            Bundle bundle=new Bundle();
            bundle.putString("groupName",name);
            bundle.putString("groupId",id);

            //add adapter here
            ChatWithGroup chatWithGroup=new ChatWithGroup();
            chatWithGroup.setArguments(bundle);
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame,chatWithGroup);
            fragmentTransaction.addToBackStack(chatWithGroup.getClass().getName());
            fragmentTransaction.commit();

        }
    }
    public void notifyDataChanged(){
        notifyDataSetChanged();
    }
}
