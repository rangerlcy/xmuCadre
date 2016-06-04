package com.cadre.controller.convertor;



import java.util.Collection;
import java.util.Date;
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
import com.cadre.model.utils.DateUtil;
import com.cadre.model.utils.StringUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.Organization;
import com.cadre.pojo.Position;


public class PositionConvertor extends AbstractCheckRepeatExcelDataConvertor<Position>{
	private static final String academy = "学院(研究院)/部处";
	private static final String department = "系所/科室";
	private static final String positionName= "职务名称";
	private static final String positionType= "岗位类型";
	private static final String positionLevel = "岗位级别";

	/**
	 * 在数据库中已经存在的用户信息
	 */
	private Map<String, Position> positionMap;  
	
	private Map<String,Organization> orgMap;
	
	private HashMap<String, String> orgValueMap;
//	private Map<String,Organization> orgValueMap;
	/**
	 * 标题行，key标题名,value为标题所在列
	 */
	private Map<String,Integer> TITLE_MAP = initTitleMap();


	
	public PositionConvertor(Map<String, Position> positionMap,Map<String,Organization> orgMap){ 
		this.positionMap = positionMap;
		this.orgMap = orgMap;
		this.orgValueMap = new HashMap<String, String>();
		Organization org = null;
		Collection<Organization> c = orgMap.values();
		Iterator<Organization> iterator = c.iterator();
		for (; iterator.hasNext();) {
			org = iterator.next();
			orgValueMap.put(org.getCode(),org.getName());
		}
		iterator = null;
		c = null;
		org= null;
	}
	
	
	
	public PositionConvertor(List<Position> positionList,List<Organization> orgList) {
		this(toPositionMap(positionList),toOrgMap(orgList));
	}
	
	private static Map<String,Organization> toOrgMap(List<Organization> list) {
		Map<String, Organization> map = new HashMap<String, Organization>();
		if(list == null || list.size() == 0) {
			return map;
		}
		for(Organization org : list) {
			if(org == null ) continue;
			map.put(org.getCode(), org);
		}
		return map;
	}
	
	private static Map<String, Position> toPositionMap(List<Position> list){
		HashMap<String, Position> map = new HashMap<String, Position>();
		if (list == null || list.size() == 0) {
			return map;
		}
		for (Position position : list){
			if (position == null) continue;
			map.put(position.getDepartment()+position.getPositionName(), position);
		}
		return map;
	}
	
	/**
	 * 初始化标题map，值都为空
	 * @return
	 */
	private static final Map<String,Integer> initTitleMap() {
		Map<String,Integer> titleMap = new HashMap<String, Integer>();
		titleMap.put(academy,null);
		titleMap.put(department,null);
		titleMap.put(positionName,null);
		titleMap.put(positionType,null);
		titleMap.put(positionLevel, null);
		titleMap.put(positionLevel, null);

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
		return PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(department)))+PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(positionName)));//根据岗位科室名称来判断是否重复
	}
	/**
	 * 检查数据行是否有错，如果有错，则返回错误的DataErrorMsg对象
	 */
	@Override
	protected DataErrorMsg checkDataRow(Row row) {
		StringBuffer sb = new StringBuffer();
		
		
		String name = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(positionName)));
		String type = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(positionType)));
		String level = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(positionLevel)));
		String acad = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(academy)));
		String dept = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(department)));
		Collection<Organization> c = orgMap.values();
		Iterator<Organization> iterator = c.iterator();
		Organization organization = null;
		
		sb.append(checkCellNotEmpty(row,department));
		sb.append(checkCellNotEmpty(row,positionName));
		
		if (positionMap.containsKey(dept+name)){
			sb.append("岗位已经存在,");
		}
		if(StringUtils.isBlank(acad)){
			sb.append("第"+(TITLE_MAP.get(academy) + 1) +"列["+academy+"]不能为空,");
		}else{
			if (!orgValueMap.containsValue(acad)){
				sb.append("第"+(TITLE_MAP.get(academy) + 1) +"列["+academy+"]填写出错,无此组织机构,");
			}else {
				if (StringUtils.isBlank(dept)){
					sb.append("第"+(TITLE_MAP.get(department) + 1) +"列["+department+"]不能为空,");
				}else {
					if (!orgValueMap.containsValue(dept)){
						sb.append("第"+(TITLE_MAP.get(department) + 1) +"列["+department+"]填写出错,无此组织机构,");
					}else {
						boolean flag = false;
						while(iterator.hasNext()){
							
							organization = iterator.next();
							if (organization.getName().equals(dept) && orgValueMap.get(organization.getParentCode()).equals(acad)){
								flag = true;
								break;
							}
						}
						if (!flag){
							sb.append("第"+(TITLE_MAP.get(department) + 1) +"列["+department+"]填写出错,组织机构从属关系出错,");
						}
					}
				}
			}
		}
		
		if (StringUtil.isBlank(level)){
			sb.append("第"+(TITLE_MAP.get(positionLevel) + 1) +"列["+positionLevel+"]填写出错,岗位级别不能为空,");
		}else if (ViewFunction.getPositionLevel(level) == null) {
			sb.append("第"+(TITLE_MAP.get(positionLevel) + 1) +"列["+positionLevel+"]填写出错,此岗位级别不存在,");
		}
		
		if (StringUtil.isBlank(type)){
			sb.append("第"+(TITLE_MAP.get(positionType) + 1) +"列["+positionType+"]填写出错,岗位类型不能为空,");
		}else if(ViewFunction.getPositionTypeCode(type) == null){
			sb.append("第"+(TITLE_MAP.get(positionType) + 1) +"列["+positionType+"]填写出错,此岗位类型不存在,");
		}
		
		if (StringUtil.isBlank(name)){
			sb.append("第"+(TITLE_MAP.get(positionName) + 1) +"列["+positionName+"]填写出错,职务不能为空,");
		}else if(ViewFunction.getPositionName(name) == null){
			sb.append("第"+(TITLE_MAP.get(positionName) + 1) +"列["+positionName+"]填写出错,此职务不存在,");
		}
		
		
		c = null;
		iterator = null;
		organization = null;
		//检查完毕
		if(StringUtils.isNotBlank(sb.toString())){
			return  new DataErrorMsg(sb.toString(), row.getRowNum()+1);
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
	 * 将Excel数据转换成Position对象 
	 */
	@Override
	public Position convert(Row row) {
		Position position;
		position = new Position();
		String name = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(positionName)));
		String type = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(positionType)));
		String level = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(positionLevel)));
		String acad = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(academy)));
		String dept = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(department)));
		
		
		position.setAcademy(getOrgCode(acad,1));
		position.setDelFlag(1);
		position.setDepartment(getOrgCode(dept, 2));
		position.setPositionDay(DateUtil.currentTime().toDate());
		position.setPositionLevel(level);
		position.setPositionName(name);
		position.setPositionType(ViewFunction.getPositionTypeCode(type));
		return position;
	}
	
	private String getOrgCode(String name,Integer type){
		if (name == null || orgValueMap == null || type == null){
			return null;
		}	
		Collection<Organization> c = orgMap.values();
		Iterator<Organization> iterator = c.iterator();
		Organization org = null;
		while (iterator.hasNext()){
			org = iterator.next();
			if (type.equals(1) && org.getName().equals(name) && org.getCode().length() == 4 ){
				return org.getCode();
			}
			if (type.equals(2) && org.getName().equals(name) && org.getCode().length() == 6){
				return org.getCode();
			}
		}
		return null;
	}

}