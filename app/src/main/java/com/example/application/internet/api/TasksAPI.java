package com.example.application.internet.api;

import com.example.application.entity.task.Task;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TasksAPI {

    @GET("tasks/all")
    Observable<List<Task>> getAll();

    @POST("tasks/create")
    Observable<Task> create(@Body Task newTask);

    @DELETE("tasks/one/{id}")
    Observable<ResponseBody> delete(@Path("id") Integer id);
}
