package com.cadre.pojo;

public class ReportFile implements java.io.Serializable {

	//field
	private Integer id;
	private Integer reportId;
	private String name;
	private String url;
	private Integer delFlag;
	
	/** default constructor */
	public ReportFile(){
		
	}
	
	public ReportFile(Integer reportid, String name, String url){
		this.reportId=reportid;
		this.name=name;
		this.url=url;
	}
	
	/** full constructor */
	public ReportFile(Integer id,Integer reportid, String name, String url, Integer delflag){
		this.id=id;
		this.reportId=reportid;
		this.name=name;
		this.url=url;
		this.delFlag=delflag;
	}

	
	
	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
