package com.ursys.myClass;

import com.alibaba.fastjson.annotation.JSONField;

/*
	json∏Ò Ω: [
		{
			"year": 2017,
			"score": 412,
			"province" : 12,
			"type": 1
		},
		{
			"year": 2016,
			"score": 412,
			"province" : 11,
			"type": 1
		}
	]

 */
public class ScorelineYearScore {
	@JSONField(name = "year")
	private int year;

	@JSONField(name = "score")
	private int score;

	@JSONField(name = "province")
	private int province;

	@JSONField(name = "type")
	private int type;

	public ScorelineYearScore(int year, int score, int province, int type) {
		super();
		this.year = year;
		this.score = score;
		this.province = province;
		this.type = type;
	}

	public ScorelineYearScore(){
		this.year = 0;
		this.score = 0;
		this.province = 0;
		this.type = 0;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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



}
