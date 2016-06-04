package com.cadre.pojo;

import java.util.Date;

import javax.management.loading.PrivateClassLoader;

import org.omg.CORBA.PRIVATE_MEMBER;


/**
 * Skill entity. @author MyEclipse Persistence Tools
 */

public class Skill  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Paper paper;
     private User user;
     private Date beginDay;
     private Date endDay;
     private String employmentLevel;
     private String remark;
     private String checkCase;
     private Integer delFlag;
     private String skillName;


    // Constructors

    /** default constructor */
    public Skill() {
    }

	/** minimal constructor */
    public Skill(Paper paper, User user) {
        this.paper = paper;
        this.user = user;
    }
    
    /** full constructor */
    public Skill(Paper paper, User user, Date beginDay, Date endDay, String employmentLevel, String remark, String checkCase, Integer delFlag) {
        this.paper = paper;
        this.user = user;
        this.beginDay = beginDay;
        this.endDay = endDay;
        this.employmentLevel = employmentLevel;
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

    public Paper getPaper() {
        return this.paper;
    }
    
    public void setPaper(Paper paper) {
        this.paper = paper;
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

    public String getEmploymentLevel() {
        return this.employmentLevel;
    }
    
    public void setEmploymentLevel(String employmentLevel) {
        this.employmentLevel = employmentLevel;
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

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

   








}