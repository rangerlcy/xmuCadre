<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide"%>
<script type="text/javascript"
	src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
<title>厦门大学干部信息管理系统</title>
<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${res }/css/ny.css" type="text/css" />
</head>
<body class="tb_700">
<form id="addForm" method="post" action="${ctx}/administrationWork/administrationWorkAdd.do" enctype="multipart/form-data" onsubmit="return false">
	<div class="reTitle"><strong>添加行政工作经历</strong></div>
	<table class="basicInfo reportInfo" cellpadding="0" cellspacing="0">
		<tr>
			<td>姓名</td>
			<td >
				<input id="userName" name="userName" type="text" class="txt" maxlength=50/>
				<div id="searchResultOutput" class='absoluteDiv'></div>
				<input type="hidden" value="" name="userId" id="userId"/>
			</td>
		</tr>
		
		<tr><td>开始时间</td>
			<td><input id="beginDay" name="beginDay" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr>
		<tr><td>结束时间</td>
			<td><input id="endDay" name="endDay" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" /></td>
		</tr>
		<tr><td>任职单位</td><td ><input id="units" name="units" type="text" class="txt required" /></td></tr>
		<tr><td>单位类型</td><td >
							<select name="jobType" id="jobType">
								<option value="">请选择</option>
								<c:forEach items="${ide:getAllInstitutionType()}" var="kv">
									<option value="${kv.key }">${kv.value }</option>
								</c:forEach>
							</select>
		</td></tr>
		<tr><td>职务名称</td><td ><input id="jobName" name="jobName" type="text" class="txt required" /></td></tr>
		<tr><td>人员级别</td>
						<td>
							<select name="userLevel" id="userLevel">
								<option value="">请选择</option>
								<c:forEach items="${ide:getAllPositionLevel()}" var="kv">
									<option value="${kv.key }">${kv.value }</option>
								</c:forEach>
							</select>
						</td></tr>
		<tr>
			<td>核对情况</td>
			<td>
				<select id="checkCase" name="checkCase">
					<option value="" selected>请选择</option>
					<option value="已核对无误">已核对无误</option>
					<option value="核对中">核对中</option>
					<option value="未核对">未核对</option>
				</select>
			</td>
		</tr>
		<tr><td>备注</td><td ><input id="remark" name="remark" type="text" class="txt required" /></td></tr>
		</table>
</form>
	
	<br/>
	<div class="tb_sub_btns">
		<input type="submit" value="确定" class="add" id="submitBtn" /> 
		<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
	</div>

<script>
$(document).ready(function(){
	 		$("#submitBtn").click(function(){
	 			//至少需输入姓名后才可添加
	 			if ($.trim($("#userName").val()) == "") {
							art.dialog.alert("姓名不能为空!");
							return;
						}
				
				$.post("${ctx}/administrationWork/administrationWorkAdd.do",{
		                		userId:$.trim($("#userId").val()),
								beginDay:$.trim($("#beginDay").val()),
								endDay:$.trim($("#endDay").val()),
								units:$.trim($("#units").val()),	
								jobName:$.trim($("#jobName").val()),
								jobType:$.trim($("#jobType").val()),		
								userLevel:$.trim($("#userLevel").val()),
								checkCase:$.trim($("#checkCase").val()),	
								remark:$.trim($("#remark").val()),	
							},function(result){
				                if("success" == result) {
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();               
				                } else if( "fail"==result){
				       
				                    art.dialog.tips("保存失败!查无此员工");
				                }
			        	});	
			});
		});
		
		//动态获取userName
			var userTemp ='';
	        function bindSearchUserEvent(){
	        	$("#userName").keyup(function(){
	        		var thiz = $(this);
	        		var user = thiz.val();
	        		if (user == '') {
	        			$("#searchResultOutput").html('');
	        			return;
	        		}
	        		userTemp = user;
	        		$.post("${ctx}/infoManager/userInfo/existUserName.do",{userName:user },function(result){
	        			if(null == result || 0 == result.length){
	        				thiz.parent().find("label").html("被举报人不存在");
	        				$("#selectName").remove();
	        			}
	        			 	
	        			else if (result.length>=1){
	        				var htmlStr="<ul id='selectName'>";
	        				for (var i =0;i<result.length;i++){
								htmlStr += "<li><a href='javascript:showUser("+result[i].id+")'>" + result[i].name + "</a><button onclick=\"chooseUser("+result[i].id+",'"+result[i].name+"')\">选择</button></li>";        				
	        				}
	        				htmlStr += "</ul>";
	        				thiz.parent().find("#searchResultOutput").html(htmlStr);
	        				$("#userName").bind("blur.hide",function(){
								$("#searchResultOutput").html('');
							});
							$("#selectName").hover(function(){//进入失去函数绑定
								$("#userName").unbind("blur.hide");
							},function(){//出去获取函数绑定
								$("#userName").bind("blur.hide",function(){
									$("#searchResultOutput").html('');
								});
							});
	        			}
	        		},"json");
	        	});
	        };
	        function showUser(id){
	        	//用户详情
				art.dialog.open(
					"${ctx}/infoManager/userInfo/openUserDetail.do?id="+id,
					{ title: "用户详情", lock: true, width: 996, height: 600 }
				);	
	        }
	        function chooseUser(id,name){
	      	  	var thiz = $("#userId");
	        	thiz.parent().find("#searchResultOutput").html("");
	        	$("#userName").val(name);
	        	$("#userId").val(id);	
	        };
	       	bindSearchUserEvent();
</script>
</body>

</html>