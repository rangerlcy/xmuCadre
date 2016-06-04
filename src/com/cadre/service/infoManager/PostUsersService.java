package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.User;

@Service
public class PostUsersService {

	@Autowired
	BaseDao<PostUser> postUserDao;
	
	public List<PostUser> findByUser(User user){
		if (user == null) return null;
		String hql="from PostUser where (delFlag is null or delFlag = 1) and user = ?";
		return  postUserDao.queryList(hql, new Object[]{user});
	}

	public List<PostUser> getUserLastActionDayByUser(User user) {
		// TODO Auto-generated method stub
		String hql = "from PostUser where (delFlag is null or delFlag = 1) and user = ? and (appointOrRemove like '%职%' ) order by actionDay desc";
		return  postUserDao.queryList(hql, new Object[]{user});
	}
}

