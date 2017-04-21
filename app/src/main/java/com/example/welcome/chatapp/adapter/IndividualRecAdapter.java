package com.example.welcome.chatapp.adapter;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.welcome.chatapp.Fragments.ChatWithOtherUser;
import com.example.welcome.chatapp.Model.User;
import com.example.welcome.chatapp.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 17-04-2017.
 */

public class IndividualRecAdapter extends RecyclerView.Adapter<IndividualRecAdapter.MyViewHolder> {

    Context mContext;
    public IndividualRecAdapter adapter;
    public List<User> chatListNames=new ArrayList<>();
    public IndividualRecAdapter(Context mContext,List<User> chatListNames) {
        this.chatListNames=chatListNames;
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
        return chatListNames.size();
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
            userNameText.setText(chatListNames.get(position).getName());
            try {
                TextDrawable drawable = TextDrawable.builder().buildRound(chatListNames.get(position).getName().charAt(0) + "", R.color.colorPrimary);
                imgText.setImageDrawable(drawable);
            }catch (Exception e)
            {

            }
        }
        //set click listener here
        @Override
        public void onClick(View v) {
            //on click call chat fragment

            //get fragment manager
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();

            String email=chatListNames.get(getAdapterPosition()).getName();
            String userKey=chatListNames.get(getAdapterPosition()).getUserId();
            String name=chatListNames.get(getAdapterPosition()).getName();
            Bundle bundle=new Bundle();
            bundle.putString("dataEmailName",email);
            bundle.putString("userKey",userKey);
            bundle.putString("userName",name);

            //add adapter here
            ChatWithOtherUser chatWithOtherUser=new ChatWithOtherUser();
            chatWithOtherUser.setArguments(bundle);
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame,chatWithOtherUser);
            fragmentTransaction.addToBackStack(chatWithOtherUser.getClass().getName());
            fragmentTransaction.commit();

        }
    }
    public void notifyDataChanged(){
        notifyDataSetChanged();
    }
}
