<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<script src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue" type="text/javascript"></script>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
	</head>
	<body>
		<div class="tb_box">
			<table class="tbexcel">
				<tr><th>用户账号：</th>
				<td><input id="username" name="username" type="text" value="${sysUser.userName }"
						class="txt readonly" readonly="readonly"/>
				</td></tr>
				<tr><th>用户姓名：</th>
				<td><input id="name" name="name" type="text" value="${sysUser.name }"
						class="txt readonly" readonly="readonly"/>
				</td></tr>
				<tr><th><span>*</span>新密码：</th>
				<td><input id="password" name="password" type="password" class="txt textString" />
					<input type="text" class="txt txtkey textStringTemp" value="密码不能少于6位" style="color:#DA1111; font-size:12px;"/>
				</td></tr>
				<tr><th><span>*</span>确认新密码：</th>
				<td><input id="rePassword" name="rePassword" type="password"
						class="txt" />
				</td></tr>
			</table>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="确定" class="add" id="saveBtn" />
			<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
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
						$.post("${ctx}/sys/user/resetPwd.do",
							{	
								id:'${sysUser.id}',
								password:password
							},function(result){
			                if("success" == result) {
			                	art.dialog.tips("重置密码成功");
			                	art.dialog.close();               
			                } else {
			                    art.dialog.tips("重置密码失败!");
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