package com.example.keepoapp.network.api;

import android.util.Log;

import com.example.keepoapp.data.response.GetUserToDoData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetUserToDoAPIManager extends APIManager implements Callback<GetUserToDoData> {
    String user_id;
    public GetUserToDoAPIManager(String user_id){
        this.user_id = user_id;
    }
    @Override
    public void onResponse(Call<GetUserToDoData> call, Response<GetUserToDoData> response) {
        Log.e("Success", response.message());
        iOnSuccess.onSuccess(call, response);
    }

    @Override
    public void onFailure(Call<GetUserToDoData> call, Throwable t) {
        Log.e("Failed", t.getMessage());
        iOnFailed.onFailed(call, t);
    }

    @Override
    public void startRequest() {
        super.startRequest();
        Call<GetUserToDoData> getUserToDoDataCall = requestData.getUserToDoData(this.user_id);
        getUserToDoDataCall.enqueue(this);
    }
}
