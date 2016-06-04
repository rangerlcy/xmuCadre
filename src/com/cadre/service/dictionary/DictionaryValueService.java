package com.cadre.service.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.DictionaryDb;
@Service
public class DictionaryValueService {

	@Autowired
	BaseDao<DictionaryDb> dictionaryDbDao;

	public List<DictionaryDb> findByCode(String code){
		if (null == code) return null;
		String hql = "from DictionaryDb where code = ?";
		return dictionaryDbDao.queryList(hql, new Object[]{code});
		
	}
	public List<DictionaryDb> findDegreeByDictionaryItemKey(String dictionaryItemKey){
		if (null == dictionaryItemKey) return null;
		String hql = "from DictionaryDb where parentCode= 14  and dictionaryItemKey=?";
		return dictionaryDbDao.queryList(hql, new Object[]{dictionaryItemKey});
		
	}
	public List<DictionaryDb> queryAll() {
		// TODO Auto-generated method stub
		String hql = " from DictionaryDb";
		return dictionaryDbDao.queryList(hql, null); 
	}
}
