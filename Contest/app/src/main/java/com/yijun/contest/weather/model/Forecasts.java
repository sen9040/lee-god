package com.yijun.contest.weather.model;

import java.io.Serializable;

public class Forecasts implements Serializable {
    private String day;
    private int date;
    private int low;
    private int high;
    private String text;
    private int code;

    public Forecasts(){

    }

    public Forecasts(String day, int date, int low, int high, String text, int code) {
        this.day = day;
        this.date = date;
        this.low = low;
        this.high = high;
        this.text = text;
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
