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
	    <div class="tb_940">
	    <form id="Addform" action="" onsubmit="return false;">
			<table cellpadding="0" cellspacing="0" class="basicInfo">
				<tr>
					<td style="width: 60px;"><span style="color:red">*</span>发文文号</td>
					<td colspan="6"><input id="paperNumber" name="paperNumber" type="text"
						class="txt" maxlength=50/>
					</td>
				</tr>
				<tr>
					<td ><span style="color:red">*</span>文件题目</td>
					<td colspan="6"><input id="paperName" name="paperName" type="text"
						class="txt"  maxlength=50/>
					</td>
				</tr>
				<tr>
					<td><span style="color:red">*</span>发文单位</td>
					<td colspan="6"><input id="paperUnits" name="paperUnits" type="text"
						class="txt" maxlength=50/>
					</td>
				</tr>
				<tr>
					<td>添加</td>
					<td colspan="6"><input type="button" value="添加" onclick="addSec();"/></td>
				</tr>
				<tr id="sec" style="background:#22B2AF;"><td>序号</td><td><span style="color:red">*</span>挂职项目名称：</td><td ><span style="color:red">*</span>挂职人员要求：</td><td class="text-right">挂职开始时间</td><td class="text-right">挂职结束时间</td><td>备注：</td><td>操作</td></tr>
				<tr class="addDemo" style="display:none;">
					<td class="secList"></td>
					<td class="temporaryProjectName">
						
					</td>
					<td class = "temporaryRequirement">
					
					</td>
						
					<td class = "remark">
						
					</td>
				</tr>
			</table>
            </form>
			
			<div id="addInfo" style="display:none;"></div>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="确定" class="add" id="saveBtn" />
			<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
		</div>
	
	    <script type="text/javascript">
	        $(function(){
	        	$("#paperNumber").focus();
	        	bindSaveEvent();//绑定提交表单事件
	        });
	        //新增挂职信息			
	        function bindSaveEvent(){
				$("#saveBtn").click(function(){
				var submit = true;
				var canUse = true;
				var paperNumber = $.trim($("#paperNumber").val());
					if($.trim($("#paperNumber").val())==""){
	                   art.dialog.alert("请输入发文文号");
	                   return;
	           		}
	           		if($.trim($("#paperName").val())==""){
	                   art.dialog.alert("请输入发文题目");
	                   return;
	           		}
	           		if($.trim($("#paperUnits").val())==""){
	                   art.dialog.alert("请输入发文单位");
	                   return;
	           		}
	           		if($.trim($("#temporaryProjectName").val())==""){
	                   art.dialog.alert("请输入挂职项目名称");
	                   return;
	           		}
	                if($.trim($("#temporaryRequirement").val())==""){
	                    art.dialog.alert("请输入挂职要求");
	                    return;
	                }
	                if(paperNumber != ''){
					$.ajax({
				      url: "${ctx}/secondment/checkPaperNumber.do",
				      data:{paperNumber:paperNumber},
				      type: 'POST',
				      async:false,
				      success: function(result){
		                if("success" != result){
		                	art.dialog.tips("发文文号冲突");
		                	canUse = false;
		                }
			       	  }
			       });
				}
				if(!canUse){
					$("#paperNumberTs").html("号码已存在");
					return;
				}
	                var data = $("#Addform").serialize();
	                if(canUse){
						$.post("${ctx}/secondment/addSecondments.do", data,
								function(result) {
									 if("success" == result) {
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();   }            
									else if( "fail"==result){
				       
				                    art.dialog.tips("保存失败!");
				                }
								});
								}
					});
		     }
	                
			function addSec()
			{
				var num=parseInt($("#sec").parent().find(".secList").length);
				var tr = '<tr class="secList">';
					tr +=	'<td><label class="addSec">'+num+'</label></td>';
					tr +=	'<td><input id="temporaryProjectName" name="secondmentPaperItems['+num+'].temporaryProjectName" type="text" class="txt"  maxlength=50/></td>';				
					tr +=	'<td><input id="temporaryRequirement" name="secondmentPaperItems['+num+'].temporaryRequirement" type="text" class="txt"  maxlength=120/></td>';				
					tr +=	'<td class="text-left">';
					tr +=		'<input type="text" name="secondmentPaperItems['+num+'].temporaryBeginDay" id="temporaryBeginDay" class="txt Wdate " onfocus="WdatePicker({readOnly:false})"></td>';				
					tr +=	'<td class="text-left"> <input type="text" name="secondmentPaperItems['+num+'].temporaryEndDate" id="temporaryEndDate" class="txt Wdate " onfocus="WdatePicker({readOnly:false})"></td>';
					tr +=	'<td><textarea id="remark" name="secondmentPaperItems['+num+'].remark" class="txt" cols="40" rows="3" maxlength=500></textarea></td>';
					tr +=	'<td><input type="button" value="删除" onclick="delSec(this);"/></td></tr>';
				$("#sec").after(tr);
			}
			function delSec(thiz)
			{
				var num=parseInt($(thiz).parent().parent().find(".addSec").html());
				alert(num);
				$(thiz).parent().parent().remove();
				var numT;
				$(".secList").each(function(){
					numT=parseInt($(this).find(".addSec").html());
					if(numT>num){
						$(this).find(".addSec").html(numT-1);
						var change = 'input[name^="paperSecondmentItem"]';
						$(this).find(change).each(function(){
			    			var tail = $(this).attr("name").split("]")[1];
			    			var head = $(this).attr("name").split("[")[0];
			    			var value = head+'['+(numT-2)+']'+tail;
			    			$(this).attr("name",value);
			    			});
					}
				});
			}
	    </script>
	</body>
</html>