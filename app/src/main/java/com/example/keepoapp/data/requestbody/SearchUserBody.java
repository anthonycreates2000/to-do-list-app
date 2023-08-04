package com.example.keepoapp.data.requestbody;

public class SearchUserBody{
    private String user_id;
    private String searchQuery;

    public SearchUserBody(String user_id, String searchQuery) {
        this.user_id = user_id;
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
