<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<title>厦门大学干部信息管理系统</title>
		<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/content.css" type="text/css"  />
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
	</head>
  
  <body>
    <form id="addForm" action="${ctx}/secondment/addSecondmentUser.do" method="post">
    	<div class="tb_box secondmentUser">
	    	<input type="hidden" name="secondmentUserList[0].secondmentId" value="${secondment.id }"/>
			<table cellpadding="0" cellspacing="0" border="0" class="tbexcel">
				<tr>
					<th >挂职人员：</th>
					<td><input id="user" name="secondmentUserList[0].userId" type="text"
						class="txt" style="width:200px;"/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<th >挂职地点：</th>
					<td><input id="temporaryPlace" name="secondmentUserList[0].temporaryPlace" type="text"
						class="txt" style="width:200px;"/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<th >挂职单位：</th>
					<td><input id="temporaryUnit" name="secondmentUserList[0].temporaryUnit" type="text"
						class="txt" style="width:200px;"/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<th >挂职职务：</th>
					<td><input id="temporaryJob" name="secondmentUserList[0].temporaryJob" type="text"
						class="txt" style="width:200px;"/> <span style="color:red">*</span>
					</td>
				</tr>
				
				<tr>
					<th >考核情况：</th>
					<td><input id="assessementSitutation" name="secondmentUserList[0].assessementSitutation" type="text"
						class="txt" style="width:200px;"/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<th >备注：</th>
					<td><textarea id="remark" style="width:300px;height:80px;" name="remark" class="txt" cols="40" rows="3"></textarea>
					</td>
				</tr>

			</table>
		</div>
		
    </form>
    
    <script type="text/javascript" >
	function bindAddSecondmentUserBtn(){
		var index = $(".secondmentUser").size();
		var addForm = $("#addForm");
		var secondmentUserBlock = $(".secondmentUser").first();
		var html = secondmentUserBlock.html();
		var replaceStr = "secondmentUserList["+(index-1)+"]";
		var newStr = "secondmentUserList["+(index)+"]"; 
		html.replace(replaceStr,newStr);
		secondMentUserBlock.html(html);
		addForm.append(secondMentUserBlock);
	}
	</script>
  </body>
</html>
