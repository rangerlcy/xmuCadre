<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide"%>
<script type="text/javascript"
	src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
<title>厦门大学干部信息管理系统</title>
<link rel="stylesheet" href="${res }/css/style.css" type="text/css" />
<link rel="stylesheet" href="${res }/css/content.css" type="text/css" />
<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
</head>

<body class="ny_body">
	<div style="padding:0 20px">
		<h2>批量添加项目培训信息</h2>
		<form id="uploadAddForm"
			action="/xmuCadre/userTrain/quantityAction/importAddUserTrainInfo.do"
			method="post" enctype="multipart/form-data">
			<div class="r_search">
				<input id="uploadAddFile" type="file" name="file"
					class="upload_file_excel" /> <input id="uploadAddBtn"
					type="button" value="上传" class="btn search_btn2" /> <span
					class="ts ts2" id="tip"></span>
			</div>
		</form>
		<div class="r_help_box r_help_box2">
			<p>上传您制作好的名单（excel格式）。[开始时间]、[结束时间]、[教育培训名称]、[主办单位]为必填字段。</p>

			<c:choose>
				<c:when test="${result == 'success' and failSize == 0}">
					<p>
						<strong>上传成功</strong>
					</p>
					<p>
						已完成 <span class="blue">${successSize }</span> 条数据的操作
					</p>
					<input type="submit" value="确定" class="btn search_btn2"
						id="saveBtn" />
				</c:when>
				<c:when test="${result == 'success' and failSize > 0}">
					<p>
						<strong>上传结果:</strong>
					</p>
					<p>
						已完成 <span class="blue">${successSize }</span> 条数据的操作，失败数据共 <span
							class="red">${failSize }</span> 条（未上传），具体如下：
					</p>
					<c:if test="${errorList != null }">
						<c:forEach items="${errorList }" var="error">
							<p>第${error.lineNum }行 &nbsp;${error.errorMsg }</p>
						</c:forEach>
					</c:if>

					<input type="submit" value="确定" class="btn search_btn2"
						id="saveBtn" />
				</c:when>
				<c:when test="${result == 'fail' }">
					<p>
						<strong>上传失败:</strong>
					</p>
					<p>${failDetail }</p>
					<input type="submit" value="确定" class="btn search_btn2"
						id="saveBtn" />
				</c:when>

			</c:choose>
		</div>
		<div style="padding:10px 20px">
			参考范例：<a href="${ctx }/resources/Template/import-userTrain.xls"
				class="blue">下载表格模板</a>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			bindUploadAddBtnEvent();//绑定上传按钮事件
			bindSaveBtnEvent();//绑定确定按钮事件
		});
		function bindSaveBtnEvent() {
			$("#saveBtn").click(function() {
					art.dialog.opener.location.reload();
					art.dialog.close();
			});
		}
		function bindUploadAddBtnEvent() {
			$("#uploadAddBtn").click(function() {
				if (!isExcelFile($("#uploadAddFile").val())) {
					art.dialog.tips("请选择Excel文件");
					return;
				}
				art.dialog.confirm("确定上传？", function() {
					$("#uploadAddForm").submit();
				});
			});
		}
		function isExcelFile(fileName) {
			if (fileName == null
					|| fileName == ""
					|| !(fileName.endWith("xls") || fileName.endWith("xlsx")
							|| fileName.endWith("XLS") || fileName
							.endWith("XLSX"))) {
				return false;
			}
			return true;
		}
	</script>
</body>
</html>
