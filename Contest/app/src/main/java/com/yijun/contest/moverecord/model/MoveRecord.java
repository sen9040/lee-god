package com.yijun.contest.moverecord.model;

public class MoveRecord {
    private int id;
    private String title;
    private String address;
    private String url;

    public MoveRecord(){

    }

    public MoveRecord(int id, String title, String address,String url) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
