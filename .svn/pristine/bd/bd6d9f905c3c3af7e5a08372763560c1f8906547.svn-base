<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>厦门大学干部信息管理系统</title>
		<script  type="text/javascript" src="${ctx}/resources/js/artDialog/artDialog.js?skin=blue"></script>
		<link rel="stylesheet" href="${ctx }/resources/js/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${ctx }/resources/js/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx }/resources/js/lib/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
		<script type="text/javascript">
		  var zNodes =[
		               <c:forEach items="${allResources}" var="resources">
		               {id:"${resources.id }", pId:"${resources.parentId }", name:"${resources.name}", open:true,dropInner:true},
		               </c:forEach>
		           ];
		  var setting = {
				  view: {
		              addHoverDom: addHoverDom,
		              removeHoverDom: removeHoverDom,
		              selectedMulti: false
		          },
				  data: {
		              simpleData: {
		                  enable: true,
		                  idKey: "id",
		                  pIdKey: "pId",
		                  rootPId: 0
		              }
		          },
		          edit: {
		              enable: true,
		              editNameSelectAll: true,
		              showRemoveBtn: showRemoveBtn,
		              showRenameBtn: false
		          },
				  callback: {
		              beforeClick: beforeClick,
		              beforeRemove: beforeRemove
		          }
		      };
			
			 $(document).ready(function(){
			     $.fn.zTree.init($("#tree"), setting, zNodes);
			 });
		
			 var newCount = 1;
			 function addHoverDom(treeId, treeNode) {
				 //不允许添加太深的层级
				 if(treeNode.level >= 6) return;
				 
		         var sObj = $("#" + treeNode.tId + "_span");
		         if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		         var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		             + "' title='add node' onfocus='this.blur();'></span>";
		         sObj.after(addStr);
		         var btn = $("#addBtn_"+treeNode.tId);
		         if (btn) btn.bind("click", function(){
		        	 var resouces = {
		        			 parentId:treeNode.id,
		        			 resourceType:1,
		        			 name:"新增资源"+(newCount++),
		        	 };
		        	 $.post("${ctx}/sys/resource/addSysResources.do",resouces,function(result){
		        		 var arr = result.split("#");
		                 if("success" == arr[0]) {
		                	 getTreeObj().addNodes(treeNode, {id:arr[1], pId:resouces.parentId, name:resouces.name});
		                 } else {
		                     art.dialog.alert("添加资源失败");
		                 }
		             }); 
		             return false;
		         });
		     };
		     
		     function beforeClick(treeId, treeNode) {
		         var zTree = $.fn.zTree.getZTreeObj(treeId);
		         if (treeNode.isParent) {
		             zTree.expandNode(treeNode);
		         }
		         
		         $("#resourcesIframe").attr("src","${ctx}/sys/resource/preSysResourcesUpdate.do?id="+treeNode.id);
		            return true;
		     }
		     //删除节点前做一些前期准备
		     function beforeRemove(treeId,treeNode) {
		    	 
		    	 if(treeNode.children == null || treeNode.children.length==0){
		    		 art.dialog.confirm("确定删除资源？",function(){
		    			 var node = treeNode;
		    			 var resouces = {
		                         id:treeNode.id
		                     }
		                     $.post("${ctx}/sys/resource/delSysResources.do",resouces,function(result){
		                         if("success" != result) {
		                             art.dialog.alert("删除资源失败");
		                         } else {
		                        	 getTreeObj().removeNode(node,false);
		                         }
		                     }); 
		    		 });
		    		 
		    		 return false;
		    	 } else {
		    		 art.dialog.alert("删除失败，请确认没有子节点后再删除.");
		    		 return false;
		    	 }
		     }
		     
		     //鼠标离开节点时，去除删除节点样式
		     function removeHoverDom(treeId, treeNode) {
		    	 $("#addBtn_"+treeNode.tId).unbind().remove();
		     };
		     function updateNodeName(id,newName) {
		    	 var node = getNode(id);
		    	 if(node != null) {
			    	 node.name = newName;
			    	 getTreeObj().updateNode(node,false);
		    	 }
		     }
		     
		     function getNode(id) {
		         return getTreeObj().getNodeByParam("id", id, null);
		     }
		     function getTreeObj(){
		    	 return $.fn.zTree.getZTreeObj("tree");
		     }
		     
		     function showRemoveBtn(treeId, treeNode) {
		    	 return treeNode.pId != 0;
		     }
		     
		     $(function(){
		    	 $("#container").height($(window).height()-20);
		     })
		  </script>
	</head>

	<body>
	    <div id="container" style="width: 100%;height:100%;">
	        <div style="width:280px;height:100%;BORDER-RIGHT: #999999 1px dashed;float: left;">
	            <ul id="tree" class="ztree" style="width:100%; height:100%;overflow: auto;"></ul>
	        </div>
	        <div style="width:70%;height:100%;float:left;">
	            <iframe ID="resourcesIframe" Name="resourcesIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100% height=100% SRC=""></iframe>
	        </div>
	    </div>
	</body>
</html>