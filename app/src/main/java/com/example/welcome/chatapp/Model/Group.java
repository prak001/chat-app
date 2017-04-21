package com.example.welcome.chatapp.Model;

import com.google.firebase.database.Exclude;

/**
 * Created by welcome on 20-04-2017.
 */

public class Group {
    private String name;
    @Exclude
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Group()
    {

    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
