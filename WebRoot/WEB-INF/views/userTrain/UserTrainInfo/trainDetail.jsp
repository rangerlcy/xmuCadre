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
		<script type="text/javascript" src="${res }/js/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${res }/js/tableType.js"></script>
  </head>
  
  <body class="tb_940">
  		<div>
	  		<table class="basicInfo" cellspacing="0">
	  			<tr>
	  				<td>培训Id</td>
	  				<td id="trainId">${train.id }</td>
	  			</tr>
	  			<tr>
	  				<td>培训开始时间</td>	
	  				<td id="beginDay" ><fmt:formatDate value="${train.beginDay }" pattern="yyyy-MM-dd"/></td>
	  			</tr>
	  			<tr>
	  				<td>培训结束时间</td>
	  				<td id="endDay" ><fmt:formatDate value="${train.endDate }" pattern="yyyy-MM-dd"/></td>
	  			</tr>
	  			<tr>	
	  				<td>培训名称</td>
	  				<td id="trainingName" >${train.trainingName }</td>
	  			</tr>
	  			<tr>
	  				<td>培训期数</td>
	  				<td id="trainingPeriod" >${ empty (train.trainingPeriod) ? "无" : (train.trainingPeriod)}</td>
	  			</tr>
	  			<tr>
	  				<td>主办单位</td>
	  				<td id="organizer" >${ empty (train.organizer) ? "无" : train.organizer }</td>
	  			</tr>
	  			<tr>
	  				<td>培训地点</td>
	  				<td id="trainingPlace" >${ empty (train.trainingPlace) ? "无" :  train.trainingPlace}</td>
	  			</tr>
	  			<tr>
	  				<td>备注</td>
	  				<td id="remark" >${train.remark }</td>
	  			</tr>
	  		</table>
  		</div>

  		<table class="basicInfo" cellspacing="0">
			<tr>
				<td>姓名</td>
				<td>时任职岗位</td>
				<td>时任行政级别</td>
				<td>时聘专业技术职务</td>
			</tr>
			<c:forEach items="${trainUsers }"  var="tus">			
				<tr>
					<td>${tus.userName }</td>
					<td>${tus.positionSimple.acadmyName } ${tus.positionSimple.departmentName } ${tus.positionSimple.positionName }</td>
					<td>${tus.positionSimple.positionLevelName }</td>
					<td>${tus.skillName}</td>
				</tr>
			</c:forEach>
		</table>
  		<div class="tb_sub_btns">
		<input type="button" value="关闭" class="cancel" onClick="art.dialog.close()" />
		</div>
	
  </body>
 
  </html>