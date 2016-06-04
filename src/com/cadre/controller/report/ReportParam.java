package com.cadre.controller.report;
import java.util.Date;

public class ReportParam {
   private String user;	
   private String informer;
   private String reportedDayOperator;
   private String reportedDay;
  
   private String reportedWay;
   private String reportedType;
   private String reportedContent;
   private String processingAndConclusion;
   private String processingAndConclusionType;
   private String remark;
   
   public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	

public String getInformer() {
	return informer;
}

public void setInformer(String informer) {
	this.informer = informer;
}

public String getReportedDay() {
	return reportedDay;
}

public void setReportedDay(String reportedDay) {
	this.reportedDay = reportedDay;
}

public String getReportedWay() {
	return reportedWay;
}

public void setReportedWay(String reportedWay) {
	this.reportedWay = reportedWay;
}

public String getReportedType() {
	return reportedType;
}

public void setReportedType(String reportedType) {
	this.reportedType = reportedType;
}

public String getReportedContent() {
	return reportedContent;
}

public void setReportedContent(String reportedContent) {
	this.reportedContent = reportedContent;
}

public String getProcessingAndConclusion() {
	return processingAndConclusion;
}

public void setProcessingAndConclusion(String processingAndConclusion) {
	this.processingAndConclusion = processingAndConclusion;
}

public String getProcessingAndConclusionType() {
	return processingAndConclusionType;
}

public void setProcessingAndConclusionType(String processingAndConclusionType) {
	this.processingAndConclusionType = processingAndConclusionType;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public String getReportedDayOperator() {
	return reportedDayOperator;
}

public void setReportedDayOperator(String reportedDayOperator) {
	this.reportedDayOperator = reportedDayOperator;
}
}
