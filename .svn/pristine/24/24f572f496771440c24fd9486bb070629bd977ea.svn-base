<%@page import="com.cadre.common.WebApplication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/common/common.jsp" %>
	<script src="./resources/js/lib/jquery.js" type="text/javascript"></script>
	<title>厦门大学学工信息管理系统</title>
	<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
	<script type="text/javascript" src="${res }/scripts/jquery-1.11.1.min.js"></script>
</head>

<body>
<div class="login">
	<img class="logo" src="${res }/images/login.png" alt="厦门大学学工信息管理系统" title="厦门大学学工信息管理系统" />
	<div class="form_bg">
	<form name='f' action='<c:url value="/j_spring_security_check" />' method='POST' id="login_form">
		<label>用户名</label><input id="userName" type="text" name="j_username" value="${SPRING_SECURITY_LAST_USERNAME}" class="type1 input"/><br />
		<label>密码</label><input type="password" id="pwd" name="j_password" value=""  class="type1 input"><br />
		<label>验证码</label><input type="text" id="checkCode" name="j_checkCode" class="type2 input">
		<img id="CodeImg" src="image.jsp" class="type2"/><h4 id="changeCode">看不清？换一张</h4><br />
		<label></label><h5 style="color:red">${SPRING_SECURITY_LAST_EXCEPTION.message } ${errmsg}</h5>
		<center><input type="submit" value="登录"  class="type3"/></center><br />
	</form>
	</div>
	<div class="footer">
		<h4>Copyright&nbsp;&nbsp;&copy;&nbsp;&nbsp;厦门大学信息学院学生工作处</h4>
		<p>地址：厦门市思明区演武路<br />电话：0592-2186110<p>
	</div>
</div>
<script type="text/javascript">
function bindchangeCodeEvent(){
	$("#changeCode").click(function(){
		$("#CodeImg").attr("src","image.jsp?randCode="+Math.random());
	});
}

$(document).keydown(function(event){
	if (event.which == 13){
		$("login_form").submit();
	}
	
});
$(function(){
	$("#userName").focus();
	bindSubmitBtnClickEvent();
	bindchangeCodeEvent();
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