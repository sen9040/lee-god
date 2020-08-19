package com.yijun.contest.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherDaily {

    int dailyDt;
    double dailyMin;
    double dailyMax;
    String dailyDescription;
    String dailyIcon;
    double dailyPop;

    public WeatherDaily(){

    }

    public WeatherDaily(int dailyDt, double dailyMin, double dailyMax, String dailyDescription, String dailyIcon, double dailyPop) {
        this.dailyDt = dailyDt;
        this.dailyMin = dailyMin;
        this.dailyMax = dailyMax;
        this.dailyDescription = dailyDescription;
        this.dailyIcon = dailyIcon;
        this.dailyPop = dailyPop;
    }

    public int getDailyDt() {
        return dailyDt;
    }

    public void setDailyDt(int dailyDt) {
        this.dailyDt = dailyDt;
    }

    public double getDailyMin() {
        return dailyMin;
    }

    public void setDailyMin(double dailyMin) {
        this.dailyMin = dailyMin;
    }

    public double getDailyMax() {
        return dailyMax;
    }

    public void setDailyMax(double dailyMax) {
        this.dailyMax = dailyMax;
    }

    public String getDailyDescription() {
        return dailyDescription;
    }

    public void setDailyDescription(String dailyDescription) {
        this.dailyDescription = dailyDescription;
    }

    public String getDailyIcon() {
        return dailyIcon;
    }

    public void setDailyIcon(String dailyIcon) {
        this.dailyIcon = dailyIcon;
    }

    public double getDailyPop() {
        return dailyPop;
    }

    public void setDailyPop(double dailyPop) {
        this.dailyPop = dailyPop;
    }
}
