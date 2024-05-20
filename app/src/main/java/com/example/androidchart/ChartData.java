package com.example.androidchart;

import java.io.Serializable;

public class ChartData implements Serializable {
    private int year;
    private int visitors;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }
}
