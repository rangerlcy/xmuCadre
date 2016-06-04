package com.cadre.pojo;



/**
 * Assess entity. @author MyEclipse Persistence Tools
 */

public class Assess  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private User user;
     private Integer year;
     private String assessmentResult;
     private Integer delFlag;


    // Constructors

    /** default constructor */
    public Assess() {
    }

	/** minimal constructor */
    public Assess(User user) {
        this.user = user;
    }
    
    /** full constructor */
    public Assess(User user, Integer year, String assessmentResult, Integer delFlag) {
        this.user = user;
        this.year = year;
        this.assessmentResult = assessmentResult;
        this.delFlag = delFlag;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public Integer getYear() {
        return this.year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAssessmentResult() {
        return this.assessmentResult;
    }
    
    public void setAssessmentResult(String assessmentResult) {
        this.assessmentResult = assessmentResult;
    }

    public Integer getDelFlag() {
        return this.delFlag;
    }
    
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
   








}