<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide"%>
<script type="text/javascript"
	src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
<title>厦门大学干部信息管理系统</title>
<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${res }/css/ny.css" type="text/css" />
</head>

<body>
	<c:if test="${empty errList }"><center><h1>成功</h1></center>
	<center><h4>窗口将在&nbsp;&nbsp;<span id="leftTime">2</span>&nbsp;&nbsp;秒内自动关闭</h4></center>
   	<script type="text/javascript">
   		function changeTime(){
   			var t=parseInt($("#leftTime").text());   			
   			if(t <= 0){
   				art.dialog.opener.location.reload();
		 		art.dialog.close();
   			}
   			$("#leftTime").text(t-1);
   			setTimeout("changeTime()",1000);
   		}
   		$(function(){
   			setTimeout("changeTime()",1000);
   		});
   	</script>
	</c:if>
	<c:if test="${!empty errList }">
		<c:forEach items="${errList} " var="err">
			${err } <br />

		</c:forEach>
	</c:if>
</body>
</html>
