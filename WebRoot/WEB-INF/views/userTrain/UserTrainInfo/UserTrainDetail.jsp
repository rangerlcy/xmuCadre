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
  </head>
<body>
		<div class="tb_box">
			<div class="requiredInfo">
				<div class="reTitle"><strong>培训信息</strong></div>
				<table class="basicInfo" cellspacing="0">
					<tr><td>开始时间</td><td ><label id="beginDay">${ut.beginDay }</label></td></tr>
					<tr><td>结束时间</td><td><label id="endDate">${ut.endDate }</label></td></tr>
					<tr><td>教育培训名称</td><td><label id="trainingName">${ut.trainingName }</label></td></tr>
					<tr><td>教育培训期数</td><td><label id="trainingPeriod">${ut.trainingPeriod }</label></td></tr>
					<tr><td>主办单位</td><td><label id="organizer">${ut.organizer }</label></td></tr>
					<tr><td>教育培训地点</td><td><label id="trainingPlace">${ut.trainingPlace }</label></td></tr>
					<tr><td>备注</td><td><label id="remark">${ut.remark }</label></td></tr>
				</table>
			</div>
			<div class="extenceInfo">
				<div class="exTitle"><strong>培训详情</strong></div>
				<c:forEach items = "${tu }" var = "etu">
				<table class="exInfo" cellspacing="0">
					<tr><td>培训人员</td><td><label id="trainUserName">${etu.user.name }</label></td></tr>
				</table>
				</c:forEach>
			</div>
			
			
			<div class="tb_sub_btns">
				<input type="button" value="关闭" class="cancel" onClick="art.dialog.close()" />
			</div>
		</div>
</body>
</html>