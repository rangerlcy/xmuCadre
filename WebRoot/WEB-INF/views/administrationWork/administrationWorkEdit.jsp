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
	<input type="hidden" value="${adm.id }" name="id">
	<div class="reTitle"><strong>修改行政工作经历</strong></div>
	<table class="basicInfo reportInfo" cellpadding="0" cellspacing="0">
		
		<tr>
			<td>姓名</td>
			<td >
				<input type="text" name="userName" value="${adm.user.name }" class="txt" readOnly/>
			</td>
		</tr>
		
		<tr><td>开始时间</td>
			<td><input id="beginDay" name="beginDay" type="text" value="<fmt:formatDate value='${adm.beginDay}'  pattern='yyyy-MM-dd'/>" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr>
		<tr><td>结束时间</td>
			<td><input id="endDay" name="endDay" type="text" value="<fmt:formatDate value='${adm.endDay }' pattern='yyyy-MM-dd'/>" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr>
		<tr><td>任职单位</td><td ><input id="units" name="units" type="text" value="${adm.units }" class="txt required" /></td></tr>
		<tr><td>单位类型</td><td >
						<select name="jobType" id="jobType">
								<option value="">请选择</option>
								<c:forEach items="${ide:getAllInstitutionType()}" var="kv">
									<c:if test="${kv.key==adm.jobType }">
									<option value="${kv.key }" selected>${kv.value }</option>
									</c:if>
									<c:if test="${kv.key!=adm.jobType }">
									<option value="${kv.key }">${kv.value }</option>
									</c:if>
								</c:forEach>
						</select>
		</td></tr>
		<tr><td>职务名称</td><td ><input id="jobName" name="jobName" type="text" value="${adm.jobName }" class="txt required" /></td></tr>
		<tr><td>人员级别</td><td >
						<select name="userLevel" id="userLevel">
								<option value="">请选择</option>
								<c:forEach items="${ide:getAllPositionLevel()}" var="kv">
									<c:if test="${kv.key==adm.userLevel }">
									<option value="${kv.key }" selected>${kv.value }</option>
									</c:if>
									<c:if test="${kv.key!=adm.userLevel }">
									<option value="${kv.key }">${kv.value }</option>
									</c:if>
								</c:forEach>
						</select>
		</td></tr>
		<tr>
			<td>核对情况</td>
			<td>
				<select id="checkCase" name="checkCase">
					<option value="请选择" <c:if test="${adm.checkCase == null ||adm.checkCase == '请选择'}">selected</c:if>> 请选择</option>
					<option value="已核对无误" <c:if test="${adm.checkCase == '已核对无误'}">selected</c:if>> 已核对无误</option>
					<option value="核对中" <c:if test="${adm.checkCase == '核对中'}">selected</c:if>> 核对中</option>
					<option value="未核对" <c:if test="${adm.checkCase == '未核对'}">selected</c:if>> 未核对</option>
				</select>
			</td>
		</tr>
		<tr><td>备注</td><td ><input id="remark" name="remark" type="text" value="${adm.remark }" class="txt required" /></td></tr>
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
    			$.post("${ctx}/administrationWork/administrationWorkEdit.do",data,function(result){
    				if ("success" == result){
    					
    					art.dialog({
    						time:1,
    						content:'修改成功，1秒后关闭'
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