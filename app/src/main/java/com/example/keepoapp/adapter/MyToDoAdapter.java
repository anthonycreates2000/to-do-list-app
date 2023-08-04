package com.example.keepoapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepoapp.R;
import com.example.keepoapp.adapter.diffUtils.MyToDoDiffCallback;
import com.example.keepoapp.adapter.diffUtils.ToDoDiffCallback;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.databinding.MyToDoAdapterLayoutBinding;

import java.util.ArrayList;
import java.util.Iterator;

public class MyToDoAdapter extends RecyclerView.Adapter<MyToDoAdapter.ViewHolder>{
    ArrayList<ToDo> toDos;
    public MyToDoAdapter(){
        this.toDos = new ArrayList<ToDo>();
    }
    public interface IClickListener{
        void clickListener(ToDo todo);
    }
    public interface ICheckListener{
        void checkListener(ToDo todo, boolean b);
    }
    public IClickListener iClickListener;
    public ICheckListener iCheckListener;

    protected void launchDiffUtil(ArrayList<ToDo> oldToDos, ArrayList<ToDo> newToDos){
        ToDoDiffCallback toDoDiffCallback = new MyToDoDiffCallback(oldToDos, newToDos);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(toDoDiffCallback);

        toDos.clear();
        toDos.addAll(newToDos);
        diffResult.dispatchUpdatesTo(this);
    }
    public void updateAllItemsToDoData(ArrayList<ToDo> newToDos){
        ArrayList<ToDo> oldToDos = (ArrayList<ToDo>) toDos.clone();
        launchDiffUtil(oldToDos, newToDos);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyToDoAdapterLayoutBinding binding = MyToDoAdapterLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDo toDo = toDos.get(position);
        holder.binding.tvToDoTitle.setText(toDo.getTitle());
        holder.binding.checkDelete.setChecked(false);
        String dateText = holder.itemView.getContext().getText(R.string.last_edited).toString() + " " + toDo.getLast_edited();
        holder.binding.tvToDoDate.setText(dateText);
        holder.binding.checkDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                iCheckListener.checkListener(toDo, b);
            }
        });
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                iClickListener.clickListener(toDo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MyToDoAdapterLayoutBinding binding;
        public ViewHolder(MyToDoAdapterLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
