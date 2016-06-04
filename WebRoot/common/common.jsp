<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tlds/cadre-tag.tld" prefix="ide"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<c:set var="res" value="${ctx }/resources"></c:set>
<script  type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<script  type="text/javascript" src="${ctx}/resources/js/jquery.js"></script>
<script type="text/javascript">
	//禁止页面右键菜单
	function disableContextMenu(){
	    $(document).bind("contextmenu",function(e){ 
	        return false;
	    });
	}
</script>