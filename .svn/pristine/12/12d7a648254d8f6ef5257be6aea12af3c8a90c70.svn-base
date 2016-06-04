/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.security.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cadre.security.pojo.LoginUserDetails;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.SysResource;
import com.cadre.pojo.SysRole;
import com.cadre.pojo.User;
import com.cadre.service.sys.DictionaryService;
import com.cadre.service.sys.SysResourceService;
import com.cadre.service.sys.SysRoleService;
import com.cadre.model.utils.StringUtil;

/**
 * @Type: com.cadre.security.service.UserInfoLoadFacade.java
 * @ClassName: UserInfoLoadFacade
 * @Description: 用户登录成功后，资源、权限、角色等信息加载器<br/>
 * 
 */
@Component("userInfoLoadFacade")
public class UserInfoLoadFacade {
	Logger LOG = LogManager.getLogger(UserInfoLoadFacade.class);
	@Autowired
	SysRoleService sysroleService;
	
	@Autowired
	SysResourceService sysResourceService;
	
	@Autowired
	DictionaryService dictionaryService;
	
	/**
	 * 加载用户角色，资源详细信息
	 * @param userDetials
	 */
	protected void loadUserAllInfo(LoginUserDetails currUser) {
    	Integer userId = currUser.getUser().getId();
    	LOG.debug("加载用户角色信息");
    	List<SysRole> roleList = sysroleService.queryRoleListByUserId(userId);
    	
    	LOG.debug("加载用户所有资源信息");
    	List<SysResource> resourceList = null;
    	
    	LOG.debug("加载用户所有字典信息");
    	List<DictionaryDb> dictionaryList = null;
    	
    	dictionaryList = dictionaryService.queryAll();
    	// TODO 管理员加载所有资源
    	if(StringUtils.equalsIgnoreCase("admin", currUser.getUser().getUserName())) {
    		resourceList = sysResourceService.queryAll();
    	} else {
    		resourceList = sysResourceService.queryUserAllRessources(userId);
    	}

    	currUser.setRoles(roleList);
    	currUser.setResources(resourceList);
    	currUser.setDictionarys(dictionaryList);
	}
	
}
