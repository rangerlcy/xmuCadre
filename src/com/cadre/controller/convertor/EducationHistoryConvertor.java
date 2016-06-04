package com.cadre.controller.convertor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cadre.pojo.User;
import com.cadre.pojo.EducationHistory;
import com.cadre.service.sys.UserService;

public class EducationHistoryConvertor extends AbstractCheckRepeatExcelDataConvertor<EducationHistory>{
	@Autowired
	UserService userService;
	
	private Logger logger = LogManager.getLogger(UserInfoConvertor.class);
	
	private static final String name= "姓名";
	private static final String identityNum= "证件号码";
	private static final String beginDay= "起始时间";
	private static final String endDay= "结束时间";
	private static final String school= "学校";
	private static final String learningForm="学习形式";
	private static final String learningPhase="学习阶段";
	private static final String learningState="学习状态";
	private static final String degree="学位";
	private static final String degreeType="学位类型";
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
	
	public EducationHistoryConvertor(List<User> list) {
		this(toMap(list));
	}
	
	public EducationHistoryConvertor(Map<String, User> map) {
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
		titleMap.put(school,null);
		titleMap.put(learningForm,null);
		titleMap.put(learningPhase,null);
		titleMap.put(learningState,null);
		titleMap.put(degree,null);
		titleMap.put(degreeType,null);
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
	protected String getRepeatKey(Row row) {		//根据姓名、身份证、开始时间判断是否重复
		// TODO Auto-generated method stub
		String mark="";
		mark=mark+PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(name)));
		mark+=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNum)));
		mark+=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(beginDay)));
		System.out.println(mark);
		return mark;                  
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
		sb.append(checkCellNotEmpty(row, name));         //姓名不允许为空
		sb.append(checkCellNotEmpty(row, identityNum));     //证件号码不允许为空
		sb.append(checkCellNotEmpty(row, beginDay));	//起始时间不允许为空
		
		String leForm = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(learningForm)));
		String leState = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(learningState)));
		String lePhase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(learningPhase)));
		String de = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(degree)));
		String deType = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(degreeType)));
		String chkCase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(checkCase)));
		
		String uu=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNum)));
		User ur = userMap.get(uu);
		
		if(ur==null){
			sb.append("查无此人，请添加存在系统中的用户姓名");
		}
		
		if(!StringUtils.isBlank(leForm)){   		//如果不空，则判断是否符合要求
			if(leForm == null){
				int columnIndex = TITLE_MAP.get(learningForm);
				sb.append("第"+(columnIndex+1)+"列[学习形式]填写错误,");
			}
		}
		if(!StringUtils.isBlank(leState)){   
			if(leState == null){
				int columnIndex = TITLE_MAP.get(learningState);
				sb.append("第"+(columnIndex+1)+"列[学习状态]填写错误,");
			}
		}
		if(!StringUtils.isBlank(lePhase)){   
			if(lePhase == null){
				int columnIndex = TITLE_MAP.get(learningPhase);
				sb.append("第"+(columnIndex+1)+"列[学习阶段]填写错误,");
			}
		}
		if(!StringUtils.isBlank(de)){   
			if(de == null){
				int columnIndex = TITLE_MAP.get(degree);
				sb.append("第"+(columnIndex+1)+"列[学位]填写错误,");
			}
		}
		if(!StringUtils.isBlank(chkCase)){   
			if(deType == null){
				int columnIndex = TITLE_MAP.get(degreeType);
				sb.append("第"+(columnIndex+1)+"列[学位类型]填写错误,");
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
	 * 将Excel数据转换成EducationHistory对象
	 */
	@Override
	public EducationHistory convert(Row row) {
		// TODO Auto-generated method stub
		EducationHistory edu = new EducationHistory();
		
		String uu=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNum)));
		User ur = userMap.get(uu);
		edu.setUser(ur);     //设置用户类
		
		String bs= PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(beginDay)));
		String es= PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(endDay)));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date begDay = sdf.parse(bs);
			if(begDay!=null){
				edu.setBeginDay(begDay);
			}else {
				edu.setBeginDay(null);
			}
			if (es!=""){
				Date edDay = sdf.parse(es);
				edu.setEndDay(edDay);
			}else {
				edu.setEndDay(null);
			}
		}catch(ParseException e){
			e.printStackTrace();
			System.out.println("日期有误");
		}
		String shl = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(school)));
		String leForm = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(learningForm)));
		String leState = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(learningState)));
		String lePhase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(learningPhase)));
		String de = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(degree)));
		String deType = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(degreeType)));
		String chkCase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(checkCase)));
		String rek = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(remark)));
				
		if(StringUtils.isNotBlank(shl)){
			edu.setSchool(shl);
		}else {
			edu.setSchool("未填写");
		}
		
		if(StringUtils.isNotBlank(leForm)){
			edu.setLearningForm(leForm);
		}else {
			edu.setLearningForm(null);
		}
		
		if(StringUtils.isNotBlank(leState) ){
			edu.setLearningState(leState);
		}else {
			edu.setLearningState(null);
		}
		
		if(StringUtils.isNotBlank(lePhase) ){
			edu.setLearningPhase(lePhase);
		}else {
			edu.setLearningPhase(null);
		}
		
		if(StringUtils.isNotBlank(de) ){
			edu.setDegree(de);
		}else {
			edu.setDegree(null);
		}
		
		if(StringUtils.isNotBlank(deType)){
			edu.setDegreeType(deType);
		}else {
			edu.setDegreeType(null);
		}
		
		if(StringUtils.isNotBlank(chkCase) ){
			edu.setCheckCase(chkCase);
		}else {
			edu.setCheckCase(null);
		}
		
		if(StringUtils.isNotBlank(rek)){
			edu.setRemark(rek);
		}else {
			edu.setRemark(null);
		}
		return edu;
	}
}
