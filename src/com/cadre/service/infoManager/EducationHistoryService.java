package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.User;

@Service
public class EducationHistoryService {

	@Autowired
	BaseDao<EducationHistory> educationHistoryDao;
	
	public List<EducationHistory> findByUser(User user){
		if (user == null) return null;
		String hql="from EducationHistory where (delFlag is null or delFlag = 1) and  user = ?";
		return  educationHistoryDao.queryList(hql, new Object[]{user});
	
		
	}
}
