package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.Train;
import com.cadre.pojo.TrainUser;
import com.cadre.pojo.User;

@Service
public class TrainUserService {

	@Autowired
	BaseDao<Train> trainDao;
	@Autowired
	BaseDao<TrainUser> trainUserDao;
	
	public List<TrainUser> findByUser(User user){
		if (null == user) return null;
		String hql = "from TrainUser where (delFlag is null or delFlag = 1) and user = ?";
		return trainUserDao.queryList(hql, new Object[]{user});
	}
}
