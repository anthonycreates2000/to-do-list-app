package com.example.keepoapp.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.keepoapp.R;
import com.example.keepoapp.adapter.SearchUserAdapter;
import com.example.keepoapp.data.User;
import com.example.keepoapp.databinding.ActivitySearchUserBinding;
import com.example.keepoapp.fragment.BottomSheetFragment;
import com.example.keepoapp.viewmodel.SearchUserViewModel;

import java.util.ArrayList;

public class SearchUserActivity extends SearchActivity {
    private ActivitySearchUserBinding binding;
    private SearchUserViewModel searchUserViewModel;
    private SearchUserAdapter adapter;
    String searchText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user);
        searchUserViewModel = new ViewModelProvider(this).get(SearchUserViewModel.class);
        searchBinding = binding.searchLayout;
        searchBinding.edtSearch.setHint(R.string.search_user);
        setListeners();
        initializeViewModel();
        prepareRecyclerView();
    }

    void initializeViewModel(){
        searchUserViewModel.iBeforeConnect = () -> {
            closeSoftKeyboard();
            binding.tvResultsFor.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tvNoData.setVisibility(View.GONE);
        };
        searchUserViewModel.getErrorLiveData().observe(this, (error) -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
        searchUserViewModel.getBottomDialogMessageLiveData().observe(this, (error) -> {
            binding.progressBar.setVisibility(View.GONE);
            showErrorBottomSheetFragment(error);
        });
        searchUserViewModel.getSearchUserLiveData().observe(this, (searchUserData) -> {
            ArrayList<User> users = searchUserData.getData();
            binding.tvResultsFor.setVisibility(View.VISIBLE);
            String resultFor = getString(R.string.result_for) + " \"" + searchText + "\"";
            binding.tvResultsFor.setText(resultFor);
            binding.progressBar.setVisibility(View.GONE);
            if (users.size() == 0){
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
            adapter.updateAllItems(users);
        });
    }
    void prepareRecyclerView(){
        adapter = new SearchUserAdapter();
        adapter.iClickListener = (user) -> {
            goToDetailUserActivity(user);
        };
        binding.recyclerUsername.setAdapter(adapter);
        binding.recyclerUsername.setLayoutManager(new LinearLayoutManager(this));
    }
    void goToDetailUserActivity(User user){
        Intent intent = new Intent(this, DetailUserActivity.class);
        intent.putExtra(DetailUserActivity.KEY_USER, user);
        startActivity(intent);
    }
    @Override
    protected void searchClickListener() {
        searchText = searchBinding.edtSearch.getText().toString();
        searchUserViewModel.validateThenSearch(searchText);
    }
}