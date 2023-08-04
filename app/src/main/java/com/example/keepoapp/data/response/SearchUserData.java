package com.example.keepoapp.data.response;

import com.example.keepoapp.data.User;

import java.util.ArrayList;

public class SearchUserData extends CommonResponse{
    private ArrayList<User> data;
    public SearchUserData(int status, String message, ArrayList<User> data) {
        super(status, message);
        this.data = data;
    }

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}
