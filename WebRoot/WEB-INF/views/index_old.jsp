<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<title>干部信息管理系统</title>
	<%@ include file="/common/common.jsp"%>
</head>
<body>
<div> 您好！${sysUser.name}<a href="<c:url value="/j_spring_security_logout"/>">[退出]</a></div>
</body>
</html>