package com.cadre.pojo;

import java.util.Date;

/**
 * OtherQualificationHistory entity. @author MyEclipse Persistence Tools
 */

public class OtherQualificationHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Date qualificationDay;
	private String qualificationName;
	private String grade;
	private String certificationUnit;
	private Date effectiveDay;
	private String type;
	private String remark;
	private String checkCase;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public OtherQualificationHistory() {
	}

	/** minimal constructor */
	public OtherQualificationHistory(User user) {
		this.user = user;
	}

	/** full constructor */
	public OtherQualificationHistory(User user, Date qualificationDay,
			String qualificationName, String grade, String certificationUnit,
			Date effectiveDay, String type, String remark, String checkCase,
			Integer delFlag) {
		this.user = user;
		this.qualificationDay = qualificationDay;
		this.qualificationName = qualificationName;
		this.grade = grade;
		this.certificationUnit = certificationUnit;
		this.effectiveDay = effectiveDay;
		this.type = type;
		this.remark = remark;
		this.checkCase = checkCase;
		this.delFlag = delFlag;
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

	public Date getQualificationDay() {
		return this.qualificationDay;
	}

	public void setQualificationDay(Date qualificationDay) {
		this.qualificationDay = qualificationDay;
	}

	public String getQualificationName() {
		return this.qualificationName;
	}

	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCertificationUnit() {
		return this.certificationUnit;
	}

	public void setCertificationUnit(String certificationUnit) {
		this.certificationUnit = certificationUnit;
	}

	public Date getEffectiveDay() {
		return this.effectiveDay;
	}

	public void setEffectiveDay(Date effectiveDay) {
		this.effectiveDay = effectiveDay;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

}