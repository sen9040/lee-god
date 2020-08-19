package com.yijun.contest.model;

public class AirInfo {

    private String date ;
    private String msrste_nm;
    private double no2;
    private double o3 ;
    private double co ;
    private double so2 ;
    private double pm10 ;
    private double pm25 ;

    public AirInfo(){

    }

    public AirInfo(String date, String msrste_nm, double no2, double o3, double co, double so2, double pm10, double pm25) {
        this.date = date;
        this.msrste_nm = msrste_nm;
        this.no2 = no2;
        this.o3 = o3;
        this.co = co;
        this.so2 = so2;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsrste_nm() {
        return msrste_nm;
    }

    public void setMsrste_nm(String msrste_nm) {
        this.msrste_nm = msrste_nm;
    }

    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }
}
