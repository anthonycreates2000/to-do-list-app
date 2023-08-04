package com.example.keepoapp.network.api;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.requestbody.DeleteToDoBody;
import com.example.keepoapp.data.response.CommonResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteToDoAPIManager extends APIManager implements Callback<CommonResponse> {
    private ArrayList<String> toDoIds;
    private String user_id;
    public DeleteToDoAPIManager(String user_id, ArrayList<String> toDoIds){
        this.user_id = user_id;
        this.toDoIds = toDoIds;
    }
    @Override
    public void startRequest() {
        super.startRequest();
        DeleteToDoBody body = new DeleteToDoBody(toDoIds);
        Call<CommonResponse> call = requestData.deleteToDoData(user_id, body);
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