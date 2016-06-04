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
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
  </head>
  
  
  <body class="tb_940">
	<form id="addForm" method="post" action="${ctx}/userTrain/UserTrainInfo/updateTrain.do" enctype="multipart/form-data" onsubmit="return false">
	<div class="reTitle"><strong>添加培训事件(<font color="red">必填</font>)</strong></div>
	<table class="basicInfo" cellspacing="0">
		<tr><td>开始时间</td>
			<td><input id="beginDay" name="beginDay" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" value="<fmt:formatDate value='${train.beginDay }'  pattern='yyyy-MM-dd'/>" /></td>
		</tr>
		<tr><td>结束时间</td>
			<td><input id="endDate" name="endDate" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" value="<fmt:formatDate value='${train.endDate }'  pattern='yyyy-MM-dd'/>" /></td>
		</tr>
		<tr>
			<td>
				培训名称
				<input name="id" type="hidden" value="${train.id }" />
			</td>
			<td ><input id="trainingName" name="trainingName" type="text" class="txt required" value="${train.trainingName }"/></td>
		</tr>
		<tr><td>主办方</td><td ><input id="organizer" name="organizer" type="text" class="txt required" value="${train.organizer }"/></td></tr>
		<tr><td>培训期数</td><td ><input id="trainingPeriod" name="trainingPeriod" type="text" class="txt required" value="${train.trainingPeriod }"/></td></tr>
		<tr><td>培训地点</td><td ><input id="trainingPlace" name="trainingPlace" type="text" class="txt required" value="${train.trainingPlace }"/></td></tr>
		<tr><td>备注</td><td ><input id="remark" name="remark" type="text" class="txt required" value="${train.remark }"/></td></tr>
	</table>
	</form>
	
	<br/>
	<div class="tb_sub_btns">
		<input type="submit" value="确定" class="add" id="saveBtn" /> 
		<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
	 		$("#saveBtn").click(function(){
	 			var data = $("#addForm").serialize();
				$.post("${ctx}/userTrain/UserTrainInfo/updateTrain.do",data,
					function(result) {
						if("success" == result) {
	    					art.dialog.opener.location.reload();
	                		art.dialog.close();   
	                	}            
						else if( "fail" == result){
	                    	art.dialog.tips("修改失败!");
	                	}
				});
			});
		});
	</script>
  </body>
</html>
