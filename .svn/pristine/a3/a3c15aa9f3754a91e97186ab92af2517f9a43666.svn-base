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
			<form id="queryRoleForm" action="${ctx }/sys/role/sysRoleList.do" method="post">
				<div class="form_head"><span>人员管理</span></div>
				<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }"/>
			    <input type="text" id="queryStrTemp" class="txt txtkey" value="角色名称/描述"/>
			    <input id="searchBtn" type="button" class="btn searchBtn" value="查询" />
			    <input id="addBtn" type="button" value="添加" class="btn search_btn" />
		    </form>
		</div>
    	<div>
	    <table class="tblist">
	        <tr>
	            <th>角色名称</th>
	            <th>角色代码</th>
	            <th>角色描述</th>
	            <th class="c">操作</th>
	        </tr>
	        <c:if test="${queryList.result != null }">
	          <c:forEach items="${queryList.result }" var="role">
	              <tr>
	                <td id="role_name_${role.id }">${role.name }</td>
	                <td id="role_code_${role.id }">${role.code }</td>
	                <td id="role_remark_${role.id }">${role.remark }</td>
	                <td>
                    	<input type="button" class="btn btn_cz updateBtn" value="修改" roleId="${role.id }">
	                    <input type="button" class="btn btn_cz delBtn" value="删除" roleId="${role.id }">
	                    <input type="button" class="btn btn_cz grantBtn" value="授权" roleId="${role.id }">
	                    <input type="button" class="btn btn_cz addUserBtn" value="选择用户" roleId="${role.id }">
	                    <input type="button" class="btn btn_cz importRoleUserBtn" value="批量导入用户" roleId="${role.id }">
	                </td>
	            </tr>
	          </c:forEach>
	        </c:if>
	    </table>
	    <ide:page page="${queryList }" pageId="roleListPage" searchForm="queryRoleForm"></ide:page>
    </div>
    <script type="text/javascript">
        $(function(){
            bindSubmitFormEvent();//绑定提交表单事件
            bindUpdateBtnEvent(); //绑定修改按钮事件
            bindDelBtnEvent(); //绑定删除按钮事件
            bindGrantBtnEvent(); //绑定授权按钮事件
            bindAddUserBtnEvent();//角色选择用户事件
            bindQueryTextEvent();//绑定查询输入框
            bindSearchEvent();//绑定搜索框
            bindImportRoleUserBtnEvent();//批量导入角色用户
        });
        function bindSearchEvent(){
			$("#searchBtn").click(function(){
                $("#queryRoleForm").submit();
   			});
		}
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
        function bindSubmitFormEvent(){
            $("#addBtn").click(function(){
            	art.dialog.open("${ctx}/sys/role/preAddSysRole.do", 
                        { title: "新增角色", lock: true,width:500,height:300}
                );
            });
        }
        
        function bindUpdateBtnEvent(){
            $(".updateBtn").click(function(){
                art.dialog.open("${ctx}/sys/role/preUpdateSysRole.do?roleId="+$(this).attr("roleId"), 
                        { title: "修改角色名称", lock: true,width:500,height:300}
                );
            });
        }
        
        function bindDelBtnEvent(){
            $(".delBtn").click(function(){
                var thiz = $(this);
                var roleId = $(this).attr("roleId");
                art.dialog.confirm("确定要删除该角色吗？<br />此操作不可恢复，请谨慎使用！",function(){
                    $.post("${ctx}/sys/role/delSysRole.do",{roleId:roleId},function(result){
                        if("success" == result) {
                            thiz.parent().parent().remove();
                        } else {
                            art.dialog.alert("删除角色失败!");
                        }
                    });
                });
            });
        }
        
        function bindGrantBtnEvent() {
            $(".grantBtn").click(function(){
                art.dialog.open("${ctx}/sys/role/preAddRoleResouces.do?roleId="+$(this).attr("roleId"), 
                        { title: "角色资源分配", lock: true,width:400,height:500});
            });
        }
        function bindAddUserBtnEvent() {
            $(".addUserBtn").click(function(){
                art.dialog.open("${ctx}/sys/role/preAddRoleUser.do?roleId="+$(this).attr("roleId"), 
                        { title: "角色用户配置", lock: true,width:400,height:500});
            });
        }
        function bindImportRoleUserBtnEvent() {
            $(".importRoleUserBtn").click(function(){
                art.dialog.open("${ctx}/sys/role/preImportRoleUser.do?roleId="+$(this).attr("roleId"), 
                        { title: "批量导入角色用户", lock: true,width:720,height:400});
            });
        }
    </script>
</body>
</html>