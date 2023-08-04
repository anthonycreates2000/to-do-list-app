package com.example.keepoapp.adapter.diffUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.keepoapp.data.ToDo;

import java.util.ArrayList;

public abstract class ToDoDiffCallback extends DiffUtil.Callback {
    ArrayList<ToDo> oldToDos;
    ArrayList<ToDo> newToDos;
    public ToDoDiffCallback(ArrayList<ToDo> oldToDos, ArrayList<ToDo> newToDos) {
        this.oldToDos = oldToDos;
        this.newToDos = newToDos;
    }

    @Override
    public int getOldListSize() {
        return oldToDos.size();
    }

    @Override
    public int getNewListSize() {
        return newToDos.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        ToDo oldToDo = oldToDos.get(oldItemPosition);
        ToDo newToDo = newToDos.get(newItemPosition);
        return oldToDo.getTodo_id() == newToDo.getTodo_id();
    }
    @Override
    public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
