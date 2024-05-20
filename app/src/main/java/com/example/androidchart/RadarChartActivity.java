package com.example.androidchart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class RadarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_radar_chart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("chartData")) {
//            List<ChartData> chartData = (List<ChartData>) intent.getSerializableExtra("chartData");
//            displayRadarChart(chartData);
//        } else {
//            Toast.makeText(this, "No chart data available", Toast.LENGTH_SHORT).show();
//        }
        List<ChartData> chartDataList = (List<ChartData>) getIntent().getSerializableExtra("chartData");
        if (chartDataList != null && !chartDataList.isEmpty()) {
            displayRadarChart(chartDataList);
        } else {
            Toast.makeText(this, "No data available for radar chart", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private void displayRadarChart(List<ChartData> chartDataList) {
//        ArrayList<RadarEntry> entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<>();
//
//        for (ChartData item : chartData) {
//            entries.add(new RadarEntry(item.getVisitors()));
//            labels.add(String.valueOf(item.getYear()));
//        }

        RadarChart radarChart = findViewById(R.id.radarChart);

        List<RadarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < chartDataList.size(); i++) {
            ChartData data = chartDataList.get(i);
            entries.add(new RadarEntry(data.getVisitors()));
            labels.add(String.valueOf(data.getYear()));
        }

        RadarDataSet dataSet = new RadarDataSet(entries, "Visitors per year");
        dataSet.setColor(Color.rgb(255, 102, 0)); // Set color for the data
        dataSet.setFillColor(Color.rgb(255, 102, 0));
        dataSet.setDrawFilled(true); // Fill the area under the line
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.RED);
        dataSet.setLineWidth(2f);

        RadarData radarData = new RadarData(dataSet);
        radarChart.setData(radarData);
        radarChart.getDescription().setEnabled(false);
        radarChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(labels));
        radarChart.animateY(1000);
        radarChart.invalidate(); // Refresh chart
    }
}