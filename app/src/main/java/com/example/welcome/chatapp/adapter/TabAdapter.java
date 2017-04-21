package com.example.welcome.chatapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.welcome.chatapp.Fragments.GroupChat;
import com.example.welcome.chatapp.Fragments.IndividualChat;

/**
 * Created by welcome on 16-04-2017.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Individual", "Group" };
    public TabAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new IndividualChat();
            case 1:
                return new GroupChat();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
