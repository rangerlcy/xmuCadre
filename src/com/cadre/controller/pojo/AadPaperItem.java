package com.cadre.controller.pojo;

import java.util.Date;

import com.cadre.model.utils.DateUtil;
import com.cadre.pojo.Position;
import com.cadre.pojo.User;

public class AadPaperItem {
	private Integer index;
	private Integer positionId;
	private Integer userId;
	private String appointOrRemove;
	private String appointOrDismissType;			//任职或者免职类型，即仅任、升级、任职且升级
	private String type;				//岗位类型，即全职或者兼职
	private String reasion;
	private String grade;
	private String academy;				
	private String department;
	private String posName;
	private String remark;
	private Date actionDay;
	
	public String getAcademy(){
		return academy;
	}
	public void setAcademy(String a){
		this.academy=a;
	}
	
	public String getDepartment(){
		return department;
	}
	public void setDepartment(String a){
		this.department=a;
	}
	
	public String getPosName(){
		return posName;
	}
	public void setPosName(String a){
		this.posName=a;
	}
	
	public String getAppointOrDismissType(){
		return appointOrDismissType;
	}
	public void setAppointOrDismissType(String a){
		this.appointOrDismissType=a;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAppointOrRemove() {
		return appointOrRemove;
	}
	public void setAppointOrRemove(String appointOrRemove) {
		this.appointOrRemove = appointOrRemove;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReasion() {
		return reasion;
	}
	public void setReasion(String reasion) {
		this.reasion = reasion;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getActionDay() {
		return actionDay;
	}
	public void setActionDay(Date actionDay) {
		this.actionDay = actionDay;
	}
	

}
