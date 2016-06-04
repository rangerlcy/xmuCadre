package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.AdministrationWorkHistory;
import com.cadre.pojo.User;
import com.cadre.pojo.WorkingPaper;

@Service
public class AdministrationWorkService {

	@Autowired
	BaseDao<AdministrationWorkHistory> administrationWorkHistoryDao;
	
	public AdministrationWorkHistory findLastByUser(User user){
		String hql = "from AdministrationWorkHistory where user = ? and (delFlag = 1 or delFlag is null) order by endDay desc";
		List<AdministrationWorkHistory> li = administrationWorkHistoryDao.queryList(hql, new Object[]{user});
		if (null == li || 0 == li.size()){
			return null;
		}else{
			return li.get(0);
		}
	}

	public List<AdministrationWorkHistory> findAll() {
		String hql = "from AdministrationWorkHistory where (delFlag = 1 or delFlag is null)";
		List<AdministrationWorkHistory> li = administrationWorkHistoryDao.queryList(hql, new Object[]{});
		if (null == li || 0 == li.size()){
			return null;
		}else{
			return li;
		}
	}

	public void save(AdministrationWorkHistory administrationWorkHistory) {
		// TODO Auto-generated method stub
		if (null == administrationWorkHistory) return;
		administrationWorkHistoryDao.save(administrationWorkHistory);
	}

	public AdministrationWorkHistory findByPaperAndUser(User user,
			WorkingPaper workingPaper) {
		// TODO Auto-generated method stub
		if (null == user || null == workingPaper) return null;
		String sql = "from AdministrationWorkHistory where (delFlag = 1 or delFlag is null) and user = ? and paper = ?";
		List<AdministrationWorkHistory> administrationWorkHistories = administrationWorkHistoryDao.queryList(sql, new Object[]{user,workingPaper});
		if (null == administrationWorkHistories || 0 == administrationWorkHistories.size())
			return null;
		return administrationWorkHistories.get(0);
	}

	public void update(AdministrationWorkHistory administrationWorkHistory) {
		// TODO Auto-generated method stub
		if (null == administrationWorkHistory || null == administrationWorkHistory.getId()) return;
		administrationWorkHistoryDao.update(administrationWorkHistory);
	}
}
