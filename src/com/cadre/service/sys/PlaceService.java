package com.cadre.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.Place;
import com.cadre.pojo.Position;

@Service
public class PlaceService {
	
	@Autowired
	BaseDao<Place> placeDao;
	
	/**
	 * 查出所有数据
	 * @return
	 */
	public List<Place> queryAll() {
		String hql = " from Place ";
		return placeDao.queryList(hql, null);
	}
	/**
	 * 查出所有省份
	 * @return
	 */
	public List<Place> queryAllProv() {
		String hql = " from Place where parentCode='AB' ";
		return placeDao.queryList(hql, null);
	}
	/**
	 * 根据编码查出下一级地区
	 * @return
	 */
	public List<Place> queryChildByCode(String code) {
		String hql = " from Place where parentCode=? ";
		return placeDao.queryList(hql, new Object[]{code});
	}
	
	/**
	 * 根据编码查名称
	 * @return
	 */
	public String queryNameByCode(String code) {
		String hql = " from Place where code=? ";
		List<Place> places = placeDao.queryList(hql, new Object[]{code});
		if(places.size() == 0){
			return null;
		}else{
			Place place = places.get(0);
			return place.getName();
		}
	}
	
	public Place queryByCode(String code) {
		String hql = " from Place where code=? ";
		List<Place> places = placeDao.queryList(hql, new Object[]{code});
		if(places.size() == 0){
			return null;
		}else{
			Place place = places.get(0);
			return place;
		}
	}
	public String queryMaxCodeByParentCode(String parentCode) {
		// TODO Auto-generated method stub
		String hql="from Place where code in (select max(code) from Place where parentCode = ?) and parentCode = ?";
		List<Place> places = placeDao.queryList(hql, new Object[]{parentCode,parentCode});
		return places.get(0).getCode();
	}

	public String queryMaxCode() {
		// TODO Auto-generated method stub
		String hql="from Place where code in (select max(code) from Place where parentCode = 'AB')";
		List<Place> places = placeDao.queryList(hql, null);
		return places.get(0).getCode();
	}
	public void save(Place place) {
		if(null == place)return;
		placeDao.save(place);
	}
	public Place queryById(Integer id) {
		return placeDao.findOne(Place.class, id);
	}
	public void update(Place place) {
		if (null == place) return;
		if (null == place.getId()) return;
		placeDao.update(place);
	}
	public void delete(Place place) {
		if (null == place) return;
		if (null == place.getId()) return;
		placeDao.delete(place);
	}
	/*
	 * 行政区划及籍贯管理模块用到的service
	 * 
	 */
	//province
	public Page<Place> findProvByPage(String queryStr,
			Integer currPage, int pageSize) throws Exception{
		// TODO Auto-generated method stub
		List<Place> result = new ArrayList<Place>();
		
		int totalSize;
		String hql= "from Place p ";			
		if(StringUtil.isNotBlank(queryStr)){		//有查询条件
			hql=hql+"where p.name=? and p.parentCode = 'AB'";
			
		    result = placeDao.findForPage(hql,new Object[]{queryStr},currPage,pageSize);
		    totalSize = placeDao.findforCount("select count(distinct p.name)  from Place p where p.name=? and p.parentCode = 'AB'",new Object[]{queryStr}); 
		}else{
			hql = hql+"where p.parentCode = 'AB'";
			result = placeDao.findForPage(hql,new Object[]{},currPage,pageSize);
			totalSize = placeDao.findforCount("select count(distinct p.name) from Place p where p.parentCode = 'AB'",new Object[]{}); 
		}
		
		Page<Place> page = new Page<Place>();
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	//下级区域信息
	public Page<Place> findBellowInfo(String parentCode, String queryStr,
			Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		
		List<Place> result = new ArrayList<Place>();
		
		int totalSize;
		String hql= "from Place as p ";			
		if(StringUtil.isNotBlank(queryStr)){		//有查询条件, 至少是二次查询
			hql=hql+" where p.parentCode=? and p.name=?";
		    result = placeDao.findForPage(hql,new Object[]{parentCode,queryStr},currPage,pageSize);
		    totalSize = placeDao.findforCount("select count(distinct p.name) from Place p where p.parentCode=? and p.name=?",new Object[]{parentCode,queryStr}); 
		    
		}else{				//第一次进来或者是空查询条件
			hql=hql+" where p.parentCode=?";
			result = placeDao.findForPage(hql,new Object[]{parentCode},currPage,pageSize);
			totalSize = placeDao.findforCount("select count(distinct p.name) from Place p where p.parentCode=?",new Object[]{parentCode}); 
		}
		
		
		Page<Place> page = new Page<Place>();
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
}
