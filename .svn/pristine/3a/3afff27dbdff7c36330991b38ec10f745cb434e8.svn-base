package com.cadre.pojo;

import java.util.Set;

import com.cadre.pojo.RoleResource;

/**
 * SysResource entity. @author MyEclipse Persistence Tools
 */

public class SysResource implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer parentId;
	private String name;
	private Integer resourceType;
	private String url;
	private String cssClass;
	private String resourceImg;
	private Integer orderNum;
	private String securityCode;
	private Set<RoleResource> roleResource;
	// Constructors

	/** default constructor */
	public SysResource() {
	}

	/** minimal constructor */
	public SysResource(String name, Integer resourceType) {
		this.name = name;
		this.resourceType = resourceType;
	}

	/** full constructor */
	public SysResource(Integer parentId, String name,
			Integer resourceType, String url, String cssClass,
			String resourceImg, Integer orderNum, String securityCode) {
		this.parentId = parentId;
		this.name = name;
		this.resourceType = resourceType;
		this.url = url;
		this.cssClass = cssClass;
		this.resourceImg = resourceImg;
		this.orderNum = orderNum;
		this.securityCode = securityCode;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getResourceImg() {
		return this.resourceImg;
	}

	public void setResourceImg(String resourceImg) {
		this.resourceImg = resourceImg;
	}

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Set<RoleResource> getRoleResource() {
		return roleResource;
	}

	public void setRoleResource(Set<RoleResource> roleResource) {
		this.roleResource = roleResource;
	}

}