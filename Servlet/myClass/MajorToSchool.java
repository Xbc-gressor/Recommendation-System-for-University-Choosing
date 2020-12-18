package com.ursys.myClass;

import com.alibaba.fastjson.annotation.JSONField;

public class MajorToSchool {
    @JSONField(name = "ranking")
    private int ranking;

    @JSONField(name = "school")
    private String school;

    @JSONField(name = "averageScore")
    private String averageScore;

    @JSONField(name = "maxScore")
    private String maxScore;


    @JSONField(name = "batch")
    private String batch;

    public MajorToSchool(int ranking, String school, String averageScore, String maxScore, String batch) {
        this.ranking = ranking;
        this.school = school;
        this.averageScore = averageScore;
        this.maxScore = maxScore;
        this.batch = batch;
    }

    public MajorToSchool() {
        this.ranking = 0;
        this.school = "";
        this.averageScore = "";
        this.maxScore = "";
        this.batch = "";
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
