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
    <div class="form_head"><span>人员列表</span></div>  
    <c:if test="${roleType=='admin' }">
	<input type="button" class="btn addBtn" value="添加人员" style="margin-left:10px;"/>
	</c:if>
	<!-- 
    <input type="button" class="btn" id="exportBtn" value="导出结果" style="margin-left:10px;"/>
    -->
    <input type="button" class="btn" id="exportListBtn" value="导出花名册" style="margin-left:10px;"/>
    <input type="button" class="btn superSearchBtn" value="高级查询" style="margin-left:10px;"/> 
    <form id="searchUserForm" action="${ctx}/infoManager/userInfo/userList.do" method="post">
		<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr }" style="width:220px;" onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=40 />
		<input type="text" id="queryStrTemp" class="txt txtkey" value="用户账号/姓名/员工号/证件号码" style="width:220px;"/>
		<input type="submit" value="查询" class="btn search_btn" />
		<div class="AdvantageSearch" style="display:none">
			姓名：<input type="text" id="SuperSearchName" name="name" value="${name }" onkeyup="value=value.replace(/[^a-zA-Z\u4E00-\u9FA5 ]/g,'')" maxlength=40/>
			性别：<select id="SuperSearchSex" name="sex">
					<option value="1">男</option>
					<option value="0">女</option>
				</select>
			证件号码：<input type="text" id="SuperSearchIdNumber" name="IdNumber" value="${IdNumber }" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" maxlength=20/><br />
			出生年月：
				<select id="SuperBirthdaySelect" name="birthdayOperator">
					<option value="R">晚于</option>
					<option value="E" selected>等于</option>
					<option value="L">早于</option>
				</select>
			<input type="text" id="SuperSearchBirthday" name="birthday" value="${birthday }" class="txt Wdate " onfocus="WdatePicker({readOnly:false})"/>
			年龄：<select id="SuperAgeOperator" name="ageOperator">
					<option value="R">大于</option>
					<option value="E" selected>等于</option>
					<option value="L">小于</option>
				</select>
			<input type="text" id="SuperSearchAge" name="age" value="${age }" onkeyup="value=value.replace(/[^0-9]/g,'')" maxlength=3/><br />
			职位级别：<select id="SuperPositionNameOperator" name="positionNameOperator">
					<option value="R">大于</option>
					<option value="E" selected>等于</option>
					<option value="L">小于</option>
				</select>
				<select id="PositionName" name="positionName">
					<option value="">未选择</option>
					<c:forEach items="${ide:getAllItems('positionLevel') }" var="pl">
						<option value="${pl.key }">${pl.value }</option>
					</c:forEach>
				</select>
			<input type="button" id="SuperSearchBtn" value="查 询"/>
		</div>
	</form>
    	<table class="tblist">
				<tr>
					<th>姓名</th>
					<th>性别</th>
					<th>证件号码</th>
					<th>出生年月</th>
					<th>年龄</th>
					<th>来校时间</th>
					<th>级别</th>
					<th>单位</th>
					<th>职务(最近或现职)</th>
					<th class="c">操作</th>
				</tr>
				<c:forEach items="${queryList.result }" var="uv">
					<tr>
						<td>${uv.user.name }</td>
						<td>${uv.user.gender }</td>
						<td>${uv.user.identifyNum }</td>
						<td><fmt:formatDate value='${uv.user.birthDay }'  pattern='yyyy-MM-dd'/></td>
						<td>
						<c:choose>
   							<c:when test="${uv.user.birthDay != null}">
   								    <c:choose>
   								    	<c:when test="${today.month >uv.user.birthDay.month}">
   								    		${today.year -uv.user.birthDay.year} 
   								    	</c:when>
   								    	<c:when test="${today.month == uv.user.birthDay.month && today.date >= uv.user.birthDay.date}">
   								    		${today.year -uv.user.birthDay.year} 
   								    	</c:when>
   								    	<c:otherwise>
   								    		${today.year -uv.user.birthDay.year-1} 
   								    	</c:otherwise>
   								    </c:choose>
    						</c:when>
    						<c:otherwise>
      									  ${null}
    						</c:otherwise>
						</c:choose>
						</td>
						<td><fmt:formatDate value='${uv.user.beginSchoolWorkDay }'  pattern='yyyy-MM-dd'/></td>
						<td>${ide:getItem("positionLevel",uv.user.level) }</td>
						<td>${uv.administrationWorkUnits }</td> <!-- 校内工作经历 -->
						<td>${uv.administrationWorkName }</td>
						<td class="c" TID="${uv.user.id }">
							<input type="button" class="btn btn_cz detailBtn" value="员工详情"/>
							<input type="button" class="btn btn_cz detailAsseBtn" value="历年考核情况"/>
							<c:if test="${roleType=='admin' }">
							<input type="button" class="btn btn_cz openAsseAdd" value="考核"/>
							<input type="button" class="btn btn_cz setPwdBtn" value="重置密码" >
							<input type="button" class="btn btn_cz editBtn" value="修改" >
							<input type="button" class="btn btn_cz delBtn" value="删除"  >
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			<ide:page page="${queryList }" pageId="userListPage" searchForm="searchUserForm"></ide:page>
  
	<a style="display:none;" id="exportUserA" href="${ctx }/infoManager/userInfo/exportUser.do?isNew=false"><span id="exportUser">导出</span></a>
	<a style="display:none;" id="export" href="${ctx }/infoManager/userInfo/exportUserList.do"><span id="ex">导出</span></a>
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
	    //高级搜素打开
	    var searchAction = 1;
	    function bindSuperSearchEvent(){
	    	$(".superSearchBtn").click(function(){
	    		if(searchAction==1){
	    		$(".AdvantageSearch").css("display","block");
	    		$("#queryStrTemp").css("display","none");	    	
	    		$("#queryStr").css("display","none");	    		
	    		$(".search_btn").css("display","none");
	    		$(".superSearchBtn").val("普通查询");
	    		searchAction = 2;
	    	}
	    	else if (searchAction == 2){
	    		$(".AdvantageSearch").css("display","none");
	    		$("#queryStrTemp").css("display","none");    	
	    		$("#queryStr").css("display","inline-block");	   	    		
	    		$(".search_btn").css("display","inline-block");
	    		$(".superSearchBtn").val("高级查询");
	    		searchAction = 1;
	    	}
	    	});
	    }
	    
	    
	    //添加操作
		function bindAddEvent() {
			$(".addBtn").click(function(){
				art.dialog.open(
					"${ctx}/infoManager/userInfo/openUserAdd.do",
					{ title: "添加用户", lock: true, width: 996, height:500 }
				);
			//self.location.href="${ctx}/infoManager/userInfo/openUserAdd.do";				
			});
		}
		
		//修改操作
		function bindEditEvent() {
			$(".editBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/infoManager/userInfo/openUserEdit.do?id="+id,
					{ title: "修改用户", lock: true, width: 996, height: 500 }
				);				
			});
		}
		//删除操作
		function bindDelEvent() {
			$(".delBtn").click(function(){
				var thiz = $(this);
				var id = thiz.parent().attr("TID");
				art.dialog.confirm("确定删除？",function(){
					$.post("${ctx}/infoManager/userInfo/userDel.do",{id:id},
						function(result){
			                if("success" == result) {
			                	art.dialog.tips("删除成功");
			                	self.location.href="${ctx}/infoManager/userInfo/userList.do";               
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
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/infoManager/userInfo/openUserDetail.do?id="+id,
					{ title: "用户详情", lock: true, width: 996, height: 600 }
				);				
			});
		}
		
		//导出文件
		function bindExportEvent(){
			$("#exportBtn").click(function(){
				art.dialog.open("${ctx}/infoManager/userInfo/openTitleCheck.do",{ 
					title: "导出数据", 
					lock: true, 
					width: 996, 
					height: 450,
					close:function(){
						var titleIds = $("#exportUserA").attr("titles");
						var url = "${ctx }/infoManager/userInfo/exportUser.do?isSuperSearch=${isSuperSearch}";
						if(titleIds != '' && titleIds != undefined){
							$("#exportUserA").attr("href",url+"&titles="+titleIds+"&queryStr="+encodeURI(encodeURI('${queryStr }'))+"&sex="+encodeURI('${sex}')+"&name="+encodeURI(encodeURI('${name}'))+"&idNumber="+encodeURI('${idNumber}')+"&birthdayOperator="+encodeURI('${birthdayOperator}')+"&birthday="+encodeURI('${birthday}')+"&ageOperator="+encodeURI('${ageOperator}')+"&age="+encodeURI('${age}')+"&positionName="+encodeURI(encodeURI('${positionName}'))+"&positionNameOperator="+encodeURI('${positionNameOperator}'));
							$("#exportUserA").attr("titles","");
							$("#exportUser").click();
						}
					} 
				});		
			});
		}
		
		//导出花名册
		function bindExportListEvent(){
			$("#exportListBtn").click(function(){
				art.dialog.open(
					"${ctx}/infoManager/userInfo/openExportRoster.do?",
					{ title: "导出花名册", lock: true, width: 758, height: 500 }
				);
			});
		}
		
		//高级查找
		function bindSuperSearchBtn(){
			$("#SuperSearchBtn").click(function(){
				$("#searchUserForm").attr("action","${ctx }/infoManager/userInfo/SuperSearchUserList.do");
				//表单验证  万超 要做下可以空 但是有值的话必须要符合 比如数字 
				$("#searchUserForm").submit();
			});
		}
		//年度考核
		function bindAsseDetailEvent() {
			$(".detailAsseBtn").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/infoManager/annualAsse/openUserAsseDetail.do?userId="+id,
					{ title: "考核详情", lock: true, width: 758, height: 500 }
				);
			});
			
		}
		//年度考核增加
		function bindAsseAddEvent() {
			$(".openAsseAdd").click(function(){
				var id = $(this).parent().attr("TID");
				art.dialog.open(
					"${ctx}/infoManager/annualAsse/openAsseAdd.do?userId="+id,
					{ title: "年度考核", lock: true, width: 500, height: 280 }
				);				
			});
		}
		function bindSetPwdBtnEvent(){
			$(".setPwdBtn").click(function(){
				var id = $(this).parent().attr("TID");
				$.post("${ctx }/infoManager/userInfo/setPwd.do",{id:id},function(result){
					if ("success" == result){
						art.dialog.tips("重置成功");
					}else{
						art.dialog.alert("失败");
					}
				});
			});
		}
		
	    $(function(){
	    	bindExportListEvent();
	    	bindQueryTextEvent();
	    	bindEditEvent();
	    	bindDetailEvent();
	    	bindAsseDetailEvent();
	    	bindAddEvent();
	    	bindDelEvent();
	    	bindSuperSearchBtn();
	    	bindSuperSearchEvent();
	    	bindAsseAddEvent();
   			bindExportEvent();
   			bindSetPwdBtnEvent();
    		//搜索按钮
   			$(".search_btn").click(function(){
                $("#searchUserForm").submit();
   			});
   			if ("${isSuperSearch}" == "1") {
   				$(".superSearchBtn").click();
   				$("#searchUserForm").attr("action","${ctx }/infoManager/userInfo/SuperSearchUserList.do");
   			}
   			$("#SuperSearchSex").val("${sex}" == "" ? "1" : "${sex}");
   			$("#SuperBirthdaySelect").val("${birthdayOperator}" == "" ? "E" : "${birthdayOperator}");
   			$("#SuperAgeOperator").val("${ageOperator}" == "" ? "E" : "${ageOperator}");
   			$("#SuperPositionNameOperator").val("${positionNameOperator}" == "" ? "E" : "${positionNameOperator}");
   			$("#PositionName").val("${positionName}" == "" ? "" : "${positionName}");
	    });
	</script>
  </body>
</html>
