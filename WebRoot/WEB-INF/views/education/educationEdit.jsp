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
	<input type="hidden" value="${edu.id }" name="id">
	<div class="reTitle"><strong>修改教育经历</strong></div>
	<table class="basicInfo reportInfo" cellpadding="0" cellspacing="0">
		
		<tr>
			<td>姓名</td>
			<td >
				<input type="text" name="userName" value="${edu.user.name }" class="txt" readOnly/>
			</td>
		</tr>
		
		<tr><td>开始时间</td>
			<td><input id="beginDay" name="beginDay" type="text" value="<fmt:formatDate value='${edu.beginDay}'  pattern='yyyy-MM-dd'/>" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr>
		<tr><td>结束时间</td>
			<td><input id="endDay" name="endDay" type="text" value="<fmt:formatDate value='${edu.endDay }' pattern='yyyy-MM-dd'/>" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr>
		<tr><td>学校</td><td ><input id="school" name="school" type="text" value="${edu.school }" class="txt required" /></td></tr>
		<tr>
			<td>学习形式</td>
			<td>
				<select id="learningForm" name="learningForm">
					<option value="请选择" <c:if test="${edu.learningForm == null ||edu.learningForm == '请选择'}">selected</c:if>> 请选择</option>
					<option value="全日制" <c:if test="${edu.learningForm == '全日制'}">selected</c:if>> 全日制</option>
					<option value="在职" <c:if test="${edu.learningForm == '在职'}">selected</c:if>> 在职</option>
					<option value="夜大学" <c:if test="${edu.learningForm == '夜大学'}">selected</c:if>> 夜大学</option>
					<option value="业余大学" <c:if test="${edu.learningForm == '业余大学'}">selected</c:if>> 业余大学</option>
					<option value="函授" <c:if test="${edu.learningForm == '函授'}">selected</c:if>> 函授</option>
					<option value="网络教育" <c:if test="${edu.learningForm == '网络教育'}">selected</c:if>> 网络教育</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学习阶段</td>
			<td>
				<select id="learningPhase" name="learningPhase">
					<option value="请选择" <c:if test="${edu.learningPhase == null ||edu.learningPhase == '请选择'}">selected</c:if>> 请选择</option>
					<option value="中专" <c:if test="${edu.learningPhase == '中专'}">selected</c:if>> 中专</option>
					<option value="大专" <c:if test="${edu.learningPhase == '大专'}">selected</c:if>> 大专</option>
					<option value="本科" <c:if test="${edu.learningPhase == '本科'}">selected</c:if>> 本科</option>
					<option value="硕士研究生" <c:if test="${edu.learningPhase == '硕士研究生'}">selected</c:if>> 硕士研究生</option>
					<option value="博士研究生" <c:if test="${edu.learningPhase == '博士研究生'}">selected</c:if>> 博士研究生</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学习状态</td>
			<td>
				<select id="learningState" name="learningState">
					<option value="请选择" <c:if test="${edu.learningState == null ||edu.learningState == '请选择'}">selected</c:if>> 请选择</option>
					<option value="毕业" <c:if test="${edu.learningState == '毕业'}">selected</c:if>> 毕业</option>
					<option value="结业" <c:if test="${edu.learningState == '结业'}">selected</c:if>> 结业</option>
					<option value="肄业" <c:if test="${edu.learningState == '肄业'}">selected</c:if>> 肄业</option>
					<option value="在读" <c:if test="${edu.learningState == '在读'}">selected</c:if>> 在读</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学位</td>
			<td>
				<select id="degree" name="degree">
					<option value="请选择" <c:if test="${edu.degree == null ||edu.degree == '请选择'}">selected</c:if>> 请选择</option>
					<option value="无" <c:if test="${edu.degree == '无'}">selected</c:if>> 无</option>
					<option value="学士" <c:if test="${edu.degree == '学士'}">selected</c:if>> 学士</option>
					<option value="硕士" <c:if test="${edu.degree == '硕士'}">selected</c:if>> 硕士</option>
					<option value="博士" <c:if test="${edu.degree == '博士'}">selected</c:if>> 博士</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学位类型</td>
			<td>
				<select id="degreeType" name="degreeType">
					<option value="请选择" <c:if test="${edu.degreeType == null ||edu.degreeType == '请选择'}">selected</c:if>> 请选择</option>
					<option value="文学" <c:if test="${edu.degreeType == '文学'}">selected</c:if>> 文学</option>
					<option value="理学" <c:if test="${edu.degreeType == '理学'}">selected</c:if>> 理学</option>
					<option value="工学" <c:if test="${edu.degreeType == '工学'}">selected</c:if>> 工学</option>
					<option value="经济学" <c:if test="${edu.degreeType == '经济学'}">selected</c:if>> 经济学</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>核对情况</td>
			<td>
				<select id="checkCase" name="checkCase">
					<option value="请选择" <c:if test="${edu.checkCase == null ||edu.checkCase == '请选择'}">selected</c:if>> 请选择</option>
					<option value="已核对无误" <c:if test="${edu.checkCase == '已核对无误'}">selected</c:if>> 已核对无误</option>
					<option value="核对中" <c:if test="${edu.checkCase == '核对中'}">selected</c:if>> 核对中</option>
					<option value="未核对" <c:if test="${edu.checkCase == '未核对'}">selected</c:if>> 未核对</option>
				</select>
			</td>
		</tr>
		<tr><td>备注</td><td ><input id="remark" name="remark" type="text" value="${edu.remark }" class="txt required" /></td></tr>
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
    			$.post("${ctx}/education/educationEdit.do",data,function(result){
    				if ("success" == result){
    					art.dialog.tips("修改成功");
    					art.dialog.opener.location.reload();
				        art.dialog.close();  
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