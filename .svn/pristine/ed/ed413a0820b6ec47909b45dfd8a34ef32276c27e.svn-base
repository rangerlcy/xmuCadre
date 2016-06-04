<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<title>厦门大学干部信息管理系统</title>
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/content.css" type="text/css"  />
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
	</head>
	<body class="ny_body">
		<div class="r_search">
			<form id="searchUserForm" action="${ctx}/sys/user/userList.do" method="post">
				<div class="form_head"><span>用户管理</span></div>
				<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"/>
		    	<input type="text" id="queryStrTemp" class="txt txtkey" value="用户账号/姓名" style="width:220px;"/>
				<input type="submit" value="查询" class="btn search_btn" />
				<input type="button" class="btn addBtn" value="添加" style="margin-left:10px;"/>
			</form>
		</div>
		<div>
			<table class="tblist">
				<tr>
					<th>登陆账号</th>
					<th>用户姓名</th>
					<th class="c">操作</th>
				</tr>
				<c:forEach items="${queryList.result }" var="kv">
					<tr>
						<td>${kv.user.userName }</td>
						<td>${kv.user.name }</td>
						<td class="c" TID="${kv.user.id }">
							<input type="button" class="btn btn_cz setPwdBtn" value="重置密码" >
							<input type="button" class="btn btn_cz editBtn" value="修改" >
							<input type="button" class="btn btn_cz delBtn" value="删除" >
						</td>
					</tr>
				</c:forEach>
			</table>
			<ide:page page="${queryList }" pageId="userListPage" searchForm="searchUserForm"></ide:page>
		</div>
	</body>
	<script type="text/javascript">
		//绑定查询输入框事件
	    function bindQueryTextEvent(){
	    	if($.trim($("#queryStr").val())==""){
	            $("#queryStr").hide();
	            $("#queryStrTemp").show();
	        } else {
	            $("#queryStr").show();
	            $("#queryStrTemp").hide();
	        }
	        $("#queryStrTemp").focus(function(){
	            $(this).hide();
	            $("#queryStr").show().focus();
	        });
	        $("#queryStr").blur(function(){
	            if($.trim($("#queryStr").val())==""){
	                $("#queryStr").hide();
	                $("#queryStrTemp").show();
	            } 
	        });
	    }
	    //添加操作
		function bindAddEvent() {
			$(".addBtn").click(function(){
				art.dialog.open(
					"${ctx}/sys/user/openUserAdd.do",
					{ title: "添加用户", lock: true, width: 700, height:270 }
				);				
			});
		}
		
		//修改操作
		function bindEditEvent() {
			$(".editBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/sys/user/openUserEdit.do?id="+id,
					{ title: "修改用户", lock: true, width: 700, height: 270 }
				);				
			});
		}
		//重置密码操作
		function bindResetPwdEvent() {
			$(".setPwdBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/sys/user/openResetPwd.do?id="+id,
					{ title: "重置密码", lock: true, width: 400, height: 270 }
				);				
			});
		}
		//删除操作
		function bindDelEvent() {
			$(".delBtn").click(function(){
				var thiz = $(this);
				var id = thiz.parent().attr("TID");
				art.dialog.confirm("确定删除？",function(){
					$.post("${ctx}/sys/user/userDel.do",{id:id},
						function(result){
			                if("success" == result) {
			                	location.href="${ctx}/sys/user/userList.do";               
			                } else {
			                    art.dialog.tips("删除失败");
			                }
			        	}
			        );
				});					
			});
		}

	    $(function(){
	    	bindQueryTextEvent();
	    	bindResetPwdEvent();
	    	bindAddEvent();
	    	bindEditEvent();
	    	bindDelEvent();
    		//搜索按钮
   			$(".search_btn").click(function(){
                $("#searchUserForm").submit();
   			});
	    });
	</script>
</html>