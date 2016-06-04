package com.cadre.controller.pojo;

import java.io.Serializable;

public class BirthPlace implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String birthPlaceProv;
	private String birthPlaceCity;
	private String birthPlaceDist;
	public String getBirthPlaceProv() {
		return birthPlaceProv;
	}
	public void setBirthPlaceProv(String birthPlaceProv) {
		this.birthPlaceProv = birthPlaceProv;
	}
	public String getBirthPlaceCity() {
		return birthPlaceCity;
	}
	public void setBirthPlaceCity(String birthPlaceCity) {
		this.birthPlaceCity = birthPlaceCity;
	}
	public String getBirthPlaceDist() {
		return birthPlaceDist;
	}
	public void setBirthPlaceDist(String birthPlaceDist) {
		this.birthPlaceDist = birthPlaceDist;
	}
	public String toCodeString() {
		// TODO Auto-generated method stub
		//return birthPlaceProv+","+birthPlaceCity+","+birthPlaceDist;
		return birthPlaceDist;
	}
	public BirthPlace format(String birthPlaceStr){
		//String[] str = birthPlaceStr.split(",");
		String[] str = new String[] {birthPlaceStr.substring(0,1),birthPlaceStr.substring(2, 3),birthPlaceStr.substring(4, 5)};
		BirthPlace bp = new BirthPlace();
		bp.setBirthPlaceProv(str[0]);
		bp.setBirthPlaceCity(str[1]);
		bp.setBirthPlaceDist(str[2]);
		return bp;
	}
	
}
