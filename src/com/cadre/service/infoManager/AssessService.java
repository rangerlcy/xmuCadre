package com.cadre.service.infoManager;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.pojo.Assess;
import com.cadre.pojo.User;

@Service
public class AssessService {

	@Autowired
	BaseDao<Assess> assessDao;
	
	
	public Page<Assess> findAssessByPage(String queryStr,int currPage,String year,int pageSize) throws Exception{
		String hqlString = "from Assess ass ";
		String where = " (ass.delFlag is null or ass.delFlag=1 ) and ass.year="+year;
		Object[] args = null;
		if(StringUtils.isNotBlank(queryStr)){
			where += " and (ass.user.userName like '%"+queryStr+"%' or ass.user.name like '%"+queryStr+"%' or ass.user.number like '%"+queryStr+"%' or ass.user.identifyNum like '%"+queryStr+"%' ) ";
		}
		hqlString = hqlString + " where "+where;
		Page<Assess> page = new Page<Assess>();
		
		List<Assess> result = assessDao.findForPage(hqlString+" order by ass.year asc",args, currPage, pageSize);
		int totalSize = assessDao.findforCount("select count(*) "+hqlString,args);
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;	
	}
	public List<Assess> findByUser(User user) {
		// TODO Auto-generated method stub
		String hqlString ="from Assess where user = ? and (delFlag = 1 or delFlag is null) order by year asc";
		List<Assess> list =  assessDao.queryList(hqlString, new Object[]{user});
		if (null == list || list.size()== 0){
			return null;
		}else {
			return list;
		}
	}
	public Assess findById(Integer id) {
		// TODO Auto-generated method stub
		return assessDao.findOne(Assess.class, id);
	}
	public void delete(Assess assess) {
		// TODO Auto-generated method stub
		assessDao.delete(assess);
	}
	public void update(Assess assess) {
		// TODO Auto-generated method stub
		assessDao.update(assess);
	}
	public void save(Assess ass) {
		// TODO Auto-generated method stub
		assessDao.save(ass);
	}
	public void addAsseBatch(List<Assess> successList) {
		// TODO Auto-generated method stub
		for (Assess assess : successList){
			if (assess.getId() != null){
				assessDao.update(assess);
			}else {
				assessDao.save(assess);
			}
		}
	}
	public List<Assess> queryAll() {
		// TODO Auto-generated method stub
		String hqlString = "from Assess where delFlag is null or delFlag = 1";
		return assessDao.queryList(hqlString, new Object[]{});
	}
	public void delAsseBatch(List<Assess> successList) {
		// TODO Auto-generated method stub
		for (Assess assess : successList){
			if (assess.getId() == null) continue;
			assessDao.delete(assess);
		}
	}
}
