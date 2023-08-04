package com.example.keepoapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.keepoapp.R;
import com.example.keepoapp.adapter.MyToDoAdapter;
import com.example.keepoapp.databinding.ActivityMyToDoBinding;
import com.example.keepoapp.preferences.SharedPreferenceManager;
import com.example.keepoapp.viewmodel.MyToDoViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MyToDoActivity extends BackableConnectivityActivity {
    public static int RESULT_REFRESH_MY_TODO = 600;
    public static int REQUEST_REFRESH_MY_TODO = 700;
    private ActivityMyToDoBinding binding;
    private MyToDoViewModel myToDoViewModel;
    private String user_id = "";
    private Snackbar snackbar;
    private MyToDoAdapter myToDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MyToDoActivity.this, R.layout.activity_my_to_do);
        snackbar = Snackbar.make(MyToDoActivity.this, binding.rootLayout, "", Snackbar.LENGTH_INDEFINITE);
        configureSnackbar();
        initializeViewModel();
        setListener();
        prepareRecyclerView();
    }
    void configureSnackbar(){
        snackbar.setAction("Delete", new SnackbarDeleteListener());
        snackbar.setActionTextColor(getResources().getColor(R.color.colorSnackbarAction));
    }
    void initializeViewModel(){
        myToDoViewModel = new ViewModelProvider(this).get(MyToDoViewModel.class);
        myToDoViewModel.iBeforeConnect = () -> {
            snackbar.dismiss();
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tvNoData.setVisibility(View.GONE);
        };
        myToDoViewModel.getDeletedCommonResponse().observe(MyToDoActivity.this, (commonResponse -> {
            Toast.makeText(this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
            myToDoViewModel.applyDeletedChangeToUserData();
            myToDoViewModel.forceCleanAllDeletedToDo();
        }));
        myToDoViewModel.getBottomDialogMessageLiveData().observe(MyToDoActivity.this, (error) -> {
            binding.progressBar.setVisibility(View.GONE);
            showErrorBottomSheetFragment(error);
        });
        myToDoViewModel.getWillBeDeletedToDo().observe(MyToDoActivity.this, (willBeDeletedToDos) -> {
            int size = willBeDeletedToDos.size();
            if (size == 0){
                snackbar.dismiss();
            }
            else{
                snackbar.setText(willBeDeletedToDos.size() + getString(R.string.items));
                snackbar.show();
            }
        });
        myToDoViewModel.getToDoLiveDataList().observe(this, (toDos) ->  {
            binding.progressBar.setVisibility(View.GONE);
            myToDoViewModel.forceCleanAllDeletedToDo();
            myToDoAdapter.updateAllItemsToDoData(toDos);
            if (toDos.size() == 0){
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });
        SharedPreferenceManager manager = new SharedPreferenceManager(this);
        user_id = manager.getUserData().getUser_id();
        myToDoViewModel.checkThenReloadData(user_id);
    }
    void setListener(){
        binding.fabCreateToDo.setOnClickListener(new FABCreateToDoListener());
        binding.imgBtnBack.setOnClickListener(new BackClickListener());
    }
    void prepareRecyclerView(){
        myToDoAdapter = new MyToDoAdapter();
        myToDoAdapter.iClickListener = (toDo) -> {
            goToDetailActivity(toDo.getTodo_id());
        };
        myToDoAdapter.iCheckListener = (toDo, b) -> {
            if (b){
                myToDoViewModel.addWillBeDeletedToDo(toDo.getTodo_id());
            }
            else{
                myToDoViewModel.removeWillBeDeletedToDo(toDo.getTodo_id());
            }
        };
        binding.recyclerToDo.setAdapter(myToDoAdapter);
        binding.recyclerToDo.setLayoutManager(new LinearLayoutManager(this));
    }
    void goToDetailActivity(String toDoId){
        Intent intent = new Intent(MyToDoActivity.this, DetailToDoActivity.class);
        intent.putExtra(DetailToDoActivity.KEY_USER_ID, user_id);
        intent.putExtra(DetailToDoActivity.KEY_TODO_ID, toDoId);
        intent.putExtra(DetailToDoActivity.KEY_REQUEST_RESPONSE_ID, new DetailToDoActivity.RequestResponse(REQUEST_REFRESH_MY_TODO, RESULT_REFRESH_MY_TODO));
        startActivityForResult(intent, REQUEST_REFRESH_MY_TODO);
    }
    void goToCreateActivity(){
        Intent intent = new Intent(MyToDoActivity.this, CreateToDoActivity.class);
        intent.putExtra(CreateToDoActivity.KEY_USER_ID_CREATE_ACTIVITY, user_id);
        startActivityForResult(intent, REQUEST_REFRESH_MY_TODO);
    }

    @Override
    void backClickListener() {
        finish();
    }

    class FABCreateToDoListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            goToCreateActivity();
        }
    }
    class SnackbarDeleteListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            AlertDialog alertDialog = new AlertDialog.Builder(MyToDoActivity.this)
                                        .setTitle("Delete Todo")
                                        .setMessage("Are you sure want to delete all this todos?")
                                        .setNegativeButton("No",(dialogInterface, i) -> {
                                            snackbar.show();
                                            dialogInterface.dismiss();
                                        })
                                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                                            myToDoViewModel.deleteToDo(user_id);
                                            dialogInterface.dismiss();
                                        })
                                        .create();
            alertDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_REFRESH_MY_TODO &&
                resultCode == RESULT_REFRESH_MY_TODO) {
            myToDoViewModel.loadToDoLiveData(user_id);
            myToDoViewModel.forceCleanAllDeletedToDo();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}