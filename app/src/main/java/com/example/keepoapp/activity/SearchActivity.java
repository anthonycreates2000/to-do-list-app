package com.example.keepoapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.example.keepoapp.databinding.SearchLayoutBinding;

public abstract class SearchActivity extends BackableConnectivityActivity {
    protected SearchLayoutBinding searchBinding;

    protected abstract void searchClickListener();

    @Override
    void backClickListener() {
        finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    protected void closeSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    protected void setListeners(){
        searchBinding.imgBtnBack.setOnClickListener(new BackClickListener());
        searchBinding.imgBtnSearch.setOnClickListener(new SearchListener());
    }
    class SearchListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            searchClickListener();
        }
    }
}
