package com.example.keepoapp.adapter;

import android.util.Log;
import android.view.View;

import com.example.keepoapp.R;
import com.example.keepoapp.data.ToDo;

public class DetailUserSearchToDoAdapter extends SearchToDoAdapter{
    String username;
    public DetailUserSearchToDoAdapter(String username){
        super();
        this.username = username;
    }
    @Override
    void bind(ViewHolder holder, ToDo toDo) {
        Log.d("Info", toDo.getTitle());
        holder.binding.tvToDoTitle.setText(toDo.getTitle());
        String date = holder.itemView.getContext().getText(R.string.last_edited).toString() + " " + toDo.getLast_edited();
        holder.binding.tvToDoDate.setText(date);
        holder.binding.tvToDoAuthor.setText(username);
        holder.binding.getRoot().setOnClickListener(view -> iClickListener.clickListener(toDo));
    }
}
