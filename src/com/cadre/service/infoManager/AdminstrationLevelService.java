package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.AdministrationLevelHistory;
import com.cadre.pojo.User;

@Service
public class AdminstrationLevelService {

	@Autowired
	BaseDao<AdministrationLevelHistory> administrationLevelHistoryDao;
	
	public AdministrationLevelHistory findLastByUser(User user){
		String hql = "from AdministrationLevelHistory where user = ? and (delFlag = 1 or delFlag is null) order by levelDay desc";
		List<AdministrationLevelHistory> resultList = administrationLevelHistoryDao.queryList(hql, new Object[]{user});
		if (resultList == null || resultList.size() == 0 ){
			return null;
		}else{
			return resultList.get(0);
		}
	}

	public List<AdministrationLevelHistory> findAll() {
		String hql = "from AdministrationLevelHistory where (delFlag = 1 or delFlag is null)";
		List<AdministrationLevelHistory> resultList = administrationLevelHistoryDao.queryList(hql, new Object[]{});
		if (resultList == null || resultList.size() == 0){
			return null;
		}else {
			return resultList;
		}
	}

	public void save(AdministrationLevelHistory administrationLevelHistory) {
		// TODO Auto-generated method stub
		if (null == administrationLevelHistory) return;
		administrationLevelHistoryDao.save(administrationLevelHistory);
	}
}
