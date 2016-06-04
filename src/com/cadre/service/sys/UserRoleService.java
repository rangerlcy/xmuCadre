package com.cadre.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.SysRole;
import com.cadre.pojo.User;
import com.cadre.pojo.UserRole;

@Service
public class UserRoleService {
	private Logger logger = LogManager.getLogger(UserRoleService.class);
	@Autowired
	BaseDao<UserRole> userRoleDao;
	
	@Autowired
	BaseDao<SysRole> sysRoleDao;
	
	@Autowired
	BaseDao<User> userDao;
	/**
	 * 根据人员主键查询该用户包含的角色
	 * @param userId
	 * @return
	 */
	public List<UserRole> queryRoleById(int userId){
		String hql = " from UserRole as ur left join fetch ur.user as su where su.id=?";
		return userRoleDao.queryList(hql, new Object[]{userId});
	}
	
	/**
	 * 根据角色主键查询该角色包含的用户
	 * @param userId
	 * @return
	 */
	public List<UserRole> queryUserByRoleId(int roleId){
		String hql = " from UserRole as ur left join fetch ur.user as su left join fetch ur.sysRole as sr where sr.id=?";
		return userRoleDao.queryList(hql, new Object[]{roleId});
	}
	
	/**
	 * 更新角色所属的用户:
	 * 	1、删除原来的。
	 * 	2、插入新的
	 * @param roleId
	 * @param userIds
	 */
	public void updateRoleUser(int roleId,List<Integer> userIds){
		//删除角色原来拥有的用户
		userRoleDao.executeUpdateQuery("delete UserRole where roleId=? ", new Object[]{roleId});
		
		SysRole sysRole = sysRoleDao.findOne(SysRole.class, roleId);
		
		if(roleId<=0) {
			logger.error("参数错误,角色Id不可能小于0");
			throw new IllegalArgumentException("参数错误,角色Id不可能小于0");
		}
		for(Integer userId : userIds) {
			if(userId == null) continue;
			User user = userDao.findOne(User.class,userId);
			UserRole ur = new UserRole();
			ur.setSysRole(sysRole);
			ur.setUser(user);
			userRoleDao.save(ur);
		}
	}
	
	public void saveRoleUsersBatch(List<User> successList,Integer roleId){
		//删除角色原来拥有的用户
		userRoleDao.executeUpdateQuery("delete UserRole where roleId=? ", new Object[]{roleId});
		
		SysRole sysRole = sysRoleDao.findOne(SysRole.class, roleId);
		List<UserRole> userRoleList = new ArrayList<UserRole>();
		for(User user : successList) {
			//给角色添加新用户
			if(user.getId() == null) continue;
			if(roleId<=0) {
				logger.error("参数错误,角色Id不可能小于0");
				throw new IllegalArgumentException("参数错误,角色Id不可能小于0");
			}
			UserRole ur = new UserRole();
			ur.setSysRole(sysRole);
			ur.setUser(user);
			userRoleList.add(ur);
			userRoleDao.save(ur);
		}
	}
}
