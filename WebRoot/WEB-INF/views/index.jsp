<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/common/common.jsp"%>
	<%@ taglib uri="/WEB-INF/tlds/cadre-resources-tag.tld" prefix="resources" %>
	<title>厦门大学学工信息管理系统</title>
	<link rel="stylesheet" href="${res }/css/style.css" type="text/css"  />
	<script type="text/javascript" src="${res }/scripts/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${res }/scripts/main_hs.js"></script>
  
  	<script type="text/javascript" src="${res }/js/layout_index.js"></script>

</head>

<body>
	<div class="main_head" style="height:128px;">
		<img src="${res }/images/logo.png" alt="厦大学工管理系统" title="厦大学工管理系统" />
		<div class="info">
			<a href="javascript:void(0)" class="user">${sysUser.name }</a>
			<span>您好，欢迎登陆</span>
			<a href="<c:url value="/j_spring_security_logout"/>" class="logout">退出登陆</a>
		</div>
	</div>
	
	<div class="fun_nav">	
			
		<c:forEach items="${resources:getTopResource() }" var="resource" varStatus="status">
			<a topId="${resource.id }" onClass="no1" class="topMenu" href="javascript:;">${resource.name }</a>        
        </c:forEach>	

        <c:forEach items="${resources:getTopResource() }" var="topResource">
        	<div class="fun_a" parentId="${topResource.id }">           	
        		<c:forEach items="${resources:getChildrenMenu(topResource) }" var="childResource">   <!-- 该处子菜单即为干部信息和系统管理 -->
        			<ul class="fun_list hide_list">                    
        			<c:choose>
        				<c:when test="${!resources:haveChildrenMenu(childResource) }">    <!-- 当没有子节点才执行 -->
        					<span class="bottom_menu menu" onclick="FrameURL('${ctx}${childResource.url }',this,'${ctx}')">${childResource.name }</span>  
        				</c:when>
        				<c:otherwise>
        					<span class="menu l_cd">${childResource.name }</span>		 <!-- 该处显示干部信息和系统管理 -->
        					<c:forEach items="${resources:getChildrenMenu(childResource) }" var="thirdLeverResource" varStatus="status">   <!-- 下一层子菜单,即干部信息和系统管理的下级菜单 -->        						
        						<li  <c:if test="${status.index == 0 }"> class="first child_m second_menu_hover" </c:if> <c:if test="${status.index > 0 }"> class="child_m second_menu_hover" </c:if> >
        							<c:choose>
        								<c:when test="${!resources:haveChildrenMenu(thirdLeverResource) }">   <!-- 没有下级菜单,将是一个a标签 -->
        									<span><a class="bottom_menu menu" href="javascript:;" onclick="FrameURL('${ctx}${thirdLeverResource.url }',this,'${ctx}')">${thirdLeverResource.name }</a></span>
        								</c:when>
        								<c:otherwise>
        									<span class="menu l_cd">${thirdLeverResource.name }</span>
        									<ul class="last_menu hide_list">                               <!-- 页面上显示的第三级菜单部分 -->
        										<c:forEach items="${resources:getChildrenMenu(thirdLeverResource) }" var="bottomResource" varStatus="status"> 
        										<li <c:if test="${status.index == 0 }"> class="first child_m" </c:if> <c:if test="${status.index > 0 }"> class="child_m" </c:if> >
        											<span><a class="bottom_menu menu" href="javascript:;" onclick="FrameURL('${ctx}${bottomResource.url }',this,'${ctx}')">${bottomResource.name }</a></span>
        										</li>	
        										</c:forEach>
        									</ul>
        								</c:otherwise>
        							</c:choose>
        						</li>                                  
                             </c:forEach>
        				</c:otherwise>
        			</c:choose>
        			</ul>
        		</c:forEach>				
			</div>
        </c:forEach>
           
	          
		
		<div class="content">
			<iframe id="iframe1" src=""  frameborder="0" scrolling-x="no"></iframe>
		</div>
	</div>
</body>
</html>
