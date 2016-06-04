package com.cadre.service.administrationWork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.AdministrationWorkHistory;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.Paper;
import com.cadre.pojo.Train;
import com.cadre.pojo.TrainUser;
import com.cadre.pojo.User;

@Service
public class AdministrationService {
	@Autowired
	BaseDao<AdministrationWorkHistory> administrationWorkHistoryDao;

	/**
	 * 查找所有经历
	 * @return
	 */
	public List<AdministrationWorkHistory> queryAll(){
		String hql = "from AdministrationWorkHistory as adm where adm.delFlag is null or adm.delFlag = 1";
		return administrationWorkHistoryDao.queryList(hql,null);
	}
	
	//分页查询
	public Page<AdministrationWorkHistory> findAdministrationWorkByPage(String queryStr,
			Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		Object[] args = null;
		String hqlString="from AdministrationWorkHistory adm";
		String where = " (adm.delFlag is null or adm.delFlag = 1) ";// 只查询没有删除的记录
		if (StringUtil.isNotBlank(queryStr)){
			queryStr.replace(" ", "");
			where = where + " and (adm.user.name like'%"+queryStr+"%')";
		}
		hqlString = hqlString + " where "+where +" order by adm.user.id , adm.beginDay";
		Page<AdministrationWorkHistory> page = new Page<AdministrationWorkHistory>();
		List<AdministrationWorkHistory> result = administrationWorkHistoryDao.findForPage(hqlString,args,currPage,pageSize);
		int totalSize = administrationWorkHistoryDao.findforCount("select count(*) "+hqlString,args); 
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	/**
	 * 添加行政工作经历经历
	 * @param adm
	 */
	public void add(AdministrationWorkHistory adm){
		administrationWorkHistoryDao.save(adm);
	}
	
	/**
	 * 删除行政工作经历经历
	 * @param id
	 */
	public void delete(Integer id){
		AdministrationWorkHistory adm = administrationWorkHistoryDao.findOne(AdministrationWorkHistory.class, id);
		//删除用户
		adm.setDelFlag(0);//置位为0， 删除
		administrationWorkHistoryDao.update(adm);
	}
	
	/**
	 * 批量增加行政工作经历经历
	 * @param admList
	 * @return
	 */
	public boolean saveadmBatch(List<AdministrationWorkHistory> admList){
		for(AdministrationWorkHistory adm : admList){
			if(adm.getId() != null){
				administrationWorkHistoryDao.update(adm);
			}else{
				administrationWorkHistoryDao.save(adm);
			}
		}
		return true;		
	}

	public AdministrationWorkHistory findById(Integer id) {
		// TODO Auto-generated method stub
		return 	administrationWorkHistoryDao.findOne(AdministrationWorkHistory.class,id);
	}

	public void update(AdministrationWorkHistory ah) {
		// TODO Auto-generated method stub
		administrationWorkHistoryDao.update(ah);
	}

	public void delByDetailInfo(List<AdministrationWorkHistory> successList) {
		// TODO Auto-generated method stub
		String name; String identify; Date beginDay; Date endDay; String units; String jobName;String jobType; String userLevel; String checkCase;
		for(AdministrationWorkHistory adw: successList){
			name=adw.getUser().getName();
			identify = adw.getUser().getIdentifyNum();
			beginDay = adw.getBeginDay();
			endDay = adw.getEndDay();
			units = adw.getUnits();
			jobName = adw.getJobName();
			checkCase = adw.getCheckCase();
			jobType = adw.getJobType();
			userLevel  = adw.getUserLevel();
//			System.out.println(name);
//			System.out.println(identify);
//			System.out.println(beginDay);
//			System.out.println(endDay);
//			System.out.println(units);
//			System.out.println(jobName);
//			System.out.println(checkCase);
			
			String hqlString="from AdministrationWorkHistory as adw where (adw.delFlag is null or adw.delFlag = 1)";
			hqlString = hqlString + " and (adw.user.name =?) and (adw.user.identifyNum =?) and (adw.userLevel =?) and (adw.jobType = ?)";
			hqlString = hqlString+ "and (adw.beginDay =?) and (adw.endDay =?) and (adw.units =?) and (adw.jobName =?) and (adw.checkCase =?)";
			
			Object[] args = new Object[]{name, identify,userLevel, jobType, beginDay, endDay, units, jobName, checkCase};
			List<AdministrationWorkHistory> result = administrationWorkHistoryDao.queryList(hqlString,args);	
			System.out.println(result.size());
			//真删除，抹除数据库中的数据
			administrationWorkHistoryDao.deleteAll(result);
		}
	}
}
