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
  <div class="form_head"><span>发文信息列表</span></div>
  <!-- 
	<input type="button" value="添加发文" class="btn addBtn"/>
  -->	
  	<c:if test="${roleType=='admin' }">
	<input type="button" value="@更新岗位" class="btn updateBtn">
	</c:if>
	<!--  
	<a class="bottom_menu menu" href="/xmuCadre/position/openPositionUpdate.do">更新岗位网页版</a>
	-->
	<a class="bottom_menu menu" href="/xmuCadre/position/articleList.do">点击将以发文为单位显示</a>

<!-- 以岗位为单位显示DIV -->
  <div class="postitionShow">
  		<form id="searchUserForm" action="${ctx}/position/positionList.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"   maxlength=40/>
			<input type="text" id="queryStrTemp" class="txt txtkey" value="岗位名称/发文文号/单位+岗位" style="width:220px;"/>
			<input type="submit" value="查询" class="btn search_btn" />
		</form>
		<p>提醒：下面的表格显示的是岗位部分属性</p>
    	<table class="tblist" >
				<tr>
					<th>发文号</th>
					<th>发文名</th>
					<th>学院（研究院）/部处</th>
					<th>系所/科室</th>
					<th>岗位名称</th>
					<th>岗位级别</th>
					<th>岗位类型</th>
					<th>设岗时间</th>
					<th>撤岗时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:if test="${queryList == null or empty queryList.result } ">
					<tr><td colspan="5">无相应记录</td></tr>
				</c:if>
				
				
				<c:forEach items="${queryList.result }" var="uv">
					<tr>
						<td>${uv.paper.paperNumber }</td>
						<td>${uv.paper.paperName }</td>
						<td>${uv.academy }</td>
						<td>${uv.department }</td>
						<td>${uv.positionName }</td>
						<td>${uv.positionLevel }</td>
						<td>${ide:getItem("positionType",uv.positionType) }</td>
						<td><fmt:formatDate value="${uv.positionDay}" pattern='yyyy-MM-dd'/></td>
						<td><fmt:formatDate value="${uv.delPositionDay}" pattern='yyyy-MM-dd'/></td>
						<td>
							
								<c:if test="${(uv.delFlag == null || uv.delFlag == 1) && (uv.emptyFlag ==0) }">岗位上有人员</c:if>
								<c:if test="${ (uv.delFlag == null || uv.delFlag == 1) && (uv.emptyFlag ==1 || uv.emptyFlag == null) }">空闲岗位</c:if>
								<c:if test="${uv.delFlag == 0 }">岗位已撤</c:if>
							
						</td>
						 
						<td class="c" TID="${uv.id }">
							<!--  这里做不了修改，因为发文信息的显示是从paper_content取数据，如果只在position中修改无法关联到paper_content， 
								如果手滑添加错误，请用更新岗位的先增后撤，发文处填写错误修改说明
							<input type="button" class="btn btn_cz editBtn" value="修改错误操作" > -->
							<input type="button" class="btn btn_cz" value="无" >
						</td>
						
					</tr>
				
				</c:forEach>
			</table>
			<ide:page page="${queryList }" pageId="userListPage" searchForm="searchUserForm"></ide:page>
	</div>
	


	<script type="text/javascript">
	
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
/*	   
	   //查看详情
		function bindPositionDetailEvent() {
			$(".detailBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/position/openPositionDetail.do?id="+id,
					{ title: "岗位详情", lock: true, width: 800, height: 450 }
				);
			});		
		}
*/
	
		//修改错误操作，无发文
		function bindPositionEditEvent(){
			$(".editBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/position/openPositionEdit.do?id="+id,
					{ title: "岗位详情修改", lock: true, width: 800, height: 450 }
				);
			});			
		}

		//添加
		function bindPositionAddEvent() {
			$(".addBtn").click(function(){
				art.dialog.open(
					"${ctx}/position/openPositionAdd.do",
					{ title: "添加岗位", lock: true, width: 1400, height: 450 }
				);
			});		
		}
		
		function bindPositionUpdateEvent(){
			$(".updateBtn").click(function(){
				art.dialog.open(
					"/xmuCadre/position/openPositionUpdate.do",
					{title:"岗位更新",lock:true, width:1400, height:600}
				);
			});
		}
	$(function(){
		bindQueryTextEvent();
		//bindPositionDetailEvent();
		bindPositionAddEvent();
		bindPositionEditEvent();
		bindPositionUpdateEvent();
	});
	</script>
	
    
  </body>
</html>
