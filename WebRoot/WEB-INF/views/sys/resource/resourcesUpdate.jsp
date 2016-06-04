<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	    <title>厦门大学干部信息管理系统</title>
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>	
	</head>
	
	<body class="ny_body">
	    <div class="r_search" style="border-bottom: 0px;" >
            <div class="tb_box">
		        <table cellpadding="0" cellspacing="0" border="0" class="tbexcel">
		        <tr>
		            <th><span style="color: red">*</span>资源类型</th>
		            <td>
		              <select id="resourceType" name="resourceType" class="select">
		                  <option value="1" <c:if test="${resources.resourceType==1 }">selected="selected"</c:if>>菜单</option>
		                  <option value="2" <c:if test="${resources.resourceType==2 }">selected="selected"</c:if>>按钮</option>
		              </select>
		              <span class="ts"></span>
		            </td>
		        </tr>
		        <tr>
		            <th><span style="color: red">*</span>资源名称</th>
		            <td>
		                <input id="name" name="name" type="text" class="txt" style="width:472px" value="${resources.name }" maxlength="60"/>
		                <span id="name_ts" class="ts">请输入资源名称</span>
		            </td>
		        </tr>
		        <tr>
                    <th>权限编号</th>
                    <td>
                        <input id="securityCode" name="securityCode" type="text" class="txt" style="width:472px" value="${resources.securityCode }" maxlength="60"/>
                        <span id="securityCode_ts" class="ts"></span>
                    </td>
                </tr>
		        <tr>
                    <th>排序值</th>
                    <td>
                        <input id="orderNum" name="orderNum" type="text" class="txt" style="width:472px" value="${resources.orderNum }" maxlength="3"/>
                        <span id="orderNum_ts" class="ts">排序值必须为数字</span>
                    </td>
                </tr>
		        <tr>
		            <th>菜单URL<!-- <span style="color: red">*</span> --></th>
		            <td>
		                <input id="url" name="url" type="text" class="txt" style="width:472px" value="${resources.url }" maxlength="128"/>
                        <span id="url_ts" class="ts"></span>
		            </td>
		        </tr>
		        <tr>
		            <th>菜单样式名<!-- <span style="color: red">*</span> --></th>
		            <td>
		                <input id="cssClass" name="cssClass" type="text" class="txt" style="width:472px" value="${resources.cssClass }" maxlength="128"/>
                        <span class="ts"></span>
		            </td>
		        </tr>
		        <tr>
		          <td colspan="2">
		              <div class="tb_sub_btns"><input id="saveBtn" type="button" value="保存" class="add" style="cursor: pointer;margin-right:400px;"/>
                      <span id="saveStauts" class="ts"></span>
		          </td>
		        </tr>
		        </table>
		    </div>
	    </div>
	    <script type="text/javascript">
	        $(function(){
	        	$(".ts").hide();
	            $("#name").focus();
	            bindSubmitFormEvent();//绑定提交表单事件
	        });
	        
	        //更新资源编码
	        function bindSubmitFormEvent(){
	            $("#saveBtn").click(function(){
	            	var canSubmit = true;
	                
	                if($.trim($("#name").val())==""){
	                	$("#name_ts").show();
	                    return;
	                }
	                var orderNum = $.trim($("#orderNum").val());
	                if(orderNum !="" && isNaN(orderNum)){
	                    $("#orderNum_ts").show();
	                    return;
	                }
	                var data ={
	                        id:"${resources.id}",
	                        parentId:"${resources.parentId}",
	                        name:$.trim($("#name").val()),
	                        url:$.trim($("#url").val()),
	                        cssClass:$.trim($("#cssClass").val()),
	                        resourceType:$("#resourceType").val(),
	                        orderNum:$("#orderNum").val(),
	                        securityCode:$("#securityCode").val()
	                        
	                };
	                if(window.parent.getNode("${resources.id}") != null ) {
		                $.post("${ctx}/sys/resource/updateSysResources.do",data,function(result){
		                    if("success" == result) {
		                    	art.dialog.tips("保存成功");
		                    	window.parent.updateNodeName("${resources.id}",$.trim($("#name").val()));
		                    	$(".ts[id!='saveStauts']").hide();
		                    } else {
		                    	art.dialog.tips("保存失败");
		                    }
		                }); 
	                } else {
	                	art.dialog.tips("保存失败,节点已被删除");
	                }
	            });
	        }
	        
	    </script>
	</body>
</html>