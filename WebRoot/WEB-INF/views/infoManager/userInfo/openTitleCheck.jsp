<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
  </head>
  
  <body>
     <div class="right_box" style="width:100%;">
			<div class="con">选择导出标题</div>
			<div class="">
				<input type="button" id="allCheck" value="全选" />
				<input type="button" id="backCheck" value="反选" /></div>
			<div>
				<ul class="ul_selblock" style="margin:0px 20px;" >
					<c:forEach items="${ide:getAllExportTitle()}" var="title">
		    			<li titleId="${title.key }" class="on checkitem">${title.value }</li>
					</c:forEach>
				</ul>
			</div>
			<div class="tb_sub_btns" style="padding-bottom:20px;">
				<input type="button" value="确定" class="add" id="saveBtn"/> 
				<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
			</div>
			
		</div>
			<script type="text/javascript">
		//标题选中事件
		function bindTitleClickEvent(){
			
			$("#allCheck").click(function(){
				$(".checkitem").addClass("on");	
			})
			$("#backCheck").click(function(){
				$(".checkitem").each(function(){
					if($(this).hasClass("on")){
						$(this).removeClass("on");
					}else{
						$(this).addClass("on");
					}
				})
			});
		
			$(".ul_selblock li.checkitem").click(function(){
				if($(this).hasClass("on")){
					$(this).removeClass("on");
				}else{
					$(this).addClass("on");
				}
			});
		}
		
		//导出文件
		function bindExportEvent(){
			$("#saveBtn").click(function(){
				var titleIds = [];
				$(".ul_selblock li.checkitem").each(function(){
					if($(this).hasClass("on")){
						var titleId = $(this).attr("titleId");
						titleIds.push(titleId);
					}
				});
				if(titleIds == ''){
					art.dialog.alert("导出标题不能为空");
					return;
				}
				$(art.dialog.opener.document.getElementById("exportUserA")).attr("titles",titleIds.join(","));
				art.dialog.close();		
			});
		}

	    $(function(){
			bindTitleClickEvent();//角色选中事件
			bindExportEvent();//保存提交
	    });
	</script>
  </body>
</html>
