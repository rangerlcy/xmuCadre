
package com.cadre.controller.pojo;

import com.cadre.pojo.Skill;

public class TrainUserSimple {
	private PositionSimple positionSimple;
	private Integer userId;
	private Integer positionId;
	private Integer trainUserId;
	private Integer skillId;
	private Integer trainId;
	private String userName;
	private String skillName;
	
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

	
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public Integer getSkillId() {
		return skillId;
	}
	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private Skill skill;
	
	public PositionSimple getPositionSimple() {
		return positionSimple;
	}
	public void setPositionSimple(PositionSimple positionSimple) {
		this.positionSimple = positionSimple;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	public Integer getTrainUserId() {
		return trainUserId;
	}
	public void setTrainUserId(Integer trainUserId) {
		this.trainUserId = trainUserId;
	}
	
}
