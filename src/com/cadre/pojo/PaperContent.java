package com.cadre.pojo;

import java.util.Date;

public class PaperContent implements java.io.Serializable {

	//fields
	private Integer id;
	private Paper paper;
	private String academy1;
    private String department1;
    private String positionName1;
    private String positionType1;
    private Date positionDay1;
    private Date delPositionDay1;
    private String positionLevel1;
    
    private String academy2;
    private String department2;
    private String positionName2;
    private String positionType2;
    private Date positionDay2;
    private Date delPositionDay2;
    private String positionLevel2;
    
    private Integer positionId;
	private String editType;
	private Integer delFlag;
	
	private String positionRemark1;
	public String getPositionRemark1() {
		return positionRemark1;
	}

	public void setPositionRemark1(String positionRemark1) {
		this.positionRemark1 = positionRemark1;
	}

	public String getPositionRemark2() {
		return positionRemark2;
	}

	public void setPositionRemark2(String positionRemark2) {
		this.positionRemark2 = positionRemark2;
	}

	private String positionRemark2;
    
    /** default constructor */
    public PaperContent() {
    }
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public String getAcademy1() {
		return academy1;
	}
	public void setAcademy1(String academy1) {
		this.academy1 = academy1;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getPositionName1() {
		return positionName1;
	}
	public void setPositionName1(String positionName1) {
		this.positionName1 = positionName1;
	}
	public String getPositionType1() {
		return positionType1;
	}
	public void setPositionType1(String positionType1) {
		this.positionType1 = positionType1;
	}
	public Date getPositionDay1() {
		return positionDay1;
	}
	public void setPositionDay1(Date positionDay1) {
		this.positionDay1 = positionDay1;
	}
	
	public Date getDelPositionDay1() {
		return delPositionDay1;
	}
	public void setDelPositionDay1(Date positionDay1) {
		this.delPositionDay1 = positionDay1;
	}
	
	public Date getDelPositionDay2() {
		return delPositionDay2;
	}
	public void setDelPositionDay2(Date positionDay1) {
		this.delPositionDay2 = positionDay1;
	}
	
	
	
	public String getPositionLevel1() {
		return positionLevel1;
	}
	public void setPositionLevel1(String positionLevel1) {
		this.positionLevel1 = positionLevel1;
	}
	public String getAcademy2() {
		return academy2;
	}
	public void setAcademy2(String academy2) {
		this.academy2 = academy2;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getPositionName2() {
		return positionName2;
	}
	public void setPositionName2(String positionName2) {
		this.positionName2 = positionName2;
	}
	public String getPositionType2() {
		return positionType2;
	}
	public void setPositionType2(String positionType2) {
		this.positionType2 = positionType2;
	}
	public Date getPositionDay2() {
		return positionDay2;
	}
	public void setPositionDay2(Date positionDay2) {
		this.positionDay2 = positionDay2;
	}
	public String getPositionLevel2() {
		return positionLevel2;
	}
	public void setPositionLevel2(String positionLevel2) {
		this.positionLevel2 = positionLevel2;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}
	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
}
