//create by lcy 2015/07/30
package com.cadre.service.education;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.Paper;
import com.cadre.pojo.Skill;
import com.cadre.pojo.Train;
import com.cadre.pojo.TrainUser;
import com.cadre.pojo.User;

@Service
public class EducationService {
	@Autowired
	BaseDao<EducationHistory> educationHistoryDao;

	/**
	 * 查找所有经历
	 * @return
	 */
	public List<EducationHistory> queryAll(){
		String hql = "from EducationHistory as edu where edu.delFlag is null or edu.delFlag = 1 order by edu.user.id , edu.beginDay";
		return educationHistoryDao.queryList(hql,null);
	}

	//分页查询
	public Page<EducationHistory> findEducationByPage(String queryStr,
			Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		Object[] args = null;
		String hqlString="from EducationHistory edu";
		String where = " (edu.delFlag is null or edu.delFlag = 1) ";// 只查询没有删除的记录
		if (StringUtil.isNotBlank(queryStr)){
			queryStr.replace(" ", "");
			where = where + " and (edu.user.name like'%"+queryStr+"%')";
		}
		hqlString = hqlString + " where "+where +" order by edu.user.id , edu.beginDay";
		Page<EducationHistory> page = new Page<EducationHistory>();
		List<EducationHistory> result = educationHistoryDao.findForPage(hqlString,args,currPage,pageSize);
		int totalSize = educationHistoryDao.findforCount("select count(*) "+hqlString,args); 
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	/**
	 * 添加教育经历
	 * @param edu
	 */
	public void add(EducationHistory edu){
		educationHistoryDao.save(edu);
	}
	
	/**
	 * 删除教育经历
	 * @param id
	 */
	public void delete(Integer id){
		EducationHistory edu = educationHistoryDao.findOne(EducationHistory.class, id);
		//删除用户
		edu.setDelFlag(0);//置位为0， 删除
		educationHistoryDao.update(edu);
	}
	
	/**
	 * 批量增加教育经历
	 * @param EduList
	 * @return
	 */
	public boolean saveEduBatch(List<EducationHistory> EduList){
		for(EducationHistory edu : EduList){
			if(edu.getId() != null){
				educationHistoryDao.update(edu);
			}else{
				educationHistoryDao.save(edu);
			}
		}
		return true;		
	}
	
	
	public EducationHistory findById(Integer id) {
		// TODO Auto-generated method stub
		return 	educationHistoryDao.findOne(EducationHistory.class,id);
	}
	
	/**
	 * 批量删除教育经历
	 * @param EduList
	 * @return
	 */
	//根据姓名、身份证号、开始时间,删除在表中的数据
	public void delByNIB(List<EducationHistory> EduList){
		String name; String identify; Date beginday; String school;
		for(EducationHistory edu : EduList){
			name=edu.getUser().getName();
			identify=edu.getUser().getIdentifyNum();
			beginday=edu.getBeginDay();
			school = edu.getSchool();
			//date转字符串，不然无法查询数据库
			SimpleDateFormat tt=new SimpleDateFormat("yyyy-MM-dd"); 
			String ss = tt.format(beginday);
			//java.sql.Date date=new java.sql.Date(beginday.getTime());
			
			String hqlString="from EducationHistory as edu where (edu.delFlag is null or edu.delFlag = 1) ";  // 只查询没有删除的记录
			
			hqlString = hqlString + " and (edu.user.name = ?) and (edu.user.identifyNum =?) and (edu.beginDay =?) and (edu.school =?)";
			
			
			Object[] args = new Object[]{name, identify,beginday , school};
			List<EducationHistory> result = educationHistoryDao.queryList(hqlString,args);	
			//真删除，抹除数据库中的数据
			educationHistoryDao.deleteAll(result);
		}
	}

	public void update(EducationHistory eh) {
		// TODO Auto-generated method stub
		educationHistoryDao.update(eh);
	}

	public List<EducationHistory> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		String hql="from EducationHistory where userId = "+ id+" order by beginDay desc limit 1";
		return educationHistoryDao.queryList(hql,null);
	}
}
