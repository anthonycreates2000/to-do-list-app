package com.example.keepoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.keepoapp.R;
import com.example.keepoapp.data.User;
import com.example.keepoapp.data.requestbody.LoginBody;
import com.example.keepoapp.data.response.LoginData;
import com.example.keepoapp.network.api.LoginAPIManager;
import com.example.keepoapp.validation.ValidationController;
import com.example.keepoapp.validation.container.TextValidationContainer;
import com.example.keepoapp.validation.container.ValidationContainer;
import com.example.keepoapp.validation.data.NotEmptyTextValidation;
import com.example.keepoapp.validation.data.TextValidationData;

public class LoginViewModel extends ConnectivityAndroidViewModel{
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginData> loginData = new MutableLiveData<>();
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
    public void verifyUser(String username, String password){
        LoginAPIManager apiManager = new LoginAPIManager(new LoginBody(username, password));
        apiManager.iBeforeConnect = () -> {
            iBeforeConnect.beforeConnect();
        };
        apiManager.iOnSuccess = (call, response) -> {
            LoginData data = (LoginData) response.body();
            loginData.postValue(data);
        };
        apiManager.iOnFailed = (call, throwable) -> {
            bottomDialogMessageLiveData.postValue(throwable.getMessage());
        };
        apiManager.startRequest();
    }
    public LiveData<LoginData> getLoginLiveData(){
        return loginData;
    }
    public LiveData<User> getUserLiveData(){
        return userLiveData;
    }
    public void saveUserData(String username, String password){
        userLiveData.setValue(new User(username, password));
    }
    public void validateThenVerifyUser(String username, String password){
        NotEmptyTextValidation notEmptyValidation = new NotEmptyTextValidation();
        notEmptyValidation.iErrorMessage = () -> {
            return getApplication().getString(R.string.username_password_notify);
        };
        TextValidationContainer usernameValidationData = new TextValidationContainer(username, notEmptyValidation);
        TextValidationContainer passwordValidationData = new TextValidationContainer(password, notEmptyValidation);

        ValidationController controller = new ValidationController(usernameValidationData, passwordValidationData);
        controller.iOnSuccess = () -> {
            verifyUser(username, password);
        };
        controller.iOnError = (errorMessage) -> {
            errorLiveData.postValue(errorMessage);
        };
        controller.evaluate();
    }
}
