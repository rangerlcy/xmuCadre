<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
<title>厦门大学干部信息管理系统</title>
<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${res }/css/ny.css" type="text/css" />

</head>
<body class="tb_700">
<form id="Editform" action="" enctype="multipart/form-data" onsubmit="return false">
	<input type="hidden" value="${skill.id }" name="id">
	<div class="reTitle"><strong>修改专业技术职务经历</strong></div>
	<table class="basicInfo reportInfo" cellpadding="0" cellspacing="0">
		
		<tr>
			<td>姓名</td>
			<td >
				<input type="text" name="userName" value="${skill.user.name }" class="txt" readOnly/>
			</td>
		</tr>
		
		<tr><td>开始时间</td>
			<td><input id="beginDay" name="beginDay" type="text" value="<fmt:formatDate value='${skill.beginDay}'  pattern='yyyy-MM-dd'/>" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr>
		<tr><td>结束时间</td>
			<td><input id="endDay" name="endDay" type="text" value="<fmt:formatDate value='${skill.endDay }' pattern='yyyy-MM-dd'/>" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr><tr>
			<td>聘任级别</td>
			<td>
				<select id="employmentLevel" name="employmentLevel">
					<c:forEach items="${ide:getAllItems('employmentLevel')}" var = "kv">
						<option value="${kv.key }" <c:if test="${kv.key eq skill.employmentLevel}">selected</c:if>>${kv.value }</option>
					</c:forEach>
				</select>
			</td>
		</tr><tr><td>专业技术职务</td><td ><input id="skillName"  name="skillName"  type="text" value="${skill.skillName }" class="txt required" /></td></tr>
		<tr>
			<td>核对情况</td>
			<td>
				<select id="checkCase" name="checkCase">
					<option value="请选择" <c:if test="${skill.checkCase == null ||skill.checkCase == '请选择'}">selected</c:if>> 请选择</option>
					<option value="已核对无误" <c:if test="${skill.checkCase == '已核对无误'}">selected</c:if>> 已核对无误</option>
					<option value="核对中" <c:if test="${skill.checkCase == '核对中'}">selected</c:if>> 核对中</option>
					<option value="未核对" <c:if test="${skill.checkCase == '未核对'}">selected</c:if>> 未核对</option>
				</select>
			</td>
		</tr>
		<tr><td>备注</td><td ><input id="remark" name="remark" type="text" value="${skill.remark }" class="txt required" /></td></tr>
	</table>
</form>
	
	<br/>
	<div class="tb_sub_btns">
		<input type="submit" value="确定" class="edit" id="editBtn" />
		<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
	</div>
	
<script>

function bindEditBtnEvent(){
    		var time=2;
    		
    		$("#editBtn").click(function(){    		
    			var data = $("#Editform").serialize();
    			$.post("${ctx}/skill/skillEdit.do",data,function(result){
    				if ("success" == result){
    					
    					art.dialog({
    						time:2,
    						content:'修改成功，2秒后关闭'
    					});
    					function update(){
    						if(time>1){
    							time--;
    						}
    						else{
    							art.dialog.opener.location.reload();
    						}
    					}
    					setInterval(update,1000);
    					
    				}
    				else{
    					art.dialog.tips("修改失败");   					
    				}
    				
    			});
    		});
    	}
  	
    	bindEditBtnEvent();
    	
</script>
</body>

</html>