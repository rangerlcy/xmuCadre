<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
  		<base href="<%=basePath%>">
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/content.css" type="text/css"  />
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
  </head>
  
  <body class="ny_body">
  
 <h2>上传文件</h2>
 <p>上传文件只支持图片和文档，格式为.jpg .png .doc .docx .pdf</p>
 <form id="uploadForm" action="${ctx}/report/reportafx.do" method="post" enctype="multipart/form-data">
        <div class="r_search">
	       <input type="file" name="file" id="uploadFile" style="float:left"/>
	       <input id="uploadBtn" type="button" value="上传文件" class="btn search_btn2" />
	       <span class="ts ts2" id="tip"></span>
        </div>		
 </form>
 <br>
 
 <h2>下载文件</h2>
 <c:forEach items="${successTime }" var="uv">
	<span style="color:red;">上传成功，本次操作时间${uv }</span>
</c:forEach>
 <c:forEach items="${failTime }" var="uv">
	<span style="color:red;">上传失败，本次操作时间${uv }</span>
</c:forEach>
 	<table>
 		<tr>
 			<th style="width:680px">文件名称(点击下载)</th>
 			<th class="c">操作</th>
 		</tr>
 		
	 	<c:if test="${result == null or empty result or result=='' } ">
			无记录
	 	</c:if>
	 	<c:forEach items="${result }" var="uv">
	 		<tr>
	 			<td style="text-align:center;"><a href=${uv.url } download>${uv.name } </a></td>
	 			<td class="c" TID="${uv.id }">
					<input type="button" class="btn btn_cz delBtn" value="删除" >
				</td>
	 		</tr>
	 	</c:forEach>
	 </table>
	
	
<script>
	$(".delBtn").click(function(){
		var id = $(this).parent().attr("TID");
		art.dialog.confirm("确定删除？",function(){
			$.post("${ctx}/report/delReportFile.do",{id:id},
				function(result){
			         if("success" == result) {
			         art.dialog.tips("删除成功");
			         art.dialog.opener.location.reload();             
			        } else {
			             	art.dialog.tips("删除失败");
			             }
			        }
			    );
			});	
	});
	$("#uploadBtn").click(function(){
		if(!judgeFormat($("#uploadFile").val())){
			art.dialog.tips("文件格式错误");
	        return;
		};
		var file=new FormData($("#uploadForm")[0]);
		art.dialog.confirm("确定上传？",function(){
		   $("#uploadForm").submit();
		});
	});
	//只允许上传.jpg .png .doc .docx格式的文件
	function judgeFormat(fileName) {
		    if(fileName == null 
		            || fileName == "" 
		            || !(fileName.endWith(".jpg") || fileName.endWith(".png") || fileName.endWith(".doc")|| fileName.endWith(".pdf") || fileName.endWith(".docx"))){
		       return false;
		    }
		    return true;
		}
</script>
	</body>
</html>