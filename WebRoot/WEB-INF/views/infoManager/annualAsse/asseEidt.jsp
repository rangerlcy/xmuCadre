<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
  </head>
  
  <body>
  <div class="tb_480">
 	<form id = "editForm" onsubmit="return false;">
 		<input type="hidden" name="id" value="${asse.id }"/>
	 	<table class="basicInfo" cellspacing="0" id="font16">
	 		<tr><td>年份</td><td><input type = "text" name="year" value="${asse.year }" class="txt" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength=4 /></td>
	 		<tr><td>考核结果</td><td>
	 			<select name = "assessmentResult">
					<option value="请选择" <c:if test="${asse.assessmentResult == null ||asse.assessmentResult == '请选择'}">selected</c:if>> 请选择</option>
					<option value="优秀" <c:if test="${asse.assessmentResult == '优秀'}">selected</c:if>> 优秀</option>
					<option value="良好" <c:if test="${asse.assessmentResult == '良好'}">selected</c:if>> 良好</option>
					<option value="合格" <c:if test="${asse.assessmentResult == '合格'}">selected</c:if>> 合格</option>
					<option value="不合格" <c:if test="${asse.assessmentResult == '不合格'}">selected</c:if>> 不合格</option>
		 		</select></td></tr>
	 	</table>
 	</form>
 </div>
	<div class="tb_sub_btns">
	 	<input type="submit" value="保存" class="add" id="saveBtn"/> 
		<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
	 </div>
 	<script type="text/javascript">
 	
 	function bindSaveBtnEvent(){
 		$("#saveBtn").click(function(){
 			var year = parseInt($("input[name=year]").val());
 			if(year<1900 || year >3000 || isNaN(year)) {
 				art.dialog.tips("失败，年份应在1900-3000之间");
 				return ;
 			}
 			var data = $("#editForm").serialize();
 			$.post("${ctx}/infoManager/annualAsse/asseEdit.do?1=2",data,function(result){
 				if ("success" == result){ 				
		 			art.dialog.opener.location.reload();
		 			art.dialog.close();
 				}else if ("invalidId" == result){
 					art.dialog.tips("失败，系统出错");
 				}else
 					art.dialog.tips("失败，未知的错误");
 			});
 		});
 	}
 	
 	$(function(){
 	
 	bindSaveBtnEvent();
 	});
 	</script>
  </body>
</html>
