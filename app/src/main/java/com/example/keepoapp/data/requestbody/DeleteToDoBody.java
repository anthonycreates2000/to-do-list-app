package com.example.keepoapp.data.requestbody;

import java.util.ArrayList;

public class DeleteToDoBody {
    private ArrayList<String> todos;
    public DeleteToDoBody(ArrayList<String> toDos){
        this.todos = toDos;
    }
    public ArrayList<String> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<String> toDos) {
        this.todos = toDos;
    }
}
