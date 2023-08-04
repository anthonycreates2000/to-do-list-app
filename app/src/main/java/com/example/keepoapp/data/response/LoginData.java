package com.example.keepoapp.data.response;

import com.example.keepoapp.data.User;

public class LoginData extends CommonResponse {
    private User data;
    public LoginData(int status, String message, User data) {
        super(status, message);
        this.data = data;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
