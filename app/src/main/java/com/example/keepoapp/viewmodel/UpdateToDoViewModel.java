package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.response.CommonResponse;
import com.example.keepoapp.network.api.UpdateToDoAPIManager;

public class UpdateToDoViewModel extends ChangesToDoViewModel {
    public UpdateToDoViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void applyData(){
        String todo_id = toDoMutableLiveData.getValue().getTodo_id();
        String user_id = userIDLiveData.getValue();
        ToDo todo = toDoMutableLiveData.getValue();
        UpdateToDoAPIManager apiManager = new UpdateToDoAPIManager(user_id, todo_id, todo);
        apiManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        apiManager.iOnSuccess = (call, response) -> {
            Log.d("Information", "Success" + response);
            CommonResponse commonResponse = (CommonResponse) response.body();
            commonResponseLiveData.postValue(commonResponse);
        };
        apiManager.iOnFailed = (call, throwable) -> {
            bottomDialogMessageLiveData.postValue(throwable.getMessage());
            Log.e("Failed", "Success" + throwable.getMessage());
        };
        apiManager.startRequest();
    }


}
