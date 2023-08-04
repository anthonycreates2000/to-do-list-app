package com.example.keepoapp.adapter;

import android.view.View;

import com.example.keepoapp.R;
import com.example.keepoapp.data.ToDo;

public class BasicSearchToDoAdapter extends SearchToDoAdapter{
    @Override
    void bind(ViewHolder holder, ToDo toDo) {
        holder.binding.tvToDoTitle.setText(toDo.getTitle());
        String date = holder.itemView.getContext().getString(R.string.last_edited) + " " + toDo.getLast_edited();
        holder.binding.tvToDoDate.setText(date);
        holder.binding.tvToDoAuthor.setText(toDo.getUsername());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                iClickListener.clickListener(toDo);
            }
        });
    }
}
