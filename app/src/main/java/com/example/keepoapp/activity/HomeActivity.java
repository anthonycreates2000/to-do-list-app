package com.example.keepoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.keepoapp.R;
import com.example.keepoapp.data.User;
import com.example.keepoapp.databinding.ActivityHomeBinding;
import com.example.keepoapp.preferences.SharedPreferenceManager;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding activityHomeBinding;
    private User user;
    private SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        user = sharedPreferenceManager.getUserData();
        activityHomeBinding.tvHomeUserName.setText("Welcome, " + user.getName());
        setListeners();
    }
    private void setListeners(){
        activityHomeBinding.btnMyToDo.setOnClickListener(new NextActivityButton(MyToDoActivity.class));
        activityHomeBinding.btnSearchTodo.setOnClickListener(new NextActivityButton(SearchToDoActivity.class));
        activityHomeBinding.btnSearchUser.setOnClickListener(new NextActivityButton(SearchUserActivity.class));
        activityHomeBinding.btnProfile.setOnClickListener(new NextActivityButton(ProfileUserActivity.class));
    }
    class NextActivityButton implements View.OnClickListener{
        private Class activityName;
        public NextActivityButton(Class activityName){
            this.activityName = activityName;
        }
        @Override
        public void onClick(View view) {
            goToNextActivity(this.activityName);
        }
        private void goToNextActivity(Class activityName){
            Intent intent = new Intent(HomeActivity.this, activityName);
            startActivity(intent);
        }
    }
}