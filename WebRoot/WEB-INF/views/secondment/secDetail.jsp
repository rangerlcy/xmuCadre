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
<link rel="stylesheet" href="${res }/css/style.css" type="text/css" />
<link rel="stylesheet" href="${res }/css/content.css" type="text/css" />
<link rel="stylesheet" href="${res }/css/ny.css" type="text/css" />
<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="tb_box">


		<div class="extenceInfo" style="float:none;width:96%">
			<div class="exTitle">
				<strong>挂职详情</strong>
			</div>
			<table class="exInfo" cellspacing="0">
				<tr>
					<td>发文文号</td>
					<td><label id="postingNumber">${paper.paperNumber }</label></td>
				</tr>
				<tr>
					<td>发文题目</td>
					<td><label id="postingName">${paper.paperName }</label></td>
				</tr>
				<tr>
					<td>发文单位</td>
					<td><label id="postingUnit">${paper.paperUnits }</label></td>
				</tr>
				<tr>
					<td>挂职项目名称</td>
					<td><label id="temporaryProjectName">${pus.temporaryProjectName }</label></td>
				</tr>
				<tr>
					<td>挂职开始时间</td>
					<td><label id="temporaryBeginDay"><fmt:formatDate
								value='${pus.temporaryBeginDay }' pattern='yyyy-MM-dd' /></label></td>
				<tr>
					<td>挂职结束时间</td>
					<td><label id="temporaryEndDate"><fmt:formatDate
								value='${pus.temporaryEndDate }' pattern='yyyy-MM-dd' /></label></td>
				<tr>
					<td>挂职持续时间</td>
					<td><label id="temporaryLastTime">${(pus.temporaryEndDate.time - pus.temporaryBeginDay.time) /1000/3600/24 }</label></td>
				</tr>
				<tr>
					<td>挂职人员要求</td>
					<td><label id="temporaryRequirement">${pus.temporaryRequirement }</label></td>
				</tr>
				<tr>
					<td>备注</td>
					<td><label id="remark">${pus.remark }</label></td>
				</tr>
			</table>
		</div>
		<center style="clear:both">
			<h4>项目中已添加的人员</h4>
		</center>
		<table cellpadding="0" cellspacing="0" border="0" class="basicInfo">
			<tr>
				<td>挂职人员</td>
				<td>挂职地点</td>
				<td>挂职单位</td>
				<td>挂职职务</td>
				<td>考核情况</td>
				<td>备注</td>
			</tr>
			<c:forEach items="${secondmentUsers }" var="secU">
				<tr>
					<td>${secU.user.name }<input type="hidden" class="userIds"
						value="${secU.user.id }" /></td>
					<td>${secU.temporaryPlace }</td>
					<td>${secU.temporaryUnit }</td>
					<td>${secU.temporaryJob }</td>
					<td>${secU.assessementSitutation }</td>
					<td>${secU.remark }</td>
				</tr>
			</c:forEach>
		</table>
		<script type="text/javascript">
			$(function() {
				$(".extenceInfo:odd").css("margin-left", "0");
				$(".extenceInfo:even").css("margin-left", "1.9%");
			})
		</script>

		<div class="tb_sub_btns">
			<input type="button" value="关闭" class="cancel"
				onClick="art.dialog.close()" />
		</div>
	</div>
</body>
</html>