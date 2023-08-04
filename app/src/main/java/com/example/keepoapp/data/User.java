package com.example.keepoapp.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.keepoapp.BR;

import java.io.Serializable;

public class User extends BaseObservable implements Serializable {
    private String name;
    private String username;
    private String password;
    private String user_id;
    public User(String user_id, String name, String username, String password) {
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.password = password;
        notifyPropertyChanged(BR.user_id);
        notifyPropertyChanged(BR.name);
        notifyPropertyChanged(BR.username);
        notifyPropertyChanged(BR.password);
    }
    public User(String user_id, String name, String username){
        this.user_id = user_id;
        this.name = name;
        this.username = username;
        this.password = "";
        notifyPropertyChanged(BR.user_id);
        notifyPropertyChanged(BR.name);
        notifyPropertyChanged(BR.username);
    }
    public User(String username, String password){
        this.password = password;
        this.username = username;
        notifyPropertyChanged(BR.password);
        notifyPropertyChanged(BR.username);
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }
    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
    @Bindable
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
