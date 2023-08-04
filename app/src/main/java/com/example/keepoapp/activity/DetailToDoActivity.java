package com.example.keepoapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.keepoapp.R;
import com.example.keepoapp.databinding.ActivityDetailToDoBinding;
import com.example.keepoapp.viewmodel.DetailToDoViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import java.io.Serializable;

public class DetailToDoActivity extends BackableConnectivityActivity {


    public static class RequestResponse implements Serializable{
        private int requestCode;
        private int resultCode;
        public RequestResponse(int requestCode, int resultCode){
            this.requestCode = requestCode;
            this.resultCode = resultCode;
        }
        public int getResultCode() {
            return resultCode;
        }
        public int getRequestCode() {
            return requestCode;
        }
    }
    public static String KEY_USER_ID = "com.example.keepoapp.KEY_USER_ID_DETAIL_ACTIVITY";
    public static String KEY_TODO_ID = "com.example.keepoapp.KEY_TODO_ID_DETAIL_ACTIVITY";
    public static String KEY_REQUEST_RESPONSE_ID = "com.example.keepoapp.KE_REQUEST_RESPONSE_ID";
    public static int REQUEST_REFRESH_CODE = 602;
    public static int RESULT_REFRESH_CODE = 702;
    private DetailToDoViewModel detailToDoViewModel;
    private ActivityDetailToDoBinding binding;
    private RequestResponse requestResponse;
    String user_id;
    String todo_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_to_do);
        requestResponse = (RequestResponse) getIntent().getSerializableExtra(KEY_REQUEST_RESPONSE_ID);
        initializeViewModel();
        setListeners();
    }
    void setListeners(){
        binding.fabEdit.setOnClickListener(new FloatingActionListener());
        binding.imgBtnBack.setOnClickListener(new BackClickListener());
    }
    private void initializeViewModel(){
        detailToDoViewModel = new ViewModelProvider(this).get(DetailToDoViewModel.class);
        detailToDoViewModel.iBeforeConnect = () -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.fabEdit.setVisibility(View.INVISIBLE);
        };
        detailToDoViewModel.getBottomDialogMessageLiveData().observe(this, (message) -> {
            binding.progressBar.setVisibility(View.GONE);
            showErrorBottomSheetFragment(message);
        });
        detailToDoViewModel.getToDoLiveData().observe(this, (toDo) -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.setTodoviewmodel(toDo);
            if (detailToDoViewModel.isTheOwner(user_id)){
                binding.fabEdit.setVisibility(View.VISIBLE);
            }
            else{
                binding.fabEdit.setVisibility(View.GONE);
            }
        });
        user_id = getIntent().getStringExtra(KEY_USER_ID);
        todo_id = getIntent().getStringExtra(KEY_TODO_ID);
        detailToDoViewModel.checkIfToDoExistThenRequest(user_id, todo_id);
    }
    void goToNextActivity(){
        Intent intent = new Intent(DetailToDoActivity.this, EditToDoActivity.class);
        intent.putExtra(EditToDoActivity.KEY_USER_ID_EDIT_ACTIVITY, user_id);
        intent.putExtra(EditToDoActivity.KEY_TO_DO_EDIT_ACTIVITY, detailToDoViewModel.getToDoLiveData().getValue());
        startActivityForResult(intent, REQUEST_REFRESH_CODE);
    }
    @Override
    void backClickListener() {
        if (requestResponse != null){
            setResult(requestResponse.resultCode);
        }
        finish();
    }
    class FloatingActionListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            goToNextActivity();
        }
    }

    @Override
    public void onBackPressed() {
        backClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_REFRESH_CODE && resultCode == RESULT_REFRESH_CODE){
            detailToDoViewModel.requestToDoLiveData(user_id, todo_id);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}