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
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
 		
  </head>
<body>
<div class="form_head"><span>行政区划及籍贯管理</span></div>
<span style="color:red; text-align:center">父级区划名称：${parentName }</span>
 <div class="place">
 		 <form id="searchBellowForm" action="${ctx }/sys/place/openBellow.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"   maxlength=40/>
			<input type="text" id="queryStrTemp" class="txt txtkey" value="二级区划名称" style="width:220px;"/>
			<input value="${queryList.result[0].parentCode }" name="parentCode" style="display:none">
			<input type="submit" value="查询" class="btn search_btn" />
		</form> 
    	<table class="tblist" >
				<tr>
					<th>二级区划名称</th>
					<th class="c">操作</th>
				</tr>
				<c:forEach items="${queryList.result }" var="uv">
				<tr>
						<td>${uv.name}</td>
						<td class="c" TID="${uv.code } " NN = "${uv.name }" >
							<input type="button" class="btn btn_cz detailBtn" value="查看详情" >
						</td>
						</tr>
				</c:forEach>
			</table>
			<ide:page page="${queryList }" pageId="userListPage" searchForm="searchBellowForm"></ide:page>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		bindDetailEvent();
		bindQueryTextEvent();
	});
	function bindDetailEvent(){
		$(".detailBtn").click(function(){
			var code = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/sys/place/openBellowInfo.do?parentCode="+code,
					{ title: "三级区划详情", lock: true, width: 800, height: 450 }
				);
			
		});
	}
	function bindQueryTextEvent(){
	    	if($.trim($("#queryStr").val())==""){
	            $("#queryStr").hide();
	            $("#queryStrTemp").show();
	        } else {
	            $("#queryStr").show();
	            $("#queryStrTemp").hide();
	        }
	        $("#queryStrTemp").focus(function(){
	            $(this).hide();
	            $("#queryStr").show().focus();
	        });
	        $("#queryStr").blur(function(){
	            if($.trim($("#queryStr").val())==""){
	                $("#queryStr").hide();
	                $("#queryStrTemp").show();
	            } 
	        });
	    }
</script>