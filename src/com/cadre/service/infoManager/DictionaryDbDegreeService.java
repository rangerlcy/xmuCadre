package com.cadre.service.infoManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.User;

@Service
public class DictionaryDbDegreeService {

	@Autowired
	BaseDao<DictionaryDb> dictionaryDbDao;
	
	public List<DictionaryDb> findByValue(String value){
		if (value == null) return null;
		String hql="from DictionaryDb where value = ?";
		return  dictionaryDbDao.queryList(hql, new Object[]{value});
	
		
	}
}
