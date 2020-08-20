package com.yijun.contest.model;

import java.io.Serializable;

public class NatureInfo implements Serializable {

    private String pIdx;
    private String pPark;
    private String pListContent;
    private String area;
    private String openDt;
    private String mainEquip;
    private String mainPlants;
    private String guidance;
    private String visitRoad;
    private String useRefer;
    private String pImg;
    private String pZone;
    private String pAddr;
    private String pName;
    private String pAdmintel;
    private String x;
    private String y;
    private String templateUrl;
    private int isFavorite;

    public NatureInfo(){

    }

    public NatureInfo(String pIdx, String pPark, String pListContent, String area, String openDt, String mainEquip, String mainPlants, String guidance, String visitRoad, String useRefer, String pImg, String pZone, String pAddr, String pName, String pAdmintel, String x, String y, String templateUrl, int isFavorite) {
        this.pIdx = pIdx;
        this.pPark = pPark;
        this.pListContent = pListContent;
        this.area = area;
        this.openDt = openDt;
        this.mainEquip = mainEquip;
        this.mainPlants = mainPlants;
        this.guidance = guidance;
        this.visitRoad = visitRoad;
        this.useRefer = useRefer;
        this.pImg = pImg;
        this.pZone = pZone;
        this.pAddr = pAddr;
        this.pName = pName;
        this.pAdmintel = pAdmintel;
        this.x = x;
        this.y = y;
        this.templateUrl = templateUrl;
        this.isFavorite = isFavorite;
    }

    public String getpIdx() {
        return pIdx;
    }

    public void setpIdx(String pIdx) {
        this.pIdx = pIdx;
    }

    public String getpPark() {
        return pPark;
    }

    public void setpPark(String pPark) {
        this.pPark = pPark;
    }

    public String getpListContent() {
        return pListContent;
    }

    public void setpListContent(String pListContent) {
        this.pListContent = pListContent;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getMainEquip() {
        return mainEquip;
    }

    public void setMainEquip(String mainEquip) {
        this.mainEquip = mainEquip;
    }

    public String getMainPlants() {
        return mainPlants;
    }

    public void setMainPlants(String mainPlants) {
        this.mainPlants = mainPlants;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public String getVisitRoad() {
        return visitRoad;
    }

    public void setVisitRoad(String visitRoad) {
        this.visitRoad = visitRoad;
    }

    public String getUseRefer() {
        return useRefer;
    }

    public void setUseRefer(String useRefer) {
        this.useRefer = useRefer;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getpZone() {
        return pZone;
    }

    public void setpZone(String pZone) {
        this.pZone = pZone;
    }

    public String getpAddr() {
        return pAddr;
    }

    public void setpAddr(String pAddr) {
        this.pAddr = pAddr;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpAdmintel() {
        return pAdmintel;
    }

    public void setpAdmintel(String pAdmintel) {
        this.pAdmintel = pAdmintel;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
