package com.example.keepoapp.network.api;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.response.CommonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateToDoAPIManager extends APIManager implements Callback<CommonResponse> {
    private String user_id;
    private String todo_id;
    private ToDo todo;
    public UpdateToDoAPIManager(String user_id, String todo_id, ToDo todo){
        this.user_id = user_id;
        this.todo_id = todo_id;
        this.todo = todo;
    }
    @Override
    public void startRequest() {
        super.startRequest();
        Call<CommonResponse> call = requestData.updateToDoData(user_id, todo_id, todo);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
        iOnSuccess.onSuccess(call, response);
    }

    @Override
    public void onFailure(Call<CommonResponse> call, Throwable t) {
        iOnFailed.onFailed(call, t);
    }
}
