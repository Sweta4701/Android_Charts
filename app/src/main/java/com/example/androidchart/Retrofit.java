package com.example.androidchart;

import com.example.androidchart.Interface.APIService;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static Retrofit instance = null;
    private APIService apiService;

    public Retrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
//                .baseUrl("http://192.168.0.189:3001")
                .baseUrl("http://192.168.0.104:3001")
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        this.apiService = retrofit.create(APIService.class);
    }

    public static synchronized Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit();
        }
        return instance;
    }

    public APIService getApiService() {
        return apiService;
    }
}
