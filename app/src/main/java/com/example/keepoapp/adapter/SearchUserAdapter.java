package com.example.keepoapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepoapp.adapter.diffUtils.SearchToDoDiffCallback;
import com.example.keepoapp.adapter.diffUtils.SearchUserDiffCallback;
import com.example.keepoapp.adapter.diffUtils.ToDoDiffCallback;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.User;
import com.example.keepoapp.databinding.SearchLayoutBinding;
import com.example.keepoapp.databinding.UserAdapterLayoutBinding;
import com.example.keepoapp.databinding.SearchToDoAdapterLayoutBinding;

import java.util.ArrayList;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    private ArrayList<User> users;
    public interface IClickListener{
        void clickListener(User user);
    }
    public IClickListener iClickListener;
    public SearchUserAdapter(){
        this.users = new ArrayList<>();
    }
    protected void launchDiffUtil(ArrayList<User> oldUsers, ArrayList<User> newUsers){
        SearchUserDiffCallback toDoDiffCallback = new SearchUserDiffCallback(oldUsers, newUsers);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(toDoDiffCallback);
        users.clear();
        users.addAll(newUsers);
        diffResult.dispatchUpdatesTo(this);
    }
    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UserAdapterLayoutBinding binding = UserAdapterLayoutBinding.inflate(inflater, parent, false);
        return new SearchUserAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.tvCardUserName.setText(user.getUsername());
        holder.binding.tvCardName.setText(user.getName());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                iClickListener.clickListener(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateAllItems(ArrayList<User> newUsers){
        ArrayList<User> oldUsers = (ArrayList<User>) users.clone();
        launchDiffUtil(oldUsers, newUsers);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        UserAdapterLayoutBinding binding;
        public ViewHolder(UserAdapterLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
