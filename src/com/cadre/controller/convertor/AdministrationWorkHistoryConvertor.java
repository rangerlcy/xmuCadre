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
import com.cadre.pojo.AdministrationWorkHistory;
import com.cadre.pojo.User;
import com.cadre.service.sys.UserService;

public class AdministrationWorkHistoryConvertor extends AbstractCheckRepeatExcelDataConvertor<AdministrationWorkHistory>{
	@Autowired
	UserService userService;
	
	private Logger logger = LogManager.getLogger(UserInfoConvertor.class);
	
	private static final String name= "姓名";
	private static final String identityNum= "证件号码";
	private static final String beginDay= "起始时间";
	private static final String endDay= "结束时间";
	private static final String units= "任职单位";
	private static final String jobName="职务名称";
	private static final String jobType="单位类型";
	private static final String userLevel="人员级别";
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
	
	public AdministrationWorkHistoryConvertor(List<User> list) {
		this(toMap(list));
	}
	
	public AdministrationWorkHistoryConvertor(Map<String, User> map) {
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
		titleMap.put(units,null);
		titleMap.put(jobName,null);
		titleMap.put(jobType,null);
		titleMap.put(userLevel,null);
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
		String chkCase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(checkCase)));
		String jt = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(jobType)));
		String ul = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(userLevel)));
		String uu=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNum)));
		User ur = userMap.get(uu);
		
		if(ur==null){
			sb.append("查无此人，请添加存在系统中的用户姓名");
		}
	
		if(!StringUtils.isBlank(chkCase)){   
			if(chkCase == null){
				int columnIndex = TITLE_MAP.get(checkCase);
				sb.append("第"+(columnIndex+1)+"列[核对情况]填写错误,");
			}
		}
		
		if(!StringUtils.isBlank(jt)){   		//如果不空，则判断是否符合要求
			if(ViewFunction.getItemCode("institutionType", jt) == null){
				int columnIndex = TITLE_MAP.get(jobType);
				sb.append("第"+(columnIndex+1)+"列[单位类型]填写错误,");
			}
		}
		
		if(!StringUtils.isBlank(ul)){   		//如果不空，则判断是否符合要求
			if(ViewFunction.getItemCode("positionLevel",ul ) == null){
				int columnIndex = TITLE_MAP.get(userLevel);
				sb.append("第"+(columnIndex+1)+"列[人员级别]填写错误,");
			}
		}
		//检查完毕
		if(StringUtils.isNotBlank(sb.toString())){
			return new DataErrorMsg(sb.toString(), row.getRowNum()+1);
		}
		return null;
	}

	/**
	 * 将Excel数据转换成adminstrationHistory对象
	 */
	@Override
	public AdministrationWorkHistory convert(Row row) {
		// TODO Auto-generated method stub
		AdministrationWorkHistory adm = new AdministrationWorkHistory();
		
		String uu=PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(identityNum)));
		User ur = userMap.get(uu);
		adm.setUser(ur);     //设置用户类
		String bs = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(beginDay)));
		String es = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(endDay)));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date begDay = sdf.parse(bs);
			if(begDay!=null){
				adm.setBeginDay(begDay);
			}else {
				adm.setBeginDay(null);
			}
			if (es!=""){
				Date edDay = sdf.parse(es);
				adm.setEndDay(edDay);
			}else {
				adm.setEndDay(null);
			}
		}catch(ParseException e){
			e.printStackTrace();
			System.out.println("日期有误");
		}
		String un = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(units)));
		String jn = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(jobName)));
		String jt = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(jobType)));
		String ul = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(userLevel)));
		String chkCase = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(checkCase)));
		String rm = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(remark))); 
		
		if(StringUtils.isNotBlank(un)){
			adm.setUnits(un);
		}else {
			adm.setUnits(null);
		}
		if(StringUtils.isNotBlank(jn)){
			adm.setJobName(jn);
		}else {
			adm.setJobName(null);
		}
		
		if(StringUtils.isNotBlank(jt) && ViewFunction.getItemCode("institutionType", jt) != null){
			adm.setJobType(ViewFunction.getItemCode("institutionType", jt));
		}else {
			adm.setJobType(null);
		}
		if(StringUtils.isNotBlank(ul) && ViewFunction.getItemCode("positionLevel", ul) != null){
			adm.setUserLevel(ViewFunction.getItemCode("positionLevel", ul));
		}else {
			adm.setUserLevel(null);
		}
		if(StringUtils.isNotBlank(chkCase)){
			adm.setCheckCase(chkCase);
		}else {
			adm.setCheckCase(null);
		}
		if(StringUtils.isNotBlank(rm)){
			adm.setRemark(rm);
		}else {
			adm.setRemark(null);
		}
		return adm;
	}
}
