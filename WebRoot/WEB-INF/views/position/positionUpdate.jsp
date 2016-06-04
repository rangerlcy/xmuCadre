<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 岗位的增撤改，都在这里做了 -->
<html>
  <head>
    	<%@ include file="/common/common.jsp"%>
		<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide" %>
		<script  type="text/javascript" src="${res}/js/artDialog/artDialog.js?skin=blue"></script>
		<title>厦门大学干部信息管理系统</title>
		<script type="text/javascript" src="${res}/js/tableType.js"></script>
		<script type="text/javascript" src="${res}/js/jquery.js"></script>
		<script type="text/javascript" src="${res}/js/ajaxfileupload.js"></script>
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
		<style type="text/css">
			.add_date:hover{
				cursor:pointer;
				text-decoration:underline;
				color:red;
			}
			.del_data:hover{
				cursor:pointer;
				text-decoration:underline;
				color:red;
			}
			.red-color{
				
				color:red;
			}
		</style>
  </head>
<body>
	<p>说明：请一次性将一份发文下对岗位的所有操作填写完整，数据库将根据填写的内容进行更新。提交前务必检查每条数据和文件的正确性，否则上传将失败</p>
	<!-- <p>变动岗位(仅修改) 这个选项请<span class='red-color'>单独使用</span>，用于改正错误信息,此时发文填写栏是说明性文字而不是发文信息。修改后的详细信息可以在发文信息中查看</p>  -->
	<p>岗位类型请输入: ---<c:forEach items="${ide:getAllItems('positionType')}" var="kv">${kv.value }---</c:forEach></p>
	<div class="tb_940">
		<div id="Addform">
		<form id="data" method="post" action="" enctype="multipart/form-data">
			<table cellpadding="0" cellspacing="0" class="basicInfo">
				<tr>
					<td style="font-size:18px;">发文名称</td>
					<td>
						<input type="text" class="paperName" name='paperName'/>
					</td>
					<td style="font-size:18px;">发文文号</td>
					<td>
						<input type="text" class="paperNumber" name='paperNumber'/>
					</td>
				</tr>
				<tr>
					<td style="font-size:18px;">发文时间</td>
					<td><input type="text" class="paperTime" name="paperTime" placeholder="格式：xxxx-xx-xx"></td>
					<td style="font-size:18px;">文件内容</td>
					<td id="ffile">	 	
						<input type="button" class="btn" id="fileBtn" value="选择文件"/>
						<input type="button" class="btn" id="nofileBtn" value="不上传文件">
						<label></label>
						<input class="btn" id="checkfile" type='button' value='检查文件是否正确' style="display:none">
						
						<!--  
						<input type='file' id='paperfile' name='paperfile'>
						<button class="checkfile" style="display:none">检查文件是否正确</button>
						-->
					</td>
					<td style='display:none'><input name='end_mark' value='table_end'></td>
				</tr>
			</table>
			<div class="append_area">
				
			</div>
		</form>
		
			<div style="margin-top: 30px"></div>
			<div class="panel">
				<select class="opt">
					<option value="0">请先选择变动类型</option>
					<option value="1">增加岗位</option>
					<option value="2">撤除岗位</option>
					<option value="3">变动岗位(先撤后增)</option>
					<!-- <option value="4">变动岗位(仅修改)</option> -->
				</select>
			</div>
			
			<span class="add_date">+点击这里添加一条数据</span>
			
			<div class="tip" style="margin-top:20px; color:red; display:none">提示：请确认上述数据是否有误,再次点击提交将不再提醒</div>
			<div class="tb_sub_btns">
				<input type="button" value="提交" class="submitBtn" />
				<input type="button" value="返回" onclick="art.dialog.close()"/>
			</div>
			 
			<form id="uploadf" method="post" action="" enctype="multipart/form-data" style="display:none;">
				<input type="file" name="file" style="display:none"/>
			</form>
			
		</div>
	</div>
	<script>
		
		
		//上传发文文件
		var flag_file=0;	//只有当这个flag_file=1时才能最终提交上数据
		
		$("#nofileBtn").click(function(){
			$("#fileBtn").css("display","none");
			$("#nofileBtn").css("display","none");
			$("#ffile").append("不上传文件");
			flag_file=1;
		});

		$(":file").change(function(){
			$("#fileBtn").next().html($(this).val());
			$("#checkfile").css("display","");
		});
		$("#fileBtn").click(function(){
			//$("#nofileBtn").css("display","none");
			$("#uploadf").find(":file").click();
		});
		
		$("#uploadf").find(":file").change(function(){
			$("#nofileBtn").css("display","none");
			$("#fileBtn").val("重新选择文件");
		});
		//检查上传文件是否正确
		$("#checkfile").click(function(){
			ajaxFileUpload();
		});
		
