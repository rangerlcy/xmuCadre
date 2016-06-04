package com.cadre.pojo;

import java.util.Date;

/**
 * PostUser entity. @author MyEclipse Persistence Tools
 */

public class PostUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private WorkingPaper workingPaper;
	private Integer workingPaperId;			//没什么卵用
	private Position position;
	private User user;
	private String appointOrRemove;
	private String type;
	private String reasion;
	private String grade;
	private String remark;
	private Integer delFlag;
	private Date actionDay;

	// Constructors

	/** default constructor */
	public PostUser() {
	}

	/** full constructor */
	public PostUser(WorkingPaper workingPaper, Position position, User user,
			String appointOrRemove, String type, String reasion, String grade,
			String remark, Integer delFlag) {
		this.workingPaper = workingPaper;
		this.position = position;
		this.user = user;
		this.appointOrRemove = appointOrRemove;
		this.type = type;
		this.reasion = reasion;
		this.grade = grade;
		this.remark = remark;
		this.delFlag = delFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setworkingPaperId(Integer workingPaperId) {
		this.workingPaperId = workingPaperId;
	}

	public Integer getworkingPaperId() {
		return this.workingPaperId;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public WorkingPaper getWorkingPaper() {
		return this.workingPaper;
	}

	public void setWorkingPaper(WorkingPaper workingPaper) {
		this.workingPaper = workingPaper;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAppointOrRemove() {
		return this.appointOrRemove;
	}

	public void setAppointOrRemove(String appointOrRemove) {
		this.appointOrRemove = appointOrRemove;
	}
	
	public Date getActionDay() {
		return this.actionDay;
	}

	public void setActionDay(Date da) {
		this.actionDay = da;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReasion() {
		return this.reasion;
	}

	public void setReasion(String reasion) {
		this.reasion = reasion;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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