package com.example.laboratorio4.service;

import com.example.laboratorio4.entity.Ciudad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CiudadService {
    @GET("/geo/1.0/direct") Call<List<Ciudad>> getCiudadDetalle(@Query("q") String cityName, @Query("limit") int limit, @Query("appid") String apiKey);
}
