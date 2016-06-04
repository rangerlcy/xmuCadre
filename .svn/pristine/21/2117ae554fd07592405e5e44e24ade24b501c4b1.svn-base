/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.springmvc.editor;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.cadre.model.utils.DateUtil;

/**
 * @Type: com.cadre.springmvc.controller.MyCustomDateEditor.java
 * @ClassName: MyCustomDateEditor
 * @Description: Parse the Date from the given text, using the specified DateFormat.<br/>
 * 
 */
public class MyCustomDateEditor extends CustomDateEditor {
	
	public MyCustomDateEditor() {
		super(null, true);
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isBlank(text)) {
			setValue(null);
		} else {
			try {
				setValue(DateUtil.parseDate(text,DateUtil.DEFAULT_YMD_FORMAT));
			}catch (Exception ex) {
				setValue(DateUtil.parseDate(text,DateUtil.DEFAULT_YMDHMS_FORMAT));
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		return (getValue() != null ? DateUtil.time2Str((Date)getValue(), DateUtil.DEFAULT_YMDHMS_FORMAT) : "");
	}
}
