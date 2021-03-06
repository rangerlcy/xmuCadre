<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/ny.css" />

		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
	
	</head>
	<body>
	    <div class="tb_700">
	    
			<table cellpadding="0" cellspacing="0" class="basicInfo reportInfo">
				<tr>
					<td><span style="color:red">*</span>被举报人</td>
					<td>
<!-- 						<select name="userId" id="userId"> -->
<!-- 							<option value="">请选择</option> -->
<!-- 						</select> -->
					
						<input id="userName" name="userName" type="text"
						class="txt" style="width:200px;"  maxlength=50/>
						<div id="searchResultOutput" class='absoluteDiv'></div>
						<input type="hidden" value="" name="userId" id="userId"/>
					</td>
				</tr>
				<tr>
					<td ><span style="color:red">*</span>举报人</td>
					<td><input id="informer" name="informer" type="text"
						class="txt" style="width:200px;" maxlength=50/>
					</td>
				</tr>
				
			
			<tr>
				<td class="text-right"><span style="color:red">*</span>举报时间</td>
        		<td class="text-left">
        			<input type="text" name="reportedDay"  id="reportedDay"  class="txt Wdate" onfocus="WdatePicker({readOnly:false})" style="width:200px;">
        			
        		</td>
				</tr>
				
				<tr>
					<td><span style="color:red">*</span>举报途径 </td>
					<td>
					<select id="reportedWay" name="reportedWay">
					        <option value="" selected >请选择</option>
					        <c:forEach items="${ide:getAllItems('reportedWay') }" var="rw">
					        	<option value="${rw.value }" >${rw.value }</option>
					        </c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td><span style="color:red">*</span>举报类型</td>
					<td><select id="reportedType" name="reportedType">
					        <option value="" selected>请选择</option>
							<c:forEach items="${ide:getAllItems('reportedType') }" var="rt">
					        	<option value="${rt.value }">${rt.value }</option>
					        </c:forEach>	
						</select>
					</td>
				</tr>
				<tr>
					<td><span style="color:red">*</span>举报内容</td>
					<td><textarea id="reportedContent" name="reportedContent" rows="6" cols="40"
						></textarea>
					</td>
				</tr>
				<tr>
					<td >处理经过和结论</td>
					<td><input id="processingAndConclusion" name="processingAndConclusion" type="text"
						class="txt" style="width:200px;" maxlength=50/> 
					</td>
				</tr>
				<tr>
					<td >处理结论类型</td>
					<td>
					<select id="processingAndConclusionType" name="processingAndConclusionType">
						 <option value="" selected>请选择</option>
						 <c:forEach items="${ide:getAllItems('processingAndConclusionType') }" var="pt">
					        	<option value="${pt.value }">${pt.value }</option>
					        </c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td >备注</td>
					<td><textarea id="remark" style="width:300px;height:80px;" name="remark" class="txt" cols="40" rows="3" maxlength=2000></textarea>
					</td>
				</tr>

			</table>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="确定" class="add" id="saveBtn" />
			<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
		</div>
	    <script type="text/javascript">
	        $(function(){
	        	$("#identifyNum").focus();
	        	bindSaveEvent();//绑定提交表单事件
	        	bindSearchUserEvent();
	        	$("#userName").focus(function(){
	        		$("#userName").keyup();
	        	});
	       		
	        });
	        
	        var userTemp ='';
	        function bindSearchUserEvent(){
	        	$("#userName").keyup(function(){
	        		var thiz = $(this);
	        		var user = thiz.val();
	        		if (user == '') {
	        			$("#searchResultOutput").html('');
	        			return;
	        		}
	        		userTemp = user;
	        		$.post("${ctx}/infoManager/userInfo/existUserName.do",{userName:user },function(result){
	        			if(null == result || 0 == result.length){
	        				thiz.parent().find("label").html("被举报人不存在");
	        				$("#selectName").remove();
	        			}
	        			 	
	        			else if (result.length>=1){
	        				var htmlStr="<ul id='selectName'>";
	        				for (var i =0;i<result.length;i++){
								htmlStr += "<li><a href='javascript:showUser("+result[i].id+")'>" + result[i].name + "</a><button onclick=\"chooseUser("+result[i].id+",'"+result[i].name+"')\">选择</button></li>";        				
	        				}
	        				htmlStr += "</ul>";
	        				thiz.parent().find("#searchResultOutput").html(htmlStr);
	        				$("#userName").bind("blur.hide",function(){
								$("#searchResultOutput").html('');
							});
							$("#selectName").hover(function(){//进入失去函数绑定
								$("#userName").unbind("blur.hide");
							},function(){//出去获取函数绑定
								$("#userName").bind("blur.hide",function(){
									$("#searchResultOutput").html('');
								});
							});
	        			}
	        		},"json");
	        	});
	        	
	        }
	        function showUser(id){
	        	//用户详情
				art.dialog.open(
					"${ctx}/infoManager/userInfo/openUserDetail.do?id="+id,
					{ title: "用户详情", lock: true, width: 996, height: 600 }
				);				
		
		
	        }
	        function chooseUser(id,name){
	      	  	var thiz = $("#userId");
	        	thiz.parent().find("#searchResultOutput").html("");
	        	$("#userName").val(name);
	        	$("#userId").val(id);	
	        }
	        function bindSaveEvent(){
				$("#saveBtn").click(function(){
					var canSubmit = true;
	                if($.trim($("#userName").val())==""){
	                    art.dialog.alert("请输入被举报人");
	                    return;
					}
	                if($.trim($("#informer").val())==""){
	                    art.dialog.alert("请输入举报人");
	                    return;
	                }
	                if($.trim($("#reportedDay").val())==""){
	                    art.dialog.alert("请输入举报时间");
	                    return;
	                }
	                 if($.trim($("#reportedWay").val())==""){
	                    art.dialog.alert("请输入举报途径");
	                    return;
	                }
	                 if($.trim($("#reportedType").val())==""){
	                    art.dialog.alert("请输入举报类型");
	                    return;
	                }
	                   if($.trim($("#reportedContent").val())==""){
	                    art.dialog.alert("请输入举报内容");
	                    return;
	                }
	                if(!canSubmit){
						art.dialog.alert("该用户不存在");
	                    return;
					}
					if(canSubmit){
		                $.post("${ctx}/report/addReports.do",{
		                		userId:$.trim($("#userId").val()),
								informer:$.trim($("#informer").val()),
								reportedDay:$.trim($("#reportedDay").val()),
								reportedWay:$.trim($("#reportedWay").val()),	
								reportedType:$.trim($("#reportedType").val()),	
								reportedContent:$.trim($("#reportedContent").val()),	
								processingAndConclusion:$.trim($("#processingAndConclusion").val()),	
								processingAndConclusionType:$.trim($("#processingAndConclusionType").val()),	
								remark:$.trim($("#remark").val())
							},function(result){
				                if("success" == result) {
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();               
				                } else if( "fail"==result){
				       
				                    art.dialog.tips("保存失败!查无此员工");
				                }
			        	});					
					}
				});
			}
			
	    </script>
	</body>
</html>