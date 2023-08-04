package com.example.keepoapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.keepoapp.R;
import com.example.keepoapp.data.User;
import com.example.keepoapp.databinding.ActivityProfileUserBinding;
import com.example.keepoapp.preferences.SharedPreferenceManager;

public class ProfileUserActivity extends BackableConnectivityActivity {
    private ActivityProfileUserBinding binding;
    private User user;
    private SharedPreferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_user);
        manager = new SharedPreferenceManager(this);
        setUserProfile();
        setListeners();
        binding.setUserviewmodel(user);
    }
    private void setUserProfile(){
        User newUser = manager.getUserData();
        user = new User(newUser.getUser_id(), newUser.getName(), newUser.getUsername());
    }
    private void setListeners(){
        binding.tvLogout.setOnClickListener(new LogoutListener());
        binding.imgBtnBack.setOnClickListener(new BackClickListener());
    }
    private void logout(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                                    .setTitle(R.string.logout)
                                    .setMessage(R.string.logout_confirmation)
                                    .setNegativeButton(R.string.no,(dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                    })
                                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                        manager.deleteUserData();
                                        goToSplashScreenActivity();
                                        dialogInterface.dismiss();
                                    })
                                    .create();
        alertDialog.show();
    }
    void goToSplashScreenActivity(){
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    void backClickListener() {
        finish();
    }

    class LogoutListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            logout();
        }
    }
}