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
		<script type="text/javascript" src="${res}/js/photo.js"></script>
		<style type="text/css">
			.hover_color:hover{
				color:red;
			}
		</style>
  </head>
	<body>
	<div class="extenceInfo appoint" id="appointRSC" index="-1" style="display:none">
				<div class="exTitle">
					<strong>人员任免信息(任)</strong>
					<span class="hover_color" onclick="deleteDismiss(this)">移除</span>
					
					<span class="toggleTag hover_color" style="float:right">缩起↑</span>
				</div>
				<table class="exInfo" cellspacing="0">
					<tr id="sec" >
						<td>任职人员</td>
						<td>
							<input name="" type="text" class="txt required" onfocus = "userNameBlur(this)" onkeyup="userNameBlur(this)"/>
							<label></label>
							<div class="absoluteDiv ml60"></div>
							<input type="hidden" class="userId" name="aadPaperItems[0].userId"/>
						</td>
					</tr>
					<tr class="appointOrRemove"><td>类型</td>
						<td>
							<label>任职</label>
							<input type="hidden" name="aadPaperItems[0].appointOrRemove" value="1" />
						</td></tr>
					<tr><td>任职原因</td><td><input name="aadPaperItems[0].reasion" type="text" class="txt" maxlength=80/></td></tr>
					<tr class="actionDay">
						<td>任职时间</td>
						<td>
							<input name="aadPaperItems[0].actionDay" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" />
						</td>
					</tr>
<script>
$(".appoint_type").change(function(){
	//alert($(this).val());
	var vv=$(this).val();
	if(vv=="0"){
		$($($(this).parents("table")).find(".appoint_grade")).css("display","none");
		$($($(this).parents("table")).find(".appoint_pos")).css("display","none");
		$($($(this).parents("table")).find(".remark")).css("display","none");
	}
	if(vv=="仅任职" || vv==""){
		$($($(this).parents("table")).find(".appoint_grade")).css("display","none");
		$($($(this).parents("table")).find(".appoint_pos")).css("display","");
		$($($(this).parents("table")).find(".remark")).css("display","");
	}
	if(vv=="仅升级"){
		$($($(this).parents("table")).find(".appoint_grade")).css("display","");
		$($($(this).parents("table")).find(".appoint_pos")).css("display","none");
		$($($(this).parents("table")).find(".remark")).css("display","");
	}
	if(vv=="任职且升级"){
		$($($(this).parents("table")).find(".appoint_grade")).css("display","");
		$($($(this).parents("table")).find(".appoint_pos")).css("display","");
		$($($(this).parents("table")).find(".remark")).css("display","");
	}
		
	
});
</script>
					<tr><td >任职类型</td>
						<td>
							<select class="appoint_type" name="aadPaperItems[0].appointOrDismissType">
								<option value="0">请选择</option>
								<option value="仅任职">仅任职</option>
								<option value="仅升级" >仅升级</option>
								<option value="任职且升级" >任职且升级</option>		
							</select>
						</td></tr>
					<tr class="appoint_grade" style="display:none">
						<td>人员级别提拔</td>
						<td>
							<select name="aadPaperItems[0].grade">
								<option value="">请选择</option>
								<c:forEach items="${ide:getAllItems('positionLevel')}" var="kv">
									<option value="${kv.key }">${kv.value }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
						
					
					<tr class="appoint_pos" style="display:none">
						<td class="appointOrDismiss_flag">任职岗位</td>
						<td>
							院/处 <input name="aadPaperItems[0].academy" class="academy" type="text"  onfocus = "insBlur(this,1)" onkeyup="insBlur(this,1)"/>  <label class="academy"></label> <div class="absoluteDiv ml60 academy"></div>  <br/>
							系/科 <input name="aadPaperItems[0].department" class="department" type="text" onfocus = "insBlur(this,2)" onkeyup="insBlur(this,2)" />  <label class="department"></label> <div class="absoluteDiv ml60 department"></div> <br/>
							职务:  <input name="aadPaperItems[0].posName" class="posName" type="text" onfocus = "insBlur(this,3)" onkeyup="insBlur(this,3)"/>  <label class="posName"></label> <div class="absoluteDiv ml60 posName"></div> <br/>
							类型:  <input name="aadPaperItems[0].type" class="posType" type="text"  onfocus = "insBlur(this,4)" onkeyup="insBlur(this,4)"/>  <label class="posType"></label> <div class="absoluteDiv ml60 posType"></div> <br/>
							<input type="button" class="check" value="检查" onclick="check_position(this)"/>
							<input name="aadPaperItems[0].positionId" class="posId" style="display:none"/>
							<label style="color:red" class="checkTip"></label>
						</td>
					</tr>	
					<tr class="remark" style="display:none"><td>备注</td><td><textarea id="remark" name="aadPaperItems[0].remark" maxlength=2000></textarea></td></tr>
				</table>
			</div>
			<div class="extenceInfo dismiss" id="dismissRSC" index="-1" style="display:none">
				<div class="exTitle">
					<strong>人员任免信息(免)</strong>
					<span class="hover_color" onclick="deleteDismiss(this)">移除</span>
					
					<span class="hover_color toggleTag" style="float:right">缩起↑</span>
				</div>
				<table class="exInfo" cellspacing="0">
					<tr id="sec" >
						<td>免职人员</td>
						<td>
							<input name="" type="text" class="txt required" onfocus="userNameBlur(this)" onkeyup="userNameBlur(this)"/>
							<label></label>
							<div class="absoluteDiv ml60"></div>
							<input type="hidden" class="userId" name="aadPaperItems[0].userId"/>
						</td>
					</tr>
					<tr class="appointOrRemove"><td>类型</td>
						<td>
							<label>免职</label>
							<input type="hidden" name="aadPaperItems[0].appointOrRemove" value="2" />
						</td></tr>
					<tr><td>免职原因</td><td><input name="aadPaperItems[0].reasion" type="text" class="txt" maxlength=80/></td></tr>
					<tr class="actionDay">
						<td>免职时间</td>
						<td>
							<input name="aadPaperItems[0].actionDay" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" />
						</td>
					</tr>
