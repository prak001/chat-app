package com.example.welcome.chatapp.Model;

/**
 * Created by welcome on 20-04-2017.
 */

public class GroupMessage {
    private String name;
    private String message;
    private String id;

    public GroupMessage()
    {

    }

    public GroupMessage(String name, String message,String id) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