/*	
		$("#paperfile").change(function(){
			$(".checkfile").css("display","");
			flag_file=1;
		});
*/		
		function ajaxFileUpload(){
			var formData = new FormData($("#uploadf")[0]);  
     		//var formData = new FormData(null);
     		$.ajax({  
          		url: '${ctx}/position/getPaperFile.do' ,  
          		type: 'POST',  
          		data: formData,  
          		dataType: 'text',
          		async: false,  
          		contentType: false,  
          		processData: false,  
          		success: function (msg) {  
              		if(msg=='success'){
              			alert("文件正确可以上传");
              			flag_file=1;
              		}
              		if(msg=='fail1'){
              			alert("文件不存在，请重新选择");
              			flag_file=0;
              		} 
              		if(msg=='fail2'){
              			alert("文件格式不正确，请重新选择");
              			flag_file=0;
              		}
          		},  
          		error: function (msg) {  
              		alert(returndata);  
         		 }  
     		});  
		}
		
		//添加一行数据
		$(".add_date").click(function(){
			var opt_value=$($(".opt").find("option:selected")).val();
			if(opt_value==0){
				alert("请先选择要修改的类型");
			}else{
				if(opt_value==1){
					$(".append_area").append(str1);
				}
				if(opt_value==2){
					$(".append_area").append(str2);
				}
				if(opt_value==3){
					$(".append_area").append(str3);
				}
				if(opt_value==4){
					$(".append_area").append(str4);
				}
			}
		});	
		
		//提交数据
		var time=0;
		$(".submitBtn").click(function(){
			var a=$.trim($(".append_area").html());
			
			var b=$.trim($(".paperName").val());
			var c=$.trim($(".paperNumber").val());
			var d=$.trim($(".paperTime").val());
			//检查文件是否上传var e=$.trim($(".paperName").val());
			if(a==null|| a==""){
				alert("请至少添加一条数据再提交！");
			}else if(b==null|| b==""|| c==null|| c==""|| d==null|| d==""){
				alert("请将发文信息填写完整");
			}else if(flag_file!=1){
				alert("请先检查上传文件的正确性");
			}
			else{
				if(time==0){
					$(".tip").css("display","");
					time++;
				}else{
					//alert($("#data").serialize());
					$.post("${ctx}/position/positionUpdate.do",{
		                		data:$("#data").serialize()
							},function(result,status,xhr){
								//alert(result+"~~~"+status+"~~~~"+xhr);
				                if("success" == result) {
				                	alert("添加成功！");
				                	flag_file=0;
				    				art.dialog.opener.location.reload();
				                	art.dialog.close();               
				                } else if( "fail"==result.substring(0,4)){
				                	
				       				if(result.substring(4,5) == "0"){
				       					art.dialog.tips("添加失败，请检查发文日期是否正确");
				       				}
				                    if(result.substring(4,5) == "1"){
				       					art.dialog.tips("添加失败，请检查 “添加岗位” 部分的日期是否正确");
				       				}
				       				if(result.substring(4,5) == "2"){
				       					art.dialog.tips("添加失败，请检查 “变动岗位(先增后撤)” 增加到岗位部分的日期是否正确");
				       				}
				       				if(result.substring(4,5) == "3"){
				       					art.dialog.tips("添加失败，请检查 “变动岗位(仅修改)” 修改成岗位部分的日期是否正确");
				       				}
				       				if(result.substring(4,5) == "4"){
				       					art.dialog.tips("数据库中数据出现了问题，查到了和当前添加的发文一致的数据，请联系管理员");
				       				}
				       				if(result.substring(4,5) == "5"){
				       					art.dialog.tips("添加失败，请检查 “删除岗位” 部分的日期是否正确");
				       				}
				       				if(result.substring(4,5) == "6"){
				       					art.dialog.tips("添加失败，没有获取到信息");
				       				}
				       				if(result.substring(4,5) == "7"){
				       					art.dialog.tips("请先点击 “检查岗位正确性”无误后再添加 ");
				       				}
				                }
			        	},"text");	
				}
			}
		});	
		
		function beclicked(thiz){
			$($(thiz).next("div")).empty();
			$($(thiz).next("div")).detach(); 
			$(thiz).detach();		
		}
		
		function synchronous_data(obj,value,mark){
			var aca_obj=$($($(obj).parents(".parent_mid")).next(".target"));
			if(mark==1){
				var temp=$(aca_obj).children(".academy");
				$(temp).text(value);
			}
			if(mark==2){
				var temp=$(aca_obj).children(".department");
				$(temp).text(value);
			}
			if(mark==3){
				var temp=$(aca_obj).children(".positionlevel");
				$(temp).text(value);
			}
		}
		
		function getpid(obj){
			var pid=$($(obj).find("option:selected")).val();
			if(pid==0){
				art.dialog.tips("有多个岗位，选择其中一个进行修改");
			}else{
				$($($($(obj).parent(".check_result")).siblings("table")).find(".position_id")).val(pid);
			}
		}
		
		function check_data(obj){
			var temp=$($(obj).prev("table"));
			var academy=$(temp.find(".academy")).val();
			var department=$(temp.find(".department")).val();
			var position_name=$(temp.find(".position_name")).val();
			var position_type=$(temp.find(".position_type")).val();
			var position_level=$(temp.find(".position_level")).val();
			var update_time=$(temp.find(".update_time")).val();
			$.ajax({
				type : "post",
				url : "${ctx}/position/positionUpdate_check.do",
				dataType : "text",
				async: false,
				data : {'academy': academy,
						'department': department,
						'position_name': position_name,
						'position_type': position_type,
						'position_level': position_level,
						'update_time': update_time},
				success:function(info){
					var text = decodeURIComponent(info);
					//text.substring()
					var str = new Array();
					str=text.split('&');
					
					$($(obj).next("div")).text("");
					str[0]=str[0].replace(/\+/g," ");
					//alert(str[0]+"!!"+str[1]);
					$(obj).next("div").append(str[0]);
					if(str[1].trim()!='' && str[1]!='many'){
						$($($(obj).siblings("table")).find(".position_id")).val(str[1]);
					}
					if(str[1]=='many'){		//有多个select 供选择
						var pid=$($(".chos").find("option:selected")).val();
						if(pid==0){
							art.dialog.tips("有多个岗位，选择其中一个进行修改");
						}
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					//开发模式下，查看错误信息
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
               		alert("因为后台的某些问题，出现错误，请联系管理员");
            	}
			});
		}
		
		/*  暂时注释
		function query_academy(obj){
			alert($(obj).val());
		}
		*/
		var str1=["<span class='del_data' onclick='beclicked(this)'>点击删除下面这一条数据</span>",
		"<div>",
"		<table cellpadding=\"0\" cellspacing=\"0\" class=\"basicInfo\">",
"						<tr id=\"addAndRemove\" style=\"background:#22B2AF;\">",
"							<td>修改类型</td>			",
"							<td >学院（研究院）/部处</td>						",
"							<td >系所/科室</td>						",
"							<td >岗位名称</td>",
"							<td>岗位类型</td>",
"							<td >岗位级别</td>",
"							<td >设岗时间</td>",
"							<td> 职位备注</td>",
"						</tr>",
"						<tr>",
"							<td><input name='mark' value=\"#增加岗位\" style='display:none'>增加岗位</td>",
"							<td>",
"<input name='academy' type=\"text\" onkeyup='query_academy(this)' class='academy'>",
"							</td>",
"							<td >",
"<input name='department' type=\"text\" class='department'>",
"							</td>",
"							<td >",
"<input name='position_name' type=\"text\" class='position_name'>",
"							</td>",
"							<td >",
"<input name='position_type' type=\"text\" class='position_type'>",
"							</td>",
"							<td >",
"<input name='position_level' type=\"text\" class='position_level'>",
"							</td>",
"							<td><input name='update_time' type=\"text\" placeholder='格式xxxx-xx-xx'\"></td>",
"							<td><input name='position_remark' type=\"text\"></td>",
"							<td style='display:none'><input name='end_mark' value='table_end'></td>",	
"						",
"						</tr>			",
"					</table>",
"<div style=\"  border: 1px dashed; margin-bottom: 10px\"></div>",
"<\div>"].join("");
var str2=["<span class='del_data' onclick='beclicked(this)'>点击删除下面这一条数据</span>",
"<div><p>为了找到撤除的岗位，请在设岗时间处填写要撤除岗位的设岗时间，岗位的实际撤岗时间请在撤岗时间中填写，如果不填则默认 = 发文时间  </p>",
"<table cellpadding=\"0\" cellspacing=\"0\" class=\"basicInfo\">",
"						<tr style=\"background:#22B2AF;\">",
"							<td>修改类型</td>			",
"							<td >学院（研究院）/部处</td>						",
"							<td >系所/科室</td>						",
"							<td >岗位名称</td>",
"							<td>岗位类型</td>",
"							<td >岗位级别</td>",
"							<td >设岗时间</td>",
"							<td> 撤岗时间</td>",
"						</tr>",
"						<tr>",
"							<td><input name='mark' value='#撤除岗位' style='display:none'>撤除岗位</td>",
"							<td>",
"<input name='academy' type=\"text\" class='academy'>",
"							</td>",
"							<td >",
"<input name='department' type=\"text\" class='department'>",
"							</td>",
"							<td >",
"<input name='position_name' type=\"text\" class='position_name'>",
"							</td>",
"							<td >",
"<input name='position_type' type=\"text\" class='position_type'>",
"							</td>",
"							<td >",
"<input name='position_level' type=\"text\" class='position_level'>",
"							</td>",
"							<td><input class='update_time' name='update_time' type=\"text\" placeholder='格式xxxx-xx-xx'\"></td>",
"							<td><input name='delPositionDay' type=\"text\" placeholder='格式xxxx-xx-xx'\"></td>",
"							<td style='display:none'><input class='position_id' name='position_id' value=''></td>",
"							<td style='display:none'><input name='end_mark' value='table_end'></td>",
"						</tr>			",
"					</table>",
"<input type='button' onclick='check_data(this)' value='检测被修改岗位的正确性'>",
"<div class=\'check_result\' style=\'color:red\'>",
"</div>",
"<div style='height:15px'></div>",
"<div style=\"  border: 1px dashed; margin-bottom: 10px\"></div>",
"</div>"].join("");
var str3=["<span class='del_data' onclick='beclicked(this)'>点击删除下面这一条数据</span>",
"<div><p>为了找到撤除的岗位，请在设岗时间处填写要撤除岗位的设岗时间，岗位的实际撤岗时间请在撤岗时间中填写，如果不填则默认 = 发文时间 </p>",
"<table cellpadding=\"0\" cellspacing=\"0\" class=\"basicInfo\">",
"						<tr id=\"addAndRemove\" style=\"background:#22B2AF;\">",
"							<td>修改类型</td>		",
"							<td>学院（研究院）/部处</td>						",
"							<td>系所/科室</td>						",
"							<td>岗位名称</td>",
"							<td>岗位类型</td>",
"							<td>岗位级别</td>",
"							<td>设岗时间</td>",
"							<td>撤岗时间</td>",
"							<td>职位备注</td>",
"						</tr>",
"						<tr>",
"							<td><input name='mark' value='#撤除从' style='display:none'> 撤除从</td>",
"							<td>",
"<input name='academy' type=\"text\" class='academy'>",
"							</td>",
"							<td >",
"<input name='department' type=\"text\" class='department'>",
"							</td>",
"							<td >",
"<input name='position_name' type=\"text\" class='position_name'>",
"							</td>",
"							<td >",
"<input name='position_type' type=\"text\" class='position_type'>",
"							</td>",
"							<td >",
"<input name='position_level' type=\"text\" class='position_level'>",
"							</td>",

"							<td><input class='update_time' name='update_time' type=\"text\" placeholder='格式xxxx-xx-xx' \"></td>",
"							<td><input name='delPositionDay' type=\"text\" placeholder='格式xxxx-xx-xx' \"></td>",
"							<td>不需要填写</td>",
"							<td style='display:none'><input class='position_id' name='position_id' value=''></td>",
"							",
"						</tr>",
"						<tr>",
"							<td><input name='mark' value='增加到' style='display:none'>增加到</td>",
"							<td>",
"<input name='academy' type=\"text\" class='academy'>",
"							</td>",
"							<td >",
"<input name='department' type=\"text\" class='department'>",
"							</td>",
"							<td >",
"<input name='position_name' type=\"text\" class='position_name'>",
"							</td>",
"							<td >",
"<input name='position_type' type=\"text\" class='position_type'>",
"							</td>",
"							<td >",
"<input name='position_level' type=\"text\" class='position_level'>",
"							</td>",
"							<td><input name='update_time' type=\"text\" placeholder='格式xxxx-xx-xx'\"></td>",
"							<td>不需要填写</td>",
"							<td><input name='positionRemark' type=\"text\" \"></td>",
"							<td style='display:none'><input name='end_mark' value='table_end'></td>",
"						</tr>			",
"					</table>",
"<input type='button' onclick='check_data(this)' value='检测被修改岗位的正确性'>",
"<div class=\'check_result\' style=\'color:red\'>",
"	</div>",
"<div style='height:15px'></div>",
"<div style=\"  border: 1px dashed; margin-bottom: 10px\"></div>",
"</div>"].join("");
var str4=["<span class='del_data' onclick='beclicked(this)'>点击删除下面这一条数据</span>",
"<div>",
"<table cellpadding=\"0\" cellspacing=\"0\" class=\"basicInfo\">",
"						<tr id=\"addAndRemove\" style=\"background:#22B2AF;\">",
"							<td>修改类型</td>		",
"							<td>学院（研究院）/部处</td>						",
"							<td>系所/科室</td>						",
"							<td>岗位名称</td>",
"							<td>岗位类型</td>",
"							<td>岗位级别</td>",
"							<td>设岗时间</td>",
"						</tr>",
"						<tr class=\"parent_mid\">",
"							<td><input name='mark' value='#修改前' style='display:none'> 修改前</td>",
"							<td>",
"<input name='academy' type=\"text\" class='academy' onchange='synchronous_data(this,this.value,1)'>",
"							</td>",
"							<td >",
"<input name='department' type=\"text\" class='department' onchange='synchronous_data(this,this.value,2)'>",
"							</td>",
"							<td >",
"<input name='position_name' type=\"text\" class='position_name'>",
"							</td>",
"							<td >",
"<input name='position_type' type=\"text\" class='position_type'>",
"							</td>",
"							<td >",
"<input name='position_level' type=\"text\" class='position_level' onchange='synchronous_data(this,this.value,3)'>",
"							</td>",
"							<td><input class='update_time' name='update_time' type=\"text\" placeholder='格式xxxx-xx-xx' \"></td>",
"							<td style='display:none'><input class='position_id' name='position_id' value=''></td>",
"							",
"						</tr>			",
"						<tr class=\"target\">",
"							<td><input name='mark' value='修改成' style='display:none'> 修改成</td>",
"							<td class=\"academy\">",
"								上方确定学院（研究院）/部处",
"							</td>",
"							<td class=\"department\">",
"								上方确定系所/科室",
"							</td>",
"							<td >",
"<input name='position_name' type=\"text\" class='position_name'>",
"							</td>",
"							<td >",
"<input name='position_type' type=\"text\" class='position_type'>",
"							</td>",
"							<td class=\"positionlevel\">",
"								上方确定岗位级别",
"							</td>",
"							<td><input name='update_time' type=\"text\" placeholder='格式xxxx-xx-xx' \"></td>",

"							<td style='display:none'><input name='end_mark' value='table_end'></td>",
"						</tr>			",
"					</table>",
"<input type='button' onclick='check_data(this)' value='检测被修改岗位的正确性'>",
"<div class=\'check_result\' style=\'color:red\'>",
"	</div>",
"<div style='height:15px'></div>",
"</div>"].join("");
	</script>
</body>
</html>