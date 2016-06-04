package com.cadre.pojo;

import java.util.Date;


/**用于导出花名册的实体类**/
public class ExportUserExcel {
	
	// Fields
	private Integer id;				//序号
	//user表
	private Integer userId;			//不导出的属性，只是为了方便查人
	private String name;
	private String gender;
	private String nation;			//民族-要查字典表
	private Date birthDay;
	private String originPlace;		//籍贯-要查place表
	private Integer age;
	private String party;			//党派-要查字典表
	private Date beginWorkDay;		//参加工作时间
	private Date beginSchoolWorkDay;
	private String levelName;		//行政级别,人员级别
	private Date levelDay;			//级别时间,确定人员级别的时间
	
	//post_user表
	private Date nowWorkDay;		//现职时间,上岗时间
	
	//position表
	private String academy;			//单位-要查origanization表
	private String department;		//系所-要查origanization表
	private String positionName;	//行政职务
	private String positionLevel;	//岗位的级别
	private String jobName;			//职位备注
	
	//skill表
	private String skillName;		//专业技术职务
	
	//education_history表，取最新的信息
	private String degree;			//学位-要查字典表
	private String school;			//毕业学校
	
	
	// Constructors

    /** default constructor */
    public ExportUserExcel() {
    }
    
    /**min constructor**/
    public ExportUserExcel(Integer id, String name) {
    	this.id=id;
    	this.name=name;
    }
    /**full constructor**/
	public ExportUserExcel(Integer id, String name, String gender,
			String nation, Date birthDay, String originPlace, Integer age,
			String party, Date beginWorkDay, Date beginSchoolWorkDay,
			Date nowWorkDay, String academy, String department,
			String positionName, String skillName, String degree,
			String school, String levelName, Date levelDay, String positionLevel,
			String jobName) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.nation = nation;
		this.birthDay = birthDay;
		this.originPlace = originPlace;
		this.age = age;
		this.party = party;
		this.beginWorkDay = beginWorkDay;
		this.beginSchoolWorkDay = beginSchoolWorkDay;
		this.nowWorkDay = nowWorkDay;
		this.academy = academy;
		this.department = department;
		this.positionName = positionName;
		this.skillName = skillName;
		this.degree = degree;
		this.school = school;
		this.levelName = levelName;
		this.levelDay = levelDay;
		this.positionLevel = positionLevel;
		this.jobName = jobName;
	}


	// Property accessors
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId=userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public Date getBirthDay() {
		return birthDay; 
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getOriginPlace() {
		return originPlace;
	}
	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public Date getBeginWorkDay() {
		return beginWorkDay;
	}
	public void setBeginWorkDay(Date beginWorkDay) {
		this.beginWorkDay = beginWorkDay;
	}
	public Date getBeginSchoolWorkDay() {
		return beginSchoolWorkDay;
	}
	public void setBeginSchoolWorkDay(Date beginSchoolWorkDay) {
		this.beginSchoolWorkDay = beginSchoolWorkDay;
	}
	public Date getNowWorkDay() {
		return nowWorkDay;
	}
	public void setNowWorkDay(Date nowWorkDay) {
		this.nowWorkDay = nowWorkDay;
	}
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Date getLevelDay() {
		return levelDay;
	}
	public void setLevelDay(Date levelDay) {
		this.levelDay = levelDay;
	}
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
}
