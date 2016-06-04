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
//public class SecondmentConvertor extends AbstractCheckRepeatExcelDataConvertor<Secondment>{
//	private Logger logger = LogManager.getLogger(SecondmentConvertor.class);
//	private static final String postingNumber = "发文文号";
//	private static final String postingTitle = "发文名称";
//	private static final String postingUnit= "发文单位";
//	private static final String year= "挂职项目名称";
//	private static final String assessmentResult = "挂职开始时间";
//	private static final String assessmentResult = "挂职结束时间";
//	private static final String assessmentResult = "挂职人员要求";
//	/**
//	 * 在数据库中已经存在的用户信息
//	 */
//	private Map<String, Secondment> asseMap;  
//	
//	private Map<String,User> userMap;
//	/**
//	 * 标题行，key标题名,value为标题所在列
//	 */
//	private Map<String,Integer> TITLE_MAP = initTitleMap();
//
//	
//	public  SecConvertor(Map<String,Secondment> secMap,Map<String, User> userMap){ 
//		this.secMap = secMap;
//		this.userMap = userMap;
//	}
//	
//	public SecConvertor(List<Secondment> secList,List<User> userList) {
//		this(toSecMap(secList),toUserMap(userList));
//	}
//	
//	private static Map<String,User> toUserMap(List<User> list) {
//		HashMap<String, User> map = new HashMap<String,User>();
//		if(list == null || list.size() == 0) {
//			return map;
//		}
//		for(User user : list) {
//			if(user == null ) continue;
//			map.put(user.getIdentifyNum(), user);
//		}
//		return map;
//	}
//	
//	private static Map<String, Secondment> toAsseMap(List<Secondment> list){
//		HashMap<String, Secondment> map = new HashMap<String, Secondment>();
//		if (list == null || list.size() == 0) {
//			return map;
//		}
//		for (Secondment secondment : list){
//			if (secondment == null) continue;
//			map.put(secondment.getUser().getIdentifyNum()+secondment.getYear(), secondment);
//		}
//		return map;
//	}
//	
//	/**
//	 * 初始化标题map，值都为空
//	 * @return
//	 */
//	private static final Map<String,Integer> initTitleMap() {
//		Map<String,Integer> titleMap = new HashMap<String, Integer>();
//		titleMap.put(identityNumber,null);
//		titleMap.put(identityType,null);
//		titleMap.put(name,null);
//		titleMap.put(year,null);
//		titleMap.put(assessmentResult, null);
//		return titleMap;
//	}
//	
//	/**
//	 *重写readTitleRow函数，读取标题行，用来确认标题对应的列数
//	 */
//	@Override
//	public void readTitleRow(Row headRow) {
//		Iterator<Cell> cellIterator = headRow.cellIterator();
//		Cell cell = null;
//		String title;
//		while(cellIterator.hasNext()) {
//			cell = cellIterator.next();
//			if(cell != null) {
//				title = PoiUtil.getTrim2EmptyText(cell);
//				if(TITLE_MAP.containsKey(title)) {
//					TITLE_MAP.put(title, cell.getColumnIndex());
//				}
//			}
//		}
//	}
//	
//	/**
//	 * 检查文件第一行（excel标题行）
//	 * @param headRow
//	 * @return
//	 */
//	@Override
//	public void checkTitleRow(Row headRow) throws FileCheckerException {
//		Set<Entry<String,Integer>> entrySet = TITLE_MAP.entrySet();
//		StringBuffer sb = new StringBuffer();
//		for(Entry<String,Integer> e : entrySet) {
//			if(e.getValue() == null) {
//				sb.append(",不存在["+e.getKey()+"]列");
//			}
//		}
//		if(sb.length() > 0) {
//			throw new FileCheckerException(sb.substring(1));
//		}
//	}
//	
//	@Override
//	protected String getRepeatKey(Row row) {
//		return PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)))+PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(year)));//根据身份证号+年份来判断是否重复
//	}
//	/**
//	 * 检查数据行是否有错，如果有错，则返回错误的DataErrorMsg对象
//	 */
//	@Override
//	protected DataErrorMsg checkDataRow(Row row) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(checkCellNotEmpty(row, identityNumber));
//		
//		String cardType = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityType)));
//		String cardNum = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)));
//		int columnIndex = TITLE_MAP.get(identityType);
//		int numIndex = TITLE_MAP.get(identityNumber);
//		if(StringUtils.isBlank(cardType)){
//			sb.append("第"+(columnIndex + 1) +"列["+identityType+"]不能为空,");
//		} else {
//			if(ViewFunction.getCardTypeCode(cardType) == null){
//				sb.append("第"+(columnIndex + 1)+"列[证件类型]填写错误,");
//			}else if("身份证".equals(cardType)){
//				if(cardNum.length() != 18){
//					sb.append("第"+(numIndex + 1)+"列[证件类型]为身份证的[证件号码]应该为18位,");
//				}
//			}
//			if (!userMap.containsKey(cardNum)){//无此用户
//				sb.append("第"+(numIndex + 1)+"[证件号码]的用户不存在,不能参加考核,");
//			}
//		}
//		
//		
//		sb.append(checkCellNotEmpty(row, name));
//		String resultStr = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(assessmentResult)));
//		resultStr = resultStr.replaceAll("（", "(").replaceAll("）", ")");
//		if( StringUtils.isNotBlank(resultStr)
//				&& ViewFunction.getAsseResultCode(resultStr) == null) {
//			sb.append("第"+(TITLE_MAP.get(assessmentResult) + 1)+"列[考核结果]填写错误,");
//		}else if (StringUtils.isBlank(resultStr)){
//			sb.append("第"+(TITLE_MAP.get(assessmentResult) + 1)+"列[考核结果]不能为空,");
//		}
//		String yearString = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(year)));
//		yearString = yearString.replaceAll("（", "(").replaceAll("）", ")");
//		if (StringUtils.isBlank(yearString)){
//			sb.append("第"+(TITLE_MAP.get(year) + 1)+"列[考核年份]不能为空,");
//		}else{
//			Integer yearInteger = Integer.valueOf(yearString);
//			if (yearInteger == null || yearInteger <1900 || yearInteger > 2099){
//				sb.append("第"+(TITLE_MAP.get(year) + 1)+"列[考核年份]填写错误,");
//			}
//		}
//		//检查完毕
//		if(StringUtils.isNotBlank(sb.toString())){
//			return new DataErrorMsg(sb.toString(), row.getRowNum()+1);
//		}
//		return null;
//	}
//	
//	/**
//	 * 检查单元格非空
//	 * @param row
//	 * @param cellName
//	 * @return
//	 */
//	private String checkCellNotEmpty(Row row,String cellName){
//		int columnIndex = TITLE_MAP.get(cellName);
//		Cell cell = row.getCell(columnIndex);
//		String content = PoiUtil.getTrim2EmptyText(cell);
//		if(StringUtils.isBlank(content)){
//			return "第"+(columnIndex + 1) +"列["+cellName+"]不能为空,";
//		} else {
//			return "";
//		}
//	}
//	
//	/**
//	 * 将Excel数据转换成secondment对象 
//	 */
//	@Override
//	public Secondment convert(Row row) {
//		Secondment secondment;
//		String identifyNum = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNumber)));
//		Integer yearInt = Integer.valueOf(PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(year))));
//		String resultString = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(assessmentResult)));
//		String key = identifyNum + yearInt.toString();
//		secondment = asseMap.get(key);
//		if(secondment == null) {
//			secondment = new Secondment();
//			secondment.setUser(userMap.get(identifyNum));
//			secondment.setYear(yearInt);
//			
//		}
//		secondment.setsecondmentmentResult(resultString);
//		secondment.setDelFlag(1);
//		return secondment;
//	}

