package com.example.welcome.chatapp.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.welcome.chatapp.FirebaseUtil.FireBaseDatabaseUtility;
import com.example.welcome.chatapp.Model.User;
import com.example.welcome.chatapp.R;
import com.example.welcome.chatapp.Utils.AppController;
import com.example.welcome.chatapp.Utils.Constants;
import com.example.welcome.chatapp.adapter.IndividualRecAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 16-04-2017.
 */

public class IndividualChat extends Fragment {
    private RecyclerView recyclerView;
    public IndividualRecAdapter adapter;
    public List<User> chatListNames;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_individual,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.individualRecycler);

        //fetch list of users here
        chatListNames=new ArrayList<>();

        //firebase for fetching user list
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.chatListNames=new ArrayList<User>();
                chatListNames=new ArrayList<User>();

                for (DataSnapshot s:dataSnapshot.getChildren())
                {//not to add to list the same id as taht of user
                    if(!TextUtils.equals(s.getKey(),AppController.prefs.getString(Constants.loggedInKey,""))) {
                        User user = s.getValue(User.class);
                        chatListNames.add(user);
                    }
                }
                adapter.chatListNames=chatListNames;
                adapter.notifyDataChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new IndividualRecAdapter(getActivity(),chatListNames);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
