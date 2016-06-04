<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/ny.css" />
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
		
	</head>
  
  <body>
     	<div class="tb_box">
	    	<table cellpadding="0" cellspacing="0" border="0" class="tb80">
	    		<tr>
	    			<td width="150">项目名称：</td>
	    			<td align="left">${secondment.temporaryProjectName }</td>
	    		</tr>
	    		<tr>
	    			<td>发文文号：</td>
	    			<td align="left">${secondment.paper.paperNumber }</td>
	    		</tr>
	    		<tr>
	    			<td>发文文名：</td>
	    			<td align="left">${secondment.paper.paperName }</td>
	    		</tr>
	    	</table>
	    	<div class="tb15"><span onclick="add()">添加</span></div>
	    	<div id="info"></div>
	    	<center style="clear:both"><h4>项目中已添加的人员</h4></center>
	    	<form id = "delForm" onsubmit="return false">
	    	<input type="hidden" name="secondmentId" value="${secondment.id }" />
	    	<table cellpadding="0" cellspacing="0" border="0" class="basicInfo">	    		
				<tr>
					<td >全选 <input type="checkBox" id="checkAll"/></td>
					<td >挂职人员</td>
					<td >挂职地点</td>
					<td >挂职单位</td>
					<td >挂职职务</td>
					<td>考核情况</td>
					<td>备注</td>					
				</tr>
				
				<c:forEach items="${secondmentUsers }" var="secU" varStatus = "index">
					
					<tr>
						<td ><input type="checkBox" name="secondmentUserList[${ index.index }].userId" value="${secU.user.id }"></td>
						<td >${secU.user.name }<input type="hidden" class="userIds" value="${secU.user.id }"/></td>
						<td >${secU.temporaryPlace }</td>
						<td >${secU.temporaryUnit }</td>
						<td >${secU.temporaryJob }</td>
						<td>${secU.assessementSitutation }</td>
						<td>${secU.remark }</td>
					</tr>
					
				</c:forEach>
				
			</table>
			<div class="tb_sub_btns">
				<input type="button" class="btn delBtn add" value="确定"/>
				<input type="button" class="btn cancelBtn cancel" value="取消"/>
			</div>
			</form>
		</div>
		
		<script type="text/javascript">
		$(function(){
			$("#checkAll").click(function(){
				if ($(this).is(":checked")) {
					$(":checkBox").prop("checked",true);
				} else {			
					$(":checkBox").removeAttr("checked");
				}
			});
			
			$(".delBtn").click(function() {
				art.dialog.confirm("确定删除么？",function(){
					var data = $("#delForm").serialize();
					$.post("${ctx}/secondment/delSecondmentUser.do",data,function(result){
						if ("success" == result) {
							art.dialog.opener.location.reload();
					        art.dialog.close();
						} else if ("nobody" == result){
							art.dialog.alert("请选择待删除的人员");
						} else {
							art.dialog.alert("错误");
						}
					});
				});
				
			});
			$(".cancelBtn").click(function() {
				art.dialog.close();
			});
		});
		</script>
  </body>
</html>
