package com.example.keepoapp.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.keepoapp.R;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.databinding.ActivityCreateToDoBinding;
import com.example.keepoapp.viewmodel.CreateToDoViewModel;

public class CreateToDoActivity extends ChangesToDoActivity {
    public static String KEY_USER_ID_CREATE_ACTIVITY = "com.example.keepoapp.KEY_USER_ID_CREATE_TODO";
    private ActivityCreateToDoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_to_do);
        formBinding = binding.formToDo;
        changesToDoViewModel = new ViewModelProvider(this).get(CreateToDoViewModel.class);
        initializeFirstSetup();
        initializeViewModel();
        setIntentData(savedInstanceState);
        setListeners();
    }

    @Override
    void exitActivity() {
        setResult(MyToDoActivity.RESULT_REFRESH_MY_TODO);
        finish();
    }
    @Override
    void setIntentData(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            ((CreateToDoViewModel)changesToDoViewModel).setEmptyToDoData();
            String user_id = getIntent().getStringExtra(KEY_USER_ID_CREATE_ACTIVITY);
            changesToDoViewModel.setUserID(user_id);
            ToDo toDo = new ToDo("", "");
            formBinding.setTodoviewmodel(toDo);
        }
    }

    @Override
    void setListeners() {
        super.setListeners();
        binding.fabApply.setOnClickListener(new ApplyFABListener());
        binding.imgBtnBack.setOnClickListener(new BackClickListener());
    }
}