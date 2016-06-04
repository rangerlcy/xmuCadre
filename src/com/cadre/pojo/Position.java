package com.cadre.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Position entity. @author MyEclipse Persistence Tools
 */

public class Position  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String academy;
     private String department;
     private String positionName;
     private String positionType;
     private Date positionDay;
     private Date delPositionDay;
     private String positionLevel;
     private Integer delFlag;
     private Integer Sequence;		//表中没有，但需要用到的属性
     private Integer isDelWithUser;
     private Paper paper;
     private Set postUsers = new HashSet(0);
     private Set postHistories = new HashSet(0);
     private Integer emptyFlag;
     private User user;
     private Integer academySeq;
     private Integer departmentSeq;
     private Integer positionNameSeq;
     private String positionRemark;
     
     public String getPositionRemark() {
		return positionRemark;
	}


	public void setPositionRemark(String positionRemark) {
		this.positionRemark = positionRemark;
	}


	public Integer getAcademySeq() {
		return academySeq;
	}


	public void setAcademySeq(Integer academySeq) {
		this.academySeq = academySeq;
	}


	public Integer getDepartmentSeq() {
		return departmentSeq;
	}


	public void setDepartmentSeq(Integer departmentSeq) {
		this.departmentSeq = departmentSeq;
	}


	public Integer getPositionNameSeq() {
		return positionNameSeq;
	}


	public void setPositionNameSeq(Integer positionNameSeq) {
		this.positionNameSeq = positionNameSeq;
	}

	
    // Constructors

    /** default constructor */
    public Position() {
    }

    
    /** full constructor */
    public Position(String academy, String department, String positionName, String positionType, Date positionDay, String positionLevel, Integer delFlag, Integer isDelWithUser,Paper paper, Set postUsers, Set postHistories) {
        this.academy = academy;
        this.department = department;
        this.positionName = positionName;
        this.positionType = positionType;
        this.positionDay = positionDay;
        this.positionLevel = positionLevel;
        this.delFlag = delFlag;
        this.isDelWithUser = isDelWithUser;
        this.paper = paper;
        this.postUsers = postUsers;
        this.postHistories = postHistories;
    }

   
    // Property accessors
    
    public Integer getEmptyFlag() {
        return this.emptyFlag;
    }
    
    public void setEmptyFlag(Integer flag) {
        this.emptyFlag = flag;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User flag) {
        this.user = flag;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    //表中没有，但需要用到的属性
    public Integer getSequence() {
        return this.Sequence;
    }
    
    public void setSequence(Integer s) {
        this.Sequence = s;
    }

    public String getAcademy() {
        return this.academy;
    }
    
    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPositionName() {
        return this.positionName;
    }
    
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionType() {
        return this.positionType;
    }
    
    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public Date getPositionDay() {
        return this.positionDay;
    }
    
    public void setPositionDay(Date positionDay) {
        this.positionDay = positionDay;
    }
    
    
    public Date getDelPositionDay() {
        return this.delPositionDay;
    }
    
    public void setDelPositionDay(Date delpositionDay) {
        this.delPositionDay = delpositionDay;
    }
    
    public String getPositionLevel() {
        return this.positionLevel;
    }
    
    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public Integer getDelFlag() {
        return this.delFlag;
    }
    
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getIsDelWithUser() {
        return this.isDelWithUser;
    }
    
    public void setIsDelWithUser(Integer isDelWithUser) {
        this.isDelWithUser = isDelWithUser;
    }

    public Paper getPaper() {
		return paper;
	}


	public void setPaper(Paper paper) {
		this.paper = paper;
	}


	public Set getPostUsers() {
        return this.postUsers;
    }
    
    public void setPostUsers(Set postUsers) {
        this.postUsers = postUsers;
    }

    public Set getPostHistories() {
        return this.postHistories;
    }
    
    public void setPostHistories(Set postHistories) {
        this.postHistories = postHistories;
    }
   








}