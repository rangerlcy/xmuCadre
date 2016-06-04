package com.cadre.service.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.PositionHistory;

@Service
public class PositionHistoryService {

	@Autowired
	BaseDao<PositionHistory> positionHistoryDao;
	
	public void save(PositionHistory positionHistory){
		if (null == positionHistory) return;
		positionHistoryDao.save(positionHistory);
	}
}
