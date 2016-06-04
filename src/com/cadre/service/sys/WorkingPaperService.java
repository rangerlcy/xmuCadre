package com.cadre.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.User;
import com.cadre.pojo.WorkingPaper;

@Service
public class WorkingPaperService {

	@Autowired
	BaseDao<WorkingPaper> workingPaperDao;
	
	public List<WorkingPaper> findById(Integer id){
		if (null == id) return null;
		String hql = "from WorkingPaper where id = ?";
		return workingPaperDao.queryList(hql, new Object[]{id});
	}
}
