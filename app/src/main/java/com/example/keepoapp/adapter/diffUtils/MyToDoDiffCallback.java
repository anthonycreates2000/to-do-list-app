package com.example.keepoapp.adapter.diffUtils;

import com.example.keepoapp.data.ToDo;

import java.util.ArrayList;

public class MyToDoDiffCallback extends ToDoDiffCallback{
    public MyToDoDiffCallback(ArrayList<ToDo> oldToDos, ArrayList<ToDo> newToDos) {
        super(oldToDos, newToDos);
    }
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ToDo oldToDo = oldToDos.get(oldItemPosition);
        ToDo newToDo = newToDos.get(newItemPosition);
        return  oldToDo.getTitle().equals(newToDo.getTitle()) &&
                oldToDo.getLast_edited() == newToDo.getLast_edited();
    }
}
