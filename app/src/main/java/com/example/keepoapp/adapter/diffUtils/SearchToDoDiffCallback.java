package com.example.keepoapp.adapter.diffUtils;

import com.example.keepoapp.data.ToDo;

import java.util.ArrayList;

public class SearchToDoDiffCallback extends ToDoDiffCallback{

    public SearchToDoDiffCallback(ArrayList<ToDo> oldToDos, ArrayList<ToDo> newToDos) {
        super(oldToDos, newToDos);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ToDo oldToDo = oldToDos.get(oldItemPosition);
        ToDo newToDo = newToDos.get(newItemPosition);
        return  oldToDo.getTitle().equals(newToDo.getTitle()) &&
                oldToDo.getDescription().equals(newToDo.getDescription()) &&
                oldToDo.getLast_edited() == newToDo.getLast_edited() &&
                oldToDo.getUsername().equals(newToDo.getUsername()) &&
                oldToDo.getUser_id().equals(newToDo.getUser_id());
    }
}
