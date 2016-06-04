<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/ny.css" />
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/index.css" />
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/comm.css"/>
		<script src="${ctx}/resources/js/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"
		type="text/javascript"></script>
	</head>
	<body>
		<div class="tb_box">
			<table cellpadding="0" cellspacing="0" border="0" class="tbexcel">
				<tr><th style="width:100px;"><span style="color:#DA1111">*</span>用户账号：</th>
				<td><input id="username" name="username" type="text" value="${username }"
						class="txt" readonly="readonly" style="width:200px;" />
						
				</td></tr>
				<tr><th style="width:100px;"><span style="color:#DA1111">*</span>新密码：</th>
				<td><input id="password" name="password" type="password" class="txt textString" style="width:200px;"/>
					<input type="text" class="txt txtkey textStringTemp" value="密码不能少于6位" style="width:200px;color:#DA1111;font-size:12px;"/>
				</td></tr>
				<tr><th style="width:100px;"><span style="color:#DA1111">*</span>确认新密码：</th>
				<td><input id="rePassword" name="rePassword" type="password"
						class="txt" style="width:200px;" />
				</td></tr>
			</table>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="确定" class="add" id="saveBtn" />
<!-- 			<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" /> -->
		</div>
		<script type="text/javascript">
			function bindSaveEvent(){
				$("#saveBtn").click(function(){
					var canSubmit = true;
					var password = $.trim($("#password").val());
					if (password == "") {
						art.dialog.alert("请输入密码");
						$("#password").focus();
						canSubmit = false;
						return;
					}else{
						if(password.length < 6){
							art.dialog.alert("新密码不能少于6位");
							$("#password").focus();
							canSubmit = false;
							return;
						}
					}
					var rePassword = $.trim($("#rePassword").val());
					if (rePassword == "") {
						art.dialog.alert("请输入确认密码");
						$("#rePassword").focus();
						canSubmit = false;
						return;
					}
					if (rePassword != password) {
						art.dialog.alert("两次输入的密码不一致");
						$("#rePassword").focus();
						canSubmit = false;
						return;
					}
					if(canSubmit){
						$.post("${ctx}/sys/user/changePwd.do",
							{	
								id:'${id}',
								password:password
							},function(result){
			                if("success" == result) {
			                	art.dialog.tips("修改成功");
			                	art.dialog.close();               
			                } else {
			                    art.dialog.tips("修改失败!");
			                }
			        	}); 				        	
			        }
				});
			}
			
			//绑定输入框提示事件
		    function bindQueryTextEvent(){
		    	$(".textString").each(function(){
		    		var thiz = $(this);
		    		var temp = thiz.parent().find(".textStringTemp");
			    	if($.trim(thiz.val())==""){
			            thiz.hide();
			            temp.show();
			        } else {
			            thiz.show();
			            temp.hide();
			        }
			        temp.focus(function(){
			            temp.hide();
			            thiz.show().focus();
			        });
			        thiz.blur(function(){
			            if($.trim(thiz.val())==""){
			                thiz.hide();
			                temp.show();
			            } 
			        });
		    	})
		    } 				
			$(function(){
				bindQueryTextEvent();
				bindSaveEvent();
			});
		</script>
	</body>
</html>