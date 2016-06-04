package com.cadre.pojo;

import java.util.Date;


/**
 * AdministrationWorkHistory entity. @author MyEclipse Persistence Tools
 */

public class AdministrationWorkHistory  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private WorkingPaper paper;
     private User user;
     private Date beginDay;
     private Date endDay;
     private String units;
     private String departments;
     private String jobName;
     private String jobType;
     private String postingName;
     private String checkCase;
     private Integer delFlag;
     public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String userLevel;
     private String remark;
     
    // Constructors

    public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	/** default constructor */
    public AdministrationWorkHistory() {
    }

	/** minimal constructor */
    public AdministrationWorkHistory(WorkingPaper paper, User user) {
        this.paper = paper;
        this.user = user;
    }
    
    /** full constructor */
    public AdministrationWorkHistory(WorkingPaper paper, User user, Date beginDay, Date endDay, String units, String departments, String jobName, String jobType, String postingName, String checkCase, Integer delFlag) {
        this.paper = paper;
        this.user = user;
        this.beginDay = beginDay;
        this.endDay = endDay;
        this.units = units;
        this.departments = departments;
        this.jobName = jobName;
        this.jobType = jobType;
        this.postingName = postingName;
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

    public WorkingPaper getPaper() {
        return this.paper;
    }
    
    public void setPaper(WorkingPaper paper) {
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

    public String getUnits() {
        return this.units;
    }
    
    public void setUnits(String units) {
        this.units = units;
    }

    public String getDepartments() {
        return this.departments;
    }
    
    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getJobName() {
        return this.jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobType() {
        return this.jobType;
    }
    
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPostingName() {
        return this.postingName;
    }
    
    public void setPostingName(String postingName) {
        this.postingName = postingName;
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