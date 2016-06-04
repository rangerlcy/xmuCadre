<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/content.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
  </head>
  
  <body class="ny_body">
		<div style="padding:20px 20px">
		<h2>批量导入挂职信息</h2>
		<form id="uploadAddForm"
			action="${ctx}/secondment/importAddSecondment.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadAddFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadAddBtn" type="button" value="开始导入" class="btn search_btn" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
		<h2>批量删除挂职信息</h2>
		<form id="uploadDelForm"
			action="${ctx}/secondment/importDelsecondment.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadDelFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadDelBtn" type="button" value="开始导入" class="btn search_btn" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
		<div class="r_help_box r_help_box2">
			<p>1、上传您制作好的名单（excel格式）。[发文文号]、[发文文件]、[发文单位]为必填字段。</p>
			<p>2、当导入的发文文号在系统中已经存在时,直接更新挂职信息的相关内容。</p>
			
		<c:choose>
			<c:when test="${result == 'success' and failSize == 0}">
					<p>
						<strong>导入成功</strong>
					</p>
					<p>
						已完成 <span class="blue">${successSize }</span> 条数据的操作
					</p>
			</c:when>
			<c:when test="${result == 'success' and failSize > 0}">
					<p>
						<strong>导入结果:</strong>
					</p>
					<p>
						已完成 <span class="blue">${successSize }</span> 条数据的操作，失败数据共 <span
							class="red">${failSize }</span> 条（未导入），具体如下：
					</p>
					<c:if test="${errorList != null }">
						<c:forEach items="${errorList }" var="error">
							<p>第${error.lineNum }行 &nbsp;${error.errorMsg }</p>
						</c:forEach>
					</c:if>
			</c:when>
			<c:when test="${result == 'fail' }">
					<p>
						<strong>导入失败:</strong>
					</p>
					<p>${failDetail }</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		</div>
		<div style="padding:10px 20px">
			参考范例：<a href="${ctx }/resources/template/import-user.xls"
				class="blue">下载表格模板</a>
		</div>
		</div>
		<script type="text/javascript">
	    $(function(){
	    	bindUploadAddBtnEvent();//绑定上传按钮事件
	    	bindUploadDelBtnEvent();
	    });
	    
	    function bindUploadAddBtnEvent(){
	    	$("#uploadDelBtn").click(function(){
	    		if(!isExcelFile($("#uploadDelFile").val())){
	    			art.dialog.tips("请选择Excel文件");
	                return;
	            }
	            art.dialog.confirm("确定导入？",function(){
	                $("#uploadDelForm").submit();
	            });
	    	});
	    }
	     function bindUploadDelBtnEvent(){
	    	$("#uploadAddBtn").click(function(){
	    		if(!isExcelFile($("#uploadAddFile").val())){
	    			art.dialog.tips("请选择Excel文件");
	                return;
	            }
	            art.dialog.confirm("确定导入？",function(){
	                $("#uploadAddForm").submit();
	            });
	    	});
	    }
	    function isExcelFile(fileName) {
		    if(fileName == null 
		            || fileName == "" 
		            || !(fileName.endWith("xls") || fileName.endWith("xlsx"))){
		       return false;
		    }
		    return true;
		}
	    </script>
	</body>
</html>
