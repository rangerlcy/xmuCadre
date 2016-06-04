<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide"%>
<script type="text/javascript"
	src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
<title>厦门大学干部信息管理系统</title>
<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${res }/css/ny.css" type="text/css" />
</head>
<body>
	<div class="displayNone" id="acadmyRSC_2">
		<select name="acadmy" id="acadmy" onchange="aSelect(this,'acadmy','s');AcadmySelectChange(this)">
			<option value="" selected>请选择</option>
			<c:forEach items="${organizations }" var="org">
				<c:if test="${org.parentCode == '01' }">
					<option value="${org.code }">${org.name }</option>
				</c:if>
			</c:forEach>
		</select>
	</div>
	<div class="displayNone" id="acadmyRSC_1">
		<input type="text" onblur="AcadmyBlur(this);"
			onfocus="showLi(this)" /> 
		
		<div class='absoluteParent'><ul class='absoluteChild'>
			<c:forEach items="${organizations }" var="org">
				<c:if test="${org.parentCode == '01' }">
					<li value="${org.code }">${org.name }</li>
				</c:if>
			</c:forEach>
		</ul></div>
		
	</div>
	<div class="displayNone" id="positionLevelRSC">
		<select onchange="aSelect(this,'positionLevel','s')">
			<c:forEach
				items="${ide:getAllPositionLevel() }" var="ps">
				<option value="${ps.key }">${ps.value }</option>
			</c:forEach>
		</select>
	</div>
	<div class="displayNone" id="positionTypeRSC">
		<select onchange="aSelect(this,'positionType','s')">
			<c:forEach
				items="${ide:getAllPositionType() }" var="ps">
				<option value="${ps.key }">${ps.value }</option>
			</c:forEach>
		</select>
	</div>
	<div class="displayNone" id="actionDayRSC">
		<input type="text" class="Wdate" value=""
		onfocus="WdatePicker({readOnly:false})"
		onblur="aSelect(this,'actionDay','i')">
	</div>
	<div class="tb_940">
		<div id="Addform">
			<table cellpadding="0" cellspacing="0" class="basicInfo">
				<tr>
					<td style="font-size:18px;">发文名称</td>
					<td>
						<input type="text" onblur="text(this,'paperName');"/>
					</td>
					<td style="font-size:18px;">发文文号</td>
					<td>
						<input type="text" onblur="text(this,'paperNumber');"/>
					</td>
				</tr>
				<tr>
					<td style="font-size:18px;">发文时间</td>
					<td><input type="text" class="Wdate" value=""
						onfocus="WdatePicker({readOnly:false})"
						 onblur="text(this,'paperDay');"></td>
					<td style="font-size:18px;">文件内容</td>
					<td>
						<input type="button" class="btn" id="fileBtn" value="选择文件"/>
						<label></label>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" class="basicInfo">
				<tr>
					<td colspan="9">增、撤</td>
				</tr>
				<tr id="addAndRemove" style="background:#22B2AF;">
					<td style="width:40px;">序号</td>
					<td style="width:70px;">变动类型</td>
					<td style="width:140px;">院/单位</td>
					<td style="width:140px;">系所/科室</td>
					<td style="width:80px;">岗位</td>
					<td style="width:120px;">级别</td>
					<td style="width:150px;">类型</td>
					<td style="width:120px;">变动时间</td>
					<td><input type="button" value="增加" 	onclick="addAnR();" /></td>
				</tr>
				<tr class="addDemo" style="display:none;">
					<td class="AddnRem">numT</td>
					<td class="type">
						<select
						onchange="aSelect(this,'actionType','s');ActionTypeChange(this)">
							<option value="">请选择</option>
							<option value="1">增加</option>
							<option value="2">撤销</option>
						</select> 
						<input type="hidden" value="indexNumT" class="indexNum">
					</td>
					<td class="acadmy">
						
					</td>
					<td class = "department">
						
					</td>
					<td class = "positionName">
						
					</td>
					<td class = "positionLevel">
						
					</td>
					<td class = "positionType">
						
					</td>
					<td class = "actionDay">
						<input type="text" class="Wdate" value=""
						onfocus="WdatePicker({readOnly:false})"
						onblur="aSelect(this,'actionDay','i')">
					</td>
					<td>
						<input type="button" value="撤销" onclick="delAnR(this);">
					</td>
				</tr>
				</table>
				<table cellpadding="0" cellspacing="0" class="basicInfo">
				<tr>
					<td colspan="9">变动</td>
				</tr>
				<tr id="change" style="background:#528fd2;">
					<td style="width:40px;">序号</td>
					<td style="width:70px;">类型</td>
					<td style="width:215px;">院/单位</td>
					<td style="width:140px;">系所/科室</td>
					<td style="width:80px;">岗位</td>
					<td style="width:120px;">级别</td>
					<td style="width:100px;">类型</td>
					<td style="width:120px;">变动时间</td>
					<td><input type="button" value="增加" onclick="addChange();" /></td>
				</tr>
				<tr class="changeDemoT" style="display:none;">
					<td rowspan="2" class="Change">numT</td>
					<td class="type">
						原<input type="hidden" value="indexNumT"
						class="indexNum">
					</td>
					<td class="acadmy">
						<select name="acadmy" id="acadmy" onchange="aSelect(this,'acadmy','s');AcadmyOriginalSelectChange(this)">
							<option value="" selected>请选择</option>
							<c:forEach items="${organizations }" var="org">
								<c:if test="${org.parentCode == '01' }">
									<option value="${org.code }">${org.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td class="department">
						
					</td>
					<td class="positionName">
						
					</td>
					<td class="positionLevel">
						
					</td>
					
					<td class="positionType">
						
					</td>
					<td rowspan="2" class="positionDay">
						<input type="text" class="Wdate" value=""
						onfocus="WdatePicker({readOnly:false})"
						onblur="aSelect(this,'actionDay','i');positionDayOriginalChange(this);">
					</td>
					<td rowspan="2"><input type="button" value="撤销"
						onclick="delChange(this);">
					</td>
				</tr>
				<tr class="changeDemoB" style="display:none;">
					<td class="type">现<input type="hidden" value="indexNumB"
						class="indexNum">
					</td>
					<td class="acadmy">
						
					</td>
					<td class="department">
						
					</td>
					<td class="positionName">
						
					</td>
					<td class="positionLevel">
						
					</td>
					<td class="positionType">
						
					</td>
			</table>
			<form id="addInfo" method="post" action="${ctx}/position/positionAdd.do" enctype="multipart/form-data" style="display:none;">
				<input type="hidden" name="paperName"/>
				<input type="hidden" name="paperNumber"/>
				<input type="hidden" name="paperDay"/>
				<input type="file" name="file" style="display:none"/>
			</form>
			<div class="tb_sub_btns">
				<input type="button" value="确认" class="addBtn" />
				<input type="button" value="返回" onclick="art.dialog.close()"/>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		
		function bindAdBtnEvent() {
			$(".addBtn").click(
					function() {
						if ($.trim($("#addInfo").find("input[name=paperName]").val()) == "") {
							art.dialog.alert("请输入发文文号");
							return;
						}
						if ($.trim($("#addInfo").find("input[name=paperNumber]").val()) == "") {
							art.dialog.alert("请输入发文文名");
							return;
						}

					$("#addInfo").submit();
					}
					);
						
		}
		function bindFileBtnEvent(){
			$(":file").change(function(){
				$("#fileBtn").next().html($(this).val());
			});
			$("#fileBtn").click(function(){
				$("#addInfo").find(":file").click();
			});
		}
		$(function() {
			
			bindAdBtnEvent();
			bindFileBtnEvent();
			
		});
		//改变发文
		function text(thiz,name){
			var input = 'input[name="'+name+'"]';
			$("#addInfo").find(input).val($(thiz).val());
		}
		/* 添加增刪信息*/
		function addAnR() {
			var num = $("#addAndRemove").nextAll().find(".AddnRem").length;
			var indexNum = $("#addInfo").find("input[name$=index]").length;
			var tr = '<tr>' + $(".addDemo").html() + '</tr>';
			tr = tr.replace(/numT/, num);
			tr = tr.replace(/indexNumT/, indexNum);
			$("#addAndRemove").after(tr);
			var div = '<input type="hidden" name="positionPaperItems['+indexNum+'].index" value="'+indexNum+'"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].actionType" value="1"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].acadmy"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].department"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionLevel"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionName"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionType"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].actionDay"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionId"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].isDelWithUser"/>';
			$("#addInfo").prepend(div);
		}
		/* 改變隱藏Input Value*/
		function aSelect(thiz, name, type) {
			var num = $(thiz).parent().parent().find(".indexNum").val();
			var input = 'input[name="positionPaperItems[' + num + '].' + name
					+ '"]';
			var value="";
			if (type == "i")
				value = $(thiz).val();
			else if (type == "s")
				value = $(thiz).val();
			else if (type == "li"){
				num = $(thiz).parent().parent().parent().parent().find(".indexNum").val();
				input = 'input[name="positionPaperItems[' + num + '].' + name
					+ '"]';
				value = $(thiz).attr("value");
			}
			$("#addInfo").find(input).val(value);
		}
		//获取隐藏Input value
		function getSelect(thiz, name){
			var num = $(thiz).parent().parent().find(".indexNum").val();
			var input = 'input[name="positionPaperItems[' + num + '].' + name+ '"]';
			return $("#addInfo").find(input).val();
		}
		function aOriginalSelect(thiz,name,value){
			var num = $(thiz).parent().parent().find(".indexNum").val();
			var input = 'input[name="positionPaperItems[' + num + '].' + name
					+ '"]';
			$("#addInfo").find(input).val(value);
		}
		/* 刪除增刪信息*/
		function delAnR(thiz) {
			var num = parseInt($(thiz).parent().parent().find(".AddnRem")
					.html());
			var removeIndex = parseInt($(thiz).parent().parent().find(
					".indexNum").val());
			var numT='';
			$(thiz).parent().parent().remove();
			$(".AddnRem").each(function() { //table 显示序号重置
				numT = parseInt($(this).html());
				if (numT > num)
					$(this).html(numT - 1);
			});
			removeInput(removeIndex); //div 隐藏序号重置
			$(".indexNum").each(function() { //table 隐藏序号重置
				var numT = parseInt($(this).val());
				if (numT > removeIndex) {
					$(this).val(numT - 1);
					aSelect(this, "index", "i");
				}
			});
		}
		
		/* 刪除隱藏的Input值*/
		function removeInput(num) {
			var input = 'input[name^="positionPaperItems\[' + num + '\]"]';
			$(input).each(function() {
				$(this).remove();
			}); //删除隐藏input
			var change = 'input[name^="positionPaperItems"]';
			$(change).each(
					function() {
						var tail = $(this).attr("name").split("]")[1];
						var head = $(this).attr("name").split("[")[0];
						var value = parseInt($(this).attr("name").split("]")[0]
								.split("[")[1]);
						if (value > num) {
							value--;
							value = head + '[' + value + ']' + tail;
							$(this).attr("name", value);
						}
						;
					});
		}
		/* 添加變動信息*/
		function addChange() {
			var num = $("#change").nextAll().find(".Change").length;
			var indexNum = $("#addInfo").find("input[name$=index]").length;
			var tr = '<tr>' + $(".changeDemoT").html() + '</tr><tr>'
					+ $(".changeDemoB").html() + '</tr>';
			tr = tr.replace(/numT/, num);
			tr = tr.replace(/indexNumT/, indexNum);
			tr = tr.replace(/indexNumB/, indexNum + 1);
			var div = '<input type="hidden" name="positionPaperItems['+indexNum+'].index" value="'+indexNum+'"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].actionType" value="3"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].acadmy"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].department"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionLevel"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionName"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionType"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].actionDay"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionId"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].isDelWithUser"/>';
			indexNum++;
			$("#change").after(tr);
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].index" value="'+(indexNum-1)+'"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].actionType" value="4"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].acadmy"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].department"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionLevel"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionName"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionType"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].actionDay"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].positionId"/>';
			div += '<input type="hidden" name="positionPaperItems['+indexNum+'].isDelWithUser"/>';
			$("#addInfo").prepend(div);
		}
		
		/* 刪除變動信息*/
		function delChange(thiz) {
			var num = parseInt($(thiz).parent().parent().find(".Change").html());
			var removeIndex = parseInt($(thiz).parent().parent().find(
					".indexNum").val());
			var numT="";
			$(thiz).parent().parent().next().remove();
			$(thiz).parent().parent().remove();
			$(".Change").each(function() { //table 显示序号重置
				numT = parseInt($(this).html());
				if (numT > num)
					$(this).html(numT - 1);
			});
			removeInput(removeIndex); //div 隐藏序号重置
			removeInput(removeIndex);
			$(".indexNum").each(function() { //table 隐藏序号重置
				var numT = parseInt($(this).val());
				if (numT > removeIndex) {
					$(this).val(numT - 2);
					aSelect(this, "index", "i");
				}
			});
		}
		//显示选择列表
		function showLi(thiz){
			$(".absoluteChild").each(function(){$(this).css("display","none");});
			$(thiz).parent().find(".absoluteChild").css("display","block");
			$(thiz).bind("blur.hide",function(){hideLi(this);});//先加入函数绑定
			$(thiz).parent().find(".absoluteChild").hover(function(){//进入失去函数绑定
				$(thiz).unbind("blur.hide");
			},function(){//出去获取函数绑定
				$(thiz).bind("blur.hide",function(){hideLi(this);});
			});
		}
		function hideLi(obj){
			var thiz = $(obj);
			thiz.parent().find(".absoluteChild").css("display","none");
		}
		/* 变动类型变化事件*/
		function ActionTypeChange(obj){
			var type = $(obj).val();
			if (type == ""){
				setNull(obj,"acadmy");
				setNull(obj,"department");
				setNull(obj,"positionName");
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
			}
			else if (1 == type){//增岗
				var acadmyRSC = $("#acadmyRSC_1").html();
				var acadmy = $(obj).parent().parent().find(".acadmy");
				acadmy.html(acadmyRSC);
				$(obj).parent().parent().find(".acadmy li").click(function(){
					var lastValue = $(this).parent().parent().parent().find("input");
					if(lastValue.val() != $(this).text()) AcadmyInputChange(obj);
					$(this).parent().parent().parent().find("input").val($(this).text());
					$(this).parent().css("display","none");
					aSelect(this,'acadmy','li');
					AcadmyLiChange(this);
				});
				setNull(obj,"department");
				setNull(obj,"positionName");
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
			}else{//撤岗
				var acadmyRSC = $("#acadmyRSC_2").html();
				var acadmy = $(obj).parent().parent().find(".acadmy");
				acadmy.html(acadmyRSC);
				//系级单位变成选择项
				var departmentRSC_1 = $('<select name="" onchange="aSelect(this,\'department\',\'s\');departmentSelectChange()"><option>请选择</option></select>');
				$(obj).parent().parent().find(".department").html(departmentRSC_1);
				//岗位变成选择项
				var positionNameRSC_1 = $('<select name="" onchange="aSelect(this,\'positionName\',\'s\');positionSelectChange()"><option>请选择</option></select>');
				$(obj).parent().parent().find(".positionName").html(positionNameRSC_1);
				
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");

			}	
		}
		function AcadmyBlur(obj){
			if($(obj).val() == '') AcadmyInputChange(obj);
			aSelect(obj,'acadmy','i');	
//			hideLi(obj);
		}
		function DepartmentBlur(obj){
			if($(obj).val() == '' ) DepartmentInputChange(obj);
			aSelect(obj,'department','i');	
	//		hideLi(obj);
		}
		/* 院级单位选择改变触发事件*/
		function AcadmySelectChange(obj){
			var thiz = $(obj);	
			var html = "<select onchange=\"aSelect(this,'department','s');DepartmentSelectChange(this)\">" +
						"<option value='' selected>请选择</option>";
			if (thiz.val() == "") {
				$("#department").html(html);
				return;
			}
			$.post("${ctx}/sys/organization/queryChildByCode.do", {
				code : thiz.val()
			}, function(result) {
				var org;
				for ( var i = 0; i < result.length; i++) {
					org = result[i];
					html += "<option value='"+org.code+"'>"
							+ org.name + "</option>";
				}
				html += "</select>";
				thiz.parent().parent().find(".department").html(html);
			}, "json");
			
		}
		/* 院级单位输入改变触发事件*/
		function AcadmyInputChange(obj){
			setNull(obj,"department");
			setNull(obj,"positionName");
			setNull(obj,"positionLevel");
			setNull(obj,"positionType");

		}
		/* 院级单位选择Li改变触发事件*/
		function AcadmyLiChange(obj){
			var thiz = $(obj);
			var html = "<input type='text' onblur=\"DepartmentBlur(this)\" onfocus='showLi(this)'/>"+
						"<div class='absoluteParent'><ul class='absoluteChild'>";
			if (thiz.val() == "") {
				setNull(obj,"department");
				return;
			}
			$.post("${ctx}/sys/organization/queryChildByCode.do", {
				code : thiz.attr("value")
			}, function(result) {
				var org;
				for ( var i = 0; i < result.length; i++) {
					org = result[i];
					html += "<li value='"+org.code+"'>"
							+ org.name + "</li>";
				}
				html += "</ul></div>";
				thiz.parent().parent().parent().parent().find(".department").html(html);
				thiz.parent().parent().parent().parent().find(".department li").click(function(){
					$(this).parent().parent().parent().find("input").val($(this).text());
					$(this).parent().css("display","none");
					aSelect(this,'department','li');
					DepartmentLiChange(this);
				});
			}, "json");
			//添加选中事件
			
			
			setNull(obj,"positionName");
			setNull(obj,"positionLevel");
			setNull(obj,"positionType");


		}
		/* 系级单位输入改变事件*/
		function DepartmentInputChange(obj){
			
			setNull(obj,"positionName");
			setNull(obj,"positionLevel");
			setNull(obj,"positionType");
		}
		/* 系级单位改变事件*/
		function DepartmentSelectChange(obj){
			thiz = $(obj);
			var html = "<select onchange=\"aSelect(this,'positionId','s');PositionNameSelectChange(this)\">"
					 + "<option value='' selected>请选择</option>";
			if (thiz.val() == '') {
				setNull(obj,"positionName");
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
				return;
			}
			$.post("${ctx}/position/queryPositionByDept.do", {
				code : thiz.val()
			}, function(result) {
				var position;
				for ( var i = 0; i < result.length; i++) {
					position = result[i];
					html += "<option value='"+position.id+"'>"
							+ position.positionName + "</option>";
				}
				html += "</select>";
				thiz.parent().parent().find(".positionName").html(html);
			}, "json");
		}
		/* 系级单位li改变事件*/
		function DepartmentLiChange(obj){
		var thiz = $(obj);
			var html = "<input type='text' onblur=\"aSelect(this,'positionName','i')\" />";
			if (thiz.val() == "") {
				setNull(obj,"positionName");
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
				return;
			}
			var positionLevelRSC = $("#positionLevelRSC").html();
			var positionTypeRSC = $("#positionTypeRSC").html();
			var actionDayRSC = $("#actionDayRSC").html();
			thiz.parent().parent().parent().parent().find('.positionLevel').html(positionLevelRSC);
			aSelect(thiz.parent().parent().parent().parent().find('.positionLevel').find("select"),"positionLevel","s");
			thiz.parent().parent().parent().parent().find('.positionType').html(positionTypeRSC);
			aSelect(thiz.parent().parent().parent().parent().find('.positionType').find("select"),"positionType","s");
			thiz.parent().parent().parent().parent().find('.actionDay').html(actionDayRSC);
			thiz.parent().parent().parent().parent().find(".positionName").html(html);
		
		}
		/* 岗位选择事件*/
		function PositionNameSelectChange(obj){
			thiz = $(obj);		
			if (thiz.val() == "") {
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
				return;
			}
			$.post("${ctx}/position/queryPositionById.do", {
				code : thiz.val()
			}, function(result) {
				var position = result;
				thiz.parent().parent().find('.positionLevel').html(position.positionLevelName);
				thiz.parent().parent().find('.positionType').html(position.positionTypeName);
			}, "json");
			
			$.post("${ctx}/AppointAndDismiss/AppointAndDismissInfo/queryUserCountByPosition.do",{positionId:thiz.val()},
			function(result){
				if (0 == result) return;
				else {
					art.dialog.confirm("此岗位还有干部在职，请问是否还要撤岗？",function(){
						aOriginalSelect(thiz,'isDelWithUser','1');
					},function(){
						aOriginalSelect(thiz,'isDelWithUser','0');
					});
				
				}
			});	
		}
		/* 原院级单位改变触发事件*/
		function AcadmyOriginalSelectChange(obj){
			var thiz = $(obj);	
			thiz.parent().parent().next().find(".acadmy").html("<input type='text' onchange=\"aSelect(this,'acadmy','i')\"/>");
			var newObj = thiz.parent().parent().next().find(".acadmy > input");
			
			if (thiz.val() == "") {
				setNull(obj,"department");
				setNull(obj,"positionName");
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
				
				setNull(newObj,"department");
				setNull(newObj,"positionName");
				setNull(newObj,"positionLevel");
				setNull(newObj,"positionType");	
				setNull(newObj,"acadmy");		
				return;
			}
			var html = "<select onchange=\"aSelect(this,'department','s');DepartmentOriginalSelectChange(this)\">" +
						"<option value='' selected>请选择</option>";
			$.post("${ctx}/sys/organization/queryChildByCode.do", {
				code : thiz.val()
			}, function(result) {
				var org;
				for ( var i = 0; i < result.length; i++) {
					org = result[i];
					html += "<option value='"+org.code+"'>"
							+ org.name + "</option>";
				}
				html += "</select>";
				thiz.parent().parent().find(".department").html(html);
			}, "json");
			
			newObj.val(thiz.find("option:selected").text());
			aOriginalSelect(newObj,"acadmy",thiz.val());
			
			setNull(obj,"positionName");
			setNull(obj,"positionLevel");
			setNull(obj,"positionType");

			
			setNull(newObj,"department");
			setNull(newObj,"positionName");
			setNull(newObj,"positionLevel");
			setNull(newObj,"positionType");
			
		}
		/* 系级单位选项事件*/
		function DepartmentOriginalSelectChange(obj){
			thiz = $(obj);
			thiz.parent().parent().next().find(".department").html("<input type='text' onchange=\"aSelect(this,'department','i')\"/>");
			var newObj = thiz.parent().parent().next().find(".department > input");
			var html = "<select onchange=\"aSelect(this,'positionId','s');PositionNameOrigionalSelectChange(this)\">"
					 + "<option value='' selected>请选择</option>";
			if (thiz.val() == '') {
				setNull(obj,"positionName");
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
				
				setNull(newObj,"positionName");
				setNull(newObj,"positionLevel");
				setNull(newObj,"positionType");
				setNull(newObj,"department");
				return;
			}
			$.post("${ctx}/position/queryPositionByDept.do", {
				code : thiz.val()
			}, function(result) {
				var position;
				for ( var i = 0; i < result.length; i++) {
					position = result[i];
					html += "<option value='"+position.id+"'>"
							+ position.positionName + "</option>";
				}
				html += "</select>";
				thiz.parent().parent().find(".positionName").html(html);
			}, "json");
			newObj.val(thiz.find("option:selected").text());
			aOriginalSelect(newObj,"department",thiz.val());
			
			setNull(obj,"positionLevel");
			setNull(obj,"positionType");

			
			setNull(newObj,"positionName");
			setNull(newObj,"positionLevel");
			setNull(newObj,"positionType");
		}
		/* 岗位选择改变事件*/
		function PositionNameOrigionalSelectChange(obj){
			var thiz = $(obj);
			thiz.parent().parent().next().find(".positionName").html("<input type='text' onchange=\"aSelect(this,'department','i')\"/>");
			var newObj = thiz.parent().parent().next().find(".positionName > input");
			if (thiz.val() == '') {
				setNull(obj,"positionLevel");
				setNull(obj,"positionType");
				setNull(newObj,"positionLevel");
				setNull(newObj,"positionType");		
				return;
			}
			$.post("${ctx}/position/queryPositionById.do", {
				code : thiz.val()
			}, function(result) {
				var position;
				position = result;
				thiz.parent().parent().find(".positionLevel").html(position.positionLevelName);
				thiz.parent().parent().find(".positionType").html(position.positionTypeName);
				aOriginalSelect(newObj,'positionLevel',position.positionLevel);
				aOriginalSelect(newObj,'positionType',position.positionType);
				aOriginalSelect(newObj,'positionName',thiz.find("option:selected").text());
				newObj.val(thiz.find("option:selected").text());
				newObj.parent().parent().find(".positionType").html($("#positionTypeRSC").html());
				newObj.parent().parent().find(".positionLevel").html($("#positionLevelRSC").html());
				newObj.parent().parent().find(".positionType > select").val(position.positionType);
				newObj.parent().parent().find(".positionLevel > select").val(position.positionLevel);
				
			}, "json");
			newObj.val(thiz.find("option:selected").text());
			aOriginalSelect(newObj,"positionId",thiz.val());
		}
		//变动，变动时间修改事件
		function positionDayOriginalChange(obj){
			var thiz = $(obj);
			aSelect(thiz.parent().parent().next().find(".type > input"),'actionDay','i');		
		}
		
		//置位
		function setNull(obj,name){
			var thiz = $(obj);
			var index = thiz.parent().parent().find(".type > .indexNum").val();
			var selector = 'input[name="positionPaperItems[' + index + '].' + name
					+ '"]';
			$(selector).val("");
			thiz.parent().parent().find("."+name).html("");
			
		}
		
	</script>
</body>
</html>
