package com.cadre.service.sys;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.RoleResource;
import com.cadre.pojo.SysResource;
import com.cadre.pojo.UserRole;
@Service
public class SysResourceService {
	private Logger logger = LogManager.getLogger(SysResourceService.class);
	@Autowired
	BaseDao<SysResource> sysResourceDao;
	@Autowired
	BaseDao<RoleResource> roleResourceDao;
	@Autowired
	BaseDao<UserRole> userRoleDao;
	/**
	 * 查询出全部的菜单资源
	 * @return
	 */
	public List<SysResource> queryAll(){
		String hql = " from SysResource order by orderNum ";
		return sysResourceDao.queryList(hql, null); 
	}
	
	/**
	 * 根据主键查询出资源信息
	 * @param id
	 * @return
	 */
	public SysResource queryById(Integer id) {
		return sysResourceDao.findOne(SysResource.class, id);
	}

	/**
	 * 保存系统资源
	 * @param resources
	 */
	public int saveSysResources(SysResource resources) {
		if(resources == null 
				|| resources.getParentId() == null 
				|| StringUtils.isBlank(resources.getName())){
			logger.error("资源信息的父id,名称不能为空");
			throw new IllegalArgumentException("资源信息的父id,名称不能为空");
		}
		
		if(resources.getId() != null) {
			sysResourceDao.update(resources);
		} else {
			sysResourceDao.save(resources);
		}
		return resources.getId();
	}

	/**
	 * 根据主键删除系统资源
	 * @param id
	 */
	public void delSysResourcesById(Integer id) {
		//先删除资源菜单中的内容
		sysResourceDao.delete(SysResource.class, id);
		//再删除角色资源关联表中的内容
		roleResourceDao.executeUpdateQuery("delete RoleResource where resourceId=? ", new Object[]{id});
	}

	/**
	 * 查出用户拥有的所有资源（权限）
	 * @param userId
	 * @return
	 */
	public List<SysResource> queryUserAllRessources(Integer userId) {
		List<SysResource> sysResourceList = new ArrayList<SysResource>();
		String userRoleHql = " from UserRole ur left join fetch ur.sysRole as sr left join fetch ur.user su  where su.id=?";
		List<UserRole> userRoleList = userRoleDao.queryList(userRoleHql, new Object[]{userId});
		StringBuffer sb = new StringBuffer();
		for(UserRole ur : userRoleList){
			sb.append(ur.getSysRole().getId()+",");
		}
		String ids = sb.toString();
		if(StringUtils.isNotBlank(ids) && ids.length() > 0){
			ids = ids.substring(0, ids.length()-1);
			String resourceHql = "select distinct sr from SysResource sr left join fetch sr.roleResource as rr left join fetch rr.sysRole as sRole where sRole.id in("+ids+")  order by sr.orderNum ";
			List<SysResource> srList = sysResourceDao.queryList(resourceHql, null);
			for(SysResource sr : srList){
				sysResourceList.add(sr);	
			}
		}
		return sysResourceList;
	}
}
