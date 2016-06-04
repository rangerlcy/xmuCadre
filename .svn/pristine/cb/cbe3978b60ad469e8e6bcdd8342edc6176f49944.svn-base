package com.cadre.model.excel;

import java.util.*;

import org.apache.poi.ss.usermodel.Row;

public abstract class AbstractCheckRepeatExcelDataConvertor<T> extends AbstractExcelDataConvertor<T> {
	/**
	 * @ClassName: AbstractCheckRepeatExcelDataConvertor
	 * @Version: 1.0 2014年5月6日 上午11:21:02
	 * @Description: 检查重复的excel数据转换器<br/>
	 * 
	 */
	/**
	 * 重复键与行号记录
	 */
	private Map<String,Integer> successsMap = new HashMap<String, Integer>();
	/**
	 * 根据什么来判断重复
	 * @param row
	 * @return
	 */
	protected abstract String getRepeatKey(Row row);
	
	/**
	 * 检查第一行数据
	 * @param row
	 * @return
	 */
	@Override
	protected boolean check(Row row) {
		//先检查数据的正确性
		DataErrorMsg error = checkDataRow(row);
		if(error != null) {
			getErrorList().add(error);
			return false;
		}
		
		//再检查数据的重复性
		error = checkRepeat(row);
		if(error != null) {
			getErrorList().add(error);
			return false;
		}
		
		return true;
	}
	/**
	 * 检查记录是否重复
	 * @return
	 */
	private DataErrorMsg checkRepeat(Row row){
		String repeatKey = getRepeatKey(row);
		Integer line = checkRepeat(repeatKey);
		if(line != null ) {
			return new DataErrorMsg("与第"+line+"行记录重复", row.getRowNum()+1);
		} else {
			addToSuccess(repeatKey, row.getRowNum()+1);
			return null;
		}
	}
	/**
	 * 根据检查重复
	 * @param number
	 * @return
	 */
	private Integer checkRepeat (String repeatKey) {
		return successsMap.get(repeatKey);
	}
	
	private void addToSuccess(String repeatKey,Integer line) {
		successsMap.put(repeatKey, line);
	}
}
