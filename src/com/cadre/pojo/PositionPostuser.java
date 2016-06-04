package com.cadre.pojo;

import java.util.Date;

/**
 * PositionPostuser entity. @author MyEclipse Persistence Tools
 */

public class PositionPostuser implements java.io.Serializable {

	// Fields

	private Integer id;
	private PostUser postUser;		//外键关联
	private Position position;		//外键关联
	private Date beginWorkDay;
	private Date endWorkDay;
	private String remark;
	private User user;				//外键关联
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public PositionPostuser() {
	}

	/** full constructor */
	public PositionPostuser(PostUser postUser, Position position,
			Date beginWorkDay, Date endWorkDay, String remark, Integer delFlag) {
		this.postUser = postUser;
		this.position = position;
		this.beginWorkDay = beginWorkDay;
		this.endWorkDay = endWorkDay;
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

	public PostUser getPostUser() {
		return this.postUser;
	}

	public void setPostUser(PostUser postUser) {
		this.postUser = postUser;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Date getBeginWorkDay() {
		return this.beginWorkDay;
	}

	public void setBeginWorkDay(Date beginWorkDay) {
		this.beginWorkDay = beginWorkDay;
	}

	public Date getEndWorkDay() {
		return this.endWorkDay;
	}

	public void setEndWorkDay(Date endWorkDay) {
		this.endWorkDay = endWorkDay;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}