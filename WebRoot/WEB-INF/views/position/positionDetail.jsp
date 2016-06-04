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
  
<!--    旧的岗位详情-->
  <body>
   <div class="tb_700">
  		<table cellpadding="0" cellspacing="0" class="basicInfo" id="font16">
	    	<tr><td>学院（研究院）/部处</td><td>${orgMap[position.academy].name }</td></tr>
	    	<tr><td>系所/科室</td><td>${orgMap[position.department].name }</td></tr> 
	    	<tr><td>岗位名称</td><td>${position.positionName }</td></tr>
	    	<tr><td>岗位类型</td><td>${ide:getPositionType(position.positionType) }</td></tr> 
	    	<tr><td>岗位级别</td><td>${position.positionLevel }</td></tr>
	    	<tr><td>发文文号</td><td>${position.paper.paperNumber }</td></tr>
	    	<tr><td>发文文名</td><td>${position.paper.paperName }</td></tr>
	    	<tr><td>设岗时间</td><td><fmt:formatDate value="${ position.positionDay}" pattern='yyyy-mm-dd'/></td></tr>	    	
    	</table>
    </div>
     <div class="tb_sub_btns">
		<input type="button" value="关闭" class="cancel" onclick="art.dialog.close()" />
    </div>
  </body>



<!-- 新的岗位详情 -->
<!--
	<body>
		<div class="tb_940">
			<div id="Showform">
				<table cellpadding="0" cellspacing="0" class="basicInfo">
					<tr>
						<td style="font-size:18px;">发文名称</td>
						<td>
							${position.paper.paperName }
						</td>
						<td style="font-size:18px;">发文文号</td>
						<td>
							${position.paper.paperNumber }
						</td>
					</tr>
					<tr>
						<td style="font-size:18px;">发文时间</td>
						<td>
							${position.paper.paperDay}
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
				
				<table cellpadding="0" cellspacing="0" class="basicInfo">
				<tr>
					<td colspan="9">增、撤</td>
				</tr>
				<tr id="addAndRemove" style="background:#22B2AF;">
					<td style="width:40px;">序号</td>						
					<td style="width:70px;">变动类型</td>						
					<td style="width:140px;">院/单位</td>						
					<td style="width:140px;">系所/科室</td>						
					<td style="width:80px;">岗位</td>
					<td style="width:120px;">级别</td>
					<td style="width:150px;">类型</td>
					<td style="width:120px;">变动时间</td>
				</tr>
				<tr id="addAndRemove">
					<td style="width:40px;">序号</td>	
					<td style="width:70px;">变动类型</td>
					<td style="width:140px;">院/单位</td>						
					<td style="width:140px;">系所/科室</td>						
					<td style="width:80px;">岗位</td>
					<td style="width:120px;">级别</td>
					<td style="width:150px;">类型</td>
					<td style="width:120px;">变动时间</td>
				</tr>
				</table>
				
				<table cellpadding="0" cellspacing="0" class="basicInfo">
				<tr>
					<td colspan="9">变动</td>
				</tr>
				<tr id="change" style="background:#528fd2;">
					<td style="width:40px;">序号</td>
					<td style="width:70px;">类型</td>
					<td style="width:215px;">院/单位</td>
					<td style="width:140px;">系所/科室</td>
					<td style="width:80px;">岗位</td>
					<td style="width:120px;">级别</td>
					<td style="width:100px;">类型</td>
					<td style="width:120px;">变动时间</td>
				</tr>
				<tr id="change">
					<td style="width:40px;">序号</td>
					<td style="width:70px;">类型</td>
					<td style="width:215px;">院/单位</td>
					<td style="width:140px;">系所/科室</td>
					<td style="width:80px;">岗位</td>
					<td style="width:120px;">级别</td>
					<td style="width:100px;">类型</td>
					<td style="width:120px;">变动时间</td>
				</tr>
			</table>
			</div>
		</div>
	</body>
-->
</html>
