package com.cadre.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * WorkingPaper entity. @author MyEclipse Persistence Tools
 */

public class WorkingPaper  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String workingType;
     private String postingNumber;
     private String postingName;
     private Date postingDay;
     private String remark;
     private Integer delFlag;
     private Set postUsers = new HashSet(0);


    // Constructors

    /** default constructor */
    public WorkingPaper() {
    }

    
    /** full constructor */
    public WorkingPaper(String workingType, String postingNumber, String postingName, Date postingDay, String remark, Integer delFlag, Set postUsers) {
        this.workingType = workingType;
        this.postingNumber = postingNumber;
        this.postingName = postingName;
        this.postingDay = postingDay;
        this.remark = remark;
        this.delFlag = delFlag;
        this.postUsers = postUsers;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkingType() {
        return this.workingType;
    }
    
    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public String getPostingNumber() {
        return this.postingNumber;
    }
    
    public void setPostingNumber(String postingNumber) {
        this.postingNumber = postingNumber;
    }

    public String getPostingName() {
        return this.postingName;
    }
    
    public void setPostingName(String postingName) {
        this.postingName = postingName;
    }

    public Date getPostingDay() {
        return this.postingDay;
    }
    
    public void setPostingDay(Date postingDay) {
        this.postingDay = postingDay;
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

    public Set getPostUsers() {
        return this.postUsers;
    }
    
    public void setPostUsers(Set postUsers) {
        this.postUsers = postUsers;
    }
   








}