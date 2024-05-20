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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bar_chart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("chartData")) {
//            List<ChartData> chartData = (List<ChartData>) intent.getSerializableExtra("chartData");
//            displayBarChart(chartData);
//        } else {
//            Toast.makeText(this, "No chart data available", Toast.LENGTH_SHORT).show();
//        }
        List<ChartData> chartDataList = (List<ChartData>) getIntent().getSerializableExtra("chartData");
        if (chartDataList != null && !chartDataList.isEmpty()) {
            displayBarChart(chartDataList);
        } else {
            Toast.makeText(this, "No data available for bar chart", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayBarChart(List<ChartData> chartDataList) {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<>();
//
//        for (int i = 0; i < chartData.size(); i++) {
//            ChartData item = chartData.get(i);
//            entries.add(new BarEntry(i, item.getVisitors()));
//            labels.add(String.valueOf(item.getYear()));
//        }
        BarChart barChart = findViewById(R.id.barChart);

        List<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < chartDataList.size(); i++) {
            ChartData data = chartDataList.get(i);
            entries.add(new BarEntry(i, data.getVisitors()));
            labels.add(String.valueOf(data.getYear()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Visitors per year");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.getDescription().setText("BAR CHART");

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f); // Set distance between each bar
        barChart.getXAxis().setCenterAxisLabels(true); // Center the labels
        barChart.getXAxis().setAxisMinimum(0f); // Start the chart from 0
        barChart.getXAxis().setLabelCount(entries.size()); // Set label count for X-axis
        barChart.getAxisLeft().setLabelCount(5, true); // Set label count for Y-axis
        barChart.getAxisRight().setEnabled(false); // Disable the right Y-axis
        barChart.setFitBars(true);
        barChart.animateY(2000);
        barChart.invalidate(); // Refresh chart

    }
}