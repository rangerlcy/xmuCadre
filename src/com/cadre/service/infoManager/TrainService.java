package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.Train;
import com.cadre.pojo.TrainUser;
import com.cadre.pojo.User;

@Service
public class TrainService {

	@Autowired
	BaseDao<Train> trainDao;
	@Autowired
	BaseDao<TrainUser> trainUserDao;
	
	public List<Train> findById(Integer id){
		if (null == id) return null;
		String hql = "from Train where (delFlag is null or delFlag = 1) and id = ?";
		return trainDao.queryList(hql, new Object[]{id});
		
	}
}
