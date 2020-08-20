package com.yijun.contest.model;

import java.io.Serializable;

public class Parking implements Serializable {
    private String pay_yn;
    private String pay_nm;
    private String parking_name;
    private String addr;
    private double lat;
    private double lng;

    public Parking(String pay_yn, String pay_nm, String parking_name, double lat, double lng) {
        this.pay_yn = pay_yn;
        this.pay_nm = pay_nm;
        this.parking_name = parking_name;
        this.lat = lat;
        this.lng = lng;
    }

    public Parking(String pay_yn, String pay_nm, String parking_name, String addr, double lat, double lng) {
        this.pay_yn = pay_yn;
        this.pay_nm = pay_nm;
        this.parking_name = parking_name;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPay_yn() {
        return pay_yn;
    }

    public void setPay_yn(String pay_yn) {
        this.pay_yn = pay_yn;
    }

    public String getPay_nm() {
        return pay_nm;
    }

    public void setPay_nm(String pay_nm) {
        this.pay_nm = pay_nm;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Parking() {
    }
}
