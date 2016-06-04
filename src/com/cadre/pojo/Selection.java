package com.cadre.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Selection entity. @author MyEclipse Persistence Tools
 */

public class Selection implements java.io.Serializable {

	// Fields

	private Integer id;
	private Date selectionDay;
	private String selectionName;
	private String selectionType;
	private Integer delFlag;
	private Set selectionHistories = new HashSet(0);

	// Constructors

	/** default constructor */
	public Selection() {
	}

	/** minimal constructor */
	public Selection(Date selectionDay, String selectionName,
			String selectionType) {
		this.selectionDay = selectionDay;
		this.selectionName = selectionName;
		this.selectionType = selectionType;
	}

	/** full constructor */
	public Selection(Date selectionDay, String selectionName,
			String selectionType, Integer delFlag, Set selectionHistories) {
		this.selectionDay = selectionDay;
		this.selectionName = selectionName;
		this.selectionType = selectionType;
		this.delFlag = delFlag;
		this.selectionHistories = selectionHistories;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getSelectionDay() {
		return this.selectionDay;
	}

	public void setSelectionDay(Date selectionDay) {
		this.selectionDay = selectionDay;
	}

	public String getSelectionName() {
		return this.selectionName;
	}

	public void setSelectionName(String selectionName) {
		this.selectionName = selectionName;
	}

	public String getSelectionType() {
		return this.selectionType;
	}

	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Set getSelectionHistories() {
		return this.selectionHistories;
	}

	public void setSelectionHistories(Set selectionHistories) {
		this.selectionHistories = selectionHistories;
	}

}