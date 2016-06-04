/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.springmvc.controller;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.cadre.springmvc.editor.MyCustomDateEditor;
import com.cadre.springmvc.editor.StringEscapeEditor;

/**
 * @Type: com.cadre.springmvc.controller.GlobalController.java
 * @ClassName: GlobalController
 * @Version: 1.0 2014年4月16日 下午4:15:08
 * @Description: 自动数据格式转换<br/>
 * 
 */
@ControllerAdvice
public class GlobalController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 自动转换日期类型的字段格式
		binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
		//binder.registerCustomEditor(java.sql.Timestamp.class,new CustomTimestampEditor(datetimeFormat, true));
		// 防止XSS攻击
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true,false));
	}
}
