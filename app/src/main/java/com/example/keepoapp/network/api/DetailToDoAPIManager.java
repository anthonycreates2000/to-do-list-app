package com.example.keepoapp.network.api;

import com.example.keepoapp.data.response.GetDetailToDoData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailToDoAPIManager extends APIManager implements Callback<GetDetailToDoData>{
    private String user_id;
    private String todo_id;
    public DetailToDoAPIManager(String user_id, String todo_id){
        this.user_id = user_id;
        this.todo_id = todo_id;
    }
    @Override
    public void startRequest() {
        super.startRequest();
        Call<GetDetailToDoData> call = requestData.getDetailToDoData(user_id, todo_id);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<GetDetailToDoData> call, Response<GetDetailToDoData> response) {
        iOnSuccess.onSuccess(call, response);
    }

    @Override
    public void onFailure(Call<GetDetailToDoData> call, Throwable t) {
        iOnFailed.onFailed(call, t);
    }
}
