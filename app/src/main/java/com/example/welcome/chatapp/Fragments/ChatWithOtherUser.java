package com.example.welcome.chatapp.Fragments;

import android.icu.text.DateFormat;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.welcome.chatapp.Model.Message;
import com.example.welcome.chatapp.R;
import com.example.welcome.chatapp.Utils.AppController;
import com.example.welcome.chatapp.Utils.Constants;
import com.example.welcome.chatapp.adapter.ChatAdapter;
import com.example.welcome.chatapp.adapter.IndividualRecAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by welcome on 17-04-2017.
 */

public class ChatWithOtherUser extends Fragment implements View.OnClickListener {
    private EditText text;
    private Button sendButton;
    private String email,userKey,name;
    private DatabaseReference mDatabaseRef;
    private List<Message> chatHistory1=new ArrayList<>(),chatHistory=new ArrayList<>();
    private List<Message> chatHistory2=new ArrayList<>();
    public ChatAdapter adapter;
    public RecyclerView chatHistoryRecycler;

    public ChatWithOtherUser()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_chat_with_other_user, container, false);
        text=(EditText)v.findViewById(R.id.textInput);
        sendButton=(Button)v.findViewById(R.id.send);
        chatHistoryRecycler=(RecyclerView)v.findViewById(R.id.chatHistoryRecycler);
        //bring previous chat data from in sorted order
        //set toolbar with email id or name
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(getArguments().getString("dataEmailName"));
        Bundle bundle=getArguments();
        email=bundle.getString("dataEmailName");
        userKey=bundle.getString("userKey");
        name=bundle.getString("userName");

        //bring previous messages and show
        chatHistory();

        adapter = new ChatAdapter(getActivity(),chatHistory);

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
                if(TextUtils.isEmpty(text.getText().toString()))
                {
                    Toast.makeText(getContext(),"Empty Text",Toast.LENGTH_SHORT).show();
                    return;
                }
                //else push text to database
                //get timestamp
                String currentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String messageTobeSent=text.getText().toString();

                //form message model and pass to database
                Message message=new Message(currentTime,messageTobeSent);

                /*//add message to chat database
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Chats").push();
                String messageKeyGen=mDatabaseRef.push().getKey();
                mDatabaseRef.setValue(message);*/

                //add key of message key to sender and receiver
                mDatabaseRef=FirebaseDatabase.getInstance().getReference("users")
                        .child(AppController.prefs.getString(Constants.loggedInKey,""))
                        .child(userKey).push();
                mDatabaseRef.setValue(message);

                //clear textview
                text.setText("");

                break;
        }
    }
    public void chatHistory()
    {
        //message which other recievr had sent to sender

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(userKey).child(AppController.prefs.getString(Constants.loggedInKey,""));
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatHistory1=new ArrayList<Message>();
                chatHistory=new ArrayList<Message>();
                for (DataSnapshot s:dataSnapshot.getChildren())
                {
                    //add to message model
                    Message message=s.getValue(Message.class);
                    message.setType(2);
                    chatHistory1.add(message);
                }
                chatHistory.addAll(chatHistory2);
                chatHistory.addAll(chatHistory1);
                //sort all messages according to date and time and set to adapter
                Collections.sort(chatHistory, new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        return o1.getDateTime().compareTo(o2.getDateTime());
                    }
                });

                adapter.chatHistory=chatHistory;
                adapter.notifyDataChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //messages which sender had sent to receiver
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(AppController.prefs.getString(Constants.loggedInKey,"")).child(userKey);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatHistory2=new ArrayList<Message>();
                chatHistory=new ArrayList<Message>();
                for (DataSnapshot s:dataSnapshot.getChildren())
                {
                    //add to emssage model
                    Message message=s.getValue(Message.class);
                    message.setType(1);
                    chatHistory2.add(message);
                }

                chatHistory.addAll(chatHistory1);
                chatHistory.addAll(chatHistory2);
                //sort all messages according to date and time and set to adapter
                Collections.sort(chatHistory, new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        return o1.getDateTime().compareTo(o2.getDateTime());
                    }
                });
                adapter.chatHistory=chatHistory;
                adapter.notifyDataChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //sort all messages according to date and time and set to adapter
    }
}
