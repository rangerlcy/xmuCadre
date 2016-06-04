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
		<h2>批量增加岗位</h2>
		<form id="uploadAddForm"
			action="${ctx}/position/importAddPosition.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadAddFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadAddBtn" type="button" value="开始导入" class="btn search_btn" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
		
		<h2>批量删除岗位</h2>
		<form id="uploadDelForm"
			action="${ctx}/position/importDelPosition.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadDelFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadDelBtn" type="button" value="开始导入" class="btn search_btn" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
	
		
		<h2>批量撤销岗位</h2>
		<form id="uploadInvalidForm"
			action="${ctx}/position/importInvalidPosition.do"
			method="post" enctype="multipart/form-data">
				<div class="r_search">
			        <input id="uploadInvalidFile" type="file" name="file" class="upload_file_excel" />
			        <input id="uploadInvalidBtn" type="button" value="开始导入" class="btn search_btn" />
			        <span class="ts ts2" id="tip"></span>
			    </div>			
		</form>
		<div class="r_help_box r_help_box2">
			<p>
				1、上传您制作好的名单（excel格式）。批量增加时，[学院（研究院）/部处]、[系所/科室]、[职务名称]、[岗位类型]、[岗位级别]、[设岗文号]、[设岗文名]为必填字段。<br/>
					  批量删除时，[设岗文号]为必填字段<br/>
				  	 批量撤岗时,[设岗文号]、[撤岗文号]、[撤岗文名]为必填字段<br/>
			
			</p>
			<p>2、[学院（研究院）/部处]、[系所/科室]包括 <div id="tag"><a id="showTag" href=" javascript:changeShowStatus()">展开</a></div>
				<div class="displayNone" id="showBox">
				<c:forEach items="${organizations}" var="kv"  varStatus="index">
					[${kv.name }]<c:if test="${!index.last }">、</c:if>
				</c:forEach>
				
				</div>
				中的一个，且必须有从属关系
				
			</p>
			<p>3、岗位类型包括：
				<c:forEach items="${ide:getAllPositionType()}" var="kv" varStatus="index">
					[${kv.value }]<c:if test="${!index.last }">、</c:if>
				</c:forEach>
			</p>
			<p>
				4、岗位级别包括:
				<c:forEach items="${ide:getAllPositionLevel()}"  varStatus="index" var="kv">
					[${kv.value }]<c:if test="${!index.last }">、</c:if>
				</c:forEach>
			</p>
			<p>
				5、操作包括:[增加]、[修改]、[删除]、[撤岗]
				
			</p>
			<p>
				6、系统以[设岗文号]为唯一标识。
				
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
			参考范例：<a href="${ctx }/resources/template/import-addPosition.xls"
				class="blue">下载批量设岗表格模板</a>
				
				<a href="${ctx }/resources/template/import-delPosition.xls"
				class="blue">下载批量删除岗位表格模板</a>
				
				<a href="${ctx }/resources/template/import-invalidPosition.xls"
				class="blue">下载批量撤岗表格模板</a>
		</div>
		</div>
		<script type="text/javascript">
	    $(function(){
	    	bindUploadAddBtnEvent();//绑定上传按钮事件
	    	bindUploadDelBtnEvent();
	    	bindUploadInvalidBtnEvent();
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
	    function bindUploadInvalidBtnEvent(){
	    	$("#uploadInvalidBtn").click(function(){
	    		if(!isExcelFile($("#uploadInvalidFile").val())){
	    			art.dialog.tips("请选择Excel文件");
	                return;
	            }
	            art.dialog.confirm("确定导入？",function(){
	                $("#uploadInvalidForm").submit();
	            });
	    	});
	    }
	  	function changeShowStatus(){
	  		if ($("#showBox").hasClass("displayNone")){
	  			$("#showBox").removeClass("displayNone");
	  			$("#showBox").slideDown();
	  			var st = $("#showTag");
	  			$("#showTag").remove();
	  			st.html("缩起");
	  			$("#showBox").append(st);
	  		}else{
	  			$("#showTag").html("展开");
	  			var st = $("#showTag");
	  			$("#showTag").remove();
	  			$("#tag").html(st);
	  			$("#showBox").addClass("displayNone");
	  			
	  		}
	  	
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
