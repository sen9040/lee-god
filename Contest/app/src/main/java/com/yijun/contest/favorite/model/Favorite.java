package com.yijun.contest.favorite.model;

public class Favorite {
    private int id;
    private String title;
    private String address;
    private String priceTime;
    private String img_url;

    public Favorite() {

    }

    public Favorite(int id, String title, String address, String priceTime, String img_url) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.priceTime = priceTime;
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(String priceTime) {
        this.priceTime = priceTime;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
