package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.response.CommonResponse;
import com.example.keepoapp.network.api.CreateToDoAPIManager;
import com.example.keepoapp.network.api.UpdateToDoAPIManager;

public class CreateToDoViewModel extends ChangesToDoViewModel{
    public CreateToDoViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void applyData() {
        String user_id = userIDLiveData.getValue();
        ToDo todo = toDoMutableLiveData.getValue();
        CreateToDoAPIManager apiManager = new CreateToDoAPIManager(user_id, todo);
        apiManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        apiManager.iOnSuccess = (call, response) -> {
            CommonResponse commonResponse = (CommonResponse) response.body();
            commonResponseLiveData.postValue(commonResponse);
        };
        apiManager.iOnFailed = (call, throwable) -> {
            bottomDialogMessageLiveData.postValue(throwable.getMessage());
        };
        apiManager.startRequest();
    }
    public void setEmptyToDoData(){
        toDoMutableLiveData.setValue(new ToDo("", "", ""));
    }
}
