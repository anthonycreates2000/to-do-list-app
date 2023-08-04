package com.example.keepoapp.network.api;

import com.example.keepoapp.data.requestbody.LoginBody;
import com.example.keepoapp.data.response.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAPIManager extends APIManager implements Callback<LoginData> {
    LoginBody loginBody;
    public LoginAPIManager(LoginBody loginBody){
        super();
        this.loginBody = loginBody;
    }
    @Override
    public void startRequest() {
        super.startRequest();
        Call<LoginData> loginDataCall = requestData.login(this.loginBody);
        loginDataCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<LoginData> call, Response<LoginData> response) {
        iOnSuccess.onSuccess(call, response);
    }

    @Override
    public void onFailure(Call<LoginData> call, Throwable t) {
        iOnFailed.onFailed(call, t);
    }
}
