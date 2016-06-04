package com.cadre.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.User;

@Service
public class PostUserNameService {

	@Autowired
	BaseDao<PostUser> postUserDao;
	
	public List<PostUser> findByUser(User user){
		if (null == user) return null;
		String hql = "from PostUser where user = ?";
		return postUserDao.queryList(hql, new Object[]{user});
	}
}
