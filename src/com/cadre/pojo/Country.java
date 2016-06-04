package com.cadre.pojo;

/**
 * Country entity. @author MyEclipse Persistence Tools
 */

public class Country implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;
	private String name;
	private String simpleSpell;

	// Constructors

	/** default constructor */
	public Country() {
	}

	/** full constructor */
	public Country(String code, String name, String simpleSpell) {
		this.code = code;
		this.name = name;
		this.simpleSpell = simpleSpell;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleSpell() {
		return this.simpleSpell;
	}

	public void setSimpleSpell(String simpleSpell) {
		this.simpleSpell = simpleSpell;
	}

}