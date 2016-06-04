package com.cadre.controller.pojo;

import java.io.Serializable;

public class OrigionPlace implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String originPlaceProv;
	private String originPlaceCity;
	private String originPlaceDist;
	
	public String getOriginPlaceProv() {
		return originPlaceProv;
	}
	public void setOriginPlaceProv(String originPlaceProv) {
		this.originPlaceProv = originPlaceProv;
	}
	public String getOriginPlaceCity() {
		return originPlaceCity;
	}
	public void setOriginPlaceCity(String originPlaceCity) {
		this.originPlaceCity = originPlaceCity;
	}
	public String getOriginPlaceDist() {
		return originPlaceDist;
	}
	public void setOriginPlaceDist(String originPlaceDist) {
		this.originPlaceDist = originPlaceDist;
	}
	public String toCodeString() {
		// TODO Auto-generated method stub
		//return originPlaceProv + "," + originPlaceCity + ","+ originPlaceDist;
		return originPlaceDist;
	}
	public OrigionPlace format(String origionPlaceStr){
		OrigionPlace op = new OrigionPlace();
		String[] str =  new String[] {origionPlaceStr.substring(0,1),origionPlaceStr.substring(2,3),origionPlaceStr.substring(4,5)};
		op.setOriginPlaceProv(str[0]);
		op.setOriginPlaceDist(str[2]);
		op.setOriginPlaceCity(str[1]);
		return op;
	}
	
}
