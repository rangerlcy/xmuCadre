package com.cadre.model.view;

import java.util.List;
import java.util.Set;

import com.cadre.model.dictionary.DictionaryItem;
import com.cadre.model.dictionary.SimpleDictinaryManager;


public class ViewFunction {
	/**
	 * 查询人员级别
	 * @param key
	 * @return
	 */
	public static List<DictionaryItem> getAllLevel(){
		return SimpleDictinaryManager.get().getItems("level");
	}
	public static String getLevel(String key){
		return SimpleDictinaryManager.get().getValue("level", key);
	}
	/**
	 * 查询所有学位
	 * @return
	 */
	public static List<DictionaryItem> getAllDegree(){
		return SimpleDictinaryManager.get().getItems("education");
	}
	/**
	 * 根据编码查询学位
	 * @param key
	 * @return
	 */
	public static String getDegreeName(String key){
		return SimpleDictinaryManager.get().getValue("education", key);
	}
	/**
	 * 查询所有民族
	 * @return
	 */
	public static List<DictionaryItem> getAllEthnicity(){
		return SimpleDictinaryManager.get().getItems("ethnicity");
	}
	/**
	 * 根据民族编码查名称
	 * @param key
	 * @return
	 */
	public static String getEthnicityName(String key){
		return SimpleDictinaryManager.get().getValue("ethnicity", key);
	}
	/**
	 * 查询所有党派
	 * @return
	 */
	public static List<DictionaryItem> getAllPolicitalStatus(){
		return SimpleDictinaryManager.get().getItems("policitalStatus");
	}
	/**
	 * 根据党派编码查询党派
	 * @param key
	 * @return
	 */
	public static String getPolicitalStatusName(String key) {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getValue("policitalStatus",key);
	}
	/**
	 * 所有身份类型标签
	 * @return
	 */
	public static List<DictionaryItem> getAllIdentityTypeLabel(){
		return SimpleDictinaryManager.get().getItems("identityTypeLabel");
	}
	
	/**
	 * 根据身份类型标签号查询标签中文
	 * @return
	 */
	public static String getIdentityTypeLabelName(String key){
		return SimpleDictinaryManager.get().getValue("identityTypeLabel", key);
	}
	/**
	 * 所有单位类型
	 * @return
	 */
	public static List<DictionaryItem> getAllInstitutionType(){
		return SimpleDictinaryManager.get().getItems("institutionType");
	}
	
	/**
	 * 根据代码查询单位类型中文
	 * @return
	 */
	public static String getInstitutionType(String key){
		return SimpleDictinaryManager.get().getValue("institutionType", key);
	}
	/**
	 * 根据证件类型查询证件编码
	 * @param key
	 * @return
	 */
	public static String getCardType(String key) {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getValue("cardType",key);
	}
	/**
	 * 查询核对情况
	 * @param key
	 * @return
	 */
	public static String getUserCheckCase(String key) {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getValue("userCheckCase",key);
	}
	/**
	 * 查询导出标题
	 * @return
	 */
	public static List<DictionaryItem> getAllExportTitle(){
		return SimpleDictinaryManager.get().getItems("exportTitle");
	}
	
	/**
	 * 获取导出用户标题名
	 * @param string
	 * @return
	 */
	public static String getExportTitleName(String key) {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getValue("exportTitle", key);
	}
	public static List<DictionaryItem> getReportTitles() {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getItems("exportReportTitle");
	}	
	public static List<DictionaryItem> getSecondmentTitles() {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getItems("exportSecondmentTitle");
	}
	/**
	 * 查询所有健康状态类型
	 * @return
	 */
	public static List<DictionaryItem> getAllHealthStatus(){
		return SimpleDictinaryManager.get().getItems("healthStatus");
	}
	/**
	 * 查询所有审核状态
	 * @return
	 */
	public static List<DictionaryItem> getAllCheckCase(){
		return SimpleDictinaryManager.get().getItems("userCheckCase");
	}
	
	public static String getCheckCase(String key){
		return SimpleDictinaryManager.get().getValue("userCheckCase", key);
	}
	/**
	 * 根据证件类型名称查证件编码
	 * @param key
	 * @return
	 */
	public static String getCardTypeCode(String name){
		return SimpleDictinaryManager.get().getKey("cardType", name);
	}
	public static String getGenderCode(String name) {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getKey("gender", name);
	}
	/**
	 * 获取所有考核结果
	 * @return
	 */
	public static List<DictionaryItem> getAllAsseResult(){
		return SimpleDictinaryManager.get().getItems("asseResult");
	}
	/**
	 * 根据键值获取考核结果
	 * @param key
	 * @return
	 */
	
	public static String getAsseResult(String key){
		return SimpleDictinaryManager.get().getValue("asseResult", key);
	}
	
	
	public static String getAsseResultCode(String name){
		return SimpleDictinaryManager.get().getKey("asseResult", name);
	}
	/**
	 * 职位信息
	 * @return
	 */
	
	public static List<DictionaryItem> getAllPositionName(){
		return SimpleDictinaryManager.get().getItems("positionName");
		
	}
	
	public static String getPositionName(String key){
		return SimpleDictinaryManager.get().getValue("positionName", key);
	}
	
	/**
	 * 岗位类型信息
	 * 
	 */
	
	public static List<DictionaryItem> getAllPositionType(){
		return SimpleDictinaryManager.get().getItems("positionType");
		
	}
	
	public static String getPositionType(String key){
		return SimpleDictinaryManager.get().getValue("positionType", key);
	}
	
	public static String getPositionTypeCode(String name) {
		// TODO Auto-generated method stub
		return SimpleDictinaryManager.get().getKey("positionType", name);
	}
	/**
	 * 岗位级别信息
	 */
	
	public static List<DictionaryItem> getAllPositionLevel(){
		return SimpleDictinaryManager.get().getItems("positionLevel");
		
	}
	
	public static String getPositionLevel(String key){
		return SimpleDictinaryManager.get().getValue("positionLevel", key);
	}
	/**
	 * 根据编码查名称
	 * @param key
	 * @return
	 */
	public static String getEducationName(String key){
		return SimpleDictinaryManager.get().getValue("education", key);
	}
	/**
	 * 获取所有举报途径
	 * @return
	 */
	public static List<DictionaryItem> getAllReportedWay(){
		return SimpleDictinaryManager.get().getItems("reportedWay");
	}
	/**
	 * 根据编码查询举报途径
	 * @param key
	 * @return
	 */
	public static String getReportedWay(String key){
		return SimpleDictinaryManager.get().getValue("reportedWay", key);
	}
	/**
	 * 根据举报途径查询编码
	 * @param name
	 * @return
	 */
	public static String getReportedWayCode(String name){
		return SimpleDictinaryManager.get().getKey("reportedWay", name);
	}
	 /**
	  * 通用方法 ， 获取所有属于此字典的子项
	  * @param dictionary
	  * @return
	  */
	public static List<DictionaryItem> getAllItems(String dictionaryName){
		return SimpleDictinaryManager.get().getItems(dictionaryName);
	}
	/**
	 * 通用方法 ，根据字典名字，编码查询项值
	 * @param key
	 * @return
	 */
	public static String getItem(String dicitionaryName,String key){
		return SimpleDictinaryManager.get().getValue(dicitionaryName, key);
	}
	/**
	 * 通用方法 ，根据字典，项值，查询编码
	 * @param name
	 * @return
	 */
	public static String getItemCode(String dicitionaryName,String name){
		return SimpleDictinaryManager.get().getKey(dicitionaryName, name);
	}
}
