package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.SecondmentUser;
import com.cadre.pojo.User;

@Service
public class SecondmentUsersService {

	@Autowired
	BaseDao< SecondmentUser>  secondmentUserrDao;
	
	public List< SecondmentUser> findByUser(User user){
		if (null == user) return null;
		String hql = "from SecondmentUser where (delFlag is null or delFlag = 1) and  user  = ?";
		return secondmentUserrDao.queryList(hql, new Object[]{user});
	}
}