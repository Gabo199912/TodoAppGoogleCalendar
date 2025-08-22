package com.example.todoappgooglecalendar;

import com.example.todoappgooglecalendar.ModelosApiTodoist.ApiResponse;
import com.example.todoappgooglecalendar.ModelosApiTodoist.Tareas;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TodoistApi {

    @GET("tasks")
    Call<ApiResponse> obtenerTareas(@Header("Authorization") String token);

    @POST("tasks")
    Call<ApiResponse> crearTarea(@Body Tareas nuevaTarea, @Header("Authorization") String token);

    @DELETE("Ttask/{task_id}")
    Call<ApiResponse> eliminarTarea(@Path ("id") String id, @Header("Authorization") String token);

}
