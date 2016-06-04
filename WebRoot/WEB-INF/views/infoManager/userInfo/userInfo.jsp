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
		<link rel="stylesheet" href="${res }/css/ny.css" type="text/css"  />
		<script type="text/javascript" src="${res}/js/DatePicker/WdatePicker.js"></script>
  </head>
<body>
		<div class="tb_box">
			<div class="requiredInfo">
				<div class="reTitle"><strong>基本信息</strong></div>
				<table class="basicInfo" cellspacing="0">
					<tr><td>姓名</td><td ><label id="name">${userDetail.user.name }</label></td></tr>
					<tr><td>员工号</td><td ><label id="number">${userDetail.user.number }</label></td></tr>
					<tr><td>身份类型标签</td><td><label id="identityTypeLabel">${ide:getIdentityTypeLabelName(userDetail.user.identityTypeLabel) }</label></td></tr>
					<tr><td>证件号</td><td><label id="identifyNum">${userDetail.user.identifyNum }</label></td></tr>
					<tr><td>性别</td><td><label id="gender">${userDetail.user.gender }</label></td></tr>
					<tr><td>民族</td><td><label id="nation">${ide:getEthnicityName(userDetail.user.nation)}</label></td></tr>
					<tr><td>出生地</td><td>
							<label id="birthPlaceProv">${bproc }</label>
						</td></tr>
					<tr><td>籍贯</td><td >
							<label id="originPlaceProv">${oproc }</label>
						</td></tr>
					<tr><td>出生日期</td><td><label id="birthday"><fmt:formatDate value="${userDetail.user.birthDay }" pattern='yyyy-MM-dd'/></label></td></tr>
					<tr><td>核对情况</td><td><label id="checkCase">${userDetail.user.checkCase }</label></td></tr>
				</table>
			</div>
			<div class="extenceInfo">
				<div class="exTitle"><strong>扩展信息</strong></div>
				<table class="exInfo" cellspacing="0">
					<tr><td>参加工作时间</td><td><label id="workBeginDay"><fmt:formatDate value="${userDetail.user.beginWorkDay }" pattern='yyyy-MM-dd'/></label></td></tr>
					<tr><td>来校工作时间</td><td><label id="beginSchoolWorkDay"><fmt:formatDate value="${userDetail.user.beginSchoolWorkDay }" pattern='yyyy-MM-dd'/></label></td></tr>
					<tr><td>退休时间</td><td><label id="retireDay"><fmt:formatDate value="${userDetail.user.retireDay }" pattern='yyyy-MM-dd'/></label></td></tr>
					<tr><td>研究方向</td><td><label id="researchDirection">${userDetail.user.researchDirection }</label></td></tr>
					<tr><td>党派</td><td><label id="party">${ide:getPolicitalStatusName(userDetail.user.party) }</label></td></tr>

					<tr><td>参加党派时间</td><td><label id="joinPartyDay"><fmt:formatDate value="${userDetail.user.joinPartyDay }" pattern='yyyy-MM-dd'/></label></td></tr>
					<tr><td>当前人员级别</td><td>${ide:getPositionLevel(userDetail.user.level) }</td></tr>
					<tr><td>照片</td><td><c:if test="${userDetail.user.photoUrl !=null and userDetail.user.photoUrl !='' }">
    						<c:set var="photoUrls" value="${fn:split(userDetail.user.photoUrl,',') }"></c:set>
    						<c:forEach items="${photoUrls }" var="photoUrl">
    							<label id="photo"><img id="photoImg" src="${ctx}${photoUrl }?1=2" style="width:100px" /></label>
    							
    						</c:forEach>
    						<c:set var="photoCheck" value="" />
    					</c:if></td></tr>
					<tr><td>健康状况</td><td><label id="healthStatus">${userDetail.user.healthStatus }</label></td></tr>
					<tr><td>办公电话</td><td><label id="contectWorkPhone">${userDetail.user.contectWorkPhone }</label></td></tr>
					<tr><td>移动电话</td><td><label id="contectMobilePhone">${userDetail.user.contectMobilePhone }</label></td></tr>
					<tr><td>电子邮箱</td><td><label id="contectEmail">${userDetail.user.contectEmail }</label></td></tr>
					<tr><td>备注</td><td><label id="remark">${userDetail.user.remark }</label></td></tr>
				</table>
			</div>
			<div class="extenceInfo2">
				<div class="exTitle"><strong>学历信息</strong></div>
				<table class="exInfo" cellspacing="0">
					<tr>
						<td>入学时间</td>
						<td>毕业时间</td>
						<td>学校</td>
						<td>学位</td>		
					</tr>
					<c:forEach items="${educations}" var="se">
						<tr>
							<td><fmt:formatDate value="${se.beginDay }"  pattern='yyyy-MM-dd' /></td>
							<td><fmt:formatDate value="${se.endDay }"  pattern='yyyy-MM-dd' /></td>
							<td>${se.school }</td>							
							<td>${se.degree}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="extenceInfo2">
				<div class="exTitle"><strong>家庭信息</strong></div>
				<table class="exInfo" cellspacing="0">
					<tr>
						<td>姓名</td>
						<td>称谓</td>
						<td>出生日期</td>
						<td>工作单位</td>
						<td>职务</td>
					</tr>
					<c:forEach items="${relations }" var="fm">			
						<tr>
							<td>${fm.name }</td>
							<td>${fm.appellation }</td>
							<td><fmt:formatDate value='${fm.birthDay }'  pattern='yyyy-MM-dd' /></td>
							<td>${fm.units }</td>
							<td>${fm.job }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="extenceInfo2">
				<div class="exTitle"><strong>干部培训信息</strong></div>
				<table class="exInfo" cellspacing="0">
					<tr>
						<td>开始时间</td>
						<td>结束时间</td>
						<td>教育培训名称</td>
						<td>教育培训期数</td>
						<td>主办单位</td>
						<td>培训地点</td>
								
					</tr>
					<c:forEach items="${trains}" var="tn">
						<tr>
							<td><fmt:formatDate value="${tn.beginDay }"  pattern='yyyy-MM-dd' /></td>
							<td><fmt:formatDate value="${tn.endDate }"  pattern='yyyy-MM-dd' /></td>
							<td>${tn.trainingName }</td>
							<td>${tn.trainingPeriod }</td>							
							<td>${tn.organizer }</td>
							<td>${tn.trainingPlace }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="extenceInfo2">
				<div class="exTitle"><strong>干部挂职信息</strong></div>
				<table class="exInfo" cellspacing="0">
					<tr>
						<td>挂职项目名称</td>
						<td>挂职开始时间</td>
						<td>挂职结束时间</td>
						<td>挂职人员要求</td>
								
					</tr>
					<c:forEach items="${secondments}" var="sm">
						<tr>
							<td>${sm.temporaryProjectName}</td>
							<td><fmt:formatDate value="${sm.temporaryBeginDay}"  pattern='yyyy-MM-dd' /></td>
							<td><fmt:formatDate value="${temporaryEndDate}"  pattern='yyyy-MM-dd' /></td>						
							<td>${tn.temporaryRequirement }</td>

						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="extenceInfo2">
				<div class="exTitle"><strong>干部任免情况</strong></div>
				<table class="exInfo" cellspacing="0">
					<tr>
						<td>任免或者免职</td>
						<td>任免类型</td>
						<td>任免原因</td>
						<td>任免级别/高聘级别</td>
								
					</tr>
					<c:forEach items="${postUsers}" var="pu">
						<tr>
							<td>${pu.appointOrRemove}</td>
							<td>${pu.type}</td>							
							<td>${pu.reasion}</td>
							<td>${pu.grade}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="tb_sub_btns">
				<input type="button" value="关闭" class="cancel" onClick="art.dialog.close()" />
			</div>
		</div>
</body>
</html>