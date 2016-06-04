package com.cadre.pojo;

import java.sql.Timestamp;



/**
 * OrganizationHistory entity. @author MyEclipse Persistence Tools
 */

public class OrganizationHistory  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Paper paper;
     private String code;
     private String parentCode;
     private String name;
     private Integer action;
     private Timestamp actionTime;

    // Constructors

    /** default constructor */
    public OrganizationHistory() {
    }

	/** minimal constructor */
    public OrganizationHistory(Paper paper) {
        this.paper = paper;
    }
    
    /** full constructor */
    public OrganizationHistory(Paper paper, String code, String parentCode, String name
    		,Integer action,Timestamp actionTime) {
        this.paper = paper;
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.action = action;
        this.actionTime = actionTime;
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

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return this.parentCode;
    }
    
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Timestamp getActionTime() {
		return actionTime;
	}

	public void setActionTime(Timestamp actionTime) {
		this.actionTime = actionTime;
	}
   








}