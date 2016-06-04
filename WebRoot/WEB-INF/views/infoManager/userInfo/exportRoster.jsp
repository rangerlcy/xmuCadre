<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<script type="text/javascript" src="${res}/js/jquery.js"></script>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
  </head>
  
  <body>
     <div>
     	<p>输入查询条件，系统将根据查询条件进行导出，如不输入则导出所有数据</p>
     	<p>如需查询多个单位名称请用*号隔开。请输入正常的查询条件，错误的条件将造成导出错误的数据</p>
     	<form id="queryTable">
     		院/处 单位：<input id="academy" name="academy" style="width:450px"><br/><br/><br/><br/>
     		年龄：<input id="age1" name="age1" style="width:30px" maxlength="3"> --- <input id="age2" name="age2" style="width:30px" maxlength="3"><br/><br/><br/><br/>
     		人员级别：<input id="level" name="level" style="width:100px"><br/><br/><br/><br/>
     	</form>
     	<div class="tb_sub_btns" style="padding-bottom:20px;">
     		<input type="button" id="query" value="查询"> 
     		<input type="button" value="关闭" onclick="art.dialog.close()"> 
     		<input type="button" id="export" value="导出" style="display:none"> 
     	</div>
     	<a style="display:none;" id="exportUrl" href="${ctx }/infoManager/userInfo/exportUserList.do"><span id="ex">导出</span></a>
     </div>
		<script type="text/javascript">
		
		$("#query").click(function(){
			var academy=$("#academy").val();
			var age1 =$("#age1").val();
			var age2 =$("#age2").val();
			var level = $("#level").val();
			if(age1>age2){
				alert("年龄前小后大,可以相等");
				return ;
			}
			$.post("${ctx}/infoManager/userInfo/queryExportRoster.do" , {academy: academy, age1: age1, age2: age2, level: level},function(result,status,xhr){
				//alert(result+status+xhr);
				if(result == "success"){
					alert("查询到数据可以导出");
					$("#export").css("display","");
				}
				if(result =="nodata"){
					$("#export").css("display","none");
					alert("没有查询到数据，请检查查询条件");
					
				}
			});
		});
		
		$("#export").click(function(){
			$("#ex").click();
		});
	</script>
  </body>
</html>
