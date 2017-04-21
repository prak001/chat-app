package com.example.welcome.chatapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.welcome.chatapp.Model.Group;
import com.example.welcome.chatapp.Model.GroupMessage;
import com.example.welcome.chatapp.Model.Message;
import com.example.welcome.chatapp.R;
import com.example.welcome.chatapp.Utils.AppController;
import com.example.welcome.chatapp.Utils.Constants;
import com.example.welcome.chatapp.adapter.ChatAdapter;
import com.example.welcome.chatapp.adapter.GroupChatAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by welcome on 20-04-2017.
 */

public class ChatWithGroup extends Fragment  implements View.OnClickListener,View.OnKeyListener {
    private EditText text;
    private Button sendButton;
    public RecyclerView chatHistoryRecycler;
    private DatabaseReference mDatabaseRef;
    public GroupChatAdapter adapter;
    private String groupId;
    private List<GroupMessage> chatHistory=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat_with_other_user, container, false);
        text=(EditText)v.findViewById(R.id.textInput);
        sendButton=(Button)v.findViewById(R.id.send);
        chatHistoryRecycler=(RecyclerView)v.findViewById(R.id.chatHistoryRecycler);

        //set toolbar with email id or name
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(getArguments().getString("groupName"));
        Bundle bundle=getArguments();

        groupId=bundle.getString("groupId");

        chatHistory();

        adapter = new GroupChatAdapter(getActivity(),chatHistory);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        chatHistoryRecycler.setLayoutManager(mLayoutManager);
        chatHistoryRecycler.setAdapter(adapter);

        //push text into others database
        sendButton.setOnClickListener(this);
        return v;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send:
                //check if text is empty when send button is clicked
                if(TextUtils.equals(text.getText().toString(),""))
                {
                    Toast.makeText(getContext(),"Empty Text",Toast.LENGTH_SHORT).show();
                    return;
                }
                String name=AppController.prefs.getString(Constants.loggedInName,"");
                String messge=text.getText().toString();
                String id=AppController.prefs.getString(Constants.loggedInKey,"");

                //form message
                GroupMessage groupMessage=new GroupMessage(name,messge,id);

                //add key of message key to sender and receiver
                mDatabaseRef=FirebaseDatabase.getInstance().getReference("Groupss")
                        .child(groupId)
                        .child("messages").push();
                mDatabaseRef.setValue(groupMessage);

                //set textview to empty
                text.setText("");
                break;

        }
    }

    public void chatHistory()
    {
        //message which other recievr had sent to sender
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Groupss").child(groupId).child("messages");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatHistory=new ArrayList<>();
                for (DataSnapshot s:dataSnapshot.getChildren())
                {
                    //add to message model
                    GroupMessage message=s.getValue(GroupMessage.class);
                    chatHistory.add(message);
                }

                adapter.chatHistory=chatHistory;
                adapter.notifyDataChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            getFragmentManager().popBackStack();
            return  true;
        }
        return false;
    }
}
