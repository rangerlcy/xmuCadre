package com.cadre.model.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.cadre.model.excel.utils.PoiUtil;

public abstract class Importor<T> {
	Logger LOG = LogManager.getLogger(Importor.class);
	/**
	 * 对象插入数据库的操作
	 * @param successList
	 * @param errorList
	 * @return 返回表示成功的字符串
	 */
	protected abstract String importBiz(List<T> successList,List<DataErrorMsg> errorList);
	
	/**文本导入器
	 * 
	 * @param fileName
	 * @param inputStream
	 * @param convertor
	 * @return
	 * @throws Exception
	 */
	public String importData(String fileName,InputStream inputStream,AbstractExcelDataConvertor<T> convertor) throws Exception{
		try {
			checkFile(fileName);//检查文件格式
			
			LOG.debug("读出Excel中的内容");
			Iterator<Row> it = PoiUtil.readExcel(inputStream,fileName.substring(fileName.lastIndexOf(".")+1));
			
			Row headRow = it.next();
			LOG.debug("读取标题行");
			convertor.readTitleRow(headRow);
			LOG.debug("检查首行");
			convertor.checkTitleRow(headRow);
			
			List<T> successList = convertor.convert(it);
			List<DataErrorMsg> errorList = convertor.getErrorList();
			
			
			return importBiz(successList,errorList);
		} catch (FileCheckerException e) {
			throw new Exception("上传的文件不正确:"+e.getMessage(),e);
		} catch (IOException e) {
			throw new Exception("读取上传的Excel文件出错",e);
		} catch (Exception e) {
			throw new Exception("导入Excel中的数据时出错",e);
		}
	}
	
	/**
	 * 文件导入器
	 * @param file
	 * @param convertor
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public String importData(File file,AbstractExcelDataConvertor<T> convertor) throws FileNotFoundException, Exception {
		return this.importData(file.getName(), new FileInputStream(file), convertor);
	}
	/**
	 * 文件导入器
	 * @param filePath
	 * @param convertor
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public String importData(String filePath,AbstractExcelDataConvertor<T> convertor) throws FileNotFoundException, Exception {
		return this.importData(filePath, new FileInputStream(new File(filePath)), convertor);
	}
	
	/**
	 * 检查文件格式
	 * @param fileName
	 * @return
	 * @throws FileCheckerException
	 */
	public static void checkFile(String fileName) throws FileCheckerException {
		if(!StringUtils.endsWithIgnoreCase(fileName, ".xls")
				&& !StringUtils.endsWithIgnoreCase(fileName, ".xlsx")){
			throw new FileCheckerException("上传的文件不是指定格式的Excel文件");
		}
	}
}
