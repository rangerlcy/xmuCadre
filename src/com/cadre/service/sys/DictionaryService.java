package com.cadre.service.sys;

import java.util.List;

import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.DictionaryDb;


@Service
public class DictionaryService {

	@Autowired
	BaseDao<DictionaryDb> dictionaryDbDao;
	/**
	 * 根据条件统计
	 * @param propName
	 * @param value
	 * @return
	 */
	public int getCount(String propName,Object value){
		String sql = "select count(*) from DictionaryDb where DictionaryDb."+propName+"= ?";
		return dictionaryDbDao.findforCount(sql,new Object[]{value});
	}
	
	/**
	 * 查询出全部的组织机构
	 * @return
	 */
	public List<DictionaryDb> queryAll(){
		String hql = " from DictionaryDb";
		return dictionaryDbDao.queryList(hql, null);
	}
	
	/**
	 * 根据机构代码查询出组织信息
	 * @param code
	 * @return
	 */
	public DictionaryDb queryByCode(String code){
		String hql = " from DictionaryDb where code = ?";
		List<DictionaryDb> dictionaryDbList = dictionaryDbDao.queryList(hql, new Object[]{code});
		if (dictionaryDbList.size() > 0){
			return dictionaryDbList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据编码查出下一级组织
	 * @param code
	 * @return
	 */
	public List<DictionaryDb> queryChildByCode(String code){
		System.out.println("查询code = "+code);
		String hql = " from DictionaryDb where parentCode=?";
		return dictionaryDbDao.queryList(hql, new Object[]{code});
	}
	
	/**
	 * 根据主键查询出组织信息
	 * @param id
	 * @return
	 */
	public DictionaryDb queryById(Integer id){
		return dictionaryDbDao.findOne(DictionaryDb.class, id);
	}
	
	/**
	 * 根据编码查名称
	 * @param code
	 * @return
	 */
	public String queryNameByCode(String code) {
		String hql = " from DictionaryDb where code = ?";
		List<DictionaryDb> dictionaryDbs = dictionaryDbDao.queryList(hql, new Object[]{code});
		if (dictionaryDbs.size()==0){
			return "";
		}else{
			DictionaryDb dic = dictionaryDbs.get(0);
			return dic.getName();
		}
	}
	
	public void save(DictionaryDb dictionaryDb){
		if (null == dictionaryDb) return;
		dictionaryDbDao.save(dictionaryDb);
	}
	
	/**
	 * 删除组织机构
	 * @param dictionaryDb
	 */
	public void delete(DictionaryDb dictionaryDb){
		if (null == dictionaryDb) return;
		dictionaryDbDao.delete(dictionaryDb);
		return;
	}
	
	/**
	 * 更新组织机构
	 * @param dictionaryDb
	 */
	public void update(DictionaryDb dictionaryDb){
		if (null == dictionaryDb) return;
		if (null == dictionaryDb.getId()) return;
		dictionaryDbDao.update(dictionaryDb);
	}
	
	public String queryMaxCodeByParentCode(String parentCode) {
		// TODO Auto-generated method stub
		String hql="from DictionaryDb where code in (select max(code) from DictionaryDb where parentCode = ?) and parentCode = ?";
		List<DictionaryDb> dictionaryDbs = dictionaryDbDao.queryList(hql, new Object[]{parentCode,parentCode});
		return dictionaryDbs.get(0).getCode();
	}
	
	public String queryMaxKeyByParentCode(String parentCode) {
		// TODO Auto-generated method stub
		String hql="from DictionaryDb where code in (select max(code) from DictionaryDb where parentCode = ?) and parentCode = ?";
		List<DictionaryDb> dictionaryDbs = dictionaryDbDao.queryList(hql, new Object[]{parentCode,parentCode});
		return dictionaryDbs.get(0).getKey();
	}
}
