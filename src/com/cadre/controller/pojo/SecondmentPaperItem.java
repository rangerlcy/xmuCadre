package com.cadre.controller.pojo;

import java.util.Date;

public class SecondmentPaperItem {
	private Integer index;
	private String temporaryProjectName;
	private Date temporaryBeginDay;
	private Date temporaryEndDate;
	private String temporaryRequirement;
	private String remark;
	public String getTemporaryProjectName() {
		return temporaryProjectName;
	}
	public void setTemporaryProjectName(String temporaryProjectName) {
		this.temporaryProjectName = temporaryProjectName;
	}
	public Date getTemporaryBeginDay() {
		return temporaryBeginDay;
	}
	public void setTemporaryBeginDay(Date temporaryBeginDay) {
		this.temporaryBeginDay = temporaryBeginDay;
	}
	public Date getTemporaryEndDate() {
		return temporaryEndDate;
	}
	public void setTemporaryEndDate(Date temporaryEndDate) {
		this.temporaryEndDate = temporaryEndDate;
	}
	public String getTemporaryRequirement() {
		return temporaryRequirement;
	}
	public void setTemporaryRequirement(String temporaryRequirement) {
		this.temporaryRequirement = temporaryRequirement;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
