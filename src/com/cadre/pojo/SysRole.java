package com.cadre.pojo;



/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */

public class SysRole  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String code;
     private String name;
     private String remark;


    // Constructors

    /** default constructor */
    public SysRole() {
    }

	/** minimal constructor */
    public SysRole(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    /** full constructor */
    public SysRole(String code, String name, String remark) {
        this.code = code;
        this.name = name;
        this.remark = remark;
    }

   
    // Property accessors

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

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}