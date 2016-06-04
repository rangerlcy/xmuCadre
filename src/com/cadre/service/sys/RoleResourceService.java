package com.cadre.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.RoleResource;
import com.cadre.pojo.SysResource;
import com.cadre.pojo.SysRole;
import com.cadre.pojo.User;
import com.cadre.pojo.UserRole;

@Service
public class RoleResourceService {
	@Autowired
	BaseDao<SysRole> sysRoleDao;
	@Autowired
	BaseDao<SysResource> sysResourceDao;
	@Autowired
	BaseDao<User> userDao;
	@Autowired
	BaseDao<RoleResource> roleResourceDao;
	@Autowired
	BaseDao<UserRole> userRoleDao;
	
	/**
	 * 此方法要小心使用，对于数据量不多及操作不频繁的业务可以这么做，否则这么处理会造成大量的数据库请求。
	 * 添加角色对应的资源
	 * @param roleId
	 * @param resourcesIds
	 */
	public void addRoleResources(int roleId,List<Integer> resourcesIds){
		if(resourcesIds == null || resourcesIds.isEmpty()) return;
		if(roleId<=0) {
			throw new IllegalArgumentException("参数错误,角色Id不可能小于0");
		}
		
		List<RoleResource> roleResourcesList = new ArrayList<RoleResource>();
		for(Integer resourceId : resourcesIds) {
			if(resourceId == null) continue;
			SysRole sysRole = sysRoleDao.findOne(SysRole.class, roleId);
			SysResource sysResource = sysResourceDao.findOne(SysResource.class, resourceId);
			RoleResource rr = new RoleResource();
			rr.setSysRole(sysRole);
			rr.setSysResource(sysResource);
			roleResourcesList.add(rr);
		}
		
		addRoleResources(roleResourcesList);
		
	}

	
	/**
	 * 添加角色对应的资源
	 * 此方法要小心使用，对于数据量不多及操作不频繁的业务可以这么做，否则这么处理会造成大量的数据库请求。
	 * @param roleResourcesList
	 */
	public void addRoleResources(List<RoleResource> roleResourceList) {
		if(roleResourceList == null || roleResourceList.isEmpty()) return;
		for(RoleResource record : roleResourceList) {
			System.out.println(record.getSysResource().getUrl());
			System.out.println(record.getSysRole().getName());
			roleResourceDao.save(record);
		}
	}
	
	
	
	/**
	 * 更新角色所拥有的资源:
	 * 	1、删除原来的。
	 * 	2、插入新的
	 * @param roleId
	 * @param resourcesIds
	 */
	public void updateRoleResource(int roleId,List<Integer> resourcesIds){
		//删除角色原来拥有的资源
		roleResourceDao.executeUpdateQuery("delete RoleResource where roleId=?", new Object[]{roleId});
		//给角色分配新资源
		addRoleResources(roleId,resourcesIds);
	}

	
	/**
	 * 查询出角色已经拥有的资源
	 * @param roleId
	 * @return
	 */
	public List<RoleResource> queryRoleResources(int roleId) {
		String hql = " from RoleResource as rr left join fetch rr.sysRole as sr left join fetch rr.sysResource as sResource  where sr.id=?";
		return roleResourceDao.queryList(hql, new Object[]{roleId});
	}
}
