<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/content.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
  </head>
<body>
		<div class="tb_940">
			<div id="Showform">
				<table cellpadding="0" cellspacing="0" class="basicInfo">
					<tr>
						<td style="font-size:18px;">发文名称</td>
						<td>
							${postList[0].workingPaper.postingName }
						</td>
						<td style="font-size:18px;">发文文号</td>
						<td>
							${postList[0].workingPaper.postingNumber  }
						</td>
					</tr>
					<tr>
						<td style="font-size:18px;">发文时间</td>
						<td>
							<fmt:formatDate value="${postList[0].workingPaper.postingDay }" pattern='yyyy-MM-dd'/>
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
				
				<p style="font-weight:bold">该发文对人员任免的所有操作如下</p>
				
				<c:forEach items="${postList }" var="pc">
					<c:if test="${pc.appointOrRemove == '升级'}">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>任职人员</td>					
								<td >任职原因</td>						
								<td >任职时间</td>
								<td>任职类型</td>
								<td >人员级别提拔</td>
								<td >备注</td>
							</tr>
							<tr>
								<td>${pc.user.name}</td>
								<td>${pc.reasion}</td>
								<td><fmt:formatDate value="${pc.actionDay }" pattern='yyyy-MM-dd'/></td>
								<td>${pc.appointOrRemove}</td>
								<td>${ide:getItem("positionLevel",pc.grade)}</td>
								<td>${pc.remark}</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${pc.appointOrRemove == '任职'}">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>任职人员</td>					
								<td >任职原因</td>						
								<td >任职时间</td>
								<td>任职类型</td>
								<td >任职岗位</td>
								<td >备注</td>
							</tr>
							<tr>
								<td>${pc.user.name }</td>
								<td>${pc.reasion}</td>
								<td><fmt:formatDate value="${pc.actionDay }" pattern='yyyy-MM-dd'/></td>
								<td>${pc.appointOrRemove}</td>
								<td>院/处:${pc.position.academy}--系/科:${pc.position.department}--岗位名称:${pc.position.positionName}</td>
								<td>${pc.remark}</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${pc.appointOrRemove == '任职且升级'}">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>任职人员</td>					
								<td >任职原因</td>						
								<td >任职时间</td>
								<td>任职类型</td>
								<td >人员级别提拔</td>
								<td >任职岗位</td>
								<td >备注</td>
							</tr>
							<tr>
								<td>${pc.user.name}</td>
								<td>${pc.reasion}</td>
								<td><fmt:formatDate value="${pc.actionDay }" pattern='yyyy-MM-dd'/></td>
								<td>${pc.appointOrRemove}</td>
								<td>${ide:getItem("positionLevel",pc.grade)}</td>
								<td>院/处:${pc.position.academy}--系/科:${pc.position.department}--岗位名称:${pc.position.positionName}</td>
								<td>${pc.remark}</td>
							</tr>
						</table>
					</c:if>
					
					<!-- *******************************分割线******************************************** -->
					<c:if test="${pc.appointOrRemove == '降级'}">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>免职人员</td>					
								<td >免职原因</td>						
								<td >免职时间</td>
								<td>免职类型</td>
								<td >人员级别贬职</td>
								<td >备注</td>
							</tr>
							<tr>
								<td>${pc.user.name}</td>
								<td>${pc.reasion}</td>
								<td><fmt:formatDate value="${pc.actionDay }" pattern='yyyy-MM-dd'/></td>
								<td>${pc.appointOrRemove}</td>
								<td>${ide:getItem("positionLevel",pc.grade)}</td>
								<td>${pc.remark}</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${pc.appointOrRemove == '免职'}">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>免职人员</td>					
								<td >免职原因</td>						
								<td >免职时间</td>
								<td>免职类型</td>
								<td >免职岗位</td>
								<td >备注</td>
							</tr>
							<tr>
								<td>${pc.user.name}</td>
								<td>${pc.reasion}</td>
								<td><fmt:formatDate value="${pc.actionDay }" pattern='yyyy-MM-dd'/></td>
								<td>${pc.appointOrRemove}</td>
								<td>院/处:${pc.position.academy}--系/科:${pc.position.department}--岗位名称:${pc.position.positionName}</td>
								<td>${pc.remark}</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${pc.appointOrRemove == '免职且降级'}">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>免职人员</td>					
								<td >免职原因</td>						
								<td >免职时间</td>
								<td>免职类型</td>
								<td >人员级别贬值</td>
								<td >免职岗位</td>
								<td >备注</td>
							</tr>
							<tr>
								<td>${pc.user.name}</td>
								<td>${pc.reasion}</td>
								<td><fmt:formatDate value="${pc.actionDay }" pattern='yyyy-MM-dd'/></td>
								<td>${pc.appointOrRemove}</td>
								<td>${ide:getItem("positionLevel",pc.grade)}</td>
								<td>院/处:${pc.position.academy}--系/科:${pc.position.department}--岗位名称:${pc.position.positionName}</td>
								<td>${pc.remark}</td>
							</tr>
						</table>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="关闭" class="cancel" onclick="art.dialog.close()" />
    	</div>
</body>
</html>