package com.example.keepoapp.data.response;

public class CommonResponse {
    protected int status;
    protected String message;
    public CommonResponse(int status, String message){
        this.status = status;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
