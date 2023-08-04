package com.example.keepoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.keepoapp.R;
import com.example.keepoapp.fragment.BottomSheetFragment;

abstract class ConnectivityActivity extends AppCompatActivity {
    protected BottomSheetFragment bottomSheetFragment;
    protected void showErrorBottomSheetFragment(String message){
        BottomSheetFragment fragment = new BottomSheetFragment();
        fragment.iTitle = (textView) -> {
            textView.setText(R.string.error);
        };
        fragment.iMessage = (textView) -> {
            textView.setText(message);
        };
        fragment.iButtonListener = () -> {
            fragment.dismiss();
        };
        fragment.show(getSupportFragmentManager(), null);
    }
}
