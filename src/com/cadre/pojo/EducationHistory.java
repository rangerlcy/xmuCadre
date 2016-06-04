package com.cadre.pojo;

import java.util.Date;

/**
 * EducationHistory entity. @author MyEclipse Persistence Tools
 */

public class EducationHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Date beginDay;
	private Date endDay;
	private String school;
	private String learningForm;
	private String learningPhase;
	private String learningState;
	private String degree;
	private String degreeType;
	private String remark;
	private String checkCase;
	private Integer delFlag;
	private String a0100;

	// Constructors

	/** default constructor */
	public EducationHistory() {
	}

	/** minimal constructor */
	public EducationHistory(User user) {
		this.user = user;
	}

	/** full constructor */
	public EducationHistory(User user, Date beginDay, Date endDay,
			String school, String learningForm, String learningPhase,
			String learningState, String degree, String degreeType,
			String remark, String checkCase, Integer delFlag, String a0100) {
		this.user = user;
		this.beginDay = beginDay;
		this.endDay = endDay;
		this.school = school;
		this.learningForm = learningForm;
		this.learningPhase = learningPhase;
		this.learningState = learningState;
		this.degree = degree;
		this.degreeType = degreeType;
		this.remark = remark;
		this.checkCase = checkCase;
		this.delFlag = delFlag;
		this.a0100 = a0100;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBeginDay() {
		return this.beginDay;
	}

	public void setBeginDay(Date beginDay) {
		this.beginDay = beginDay;
	}

	public Date getEndDay() {
		return this.endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getLearningForm() {
		return this.learningForm;
	}

	public void setLearningForm(String learningForm) {
		this.learningForm = learningForm;
	}

	public String getLearningPhase() {
		return this.learningPhase;
	}

	public void setLearningPhase(String learningPhase) {
		this.learningPhase = learningPhase;
	}

	public String getLearningState() {
		return this.learningState;
	}

	public void setLearningState(String learningState) {
		this.learningState = learningState;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDegreeType() {
		return this.degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckCase() {
		return this.checkCase;
	}

	public void setCheckCase(String checkCase) {
		this.checkCase = checkCase;
	}

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getA0100() {
		return this.a0100;
	}

	public void setA0100(String a0100) {
		this.a0100 = a0100;
	}

}