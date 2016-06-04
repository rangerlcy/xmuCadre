<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/ny.css" />
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
	</head>
	<body>
	    <div class="tb_700">
	      
			<table cellpadding="0" cellspacing="0" class="basicInfo">
			   
				<tr>
					<td >发文文号：</td>
					<td><input id="paperNumber" name="paperNumber" type="text"
						class="txt" style="width:200px;" value="${secondment.paper.paperNumber }" readonly="readonly" maxlength=50/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td >文件题目：</td>
					<td><input id="paperName" name="paperName" type="text"
						class="txt" style="width:200px;" value="${secondment.paper.paperName }" readonly="readonly" maxlength=50/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td >发文单位：</td>
					<td><input id="paperUnits" name="paperUnits" type="text"
						class="txt" style="width:200px;"  value="${secondment.paper.paperUnits }" readonly="readonly" maxlength=50/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td >挂职项目名称：</td>
					<td><input id="temporaryProjectName" name="temporaryProjectName" type="text"
						class="txt" style="width:200px;" value="${secondment.temporaryProjectName }"  maxlength=50/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td >挂职人员要求：</td>
					<td><input id="temporaryRequirement" name="temporaryRequirement" type="text"
						class="txt" style="width:200px;" value="${secondment.temporaryRequirement }" maxlength=120/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr><td class="text-right">挂职开始时间</td>
				<td class="text-left">
					<input type="text" name="temporaryBeginDay" id="temporaryBeginDay" class="txt Wdate " onfocus="WdatePicker({readOnly:false})" style="width:200px;"  value="<fmt:formatDate value='${secondment.temporaryBeginDay }'  pattern='yyyy-MM-dd'/>">
					<span class="errormsg"></span>
				</td>
				</tr>
					<tr>
					<td class="text-right">挂职结束时间</td>
					<td class="text-left">
						<input type="text" name="temporaryEndDate" id="temporaryEndDate" class="txt Wdate " onfocus="WdatePicker({readOnly:false})" style="width:200px;"  value="<fmt:formatDate value='${secondment.temporaryEndDate }'  pattern='yyyy-MM-dd'/>">
						<span class="errormsg"></span>
					</td>
				</tr>				
				<tr>
					<td >备注：</td>
					<td><textarea id="remark" style="width:300px;height:80px;" name="remark" class="txt" cols="40" rows="3"  maxlength=500>${secondment.remark }</textarea>
					</td>
				</tr>

			</table>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="确定" class="add" id="saveBtn" />
			<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
		</div>
	    <script type="text/javascript">
	        $(function(){
	        	$("#paperNumber").focus();
	        	bindSaveEvent();//绑定提交表单事件
	        });
	        
	       function bindSaveEvent(){
				$("#saveBtn").click(function(){
	           		if($.trim($("#temporaryProjectName").val())==""){
	                   art.dialog.alert("请输入挂职项目名称");
	                   return;
	           		}
	           		if($.trim($("#temporaryRequirement").val())==""){
	                    art.dialog.alert("请输入挂职要求");
	                    return;
	                }
					var canSubmit = true;
	                
					if(canSubmit){
		                $.post("${ctx}/secondment/updateSecondments.do",
		                { 
		                        id:"${secondment.id}",
								temporaryProjectName:$.trim($("#temporaryProjectName").val()),	
								temporaryBeginDay:$.trim($("#temporaryBeginDay").val()),	
								temporaryEndDate:$.trim($("#temporaryEndDate").val()),	
								temporaryRequirement:$.trim($("#temporaryRequirement").val()),	
			
								remark:$.trim($("#remark").val())

								},
		                function(result){
				                if("success" == result) {
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();               
				                } else {
				                    art.dialog.tips("修改失败!");
				                }
	                });
	                }		
				});
			}
	    </script>
	</body>
</html>