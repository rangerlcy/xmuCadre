package com.cadre.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields
	private String level;
	private Date levelTime;
	private Integer id;
	private String name;
	private String gender;
	private String identifyNum;
	private Date birthDay;
	private String birthPlace;
	private String originPlace;
	private String nation;
	private Date beginWorkDay;
	private Date beginSchoolWorkDay;
	private Date retireDay;
	private String party;
	private Date joinPartyDay;
	private String researchDirection;
	private String healthStatus;
	private String photoUrl;
	private String identityTypeLabel;
	private String remark;
	private String checkCase;
	private String contectWorkPhone;
	private String contectMobilePhone;
	private String contectEmail;
	private Integer delFlag;
	private String a0100;
	private String userName;
	private String passWord;
	private String number;
	private Set assesses = new HashSet(0);
	private Set otherQualificationHistories = new HashSet(0);
	private Set postHistories = new HashSet(0);
	private Set reports = new HashSet(0);
	private Set skills = new HashSet(0);
	private Set educationHistories = new HashSet(0);
	private Set postUsers = new HashSet(0);
	private Set trainUsers = new HashSet(0);
	private Set administrationLevelHistories = new HashSet(0);
	private Set secondmentUsers = new HashSet(0);
	private Set administrationWorkHistories = new HashSet(0);
	private Set otherWorkHistories = new HashSet(0);
	private Set relations = new HashSet(0);
	private Set partTimeJobs = new HashSet(0);
	private Set selectionHistories = new HashSet(0);
	private Set positions = new HashSet(0);
	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String level, Date levelTime, String name, String gender, String identifyNum, Date birthDay,
			String birthPlace, String originPlace, String nation,
			Date beginWorkDay, Date beginSchoolWorkDay, Date retireDay,
			String party, Date joinPartyDay, String researchDirection,
			String healthStatus, String photoUrl, String identityTypeLabel,
			String remark, String checkCase, String contectWorkPhone,
			String contectMobilePhone, String contectEmail, Integer delFlag,
			String a0100, String userName, String passWord, String number,
			Set assesses, Set otherQualificationHistories, Set postHistories,
			Set reports, Set skills, Set educationHistories, Set postUsers,
			Set trainUsers, Set administrationLevelHistories,
			Set secondmentUsers, Set administrationWorkHistories,
			Set otherWorkHistories, Set relations, Set partTimeJobs,
			Set selectionHistories) {
		this.levelTime = levelTime;
		this.level = level;
		this.name = name;
		this.gender = gender;
		this.identifyNum = identifyNum;
		this.birthDay = birthDay;
		this.birthPlace = birthPlace;
		this.originPlace = originPlace;
		this.nation = nation;
		this.beginWorkDay = beginWorkDay;
		this.beginSchoolWorkDay = beginSchoolWorkDay;
		this.retireDay = retireDay;
		this.party = party;
		this.joinPartyDay = joinPartyDay;
		this.researchDirection = researchDirection;
		this.healthStatus = healthStatus;
		this.photoUrl = photoUrl;
		this.identityTypeLabel = identityTypeLabel;
		this.remark = remark;
		this.checkCase = checkCase;
		this.contectWorkPhone = contectWorkPhone;
		this.contectMobilePhone = contectMobilePhone;
		this.contectEmail = contectEmail;
		this.delFlag = delFlag;
		this.a0100 = a0100;
		this.userName = userName;
		this.passWord = passWord;
		this.number = number;
		this.assesses = assesses;
		this.otherQualificationHistories = otherQualificationHistories;
		this.postHistories = postHistories;
		this.reports = reports;
		this.skills = skills;
		this.educationHistories = educationHistories;
		this.postUsers = postUsers;
		this.trainUsers = trainUsers;
		this.administrationLevelHistories = administrationLevelHistories;
		this.secondmentUsers = secondmentUsers;
		this.administrationWorkHistories = administrationWorkHistories;
		this.otherWorkHistories = otherWorkHistories;
		this.relations = relations;
		this.partTimeJobs = partTimeJobs;
		this.selectionHistories = selectionHistories;
	}

	// Property accessors

	public Date getLevelTime(){
		return this.levelTime;
	}
	
	public void setLevelTime(Date levelTime){
		this.levelTime=levelTime;
	}
	public String getLevel(){
		return this.level;
	}
	
	public void setLevel(String level){
		this.level = level;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdentifyNum() {
		return this.identifyNum;
	}

	public void setIdentifyNum(String identifyNum) {
		this.identifyNum = identifyNum;
	}

	public Date getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getBirthPlace() {
		return this.birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getOriginPlace() {
		return this.originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getBeginWorkDay() {
		return this.beginWorkDay;
	}

	public void setBeginWorkDay(Date beginWorkDay) {
		this.beginWorkDay = beginWorkDay;
	}

	public Date getBeginSchoolWorkDay() {
		return this.beginSchoolWorkDay;
	}

	public void setBeginSchoolWorkDay(Date beginSchoolWorkDay) {
		this.beginSchoolWorkDay = beginSchoolWorkDay;
	}

	public Date getRetireDay() {
		return this.retireDay;
	}

	public void setRetireDay(Date retireDay) {
		this.retireDay = retireDay;
	}

	public String getParty() {
		return this.party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public Date getJoinPartyDay() {
		return this.joinPartyDay;
	}

	public void setJoinPartyDay(Date joinPartyDay) {
		this.joinPartyDay = joinPartyDay;
	}

	public String getResearchDirection() {
		return this.researchDirection;
	}

	public void setResearchDirection(String researchDirection) {
		this.researchDirection = researchDirection;
	}

	public String getHealthStatus() {
		return this.healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getIdentityTypeLabel() {
		return this.identityTypeLabel;
	}

	public void setIdentityTypeLabel(String identityTypeLabel) {
		this.identityTypeLabel = identityTypeLabel;
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

	public String getContectWorkPhone() {
		return this.contectWorkPhone;
	}

	public void setContectWorkPhone(String contectWorkPhone) {
		this.contectWorkPhone = contectWorkPhone;
	}

	public String getContectMobilePhone() {
		return this.contectMobilePhone;
	}

	public void setContectMobilePhone(String contectMobilePhone) {
		this.contectMobilePhone = contectMobilePhone;
	}

	public String getContectEmail() {
		return this.contectEmail;
	}

	public void setContectEmail(String contectEmail) {
		this.contectEmail = contectEmail;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Set getAssesses() {
		return this.assesses;
	}

	public void setAssesses(Set assesses) {
		this.assesses = assesses;
	}

	public Set getOtherQualificationHistories() {
		return this.otherQualificationHistories;
	}

	public void setOtherQualificationHistories(Set otherQualificationHistories) {
		this.otherQualificationHistories = otherQualificationHistories;
	}

	public Set getPostHistories() {
		return this.postHistories;
	}

	public void setPostHistories(Set postHistories) {
		this.postHistories = postHistories;
	}

	public Set getReports() {
		return this.reports;
	}

	public void setReports(Set reports) {
		this.reports = reports;
	}

	public Set getSkills() {
		return this.skills;
	}

	public void setSkills(Set skills) {
		this.skills = skills;
	}
	
	public Set getPositions() {
		return this.positions;
	}

	public void setPositions(Set positions) {
		this.positions = positions;
	}

	public Set getEducationHistories() {
		return this.educationHistories;
	}

	public void setEducationHistories(Set educationHistories) {
		this.educationHistories = educationHistories;
	}

	public Set getPostUsers() {
		return this.postUsers;
	}

	public void setPostUsers(Set postUsers) {
		this.postUsers = postUsers;
	}

	public Set getTrainUsers() {
		return this.trainUsers;
	}

	public void setTrainUsers(Set trainUsers) {
		this.trainUsers = trainUsers;
	}

	public Set getAdministrationLevelHistories() {
		return this.administrationLevelHistories;
	}

	public void setAdministrationLevelHistories(Set administrationLevelHistories) {
		this.administrationLevelHistories = administrationLevelHistories;
	}

	public Set getSecondmentUsers() {
		return this.secondmentUsers;
	}

	public void setSecondmentUsers(Set secondmentUsers) {
		this.secondmentUsers = secondmentUsers;
	}

	public Set getAdministrationWorkHistories() {
		return this.administrationWorkHistories;
	}

	public void setAdministrationWorkHistories(Set administrationWorkHistories) {
		this.administrationWorkHistories = administrationWorkHistories;
	}

	public Set getOtherWorkHistories() {
		return this.otherWorkHistories;
	}

	public void setOtherWorkHistories(Set otherWorkHistories) {
		this.otherWorkHistories = otherWorkHistories;
	}

	public Set getRelations() {
		return this.relations;
	}

	public void setRelations(Set relations) {
		this.relations = relations;
	}

	public Set getPartTimeJobs() {
		return this.partTimeJobs;
	}

	public void setPartTimeJobs(Set partTimeJobs) {
		this.partTimeJobs = partTimeJobs;
	}

	public Set getSelectionHistories() {
		return this.selectionHistories;
	}

	public void setSelectionHistories(Set selectionHistories) {
		this.selectionHistories = selectionHistories;
	}

}