/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.model.excel.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cadre.model.utils.DataIterator;
import com.cadre.model.utils.DateUtil;
import com.cadre.model.utils.StringUtil;
/**
 * @ClassName: PoiUtil
 * @Version: 1.0 2014年4月2日 下午3:29:08
 * @Description: <br/>
 * 
 */
public class PoiUtil {
	
	/**
	 * 创建高亮的文本
	 * @param content 内容
	 * @param hightLightCotent 需要高亮的子串
	 * @param highLightFont 高亮字体样式
	 * @return
	 */
	public static RichTextString createHighLightText(String content,String hightLightCotent,Font highLightFont){
		HSSFRichTextString richText = new HSSFRichTextString(content);
		
		//需要高亮的内容不为空时设置高亮子串
		if(!StringUtils.isBlank(hightLightCotent)){
			
			int[] indexOfArr = StringUtil.indexOfArr(content,hightLightCotent); //得到高亮内容的位置
			int length = hightLightCotent.length(); // 高亮内容的长度
			
			if(indexOfArr != null) {
				for(int index : indexOfArr) {
					richText.applyFont(index,index + length,highLightFont);
				}
			}
		}
		
		return richText;
	}
	
	/**
	 * 创建标题行
	 * @param titleRow
	 * @param titles
	 */
	public static void fillRow(Row titleRow,String contents) {
		if(StringUtils.isEmpty(contents)) return;
		
		fillRow(titleRow,contents.split(","));
	}
	
	/**
	 * 创建标题行
	 * @param row
	 * @param contents
	 */
	public static void fillRow(Row row,String...contents) {
		if(row == null) {
			throw new IllegalArgumentException("行row不能为空");
		}
		if(contents == null ) return;
		
		for(int i=0;i< contents.length; i++) {
			row.createCell(i).setCellValue(contents[i]==null ? "" : contents[i]);
		}
	}
	
	/**
	 * 设置Excel一行的字体
	 * @param row
	 * @param font
	 */
	public static void setFont(Row row,Font font,Workbook wb) {
		if(row == null || wb == null || wb == null) return;
		
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle style = wb.createCellStyle();
		style.setFont(font );
		
		Iterator<Cell> it = row.cellIterator();
		Cell cell;
		while(it.hasNext()){
			cell = it.next();
			cell.setCellStyle(style);
		}
	}
	
	/**
	 * 读取Excel文件，返回文件中的所有行
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static Iterator<Row> readExcel2003(InputStream inputStream) throws IOException {
		Workbook workBook = new HSSFWorkbook(inputStream);
		Sheet sheet = workBook.getSheetAt(0);
		return sheet.rowIterator();
	}
	
	public static Iterator<Row> readExcel2007To2010(InputStream inputStream) throws IOException {
		Workbook workBook = new XSSFWorkbook(inputStream);
		Sheet sheet = workBook.getSheetAt(0);
		return sheet.rowIterator();
	}
	/**
	 * 读取Excel文件，返回文件中的所有行
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static Iterator<Row> readExcel(String filePath) throws FileNotFoundException, IOException {
		File file = new File(filePath);
		return readExcel(new FileInputStream(file),filePath.substring(filePath.lastIndexOf(".")+1));
	}
	
	/**
	 * @param inputStream
	 * @param fileType xls 或xlsx
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Iterator<Row> readExcel(InputStream inputStream,String fileType) throws FileNotFoundException, IOException {
		if(StringUtils.equalsIgnoreCase(fileType, "xlsx")){
			return readExcel2007To2010(inputStream);
		} else {
			return readExcel2003(inputStream);
		}
	}
	
	
	/**
	 * 将内容写到Excel文件中
	 * @param xlsFile
	 * @param dataProvider
	 * @throws IOException
	 */
	public static <T> void  writeToExcel(File xlsFile,ExcelDataProvider<T> dataProvider) throws IOException{
		FileOutputStream os = new FileOutputStream(xlsFile);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet createSheet = wb.createSheet();
		
		int rowNum = 0;
		Row row;
		if(dataProvider.getTitleColumns() != null){
			row = createSheet.createRow(rowNum++);
			PoiUtil.fillRow(row, dataProvider.getTitleColumns());	
		}
		
		for(T t : dataProvider.getDataList()){
			row = createSheet.createRow(rowNum++);
			PoiUtil.fillRow(row, dataProvider.getColumns(t));
		}
		
		wb.write(os); 
		os.flush(); //刷新缓存
		IOUtils.closeQuietly(os);
	}
	
