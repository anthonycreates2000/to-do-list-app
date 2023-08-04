package com.example.keepoapp.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.keepoapp.formatter.Formatter;

import java.io.Serializable;
import java.util.Date;

public class ToDo extends BaseObservable implements Serializable {
    private String todo_id;
    private String title;
    private Date last_edited;
    private String description;
    private String user_id;
    private String username;
    public ToDo(String todo_id, String title, Date last_edited, String user_id, String username){
        this.todo_id = todo_id;
        this.title = title;
        this.last_edited = last_edited;
        this.user_id = user_id;
        this.username = username;
        notifyPropertyChanged(BR.todo_id);
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.last_edited);
        notifyPropertyChanged(BR.user_id);
        notifyPropertyChanged(BR.username);
    }
    public ToDo(String todo_id, String title, String description){
        this.todo_id = todo_id;
        this.title = title;
        this.description = description;
        notifyPropertyChanged(BR.todo_id);
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.description);
    }
    public ToDo(String title, String description){
        this.title = title;
        this.description = description;
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.description);
    }
    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable
    public String getLast_edited() {
        String human_readable_date = new Formatter().getHumanReadableDate(last_edited);
        return human_readable_date;
    }

    public void setLast_edited(Date last_edited) {
        this.last_edited = last_edited;
        notifyPropertyChanged(BR.last_edited);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
    @Bindable
    public String getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(String todo_id) {
        this.todo_id = todo_id;
        notifyPropertyChanged(BR.todo_id);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
