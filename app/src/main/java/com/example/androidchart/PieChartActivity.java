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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pie_chart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<ChartData> chartDataList = (List<ChartData>) getIntent().getSerializableExtra("chartData");
        if (chartDataList != null && !chartDataList.isEmpty()) {
            displayPieChart(chartDataList);
        } else {
            Toast.makeText(this, "No data available for pie chart", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayPieChart(List<ChartData> chartDataList) {
        PieChart pieChart = findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();
        for (ChartData data : chartDataList) {
            entries.add(new PieEntry(data.getVisitors(), String.valueOf(data.getYear())));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Visitors per year");
        dataSet.setColors(Color.rgb(255, 102, 0), Color.rgb(0, 204, 102), Color.rgb(51, 153, 255),
                Color.rgb(255, 153, 51), Color.rgb(255, 0, 102), Color.rgb(153, 51, 255),
                Color.rgb(102, 255, 153));
        dataSet.setValueTextSize(16f);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("PIE CHART");
        pieChart.animateY(1000);
        pieChart.animate();
        pieChart.invalidate(); // Refresh chart
    }
}