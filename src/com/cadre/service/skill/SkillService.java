package com.cadre.service.skill;

import java.sql.Date;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.Skill;

@Service
public class SkillService {

	@Autowired
	BaseDao<Skill> skillDao;
	
	private Logger logger = LogManager.getLogger(SkillService.class);
	
	public void add(Skill skill) {
		if (null == skill) {
			logger.debug("null skill");
			return;
		}
		try {
			skillDao.save(skill);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return;
			// TODO: handle exception
		}
		
	}
	
	public void delete(Skill skill) {
		if (null == skill || null == skill.getId()) {
			logger.debug("null skill");
			return;
		}
		try {
			skillDao.delete(skill);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			return;
		}
		
	}
	
	public void update(Skill skill) {
		if (null == skill || null == skill.getId()) {
			logger.debug("null skill");
			return;
		}
		try {
			skillDao.update(skill);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return;
			// TODO: handle exception
		}
	}
	
	public Skill queryById(Integer id){
		return skillDao.findOne(Skill.class, id);
	}
	
	public Skill searchWhenPost(Integer userId,Date trainBeginDay){
		String hql = " from Skill where user.id = ? and beginDay <= ? and endDay >= ?";
		List<Skill> skills = skillDao.queryList(hql, new Object[]{userId, trainBeginDay, trainBeginDay});
		if (skills.size() == 0) return null;
		else return skills.get(0);
		
	}
	/**
	 * 查找所有经历
	 * @return
	 */
	public List<Skill> queryAll(){
		String hql = "from Skill as skill where skill.delFlag is null or skill.delFlag = 1";
		return skillDao.queryList(hql, null);
	}
	
	//分页查询
	public Page<Skill> findSkillByPage(String queryStr,
			Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		Object[] args = null;
		String hqlString="from Skill skill";
		String where = " (skill.delFlag is null or skill.delFlag = 1) ";// 只查询没有删除的记录
		if (StringUtil.isNotBlank(queryStr)){
			queryStr.replace(" ", "");
			where = where + " and (skill.user.name like'%"+queryStr+"%')";
		}
		hqlString = hqlString + " where "+where +" order by skill.user.id , skill.beginDay";
		Page<Skill> page = new Page<Skill>();
		List<Skill> result = skillDao.findForPage(hqlString,args,currPage,pageSize);
		int totalSize = skillDao.findforCount("select count(*) "+hqlString,args); 
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	/**
	 * 添加专业技术职务经历
	 * @param skill
	 */
	public void addSkill(Skill skill){
		skillDao.save(skill);
	}
	
	/**
	 * 删除专业技术职务经历
	 * @param id
	 */
	public void delete(Integer id){
		Skill skill = skillDao.findOne(Skill.class, id);
		//删除用户
		skill.setDelFlag(0);//置位为0， 删除
		skillDao.update(skill);
	}
	
	/**
	 * 批量增加专业技术职务经历
	 * @param SkillList
	 * @return
	 */
	public boolean saveSkillBatch(List<Skill> skillList){
		for(Skill skill : skillList){
			if(skill.getId() != null){
				skillDao.update(skill);
			}else{
				skillDao.save(skill);
			}
		}
		return true;		
	}

	public	Skill findById(Integer id) {
		// TODO Auto-generated method stub
		return 	skillDao.findOne(Skill.class,id);
	}
	
	public List<Skill> findByUserId(Integer id){			//根据userId 找到最新的专业技术职务
		String hql="from Skill where userId = "+ id+" ORDER BY beginDay DESC LIMIT 1";
		return skillDao.queryList(hql,null);
	}

	public void updateSkill(Skill sk) {
		// TODO Auto-generated method stub
		skillDao.update(sk);
	}
}
