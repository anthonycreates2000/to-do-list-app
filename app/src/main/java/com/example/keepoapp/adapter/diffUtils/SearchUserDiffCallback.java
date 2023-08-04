package com.example.keepoapp.adapter.diffUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.keepoapp.data.User;

import java.util.ArrayList;

public class SearchUserDiffCallback extends DiffUtil.Callback {
    private ArrayList<User> oldUsers;
    private ArrayList<User> newUsers;

    public SearchUserDiffCallback(ArrayList<User> oldUsers, ArrayList<User> newUsers) {
        this.oldUsers = oldUsers;
        this.newUsers = newUsers;
    }

    @Override
    public int getOldListSize() {
        return oldUsers.size();
    }

    @Override
    public int getNewListSize() {
        return newUsers.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = oldUsers.get(oldItemPosition);
        User newUser = newUsers.get(newItemPosition);
        return oldUser.getUser_id().equals(newUser.getUser_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = oldUsers.get(oldItemPosition);
        User newUser = newUsers.get(newItemPosition);
        return  oldUser.getName().equals(newUser.getName()) &&
                oldUser.getUsername().equals(newUser.getUsername());
    }
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