	/**
	 * 此类比较适合写出数据量比较小的
	 * Excel数据提供器
	 * @author yelb
	 *
	 * @param <T>
	 */
	public interface ExcelDataProvider<T>{
		public List<T> getDataList();
		public String[] getTitleColumns();
		public String[] getColumns(T t);
	}
	
	/**
     * 将内容写到Excel文件中
     * @param xlsFile
     * @param it 迭代大器
     * @throws IOException
     */
    public static <T> void  writeToExcel(File xlsFile,DataIterator<String[]> it) throws IOException{
        FileOutputStream os = new FileOutputStream(xlsFile);
        
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet createSheet = wb.createSheet();
        
        int rowNum = 0;
        Row row;
        if(it.getTitleColumns() != null){
            row = createSheet.createRow(rowNum++);
            PoiUtil.fillRow(row, it.getTitleColumns());   
        }
        
        while(it.hasNext()) {
            row = createSheet.createRow(rowNum++);
            PoiUtil.fillRow(row, it.next());
        }
        
        wb.write(os); 
        os.flush(); //刷新缓存
        IOUtils.closeQuietly(os);
    }
    

    /**
     * 得到单元格中的文本值
     * @param cell
     * @return
     */
    public static String getText(Cell cell) {
    	if(cell == null) {
    		return null;
    	}
    	cell.setCellType(Cell.CELL_TYPE_STRING);
    	return cell.getStringCellValue();
    }
    
    /**
     * 得到单元格中的文本值-trim 后非null
     * @param cell
     * @return
     */
    public static String getTrim2EmptyText(Cell cell) {
    	return StringUtils.trimToEmpty(getText(cell));
    }
    
    public static String getTrim2NullText(Cell cell) {
    	return StringUtils.trimToNull(getText(cell));
    }
    
    /**
     * 读取日期
     * @param cell
     * @return
     */
    public static Date getDate(Cell cell) {
    	return getDate(cell,DateUtil.COMPACT_YMD_FORMAT);
    }
    
    public static Date getDate(Cell cell,Date defaultDate) {
    	Date date =  getDate(cell);
    	if(date == null)
    		date = defaultDate;
    	return date;
    }
    
    /**
     * 读取日期
     * @param cell
     * @param formatPattern
     * @return
     */
    public static Date getDate(Cell cell,String formatPattern) {
    	String dateStr = getTrim2NullText(cell);
    	if(StringUtils.isBlank(dateStr)) return null;
    	return DateUtil.parseDate(dateStr,formatPattern);
    }
    
    /**
     * 得到int值
     * @param cell
     * @return if cell is empty ,return default value(zero);
     */
    public static int getInt(Cell cell) {
    	return NumberUtils.toInt(getText(cell));
    }
    /**
     * 得到int值
     * @param cell
     * @return if cell is empty ,return default value(zero);
     */
    public static double getDouble(Cell cell) {
    	return NumberUtils.toDouble(getText(cell));
    }
    
    /**
     * 得到Long值
     * @param cell
     * @return if cell is empty ,return default value(zero);
     */
    public static long getLong(Cell cell) {
    	return NumberUtils.toLong(getText(cell));
    }
    
    /**
     * 得到默认值
     * @param cell
     * @return when cell.value.equalsIgnoreCase("true")) return true;
     */
    public static boolean getBool(Cell cell) {
    	return Boolean.valueOf(getText(cell));
    }
    
    
}
