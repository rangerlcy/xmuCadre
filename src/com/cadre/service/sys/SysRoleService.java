package com.cadre.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.pojo.RoleResource;
import com.cadre.pojo.SysRole;
import com.cadre.pojo.UserRole;

@Service
public class SysRoleService {
	private Logger logger = LogManager.getLogger(SysRoleService.class);
	@Autowired
	BaseDao<SysRole> sysRoleDao;
	@Autowired
	BaseDao<RoleResource> roleResourceDao;
	@Autowired
	BaseDao<UserRole> userRoleDao;
	/**
	 * 根据条件统计
	 * @param propName
	 * @param value
	 * @return
	 */
	public int queryCountByProp(String propName, Object value){
		String sql = "select count(*) from SysRole as model where model."+propName+"= ?";
		return sysRoleDao.findforCount(sql,new Object[]{value});
	}
	
	/**
	 * 根据id和code统计
	 * @param id
	 * @param code
	 * @return
	 */
	public int queryCountByIdAndCode(Integer id,String code){
		String hql = "select count(*) from SysRole  where id<>? and code=?";
		return sysRoleDao.findforCount(hql,new Object[]{id,code});
	}
	
	/**
	 * 保存角色对象
	 * @param role
	 */
	public void saveSysRole(SysRole role) {
		if(role == null) {
			logger.error("要保存的角色对象不能为null");
			throw new NullPointerException("要保存的角色对象不能为null");
		};
		if(StringUtils.isBlank(role.getName())) {
			logger.error("角色对象不符合保存条件：角色名不能为空");
			throw new IllegalArgumentException("角色对象不符合保存条件：角色名不能为空");
		}
		//保存
		if(role.getId() == null) {
			sysRoleDao.save(role);
		} else {
			sysRoleDao.update(role);
		}
	}
	
	/**
	 * 修改角色名
	 * @param roleId
	 * @param roleName
	 */
	public void updateSysRoleName(Integer roleId,String roleName) {
		if(StringUtils.isBlank(roleName)) {
			logger.error("角色名不能为空");
			throw new IllegalArgumentException("角色名不能为空");
		}
		SysRole role = new SysRole();
		role.setId(roleId);
		role.setName(roleName);
		sysRoleDao.update(role);
	}
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void delSysRole(Integer roleId) {
		//删除角色表中的角色信息
		sysRoleDao.delete(SysRole.class, roleId);
		
		//删除角色资源表中对应的记录
		roleResourceDao.executeUpdateQuery("delete RoleResource where roleId=?", new Object[]{roleId});
		
		//删除用户角色表中对应的记录
		userRoleDao.executeUpdateQuery("delete UserRole where roleId=? ", new Object[]{roleId});
	}
	
	
	/**
	 * 根据主键查询角色信息
	 * @param roleId
	 * @return
	 */
	public SysRole queryById(Integer roleId){
		return sysRoleDao.findOne(SysRole.class, roleId);
	}
	
	/**
	 * 查询出所有角色列表
	 * @param role
	 * @return
	 */
	public List<SysRole> queryAll() {
		String hql = " from SysRole ";
		return sysRoleDao.queryList(hql, null);
	}
	
	/**
	 * 根据条件查询出角色列表
	 * @param role
	 * @return
	 */
	public List<SysRole> queryList(String queryStr) {
		String hql = generateQueryExample(queryStr);
		return sysRoleDao.queryList(hql, null);
	}
	
	/**
	 * 分页查询角色列表
	 * @param role
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public Page<SysRole> queryPage(String queryStr, Integer currPage, int pageSize) throws Exception {
		String hql = generateQueryExample(queryStr);
		//返回查询结果
		Page<SysRole> page = new Page<SysRole>();
		List<SysRole> result = sysRoleDao.findForPage(hql+" order by id desc",null, currPage, pageSize);
		int totalSize = sysRoleDao.findforCount("select count(*) "+hql,null);	
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	/**
	 * 封装查询条件
	 * @param role
	 * @return
	 */
	private String generateQueryExample(String queryStr) {
		String hql = " from SysRole ";
		if(StringUtils.isNotBlank(queryStr)) {
			hql += " where name like '%"+queryStr+"%' or remark like '%"+queryStr+"%'";
		}
		return hql;
	}

	public List<SysRole> queryRoleListByUserId(Integer userId) {
		List<SysRole> sysRoleList = new ArrayList<SysRole>();
		String userRoleHql = " from UserRole as ur left join fetch ur.user as su where su.id=?";
		List<UserRole> userRoleList = userRoleDao.queryList(userRoleHql, new Object[]{userId});
		for(UserRole ur : userRoleList){
			sysRoleList.add(ur.getSysRole());
		}
		return sysRoleList;
	}

}
