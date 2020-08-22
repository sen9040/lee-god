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

    public Favorite(){

    }

    public Favorite(int id, String idx, String imgUrl, String title, String address, String price, String time, int isFavorite, String pageUrl) {
        this.id = id;
        this.idx = idx;
        this.imgUrl = imgUrl;
        this.title = title;
        this.address = address;
        this.price = price;
        this.time = time;
        this.isFavorite = isFavorite;
        this.pageUrl = pageUrl;
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
