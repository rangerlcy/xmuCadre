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
 <div class="place">
 		 <form id="searchPlaceForm" action="${ctx }/sys/place/placeList.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"   maxlength=40/>
			<input type="text" id="queryStrTemp" class="txt txtkey" value="省份名称" style="width:220px;"/>
			<input type="submit" value="查询" class="btn search_btn" />
			<input type="button" value="新增" class="btn add_btn"/>
		</form> 
    	<table class="tblist" >
				<tr>
					<th>省份名称</th>
					<th class="c">操作</th>
				</tr>
				<c:forEach items="${queryList.result }" var="uv">
				<tr>
						<td>${uv.name}</td>
						<td class="c" TID="${uv.code } " NN = "${uv.name }" >
							<input type="button" class="btn btn_cz delBtn" value="删除" >
							<input type="button" class="btn btn_cz editBtn" value="修改" >
							<input type="button" class="btn btn_cz detailBtn" value="查看该省份下级区划" >
						</td>
						</tr>
				</c:forEach>
			</table>
			<ide:page page="${queryList }" pageId="userListPage" searchForm="searchPlaceForm"></ide:page>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		bindQueryTextEvent();
		bindDeleteEvent();
		bindEditEvent();
		bindDetailEvent();
		bindAddEvent();
	});
	function bindDeleteEvent(){
		$(".delBtn").click(function(){
			var code = $(this).parent().attr("TID");
					var thiz = $(this);
					art.dialog.confirm("确定要删除？删除之后该省份下所有信息清除",function(){
						$.post("${ctx}/sys/place/queryByCode.do",{code : code},function(result){
							$.post("${ctx}/sys/place/deleteProv.do",{code : code},function(result1){
							alert("删除成功");
							window.location.reload(); 
							},"text");
						},"json");
					});
		});
	}
	function bindAddEvent(){
	$(".add_btn").click(function(){
	art.dialog({ 
				title: "新增省份", 
				content: "<br/><span style='font-size:5px'>请输入新增省份名称<span><br/>"+"<br/><input type='text' id='name'/>",
				ok: function(){
						$.post("${ctx}/sys/place/save.do",
						{
							name: $("#name").val(),
						},
						function(result){
							if(result=="success"){
								alert("新增成功");
								window.location.reload();  
							}
							});
							}
		});
		});
		}
	function bindEditEvent(){
		$(".editBtn").click(function(){
			var code = $(this).parent().attr("TID");
			var name = $(this).parent().attr("NN");
			art.dialog({ 
				title: "省份修改", 
				content: name+"<br/><span style='color:red;font-size:5px'>请输入修改后省份名称<span><br/>"+"<br/><input type='text' id='name'/>",
				ok: function(){
						$.post("${ctx}/sys/place/update.do",
						{
							code: code,
							name: $("#name").val(),
						},
						function(result){
							if(result=="success"){
								alert("修改成功");
								window.location.reload();  
							}
						});
				},
				cancelVal: '关闭',
				cancel:true,
				lock: true
			});
		});
	}
	function bindDetailEvent(){
		$(".detailBtn").click(function(){
			var code = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/sys/place/openBellow.do?parentCode="+code,
					{ title: "二级行政区域", lock: true, width: 800, height: 450 }
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