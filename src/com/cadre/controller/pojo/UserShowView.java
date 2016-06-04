package com.cadre.controller.pojo;

import com.cadre.pojo.User;

public class UserShowView {

	/**
	 * 
	 */

	private User user;
	private String administrationLevel;
	private String administrationWorkName;
	private String administrationWorkUnits;
	
	public String getAdministrationLevel() {
		return administrationLevel;
	}
	public void setAdministrationLevel(String administrationLevel) {
		this.administrationLevel = administrationLevel;
	}
	public String getAdministrationWorkName() {
		return administrationWorkName;
	}
	public void setAdministrationWorkName(String administrationWorkName) {
		this.administrationWorkName = administrationWorkName;
	}
	public String getAdministrationWorkUnits() {
		return administrationWorkUnits;
	}
	public void setAdministrationWorkUnits(String administrationWorkUnits) {
		this.administrationWorkUnits = administrationWorkUnits;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
