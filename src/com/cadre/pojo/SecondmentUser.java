package com.cadre.pojo;

/**
 * SecondmentUser entity. @author MyEclipse Persistence Tools
 */

public class SecondmentUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private Secondment secondment;
	private User user;
	private String temporaryPlace;
	private String temporaryUnit;
	private String temporaryJob;
	private String assessementSitutation;
	private String remark;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public SecondmentUser() {
	}

	/** full constructor */
	public SecondmentUser(Secondment secondment, User user,
			String temporaryPlace, String temporaryUnit, String temporaryJob,
			String assessementSitutation, String remark, Integer delFlag) {
		this.secondment = secondment;
		this.user = user;
		this.temporaryPlace = temporaryPlace;
		this.temporaryUnit = temporaryUnit;
		this.temporaryJob = temporaryJob;
		this.assessementSitutation = assessementSitutation;
		this.remark = remark;
		this.delFlag = delFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Secondment getSecondment() {
		return this.secondment;
	}

	public void setSecondment(Secondment secondment) {
		this.secondment = secondment;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTemporaryPlace() {
		return this.temporaryPlace;
	}

	public void setTemporaryPlace(String temporaryPlace) {
		this.temporaryPlace = temporaryPlace;
	}

	public String getTemporaryUnit() {
		return this.temporaryUnit;
	}

	public void setTemporaryUnit(String temporaryUnit) {
		this.temporaryUnit = temporaryUnit;
	}

	public String getTemporaryJob() {
		return this.temporaryJob;
	}

	public void setTemporaryJob(String temporaryJob) {
		this.temporaryJob = temporaryJob;
	}

	public String getAssessementSitutation() {
		return this.assessementSitutation;
	}

	public void setAssessementSitutation(String assessementSitutation) {
		this.assessementSitutation = assessementSitutation;
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

}