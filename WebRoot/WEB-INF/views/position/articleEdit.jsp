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
    	<input type="hidden" value="${paper.id }" name="id">
    	<table cellpadding="0" cellspacing="0" class="basicInfo" id="font16">
		    	<tr>
		    		<td>发文文号</td>
		    		<td><input type="text" name="paperNumber" value="${paper.paperNumber }" class="txt"/></td>
		    	</tr>
		    	<tr>
		    		<td>发文名称</td>
		    		<td><input type="text" name="paperName" value="${paper.paperName }" class="txt"/></td>
		    	</tr>
		    	<tr>
		    		<td>发文日期</td>
		    		<td>
						<input type="text" name="paperDay" class="Wdate" value="<fmt:formatDate value='${paper.paperDay}'  pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({readOnly:false})"onblur="text(this,'day');">
		    		</td>
		    	</tr>
		    </table>
   	
			<div class="tb_sub_btns">
				<input type="button" class="editBtn" value="修改"/>
				<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
			</div>
    	 </form>
    </div>
    <script type="text/javascript">
     	function bindEditBtnEvent(){
    		var time=2;
    		
    		$(".editBtn").click(function(){    		
    			var data = $("#Editform").serialize();
    			$.post("${ctx}/position/articleEdit.do",data,function(result){
    				if ("success" == result){
    					
    					art.dialog({
    						time:2,
    						content:'修改成功，2秒后关闭'
    					});
    					function update(){
    						if(time>1){
    							time--;
    						}
    						else{
    							art.dialog.opener.location.reload();
    						}
    					}
    					setInterval(update,1000);
    					
    				}
    				else{
    					art.dialog.tips("修改失败");   					
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
