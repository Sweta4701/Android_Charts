package com.example.androidchart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidchart.Interface.APIService;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnBarChart;
    private Button btnPieChart;
    private Button btnRadarChart;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBarChart = findViewById(R.id.btnBarChart);
        btnPieChart = findViewById(R.id.btnPieChart);
        btnRadarChart = findViewById(R.id.btnRadarChart);

        apiService = Retrofit.getInstance().getApiService();

        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchChartData("bar");
            }
        });

        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchChartData("pie");
            }
        });

        btnRadarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchChartData("radar");
            }
        });
    }
    private void fetchChartData(String chartType) {
        Call<ChartDataResponse> call = apiService.getChartData(chartType);
        call.enqueue(new Callback<ChartDataResponse>() {
            @Override
            public void onResponse(Call<ChartDataResponse> call, Response<ChartDataResponse> response) {
//                if (response.isSuccessful()) {
//                    ChartDataResponse chartDataResponse = response.body();
////                    Log.d(TAG, "Response Body: " + new Gson().toJson(chartDataResponse));
//                    List<ChartData> chartData = chartDataResponse.getData();
//                    for (ChartData data: chartData) {
//                        Log.i(TAG, "onResponse Chart Year: " + data.getYear() + " Visitor: " + data.getVisitors());
//                    }
//                    startChartActivity(chartType, chartData);
//                }
                if (response.isSuccessful()) {
                    ChartDataResponse chartDataResponse = response.body();
                    if (chartDataResponse != null) {
                        List<ChartData> chartData = chartDataResponse.getData();
                        for (ChartData data : chartData) {
                            Log.i(TAG, "onResponse Chart Year: " + data.getYear() + " Visitor: " + data.getVisitors());
                        }
                        startChartActivity(chartType, chartData);
                    } else {
                        Log.e(TAG, "onResponse: ChartDataResponse is null");
                        Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.e(TAG, "onResponse: failed");
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChartDataResponse> call, Throwable t) {
                Log.e(TAG, "Failed to fetch data", t);
                Toast.makeText(MainActivity.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startChartActivity(String chartType, List<ChartData> chartData) {
        Intent intent;
        switch (chartType) {
            case "bar":
                intent = new Intent(MainActivity.this, BarChartActivity.class);
                break;
            case "pie":
                intent = new Intent(MainActivity.this, PieChartActivity.class);
                break;
            case "radar":
                intent = new Intent(MainActivity.this, RadarChartActivity.class);
                break;
            default:
                return;
        }
        intent.putExtra("chartData", (Serializable) chartData);
        startActivity(intent);
    }
}