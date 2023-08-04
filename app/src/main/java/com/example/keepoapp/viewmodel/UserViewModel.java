package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.response.GetUserToDoData;
import com.example.keepoapp.network.api.GetUserToDoAPIManager;

import java.util.ArrayList;

public class UserViewModel extends ConnectivityAndroidViewModel{
    protected MutableLiveData<ArrayList<ToDo>> toDoLiveDataList = new MutableLiveData<>();
    public UserViewModel(@NonNull Application application) {
        super(application);
    }
    public void loadToDoLiveData(String user_id) {
        GetUserToDoAPIManager apiManager = new GetUserToDoAPIManager(user_id);
        apiManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        apiManager.iOnSuccess = (call, response) -> {
            GetUserToDoData getUserToDoData = (GetUserToDoData) response.body();
            toDoLiveDataList.postValue(getUserToDoData.getData().getListTodo());
        };
        apiManager.iOnFailed = (call, throwable) -> {
            bottomDialogMessageLiveData.postValue(throwable.getMessage());
        };
        apiManager.startRequest();
    }
    public LiveData<ArrayList<ToDo>> getToDoLiveDataList() {
        return toDoLiveDataList;
    }
    public void checkThenReloadData(String user_id){
        if (toDoLiveDataList.getValue() != null){
            return;
        }
        loadToDoLiveData(user_id);
    }
}
