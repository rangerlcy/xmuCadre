<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<title>厦门大学干部信息管理系统</title>
		<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
		<link rel="stylesheet" href="${res }/css/content.css" type="text/css"  />
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
	</head>
	<body class="ny_body">
	    <div class="r_search">
   			<div class="form_head"><span>挂职信息</span></div> 
   			<c:if test="${roleType=='admin' }">
			<input type="button" class="btn addBtn" value="添加挂职信息" style="margin-left:10px;"/>
			</c:if>
		    <input type="button" class="btn" id="exportBtn" value="导出结果" style="margin-left:10px;"/>
		    <input type="button" class="btn superSearchBtn" value="高级查询" style="margin-left:10px;"/> 
		    <%-- <input type="button" class="btn importsecondmentUserBtn" value="批量操作" onclick="self.location.href='${ctx}/secondment/openSecondmentBatchAction.do'"/> --%>
			<input id="projectBtn" type="button" value="按挂职项目排序" class="btn project_btn" style="margin-left:10px;"/>
			<form id="querySecondmentForm" action="${ctx }/secondment/secondment.do" method="post">
				<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr}" onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=50 />
			    <input type="text" id="queryStrTemp" class="txt txtkey" value="除持续时间外的信息查询"/>
			     <input id="searchBtn" type="submit" class="btn searchBtn" value="查询" style="cursor: pointer;margin-right:0px;"/> 
			     <!-- <input type="button" class="btn btn_cz importSecondmentBtn" value="批量导入挂职信息" style="cursor: pointer;margin-right:0px;"/> -->
			     
	<div class="AdvantageSearch" style="display:none">	
			     
	        发文文号：<input type="text" id="SuperSearchPaperNumber" name="PaperNumber"  onkeyup="value=value.replace(/[^\w]/g,'')" maxlength=10 />
	         发文题目：<input type="text" id="SuperSearchPaperTitle" name="PaperTitle" onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=50/><br />
	    
	        发文单位：<input type="text" id="SuperSearchPaperUnit" name="PaperUnit" onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ]/g,'')" maxlength=50/>
	         挂职项目名称：<input type="text" id="SuperSearchTemporaryProjectName" name="temporaryProjectName"  onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ,;:：，；。.《》<>\n、]/g,'')" maxlength=50/><br />
	        挂职开始时间：
			<select id="SuperTemporaryBeginDaySelect" name="temporaryBeginDayOperator">
				<option value="R">晚于</option>
				<option value="E" selected>等于</option>
				<option value="L">早于</option>
			</select>
			<input type="text" id="SuperSearchTemporaryBeginDay" name="temporaryBeginDay" class="txt Wdate " onfocus="WdatePicker({readOnly:false})"/>
	      挂职结束时间：
			<select id="SuperTemporaryEndDateSelect" name="temporaryEndDateOperator">
				<option value="R">晚于</option>
				<option value="E" selected>等于</option>
				<option value="L">早于</option>
			</select>
			<input type="text" id="SuperSearchTemporaryEndDate" name="temporaryEndDate" class="txt Wdate " onfocus="WdatePicker({readOnly:false})"/>        
	        <br /> 挂职人员要求：<input type="text" id="SuperSearchTemporaryRequirement" name="temporaryRequirement"  onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5 ,;:：，；。.《》<>\n、]/g,'')" maxlength=120/>
		
		<input type="button" id="SuperSearchBtn" value="查询"/><br />
	</div>
		    </form>
		</div>
    	<div>
	    <table cellpadding="0" cellspacing="0" class="tblist">
	        <tr>
	            <th>发文文号</th>
	            <th>发文题目</th>
	            <th>发文单位</th>
	            <th>挂职项目名称</th>
	            <th>挂职开始时间</th>
	            <th>挂职结束时间</th>
	            <th>挂职持续时间</th>
	            <th>挂职人员要求</th>
	          
	            <th>备注</th>
	            <th class="c">操作</th>
	        </tr>
	        <c:if test="${queryList.result != null }">
	          <c:forEach items="${queryList.result }" var="secondment">
	              <tr>
	                <td id="secondment_postingNumber_${secondment.id }">${secondment.paper.paperNumber }</td>
	                <td id="secondment_postingTitle_${secondment.id }">${secondment.paper.paperName }</td>
	                <td id="secondment_postingUnit_${secondment.id }">${secondment.paper.paperUnits }</td>
	                <td id="secondment_temporaryProjectName_${secondment.id }">${secondment.temporaryProjectName }</td>
	                <td id="secondment_temporaryBeginDay_${secondment.id }"><fmt:formatDate value='${secondment.temporaryBeginDay }'  pattern='yyyy-MM-dd'/></td>
	                <td id="secondment_temporaryEndDate_${secondment.id }"><fmt:formatDate value='${secondment.temporaryEndDate }'  pattern='yyyy-MM-dd'/></td>
	                <td id="secondment_temporaryLastTime_${secondment.id }">${ (secondment.temporaryEndDate.time - secondment.temporaryBeginDay.time) /1000/3600/24}</td>
	                <td id="secondment_temporaryRequirement_${secondment.id }">${secondment.temporaryRequirement }</td>
	         
	                <td id="secondment_remark_${secondment.id }">${secondment.remark }</td>
	                
	                <td>
	                    <input type="button" class="btn btn_cz detailBtn" value="查看详情" secondmentId="${secondment.id }" >
	                    <c:if test="${roleType=='admin' }">
                    	<input type="button" class="btn btn_cz updateBtn" value="修改" secondmentId="${secondment.id }">
	                    <input type="button" class="btn btn_cz delBtn" value="删除" secondmentId="${secondment.id }">
	                   
	                    <input type="button" class="btn btn_cz delSecondmentUserBtn" value="删除挂职人员"  secondmentId="${secondment.id }">
	                   	<input type="button" class="btn btn_cz addSecondmentUserBtn" value="添加挂职人员"  secondmentId="${secondment.id }">
	                    </c:if>
	                </td>
	            </tr>
	          </c:forEach>
	        </c:if>
	    </table>
	    <ide:page page="${queryList }" pageId="secondmentPage" searchForm="querySecondmentForm"></ide:page>
	    <a style="display:none;" id="exportSecondmentA" href="${ctx }/secondment/exportSecondment.do?isNew=false"><span id="exportSecondment">导出</span></a>
    </div>
  	
    <script type="text/javascript">
    /*
    $("#SuperSearchBtn").click(function(){
    	var ss=$("#querySecondmentForm").serialize();
    	alert(ss);
    	$.post("${ctx}/secondment/SuperSearchSecondment.do",{
		                		data:ss
							},function(result,status,xhr){
							alert(result);
    });*/
    
        $(function(){
            bindSubmitFormEvent();//绑定提交表单事件
            bindUpdateBtnEvent(); //绑定修改按钮事件
            bindDelBtnEvent(); //绑定删除按钮事件
			bindSuperSearchEvent();
            bindQueryTextEvent();//绑定查询输入框
            bindSearchEvent();//绑定搜索框
            bindSuperSearchBtn();//绑定高级搜索按钮事件
   			bindExportEvent();//绑定导出按钮事件
   			binProjectEvent();//绑定按挂职项目排序事件
   			bindImportSecondmentBtnEvent();//批量导入挂职信息
   			addSecondmentUserEvent();
   			delSecondmentUserEvent();
   			bindDetailEvent();//绑定查看详情按钮事件
   			//binUserEvent;//绑定按挂职人员排序事件
        
           
        });
        function bindSearchEvent(){
			$("#searchBtn").click(function(){
                $("#querySecondmentForm").submit();
   			});
		}
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
	    		$(".searchBtn").css("display","none");
	    		$(".superSearchBtn").val("普通查询");
	    		searchAction = 2;
	    	}
	    	else if (searchAction == 2){
	    		$(".AdvantageSearch").css("display","none");
	    		$("#queryStrTemp").css("display","none");    	
	    		$("#queryStr").css("display","inline-block");	   	    		
	    		$(".searchBtn").css("display","inline-block");
	    		$(".superSearchBtn").val("高级查询");
	    		searchAction = 1;
	    	}
	    	});
	    }
	     //高级查找
		function bindSuperSearchBtn(){
			$("#SuperSearchBtn").click(function(){
				//alert($("#querySecondmentForm").attr("id"));
				$("#querySecondmentForm").attr("action","${ctx }/secondment/SuperSearchSecondment.do");
				//表单验证  万超 要做下可以空 但是有值的话必须要符合 比如数字 
				alert($("#querySecondmentForm").serialize());
				$("#querySecondmentForm").submit();
			});
		}
		
		 //导出文件
		function bindExportEvent(){
			$("#exportBtn").click(function(){
				var titleIds = $("#exportSecondmentA").attr("href");	
				var url = "${ctx }/secondment/exportSecondment.do?isSuperSearch=${isSuperSearch}";
				$("#exportSecondmentA").attr("href",url+"&titles="+titleIds+"&queryStr="+encodeURI(encodeURI('${queryStr }'))+"&postingNumber="+encodeURI(encodeURI('${postingNumber}'))+"&postingTitle="+encodeURI(encodeURI('${postingTitle}'))+"&postingUnit="+encodeURI(encodeURI('${postingUnit}'))+"&temporaryProjectName="+encodeURI(encodeURI('${temporaryProjectName}'))+"&temporaryBeginDayOperator="+encodeURI(encodeURI('${temporaryBeginDayOperator}'))+"&temporaryEndDateOperator="+encodeURI(encodeURI('${temporaryEndDateOperator}'))+"&temporaryBeginDay="+encodeURI(encodeURI('${temporaryBeginDay}'))+"&temporaryEndDate="+encodeURI(encodeURI('${temporaryEndDate}'))+"&temporaryRequirement="+encodeURI(encodeURI('${temporaryRequirement}'))+"&remark="+encodeURI(encodeURI('${remark}')));
				$("#exportSecondment").click();
					
			});
		}
	   //新增挂职信息
        function bindSubmitFormEvent(){
            $(".addBtn").click(function(){
            	art.dialog.open("${ctx}/secondment/preAddSecondment.do", 
                        { title: "新增挂职信息", lock: true,width:996,height:420}
                );
            });
        }
        //修改挂职信息
        function bindUpdateBtnEvent(){
            $(".updateBtn").click(function(){
                art.dialog.open("${ctx}/secondment/preSecondmentUpdate.do?id="+$(this).attr("secondmentId"), 
                        { title: "修改挂职信息", lock: true,width:758,height:420}
                );
            });
        }
        //删除挂职信息
        function bindDelBtnEvent(){
            $(".delBtn").click(function(){
                var thiz = $(this);
                var id = $(this).attr("secondmentId");
                art.dialog.confirm("确定要删除该举报信息吗？<br />此操作不可恢复，请谨慎使用！",function(){
                    $.post("${ctx}/secondment/delSecondments.do",{id:id},function(result){
                        if("success" == result) {
                            thiz.parent().parent().remove();
                        } else {
                            art.dialog.alert("删除举报信息失败!");
                        }
                    });
                });
            });
        }
         //按挂职项目排序
        function binProjectEvent(){
        	    $("#projectBtn").click(function(){
				$("#querySecondmentForm").attr("action","${ctx}/secondment/orderByProject.do");
				$("#querySecondmentForm").submit();
			});
		}
		//批量导入
		 function bindImportSecondmentBtnEvent() {
            $(".importSecondmentBtn").click(function(){
                art.dialog.open("${ctx}/secondment/preImportSecondment.do", 
                        { title: "批量导入挂职信息", lock: true,width:720,height:400});
            });
        }
		//新增挂职人员信息
        function addSecondmentUserEvent(){
            $(".addSecondmentUserBtn").click(function(){
             	var id = $(this).attr("secondmentId");
            	art.dialog.open("${ctx}/secondment/openAddSecondmentUser.do?id="+id, 
                        { title: "新增挂职人员", lock: true,width:996,height:420}
                );
            });
        }
        //删除挂职人员信息
        function delSecondmentUserEvent(){
            $(".delSecondmentUserBtn").click(function(){
             	var id = $(this).attr("secondmentId");
            	art.dialog.open("${ctx}/secondment/openDelSecondmentUser.do?id="+id, 
                        { title: "删除挂职人员", lock: true,width:996,height:420}
                );
            });
        }
        //打开详情
		function bindDetailEvent() {
			$(".detailBtn").click(function(){
			 art.dialog.open("${ctx}/secondment/secondmentDetail.do?id="+$(this).attr("secondmentId"), 
					{ title: "挂职详情", lock: true, width: 996, height: 600 }
				);				
			});
		}
		 //按挂职人员排序
       /* function binProjectEvent(){
        	    $("#projectBtn").click(function(){
				$("#querySecondmentForm").attr("action","${ctx}/secondment/orderByUser.do");
				$("#querySecondmentForm").submit();
			});
		}*/
    </script>
</body>
</html>