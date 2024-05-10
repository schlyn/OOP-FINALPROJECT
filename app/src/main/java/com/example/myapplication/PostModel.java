package com.example.myapplication;

public class PostModel {

    private int postId;
    private String username;
    private String content;
    private String date;

    public PostModel( String username, String content, String date) {
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }



}