package com.example.keepoapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepoapp.adapter.diffUtils.MyToDoDiffCallback;
import com.example.keepoapp.adapter.diffUtils.SearchToDoDiffCallback;
import com.example.keepoapp.adapter.diffUtils.ToDoDiffCallback;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.databinding.MyToDoAdapterLayoutBinding;
import com.example.keepoapp.databinding.SearchToDoAdapterLayoutBinding;

import java.util.ArrayList;

public abstract class SearchToDoAdapter extends RecyclerView.Adapter<SearchToDoAdapter.ViewHolder> {
    ArrayList<ToDo> toDos;
    public SearchToDoAdapter(){
        this.toDos = new ArrayList<ToDo>();
    }
    public MyToDoAdapter.IClickListener iClickListener;
    abstract void bind(SearchToDoAdapter.ViewHolder holder, ToDo currentToDoPosition);
    protected void launchDiffUtil(ArrayList<ToDo> oldToDos, ArrayList<ToDo> newToDos){
        ToDoDiffCallback toDoDiffCallback = new SearchToDoDiffCallback(oldToDos, newToDos);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(toDoDiffCallback);
        toDos.clear();
        toDos.addAll(newToDos);
        diffResult.dispatchUpdatesTo(this);
    }
    @NonNull
    @Override
    public SearchToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchToDoAdapterLayoutBinding binding = SearchToDoAdapterLayoutBinding.inflate(inflater, parent, false);
        return new SearchToDoAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchToDoAdapter.ViewHolder holder, int position) {
        ToDo toDo = toDos.get(position);
        bind(holder, toDo);
    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    public void updateAllItems(ArrayList<ToDo> newToDos){
        ArrayList<ToDo> oldToDos = (ArrayList<ToDo>) toDos.clone();
        launchDiffUtil(oldToDos, newToDos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SearchToDoAdapterLayoutBinding binding;
        public ViewHolder(SearchToDoAdapterLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
