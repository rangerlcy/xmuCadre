<!-- create by lcy 2015/07/30-->
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
		<style type="text/css">
			td{
				padding-top:5px;
				padding-bottom:5px;
			}
		</style>
  </head>
  
  
  <body>
  <div class="form_head"><span class="moreDel">批量删除专业技术职务经历</span></div>

	<input type="button" value="返回" class="btn backBtn">
	<input type="button" value="批量删除" class="btn manyDel">
	<span style="color:red; font-weight:bold;">说明:点击将删除被选中的本页面成员,无法跨页面删除</span>


  <div class="skillShow">
  		<form id="searchUserForm" action="${ctx}/skill/skillList.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"  onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=40/>
			<input type="text" id="queryStrTemp" class="txt txtkey" value="职工姓名" style="width:220px;"/>
			<input type="submit" value="查询" class="btn search_btn" />
		</form>
    	<table class="tblist" id="tab">
				<tr>
					<th>姓名</th>
					<th>起始时间</th>
					<th>结束时间</th>
					<th>聘任级别</th>
					<th>专业技术职务</th>
					<th>核对情况</th>
					<th>备注</th>
				</tr>
				<c:if test="${queryList == null or empty queryList.result } ">
					<tr><td colspan="5">无相应记录</td></tr>
				</c:if>
				
				
				<c:forEach items="${queryList.result }" var="uv">
					<tr>
						<td><input type="checkbox" class="checkbox moreDel" value="${uv.id}"> ${uv.user.name }</td>
						<td><fmt:formatDate value='${uv.beginDay }' pattern='yyyy-MM-dd'/></td>
						<td><fmt:formatDate value='${uv.endDay }' pattern='yyyy-MM-dd'/></td>
						<td>${uv.employmentLevel }</td>
						<td>${uv.skillName }</td>
						<td>${ide:getItem('userCheckCase',uv.checkCase) }</td>
						<td>${uv.remark }</td>
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
	    
	
	//关闭批量删除
	function bindCloseManyDelEvent(){
		$(".backBtn").click(function(){
			window.location.href="/xmuCadre/skill/skillList.do";
		});
	}
	
	//批量删除
	function bindManyDelEvent(){
		$(".manyDel").click(function(){
			var str="";
			$(".checkbox").each(function(){
				if ($(this).is(":checked")){
					str+=$(this).val()+";";
				}
			});
			art.dialog.confirm("确定删除本页面被选中的专业技术职务经历？",function(){
					$.post("${ctx}/skill/skillManyDel.do?str="+str,
						function(result){
			                if("success" == result) {
			                	art.dialog.tips("删除成功");
			                	art.dialog.opener.location.reload();
			                	 
			                } else {
			                    art.dialog.tips("删除失败:"+result);
			                }
			        	}
			        );
				});	
		});
	}

	    
	    $(function(){
			bindQueryTextEvent();
			bindManyDelEvent();
			bindCloseManyDelEvent();
		});
	</script>    
  </body>
</html>
