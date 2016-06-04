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
			<table class="tbexcel">
				<tr>
					<th><span>*</span>登陆账号：</th>
					<td >
					<input id="username" name="username" type="text" value="${sysUser.userName }"
						class="txt" readonly="readonly"/>
					</td>
					<td><span>提示：初始密码与登陆账号一致</span></td>
				</tr>
				<tr>
					<th><span>*</span>用户姓名：</th>
					<td colspan="2"><input id="name" name="name" type="text"  value="${sysUser.name }"
						class="txt required"/>
						<span class="ts"></span>
					</td>
				</tr>
				<tr>
					<th >所属角色：</th>
					<td colspan="2">
						<c:if test="${sysRoles != null }">
							<ul class="ul_selblock" style="margin:0px;" >
							<c:forEach items="${sysRoles}" var="sysRole">
				                <c:set var="checked" value="false"></c:set>
				                <c:forEach items="${userRoles }" var="uRole">
				                	<c:if test="${uRole.sysRole.id eq sysRole.id}">
				                        <c:set var="checked" value="true"></c:set>
				                    </c:if>
				                </c:forEach>
		    					<li roleId="${sysRole.id }" <c:if test="${checked}" >class="on"</c:if> >${sysRole.name }</li>
							</c:forEach>
							</ul>				
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="确定" class="add" id="saveBtn"/> 
			<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
		</div>
		<script type="text/javascript">
		function bindSaveEvent(){
			$("#saveBtn").click(function(){
				var canSubmit = true;
				var canUse = true;			
				$(".ts").hide();
				var username = $.trim($("#username").val());
				var name = $.trim($("#name").val());
				
				$(".required").each(function(){
					var thiz = $(this);
					if(thiz.val() == ''){
						canSubmit = false;
						thiz.parent().find(".ts").text("不能为空").show();
					}
				})
				
				if(!canSubmit){
					return;
				}else{
					var roleIds = [];
					$(".ul_selblock li").each(function(){
						if($(this).hasClass("on")){
							var roleId = $(this).attr("roleId");
							roleIds.push(roleId);
						}
					});
					$.post("${ctx}/sys/user/userEdit.do",{
							userId:'${sysUser.id}',	
							username:username,
							name:name,
							roleIds:roleIds.join(',')
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
		
		//角色选中事件
		function bindRoleClickEvent(){
			$(".ul_selblock li").click(function(){
				if($(this).hasClass("on"))
					$(this).removeClass("on");
				else
					$(this).addClass("on");			
			});
		}
		
		$(function(){
			bindSaveEvent();
			bindRoleClickEvent();
		});
		</script>
	</body>
</html>