<script>
$(".dismiss_type").change(function(){
	//alert($(this).val());
	var vv=$(this).val();
	if(vv=="0"){
		$($($(this).parents("table")).find(".dismiss_grade")).css("display","none");
		$($($(this).parents("table")).find(".dismiss_pos")).css("display","none");
		$($($(this).parents("table")).find(".remark")).css("display","none");
	}
	if(vv=="仅免职" || vv==""){
		$($($(this).parents("table")).find(".dismiss_grade")).css("display","none");
		$($($(this).parents("table")).find(".dismiss_pos")).css("display","");
		$($($(this).parents("table")).find(".remark")).css("display","");
	}
	if(vv=="仅降级"){
		$($($(this).parents("table")).find(".dismiss_grade")).css("display","");
		$($($(this).parents("table")).find(".dismiss_pos")).css("display","none");
		$($($(this).parents("table")).find(".remark")).css("display","");
	}
	if(vv=="免职且降级"){
		$($($(this).parents("table")).find(".dismiss_grade")).css("display","");
		$($($(this).parents("table")).find(".dismiss_pos")).css("display","");
		$($($(this).parents("table")).find(".remark")).css("display","");
	}
		
	
});
</script>
					<tr><td>免职类型</td>
						<td>
							<select class="dismiss_type" name="aadPaperItems[0].appointOrDismissType">
								<option value="0">请选择</option>
								<option value="仅免职" >仅免职</option>
								<option value="仅降级" >仅降级</option>
								<option value="免职且降级" >免职且降级</option>		
							</select>
						</td></tr>
					
					<tr class="dismiss_grade" style="display:none">
						<td>人员级别贬职</td>
						<td>
							<select name="aadPaperItems[0].grade">
								<option value="">请选择</option>
								<c:forEach items="${ide:getAllItems('positionLevel')}" var="kv">
									<option value="${kv.key }">${kv.value }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr class="dismiss_pos" style="display:none">
						<td class="appointOrDismiss_flag">免职岗位</td>
						<td>
							院/处 <input name="aadPaperItems[0].academy" class="academy" type="text"  onfocus = "insBlur(this,1)" onkeyup="insBlur(this,1)"/>  <label class="academy"></label> <div class="absoluteDiv ml60 academy"></div> <br/>
							系/科 <input name="aadPaperItems[0].department" class="department" type="text" onfocus = "insBlur(this,2)" onkeyup="insBlur(this,2)" />  <label class="department"></label> <div class="absoluteDiv ml60 department"></div> <br/>
							职务:  <input name="aadPaperItems[0].posName" class="posName" type="text"  onfocus = "insBlur(this,3)" onkeyup="insBlur(this,3)"/>  <label class="posName"></label> <div class="absoluteDiv ml60 posName"></div> <br/>
							类型:  <input name="aadPaperItems[0].type" class="posType" type="text"  onfocus = "insBlur(this,4)" onkeyup="insBlur(this,4)"/>  <label class="posType"></label> <div class="absoluteDiv ml60 posType"></div> <br/>
							<input type="button" class="check" value="检查" onclick="check_position(this)"/>
							<input name="aadPaperItems[0].positionId" class="posId" style="display:none"/>
							<label style="color:red" class="checkTip"></label>
						</td>
					</tr>
					
					<tr class="remark"><td>备注</td><td><textarea id="remark" name="aadPaperItems[0].remark" maxlength=2000></textarea></td></tr>
				</table>
			</div>
		<div class="tb_box">
		
