<%@page import="com.cadre.common.WebApplication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>干部信息管理系统</title>
		<%@ include file="/common/common.jsp" %>
		<script src="./resources/js/lib/jquery.js" type="text/javascript"></script>
	</head>
	<body>
	    <div class="main">
	        <div class="main_con">
		        <form id="loginForm" name='f' action='<c:url value="/j_spring_security_check" />' method='POST'>
		           	 用户名：<input type="text" id="userName" name="j_username" maxlength="30" class="txt uid" value="${SPRING_SECURITY_LAST_USERNAME}"/><br/>
		                                     密&nbsp;&nbsp;码：<input type="password" id="pwd" name="j_password" maxlength="30" class="txt pwd" value=""/><br/>
		                                     验证码:<input type="text" id="checkCode" name="j_checkCode"/>    <br/><img src="image.jsp" alt="验证码">
		            <input type="button" id="submitBtn" class="btn" value="登陆" /> 
		            	
		            <span id="ts" class="ts">${SPRING_SECURITY_LAST_EXCEPTION.message } ${errmsg}</span>  
		        </form>
	        </div>
	    </div>
	    
	    <script type="text/javascript">
		    $(document).keypress(function(e) {
		        if(e.which == 13) {
		        	login();
		        }
		    });
		    $(function(){
		    	$("#userName").focus();
		    	bindSubmitBtnClickEvent();
		    });
		    function bindSubmitBtnClickEvent(){
		    	$("#submitBtn").click(function(){
		    		login();
		    	});
		    }
		    function login(){
		    	if($("#userName").val() == ""){
	                $("#ts").html("请输入用户名");
	                $("#userName").focus();
	                return;
	            }
	            if($("#pwd").val() == ""){
	                $("#ts").html("请输入密码");
	                $("#pwd").focus();
	                return;
	            }
	            $("#loginForm").submit();
		    }
	    </script>
	</body>
</html>
