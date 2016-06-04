package com.cadre.service.secondment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.pojo.Secondment;
import com.cadre.pojo.SecondmentUser;
import com.cadre.pojo.User;
@Service

public class SecondmentUserService {
	@Autowired
	BaseDao<SecondmentUser> secondmentUserDao;
	/**
	 * 查找所有用户
	 * @return
	 */
	public List<SecondmentUser> queryAll(){
		String hql = "from SecondmentUser ";
		return secondmentUserDao.queryList(hql,null);
	}
	
	
	public SecondmentUser findById(Integer id){
		return secondmentUserDao.findOne(SecondmentUser.class,id);
	}
	
	private String generateQueryExample(String queryStr) {
		String hql = " from SecondmentUser ";
		return hql;
	}
	/**
	 * 分页查询列表
	 * @param secondment
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public Page<SecondmentUser> queryPage(String queryStr, Integer currPage, int pageSize) throws Exception {
		String hql = generateQueryExample(queryStr);
		//返回查询结果
		Page<SecondmentUser> page = new Page<SecondmentUser>();
		List<SecondmentUser> result = secondmentUserDao.findForPage(hql+" order by id desc",null, currPage, pageSize);
		int totalSize = secondmentUserDao.findforCount("select count(*) "+hql,null);	
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	/**
	 * 批量插入挂职信息
	 * @param SecondmentList
	 * @return
	 */
	@Transactional
	public boolean saveSecondmentUsersBatch(List<SecondmentUser> SecondmentUserList){
		for(SecondmentUser secondmentUser : SecondmentUserList){
			if(secondmentUser.getId() != null){
				secondmentUserDao.update(secondmentUser);
			}else{
				secondmentUserDao.save(secondmentUser);
			}
		}
		return true;		
	}
	
	/**
	 * 保存对象
	 * @param role
	 */
	public void saveSecondmentUser(SecondmentUser secondmentUser) {
		secondmentUserDao.save(secondmentUser);
		}
	/**
	 * 修改对象
	 * @param fm
	 */
	public void updateSecondmentUser(SecondmentUser secondmentUser){
		
		secondmentUserDao.update(secondmentUser);
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delSecondmentUser(Integer id) {
		
		secondmentUserDao.delete(SecondmentUser.class, id);
	}


	public SecondmentUser findBySecondmentAndUser(Secondment secondment, User user) {
		// TODO Auto-generated method stub
		if (null == secondment || null == user) return null;
		String sql = "from SecondmentUser where (delFlag is null or delFlag = 1) and secondment = ? and user = ?";
		List<SecondmentUser> secondmentUsers = secondmentUserDao.queryList(sql, new Object[]{secondment,user});
		if (0 == secondmentUsers.size()) 
			return null;
		return secondmentUsers.get(0);
	}


	public List<SecondmentUser> findBySecondment(Secondment secondment) {
		List<SecondmentUser> secondmentUsers = new ArrayList<SecondmentUser>();
		if (null == secondment) return secondmentUsers;
		String sql = "from SecondmentUser where (delFlag is null or delFlag = 1) and secondment = ?";
		secondmentUsers = secondmentUserDao.queryList(sql, new Object[]{secondment});
		return secondmentUsers;
	}


	public void delSecondmentUserByUserId(Secondment secondment, Integer userId) {
		// TODO Auto-generated method stub
		if (null == userId || secondment == null || secondment.getId() == null) return;
		try {
			String sql = "from SecondmentUser where secondment = ? and user.id = ?";
			List<SecondmentUser> secondmentUsers =  secondmentUserDao.queryList(sql, new Object[]{secondment,userId});
			if (null == secondmentUsers || 0 == secondmentUsers.size()) {
				return;
			}
			secondmentUserDao.delete(secondmentUsers.get(0));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
}