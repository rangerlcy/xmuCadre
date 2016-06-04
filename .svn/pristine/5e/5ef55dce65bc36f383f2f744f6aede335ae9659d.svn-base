package com.cadre.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.Country;
@Service
public class CountryService {
	@Autowired
	BaseDao<Country> countryDao;
	/**
	 * 查询出全部的国家
	 * @return
	 */
	public List<Country> queryAllCountrys(){
		String hql = " from Country ";
		return countryDao.queryList(hql, null);
	}
	/**
	 * 根据编码查名称
	 * @return
	 */
	public String queryNameByCode(String code){
		String hql = " from Country where code=?";
		List<Country> countrys = countryDao.queryList(hql, new Object[]{code}); 
		if(countrys.size() == 0){
			return "";
		}else{
			Country country = countrys.get(0);
			return country.getName();
		}
	}
}
