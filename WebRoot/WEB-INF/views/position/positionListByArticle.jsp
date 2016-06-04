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
  	<c:if test="${roleType=='admin' }">
	<input type="button" value="@更新岗位" class="btn updateBtn">
	</c:if>
	<a class="bottom_menu menu" href="/xmuCadre/position/positionList.do">点击将以岗位为单位显示</a>
	
	<!-- 以发文为单位显示DIV -->
 	  
	<div class="articleShow">
  		<form id="searchUserForm" action="${ctx}/position/articleList.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"  onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=40/>
			<input type="text" id="queryStrTemp" class="txt txtkey" value="发文名称/发文文号" style="width:220px;"/>
			<input type="submit" value="查询" class="btn search_btn" />
		</form>
    	<table class="tblist">
				<tr>
					<td>发文时间</td>
					<td>发文文号</td>
					<td>发文名称</td>
					<td class="c">操作</td>
				</tr>
				<c:if test="${queryList == null or empty queryList.result } ">
					<tr><td colspan="5">无相应记录</td></tr>
				</c:if>
				<c:forEach items="${queryList.result }" var="uv">
					<tr>	
						<td><fmt:formatDate value="${uv.paperDay}" pattern='yyyy-MM-dd'/></td>
						<td>${uv.paperNumber }</td>
						<td>${uv.paperName }</td>
						<td class="c" TID="${uv.id }">
						
							<input type="button" class="btn btn_cz detailBtn" value="查看详情">
							<c:if test="${roleType=='admin' }">
							<input type="button" class="btn btn_cz editBtn" value="修改">
							<input type="button" class="btn btn_cz downloadBtn" value="下载发文">
							</c:if>
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
	   
	   //查看详情
		function bindArticleDetailEvent() {
			$(".detailBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/position/AricleDetail.do?id="+id,
					{ title: "岗位详情", lock: true, width: 1200, height: 500 }
				);
			});		
		}	   
	   //修改
		function bindArticleEditEvent() {
			$(".editBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/position/openAricleEdit.do?id="+id,
					{ title: "发文修改", lock: true, width: 800, height: 300 }
				);
			});		
		}
		/*
		//删除
		function bindArticleDelEvent(){
			$(".delBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.confirm("确定删除？",function(){
					$.post("${ctx}/position/articleDel.do?id="+id,{id:id},
						function(result){
			                if("success" == result) {
			                	art.dialog.tips("删除成功");
			                	self.location.href="${ctx}/position/articleList.do";     
			                } else {
			                    art.dialog.tips("删除失败:"+result);
			                }
			        	}
			        );
				});	
			});
		}
		*/
		//下载发文
		function bindArticleDownEvent(){
			$(".downloadBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.confirm("确定下载？",function(){
					$.post("${ctx}/position/articleDownload.do?id="+id,{id:id},
						function(result){
			                if("fail" == result) {
			                	art.dialog.tips("下载失败,原因可能是该发文不存在");  
			                } else {
			                	//art.dialog.tips("开始下载成功");
			                	//alert(result);
			                	if(result=="" || result==null){
			                		art.dialog.tips("下载失败,原因可能是该发文不存在");  
			                	}else{
			                		self.location.href="${ctx}/"+result;
			                	}    			                    
			                }
			        	}
			        );
				});	
			});		
		}
		//添加
		function bindPositionUpdateEvent(){
			$(".updateBtn").click(function(){
				art.dialog.open(
					"/xmuCadre/position/openPositionUpdate.do",
					{title:"岗位更新",lock:true, width:1400, height:600}
				)
			});
		}
	$(function(){
		bindQueryTextEvent();
		bindArticleEditEvent();
//		bindPositionAddEvent();
//		bindArticleDelEvent();
		bindArticleDownEvent();
		bindArticleDetailEvent();
		bindPositionUpdateEvent();
	});
	</script>
	
    
  </body>
</html>
