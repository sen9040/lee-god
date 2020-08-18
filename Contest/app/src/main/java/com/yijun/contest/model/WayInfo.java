package com.yijun.contest.model;

import java.io.Serializable;

public class WayInfo implements Serializable {

    private String courseCategory;
    private String courseCategoryNm;
    private String southNorthdiv;
    private String southNorthDivNm;
    private String AreaGu;
    private String LeadTime;
    private String courseLevel;
    private String voteCnt;
    private String RelateSubway;
    private String trafficInfo;
    private String content;
    private String pdfFilePath;
    private String courseName;
    private String regDate;
    private String datailCourse;
    private String cpiIdx;
    private String cpiName;
    private String x;
    private String y;
    private String cpiContent;

    public WayInfo(){

    }

    public WayInfo(String courseCategory, String courseCategoryNm, String southNorthdiv, String southNorthDivNm, String areaGu, String leadTime, String courseLevel, String voteCnt, String relateSubway, String trafficInfo, String content, String pdfFilePath, String courseName, String regDate, String datailCourse, String cpiIdx, String cpiName, String x, String y, String cpiContent) {
        this.courseCategory = courseCategory;
        this.courseCategoryNm = courseCategoryNm;
        this.southNorthdiv = southNorthdiv;
        this.southNorthDivNm = southNorthDivNm;
        AreaGu = areaGu;
        LeadTime = leadTime;
        this.courseLevel = courseLevel;
        this.voteCnt = voteCnt;
        RelateSubway = relateSubway;
        this.trafficInfo = trafficInfo;
        this.content = content;
        this.pdfFilePath = pdfFilePath;
        this.courseName = courseName;
        this.regDate = regDate;
        this.datailCourse = datailCourse;
        this.cpiIdx = cpiIdx;
        this.cpiName = cpiName;
        this.x = x;
        this.y = y;
        this.cpiContent = cpiContent;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getCourseCategoryNm() {
        return courseCategoryNm;
    }

    public void setCourseCategoryNm(String courseCategoryNm) {
        this.courseCategoryNm = courseCategoryNm;
    }

    public String getSouthNorthdiv() {
        return southNorthdiv;
    }

    public void setSouthNorthdiv(String southNorthdiv) {
        this.southNorthdiv = southNorthdiv;
    }

    public String getSouthNorthDivNm() {
        return southNorthDivNm;
    }

    public void setSouthNorthDivNm(String southNorthDivNm) {
        this.southNorthDivNm = southNorthDivNm;
    }

    public String getAreaGu() {
        return AreaGu;
    }

    public void setAreaGu(String areaGu) {
        AreaGu = areaGu;
    }

    public String getLeadTime() {
        return LeadTime;
    }

    public void setLeadTime(String leadTime) {
        LeadTime = leadTime;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(String voteCnt) {
        this.voteCnt = voteCnt;
    }

    public String getRelateSubway() {
        return RelateSubway;
    }

    public void setRelateSubway(String relateSubway) {
        RelateSubway = relateSubway;
    }

    public String getTrafficInfo() {
        return trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
        this.trafficInfo = trafficInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getDatailCourse() {
        return datailCourse;
    }

    public void setDatailCourse(String datailCourse) {
        this.datailCourse = datailCourse;
    }

    public String getCpiIdx() {
        return cpiIdx;
    }

    public void setCpiIdx(String cpiIdx) {
        this.cpiIdx = cpiIdx;
    }

    public String getCpiName() {
        return cpiName;
    }

    public void setCpiName(String cpiName) {
        this.cpiName = cpiName;
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

    public String getCpiContent() {
        return cpiContent;
    }

    public void setCpiContent(String cpiContent) {
        this.cpiContent = cpiContent;
    }
}
