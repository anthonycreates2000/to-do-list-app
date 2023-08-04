package com.example.keepoapp.activity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.keepoapp.R;
import com.example.keepoapp.data.User;
import com.example.keepoapp.databinding.ActivityLoginBinding;
import com.example.keepoapp.fragment.BottomSheetFragment;
import com.example.keepoapp.preferences.SharedPreferenceManager;
import com.example.keepoapp.viewmodel.LoginViewModel;

public class LoginActivity extends ConnectivityActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel model;
    private User user = new User("","");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomSheetFragment = new BottomSheetFragment();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        model = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setUserviewmodel(user);
        setListeners();
        initializeViewModel();
    }
    void setListeners(){
        binding.btnLogin.setOnClickListener(new LoginButtonValidation());
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        model.saveUserData(user.getUsername(), user.getPassword());
    }

    void initializeViewModel(){
        model.iBeforeConnect = () -> {
            binding.progressCircular.setVisibility(View.VISIBLE);
        };
        model.getLoginLiveData().observe(this, (data) -> {
            binding.progressCircular.setVisibility(View.GONE);
            if (data.getData() == null){
                showErrorBottomSheetFragment(data.getMessage());
            }
            else{
                SharedPreferenceManager manager = new SharedPreferenceManager(LoginActivity.this);
                manager.saveUserData(data.getData());
                goToHomeActivity();
            }
        });
        model.getBottomDialogMessageLiveData().observe(this, (message) -> {
            binding.progressCircular.setVisibility(View.GONE);
            showErrorBottomSheetFragment(message);
        });
        model.getUserLiveData().observe(this, (data) -> {
            user = data;
            binding.setUserviewmodel(user);
        });
        model.getErrorLiveData().observe(this, (error) -> {
            showErrorBottomSheetFragment(error);
        });
    }
    void goToHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    class LoginButtonValidation implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            validate();
        }
        protected void validate(){
            String username = user.getUsername();
            String password = user.getPassword();
            model.validateThenVerifyUser(username, password);
        }
    }
}