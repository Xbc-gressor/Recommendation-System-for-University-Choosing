package com.ursys.myClass;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class SchoolToMajor {
    public static class Major {
        @JSONField(name = "ranking")
        private int ranking;

        @JSONField(name = "majorName")
        private String majorName;

        @JSONField(name = "averageScore")
        private String averageScore;

        @JSONField(name = "maxScore")
        private String maxScore;

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public String getMajorName() {
            return majorName;
        }

        public void setMajorName(String majorName) {
            this.majorName = majorName;
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


        public Major(int ranking, String majorName, String averageScore, String maxScore) {
            this.ranking = ranking;
            this.majorName = majorName;
            this.averageScore = averageScore;
            this.maxScore = maxScore;
        }
        public Major() {
            this.ranking = 0;
            this.majorName = "majorName";
            this.averageScore = "averageScore";
            this.maxScore = "maxScore";
        }
    }

    @JSONField(name = "year")
    private int year;

    @JSONField(name = "Province")
    private int province;

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "majorList")
    private List<Major> majorList;

    public SchoolToMajor(int year, int province, int type, List<Major> majorList) {
        this.year = year;
        this.province = province;
        this.type = type;
        this.majorList = majorList;
    }

    public SchoolToMajor() {
        this.year = 0;
        this.province = 0;
        this.type = 0;
        this.majorList = null;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Major> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<Major> majorList) {
        this.majorList = majorList;
    }
}
