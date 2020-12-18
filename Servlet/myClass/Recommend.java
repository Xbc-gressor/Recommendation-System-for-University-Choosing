package com.ursys.myClass;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/*
    json结构: {
        status: 202,
        province: 12,
        type: 2,
        yourScore: 407,
        scoreList: [
            {
                ranking: 1,
                score: 410,
                num: 2,
                infScore:[
                    { schoolName: "上海交通大学", majorName: "自动化", batch: "第一批"},
                    { schoolName: "上海交通大学", majorName: "自动化", batch: "第一批"}
                ]
            }
        ]


    }
 */
public class Recommend {
    public static class SomeScore{
        public static class infOfScore{
            @JSONField(name = "schoolName")
            private String schoolName;

            @JSONField(name = "majorName")
            private String majorName;

            @JSONField(name = "batch")
            private String batch;

            public infOfScore(String schoolName, String majorName, String batch) {
                this.schoolName = schoolName; this.majorName = majorName; this.batch = batch;
            }
            public infOfScore() {
                this.schoolName = "schoolName"; this.majorName = "majorName"; this.batch = "batch";
            }

            public String getSchoolName() { return schoolName; }
            public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
            public String getMajorName() { return majorName; }
            public void setMajorName(String majorName) { this.majorName = majorName; }
            public String getBatch() { return batch; }
            public void setBatch(String batch) { this.batch = batch; }

        }
        @JSONField(name = "ranking")
        private int ranking;

        @JSONField(name = "score")
        private int score;

        @JSONField(name = "num")
        private int num;

        @JSONField(name = "infList")
        private List<infOfScore> infList;

        public SomeScore(int ranking, int score, int num, List<infOfScore> infList) {
            this.ranking = ranking; this.score = score; this.num = num; this.infList = infList;
        }
        public SomeScore() {
            this.ranking = 0; this.score = 0; this.num = 0; this.infList = null;
        }

        public int getRanking() { return ranking; }
        public void setRanking(int ranking) { this.ranking = ranking; }
        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }
        public int getNum() { return num; }
        public void setNum(int num) { this.num = num; }
        public List<infOfScore> getInfList() { return infList; }
        public void setInfList(List<infOfScore> infList) { this.infList = infList; }
    }

    @JSONField(name = "status")
    private int status;

    @JSONField(name = "scoreList")
    private List<SomeScore> scoreList;

    @JSONField(name = "province")
    private int province;

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "yourScore")
    private int yourScore;

    public Recommend(int status, List<SomeScore> scoreList, int province, int type, int yourScore) {
        this.status = status; this.scoreList = scoreList; this.province = province; this.type = type; this.yourScore = yourScore;
    }
    public Recommend() {
        this.status = 0; this.scoreList = null; this.province = 0; this.type = 0; this.yourScore = 0;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public List<SomeScore> getScoreList() { return scoreList; }
    public void setScoreList(List<SomeScore> scoreList) { this.scoreList = scoreList; }
    public int getProvince() { return province; }
    public void setProvince(int province) { this.province = province; }
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    public int getYourScore() { return yourScore; }
    public void setYourScore(int yourScore) { this.yourScore = yourScore; }
}
