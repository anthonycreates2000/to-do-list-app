package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.response.GetDetailToDoData;
import com.example.keepoapp.network.api.DetailToDoAPIManager;
import com.example.keepoapp.preferences.SharedPreferenceManager;

import java.util.ArrayList;

public class DetailToDoViewModel extends ConnectivityAndroidViewModel {
    private MutableLiveData<ToDo> toDoLiveData = new MutableLiveData<ToDo>();

    public DetailToDoViewModel(@NonNull Application application) {
        super(application);
    }

    public void requestToDoLiveData(String user_id, String todo_id){
        DetailToDoAPIManager apiManager = new DetailToDoAPIManager(user_id, todo_id);
        apiManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        apiManager.iOnSuccess = (call, response) -> {
            GetDetailToDoData detailToDoData = (GetDetailToDoData) response.body();
            ToDo todo = detailToDoData.getToDo();
            toDoLiveData.postValue(todo);
        };
        apiManager.iOnFailed = (call, throwable) -> {
            bottomDialogMessageLiveData.postValue(throwable.getMessage());
        };
        apiManager.startRequest();
    }
    public LiveData<ToDo> getToDoLiveData(){
        return toDoLiveData;
    }
    public boolean isTheOwner(String accessed_user_id){
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(getApplication());
        String current_account_user_id = sharedPreferenceManager.getUserData().getUser_id();
        return current_account_user_id.equals(accessed_user_id);
    }
    public void checkIfToDoExistThenRequest(String user_id, String todo_id){
        if (toDoLiveData.getValue() != null){
            return;
        }
        requestToDoLiveData(user_id, todo_id);
    }
}
