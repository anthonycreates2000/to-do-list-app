package com.example.keepoapp.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.keepoapp.R;
import com.example.keepoapp.adapter.DetailUserSearchToDoAdapter;
import com.example.keepoapp.adapter.SearchToDoAdapter;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.User;
import com.example.keepoapp.databinding.ActivityDetailUserBinding;
import com.example.keepoapp.viewmodel.UserViewModel;

import java.util.ArrayList;

public class DetailUserActivity extends BackableConnectivityActivity {
    public static int KEY_REQUEST_DETAIL_USER_REFRESH = 603;
    public static int KEY_RESULT_DETAIL_USER_REFRESH = 703;
    public static String KEY_USER = "com.example.keepoapp.KEY_USER_ID_DETAIL_USER_ACTIVITY";
    private UserViewModel model;
    private SearchToDoAdapter adapter;
    private ActivityDetailUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_user);
        model = new ViewModelProvider(this).get(UserViewModel.class);
        setListeners();
        initializeViewModel();
        prepareRecyclerView();
        checkThenLoadUseToDoData();
    }
    public void setListeners(){
        binding.imgBtnBack.setOnClickListener(new BackClickListener());
    }
    private User getUser(){
        User user = (User) getIntent().getSerializableExtra(KEY_USER);
        return user;
    }

    private void initializeViewModel(){
        model.iBeforeConnect = () -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tvNoData.setVisibility(View.GONE);
        };
        model.getBottomDialogMessageLiveData().observe(this, (error) -> {
            binding.progressBar.setVisibility(View.GONE);
            showErrorBottomSheetFragment(error);
        });
        model.getToDoLiveDataList().observe(this, (todos) -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.tvUsernameDetail.setText(getUser().getUsername());
            binding.tvNameDetail.setText(getUser().getName());

            ArrayList<ToDo> toDosArrayList = todos;
            int toDosLength = toDosArrayList.size();
            if(toDosLength == 0){
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
            binding.tvNumberOfTodos.setText("Todos: " + toDosLength);
            adapter.updateAllItems(toDosArrayList);
        });
    }
    private void prepareRecyclerView(){
        adapter = new DetailUserSearchToDoAdapter(getUser().getUsername());
        adapter.iClickListener = (toDo) -> {
            startDetailActivityIntent(toDo);
        };
        binding.recyclerTodo.setAdapter(adapter);
        binding.recyclerTodo.setLayoutManager(new LinearLayoutManager(this));
    }
    private void startDetailActivityIntent(ToDo todo){
        String userId = getUser().getUser_id();
        Intent intent = new Intent(this, DetailToDoActivity.class);
        intent.putExtra(DetailToDoActivity.KEY_USER_ID, userId);
        intent.putExtra(DetailToDoActivity.KEY_TODO_ID, todo.getTodo_id());
        intent.putExtra(DetailToDoActivity.KEY_REQUEST_RESPONSE_ID, new DetailToDoActivity.RequestResponse(KEY_REQUEST_DETAIL_USER_REFRESH, KEY_RESULT_DETAIL_USER_REFRESH));
        startActivityForResult(intent, KEY_REQUEST_DETAIL_USER_REFRESH);
    }
    private void loadUserToDoData(){
        model.loadToDoLiveData(getUser().getUser_id());
    }
    private void checkThenLoadUseToDoData() {model.checkThenReloadData(getUser().getUser_id());}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == KEY_REQUEST_DETAIL_USER_REFRESH &&
            resultCode == KEY_RESULT_DETAIL_USER_REFRESH){
            loadUserToDoData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    void backClickListener() {
        finish();
    }
}