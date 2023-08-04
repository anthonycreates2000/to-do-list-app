package com.example.keepoapp.network.api;

import com.example.keepoapp.data.requestbody.SearchToDoBody;
import com.example.keepoapp.data.response.SearchToDoData;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchToDoAPIManager extends APIManager implements Callback<SearchToDoData> {
    private SearchToDoBody body;
    public SearchToDoAPIManager(String searchQuery, HashMap<String, Integer> filters){
        this.body = new SearchToDoBody(searchQuery, filters.get("filterUser"), filters.get("filterTodo"));
    }
    @Override
    public void startRequest() {
        super.startRequest();
        Call<SearchToDoData> data = requestData.searchToDoData(body);
        data.enqueue(this);
    }

    @Override
    public void onResponse(Call<SearchToDoData> call, Response<SearchToDoData> response) {
        iOnSuccess.onSuccess(call, response);
    }

    @Override
    public void onFailure(Call<SearchToDoData> call, Throwable t) {
        iOnFailed.onFailed(call, t);
    }
}
