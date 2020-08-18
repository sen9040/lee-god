package com.yijun.contest.model;

public class Favorite {
    private String imgUrl;
    private String title;
    private String address;
    private String price;
    private String time;

    public Favorite(){

    }

    public Favorite(String imgUrl, String title, String address, String price, String time) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.address = address;
        this.price = price;
        this.time = time;
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
}
