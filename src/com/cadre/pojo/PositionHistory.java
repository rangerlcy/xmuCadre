package com.cadre.pojo;

import java.sql.Timestamp;


/**
 * PositionHistory entity. @author MyEclipse Persistence Tools
 */

public class PositionHistory  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Paper paper;
     private String positionLevel;
     private String positionType;
     private String positionName;
     private String academy;
     private String department;
     private Integer action;
     private Timestamp actionTime;


    // Constructors

    /** default constructor */
    public PositionHistory() {
    }

    
    /** full constructor */
    public PositionHistory(Paper paper, String positionLevel, String positionType, String positionName, String academy, String department, Integer action, Timestamp actionTime) {
        this.paper = paper;
        this.positionLevel = positionLevel;
        this.positionType = positionType;
        this.positionName = positionName;
        this.academy = academy;
        this.department = department;
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

    public String getPositionLevel() {
        return this.positionLevel;
    }
    
    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getPositionType() {
        return this.positionType;
    }
    
    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionName() {
        return this.positionName;
    }
    
    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

    public Integer getAction() {
        return this.action;
    }
    
    public void setAction(Integer action) {
        this.action = action;
    }

    public Timestamp getActionTime() {
        return this.actionTime;
    }
    
    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }
   








}