package com.yijun.contest.model;

import java.io.Serializable;

public class SportsInfo implements Serializable {

    private String svcId;
    private String maxClassNm;
    private String minClassNm;
    private String svcStaTnm;
    private String svcNm;
    private String paYaTnm;
    private String placeNm;
    private String useTgtInfo;
    private String svcUrl;
    private double x;
    private double y;
    private String svcOpnBgnDt;
    private String svcOpnEndDt;
    private String rcptBgnDt;
    private String rcptEndDt;
    private String areaNm;
    private String imgUrl;
    private String dtlCont;
    private String telNo;
    private String v_min;
    private String v_max;
    private String revStdDayNm;
    private String revStdDay;
    private double distance;
    private int isFavorite;
    public SportsInfo(){

    }

    public SportsInfo(String svcId, String maxClassNm, String minClassNm, String svcStaTnm, String svcNm, String paYaTnm, String placeNm, String useTgtInfo, String svcUrl, double x, double y, String svcOpnBgnDt, String svcOpnEndDt, String rcptBgnDt, String rcptEndDt, String areaNm, String imgUrl, String dtlCont, String telNo, String v_min, String v_max, String revStdDayNm, String revStdDay) {
        this.svcId = svcId;
        this.maxClassNm = maxClassNm;
        this.minClassNm = minClassNm;
        this.svcStaTnm = svcStaTnm;
        this.svcNm = svcNm;
        this.paYaTnm = paYaTnm;
        this.placeNm = placeNm;
        this.useTgtInfo = useTgtInfo;
        this.svcUrl = svcUrl;
        this.x = x;
        this.y = y;
        this.svcOpnBgnDt = svcOpnBgnDt;
        this.svcOpnEndDt = svcOpnEndDt;
        this.rcptBgnDt = rcptBgnDt;
        this.rcptEndDt = rcptEndDt;
        this.areaNm = areaNm;
        this.imgUrl = imgUrl;
        this.dtlCont = dtlCont;
        this.telNo = telNo;
        this.v_min = v_min;
        this.v_max = v_max;
        this.revStdDayNm = revStdDayNm;
        this.revStdDay = revStdDay;
    }

    public SportsInfo(String svcId, String maxClassNm, String minClassNm, String svcStaTnm, String svcNm, String paYaTnm, String placeNm, String useTgtInfo, String svcUrl, double x, double y, String svcOpnBgnDt, String svcOpnEndDt, String rcptBgnDt, String rcptEndDt, String areaNm, String imgUrl, String dtlCont, String telNo, String v_min, String v_max, String revStdDayNm, String revStdDay, double distance) {
        this.svcId = svcId;
        this.maxClassNm = maxClassNm;
        this.minClassNm = minClassNm;
        this.svcStaTnm = svcStaTnm;
        this.svcNm = svcNm;
        this.paYaTnm = paYaTnm;
        this.placeNm = placeNm;
        this.useTgtInfo = useTgtInfo;
        this.svcUrl = svcUrl;
        this.x = x;
        this.y = y;
        this.svcOpnBgnDt = svcOpnBgnDt;
        this.svcOpnEndDt = svcOpnEndDt;
        this.rcptBgnDt = rcptBgnDt;
        this.rcptEndDt = rcptEndDt;
        this.areaNm = areaNm;
        this.imgUrl = imgUrl;
        this.dtlCont = dtlCont;
        this.telNo = telNo;
        this.v_min = v_min;
        this.v_max = v_max;
        this.revStdDayNm = revStdDayNm;
        this.revStdDay = revStdDay;
        this.distance = distance;
    }

