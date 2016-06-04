<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
  </head>
  <body>
  <div class="tb_700">
    <form id="Editform" action="" onsubmit="return false">
    	<input type="hidden" value="${position.id }" name="id">
    	<input type="hidden" value="${position.delFlag }" name="delFlag">
    	<table cellpadding="0" cellspacing="0" class="basicInfo" id="font16">
		    	<tr>
		    		<td>学院（研究院）/部处</td>
		    		<td><input type="text" name="positionAcademy" value="${position.academy }" class="txt"/></td>
		    	</tr>
		    	<tr>
		    		<td>系所/科室</td>
		    		<td><input type="text" name="positionDepartment" value="${position.department}" class="txt"/></td>
		    	</tr>
		    	<tr>
		    		<td>岗位名称</td>
		    		<td><input type="text" name="positionName" value="${position.positionName }" class="txt"/></td>
		    	</tr>
		    	<tr>
		    		<td>岗位类型</td>
		    		<td><input type="text" name="positionType" value="${position.positionType }" class="txt"/></td>
		    	</tr>
		    	<tr>
		    		<td>岗位级别</td>
		    		<td><input type="text" name="positionLevel" value="${position.positionLevel }" class="txt"/></td>
		    	</tr>
		    	<tr>
		    		<td>设岗时间</td>
		    		<td>
		    			<input type="text" class="Wdate" name="positionDay" value="<fmt:formatDate value='${position.positionDay}'  pattern='yyyy-MM-dd'/>" 
		    			onfocus="WdatePicker({readOnly:false})"onblur="text(this,'day');">
					</td>
		    	</tr>
		    	<tr>
		    		<td>撤岗时间</td>
		    		<td>
		    			<c:if test="${empty position.delPositionDay }">
		    				该岗位正在服役，无法修改撤岗时间
		    			</c:if>
		    			<c:if test="${!empty position.delPositionDay }">
		    				<input type="text" class="Wdate" name="positionDay" value="<fmt:formatDate value='${position.delPositionDay}'  pattern='yyyy-MM-dd'/>" 
		    				onfocus="WdatePicker({readOnly:false})"onblur="text(this,'day');">
		    			</c:if>
		    			
					</td>
		    	</tr>
		    	<tr>
		    		<td>职位备注</td>
		    		<td><input type="text" name="positionRemark" value="${position.positionRemark }" class="txt"/></td>
		    	</tr>
		    </table>
   	
    		<div class="tb_sub_btns">	
    			<input type="button" class="editBtn" value="提交"/>
				<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
    		</div>
    	 </form>
    </div>
    <script type="text/javascript">
    	
    	function bindEditBtnEvent(){
    	
    		$(".editBtn").click(function(){    		
    			var data = $("#Editform").serialize();
    			$.post("${ctx}/position/positionEdit.do",data,function(result){
    				if ("success" == result){
    					art.dialog.opener.location.reload();
    					art.dialog.close();
    					art.dialog.tips("成功");
    				}
    				else{
    					art.dialog.tips("失败");
    				}
    				
    			});
    		});
    	}
    	$(function(){
    		bindEditBtnEvent();
    	});
    </script>
  </body>
</html>
