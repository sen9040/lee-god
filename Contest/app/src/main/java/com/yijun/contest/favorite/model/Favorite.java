package com.yijun.contest.favorite.model;

public class Favorite {
    private int id;
    private String Title;
    private String address;
    private String img;

    public Favorite() {

    }

    public Favorite(int id, String title, String address, String img) {
        this.id = id;
        Title = title;
        this.address = address;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
