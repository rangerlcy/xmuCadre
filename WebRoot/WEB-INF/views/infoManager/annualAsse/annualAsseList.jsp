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
  <div class="form_head"><span>年度考核列表</span></div>
  <c:if test="${roleType=='admin' }">
	<input type="button" value="批量操作" class="btn" onclick="self.location.href='${ctx}/infoManager/quantityManager/batchAsseAction.do'"/>
  </c:if>
  <form id="searchUserForm" action="${ctx}/infoManager/annualAsse/annualAsseList.do" method="post">
		<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;"  onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=40/>
		<input type="text" id="queryStrTemp" class="txt txtkey" value="用户账号/姓名/员工号/证件号码" style="width:220px;"/>
		<select name='year'>
			<c:forEach begin="1900" end="2099" varStatus="index">
				<option value="${index.index }" <c:if test="${index.index == year}">selected</c:if>>${index.index }</option>
			</c:forEach>
		</select>
		<input type="submit" value="查询" class="btn search_btn" />
	</form>
    <table class="tblist">
				<tr>
					<th>姓名</th>
					<th>性别</th>
					<th>证件号码</th>
					<th>${year }本年度考核结果</th>
					<th class="c">操作</th>
				</tr>
				<c:if test="${queryList == null or empty queryList.result } ">
					<tr><td colspan="5">无相应记录</td></tr>
				</c:if>
				<c:forEach items="${queryList.result }" var="uv">
					
					<tr>
						<td>${uv.user.name }</td>
						<td>${uv.user.gender }</td>
						<td>${uv.user.identifyNum }</td>
						<td>${uv.assessmentResult }</td>
						<td class="c" TID="${uv.id }" TUID="${uv.user.id }">
							<input type="button" class="btn btn_cz detailBtn" value="员工详情"/>
							<input type="button" class="btn btn_cz detailAsseBtn" value="历年考核情况"/>
							<c:if test="${roleType=='admin' }">
							<input type="button" class="btn btn_cz editBtn" value="修改" >
							<input type="button" class="btn btn_cz delBtn" value="删除"  >
							</c:if>
						</td>
					</tr>
					
				</c:forEach>
			</table>
			<ide:page page="${queryList }" pageId="userListPage" searchForm="searchUserForm"></ide:page>
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
	    //修改操作
		function bindEditEvent() {
			$(".editBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/infoManager/annualAsse/openAsseEdit.do?id="+id,
					{ title: "修改考核信息", lock: true, width: 758, height: 333 }
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
		//用户详情
		function bindDetailEvent() {
			$(".detailBtn").click(function(){
				var id = $(this).parent().attr("TUID");
				art.dialog.open(
					"${ctx}/infoManager/userInfo/openUserDetail.do?id="+id,
					{ title: "用户详情", lock: true, width: 996, height: 500 }
				);				
			});
		}
		function bindAsseDetailEvent() {
			$(".detailAsseBtn").click(function(){
				var id = $(this).parent().attr("TUID");
				art.dialog.open(
					"${ctx}/infoManager/annualAsse/openUserAsseDetail.do?userId="+id,
					{ title: "考核详情", lock: true, width: 996, height: 500 }
				);
			});		
		}
	$(function(){
		bindQueryTextEvent();
		bindAsseDetailEvent();
		bindEditEvent();
		bindDelEvent();
		bindDetailEvent();
	});
	</script>
  </body>
</html>
