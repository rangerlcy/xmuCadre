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
import com.cadre.pojo.User;

public class UserConvertor extends AbstractCheckRepeatExcelDataConvertor<User> {
	private Logger logger = LogManager.getLogger(UserConvertor.class);
	private static final String username = "登陆账号";
	private static final String name= "姓名";
	/**
	 * 在数据库中已经存在的用户信息
	 */
	private Map<String,User> userMap;
	/**
	 * 标题行，key标题名,value为标题所在列
	 */
	private Map<String,Integer> TITLE_MAP = initTitleMap();
	
	public UserConvertor(Map<String,User> map){ 
		this.userMap = map;
	}
	
	public UserConvertor(List<User> list) {
		this(toMap(list));
	}
	
	private static Map<String,User> toMap(List<User> list) {
		HashMap<String, User> map = new HashMap<String,User>();
		if(list == null || list.size() == 0) {
			return map;
		}
		for(User user : list) {
			if(user == null ) continue;
			map.put(user.getUserName(), user);
		}
		return map;
	}
	
	
	/**
	 * 初始化标题map，值都为空
	 * @return
	 */
	private static final Map<String,Integer> initTitleMap() {
		Map<String,Integer> titleMap = new HashMap<String, Integer>();
		titleMap.put(username,null);
		titleMap.put(name,null);
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
		return PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(username)));//根据登陆账号来判断是否重复
	}
	/**
	 * 检查数据行是否有错，如果有错，则返回错误的DataErrorMsg对象
	 */
	@Override
	protected DataErrorMsg checkDataRow(Row row) {
		StringBuffer sb = new StringBuffer();
		sb.append(checkCellNotEmpty(row, username));
		sb.append(checkCellNotEmpty(row, name));
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
		User user;
		String yhm = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(username)));
		user = userMap.get(yhm);
		if(user == null) {
			user = new User();
			//用户名
			user.setUserName(yhm);
			//导入的初始密码与登陆账号一致
			user.setPassWord(DigestUtil.encryptPWD(yhm));
		}
		//姓名
		user.setName(PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP.get(name))));
		return user;
	}

}
