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
<div class="form_head"><span>岗位导出顺序管理</span></div>

<span style="color:red; text-align:center">父级单位：${queryList.result[0].academy }</span>
<div class="departmentSequece">
 		<form id="searchDepartmentForm" action="${ctx }/sys/sequence/openDepartmentSequence.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="" style="width:220px;"   maxlength=40/>
			<input type="text" id="queryStrTemp" class="txt txtkey" value="系级单位名称" style="width:220px;"/>
			<input value="${queryList.result[0].academy }" name="academyName" style="display:none">
			<input type="submit" value="查询" class="btn search_btn" />
		</form>
    	<table class="tblist" >
				<tr>
					<th>序号</th>
					<th>系级单位名称</th>
					<th class="c">操作</th>
				</tr>
				<c:if test="${queryList == null or empty queryList.result } ">
					<tr><td colspan="5">无相应记录</td></tr>
				</c:if>
				<c:forEach items="${queryList.result }" var="uv">
					<tr>
						<c:if test="${uv.departmentSeq == null}">
							<td>未设置顺序</td>
						</c:if>
						<c:if test="${uv.departmentSeq != null}">
							<td>${uv.departmentSeq }</td>
						</c:if>	
						<td>${uv.department }</td>
						<td class="c" TID="${uv.id }" NN="${uv.department }" NUM="${uv.departmentSeq }">
							<input type="button" class="btn btn_cz sqBtn" value="修改顺序" >
							<input type="button" class="btn btn_cz detailBtn" value="查看该单位下的岗位顺序">
						</td>
					</tr>
				
				</c:forEach>
			</table>
			<ide:page page="${queryList }" pageId="userListPage" searchForm="searchUserForm"></ide:page>
	</div>
</body>
<script type="text/javascript">

	$(".detailBtn").click(function(){
		var departmentName = $(this).parent().attr("NN");
		var sq = $(this).parent().attr("NUM");
		if(sq==""){
			alert("请先确定该单位的顺序后,才能进行该单位下岗位顺序的修改");
		}else{
			art.dialog.open(
				"${ctx}/sys/sequence/openPositionNameSequence.do?departmentName="+departmentName,
				{ title: "岗位顺序", lock: true, width: 800, height: 450 }
			);
		}	
	});
	
	$(function(){
		bindQueryTextEvent();
		bindEditEvent();
	});
	function bindEditEvent(){
		$(".sqBtn").click(function(){
			var id = $(this).parent().attr("TID");
			var name = $(this).parent().attr("NN");
			var num = $(this).parent().attr("NUM");
			if(num==null || num.trim()==""){
				num=999999;
			}
			art.dialog({ 
				title: "单位顺序修改", 
				content: name+"<br/><span style='color:red;font-size:5px'>在框内输入数字对顺序进行修改<span><br/>"+"<br/><input type='text' id='sq'/>",
				ok: function(){
					var temp=/^\d+(\d+)?$/;
					if(temp.test($("#sq").val())==false){
						alert("请输入正整数");
					}else{
						$.post("${ctx}/sys/sequence/academySqEdit.do",
						{
							name: name,
							sqold: num,
							sq:$("#sq").val(),
							type: "2"
						},
						function(result){
							alert("修改成功");
							window.location.reload();  
						});
					}
				},
				cancelVal: '关闭',
				cancel:true,
				lock: true
			});
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