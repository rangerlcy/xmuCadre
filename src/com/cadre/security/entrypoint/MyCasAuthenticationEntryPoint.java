/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.security.entrypoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.cadre.springmvc.RequestType;  
/**
 * @Type: com.cadre.security.entrypoint.MyCasAuthenticationEntryPoint.java
 * @ClassName: MyCasAuthenticationEntryPoint
 * @Description: <br/>
 * 
 */
public class MyCasAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
//		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "admin");
//		SecurityContextHolder.getContext().setAuthentication(authRequest);
//		request.getSession().setAttribute("auth", true);
//		response.sendRedirect(request.getContextPath() + "/index.do");
		if(RequestType.isAjaxRequest(request)){
			response.setHeader("sessionstatus", "timeout");  
			response.sendError(518, "session timeout.");  
		}else{
			request.setAttribute("message", authException.getMessage());
			String jsCode = "<script type='text/javascript'>" +  
					"var p=window;while(p!=p.parent){p=p.parent; } p.location.href='" +  request.getContextPath() + "/'</script>";
			response.getWriter().write(jsCode);
		}
		response.getWriter().flush();
		response.getWriter().close();
		
	}
}