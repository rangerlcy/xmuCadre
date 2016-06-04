<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/css/ny.css" />
		<link rel="stylesheet" href="${ctx }/resources/js/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${ctx }/resources/js/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx }/resources/js/lib/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<script type="text/javascript">
		  var zNodes =[
		               <c:forEach items="${treeNodeList}" var="tnVO">
		                {id:"${tnVO.id }", pId:"${tnVO.pId }", name:"${tnVO.name}", open:false, nocheck:${tnVO.nocheck},checked:${checkedMap[tnVO.id]}},
		               </c:forEach>
		           ];
		  var setting = {
					view: {
					    selectedMulti: false
					},
					check: {
					    chkboxType : { "Y" : "s", "N" : "s" },
						enable: true,
					    chkStyle: "checkbox",
					    autoCheckTrigger: true
					},
					data: {
						simpleData: {
						    enable: true,
						    idKey: "id",
						    pIdKey: "pId",
						    rootPId: 0
						}
					}
		      };
			
			 $(function(){
			     $.fn.zTree.init($("#tree"), setting, zNodes);
			    // getTreeObj().expandAll(true);
			     $("#saveBtn").click(save);
			 });
		
			 
		     function getTreeObj(){
		    	 return $.fn.zTree.getZTreeObj("tree");
		     }
		     
		     //保存角色资源
		     function save(){
		    	 var nodes = getTreeObj().getCheckedNodes(true);
		    	 var userIds = [];
		    	 for(var i=0;i<nodes.length;i++) {
		    		 userIds.push(nodes[i].id);
		    	 }
		    	 var data = {
		    			 roleId:"${roleId}",
		    			 userIds:userIds.join(",")
		    	 };
		    	 $.post("${ctx}/sys/role/updateRoleUsers.do",data,function(result){
		             if("success" == result) {
		                art.dialog.close();
		             } else {
		                 $("#saveStauts").html("保存失败");
		             }
		         }); 
		     }
		     
		     
		     
		  </script>
		</head>
		
		<body>
		       <div style="width:98%;height:100%;">
		           <ul id="tree" class="ztree" style="height:100%;overflow: auto;"></ul>
		       </div>
		       <div class="tb_sub_btns" style="padding-bottom: 10px;">
		       <input id="saveBtn" type="button" value="保存" class="add" />
		       <input type="button" value="取消" class="cancel" onclick="art.dialog.close()" />
		       <span id="saveStauts" class="ts">
		       </div>
		       
		</body>	
</html>