package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.Secondment;


@Service
public class SecondmentsService {

	@Autowired
	BaseDao<Secondment> secondmentsDao;

	
	public List<Secondment> findById(Integer id){
		if (null == id) return null;
		String hql = "from Secondment (delFlag is null or delFlag = 1) and where id = ?";
		return secondmentsDao.queryList(hql, new Object[]{id});
		
	}
}