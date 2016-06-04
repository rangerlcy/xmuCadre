package com.cadre.model.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.cadre.model.excel.utils.PoiUtil;
import com.cadre.model.utils.StringUtil;


public abstract class AbstractExcelDataConvertor<T> {
	/**
	 * 错误信息列表
	 */
	private List<DataErrorMsg> errorList = new ArrayList<DataErrorMsg>();
	public List<DataErrorMsg> getErrorList() {
		return errorList;
	}
	/**
	 * 将行转换成java对象
	 * @param row
	 * @return
	 */
	public abstract T convert(Row row);
	/**
	 * 检查读取出来的数据的正确性
	 * @param t
	 * @return 如果数据完成正确，返回null
	 */
	protected abstract DataErrorMsg checkDataRow(Row row);
	/**
	 * 读标题行
	 * @param headRow
	 */
	public void readTitleRow(Row headRow) {}
	
	/**
	 * 批量信息转换
	 * @param it
	 * @return
	 */
	public List<T> convert(Iterator<Row> it){
		List<T> resultList= new ArrayList<T>();
		Row row;
		T t;
		while(it.hasNext()){
			row = it.next();
			if(isTitle(row) || isEmptyRow(row)) continue;
			if(!check(row)) continue;
			
			t = convert(row);
			if(t != null){
				resultList.add(t);
			}
		}
		return resultList;
	}
	
	/**
	 * 判断是否标题行
	 * @param row
	 * @return
	 */
	protected boolean isTitle(Row row) {
		return 0 == row.getRowNum();
	}
	/**
	 * 判断是否是空行
	 * @param row
	 * @return
	 */
	protected boolean isEmptyRow(Row row) {
		if(row == null) return true;
		int cellSize = row.getLastCellNum();
		for(int i=0;i<cellSize;i++) {
			if(StringUtils.isNotBlank(
					PoiUtil.getText(row.getCell(i)))){
				return false;
			}
		}
		return true;
	}
	/**
	 * 检查第一行数据
	 * @param row
	 * @return
	 */
	protected boolean check(Row row) {
		DataErrorMsg error = checkDataRow(row);
		if(error != null) {
			errorList.add(error);
		}
		return error == null;
	}
	/**
	 * 检查标题行
	 * @param headRow
	 * @throws FileCheckerException
	 */
	public void checkTitleRow(Row headRow) throws FileCheckerException {
		return;
	}
	/**
	 * 检查标题行
	 * @param headRow
	 * @param coloumNum
	 * @param titles
	 * @throws FileCheckerException 
	 */
	protected final void checkTitleRow(Row headRow,String[] titles) throws FileCheckerException {
		if(headRow == null) {
			throw new FileCheckerException("标题行为空");
		}
		
		if(headRow.getLastCellNum() != titles.length){
			throw new FileCheckerException("标题行不正确(列数不足或多出),正确 的列数应该为"+titles.length+"列");
		}
		
		for(short i = 0; i<titles.length;i++) {
			Cell cell = headRow.getCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if(!titles[i].equalsIgnoreCase(StringUtil.trimAllWhitespace(cell.getStringCellValue()))){
				throw new FileCheckerException("标题行"+i+1+"列名称不正确");
			}
		}
	}
}
