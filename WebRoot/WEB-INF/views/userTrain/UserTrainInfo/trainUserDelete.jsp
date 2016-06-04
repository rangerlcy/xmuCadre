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
  		<form action="${ctx}/userTrain/UserTrainInfo/deleteTrainUsers.do" method="post">
 				<table class="basicInfo" cellspacing="0">
					<tr>
						<td>选择</td>
						<td>姓名</td>
						<td>时任职岗位</td>
						<td>时任行政级别</td>
						<td>时聘专业技术职务</td>
					</tr>
					<c:forEach items="${ trainUsers}" var="tus" varStatus="status">
						<tr>
							<td><input type="checkbox" name="trainUserSimples[${ status.count-1}].trainUserId" value="${tus.trainUserId }"></td>
							<td>${tus.userName }</td>
							<td>${tus.positionSimple.acadmyName } ${tus.positionSimple.departmentName } ${tus.positionSimple.positionName }</td>
							<td>${tus.positionSimple.positionLevelName }</td>
							<td>${tus.skillName}</td>
						</tr>
					</c:forEach>
				</table>
			 	<div class="tb_sub_btns">
					<input type="submit" value="确定" class="add" id="saveBtn" /> 
					<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
				</div>
			</form>
<!-- ------------------------------------------------------------------------------------------------------------------------------ -->
	<script>
		
	</script>

  </body>
  
</html>
