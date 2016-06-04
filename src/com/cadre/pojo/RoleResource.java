package com.cadre.pojo;

import com.cadre.pojo.SysResource;
import com.cadre.pojo.SysRole;

/**
 * RoleResource entity. @author MyEclipse Persistence Tools
 */

public class RoleResource implements java.io.Serializable {

	// Fields

	private Integer id;
	private SysResource sysResource;
	private SysRole sysRole;

	// Constructors

	/** default constructor */
	public RoleResource() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysResource getSysResource() {
		return sysResource;
	}

	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

}