package com.example.keepoapp.activity;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.keepoapp.fragment.BottomSheetFragment;

public abstract class BackableConnectivityActivity extends ConnectivityActivity {
    abstract void backClickListener();

    protected class BackClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            backClickListener();
        }
    }

}
