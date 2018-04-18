package com.example.chatroomfirebaseapp;

public class Users {
    String userID, email, password, fname, lname;

    public Users() {
    }

    public Users(String userID, String email, String password, String fname, String lname) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
    }
}
