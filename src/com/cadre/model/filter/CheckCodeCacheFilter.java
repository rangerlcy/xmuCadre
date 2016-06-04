package com.cadre.model.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckCodeCacheFilter implements Filter{
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest HttpRequest = (HttpServletRequest)request; 
    	HttpServletResponse HttpResponse = (HttpServletResponse)response;
        if (request.getParameter("j_checkCode").toString().equals(
        		HttpRequest.getSession().getAttribute("rand").toString()
        		)
        	)
        	filterChain.doFilter(request, response);
        else{
        	HttpRequest.getSession().setAttribute("errmsg", "验证码错误");
        	HttpRequest.getSession().setAttribute("rand", null);
        	HttpResponse.sendRedirect(HttpRequest.getContextPath().toString()+ "/");
        	return ;
        }

    }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
