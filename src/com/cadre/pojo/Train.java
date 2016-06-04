package com.cadre.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Train entity. @author MyEclipse Persistence Tools
 */

public class Train implements java.io.Serializable {

	// Fields

	private Integer id;
	private Date beginDay;
	private Date endDate;
	private String trainingName;
	private Integer trainingPeriod;
	private String organizer;
	private String trainingPlace;
	private String remark;
	private Integer delFlag;
	private Set trainUsers = new HashSet(0);

	// Constructors

	/** default constructor */
	public Train() {
	}

	/** full constructor */
	public Train(Date beginDay, Date endDate, String trainingName,
			Integer trainingPeriod, String organizer, String trainingPlace,
			String remark, Integer delFlag, Set trainUsers) {
		this.beginDay = beginDay;
		this.endDate = endDate;
		this.trainingName = trainingName;
		this.trainingPeriod = trainingPeriod;
		this.organizer = organizer;
		this.trainingPlace = trainingPlace;
		this.remark = remark;
		this.delFlag = delFlag;
		this.trainUsers = trainUsers;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBeginDay() {
		return this.beginDay;
	}

	public void setBeginDay(Date beginDay) {
		this.beginDay = beginDay;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTrainingName() {
		return this.trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public Integer getTrainingPeriod() {
		return this.trainingPeriod;
	}

	public void setTrainingPeriod(Integer trainingPeriod) {
		this.trainingPeriod = trainingPeriod;
	}

	public String getOrganizer() {
		return this.organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public String getTrainingPlace() {
		return this.trainingPlace;
	}

	public void setTrainingPlace(String trainingPlace) {
		this.trainingPlace = trainingPlace;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Set getTrainUsers() {
		return this.trainUsers;
	}

	public void setTrainUsers(Set trainUsers) {
		this.trainUsers = trainUsers;
	}

}