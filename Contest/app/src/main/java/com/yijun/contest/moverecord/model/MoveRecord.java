package com.yijun.contest.moverecord.model;

public class MoveRecord {
    private int id;
    private String title;
    private String address;
    private String url;
    private String date;
    public MoveRecord(){

    }

    public MoveRecord(int id, String title, String address, String url, String date) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.url = url;
        this.date = date;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
