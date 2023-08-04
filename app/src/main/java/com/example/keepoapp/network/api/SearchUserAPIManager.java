package com.example.keepoapp.network.api;

import com.example.keepoapp.data.requestbody.SearchUserBody;
import com.example.keepoapp.data.response.SearchUserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserAPIManager extends APIManager implements Callback<SearchUserData>{
    private SearchUserBody body;

    public SearchUserAPIManager(SearchUserBody body) {
        this.body = body;
    }

    @Override
    public void startRequest() {
        super.startRequest();
        Call<SearchUserData> dataCall = requestData.searchUserData(this.body);
        dataCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<SearchUserData> call, Response<SearchUserData> response) {
        iOnSuccess.onSuccess(call, response);
    }

    @Override
    public void onFailure(Call<SearchUserData> call, Throwable t) {
        iOnFailed.onFailed(call, t);
    }
}
