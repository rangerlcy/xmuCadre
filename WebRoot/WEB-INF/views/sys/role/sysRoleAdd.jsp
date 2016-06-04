<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/ny.css" />
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
	</head>
	<body>
	    <div class="tb_box">
			<table cellpadding="0" cellspacing="0" border="0" class="tbexcel">
				<tr>
					<th >角色代码：</th>
					<td><input id="code" name="code" type="text"
						class="txt" style="width:200px;" onkeyup="value=value.replace(/[^\w ]/g,'')" maxlength=32/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<th >角色名称：</th>
					<td><input id="name" name="name" type="text"
						class="txt" style="width:200px;"onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=128/> <span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<th >角色描述：</th>
					<td><textarea id="remark" style="width:300px;height:80px;" name="remark" class="txt" cols="40" rows="3" onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ,;:：，；。.《》<>\n、]/g,'')" maxlength=250></textarea>
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
	        	$("#code").focus();
	        	bindSaveEvent();//绑定提交表单事件
	        });
	        
	        function bindSaveEvent(){
				$("#saveBtn").click(function(){
					var canSubmit = true;
	                if($.trim($("#code").val())==""){
	                    art.dialog.alert("请输入角色代码");
	                    return;
	                }else{
		                $.ajax({
						      url: "${ctx}/sys/role/checkCode.do",
						      data:{code:$.trim($("#code").val())},
						      type: 'POST',
						      async:false,
						      success: function(result){
				                if("success" != result){
				                	canSubmit = false;
				                }
					       	  }
					    });                
	                }
	                if(!canSubmit){
						art.dialog.alert("该角色代码已被使用");
	                    return;
					}
	                if($.trim($("#name").val())==""){
	                    art.dialog.alert("请输入角色名称");
	                    return;
	                }
					if(canSubmit){
		                $.post("${ctx}/sys/role/addSysRole.do",{
		                		code:$.trim($("#code").val()),	
								name:$.trim($("#name").val()),
								remark:$.trim($("#remark").val())
							},function(result){
				                if("success" == result) {
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();               
				                } else {
				                    art.dialog.tips("保存失败!");
				                }
			        	});					
					}
				});
			}
	    </script>
	</body>
</html>