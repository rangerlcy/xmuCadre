package com.cadre.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.OrganizationHistory;

@Service
public class OrganizationHistoryService {

	@Autowired
	BaseDao<OrganizationHistory> organizationHistoryDao;
	
	public void save(OrganizationHistory organizationHistory){
		if (null == organizationHistory) return;
		organizationHistoryDao.save(organizationHistory);
	}
}
