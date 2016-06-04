package com.cadre.controller.convertor;

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

import com.cadre.model.excel.AbstractCheckRepeatExcelDataConvertor;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.model.excel.FileCheckerException;
import com.cadre.model.excel.utils.PoiUtil;
import com.cadre.model.utils.DigestUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.User;

public class UserInfoConvertor extends AbstractCheckRepeatExcelDataConvertor<User>{
	private Logger logger = LogManager.getLogger(UserInfoConvertor.class);
	private static final String identityNumber = "证件号码";
	//private static final String identityType = "证件类型";
	private static final String name= "姓名";
	private static final String gender= "性别";
	/**
	 * 在数据库中已经存在的用户信息
	 */
	private Map<String,User> userMap;
	/**
	 * 标题行，key标题名,value为标题所在列
	 */
	private Map<String,Integer> TITLE_MAP = initTitleMap();
	
	public UserInfoConvertor(Map<String,User> map){ 
		this.userMap = map;
	}
	
	public UserInfoConvertor(List<User> list) {
		this(toMap(list));
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
		titleMap.put(identityNumber,null);
		//titleMap.put(identityType,null);
		titleMap.put(name,null);
		titleMap.put(gender,null);
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
		return PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)));//根据身份证号来判断是否重复
	}
	/**
	 * 检查数据行是否有错，如果有错，则返回错误的DataErrorMsg对象
	 */
	@Override
	protected DataErrorMsg checkDataRow(Row row) {
		StringBuffer sb = new StringBuffer();
		sb.append(checkCellNotEmpty(row, identityNumber));
		
		//String cardType = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityType)));
		String cardNum = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)));
		//int columnIndex = TITLE_MAP.get(identityType);
		int numIndex = TITLE_MAP.get(identityNumber);
		/*if(StringUtils.isBlank(cardType)){
			sb.append("第"+(columnIndex + 1) +"列["+identityType+"]不能为空,");
		} else {
			if(ViewFunction.getCardTypeCode(cardType) == null){
				sb.append("第"+(columnIndex + 1)+"列[证件类型]填写错误,");
			}else if("身份证".equals(cardType)){
				if(cardNum.length() != 18){
					sb.append("第"+(numIndex + 1)+"列[证件类型]为身份证的[证件号码]应该为18位,");
				}
			}
		}*/
		sb.append(checkCellNotEmpty(row, name));
		String genderStr = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(gender)));
		genderStr = genderStr.replaceAll("（", "(").replaceAll("）", ")");
		/*if( StringUtils.isNotBlank(genderStr)) {
			sb.append("第"+(TITLE_MAP.get(gender) + 1)+"列[性别]填写错误,");
		}*/
		//检查完毕
		if(StringUtils.isNotBlank(sb.toString())){
			return new DataErrorMsg(sb.toString(), row.getRowNum()+1);
		}
		return null;
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
	 * 将Excel数据转换成User对象
	 */
	@Override
	public User convert(Row row) {
		User User;
		String zjh = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)));
		User = userMap.get(zjh);
		if(User == null) {			//判断要添加的数据在数据库中是否已经存在,为null说明不存在
			User = new User();
			//用户名
			User.setUserName(zjh);
			//密码
			if(zjh.length() > 6){
				User.setPassWord(DigestUtil.encryptPWD(zjh.substring(zjh.length()-6,zjh.length())));//密码为证件号后6位
			}else{ 
				User.setPassWord(DigestUtil.encryptPWD(zjh));
			}
			//证件号码
			User.setIdentifyNum(zjh);
		}/*
		//证件类型
		String cardType = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityType)));
		User.setIdentityTypeLabel(ViewFunction.getCardTypeCode(cardType));*/
		
		//姓名
		User.setName(PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(name))));
		
		String genderStr = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(gender)));
		genderStr = genderStr.replaceAll("（", "(").replaceAll("）", ")");
		if(StringUtils.isNotBlank(gender)){
			User.setGender(genderStr);
		}else {
		    User.setGender("未填写");
		}
		return User;
	}

}
