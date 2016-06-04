<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
  </head>
	<body>
		<div class="tb_940">
			<div id="Showform">
				<table cellpadding="0" cellspacing="0" class="basicInfo">
					<tr>
						<td style="font-size:18px;">发文名称</td>
						<td>
							${pclist[0].paper.paperName }
						</td>
						<td style="font-size:18px;">发文文号</td>
						<td>
							${pclist[0].paper.paperNumber }
						</td>
					</tr>
					<tr>
						<td style="font-size:18px;">发文时间</td>
						<td>
							<fmt:formatDate value="${pclist[0].paper.paperDay}" pattern='yyyy-MM-dd'/>
						</td>
						<td style="font-size:18px;">发文文件</td>
						<td>
							${empty pclist[0].paper.paperFile ? "没有发文文件" : "有可供下载的发文文件"}
						</td>
					</tr>
				</table>
				
				<p style="font-weight:bold">该发文对岗位的所有操作如下</p>
				
				<c:forEach items="${pclist }" var="pc">
					<c:if test="${pc.editType == 1}">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>修改类型</td>			
								<td >学院（研究院）/部处</td>						
								<td >系所/科室</td>						
								<td >职务名称</td>
								<td>岗位类型</td>
								<td >岗位级别</td>
								<td >设岗时间</td>
								<td>撤岗时间</td>
								<td> 职位备注</td>
							</tr>
							<tr>
								<td>增加岗位</td>
								<td>${pc.academy1}</td>
								<td>${pc.department1}</td>
								<td>${pc.positionName1}</td>
								<td>${pc.positionType1}</td>
								<td>${pc.positionLevel1}</td>
								<td><fmt:formatDate value="${pc.positionDay1}" pattern='yyyy-MM-dd'/></td>
								<td><fmt:formatDate value="${pc.delPositionDay1}" pattern='yyyy-MM-dd'/></td>
								<td>${pc.positionRemark1}</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${pc.editType==2 }">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>修改类型</td>			
								<td >学院（研究院）/部处</td>						
								<td >系所/科室</td>						
								<td >职务名称</td>
								<td>岗位类型</td>
								<td >岗位级别</td>
								<td >设岗时间</td>
								<td>撤岗时间</td>
								<td> 职位备注</td>
							</tr>
							<tr>
								<td>撤除岗位</td>
								<td>${pc.academy1}</td>
								<td>${pc.department1}</td>
								<td>${pc.positionName1}</td>
								<td>${pc.positionType1}</td>
								<td>${pc.positionLevel1}</td>
								<td><fmt:formatDate value="${pc.positionDay1}" pattern='yyyy-MM-dd'/></td>
								<td><fmt:formatDate value="${pc.delPositionDay1}" pattern='yyyy-MM-dd'/></td>
								<td>${pc.positionRemark1}</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${pc.editType==3 }">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>修改类型</td>			
								<td >学院（研究院）/部处</td>						
								<td >系所/科室</td>						
								<td >职务名称</td>
								<td>岗位类型</td>
								<td >岗位级别</td>
								<td >设岗时间</td>
								<td>撤岗时间</td>
								<td>职位备注</td>
							</tr>
							<tr>
								<td>撤除从</td>
								<td>${pc.academy1}</td>
								<td>${pc.department1}</td>
								<td>${pc.positionName1}</td>
								<td>${pc.positionType1}</td>
								<td>${pc.positionLevel1}</td>
								<td><fmt:formatDate value="${pc.positionDay1}" pattern='yyyy-MM-dd'/></td>
								<td><fmt:formatDate value="${pc.delPositionDay1}" pattern='yyyy-MM-dd'/></td>
								<td>${pc.positionRemark1}</td>
							</tr>
							<tr>
								<td>增加到</td>
								<td>${pc.academy2}</td>
								<td>${pc.department2}</td>
								<td>${pc.positionName2}</td>
								<td>${pc.positionType2}</td>
								<td>${pc.positionLevel2}</td>
								<td><fmt:formatDate value="${pc.positionDay2}" pattern='yyyy-MM-dd'/></td>
								<td><fmt:formatDate value="${pc.delPositionDay2}" pattern='yyyy-MM-dd'/></td>
								<td>${pc.positionRemark2}</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${pc.editType==4 }">
						<table cellpadding="0" cellspacing="0" class="basicInfo">
							<tr id="addAndRemove" style="background:#22B2AF;">
								<td>修改类型</td>			
								<td >学院（研究院）/部处</td>						
								<td >系所/科室</td>						
								<td >职务名称</td>
								<td>岗位类型</td>
								<td >岗位级别</td>
								<td >设岗时间</td>
							</tr>
							<tr>
								<td>修改前</td>
								<td>${pc.academy1}</td>
								<td>${pc.department1}</td>
								<td>${pc.positionName1}</td>
								<td>${pc.positionType1}</td>
								<td>${pc.positionLevel1}</td>
								<td><fmt:formatDate value="${pc.positionDay1}" pattern='yyyy-MM-dd'/></td>
							</tr>
							<tr>
								<td>修改成</td>
								<td>${pc.academy2}</td>
								<td>${pc.department2}</td>
								<td>${pc.positionName2}</td>
								<td>${pc.positionType2}</td>
								<td>${pc.positionLevel2}</td>
								<td><fmt:formatDate value="${pc.positionDay2}" pattern='yyyy-MM-dd'/></td>
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
