package com.cadre.pojo;

/**
 * SelectionHistory entity. @author MyEclipse Persistence Tools
 */

public class SelectionHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private Selection selection;
	private User user;
	private String selectionResults;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public SelectionHistory() {
	}

	/** minimal constructor */
	public SelectionHistory(Selection selection, User user) {
		this.selection = selection;
		this.user = user;
	}

	/** full constructor */
	public SelectionHistory(Selection selection, User user,
			String selectionResults, Integer delFlag) {
		this.selection = selection;
		this.user = user;
		this.selectionResults = selectionResults;
		this.delFlag = delFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Selection getSelection() {
		return this.selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSelectionResults() {
		return this.selectionResults;
	}

	public void setSelectionResults(String selectionResults) {
		this.selectionResults = selectionResults;
	}

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}