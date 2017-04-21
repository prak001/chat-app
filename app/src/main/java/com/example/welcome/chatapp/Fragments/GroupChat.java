package com.example.welcome.chatapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.welcome.chatapp.FirebaseUtil.FireBaseDatabaseUtility;
import com.example.welcome.chatapp.Model.Group;
import com.example.welcome.chatapp.Model.User;
import com.example.welcome.chatapp.R;
import com.example.welcome.chatapp.adapter.GroupRecAdapter;
import com.example.welcome.chatapp.adapter.IndividualRecAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by welcome on 16-04-2017.
 */

public class GroupChat extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    public List<Group> groupListNames;
    public GroupRecAdapter adapter;
    FloatingActionButton floatingActionButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_group,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.groupRecycler);
        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.fab);

        //fetch list of users here
        groupListNames=new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Groupss");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapter.groupListNames=new ArrayList<Group>();
                groupListNames=new ArrayList<>();
                for (DataSnapshot s:dataSnapshot.getChildren())
                {
                    Group group=s.getValue(Group.class);
                    group.setKey(s.getKey());
                    groupListNames.add(group);
                }
                adapter.groupListNames=groupListNames;
                adapter.notifyDataChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new GroupRecAdapter(getActivity(),groupListNames);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        floatingActionButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
                final EditText edittext = new EditText(getContext());
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Add group");
                alert.setView(edittext);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Group group=new Group(edittext.getText().toString());
                        FireBaseDatabaseUtility.getInstance().insrtNewGroup(group);
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.dismiss();
                    }
                });
                alert.show();

                break;
        }
    }
}
