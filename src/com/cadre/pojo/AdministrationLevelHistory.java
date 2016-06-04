package com.cadre.pojo;

import java.util.Date;


/**
 * AdministrationLevelHistory entity. @author MyEclipse Persistence Tools
 */

public class AdministrationLevelHistory  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private WorkingPaper paper;
     private User user;
     private Date levelDay;
     private String levelName;
     private String remark;
     private String checkCase;
     private Integer delFlag;


    // Constructors

    /** default constructor */
    public AdministrationLevelHistory() {
    }

	/** minimal constructor */
    public AdministrationLevelHistory(WorkingPaper paper, User user) {
        this.paper = paper;
        this.user = user;
    }
    
    /** full constructor */
    public AdministrationLevelHistory(WorkingPaper paper, User user, Date levelDay, String levelName, String remark, String checkCase, Integer delFlag) {
        this.paper = paper;
        this.user = user;
        this.levelDay = levelDay;
        this.levelName = levelName;
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

    public Date getLevelDay() {
        return this.levelDay;
    }
    
    public void setLevelDay(Date levelDay) {
        this.levelDay = levelDay;
    }

    public String getLevelName() {
        return this.levelName;
    }
    
    public void setLevelName(String levelName) {
        this.levelName = levelName;
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