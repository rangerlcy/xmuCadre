package com.cadre.controller.convertor;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;

import com.cadre.model.excel.AbstractCheckRepeatExcelDataConvertor;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.model.excel.FileCheckerException;
import com.cadre.model.excel.utils.PoiUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.Skill;
import com.cadre.pojo.User;
import com.cadre.service.sys.UserService;

public class SkillConvertor extends AbstractCheckRepeatExcelDataConvertor<Skill>{
	@Autowired
	UserService userService;
	
	private Logger logger = LogManager.getLogger(UserInfoConvertor.class);
	
	private static final String name= "姓名";
	private static final String identityNum= "证件号码";
	private static final String beginDay= "起始时间";
	private static final String endDay= "结束时间";
	private static final String employmentLevel= "聘任级别";
	private static final String skillName="专业技术职务";
	private static final String checkCase="核对情况";
	private static final String remark="备注";
	/**
	 * 在数据库中已经存在的用户信息
	 */
	private Map<String,User> userMap;
	/**
	 * 标题行，key标题名,value为标题所在列
	 */
	private Map<String,Integer> TITLE_MAP = initTitleMap();
	
	public SkillConvertor(List<User> list) {
		this(toMap(list));
	}
	
	public SkillConvertor(Map<String, User> map) {
		// TODO Auto-generated constructor stub
		this.userMap = map;
	}

	private static Map<String,User> toMap(List<User> list) {
		HashMap<String, User> map = new HashMap<String,User>();
		if(list == null || list.size() == 0) {
			return map;
		}
		for(User user : list) {
			if(user == null ) continue;
			map.put(user.getIdentifyNum(), user);
		}
		return map;
	}
	
	/**
	 * 初始化标题map，值都为空
	 * @return
	 */
	private static final Map<String,Integer> initTitleMap() {
		Map<String,Integer> titleMap = new HashMap<String, Integer>();
		titleMap.put(name,null);
		titleMap.put(identityNum,null);
		titleMap.put(beginDay,null);
		titleMap.put(endDay,null);
		titleMap.put(employmentLevel,null);
		titleMap.put(skillName,null);
		titleMap.put(checkCase,null);
		titleMap.put(remark,null);
		return titleMap;
	}
	
	/**
	 *重写readTitleRow函数，读取标题行，用来确认标题对应的列数
	 */
	@Override
	public void readTitleRow(Row headRow) {
		Iterator<Cell> cellIterator = headRow.cellIterator();
		Cell cell = null;
		String title;
		while(cellIterator.hasNext()) {
			cell = cellIterator.next();
			if(cell != null) {
				title = PoiUtil.getTrim2EmptyText(cell);
				if(TITLE_MAP.containsKey(title)) {
					TITLE_MAP.put(title, cell.getColumnIndex());
				}
			}
		}
	}
	
	/**
	 * 检查文件第一行（excel标题行）
	 * @param headRow
	 * @return
	 */
	@Override
	public void checkTitleRow(Row headRow) throws FileCheckerException {
		Set<Entry<String,Integer>> entrySet = TITLE_MAP.entrySet();
		StringBuffer sb = new StringBuffer();
		for(Entry<String,Integer> e : entrySet) {
			if(e.getValue() == null) {
				sb.append(",不存在["+e.getKey()+"]列");
			}
		}
		if(sb.length() > 0) {
			throw new FileCheckerException(sb.substring(1));
		}
	}

	@Override
	protected String getRepeatKey(Row row) {
		// TODO Auto-generated method stub
		return "true";                  //经历允许重复，默认有重复的经历
	}
		
	/**
	 * 检查单元格非空
	 * @param row
	 * @param cellName
	 * @return
	 */
	private String checkCellNotEmpty(Row row,String cellName){
		int columnIndex = TITLE_MAP.get(cellName);
		Cell cell = row.getCell(columnIndex);
		String content = PoiUtil.getTrim2EmptyText(cell);
		if(StringUtils.isBlank(content)){
			return "第"+(columnIndex + 1) +"列["+cellName+"]不能为空,";
		} else {
			return "";
		}
	}
	
	/**
	 * 检查数据行是否有错，如果有错，则返回错误的DataErrorMsg对象
	 */
	@Override
	protected DataErrorMsg checkDataRow(Row row) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(checkCellNotEmpty(row, name));         //姓名不允许为空，其余属性可以为空
		sb.append(checkCellNotEmpty(row, identityNum));     //证件号码不允许为空
		Date begDay = PoiUtil.getDate(row.getCell(TITLE_MAP.get(beginDay)));
		Date edDay = PoiUtil.getDate(row.getCell(TITLE_MAP.get(endDay)));
		
		String el = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(employmentLevel)));
		String sn = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(skillName)));
		String chkCase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(checkCase)));
		String uu=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNum)));
		User ur = userMap.get(uu);
		
		if(ur==null){
			sb.append("查无此人，请添加存在系统中的用户姓名");
		}
		
		if(!StringUtils.isBlank(el)){   		//如果不空，则判断是否符合要求
			
			if(ViewFunction.getItemCode("employmentLevel", el) == null){
				int columnIndex = TITLE_MAP.get(employmentLevel);
				sb.append("第"+(columnIndex+1)+"列[学习形式]填写错误,");
			}
		}
		
		if(!StringUtils.isBlank(chkCase)){   
			if(chkCase == null){
				int columnIndex = TITLE_MAP.get(checkCase);
				sb.append("第"+(columnIndex+1)+"列[核对情况]填写错误,");
			}
		}
		
		//检查完毕
		if(StringUtils.isNotBlank(sb.toString())){
			return new DataErrorMsg(sb.toString(), row.getRowNum()+1);
		}
		return null;
	}

	/**
	 * 将Excel数据转换成SkillHistory对象
	 */
	@Override
	public Skill convert(Row row) {
		// TODO Auto-generated method stub
		Skill skill = new Skill();
		
		String uu=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNum)));
		User ur = userMap.get(uu);
		skill.setUser(ur);     //设置用户类
		
		Date begDay = PoiUtil.getDate(row.getCell(TITLE_MAP.get(beginDay)));
		Date edDay = PoiUtil.getDate(row.getCell(TITLE_MAP.get(endDay)));
		String el = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(employmentLevel)));
		String sn = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(skillName)));
		String chkCase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(checkCase)));
		String rek = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(remark)));
		if(begDay!=null){
			skill.setBeginDay(begDay);
		}else {
			skill.setBeginDay(null);
		}
		
		if(edDay!=null){
			skill.setEndDay(edDay);
		}else {
			skill.setEndDay(null);
		}
		
		if(StringUtils.isNotBlank(el) && ViewFunction.getItemCode("employmentLevel",el) != null){
			skill.setEmploymentLevel(ViewFunction.getItemCode("employmentLevel",el));
		}else {
			skill.setEmploymentLevel("未填写");
		}
		if(StringUtils.isNotBlank(sn)){
			skill.setSkillName(sn);
		}else {
			skill.setSkillName("未填写");
		}	
		if(StringUtils.isNotBlank(chkCase)){
			skill.setCheckCase(chkCase);
		}else {
			skill.setCheckCase(null);
		}
		
		if(StringUtils.isNotBlank(rek)){
			skill.setRemark(rek);
		}else {
			skill.setRemark(null);
		}
		return skill;
	}
}
