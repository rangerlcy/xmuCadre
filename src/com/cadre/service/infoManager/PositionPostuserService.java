package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.PositionPostuser;

@Service
public class PositionPostuserService {

	@Autowired
	BaseDao<PositionPostuser> positionPostuserDao;
	
	public List<PositionPostuser> findAll() {
		String hql = "from PositionPostuser where (delFlag = 1 or delFlag is null)";
		List<PositionPostuser> resultList = positionPostuserDao.queryList(hql, new Object[]{});
		if (resultList == null || resultList.size() == 0){
			return null;
		}else {
			return resultList;
		}
	}
}
