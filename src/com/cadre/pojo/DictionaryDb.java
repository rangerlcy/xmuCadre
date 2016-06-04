package com.cadre.pojo;

/**
 * DictionaryDb entity. @author MyEclipse Persistence Tools
 */

public class DictionaryDb implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;
	private String parentCode;
	private String key;
	private String value;
	private String name;
	private String remark;

	// Constructors

	/** default constructor */
	public DictionaryDb() {
	}

	/** full constructor */
	public DictionaryDb(String code, String parentCode, String key,
			String value, String name, String remark) {
		this.code = code;
		this.parentCode = parentCode;
		this.key = key;
		this.value = value;
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

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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