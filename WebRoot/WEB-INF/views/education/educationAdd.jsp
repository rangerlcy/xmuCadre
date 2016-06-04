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
<form id="addForm" method="post" action="" enctype="multipart/form-data" onsubmit="return false">
	<div class="reTitle"><strong>添加教育经历</strong></div>
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
		<tr><td>学校</td><td ><input id="school" name="school" type="text" class="txt required" /></td></tr>
		<tr>
			<td>学习形式</td>
			<td>
				<select id="learningForm" name="learningForm">
					<option value="" selected>请选择</option>
					<option value="全日制" >全日制</option>
					<option value="在职" >在职</option>
					<option value="夜大学" >夜大学</option>
					<option value="业余大学" >业余大学</option>
					<option value="函授" >函授</option>
					<option value="网络教育" >网络教育</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学习阶段</td>
			<td>
				<select id="learningPhase" name="learningPhase">
					<option value="" selected>请选择</option>
					<option value="中专" >中专</option>
					<option value="大专" >大专</option>
					<option value="本科" >本科</option>
					<option value="硕士研究生" >硕士研究生</option>
					<option value="博士研究生" >博士研究生</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学习状态</td>
			<td>
				<select id="learningState" name="learningState">
					<option value="" selected>请选择</option>
					<option value="毕业" >毕业</option>
					<option value="结业" >结业</option>
					<option value="肄业" >肄业</option>
					<option value="在读" >在读</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学位</td>
			<td>
				<select id="degree" name="degree">
					<option value="" selected>请选择</option>
					<option value="无" >无</option>
					<option value="学士" >学士</option>
					<option value="硕士" >硕士</option>
					<option value="博士" >博士</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>学位类型</td>
			<td>
				<select id="degreeType" name="degreeType">
					<option value="" selected>请选择</option>
					<option value="文学" >文学</option>
					<option value="理学" >理学</option>
					<option value="工学" >工学</option>
					<option value="经济学" >经济学</option>
				</select>
			</td>
		</tr>
		<tr><td>备注</td><td ><input id="remark" name="remark" type="text" class="txt required" /></td></tr>
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
	</table>
</form>
	
	<br/>
	<div class="tb_sub_btns">
		<input type="button" value="确定" class="add" id="submitBtn" /> 
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
				
				$.post("${ctx}/education/educationAdd.do",{
		                		userId:$.trim($("#userId").val()),
								beginDay:$.trim($("#beginDay").val()),
								endDay:$.trim($("#endDay").val()),
								school:$.trim($("#school").val()),	
								learningForm:$.trim($("#learningForm").val()),	
								learningPhase:$.trim($("#learningPhase").val()),	
								learningState:$.trim($("#learningState").val()),	
								degree:$.trim($("#degree").val()),	
								degreeType:$.trim($("#degreeType").val()),
								remark:$.trim($("#remark").val()),
								checkCase:$.trim($("#checkCase").val()),
							},function(result){
				                if("success" == result) {
				                	alert("添加成功！");
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();               
				                } else if( "fail"==result){
				       
				                    art.dialog.tips("添加失败，请检查数据是否正确");
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
	        				thiz.parent().find("label").html("该人员不存在");
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