<!-- ---------------------------------------------分割线---------------------------------------------------------------------------------- -->		
		<form id="addForm" method="post" action="">
			<div class="requiredInfo">
				<div class="reTitle"><strong>任免文件信息(<font color="red">必填</font>)</strong></div>
				<table class="basicInfo" cellspacing="0">
					<tr>
						<td>发文文号</td>
						<td >
							<input id="postingNumber" name="postingNumber" type="text" class="txt required" />
						</td>
					</tr>
					<tr>
						<td>发文名称</td>
						<td >
							<input id="postingName" name="postingName" type="text" class="txt required" />
						</td>
					</tr>
					<tr>
						<td>发文时间</td>
						<td>
							<input id="postingDay" name="postingDay" type="text" class="txt time Wdate required"  onfocus="WdatePicker({readOnly:false})" />
						</td>
					</tr>
					<tr><td>备注</td><td ><textarea id="remark" name="remark" maxlength="2000"></textarea></td></tr>
				</table>
				<label><a id="addAppointment" href="javascript:void(0);">添加任职信息</a></label>
				<label><a id="addDismiss" href="javascript:void(0);">添加免职信息</a></label>
			</div>
			<div class="extenceContainer"></div>
			<div class="tb_sub_btns">
				<input type="button" value="确定" class="add" id="saveBtn" /> 
				<input type="button" value="取消" class="cancel" onClick="art.dialog.close()" />
			</div>
		</form>
		
		</div>
		<script type="text/javascript">
		
		var submitOk=0;			//当submitOk=1时才能提交
		function bindSaveBtnEvent(){
  			$("#saveBtn").click(function(){
 				var data = $("#addForm").serialize();
 				var ff1 = $("select.appoint_type").val();
 				var ff2 = $("select.dismiss_type").val();
 				//alert(data);
						$.post("${ctx}/AppointAndDismiss/AppointAndDismissInfo/Aadadd.do", data,
						function(result) {
							//alert(result);
								if("success" == result) {
									alert("添加成功");
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();
				                }
				                if(result=="postingError"){
				                	alert("发文信息不完整或错误");
				                }
								if( "userNameError"==result){
				                    alert("任免人员信息不完整或错误");
				                }
				                if(result=="posError"){
				                	alert("岗位信息不完整或错误");
				                }
						});
 				
			}); 
		}

		function changeDismiss(){			
			$(".extenceInfo > table").slideUp();
			$(".extenceInfo").find(".toggleTag").html("展开↓");
			$(".extenceInfo:last > table").slideDown();
			$(".extenceInfo:last").find(".toggleTag").html("缩起↑");
			bindToggleTagEvent();
		}
		function bindToggleTagEvent(){
			$(".extenceInfo:last").find(".toggleTag").click(function(){
			
				if ($(this).parent().parent().find("table").css("display") == "none"){
					$(this).parent().parent().find("table").slideDown();
					$(this).html("缩起↑");
				}else {
					$(this).parent().parent().find("table").slideUp();
					$(this).html("展开↓");
				}
			});
		}
		function deleteDismiss(thiz)
			{
				
				$(thiz).parent().parent().remove();
				
			}
	
	//岗位填写正确性查询
	function check_position(obj){
		var thiz = $(obj);
		userId=thiz.parent().parent().siblings("#sec").find("input.userId").val();
		//alert(userId);
		var text = thiz.parent().siblings(".appointOrDismiss_flag").text();
		var flag=0;
		if(text=="免职岗位"){
			flag=1;
		}
		var aca = thiz.siblings("input.academy").val();
		var dep = thiz.siblings("input.department").val();
		var posN = thiz.siblings("input.posName").val();
		var posT = thiz.siblings("input.posType").val();
		$.post("${ctx}/position/checkPositionCorrect.do",{academy: aca, department: dep, posName: posN, posType: posT, appointOrDismiss: flag, userId:userId},function(result){
			var text = decodeURIComponent(result);
			//alert(text);
			if(text=="invalidData"){
				thiz.siblings("label.checkTip").html("请填完整数据");
			}else{
				var tt=text.substring(0,4);
				var iid=text.substring(4);
				if(tt=="岗位可用"){
					submitOk=1;
					thiz.siblings("label.checkTip").html("岗位可用");
					thiz.siblings("input.posId").val(iid);			//查询之后设置岗位id
				}else{
					submitOk=0;
					thiz.siblings("label.checkTip").html("岗位不可用");
				}
			}
		});
	}
	
	//选择一个查询出来的结果
	function chooseName(obj, flag){
		var thiz = $(obj);
		
		thiz.parent().parent().siblings("label.checkTip").html("请点击按钮检查");
		submitOk=0;
		//alert(thiz.text());
		if(flag==1){
			thiz.parent().parent().siblings("input.academy").val(thiz.text());
			thiz.parent().parent().siblings("label.academy").html("");
			thiz.parent().parent().css("display","none");
		}
		if(flag==2){
			thiz.parent().parent().siblings("input.department").val(thiz.text());
			thiz.parent().parent().siblings("label.department").html("");
			thiz.parent().parent().css("display","none");
		}
		if(flag==3){
			thiz.parent().parent().siblings("input.posName").val(thiz.text());
			thiz.parent().parent().siblings("label.posName").html("");
			thiz.parent().parent().css("display","none");
		}
		if(flag==4){
			thiz.parent().parent().siblings("input.posType").val(thiz.text());
			thiz.parent().parent().siblings("label.posType").html("");
			thiz.parent().parent().css("display","none");
		}
	}


		//机构边输入边查询
	function insBlur(obj, flag){
		var thiz=$(obj);
		var ins=thiz.val();
		thiz.siblings("label.checkTip").html("请点击按钮检查");
		submitOk=0;
		if(ins==""){
			if(flag==1){ 
				thiz.parent().find("div.academy").css("display","none");
				thiz.next("label").html("");
			}
			if(flag==2) {
				thiz.parent().find("div.department").css("display","none");
				thiz.next("label").html("");
			}
			if(flag==3) {
				thiz.parent().find("div.posName").css("display","none");
				thiz.next("label").html("");
			}
			if(flag==4) {
				thiz.parent().find("div.posType").css("display","none");
				thiz.next("label").html("");
			}
			return ;
		}
		
		//一级机构，院级
		if(flag==1){
			$.post("${ctx}/position/existInsName.do",{insName: ins, flag: 1},function(result){
				
				if(result == null || result.length==0 || typeof(result)==undefined){
					//alert(result.length);
					thiz.parent().find("div").html("");
   					thiz.next("label").html("错误");
   					thiz.parent().find(".selectName").remove();
				}else{
					if(result.length==1 && result[0]==ins){
							thiz.next("label").html("");
							thiz.parent().find("div.academy").css("display","none");
					}else{
						//alert(result.length);
						thiz.parent().find("div.academy").css("display","");
						var htmlStr="<ul class='selectName'>";
	   					for (var i =0;i<result.length;i++){
							htmlStr += "<li onclick=\" chooseName(this,1) \">"+result[i]+"</li>";        				
	   					}
	   					htmlStr += "</ul>";
	   					thiz.parent().find("div.academy").html(htmlStr);
	   				}
				}
				thiz.bind("blur.hide",function(){
					thiz.parent().find("div").html('');
				});
				thiz.parent().find("div").hover(function(){//进入失去函数绑定
					thiz.unbind("blur.hide");
				},function(){//出去获取函数绑定
					thiz.bind("blur.hide",function(){
						thiz.parent().find("div").html('');
					});
				});
			});
		}
		
		//二级机构，系级
		if(flag==2){
			$.post("${ctx}/position/existInsName.do",{insName: ins, flag: 2},function(result){
				
				if(result == null || result.length==0 || typeof(result)==undefined){
					//alert(result.length);
					thiz.parent().find("div").html("");
   					thiz.next("label").html("错误");
   					thiz.parent().find(".selectName").remove();
				}else{
					if(result.length==1 && result[0]==ins){
							thiz.next("label").html("");
							thiz.parent().find("div.department").css("display","none");
					}else{
						//alert(result.length);
						thiz.parent().find("div.department").css("display","");
						var htmlStr="<ul class='selectName'>";
	   					for (var i =0;i<result.length;i++){
							htmlStr += "<li onclick=\" chooseName(this,2) \">"+result[i]+"</li>";        				
	   					}
	   					htmlStr += "</ul>";
	   					thiz.parent().find("div.department").html(htmlStr);
	   				}
				}
				thiz.bind("blur.hide",function(){
					thiz.parent().find("div").html('');
				});
				thiz.parent().find("div").hover(function(){//进入失去函数绑定
					thiz.unbind("blur.hide");
				},function(){//出去获取函数绑定
					thiz.bind("blur.hide",function(){
						thiz.parent().find("div").html('');
					});
				});
			});
		}
		
		//三级机构，岗位级
		if(flag==3){
			$.post("${ctx}/position/existInsName.do",{insName: ins, flag: 3},function(result){
				
				if(result == null || result.length==0 || typeof(result)==undefined){
					//alert(result.length);
					thiz.parent().find("div").html("");
   					thiz.next("label").html("错误");
   					thiz.parent().find(".selectName").remove();
				}else{
					if(result.length==1 && result[0]==ins){
							thiz.next("label").html("");
							thiz.parent().find("div.posName").css("display","none");
					}else{
						//alert(result.length);
						thiz.parent().find("div.posName").css("display","");
						var htmlStr="<ul class='selectName'>";
	   					for (var i =0;i<result.length;i++){
							htmlStr += "<li onclick=\" chooseName(this,3) \">"+result[i]+"</li>";        				
	   					}
	   					htmlStr += "</ul>";
	   					thiz.parent().find("div.posName").html(htmlStr);
	   				}
				}
				thiz.bind("blur.hide",function(){
					thiz.parent().find("div").html('');
				});
				thiz.parent().find("div").hover(function(){//进入失去函数绑定
					thiz.unbind("blur.hide");
				},function(){//出去获取函数绑定
					thiz.bind("blur.hide",function(){
						thiz.parent().find("div").html('');
					});
				});
			});
		}
		//四级，岗位类型
		if(flag==4){
			$.post("${ctx}/position/existInsName.do",{insName: ins, flag: 4},function(result){
				
				if(result == null || result.length==0 || typeof(result)==undefined){
					//alert(result.length);
					thiz.parent().find("div").html("");
   					thiz.next("label").html("错误");
   					thiz.parent().find(".selectName").remove();
				}else{
					if(result.length==1 && result[0]==ins){
							thiz.next("label").html("");
							thiz.parent().find("div.posType").css("display","none");
					}else{
						//alert(result.length);
						thiz.parent().find("div.posType").css("display","");
						var htmlStr="<ul class='selectName'>";
	   					for (var i =0;i<result.length;i++){
							htmlStr += "<li onclick=\" chooseName(this,4) \">"+result[i]+"</li>";        				
	   					}
	   					htmlStr += "</ul>";
	   					thiz.parent().find("div.posType").html(htmlStr);
	   				}
				}
				thiz.bind("blur.hide",function(){
					thiz.parent().find("div").html('');
				});
				thiz.parent().find("div").hover(function(){//进入失去函数绑定
					thiz.unbind("blur.hide");
				},function(){//出去获取函数绑定
					thiz.bind("blur.hide",function(){
						thiz.parent().find("div").html('');
					});
				});
			});
		}
	}
		
		/* 任免人员*/
	function userNameBlur(obj){
		var thiz = $(obj);
		var user = thiz.val();
		var index = thiz.parent().parent().attr("index");
   		if (user == "") return;
   		$.post("${ctx}/infoManager/userInfo/existUserName.do",{userName:user },function(result){
   			if(null == result || 0 == result.length){
   				thiz.parent().find("div").html("");
   				thiz.parent().find("label").html("该人员不存在");
   				thiz.parent().find(".selectName").remove();
   			}
   			if (1 == result.length){
   				if (result[0].name == user){
	   				thiz.parent().find("div").html("");
	   				thiz.parent().find("label").html("正确");
	   				thiz.parent().find("input[name='userId']").val(result[0].id);
	   				thiz.parent().find(".selectName").remove();
   				} else {
	   				var htmlStr="<ul class='selectName'>";
	   				for (var i =0;i<result.length;i++){
						htmlStr += "<li><a href='javascript:showUser("+result[i].id+")'>" + result[i].name + "</a><input type='button' onclick=\"chooseUser("+result[i].id+",'"+result[i].name+"',this)\" value='选择'/></li>";        				
	   				}
	   				htmlStr += "</ul>";
	   				thiz.parent().find("div").html(htmlStr);
	   			}
   			}   	
   			else if (result.length>1){
   				var htmlStr="<ul class='selectName'>";
   				for (var i =0;i<result.length;i++){
					htmlStr += "<li><a href='javascript:showUser("+result[i].id+")'>" + result[i].name + "</a><input type='button' onclick=\"chooseUser("+result[i].id+",'"+result[i].name+"',this)\" value='选择'/></li>";        				
   				}
   				htmlStr += "</ul>";
   				thiz.parent().find("div").html(htmlStr);
   			}
   			thiz.bind("blur.hide",function(){
				thiz.parent().find("div").html('');
			});
			thiz.parent().find("div").hover(function(){//进入失去函数绑定
				thiz.unbind("blur.hide");
			},function(){//出去获取函数绑定
				thiz.bind("blur.hide",function(){
					thiz.parent().find("div").html('');
				});
			});
   		},"json");
	}
	function positionSelectChange(obj){
	var thiz = $(obj);
	if ("" == thiz.val())
		thiz.parent().parent().find(".grade > select ").val("");
	$.post("${ctx}/position/queryPositionById.do", {
				code : thiz.val()
			}, function(result) {
				var position = result;
				thiz.parent().parent().parent().find(".grade select ").val(result.positionLevel);
			}, "json");
			
	}
	 function showUser(id){
       	//用户详情
		art.dialog.open(
			"${ctx}/infoManager/userInfo/openUserDetail.do?id="+id,
			{ title: "用户详情", lock: true, width: 996, height: 600 }
		);
       }
        function chooseUser(id,name,obj){
	     	var thiz = $(obj);
	     	var td = thiz.parent().parent().parent().parent();
	     	td.find(":text").val(name);
	     	td.find(".userId").val(id);
	       	td.find("label").html("正确");
	       	td.find("div").html("");
	       	if (td.parent().parent().parent().parent().hasClass("dismiss")) {
	       		$.post("${ctx}/AppointAndDismiss/AppointAndDismissInfo/queryPositionByUserId.do"
	       		,{userId:id}
	       		,function(result){
	       			if (result == "noUser") {
	       				alert("请选择正确的人员");
	       				td.find("label").html("错误");
	       			}
	       			if(result =="theUserNoPosition"){
	       				alert("该人员无岗位,无法撤岗");
	       				td.find("label").html("错误");
	       			}
	       		});
	       	}
	       	
	       
       	
       }
       function bindAddAppointmentEvent(){
       		$("#addAppointment").click(function(){    		
       			var div= $("#appointRSC");
				var newDiv = div.clone(true);
				var count = 1;
				var counts = new Array();
				$(".extenceInfo").each(function(){
					counts[$(this).attr("index")] = $(this).attr("index"); 
				});
				var flag = 1;
				for (count = 0;;count++){
					flag = 1;
					for (var x in counts){
						if (counts[x] == count){
							flag = 0;
							break;
						}
					}
					if (flag == 1)
						break;
				}
				var replaceString = "aadPaperItems["+count+"]";
				var regx = /aadPaperItems\[\d\]/g;
				newDiv.html(newDiv.html().replace(regx,replaceString));
				newDiv.attr("index",count);
				newDiv.css("display","inline");
				$(".extenceContainer").append(newDiv);
				changeDismiss();
       		});
       }
       
       function bindAddDismissEvent(){
     	  $("#addDismiss").click(function(){
       			var div= $("#dismissRSC");
				var newDiv = div.clone(true);
				var count = 1;
				var counts = new Array();
				$(".extenceInfo").each(function(){
					counts[$(this).attr("index")] = $(this).attr("index"); 
				});
				var flag = 1;
				for (count = 0;;count++){
					flag = 1;
					for (var x in counts){
						if (counts[x] == count){
							flag = 0;
							break;
						}
					}
					if (flag == 1)
						break;
				}
				var replaceString = "aadPaperItems["+count+"]";
				var regx = /aadPaperItems\[\d\]/g;
				newDiv.html(newDiv.html().replace(regx,replaceString));
				newDiv.attr("index",count);
				newDiv.css("display","inline");
				$(".extenceContainer").append(newDiv);
				changeDismiss();
       		});
       }
 	$(function(){
	 	bindToggleTagEvent();
	 	bindAddAppointmentEvent();
	 	bindAddDismissEvent();
	 	bindSaveBtnEvent();
 	});
 	</script>
</body>
</html>
