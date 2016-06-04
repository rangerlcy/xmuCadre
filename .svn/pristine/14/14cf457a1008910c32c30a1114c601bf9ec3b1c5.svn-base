package com.cadre.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Secondment entity. @author MyEclipse Persistence Tools
 */

public class Secondment implements java.io.Serializable {

	// Fields

	private Integer id;
	private Paper paper;
	private String temporaryProjectName;
	private Date temporaryBeginDay;
	private Date temporaryEndDate;
	private String temporaryRequirement;
	private String remark;
	private Integer delFlag;
	private Set secondmentUsers = new HashSet(0);

	// Constructors

	/** default constructor */
	public Secondment() {
	}

	/** minimal constructor */
	public Secondment(Paper paper) {
		this.paper = paper;
	}

	/** full constructor */
	public Secondment(Paper paper, String temporaryProjectName,
			Date temporaryBeginDay, Date temporaryEndDate,
			String temporaryRequirement, String remark, Integer delFlag,
			Set secondmentUsers) {
		this.paper = paper;
		this.temporaryProjectName = temporaryProjectName;
		this.temporaryBeginDay = temporaryBeginDay;
		this.temporaryEndDate = temporaryEndDate;
		this.temporaryRequirement = temporaryRequirement;
		this.remark = remark;
		this.delFlag = delFlag;
		this.secondmentUsers = secondmentUsers;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public String getTemporaryProjectName() {
		return this.temporaryProjectName;
	}

	public void setTemporaryProjectName(String temporaryProjectName) {
		this.temporaryProjectName = temporaryProjectName;
	}

	public Date getTemporaryBeginDay() {
		return this.temporaryBeginDay;
	}

	public void setTemporaryBeginDay(Date temporaryBeginDay) {
		this.temporaryBeginDay = temporaryBeginDay;
	}

	public Date getTemporaryEndDate() {
		return this.temporaryEndDate;
	}

	public void setTemporaryEndDate(Date temporaryEndDate) {
		this.temporaryEndDate = temporaryEndDate;
	}

	public String getTemporaryRequirement() {
		return this.temporaryRequirement;
	}

	public void setTemporaryRequirement(String temporaryRequirement) {
		this.temporaryRequirement = temporaryRequirement;
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

	public Set getSecondmentUsers() {
		return this.secondmentUsers;
	}

	public void setSecondmentUsers(Set secondmentUsers) {
		this.secondmentUsers = secondmentUsers;
	}

}