package com.cadre.pojo;

/**
 * TrainUser entity. @author MyEclipse Persistence Tools
 */

public class TrainUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private Train train;
	private User user;
	private Integer delFlag;
	private Position position;
	private Skill skill;
	

	// Constructors

	/** default constructor */
	public TrainUser() {
	}

	/** full constructor */
	public TrainUser(Train train, User user, Integer delFlag) {
		this.train = train;
		this.user = user;
		this.delFlag = delFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Train getTrain() {
		return this.train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

}