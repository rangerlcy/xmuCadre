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
    <div class="Info_lists">
	    <div class="form_head"><span>干部培训信息</span></div>
	    <c:if test="${roleType=='admin' }">
	    <input type="button" class="btn addBtn" value="添加培训事件" style="margin-left:10px;"/>
	    <input type="button" class="btn batchaddBtn" value="批量添加培训事件" style="margin-left:10px;"/>
	    <input type="button" class="btn batchdeleteBtn" value="批量删除培训事件" style="margin-left:10px;"/>
	    </c:if>
		<input type="button" class="btn superSearchBtn" value="高级查找培训事件" style="margin-left:10px;"/>
	    <form id="searchUserForm" action="${ctx}/userTrain/UserTrainInfo/UserTrainList.do" method="post">
			<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;" onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=40 />
			<input type="text" id="queryStrTemp" class="txt txtkey"  value="教育培训名称/教育培训期数/主办单位/培训地点" style="width:220px;"/>
			<input type="submit" value="查询" class="btn search_btn" />
			<div class="AdvantageSearch" style="display:none">
				培训名称：<input type="text" id="SuperSearchName" name="trainingName" />
				培训期数：<input type="text" id="SuperSearchPeriod" name="trainingPeriod" onkeyup="value=value.replace(/[^\d]/g,'')" />
				<br/>
				主办单位：<input type="text" id="SuperSearchOrganizer" name="organizer" />
				培训地点：<input type="text" id="SuperSearchPlace" name="trainingPlace" />
				<br/>
				培训开始时间：<select id="SuperbeginDayOperator" name="beginDayOperator">
					<option value="R">晚于</option>
					<option value="E" selected>等于</option>
					<option value="L">早于</option>
				</select>
				<input type="text" id="SuperSearchBeginDay" name="beginDay" class="txt Wdate " onfocus="WdatePicker({readOnly:false})"/>
				<br/>
				<input type="button" id="SuperSearchBtn" value="查询">
			</div>
		</form>
	
    	<table class="tblist">
				<tr>
					<td>序号</td>
					<td>开始时间</td>
					<td>结束时间</td>
					<td>教育培训名称</td>
					<td>教育培训期数</td>
					<td>主办单位</td>
					<td>教育培训地点</td>
					<td class="c">操作</td>
				</tr>
				<c:forEach items="${queryList.result }" var="ut">
					<tr>
						<td>${ut.id }</td>
						<td><fmt:formatDate value="${ut.beginDay }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${ut.endDate }" pattern="yyyy-MM-dd"/></td>
						<td>${ut.trainingName }</td>
						<td>${ut.trainingPeriod }</td>
						<td>${ut.organizer }</td>
						<td>${ut.trainingPlace }</td>
						<td class="c" TID="${ut.id }">
							<input type="button" class="btn btn_cz detailBtn" value="详情" >
							<c:if test="${roleType=='admin' }">
							<input type="button" class="btn btn_cz updateBtn" value="修改培训事件">
							<input type="button" class="btn btn_cz addUserBtn" value="添加培训人员">
							<input type="button" class="btn btn_cz deleteUserBtn" value="删除培训人员">
							<input type="button" class="btn btn_cz delBtn" value="删除" >
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		 <ide:page page="${queryList }" pageId="AADListPage" searchForm="searchUserForm"></ide:page>
	
    </div>
