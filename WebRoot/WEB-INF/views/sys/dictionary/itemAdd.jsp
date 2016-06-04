<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
 		
  </head>
  
  <body>
  <div class="tb_480">
	<center>
		<label>请输入：</label><br />
		<input type="text" id="creat_value" />
	</center>
	<div class="tb_sub_btns">
  		<input type="button" value="添加" class="addBtn" />
		<input type="button" value="返回" onclick="art.dialog.close()"/>
  	</div>
  </div>
  </body>
  
  <script>
  $(function(){
  	$(".addBtn").click(function(){
  		$.post("${ctx}/sys/dictionary/save.do",{parentCode:"${pid}" , value:$("#creat_value").val()},function(result){
				if ("success" == result) 
					art.dialog.close();
				else
					art.dialog.alert("未成功");	
			});
  	});
  });
  </script>
</html>
