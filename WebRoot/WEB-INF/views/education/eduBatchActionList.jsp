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
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
  </head>
  
  <body class="ny_body">
		<div style="padding:0 20px">
		<h2>批量导入教育经历</h2>
		<form id="uploadAddForm"
			action="${ctx}/education/importEducationHistory.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadAddFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadAddBtn" type="button" value="开始导入" class="btn search_btn2" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
		<h2>批量删除教育经历</h2>
		<form id="uploadDelForm"
			action="${ctx}/education/delEducationHistory.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadDelFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadDelBtn" type="button" value="开始导入" class="btn search_btn2" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
		<div class="r_help_box r_help_box2">
			<p><span style="color:red; font-weight:bold;">批量导入与批量删除须知</span></p>
			<p>0、删除数据时务必保证与数据库中数据一致，否则将无法删除</p>
			<p>1、上传您制作好的名单（excel格式）。按照[姓名]、[证件号码]、[起始时间]、[结束时间]、[学校]、[学习形式]、[学习阶段]、[学习状态]、[学位]、[学位类型]、[核对情况]、[备注]的顺序</p>
			<p><span style="color:red; font-weight:bold;">其中，[姓名]、[证件号码]、[起始时间]为必填，其余选填，请按要求规范填写，违反规范将无法成功导入</span></p>
			<p><span style="color:red; font-weight:bold;">起始时间和结束时间的格式为2015-09-01、1999-12-12、1968-02-01等等	</p>
			<p>
				2、学习形式包括:<br/>
				全日制、在职、夜大学、业余大学、函授、网络教育
			</p>
			<p>
				3、学习阶段包括:<br/>
				中专、大专、本科、硕士研究生、博士研究生
			</p>
			<p>
				4、学习状态包括:<br/>
				毕业、肄业、结业、在读
			</p>
			<p>
				5、学位包括: <br/>
				学士、硕士、博士
			</p>
			<p>
				6、学位类型包括:<br/>
				文学、理学、工学、经济学
			</p>
			
			<p>
				7、核对情况包括:<br/>
				已核对无误、待核对
			</p>
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
			参考范例：<a href="${ctx }/resources/Template/import-education.xlsx"
				class="blue">下载表格模板</a>
		</div>
		</div>
		<script type="text/javascript">
	    $(function(){
	    	bindUploadAddBtnEvent();//绑定上传按钮事件
	    	bindUploadDelBtnEvent();
	    });
	    
	    function bindUploadAddBtnEvent(){
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
	    
	    function bindUploadDelBtnEvent(){
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
