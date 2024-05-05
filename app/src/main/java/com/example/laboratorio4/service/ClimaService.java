package com.example.laboratorio4.service;

import com.example.laboratorio4.entity.Clima;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClimaService {
    @GET("/data/2.5/weather") Call<Clima> getClimaDetalle(@Query("lat") double latitud, @Query("lon") double longitud, @Query("appid") String apiKey);
}
