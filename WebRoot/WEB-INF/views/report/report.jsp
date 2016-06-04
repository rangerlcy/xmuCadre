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
   			<div class="form_head"><span>举报信息</span></div> 
   			<c:if test="${roleType=='admin' }">
   			<input type="button" class="btn addBtn" value="添加举报信息" style="margin-left:10px;"/>
   			</c:if>
			<input type="button" class="btn" id="exportBtn" value="下载导出表" style="margin-left:10px;"/>
		    <input type="button" class="btn superSearchBtn" value="高级查询" style="margin-left:10px;"/> 
		    
			<form id="queryReportForm" action="${ctx }/report/report.do" method="post">
				<input type="text" id="queryStr" class="txt txtkey" name="queryStr" value="${queryStr}"/>
			    <input type="text" id="queryStrTemp" class="txt txtkey" value="除备注外的所有信息"/>
			    <input id="searchBtn" type="submit" class="btn searchBtn" value="查询" style="cursor: pointer;margin-right:0px;"/>
			<div class="AdvantageSearch" style="display:none;">     
				被举报人：<input type="text" id="SuperSearchUser" name="user"/>
				举报人：<input type="text" id="SuperSearchInformer" name="Informer"/><br/>
				举报时间：
					<select id="SuperreportedDaySelect" name="reportedDayOperator">
						<option value="R">晚于</option>
						<option value="E" selected>等于</option>
						<option value="L">早于</option>
					</select>
					<input type="text" id="SuperSearchreportedDay" name="reportedDay" class="txt Wdate " onfocus="WdatePicker({readOnly:false})"/>
					
			          举报途径：
			          <select id="reportedWay" name="reportedWay">
					        <option value="" selected >请选择</option>
					        <c:forEach items="${ide:getAllItems('reportedWay') }" var="rw">
					        	<option value="${rw.value }" >${rw.value }</option>
					        </c:forEach>
					</select>
			          举报类型：
			          <select id="reportedType" name="reportedType">
					        <option value="" selected>请选择</option>
							<c:forEach items="${ide:getAllItems('reportedType') }" var="rt">
					        	<option value="${rt.value }">${rt.value }</option>
					        </c:forEach>	
						</select>
			    
			         举报内容：<input type="text" id="SuperSearchreportedContent" name="reportedContent"/>
			           处理经过和结论：<input type="text" id="SuperSearchprocessingAndConclusion" name="processingAndConclusion"/>
			           处理结论类型：
			           <select id="processingAndConclusionType" name="processingAndConclusionType">
							<option value="" >请选择</option>
							<option value="属实" >属实</option>
							<option value="误报" >误报</option>
							<option value="诬陷" >诬陷</option>
						</select>
				
				<input type="button" id="SuperSearchBtn" value="查询"/>
			</div>
		    </form>
		</div>
		
	    <table cellpadding="0" cellspacing="0" class="tblist">
	        <tr>
	            <th>被举报人</th>
	            <th>举报人</th>
	            <th>举报时间</th>
	            <th>举报途径</th>
	            <th>举报类型</th>
	            <th>举报内容</th>
	            <th>处理经过和结论</th>
	            <th>处理结论类型</th>
	            <th>备注</th>
	            <th class="c">操作</th>
	        </tr>
	        <c:if test="${queryList.result != null }">
	          <c:forEach items="${queryList.result }" var="report">
	              <tr>
	                <td id="report_user_${report.id }">${report.user.name }</td>
	                <td id="report_informer_${report.id }">${report.informer }</td>
	                <td id="report_reportedTime_${report.id }"><fmt:formatDate value='${report.reportedDay }'  pattern='yyyy-MM-dd'/></td>
	                <td id="report_reportedWay_${report.id }">${report.reportedWay }</td>
	                <td id="report_reportedType_${report.id }">${report.reportedType }</td>
	                <td id="report_reportedContent_${report.id }">${report.reportedContent }</td>
	                <td id="report_processingAndConclusion_${report.id }">${report.processingAndConclusion }</td>
	                <td id="report_processingAndConclusionType_${report.id }">${report.processingAndConclusionType }</td>
	                <td id="report_remark_${report.id }">${report.remark }</td>
	                <td>
	                	<c:if test="${roleType=='admin' }">
                    	<input type="button" class="btn btn_cz updateBtn" value="修改" reportId="${report.id }">
	                    <input type="button" class="btn btn_cz delBtn" value="删除" reportId="${report.id }">
	                    <input type="button" class="btn btn_cz afxBtn" value="附件" reportId="${report.id}">
	             		</c:if>
	                </td>
	            </tr>
	          </c:forEach>
	        </c:if>
	    </table>
	    <ide:page page="${queryList }" pageId="reportPage" searchForm="queryReportForm"></ide:page>
	    <a style="display:none;" id="exportReportA" href="${ctx }/report/exportReport.do?isNew=false"><span id="exportReport">导出</span></a>

    <script type="text/javascript">
        $(function(){
            bindSubmitFormEvent();//绑定提交表单事件
            bindUpdateBtnEvent(); //绑定修改按钮事件
            bindDelBtnEvent(); //绑定删除按钮事件
            bindQueryTextEvent();//绑定查询输入框
            bindSearchEvent();//绑定搜索框
            bindSuperSearchBtn();//绑定高级搜索按钮事件
   			bindExportEvent();//绑定导出按钮事件
        	bindSuperSearchEvent(); 
        	bindAffixEvent();//绑定附件按钮事件     
        });
        function bindSearchEvent(){
			$("#searchBtn").click(function(){
                $("#queryReportForm").submit();
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
	    //导出文件
		function bindExportEvent(){
			$("#exportBtn").click(function(){
				var titleIds = $("#exportReportA").attr("href");	
				var url = "${ctx }/report/exportReport.do?isSuperSearch=${isSuperSearch}";

				$("#exportReportA").attr("href",url+"&titles="+titleIds+"&queryStr="+encodeURI(encodeURI('${queryStr }'))+"&user="+encodeURI(encodeURI('${user}'))+"&informer="+encodeURI(encodeURI('${informer}'))+"&reportedDayOperator="+encodeURI(encodeURI('${reportedDayOperator}'))+"&reportedDay="+encodeURI(encodeURI('${reportedDay}'))+"&reportedWay="+encodeURI(encodeURI('${reportedWay}'))+"&reportedType="+encodeURI(encodeURI('${reportedType}'))+"&reportedContent="+encodeURI(encodeURI('${reportedContent}'))+"&processingAndConclusion="+encodeURI(encodeURI('${processingAndConclusion}'))+"&processingAndConclusionType="+encodeURI(encodeURI('${processingAndConclusionType}'))+"&remark="+encodeURI(encodeURI('${remark}')));
				//alert(url+"&titles="+titleIds+"&queryStr="+encodeURI(encodeURI('${queryStr }'))+"&user="+encodeURI(encodeURI('${user}'))+"&informer="+encodeURI(encodeURI('${informer}'))+"&reportedDayOperator="+encodeURI(encodeURI('${reportedDayOperator}'))+"&reportedDay="+encodeURI(encodeURI('${reportedDay}'))+"&reportedWay="+encodeURI(encodeURI('${reportedWay}'))+"&reportedType="+encodeURI(encodeURI('${reportedType}'))+"&reportedContent="+encodeURI(encodeURI('${reportedContent}'))+"&processingAndConclusion="+encodeURI(encodeURI('${processingAndConclusion}'))+"&processingAndConclusionType="+encodeURI(encodeURI('${processingAndConclusionType}'))+"&remark="+encodeURI(encodeURI('${remark}')));
				$("#exportReport").click();
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
				$("#queryReportForm").attr("action","${ctx }/report/SuperSearchReport.do");
				//表单验证  万超 要做下可以空 但是有值的话必须要符合 比如数字 
				$("#queryReportForm").submit();
			});
		}
		//新增举报信息
        function bindSubmitFormEvent(){
            $(".addBtn").click(function(){
            	art.dialog.open("${ctx}/report/preAddReport.do", 
                        { title: "新增举报信息", lock: true,width:758,height:450}
                );
            });
        }
        //修改举报信息
        function bindUpdateBtnEvent(){
            $(".updateBtn").click(function(){
                art.dialog.open("${ctx}/report/preReportUpdate.do?id="+$(this).attr("reportId"), 
                        { title: "修改举报信息", lock: true,width:758,height:450}
                );
            });
        }
        //删除举报信息
        function bindDelBtnEvent(){
            $(".delBtn").click(function(){
                var thiz = $(this);
                var id = $(this).attr("reportId");
                art.dialog.confirm("确定要删除该举报信息吗？<br />此操作不可恢复，请谨慎使用！",function(){
                    $.post("${ctx}/report/delReports.do",{id:id},function(result){
                        if("success" == result) {
                            thiz.parent().parent().remove();
                        } else {
                            art.dialog.alert("删除举报信息失败!");
                        }
                    });
                });
            });
        }
        //上传下载附件信息
        function bindAffixEvent(){
            $(".afxBtn").click(function(){
                art.dialog.open("${ctx}/report/afxReports.do?id="+$(this).attr("reportId"), 
                        {title: "附件信息", lock: true,width:758,height:450}
                );
            });
        }
    </script>
</body>
</html>