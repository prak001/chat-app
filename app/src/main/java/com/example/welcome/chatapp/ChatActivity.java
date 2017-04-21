package com.example.welcome.chatapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.welcome.chatapp.adapter.TabAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabAdapter mAdapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //set firebase auth listener
        mAuth=FirebaseAuth.getInstance();
        //initialize views
        viewPager = (ViewPager) findViewById(R.id.viewPagerId);
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        actionBar = getActionBar();
        mAdapter=new TabAdapter(getSupportFragmentManager());
        //attach adapter to view pager
        viewPager.setAdapter(mAdapter);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        //set up listeners
        tabsStrip.setOnPageChangeListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_functions, menu);
        MenuItem searchMenuItem=menu.findItem(R.id.menu_logout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_logout:
                //Reset data in shared preference and intent to login activity
                Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(ChatActivity.this,MainActivity.class));
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
