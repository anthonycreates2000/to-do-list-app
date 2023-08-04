package com.example.keepoapp.data.requestbody;

import android.util.Log;

public class SearchToDoBody{
    private String searchQuery;
    private int filterUser;
    private int filterTodo;

    public SearchToDoBody(String searchQuery, int filterUser, int filterTodo) {
        this.searchQuery = searchQuery;
        Log.d("Number", filterUser + " " + filterTodo);
        this.filterUser = filterUser;
        this.filterTodo = filterTodo;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getFilterUser() {
        return filterUser;
    }

    public void setFilterUser(int filterUser) {
        this.filterUser = filterUser;
    }

    public int getFilterTodo() {
        return filterTodo;
    }

    public void setFilterTodo(int filterTodo) {
        this.filterTodo = filterTodo;
    }
}
