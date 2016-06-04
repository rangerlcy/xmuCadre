package com.cadre.model.excel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public abstract class ExcelImportorAdapter<T> extends Importor<T> {
	Logger LOG = LogManager.getLogger(ExcelImportorAdapter.class);
	/**
	 * 
	 * @param file
	 * @param convertor
	 * @return
	 * @throws Exception
	 */
	public String importData(MultipartFile file,AbstractExcelDataConvertor<T> convertor) throws Exception{
		return super.importData(file.getOriginalFilename(), file.getInputStream(), convertor);
	}

}