<script type="text/javascript">
		//绑定查询输入框事件
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
	    
	    //高级搜索打开
	    var searchAction = 0;
	    function bindSuperSearchEvent(){
	    	$(".superSearchBtn").click(function(){
	    		if(searchAction==0){
		    		$(".AdvantageSearch").css("display","block");
		    		$("#queryStrTemp").css("display","none");	    	
		    		$("#queryStr").css("display","none");	    		
		    		$(".search_btn").css("display","none");
		    		$(".superSearchBtn").val("普通查询");
		    		searchAction = 1;
		    	}
		    	else{
		    		$(".AdvantageSearch").css("display","none");
		    		$("#queryStrTemp").css("display","none");    	
		    		$("#queryStr").css("display","inline-block");	   	    		
		    		$(".search_btn").css("display","inline-block");
		    		$(".superSearchBtn").val("高级查询");
		    		searchAction = 0;
	    	}
	    	});
	    }
	    
	    	    
	    //添加培训项目操作
		function bindAddEvent() {
			$(".addBtn").click(function(){
				art.dialog.open(
					"${ctx}/userTrain/UserTrainInfo/openTrainAdd.do",
					{ title: "添加培训项目", lock: true, width: 996, height:500 }
				);				
			});
			}
			
		//批量添加操作
		function bindBatchAddEvent() {
			$(".batchaddBtn").click(function(){
				art.dialog.open(
					"${ctx}/userTrain/UserTrainInfo/openTrainBatchAdd.do",
					{ title: "批量添加培训项目",lock: true, width: 996, height:500 }
				);				
			});
		}
		
		//批量删除操作
		function bindBatchDeleteEvent() {
			$(".batchdeleteBtn").click(function(){
				art.dialog.open(
					"${ctx}/userTrain/UserTrainInfo/openTrainBatchDelete.do",
					{ title: "批量删除培训项目",lock: true, width: 996, height:500 }
				);				
			});
		}
	    //高级搜索按钮
	    function bindSuperSearchBtn(){
			$("#SuperSearchBtn").click(function(){
				$("#searchUserForm").attr("action","${ctx}/userTrain/UserTrainInfo/SuperSearchTrainUserList.do");
				//表单验证  万超 要做下可以空 但是有值的话必须要符合 比如数字 
				$("#searchUserForm").submit();
			});
		}
			
		//添加培训人员操作
		function bindAddTrainUser(){
			$(".addUserBtn").click(function(){
				var trainId = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/userTrain/UserTrainInfo/openTrainAddUser.do?trainId="+trainId,
					{ title:"添加培训人员", lock: true, width: 996, height: 500}
				);
			});
		}
		//删除培训人员操作
		function bindDeleteTrainUser(){
			$(".deleteUserBtn").click(function(){
				var trainId = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/userTrain/UserTrainInfo/openTrainUserDelete.do?trainId="+trainId,
					{ title:"删除培训人员", lock: true, width: 996, height: 500}
				);
			});
		}
		//编辑操作
		function bindEditEvent() {
			$(".updateBtn").click(function(){
				var trainId = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/userTrain/UserTrainInfo/openTrainEdit.do?trainId="+trainId,
					{ title:"修改培训信息", lock: true, width:996, height: 500}
				);
				
			});
		}

		//删除操作
		function bindDelEvent() {
			$(".delBtn").click(function(){
				var thiz = $(this);
				var id = thiz.parent().attr("TID");
				art.dialog.confirm("确定删除？",function(){
					$.post("${ctx}/userTrain/UserTrainInfo/deleteTrain.do",{id:id},
						function(result){
			                if("success" == result) {
			                	self.location.reload();               
			                } else {
			                    art.dialog.tips("删除失败");
			                }
			        	}
			        );
				});					
			});
		}
		//培训详情
		function bindDetailEvent() {
			$(".detailBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/userTrain/UserTrainInfo/openTrainDetail.do?trainId="+id,
					{ title: "培训详情", lock: true, width: 996, height: 600 }
				);				
			});
		}
		
		//导出文件
		function bindExportEvent(){
			$("#exportBtn").click(function(){
				art.dialog.open("${ctx}/sys/user/openTitleCheck.do",{ 
					title: "导出数据", 
					lock: true, 
					width: 1000, 
					height: 450,
					close:function(){
						var titleIds = $("#exportUserA").attr("titles");
						var url = "${ctx }/sys/user/exportUser.do?isNew=false&queryDept=${queryDept}";
						if(titleIds != '' && titleIds != undefined){
							$("#exportUserA").attr("href",url+"&titles="+titleIds+"&queryStr="+encodeURI(encodeURI('${queryStr }')));
							$("#exportUserA").attr("titles","");
							$("#exportUser").click();
						}
					} 
				});		
			});
		}
		
	    $(function(){
	    	bindQueryTextEvent();
	    	bindSuperSearchEvent();
	    	bindSuperSearchBtn();
	    	bindDetailEvent();
	    	bindAddEvent();
	    	bindBatchAddEvent();
	    	bindBatchDeleteEvent();
	    	bindAddTrainUser();
	    	bindDelEvent();
	    	bindEditEvent();
	    	bindDeleteTrainUser();
	    	//bindLcInfoEvent();
   			//bindExportEvent();
    		//搜索按钮
   			$(".search_btn").click(function(){
                $("#searchUserForm").submit();
   			});
	    });
	</script>
  </body>
</html>
