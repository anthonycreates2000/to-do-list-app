package com.example.keepoapp.network.api;

import com.example.keepoapp.data.requests.RequestData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class APIManager {
    protected String baseURL = "https://it-division-kepo.herokuapp.com/";
    protected Retrofit retrofit;
    protected RequestData requestData;
    public interface IOnSuccess{
        void onSuccess(Call<?> call, Response<?> response);
    }
    public interface IOnFailed{
        void onFailed(Call<?> call, Throwable t);
    }
    public interface IBeforeConnect{
        void beforeConnect();
    }
    public IBeforeConnect iBeforeConnect;
    public IOnSuccess iOnSuccess;
    public IOnFailed iOnFailed;
    public APIManager(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        requestData = retrofit.create(RequestData.class);
    }
    public void startRequest() {
        iBeforeConnect.beforeConnect();
    }
}
