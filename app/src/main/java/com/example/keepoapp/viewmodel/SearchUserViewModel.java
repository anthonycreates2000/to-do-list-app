package com.example.keepoapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.keepoapp.R;
import com.example.keepoapp.data.User;
import com.example.keepoapp.data.requestbody.SearchUserBody;
import com.example.keepoapp.data.response.SearchUserData;
import com.example.keepoapp.network.api.SearchUserAPIManager;
import com.example.keepoapp.preferences.SharedPreferenceManager;
import com.example.keepoapp.validation.ValidationController;
import com.example.keepoapp.validation.container.TextValidationContainer;
import com.example.keepoapp.validation.data.NotEmptyTextValidation;

public class SearchUserViewModel extends ConnectivityAndroidViewModel{
    private MutableLiveData<SearchUserData> searchUserLiveData = new MutableLiveData<>();
    private MutableLiveData<String> searchQueryLiveData = new MutableLiveData<>();
    private String user_id;
    public SearchUserViewModel(@NonNull Application application) {
        super(application);
        getUserId();
    }
    public void loadUserData(){
        SearchUserBody body = new SearchUserBody(user_id, searchQueryLiveData.getValue());
        SearchUserAPIManager apiManager = new SearchUserAPIManager(body);
        apiManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        apiManager.iOnSuccess = (call,response) -> {
            SearchUserData data = (SearchUserData) response.body();
            searchUserLiveData.postValue(data);
        };
        apiManager.iOnFailed = (call, throwable) -> {
            bottomDialogMessageLiveData.postValue(throwable.getMessage());
        };
        apiManager.startRequest();
    }
    public String getUserId(){
        SharedPreferenceManager manager = new SharedPreferenceManager(getApplication());
        user_id = manager.getUserData().getUser_id();
        return user_id;
    }
    public LiveData<SearchUserData> getSearchUserLiveData(){
        return searchUserLiveData;
    }
    public void validateThenSearch(String searchQuery){
        NotEmptyTextValidation textValidation = new NotEmptyTextValidation();
        textValidation.iErrorMessage = () -> {
            return getApplication().getString(R.string.field_text_not_empty);
        };
        TextValidationContainer container = new TextValidationContainer(searchQuery, textValidation);
        ValidationController controller = new ValidationController(container);
        controller.iOnSuccess = () -> {
            searchQueryLiveData.setValue(searchQuery);
            loadUserData();
        };
        controller.iOnError = (error) -> {
            errorLiveData.postValue(error);
        };
        controller.evaluate();
    }
}
