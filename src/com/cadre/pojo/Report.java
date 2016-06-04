package com.cadre.pojo;

import java.util.Date;

/**
 * Report entity. @author MyEclipse Persistence Tools
 */

public class Report implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String informer;
	private Date reportedDay;
	private String reportedWay;
	private String reportedType;
	private String reportedContent;
	private String processingAndConclusion;
	private String processingAndConclusionType;
	private String remark;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public Report() {
	}

	/** minimal constructor */
	public Report(User user) {
		this.user = user;
	}

	/** full constructor */
	public Report(User user, String informer, Date reportedDay,
			String reportedWay, String reportedType, String reportedContent,
			String processingAndConclusion, String processingAndConclusionType,
			String remark, Integer delFlag) {
		this.user = user;
		this.informer = informer;
		this.reportedDay = reportedDay;
		this.reportedWay = reportedWay;
		this.reportedType = reportedType;
		this.reportedContent = reportedContent;
		this.processingAndConclusion = processingAndConclusion;
		this.processingAndConclusionType = processingAndConclusionType;
		this.remark = remark;
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

	public String getInformer() {
		return this.informer;
	}

	public void setInformer(String informer) {
		this.informer = informer;
	}

	public Date getReportedDay() {
		return this.reportedDay;
	}

	public void setReportedDay(Date reportedDay) {
		this.reportedDay = reportedDay;
	}

	public String getReportedWay() {
		return this.reportedWay;
	}

	public void setReportedWay(String reportedWay) {
		this.reportedWay = reportedWay;
	}

	public String getReportedType() {
		return this.reportedType;
	}

	public void setReportedType(String reportedType) {
		this.reportedType = reportedType;
	}

	public String getReportedContent() {
		return this.reportedContent;
	}

	public void setReportedContent(String reportedContent) {
		this.reportedContent = reportedContent;
	}

	public String getProcessingAndConclusion() {
		return this.processingAndConclusion;
	}

	public void setProcessingAndConclusion(String processingAndConclusion) {
		this.processingAndConclusion = processingAndConclusion;
	}

	public String getProcessingAndConclusionType() {
		return this.processingAndConclusionType;
	}

	public void setProcessingAndConclusionType(
			String processingAndConclusionType) {
		this.processingAndConclusionType = processingAndConclusionType;
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

}