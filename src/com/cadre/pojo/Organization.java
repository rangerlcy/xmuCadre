package com.cadre.pojo;



/**
 * Organization entity. @author MyEclipse Persistence Tools
 */

public class Organization  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String code;
     private String parentCode;
     private String name;
     private Integer sequence;
     private Integer delFlag;


    // Constructors

    /** default constructor */
    public Organization() {
    }

	/** minimal constructor */
    public Organization(String code, String parentCode, String name) {
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
    }
    
    /** full constructor */
    public Organization(String code, String parentCode, String name, Integer sequence, Integer delFlag) {
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.sequence = sequence;
        this.delFlag = delFlag;
    }

   
    // Property accessors

    public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDelFlag() {
        return this.delFlag;
    }
    
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
   








}