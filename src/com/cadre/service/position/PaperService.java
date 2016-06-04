package com.cadre.service.position;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.Paper;
import com.cadre.pojo.PaperContent;
import com.cadre.pojo.Secondment;

@Service
public class PaperService {
	
	@Autowired
	BaseDao<Paper> paperDao;
	@Autowired
	BaseDao<PaperContent> paperContentDao;
	
	public void addPaper(Paper paper){
		if (null == paper) return;
		paperDao.save(paper);
	}
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public Paper findById(Integer id){
		return 	paperDao.findOne(Paper.class,id);
	}
	
	//删除发文
	public void deletePaper(Integer id) {
		// TODO Auto-generated method stub
		Paper paper=paperDao.findOne(Paper.class, id);
		paper.setDelFlag(0); //置0删除
		paperDao.update(paper);
	}
	//更新发文
	public void update(Paper paper) {
		// TODO Auto-generated method stub
		paperDao.update(paper);
	}
	//根据数据找到发文
	public List<Paper> findPaperByData(String paper_name, Date tt, String paper_num) {
		// TODO Auto-generated method stub
		java.sql.Date sqlDate = new java.sql.Date(tt.getTime());
		String hql="from Paper as p where p.paperNumber=? and p.paperName=? and p.paperDay=?";
		hql=hql+" and (delFlag is null or delFlag = 1)";
		return paperDao.queryList(hql, new Object[]{paper_num, paper_name, sqlDate});
	}
	
	//保存发文的详细信息
	public void savePaperContent(PaperContent pc) {
		// TODO Auto-generated method stub
		if(pc==null)
			return;
		paperContentDao.save(pc);
	}
	
	//根据paperId找到发文详细信息
	public List<PaperContent> findPaperContentById(Integer id) {
		// TODO Auto-generated method stub
		String hql="from PaperContent as pc where pc.paper.id="+id;
		return 	paperContentDao.queryList(hql,null);
	}
	
}
