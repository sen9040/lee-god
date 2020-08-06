package com.yijun.contest.Ranking.model;

public class Ranking {
    private String title;
    private String img_url;
    private String address;
    private String priceTime;

    public Ranking(){

    }

    public Ranking(String title, String img_url, String address, String priceTime) {
        this.title = title;
        this.img_url = img_url;
        this.address = address;
        this.priceTime = priceTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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
}
