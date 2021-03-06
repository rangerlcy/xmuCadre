package com.cadre.controller.convertor;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.cadre.model.excel.AbstractCheckRepeatExcelDataConvertor;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.model.excel.FileCheckerException;
import com.cadre.model.excel.utils.PoiUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.Assess;
import com.cadre.pojo.User;

public class AsseDelConvertor extends AbstractCheckRepeatExcelDataConvertor<Assess>{
	private static final String identityNumber = "证件号码";
	//private static final String identityType = "证件类型";
	private static final String name= "姓名";
	private static final String year= "考核年份";
	/**
	 * 在数据库中已经存在的用户信息
	 */
	private Map<String, Assess> asseMap;  
	
	private Map<String,User> userMap;
	/**
	 * 标题行，key标题名,value为标题所在列
	 */
	private Map<String,Integer> TITLE_MAP = initTitleMap();

	
	public AsseDelConvertor(Map<String,Assess> asseMap,Map<String, User> userMap){ 
		this.asseMap = asseMap;
		this.userMap = userMap;
	}
	
	public AsseDelConvertor(List<Assess> assList,List<User> userList) {
		this(toAsseMap(assList),toUserMap(userList));
	}
	
	private static Map<String,User> toUserMap(List<User> list) {
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
	
	private static Map<String, Assess> toAsseMap(List<Assess> list){
		HashMap<String, Assess> map = new HashMap<String, Assess>();
		if (list == null || list.size() == 0) {
			return map;
		}
		for (Assess assess : list){
			if (assess == null) continue;
			map.put(assess.getUser().getIdentifyNum()+assess.getYear(), assess);
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
		titleMap.put(year,null);
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
		return PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)))+PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(year)));//根据身份证号+年份来判断是否重复
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
			}*/
			if (!userMap.containsKey(cardNum)){//无此用户
				sb.append("第"+(numIndex + 1)+"[证件号码]的用户不存在,不能删除,");
			}else if (!asseMap.containsKey(cardNum+PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(year))))){
				sb.append("第"+(numIndex + 1)+"记录不存在");
			}
		//}
		
		
		sb.append(checkCellNotEmpty(row, name));

		String yearString = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(year)));
		yearString = yearString.replaceAll("（", "(").replaceAll("）", ")");
		if (StringUtils.isBlank(yearString)){
			sb.append("第"+(TITLE_MAP.get(year) + 1)+"列[考核年份]不能为空,");
		}else{
			Integer yearInteger = Integer.valueOf(yearString);
			if (yearInteger == null || yearInteger <1900 || yearInteger > 2099){
				sb.append("第"+(TITLE_MAP.get(year) + 1)+"列[考核年份]填写错误,");
			}
		}
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
	 * 将Excel数据转换成Assess对象 
	 */
	@Override
	public Assess convert(Row row) {
		Assess assess;
		String identifyNum = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)));
		Integer yearInt = Integer.valueOf(PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(year))));
		String key = identifyNum + yearInt.toString();
		assess = asseMap.get(key);
		if(assess == null) {
			assess = new Assess();
			assess.setUser(userMap.get(identifyNum));
			assess.setYear(yearInt);
			
		}
		assess.setDelFlag(1);
		return assess;
	}

}