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
  <div class="form_head"><span class="showList">行政工作经历信息列表</span> </div>
  	<c:if test="${roleType=='admin' }">
	<input type="button" value="添加行政工作经历" class="btn addBtn showList"/>
	</c:if>
	<!--批量删除逻辑有错且作用不大去掉该功能<input type="button" value="打开批量删除" class="btn manyDelBtn showList">-->
	<!-- 
	<div class="moreDel" style="display:none">
		<input type="button" value="返回" class="btn backBtn">
		<input type="button" value="批量删除" class="btn manyDel">
		<span style="color:red; font-weight:bold;">说明:点击将删除被选中的本页面成员,无法跨页面删除</span>
	</div>
	-->
  <div class="administrationWorkShow">
  		<form id="searchUserForm" action="${ctx}/administrationWork/administrationWorkList.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"  onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=40/>
			<input type="text" id="queryStrTemp" class="txt txtkey" value="职工姓名" style="width:220px;"/>
			<input type="submit" value="查询" class="btn search_btn" />
		</form>
    	<table class="tblist" id="tab">
				<tr>
					<th>姓名</th>
					<th>起始时间</th>
					<th>结束时间</th>
					<th>任职单位</th>
					<th>单位类型</th>
					<th>职务名称</th>
					<th>人员级别</th>
					<th>核对情况</th>
					<th>备注</th>
					<th class="c">操作</th>
				</tr>
				<c:if test="${queryList == null or empty queryList.result } ">
					<tr><td colspan="5">无相应记录</td></tr>
				</c:if>
				
				
				<c:forEach items="${queryList.result }" var="uv">
					<tr>
						<td><input type="checkbox" class="checkbox moreDel" value="${uv.id}" style="display:none"> ${uv.user.name }</td>
						<td><fmt:formatDate value='${uv.beginDay }' pattern='yyyy-MM-dd'/></td>
						<td><fmt:formatDate value='${uv.endDay }' pattern='yyyy-MM-dd'/></td>
						<td>${uv.units }</td>
						<td>${ide:getInstitutionType(uv.jobType) }</td>
						<td>${uv.jobName }</td>
						<td>${ide:getPositionLevel(uv.userLevel) }</td>
						<td>${uv.checkCase }</td>
						<td>${uv.remark }</td>
						<td class="c" TID="${uv.id }">
							<c:if test="${roleType=='admin' }">
							<input type="button" class="btn btn_cz editBtn" value="修改" >
							<input type="button" class="btn btn_cz delBtn showList" value="删除" >
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
	    
	//添加
		function bindAddEvent() {
			$(".addBtn").click(function(){
				art.dialog.open(
					"${ctx}/administrationWork/openAdministrationWorkAdd.do",
					{ title: "添加行政工作经历", lock: true, width: 1000, height: 650 }
				);
			});		
		}
	//修改
	function bindEditEvent() {
			$(".editBtn").click(function(){
			var thiz = $(this);
			var id = thiz.parent().attr("TID");
				art.dialog.open(
					"${ctx}/administrationWork/openAdministrationWorkEdit.do?id="+id,
					{ title: "修改行政工作经历", lock: true, width: 1000, height: 650 }
				);
			});		
		}
		
	
	//打开批量删除
	function bindOpenManyDelEvent(){
		$(".manyDelBtn").click(function(){
			window.location.href="/xmuCadre/administrationWork/openAdministrationWorkMoreDel.do";
		});
	}
	
	//关闭批量删除
	function bindCloseManyDelEvent(){
		$(".backBtn").click(function(){
			window.location.href="/xmuCadre/administrationWork/administrationWorkList.do";
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
			art.dialog.confirm("确定删除本页面被选中的行政工作经历？",function(){
					$.post("${ctx}/administrationWork/administrationWorkManyDel.do?str="+str,
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
	//单个删除操作
		function bindDelEvent() {
			$(".delBtn").click(function(){
				var thiz = $(this);
				var id = thiz.parent().attr("TID");
				art.dialog.confirm("确定删除？",function(){
					$.post("${ctx}/administrationWork/administrationWorkDel.do",{id:id},
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
			bindAddEvent();
			bindDelEvent();
			bindEditEvent();
			bindManyDelEvent();
			bindOpenManyDelEvent();
			bindCloseManyDelEvent();
		});
	</script>    
  </body>
</html>
