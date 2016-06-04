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
<div class="form_head"><span>字典管理</span></div>
<div class="changeInfoLeft">
	<c:forEach items="${dictionaries }" var="dic">
		<c:if test = "${dic.parentCode eq 'init' }">
			<div  data="${dic.code }" class="parentDic">
				<label class = "topElement">${dic.remark }</label> <span class = "save" title="添加">+</span> 
			</div>
		</c:if>
	</c:forEach>
</div>
	

<script>
	$(document).ready(function(){
		//只允许编辑子级元素
		//创建二级元素
		$(".save").click(function(){
			var pcode = $(this).parent().attr("data");
			var name = $(this).parent().children("label").text();
			art.dialog.open(
					"${ctx}/sys/dictionary/preOpenItemEdit.do?pid="+pcode,
					{title:"【"+name+"】-新增条目", lock: true, width: 480, height: 250}
					);
			/*$.post("${ctx}/sys/dictionary/save.do",{parentCode:$(this).parent().attr("data") , value:$("#create_value").val()},function(){
				alert("Save Ok!");	
			});*/
		});
		
		$(".topElement").click(function(){
			//列举二级元素
			var temp = $(this).parent();							
			var code=$(this).parent().attr("data");
			if($(this).parent().find(".outputList").size() >= 1 ){
				if($(this).parent().find(".outputList").css("display") == "none")
					$(this).parent().find(".outputList").fadeIn("slow");
				else
					$(this).parent().find(".outputList").hide();
				return;
			}
			$.post("${ctx}/sys/dictionary/queryChildByCode.do",{code : code},function(result){
				var dic,html="<div class='outputFather'>";
				$(".outputList").each(function(){$(".outputList").remove();});
				html+="<div class='outputList' style='display:none'>";
				for (var i= 0; i < result.length; ++i){
					dic = result[i];
					html+="<div data='"+dic.code+"'><label style='margin-left:20px;'>"+dic.value+
						"</label> <button class = \"update\" style='margin-right:30px;'>编辑</button> <button class = \"delete\" style='margin-left:20px;'>删除</button><hr /></div>";								
				}
			    html+="</div></div>";
				$(temp).append(html);
				$(temp).find(".outputList").fadeIn("slow");
				//编辑二级元素
				$(".update").click(function(){				
					var dic,updateSection='';
					if($(this).parent().find("#toUpdate").size() >= 1){
						$(this).parent().find("#toUpdate").remove();
						return;
					}
					$("#toUpdate").remove();							
					updateSection+='<div id="toUpdate" style="width:70%"><label style="margin-left:20px;">请输入：</label><input id="update_value" type="text"><button id="update_confirm" style="margin-left:10px;">确定</button><hr/></div>';
					$(this).parent().append(updateSection);							//以上控制输入组件的显示与隐藏
					var code = $(this).parent().attr("data");
					$.post("${ctx}/sys/dictionary/queryByCode.do",{code : code},function(result){   //从服务器获取得到现在这一行的数据
						dic = result;
						$("#update_value").val(dic.value);											//将原来的值显示在输入框中
						$("#update_confirm").click(function(){										//点击“确定”按键，触发更新
							$.post("${ctx}/sys/dictionary/update.do",{id : dic.id,value:$("#update_value").val()},function(){
								art.dialog.tips("成功");
								setTimeout(function(){self.location.reload();},1000);
							});
						});
					},"json");																		//到此更新完成
				});
				
				//删除二级元素
				$(".delete").click(function(){
					var dic;
					var code = $(this).parent().attr("data");
					var thiz = $(this);
					art.dialog.confirm("确定要删除？删除之后将不可回复",function(){
						$.post("${ctx}/sys/dictionary/queryByCode.do",{code : code},function(result){
							dic = result;
							//alert(dic.id+dic.code);
							$.post("${ctx}/sys/dictionary/delete.do",{id : dic.id},function(result1){
								thiz.parent().remove();
								art.dialog.tips("删除成功");
								
							},"text");
						},"json");
					});
					
					
				}); 

			},"json");
		});
	});
	
</script>

</body>
</html>