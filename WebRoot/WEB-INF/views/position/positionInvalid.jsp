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
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
  </head>
  
  <body>
  <div class="tb_480">
   	<center>${position.positionName }撤岗</center><br />
    <form action="" id="invalidForm" method="post">
    	<input type="hidden" name="id" value="${position.id }"/>
    	<table cellpadding="0" cellspacing="0" class="basicInfo" id="font16">
	    	<tr><td>撤岗文名</td><td><input name="offPositionPaperName" class="txt"/></td></tr>
	    	<tr><td>撤岗文号</td><td><input name="offPositionPaperNumber" class="txt"/></td></tr>
    	</table>
    </form>
  </div>
    <div class="tb_sub_btns">
    	<input type = "button" value="撤岗" class = " btn_cz invalidBtn"/>
    	<input type ="button" value="取消" onclick="art.dialog.close()" class="cancel" />
    </div>
    <script type="text/javascript">
    function bindInvalidPositionEvent(){
    	var data=$("#invalidForm").serialize();
    	$(".invalidBtn").click(function(){
    		art.dialog.confirm("确定撤岗？",function(){
				$.post("${ctx}/position/positionInvalid.do",data,
					function(result){
						if ("success" == result){
			                art.dialog.opener.location.reload();
			                art.dialog.close();
			             }else if("invalid" == result){
			             	art.dialog.tips("岗位已被撤岗");
			             }else{
			             	art.dialog.alert("失败");
			             }
		        	}
		        );
			});		
    	
    	});
    	
    }
    $(function(){
    	bindInvalidPositionEvent();
    });
    </script>
  </body>
</html>
