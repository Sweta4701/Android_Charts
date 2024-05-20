package com.example.androidchart.Interface;

import com.example.androidchart.ChartData;
import com.example.androidchart.ChartDataResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("chart")
    Call<ChartDataResponse> getChartData(@Body String chartType);
}
