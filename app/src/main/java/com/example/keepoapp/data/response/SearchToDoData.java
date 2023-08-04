package com.example.keepoapp.data.response;

import com.example.keepoapp.data.ToDo;

import java.util.ArrayList;

public class SearchToDoData extends CommonResponse{
    private ArrayList<ToDo> data;

    public SearchToDoData(int status, String message, ArrayList<ToDo> data) {
        super(status, message);
        this.data = data;
    }

    public ArrayList<ToDo> getData() {
        return data;
    }

    public void setData(ArrayList<ToDo> data) {
        this.data = data;
    }
}