    public SportsInfo(String svcId, String maxClassNm, String minClassNm, String svcStaTnm, String svcNm, String paYaTnm, String placeNm, String useTgtInfo, String svcUrl, double x, double y, String svcOpnBgnDt, String svcOpnEndDt, String rcptBgnDt, String rcptEndDt, String areaNm, String imgUrl, String dtlCont, String telNo, String v_min, String v_max, String revStdDayNm, String revStdDay, double distance, int isFavorite) {
        this.svcId = svcId;
        this.maxClassNm = maxClassNm;
        this.minClassNm = minClassNm;
        this.svcStaTnm = svcStaTnm;
        this.svcNm = svcNm;
        this.paYaTnm = paYaTnm;
        this.placeNm = placeNm;
        this.useTgtInfo = useTgtInfo;
        this.svcUrl = svcUrl;
        this.x = x;
        this.y = y;
        this.svcOpnBgnDt = svcOpnBgnDt;
        this.svcOpnEndDt = svcOpnEndDt;
        this.rcptBgnDt = rcptBgnDt;
        this.rcptEndDt = rcptEndDt;
        this.areaNm = areaNm;
        this.imgUrl = imgUrl;
        this.dtlCont = dtlCont;
        this.telNo = telNo;
        this.v_min = v_min;
        this.v_max = v_max;
        this.revStdDayNm = revStdDayNm;
        this.revStdDay = revStdDay;
        this.distance = distance;
        this.isFavorite = isFavorite;
    }

    public String getSvcId() {
        return svcId;
    }

    public void setSvcId(String svcId) {
        this.svcId = svcId;
    }

    public String getMaxClassNm() {
        return maxClassNm;
    }

    public void setMaxClassNm(String maxClassNm) {
        this.maxClassNm = maxClassNm;
    }

    public String getMinClassNm() {
        return minClassNm;
    }

    public void setMinClassNm(String minClassNm) {
        this.minClassNm = minClassNm;
    }

    public String getSvcStaTnm() {
        return svcStaTnm;
    }

    public void setSvcStaTnm(String svcStaTnm) {
        this.svcStaTnm = svcStaTnm;
    }

    public String getSvcNm() {
        return svcNm;
    }

    public void setSvcNm(String svcNm) {
        this.svcNm = svcNm;
    }

    public String getPaYaTnm() {
        return paYaTnm;
    }

    public void setPaYaTnm(String paYaTnm) {
        this.paYaTnm = paYaTnm;
    }

    public String getPlaceNm() {
        return placeNm;
    }

    public void setPlaceNm(String placeNm) {
        this.placeNm = placeNm;
    }

    public String getUseTgtInfo() {
        return useTgtInfo;
    }

    public void setUseTgtInfo(String useTgtInfo) {
        this.useTgtInfo = useTgtInfo;
    }

    public String getSvcUrl() {
        return svcUrl;
    }

    public void setSvcUrl(String svcUrl) {
        this.svcUrl = svcUrl;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getSvcOpnBgnDt() {
        return svcOpnBgnDt;
    }

    public void setSvcOpnBgnDt(String svcOpnBgnDt) {
        this.svcOpnBgnDt = svcOpnBgnDt;
    }

    public String getSvcOpnEndDt() {
        return svcOpnEndDt;
    }

    public void setSvcOpnEndDt(String svcOpnEndDt) {
        this.svcOpnEndDt = svcOpnEndDt;
    }

    public String getRcptBgnDt() {
        return rcptBgnDt;
    }

    public void setRcptBgnDt(String rcptBgnDt) {
        this.rcptBgnDt = rcptBgnDt;
    }

    public String getRcptEndDt() {
        return rcptEndDt;
    }

    public void setRcptEndDt(String rcptEndDt) {
        this.rcptEndDt = rcptEndDt;
    }

    public String getAreaNm() {
        return areaNm;
    }

    public void setAreaNm(String areaNm) {
        this.areaNm = areaNm;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDtlCont() {
        return dtlCont;
    }

    public void setDtlCont(String dtlCont) {
        this.dtlCont = dtlCont;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getV_min() {
        return v_min;
    }

    public void setV_min(String v_min) {
        this.v_min = v_min;
    }

    public String getV_max() {
        return v_max;
    }

    public void setV_max(String v_max) {
        this.v_max = v_max;
    }

    public String getRevStdDayNm() {
        return revStdDayNm;
    }

    public void setRevStdDayNm(String revStdDayNm) {
        this.revStdDayNm = revStdDayNm;
    }

    public String getRevStdDay() {
        return revStdDay;
    }

    public void setRevStdDay(String revStdDay) {
        this.revStdDay = revStdDay;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
