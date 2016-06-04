/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.springmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cadre.springmvc.RequestType;

/**
 * @Type: com.cadre.springmvc.controller.LoginController.java
 * @ClassName: LoginController
 * @Description: <br/>
 * 
 */
@Controller
public class LoginController {
	
	@RequestMapping("preLogin")
	public String preLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(RequestType.isAjaxRequest(request)){
        	response.setHeader("sessionstatus", "timeout");  
        	response.sendError(518, "session timeout.");  
        }else{
        	String jsCode = "<script type='text/javascript'>" +  
        			"var p=window;while(p!=p.parent){p=p.parent; } p.location.href='" +  request.getContextPath() + "/'</script>";
        	response.getWriter().write(jsCode);
        }
        response.getWriter().flush();
        response.getWriter().close();
		return null;
	}
}
