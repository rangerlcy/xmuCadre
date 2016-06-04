package com.cadre.service.appointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.PostUser;

@Service
public class PostUserService {
	@Autowired
	BaseDao<PostUser>  PostUserDao;
	
	public List<PostUser> findPostUserByUserid(int userId){
		String hql = " from PostUser where userId = ?";
		return PostUserDao.queryList(hql, new Object[]{userId});
	}

}
