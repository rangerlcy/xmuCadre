<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/ny.css" />
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
		<script type="text/javascript">		
			function add(){
				var div='<table cellpadding="0" cellspacing="0" border="0" class="basicInfo">';
					div+='<tr>';
					div+='	<td ><span>*</span>挂职人员<input id="user" name="user" type="text" class="txt" onfocus="userNameBlur(this)" onchange="userNameBlur(this)" onkeyup="userNameBlur(this)"/><input name="userId" id="userId" type="hidden" value=""/><div id="searchResultOutput" style="margin-left:72px;text-decoration:none;"></div></td>';
					div+='	<td ><span>*</span>挂职地点<input id="temporaryPlace" name="temporaryPlace" type="text" class="txt"/></td>';
					div+='</tr>';
					div+='<tr>';
					div+='	<td ><span>*</span>挂职单位<input id="temporaryUnit" name="temporaryUnit" type="text" class="txt" /></td>';
					div+='	<td ><span>*</span>挂职职务<input id="temporaryJob" name="temporaryJob" type="text" class="txt"/></td>';
					div+='</tr>';
					div+='<tr>';
					div+='	<td><span>*</span>考核情况<input id="assessementSitutation" name="assessementSitutation" type="text" class="txt"/></td>';
					div+='	<td>备注<input type="text" id="otherinfo" /></td>';
					div+='</tr>';
					div+='<tr>';
					div+='	<td colspan="2"><input type="button" value="添加" onclick="addtb();" />';
					div+='	<input type="button" value="取消" onclick="closeadd();" /></td>';
					div+='</table>';
				$("#info").html(div);
				$(".tb15").html('');
			
				$("#user").bind("blur.hide",function(){
					$("#searchResultOutput").html('');
				});
				$("#searchResultOutput").hover(function(){//进入失去函数绑定
					$("#user").unbind("blur.hide");
				},function(){//出去获取函数绑定
					$("#user").bind("blur.hide",function(){
						$("#searchResultOutput").html('');
					});
				});
			}
			function addtb(){
				if($("#user").val()==""||$("#temporaryPlace").val()==""||$("#temporaryUnit").val()==""
					||$("#temporaryJob").val()==""||$("#assessementSitutation").val()=="") art.dialog.alert("还有必要信息为填写");
				else {
					if ($(".userIds[value='"+$("#userId").val()+"']").size()>=1) {
						art.dialog.alert("添加的人员已存在");
						return;
					}
					$(".tb15").html('<span onclick="add()">添加</span>');
					var count = 1;
					var counts = new Array();
					$(".scUser").each(function(){
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
					var num = count;
					var tr = '<tr class="scUser" index="'+num+'">';
						tr += '	<td><label></label>'+$("#user").val()+'<input type="hidden" value="'+$("#userId").val()+'"  class="userIds" name="secondmentUserList['+num+'].userId"/></td>';
						tr += '	<td><label></label>'+$("#temporaryPlace").val()+'<input type="hidden" value="'+$("#temporaryPlace").val()+'" name="secondmentUserList['+num+'].temporaryPlace"/></td>';
						tr += '	<td><label></label>'+$("#temporaryUnit").val()+'<input type="hidden" value="'+$("#temporaryUnit").val()+'" name="secondmentUserList['+num+'].temporaryUnit"/></td>';
						tr += '	<td><label></label>'+$("#temporaryJob").val()+'<input type="hidden" value="'+$("#temporaryJob").val()+'" name="secondmentUserList['+num+'].temporaryJob"/></td>';
						tr += '	<td><label></label>'+$("#assessementSitutation").val()+'<input type="hidden" value="'+$("#assessementSitutation").val()+'" name="secondmentUserList['+num+'].assessementSitutation"/></td>';
						tr += '	<td><textarea id="remark" name="remark">'+$("#otherinfo").val()+'</textarea><input type="hidden" value="'+$("#otherinfo").val()+'" name="secondmentUserList['+num+'].remark"/></td>';
						tr += '	<td style="width:40px; cursor:pointer;"><span onclick="del(this)">删除</span></td>';
						tr += '</tr>';
					$("#secondementPointer").after(tr);
					$("#info").html('');
				}
				
			}
			function del(thiz){
				$(thiz).parent().parent().remove();
			}
			function closeadd(){
				$(".tb15").html('<span onclick="add()">添加</span>');
				$("#info").html('');
			}
		</script>
	</head>
	<body class="tb_940">
	    <div class="tb_box">
	    	<table cellpadding="0" cellspacing="0" border="0" class="tb80">
	    		<tr>
	    			<td width="150">项目名称：</td>
	    			<td align="left">${secondment.temporaryProjectName }</td>
	    		</tr>
	    		<tr>
	    			<td>发文文号：</td>
	    			<td align="left">${secondment.paper.paperNumber }</td>
	    		</tr>
	    		<tr>
	    			<td>发文文名：</td>
	    			<td align="left">${secondment.paper.paperName }</td>
	    		</tr>
	    	</table>
	    	<div class="tb15"><span onclick="add()">添加</span></div>
	    	<div id="info"></div>
	    	<center style="clear:both"><h4>项目中已添加的人员</h4></center>
	    	<table cellpadding="0" cellspacing="0" border="0" class="basicInfo">	    		
				<tr>
					<td >挂职人员</td>
					<td >挂职地点</td>
					<td >挂职单位</td>
					<td >挂职职务</td>
					<td>考核情况</td>
					<td>备注</td>					
				</tr>
				<c:forEach items="${secondmentUsers }" var="secU">
					<tr>
						<td >${secU.user.name }<input type="hidden" class="userIds" value="${secU.user.id }"/></td>
						<td >${secU.temporaryPlace }</td>
						<td >${secU.temporaryUnit }</td>
						<td >${secU.temporaryJob }</td>
						<td>${secU.assessementSitutation }</td>
						<td>${secU.remark }</td>
					</tr>
				</c:forEach>
			</table>
			<center style="clear:both"><h4>项目中正在添加的人员</h4></center>
	    	<form action="" id="secondmentUserForm" onsubmit="return false">
	    		<input type="hidden" value="${secondment.id}" name="secondmentId"/>
			<table cellpadding="0" cellspacing="0" border="0" class="basicInfo">
				<tr id="secondementPointer">
					<td ><span>*</span>挂职人员</td>
					<td ><span>*</span>挂职地点</td>
					<td ><span>*</span>挂职单位</td>
					<td ><span>*</span>挂职职务</td>
					<td><span>*</span>考核情况</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
			</table>
			</form>
		</div>
		<div class="tb_sub_btns">
			<input type="button" value="确定" class="add" id="saveBtn" />
			<input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
		</div>
	    <script type="text/javascript">
	        $(function(){
	        	$("#user").focus();
	        	bindSaveEvent();//绑定提交表单事件
	        	
	        	
	        	
	        });
			function userNameBlur(obj){
		var thiz = $(obj);
		var user = thiz.val();

		if ("" == user) {
			thiz.parent().find(".selectName").remove();
			return;
		}
   		$.post("${ctx}/infoManager/userInfo/existUserName.do",{userName:user },function(result){
   			if(null == result || 0 == result.length){
   				thiz.parent().find("label").html("该人员不存在");
   				thiz.parent().find(".selectName").remove();
   			}
   			if (1 == result.length){
   				if (result[0].name == user){
	   				thiz.parent().find("label").html("");
	   				thiz.parent().find("input[name='userId']").val(result[0].id);
	   				thiz.parent().find(".selectName").remove();
   				} else {
	   				var htmlStr="<ul id='selectName'>";
	   				for (var i =0;i<result.length;i++){
						htmlStr += "<li><a href='javascript:showUser("+result[i].id+")'>" + result[i].name + "</a><button onclick=\"chooseUser("+result[i].id+",'"+result[i].name+"',this)\">选择</button></li>";        				
	   				}
	   				htmlStr += "</ul>";
	   				thiz.parent().find("label").html(htmlStr);
	   			}
   				
   			}   	
   			else if (result.length>1){
   				var htmlStr="<ul id='selectName'>";
   				for (var i =0;i<result.length;i++){
					htmlStr += "<li><a href='javascript:showUser("+result[i].id+")'>" + result[i].name + "</a><button onclick=\"chooseUser("+result[i].id+",'"+result[i].name+"',this)\">选择</button></li>";        				
   				}
   				htmlStr += "</ul>";
   				thiz.parent().find("#searchResultOutput").html(htmlStr);
   			}
   		},"json");
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
	     	td.find(":hidden").val(id);
	       	td.find("#searchResultOutput").html("");
	       	
	       	
	       
       	
       }
	        function bindSaveEvent(){
				$("#saveBtn").click(function(){
					var canSubmit = true;
	                {
	               		var data = $("#secondmentUserForm").serialize();
		                if(!canSubmit){
							art.dialog.alert("该人员不存在");
		                    return;
						}
						if(canSubmit){
							var data = $("#secondmentUserForm").serialize();
			                $.post("${ctx}/secondment/addSecondmentUser.do",data,function(result){
					                if("success" == result) {
					                	
					    				art.dialog.opener.location.reload();
					                	art.dialog.close();               
					                } else if (result == "error"){
					       
					                    art.dialog.tips("无此信息");
					                } else if ( result == "nobody") {
					                	art.dialog.tips("无人添加");
					                }
					                
				        	});					
						}
					}
				});
			}
	    </script>
	</body>
</html>