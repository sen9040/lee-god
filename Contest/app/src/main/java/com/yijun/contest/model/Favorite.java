package com.yijun.contest.model;

public class Favorite {
    private String id;
    private String imgUrl;
    private String title;
    private String address;
    private String price;
    private String time;
    private int isFavorite;

    public Favorite(){

    }

    public Favorite(String id, String imgUrl, String title, String address, String price, String time, int isFavorite) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.address = address;
        this.price = price;
        this.time = time;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
