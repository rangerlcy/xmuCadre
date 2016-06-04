<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
  </head>
  
  <body>
<div class="tb_700">
	<table class="listInfo" cellspacing="0">
	  	<tr>
	  		<td>姓名</td>
			<td>性别</td>
			<td>证件号码</td>
			<td>年份</td>
			<td>考核结果</td>
			<td>操作</td>
	  	</tr>
	  	<c:if test="${empty asseList}">
			<tr><td colspan="6"><center>无相应记录</center></td></tr>
		</c:if>
	  	<c:forEach items="${asseList }" var="asse">
	    	<tr>
	    		<td>${asse.user.name }</td>
	    		<td>${asse.user.gender }</td>
	    		<td>${asse.user.identifyNum }</td>
	    		<td>${asse.year }</td>
	    		<td>${asse.assessmentResult }</td>
	    		<td TID = "${asse.id }">
	    			<c:if test="${roleType=='admin' }">
	    			<input type="button" value="修改" class="btn editBtn" />
	    			<input type="button" value="删除" class="btn delBtn" />
	    			</c:if>
	    		</td>
	    	</tr>
	    </c:forEach>
	</table>
</div>
<div class="tb_sub_btns">
	<input type="button" value="关闭" class="cancel" onClick="art.dialog.close()" />
</div>
   <script type="text/javascript">
    //修改操作
		function bindEditEvent() {
			$(".editBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/infoManager/annualAsse/openAsseEdit.do?id="+id,
					{ title: "修改考核信息", lock: true, width: 500, height: 280 }
				);				
			});
		}
		//删除操作
		function bindDelEvent() {
			$(".delBtn").click(function(){
				var thiz = $(this);
				var id = thiz.parent().attr("TID");
				art.dialog.confirm("确定删除？",function(){
					$.post("${ctx}/infoManager/annualAsse/asseDel.do",{id:id},
						function(result){
			                if("success" == result) {
			                	art.dialog.tips("删除成功");
			                	self.location.href="${ctx}/infoManager/annualAsse/annualAsseList.do";          
			                } else {
			                    art.dialog.tips("删除失败:"+result);
			                }
			        	}
			        );
				});					
			});
		}
		$(function(){
		bindEditEvent();
		bindDelEvent();
		});
   </script>
  </body>
</html>
