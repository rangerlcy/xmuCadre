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
		<div style="padding:20px 20px">
		<form id="uploadForm"
			action="${ctx}/sys/user/importInfo.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadBtn" type="button" value="开始导入" class="btn search_btn" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
		<div class="r_help_box r_help_box2">
			<p>1、上传您制作好的名单（excel格式）。[登陆账号]、[姓名]为必填字段。</p>
			<p>2、新导入的员工的初始密码与登陆账号一致。</p>
		<c:choose>
			<c:when test="${result == 'success' and failSize == 0}">
					<p>
						<strong>导入成功</strong>
					</p>
					<p>
						已完成 <span class="blue">${successSize }</span> 条数据的导入
					</p>
			</c:when>
			<c:when test="${result == 'success' and failSize > 0}">
					<p>
						<strong>导入结果:</strong>
					</p>
					<p>
						已完成 <span class="blue">${successSize }</span> 条数据的导入，失败数据共 <span
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
	    	bindUploadBtnEvent();//绑定上传按钮事件
	    });
	    
	    function bindUploadBtnEvent(){
	    	$("#uploadBtn").click(function(){
	    		if(!isExcelFile($("#uploadFile").val())){
	    			$("#tip").html("请选择Excel文件");
	                return;
	            }
	            art.dialog.confirm("确定导入？",function(){
	                $("#uploadForm").submit();
	            });
	    	})
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