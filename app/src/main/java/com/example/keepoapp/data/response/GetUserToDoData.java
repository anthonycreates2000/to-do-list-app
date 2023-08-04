package com.example.keepoapp.data.response;

import android.util.Log;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.User;

import java.util.ArrayList;

public class GetUserToDoData extends CommonResponse {
    private Data data;
    public GetUserToDoData(int status, String message, Data data) {
        super(status, message);
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String userId;
        private User user;
        private ArrayList<ToDo> listTodo;
        public Data(String userId, String name, String username, ArrayList<ToDo> listTodo){
            this.userId = userId;
            this.user = new User(userId, name, username);
            this.listTodo = listTodo;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public ArrayList<ToDo> getListTodo() {
            return listTodo;
        }

        public void setListTodo(ArrayList<ToDo> listTodo) {
            this.listTodo = listTodo;
        }
    }
}
