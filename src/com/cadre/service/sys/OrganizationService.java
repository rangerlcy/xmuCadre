package com.cadre.service.sys;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.Organization;
import com.cadre.pojo.Paper;
import com.cadre.pojo.User;


@Service
public class OrganizationService {

	@Autowired
	BaseDao<Organization> organizationDao;
	@Autowired
	BaseDao<Paper> paperDao;
	
	//分页查询出所有的单位
	public Page<Organization> findAcademyByPage(String queryStr,
			Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		Object[] args = null;
		String hqlString="from Organization as org";
		String where = " (org.parentCode is '01') and (delFlag is null or delFlag = 1) order by -org.sequence desc";
		hqlString = hqlString + " where "+where;
		Page<Organization> page = new Page<Organization>();
		List<Organization> result = organizationDao.findForPage(hqlString,args,currPage,pageSize);
		int totalSize = paperDao.findforCount("select count(*) "+hqlString,args); 
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	//分页查询出某单位下的所有岗位
	public Page<Organization> findDepartmentByPage(String queryStr,
			Integer currPage, int pageSize , String code) throws Exception {
		// TODO Auto-generated method stub
		Object[] args = null;
		String hqlString="from Organization as org";
		String where = " (org.parentCode is '"+code+"') and (delFlag is null or delFlag = 1) order by -org.sequence desc";
		hqlString = hqlString + " where "+where;
		Page<Organization> page = new Page<Organization>();
		List<Organization> result = organizationDao.findForPage(hqlString,args,currPage,pageSize);
		int totalSize = paperDao.findforCount("select count(*) "+hqlString,args); 
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	/**
	 * 根据条件统计
	 * @param propName
	 * @param value
	 * @return
	 */
	public int getCount(String propName, Object value){
		String sql = "select count(*) from Organization as model where model."+propName+"= ? and (delFlag is null or delFlag = 1)";
		return organizationDao.findforCount(sql,new Object[]{value});
	}
	
	/**
	 * 查询院（处），即parentCode=01
	 * @return
	 */
	public List<Organization> getDepartment(){
		return organizationDao.queryList(" from Organization where parentCode='01' and (delFlag is null or delFlag = 1)",new Object[]{});
	}
	
	/**
	 * 查询出全部的组织机构
	 * @return
	 */
	public List<Organization> queryAll(){
		String hql = " from Organization";
		return organizationDao.queryList(hql, null); 
	}
	
	/**
	 * 根据机构代码查询出组织信息
	 * @param id
	 * @return
	 */
	public Organization queryByCode(String code) {
		String hql = " from Organization where code=? and (delFlag is null or delFlag = 1)";
		List<Organization> organizationList = organizationDao.queryList(hql, new Object[]{code});
		if(organizationList.size() > 0){
			return organizationList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据编码查出下一级组织
	 * @return
	 */
	public List<Organization> queryChildByCode(String code) {
		String hql = " from Organization where parentCode=? and (delFlag is null or delFlag = 1) ";
		return organizationDao.queryList(hql, new Object[]{code});
	}
	
	/**
	 * 根据主键查询出组织信息
	 * @param id
	 * @return
	 */
	public Organization queryById(Integer id) {
		return organizationDao.findOne(Organization.class, id);
	}
	
	/**
	 * 根据编码查名称
	 * @return
	 */
	public String queryNameByCode(String code) {
		String hql = " from Organization where code=? and (delFlag is null or delFlag = 1) ";
		List<Organization> organizations = organizationDao.queryList(hql, new Object[]{code});
		if(organizations.size() == 0){
			return "";
		}else{
			Organization org = organizations.get(0);
			return org.getName();
		}
	}

	public void save(Organization organization) {
		// TODO Auto-generated method stub
		if (null == organization) return;
		organizationDao.save(organization);
	}
	/**
	 * （伪）删除组织机构
	 * @param organization
	 */
	@Transactional
	public void delete(Organization organization) {
		if (null == organization) return;
		if (getCount("parentCode", organization.getCode())>0){//删除所有子组织机构
			List<Organization> oList = queryChildByCode(organization.getCode());
			for (Organization org : oList){
				delete(org);
			}
		}
		organization.setDelFlag(0);
		organizationDao.update(organization);
	}
	/**
	 * 更新组织机构
	 * @param resources
	 */
	public void update(Organization organization) {//不更新Code
		if (null == organization) return;
		if (null == organization.getId()) return;
		organizationDao.update(organization);
	}
	/**
	 * 根据id找到单位岗位
	 * @param id
	 * @return
	 */
	public Organization findOrgById(Integer id) {
		// TODO Auto-generated method stub
		return organizationDao.findOne(Organization.class,id);
	}
	
}
