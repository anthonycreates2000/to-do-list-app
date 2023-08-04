package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.response.CommonResponse;
import com.example.keepoapp.data.response.GetUserToDoData;
import com.example.keepoapp.network.api.DeleteToDoAPIManager;
import com.example.keepoapp.network.api.GetUserToDoAPIManager;

import java.util.ArrayList;
import java.util.Iterator;

public class MyToDoViewModel extends UserViewModel {
    private MutableLiveData<ArrayList<String>> willBeDeletedToDoLiveDataList = new MutableLiveData<>();
    private MutableLiveData<CommonResponse> deletedCommonResponseLiveData = new MutableLiveData<>();

    public MyToDoViewModel(@NonNull Application application) {
        super(application);
        if (willBeDeletedToDoLiveDataList.getValue() == null){
            willBeDeletedToDoLiveDataList.setValue(new ArrayList<>());
        }
    }
    public void deleteToDo(String user_id){
        Log.d("Info", willBeDeletedToDoLiveDataList.getValue().get(0));
        DeleteToDoAPIManager apiManager = new DeleteToDoAPIManager(user_id, willBeDeletedToDoLiveDataList.getValue());
        apiManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        apiManager.iOnSuccess = (call, response) -> {
            CommonResponse commonResponse = (CommonResponse) response.body();
            deletedCommonResponseLiveData.postValue(commonResponse);
        };
        apiManager.iOnFailed = (call, throwable) -> {
            bottomDialogMessageLiveData.postValue(throwable.getMessage());
        };
        apiManager.startRequest();
    }
    public void applyDeletedChangeToUserData(){
        Iterator<ToDo> toDoIterator = getToDoLiveDataList().getValue().iterator();
        while (toDoIterator.hasNext()){
            ToDo todo = toDoIterator.next();
            if (willBeDeletedToDoLiveDataList.getValue().contains(todo.getTodo_id())){
                toDoIterator.remove();
            }
        }
        toDoLiveDataList.postValue(getToDoLiveDataList().getValue());
    }
    public LiveData<ArrayList<String>> getWillBeDeletedToDo(){
        return willBeDeletedToDoLiveDataList;
    }
    public void addWillBeDeletedToDo(String toDoId) {
        willBeDeletedToDoLiveDataList.getValue().add(toDoId);
        willBeDeletedToDoLiveDataList.postValue(willBeDeletedToDoLiveDataList.getValue());
    }
    public void forceCleanAllDeletedToDo(){
        willBeDeletedToDoLiveDataList.getValue().clear();
    }
    public void removeWillBeDeletedToDo(String todoId) {
        willBeDeletedToDoLiveDataList.getValue().remove(todoId);
        willBeDeletedToDoLiveDataList.postValue(willBeDeletedToDoLiveDataList.getValue());
    }
    public LiveData<CommonResponse> getDeletedCommonResponse(){
        return deletedCommonResponseLiveData;
    }

}
