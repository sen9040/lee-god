package com.yijun.contest.model;

import java.io.Serializable;

public class Favorite implements Serializable {
    private int id;
    private String idx;
    private String imgUrl;
    private String title;
    private String address;
    private String price;
    private String time;
    private int isFavorite;
    private String pageUrl;
    private String lat;
    private String lng;
    private String content;
    public Favorite(){

    }

    public Favorite(int id, String idx, String imgUrl, String title, String address, String price, String time, int isFavorite, String pageUrl, String lat, String lng, String content) {
        this.id = id;
        this.idx = idx;
        this.imgUrl = imgUrl;
        this.title = title;
        this.address = address;
        this.price = price;
        this.time = time;
        this.isFavorite = isFavorite;
        this.pageUrl = pageUrl;
        this.lat = lat;
        this.lng = lng;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
