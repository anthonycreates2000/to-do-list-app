package com.example.keepoapp.data.response;

import com.example.keepoapp.data.ToDo;

public class GetDetailToDoData extends CommonResponse {
    private ToDo data;
    public GetDetailToDoData(int status, String message, ToDo data) {
        super(status, message);
        this.data = data;
    }
    public ToDo getToDo() {
        return data;
    }

    public void setToDo(ToDo toDo) {
        this.data = toDo;
    }
}
