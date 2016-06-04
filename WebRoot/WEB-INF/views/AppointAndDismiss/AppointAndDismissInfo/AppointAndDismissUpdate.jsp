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
		<script type="text/javascript" src="${res}/js/photo.js"></script>
  </head>
	<body>
		<div class="tb_box">
		<form id="updateForm" method="post" action="${ctx}/AppointAndDismiss/AppointAndDismissInfo/AppointAndDismissUpdate.do" enctype="multipart/form-data" onsubmit="return false">
			<div class="requiredInfo">
				<div class="reTitle"><strong>任免文件信息(<font color="red">必填</font>)</strong></div>
				<table class="basicInfo" cellspacing="0">
					<tr><td>任免类型</td><td ><input id="workingType" name="workingType" type="text" class="txt required"  value="${workingPaper.workingType}" readOnly/></td></tr>
					<tr><td>发文文号</td><td ><input id="postingNumber" name="postingNumber" type="text" class="txt required" value="${workingPaper.postingNumber}" readOnly /></td></tr>
					<tr><td>发文名称</td><td ><input id="postingName" name="postingName" type="text" class="txt required" value="${workingPaper.postingName}" readOnly/></td></tr>
					<tr><td>发文时间</td>
						<td><input id="postingDay" name="postingDay" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:ture})" value="${workingPaper.postingDay}" /></td>
					</tr>
					<tr><td>备注</td><td ><input id="remark" name="remark" type="text" class="txt required"  value="${workingPaper.remark}" readOnly/></td></tr>
				</table>
			</div>
			<c:forEach items = "${postUser }" var = "postUser">
			<div class="extenceInfo">
				<div class="exTitle"><strong>人员任免信息</strong><span onclick="addDismiss(this)">添加</span><span onclick="deleteDismiss(this)">移除</span></div>
				<table class="exInfo" cellspacing="0">
					<tr><td>任免人员</td><td><input id="user" name="user" type="text" class="txt required"  value="${postUser.user.name}" /></td></tr>
					<tr><td>任免</td>
						<td>
							<select id="appointOrRemove" name="appointOrRemove" readOnly>
								<option value="" <c:if test = "${postUser.appointOrRemove == null ||postUser.appointOrRemove == '请选择'}">selected</c:if>>请选择</option>
								<option value="任职" <c:if test = "${postUser.appointOrRemove == '任职'}">selected</c:if>>任职</option>
								<option value="免职" <c:if test = "${postUser.appointOrRemove == '免职'}">selected</c:if>>免职</option>		
							</select>
						</td></tr>
					<tr><td>任免类型</td>
						<td>
							<select id="type" name="type">
								<option value="" <c:if test = "${postUser.type == null ||postUser.type == '请选择'}">selected</c:if>>请选择</option>
								<option value="高聘" <c:if test = "${postUser.type == '高聘'}">selected</c:if>>高聘</option>		
								<option value="升级" <c:if test = "${postUser.type == '升级'}">selected</c:if>>升级</option>		
								<option value="轮岗" <c:if test = "${postUser.type == '轮岗'}">selected</c:if>>轮岗</option>		
								<option value="仅任" <c:if test = "${postUser.type == '仅任'}">selected</c:if>>仅任</option>		
								<option value="仅免" <c:if test = "${postUser.type == '仅免'}">selected</c:if>>仅免</option>					
							</select>
						</td></tr>
					<tr><td>任免原因</td><td><input id="reasion" name="reasion" type="text" class="txt" maxlength=80/></td></tr>
					<tr><td>任免岗位</td><td><input id="positionId" name="positionId" type="text" class="txt required" /></td></tr>
					<tr><td>任免级别</td><td><input id="grade" name="grade" type="text" class="txt" /></td></tr>
					<tr><td>备注</td><td><textarea name="remark" id="remark" maxlength=2000></textarea></td></tr>
				</table>
			</div>
		</c:forEach>
			
			<div class="tb_sub_btns">
				<input type="submit" value="确定" class="add" id="saveBtn" /> 
				<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
			</div>
		</form>
		
		</div>
		<script type="text/javascript">
		function bindSaveBtnEvent(){
 		$("#saveBtn").click(function(){
 			var data = $("#updateForm").serialize();
 	         $.post("${ctx}/AppointAndDismiss/AppointAndDismissInfo/AppointAndDismissUpdate.do",data,function(result){
    				if ("success" == result){
    					art.dialog.opener.location.reload();
    					art.dialog.close();
    				}else{
    					art.dialog.tips("失败");
    				}
 			
 			});
 		
 		});
 	}
 	$(function(){
 	bindSaveBtnEvent();
 	});
 	</script>
</body>
</html>
