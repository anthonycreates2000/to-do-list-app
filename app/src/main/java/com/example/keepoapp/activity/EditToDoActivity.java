package com.example.keepoapp.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.keepoapp.R;
import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.databinding.ActivityEditToDoBinding;
import com.example.keepoapp.viewmodel.UpdateToDoViewModel;

public class EditToDoActivity extends ChangesToDoActivity {
    public static String KEY_TO_DO_EDIT_ACTIVITY = "com.example.keepoapp.KEY_TO_DO_EDIT_ACTIVITY";
    public static String KEY_USER_ID_EDIT_ACTIVITY = "com.example.keepoapp.KEY_USER_ID_EDIT_ACTIVITY";
    private ActivityEditToDoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_to_do);
        formBinding = binding.formTodo;
        changesToDoViewModel = new ViewModelProvider(this).get(UpdateToDoViewModel.class);
        initializeFirstSetup();
        setIntentData(savedInstanceState);
        initializeViewModel();
        setListeners();
    }

    @Override
    void exitActivity() {
        setResult(DetailToDoActivity.RESULT_REFRESH_CODE);
        finish();
    }

    @Override
    void setIntentData(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            String user_id = getIntent().getStringExtra(KEY_USER_ID_EDIT_ACTIVITY);
            changesToDoViewModel.setUserID(user_id);
            ToDo todo = (ToDo) getIntent().getSerializableExtra(KEY_TO_DO_EDIT_ACTIVITY);
            changesToDoViewModel.saveToDoData(todo);
            formBinding.setTodoviewmodel(todo);
        }
    }

    @Override
    void setListeners() {
        super.setListeners();
        binding.fabApply.setOnClickListener(new ApplyFABListener());
        binding.imgBtnBack.setOnClickListener(new BackClickListener());
    }
}