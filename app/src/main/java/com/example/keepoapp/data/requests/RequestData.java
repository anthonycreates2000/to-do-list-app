package com.example.keepoapp.data.requests;

import com.example.keepoapp.data.ToDo;
import com.example.keepoapp.data.requestbody.DeleteToDoBody;
import com.example.keepoapp.data.requestbody.LoginBody;
import com.example.keepoapp.data.requestbody.SearchToDoBody;
import com.example.keepoapp.data.requestbody.SearchUserBody;
import com.example.keepoapp.data.response.CommonResponse;
import com.example.keepoapp.data.response.GetDetailToDoData;
import com.example.keepoapp.data.response.GetUserToDoData;
import com.example.keepoapp.data.response.LoginData;
import com.example.keepoapp.data.response.SearchToDoData;
import com.example.keepoapp.data.response.SearchUserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RequestData {
    @POST("login")
    Call<LoginData> login(@Body LoginBody loginBody);

    @GET("user/{user_id}/todo")
    Call<GetUserToDoData> getUserToDoData(@Path("user_id") String user_id);

    @GET("user/{user_id}/todo/{todo_id}")
    Call<GetDetailToDoData> getDetailToDoData(@Path("user_id") String user_id, @Path(("todo_id")) String todo_id);

    @POST("user/{user_id}/todo")
    Call<CommonResponse> createToDoData(@Path("user_id") String user_id, @Body ToDo toDoBody);

    @PUT("user/{user_id}/todo/{todo_id}")
    Call<CommonResponse> updateToDoData(@Path("user_id") String user_id, @Path("todo_id") String todo_id, @Body ToDo todo);

    @POST("user/{userId}/deleteTodo")
    Call<CommonResponse> deleteToDoData(@Path("userId") String userId, @Body DeleteToDoBody deleteToDoBody);

    @POST("searchTodos")
    Call<SearchToDoData> searchToDoData(@Body SearchToDoBody searchToDoBody);

    @POST("searchUser")
    Call<SearchUserData> searchUserData(@Body SearchUserBody searchUserBody);
}