package com.cadre.service.position;

import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javassist.bytecode.Descriptor.Iterator;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.Organization;
import com.cadre.pojo.Paper;
import com.cadre.pojo.Position;
import com.cadre.pojo.PositionPostuser;
import com.cadre.pojo.PostHistory;
import com.cadre.pojo.User;


@Service
public class PositionService {
	
	@Autowired
	BaseDao<PositionPostuser> positionPostuserDao;
	@Autowired
	BaseDao<Position> positionDao;
	@Autowired
	BaseDao<PostHistory> postHistoryDao;
	@Autowired
	BaseDao<Paper> paperDao;
	
	public void save(Position position){
		positionDao.save(position);
	}
	
	public void update(Position position){
		positionDao.update(position);
	}
	
	@Transactional(rollbackFor={Exception.class})
	public List<PostHistory> delete(Position position){
		try{
			List<PostHistory> postHistories = postHistoryDao.queryList("from PostHistory where position = ?", new Object[]{position});
			for (PostHistory posthistory : postHistories){
				postHistoryDao.delete(posthistory);
			}
			positionDao.delete(position);
			return postHistories;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public Page<Position> findPositionByPage(String queryStr,
			Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		Object[] args = null;
		
		String hqlString="from Position ps";
		String str[]=null;
		if(queryStr!=null){
			str=queryStr.split("\\+");//将queryStr用+分割程数组
		}
		String where = " (ps.delFlag is null or ps.delFlag=1 or ps.delFlag = 0) ";//撤岗还能查询
		if (StringUtil.isNotBlank(queryStr)){
			if(str.length==1){//数组长是1，没有分割，说明没有+ 不是单位+岗位
			queryStr.replace(" ", "");
			where = where + " and (ps.positionName like '%"+queryStr+"%' or ps.paper.paperNumber like '%"+queryStr+"%')";
			}
			else {
				str[0].replace(" ", "");
				str[1].replace(" ", "");
				where = where + " and (ps.positionName like '%"+str[1]+"%'and  ps.academy in (select code from Organization  where name like  '%"+str[0]+"%'))";
			}
		}
		hqlString = hqlString + " where "+where;
		System.out.println(hqlString);
		Page<Position> page = new Page<Position>();
		List<Position> result = positionDao.findForPage(hqlString,args,currPage,pageSize);
		int totalSize = positionDao.findforCount("select count(*) "+hqlString,args); 
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	
	public Page<Paper> findArticleByPage(String queryStr,
			Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		Object[] args = new Object[]{"岗位信息发文"};
		String hql="from Paper as ps where ps.paperType=?";
		if (StringUtil.isNotBlank(queryStr)){
			queryStr.replace(" ", "");
			hql = hql + " and (ps.paperNumber like'%"+queryStr+"%' or ps.paperName like'%"+queryStr+"%')";
		}
		
		Page<Paper> page = new Page<Paper>();
		List<Paper> result = paperDao.findForPage(hql, args,currPage,pageSize);
		int totalSize = paperDao.findforCount("select count(*) "+hql,args); 
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	public boolean duplicatePaperNumber(String positionPaperNumber) {
		// TODO Auto-generated method stub
		String hqlString="from Position where paper.paperNumber = ?";
		List<Position> positions = positionDao.queryList(hqlString, new Object[]{positionPaperNumber});
		if (null == positions || positions.size() == 0)
			return false;
		return true;
	}

	public Position findById(Integer id) {
		// TODO Auto-generated method stub
		return positionDao.findOne(Position.class, id);
	}

	public void addUsersBatch(List<Position> successList) {
		// TODO Auto-generated method stub
		for(Position position : successList){
			positionDao.save(position);
		}
	}

	public List<Position> queryAll() {
		// TODO Auto-generated method stub
		return positionDao.queryList("from Position pos where (pos.delFlag = 1 or pos.delFlag is null)", null);
	}

	public void delPositionsBatch(List<Position> successList) {
		// TODO Auto-generated method stub
		for(Position position : successList){
			this.delete(position);
		}
	}

	public void updateInvalidPositionBatch(List<Position> successList) {
		// TODO Auto-generated method stub
		for(Position position : successList){
			position.setDelFlag(0);
			this.update(position);
		}
	}

	public List<Position> findByDept(String code) {
		// TODO Auto-generated method stub
		String hql = "from Position where department =? and (delFlag is null or delFlag = 1) ";
		return positionDao.queryList(hql, new Object[]{code});
	}
	public List<Position> findByPaper(Integer id) {
		// TODO Auto-generated method stub
		
		return positionDao.queryList("from Position where paper ="+id, null);
	}

	public List<PositionPostuser> findUserbyPosId(int posId) {
		// TODO Auto-generated method stub
		String hql ="from PositionPostuser where positionId="+posId+" and beginWorkDay <> null and endWorkDay = null and (delFlag is null or delFlag = 1)";
		return positionPostuserDao.queryList(hql, null);
	}

	/**
	 * 根据参数找到岗位
	 * @param academy
	 * @param department
	 * @param position_name
	 * @param position_type
	 * @param position_level
	 * @param update_time
	 * @return
	 */
	public List<Position> findByData(String academy, String department,
			String position_name, String position_type, String position_level,
			Date update_time) {
		// TODO Auto-generated method stub
		
		String hql ="from Position as p where p.academy='"+academy+"' and p.department='"+department+"' and p.positionName='"+position_name+"' and p.positionType='"+position_type+"' and p.positionLevel='"+position_level+"' and p.positionDay=?";
		hql=hql+" and (delFlag is null or delFlag = 1)";
		System.out.println(hql);
		return positionDao.queryList(hql, new Object[]{update_time});
	}
	
	/**
	 * 根据id删除岗位
	 * @param position_id
	 */
	public void deleteById(Integer id){
		Position pos = positionDao.findOne(Position.class, id);
		//删除用户
		pos.setDelFlag(0);//置位为0， 删除
		positionDao.update(pos);
	}
	
	/**
	 * 根据id修改岗位部分数据
	 * @param position_id
	 */
	public void editById(Integer id, String new_position_name, String new_position_type, java.util.Date time){
		Position pos = positionDao.findOne(Position.class, id);
		//修改用户
		pos.setPositionType(new_position_type);
		pos.setPositionName(new_position_name);
		pos.setPositionDay(time);
		System.out.println(pos.getPositionName()+pos.getAcademy());
		positionDao.update(pos);
	}

	
	//模糊查询
	public List<Position> queryVague(String insName, Integer flag) {
		// TODO Auto-generated method stub
		List<Position> result = new ArrayList<Position>();
		if(flag==1){		//查询academy
			String hql = "from Position where replace(academy,' ','') like '%"+insName.replaceAll(" ", "") +"%'";
			hql=hql+" and (delFlag is null or delFlag = 1)";
			result = positionDao.queryList(hql,new Object[]{});
		}
		if(flag==2){		//查询department
			String hql = "from Position where replace(department,' ','') like '%"+insName.replaceAll(" ", "") +"%'";
			hql=hql+" and (delFlag is null or delFlag = 1)";
			result = positionDao.queryList(hql,new Object[]{});
		}
		if(flag==3){		//查询positionName
			String hql = "from Position where replace(positionName,' ','') like '%"+insName.replaceAll(" ", "") +"%'";
			hql=hql+" and (delFlag is null or delFlag = 1)";
			result = positionDao.queryList(hql,new Object[]{});
		}
		if(flag==4){		//查询positionType
			String hql = "from Position where replace(positionType,' ','') like '%"+insName.replaceAll(" ", "") +"%'";
			hql=hql+" and (delFlag is null or delFlag = 1)";
			result = positionDao.queryList(hql,new Object[]{});
		}
		return result;
	}

	public List<Position> hasEmptyPositionByAttr(String academy,
			String department, String posName, String posType) {
		// TODO Auto-generated method stub
		String hql="from Position as p where p.academy=? and p.department=? and p.positionName=? and p.positionType=? and (emptyFlag is null or emptyFlag =1)";
		hql = hql+" and (delFlag is null or delFlag =1)";
		
		List<Position> result = positionDao.queryList(hql,new Object[]{ academy, department, posName,posType});
		
		return result;
	}

	public List<Position> findByUser(Integer userId) {
		// TODO Auto-generated method stub
		String hql ="from Position as p where p.user.id=?";
		hql = hql+ " and (delFlag is null or delFlag =1 and emptyFlag =0)";
		List<Position> result = positionDao.queryList(hql,new Object[]{userId});
		return result;
	}

	public List<Position> hasDoingPositionByAttrAndUserId(String academy,
			String department, String posName, String posType, int userId) {
		// TODO Auto-generated method stub
		String hql="from Position as p where p.academy=? and p.department=? and p.positionName=? and p.positionType=? and p.user.id=?";
		hql = hql+ " and (delFlag is null or delFlag =1 and emptyFlag =0)";
		List<Position> result = positionDao.queryList(hql,new Object[]{academy, department, posName,posType, userId});
		return result;
	}

	
	/*
	 * 组织机构顺序修改模块用到的service
	 * 
	 */
	//academy
	public Page<Position> findOrganizationBySeq(String queryStr,
			Integer currPage, int pageSize) throws Exception{
		// TODO Auto-generated method stub
		List<Position> result = new ArrayList<Position>();
		
		int totalSize;
		String hql= "from Position p ";			//被删除的岗位也可以设置顺序
		if(StringUtil.isNotBlank(queryStr)){		//有查询条件
			hql=hql+"where p.academy=? group by p.academy order by -p.academySeq desc";
			
		    result = positionDao.findForPage(hql,new Object[]{queryStr},currPage,pageSize);
		    totalSize = positionDao.findforCount("select count(distinct p.academy)  from Position p where p.academy=? ",new Object[]{queryStr}); 
		}else{
			hql = hql+" group by p.academy order by -p.academySeq desc";
			result = positionDao.findForPage(hql,new Object[]{},currPage,pageSize);
			totalSize = positionDao.findforCount("select count(distinct p.academy) from Position p",new Object[]{}); 
		}
		
		Page<Position> page = new Page<Position>();
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	//department
	public Page<Position> findDepartmentByAcademyNameOrQuery(String academyName,
			String queryStr, Integer currPage, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		
		List<Position> result = new ArrayList<Position>();
		
		int totalSize;
		String hql= "from Position as p ";			//被删除的岗位也可以设置顺序
		if(StringUtil.isNotBlank(queryStr)){		//有查询条件, 至少是二次查询
			hql=hql+" where p.academy=? and p.department=?";
			hql = hql+" group by p.department order by -p.departmentSeq desc";
		    result = positionDao.findForPage(hql,new Object[]{academyName,queryStr},currPage,pageSize);
		    totalSize = positionDao.findforCount("select count(distinct p.department) from Position p where p.academy=? and p.department=?",new Object[]{academyName,queryStr}); 
		    
		}else{				//第一次进来或者是空查询条件
			hql=hql+" where p.academy=? group by p.department order by -p.departmentSeq desc";
			result = positionDao.findForPage(hql,new Object[]{academyName},currPage,pageSize);
			totalSize = positionDao.findforCount("select count(distinct p.department) from Position p where p.academy=?",new Object[]{academyName}); 
		}
		
		
		Page<Position> page = new Page<Position>();
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}

	
	//positonName
	public Page<Position> findPositionNameByDepartmentNameOrQuery(
			String departmentName, String queryStr, Integer currPage, int pageSize)throws Exception {
		// TODO Auto-generated method stub
		List<Position> result = new ArrayList<Position>();
		
		int totalSize;
		String hql= "from Position as p ";			//被删除的岗位也可以设置顺序
		
		if(StringUtil.isNotBlank(queryStr)){		//有查询条件, 至少是二次查询
			hql=hql+" where p.department=? and p.positionName=?";
			hql = hql+" group by p.positionName order by -p.positionNameSeq desc";
		    result = positionDao.findForPage(hql,new Object[]{departmentName,queryStr},currPage,pageSize);
		    totalSize = positionDao.findforCount("select count(distinct p.positionName) from Position p where p.department=? and p.positionName=?",new Object[]{departmentName,queryStr}); 
		    
		}else{				//第一次进来或者是空查询条件
			System.out.println(departmentName);
			hql=hql+" where p.department=? group by p.positionName order by -p.positionNameSeq desc";
			result = positionDao.findForPage(hql,new Object[]{departmentName},currPage,pageSize);
			totalSize = positionDao.findforCount("select count(distinct p.positionName) from Position p where p.department=?",new Object[]{departmentName}); 
		}
		
		
		Page<Position> page = new Page<Position>();
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	
	/*
	 * 改一处自动调节其他所有位置的顺序
	 */
	public void updateOtherSeq(String name,int sqold, int seq, String type) {
		// TODO Auto-generated method stub
		List<Position> pos = new ArrayList<Position>();
		String hql;
		//System.out.println(sqold+"!!!!!"+seq);
		
		//调academy顺序
		if(type.equals("1")){
			if(sqold==999999){			//顺序事先没有指定,根据name修改
				hql="from Position as p where p.academySeq = ?";
				pos = positionDao.queryList(hql,new Object[]{seq});
				if(pos.size()>0){
					hql="from Position as p where p.academySeq >= ? and p.academySeq is not null";
					pos = positionDao.queryList(hql,new Object[]{seq});
					for(Position p: pos){
						p.setAcademySeq(p.getAcademySeq()+1);		//前面的往后+1
						this.update(p);
					}
				}
				hql="from Position as p where p.academy=?";
				pos = positionDao.queryList(hql,new Object[]{name});
				for(Position p: pos){
					p.setAcademySeq(seq);
					this.update(p);
				}
			}else{
			if(sqold>seq){			//从后往前调
				hql="from Position as p where p.academySeq=?";
				pos = positionDao.queryList(hql,new Object[]{sqold});
				for(Position p: pos){
					p.setAcademySeq(-100);		
					this.update(p);
				}
				
				hql="from Position as p where p.academySeq >= ? and p.academySeq< ?";
				pos = positionDao.queryList(hql,new Object[]{seq,sqold});
				for(Position p: pos){
					p.setAcademySeq(p.getAcademySeq()+1);		//前面的往后+1
					this.update(p);
				}
				
				hql="from Position as p where p.academySeq = ?";
				pos = positionDao.queryList(hql,new Object[]{-100});
				for(Position p: pos){
					p.setAcademySeq(seq);		//设置新的seq
					this.update(p);
				}
			}
			if(sqold< seq){
				hql="from Position as p where p.academySeq=?";
				pos = positionDao.queryList(hql,new Object[]{sqold});
				for(Position p: pos){
					p.setAcademySeq(-100);		
					this.update(p);
				}
				
				hql="from Position as p where p.academySeq <= ? and p.academySeq> ?";
				pos = positionDao.queryList(hql,new Object[]{seq,sqold});
				for(Position p: pos){
					p.setAcademySeq(p.getAcademySeq()-1);		//后面往前-1
					this.update(p);
				}
				
				hql="from Position as p where p.academySeq = ?";
				pos = positionDao.queryList(hql,new Object[]{-100});
				for(Position p: pos){
					p.setAcademySeq(seq);		//设置新的seq
					this.update(p);
				}
			}
			}
		}
		
		
		//调department顺序
		if(type.equals("2")){
			if(sqold==999999){			//顺序事先没有指定,根据name修改
				hql="from Position as p where p.departmentSeq = ?";
				pos = positionDao.queryList(hql,new Object[]{seq});
				if(pos.size()>0){
					hql="from Position as p where p.departmentSeq >= ? and p.departmentSeq is not null";
					pos = positionDao.queryList(hql,new Object[]{seq});
					for(Position p: pos){
						p.setDepartmentSeq(p.getDepartmentSeq()+1);		//前面的往后+1
						this.update(p);
					}
				}
				hql="from Position as p where p.department=?";
				pos = positionDao.queryList(hql,new Object[]{name});
				for(Position p: pos){
					p.setDepartmentSeq(seq);
					this.update(p);
				}
				
			}else{
			if(sqold>seq){			//从后往前调
				hql="from Position as p where p.academySeq=?";
				pos = positionDao.queryList(hql,new Object[]{sqold});
				for(Position p: pos){
					p.setDepartmentSeq(-100);		
					this.update(p);
				}
				hql="from Position as p where p.departmentSeq >= ? and p.departmentSeq< ?";
				pos = positionDao.queryList(hql,new Object[]{seq,sqold});
				for(Position p: pos){
					p.setDepartmentSeq(p.getDepartmentSeq()+1);		//前面的往后+1
					this.update(p);
				}
				
				hql="from Position as p where p.departmentSeq = ?";
				pos = positionDao.queryList(hql,new Object[]{-100});
				for(Position p: pos){
					p.setDepartmentSeq(seq);		//设置新的seq
					this.update(p);
				}
			}
			if(sqold< seq){
				hql="from Position as p where p.academySeq=?";
				pos = positionDao.queryList(hql,new Object[]{sqold});
				for(Position p: pos){
					p.setDepartmentSeq(-100);		
					this.update(p);
				}
				
				hql="from Position as p where p.departmentSeq <= ? and p.departmentSeq> ?";
				pos = positionDao.queryList(hql,new Object[]{seq,sqold});
				for(Position p: pos){
					p.setDepartmentSeq(p.getDepartmentSeq()-1);		//后面往前-1
					this.update(p);
				}
				
				hql="from Position as p where p.departmentSeq = ?";
				pos = positionDao.queryList(hql,new Object[]{-100});
				for(Position p: pos){
					p.setDepartmentSeq(seq);		//设置新的seq
					this.update(p);
				}
			}
			}
		}
		
		//调positionName顺序
				if(type.equals("3")){
					if(sqold==999999){			//顺序事先没有指定,根据name修改
						hql="from Position as p where p.positionNameSeq = ?";
						pos = positionDao.queryList(hql,new Object[]{seq});
						if(pos.size()>0){
							hql="from Position as p where p.positionNameSeq >= ? and p.positionNameSeq is not null";
							pos = positionDao.queryList(hql,new Object[]{seq});
							for(Position p: pos){
								p.setPositionNameSeq(p.getPositionNameSeq()+1);		//前面的往后+1
								this.update(p);
							}
						}
						
						hql="from Position as p where p.positionName=?";
						pos = positionDao.queryList(hql,new Object[]{name});
						for(Position p: pos){
							p.setPositionNameSeq(seq);
							this.update(p);
						}
						
					}else{
					if(sqold>seq){			//从后往前调
						hql="from Position as p where p.positionNameSeq=?";
						pos = positionDao.queryList(hql,new Object[]{sqold});
						for(Position p: pos){
							p.setPositionNameSeq(-100);		
							this.update(p);
						}
						
						hql="from Position as p where p.positionNameSeq >= ? and p.positionNameSeq< ?";
						pos = positionDao.queryList(hql,new Object[]{seq,sqold});
						for(Position p: pos){
							p.setPositionNameSeq(p.getPositionNameSeq()+1);		//前面的往后+1
							this.update(p);
						}
						
						hql="from Position as p where p.positionNameSeq = ?";
						pos = positionDao.queryList(hql,new Object[]{-100});
						for(Position p: pos){
							p.setPositionNameSeq(seq);		//设置新的seq
							this.update(p);
						}
					}
					if(sqold< seq){
						hql="from Position as p where p.positionNameSeq=?";
						pos = positionDao.queryList(hql,new Object[]{sqold});
						for(Position p: pos){
							p.setPositionNameSeq(-100);		
							this.update(p);
						}
						
						hql="from Position as p where p.positionNameSeq <= ? and p.positionNameSeq> ?";
						pos = positionDao.queryList(hql,new Object[]{seq,sqold});
						for(Position p: pos){
							p.setPositionNameSeq(p.getPositionNameSeq()-1);		//后面往前-1
							this.update(p);
						}
						
						hql="from Position as p where p.positionNameSeq = ?";
						pos = positionDao.queryList(hql,new Object[]{-100});
						for(Position p: pos){
							p.setPositionNameSeq(seq);		//设置新的seq
							this.update(p);
						}
					}
				}
				}
	}

	
	/**
	 * 导出花名册查询
	 * @param positionName
	 * @return
	 */
	public List<Position> queryByAcademy(String academy) {
		// TODO Auto-generated method stub
		List<Position> result = new ArrayList<Position>();
		
		if(StringUtil.isBlank(academy)){
			String hql = "from Position as pos";
			hql=hql+" where (pos.delFlag is null or pos.delFlag =1) order by -pos.academySeq desc, -pos.departmentSeq desc, -pos.positionName desc";
			System.out.println(hql);
			result = positionDao.queryList(hql,new Object[]{});
		}
		else{
			if(academy.contains("*")){
				System.out.println(academy);
				String[] academyArray= academy.split("[*]");
				String queryStr="";
				int i;
				for(i=0; i<academyArray.length-1; i++){
					queryStr+=" pos.academy = '"+academyArray[i]+"' or ";
				}
				queryStr+=" pos.academy = '"+academyArray[i]+"' ";
				String hql="from Position as pos where "+queryStr;
				hql=hql+"and (pos.delFlag is null or pos.delFlag =1) order by -pos.academySeq desc, -pos.departmentSeq desc, -pos.positionName desc";
				System.out.println(hql);
				result = positionDao.queryList(hql,new Object[]{});
			}
			else{
				String hql = "from Position as pos where pos.academy=? ";
				hql=hql+" and (pos.delFlag is null or pos.delFlag =1) order by -pos.academySeq desc, -pos.departmentSeq desc, -pos.positionName desc";
				System.out.println(hql);
				result = positionDao.queryList(hql,new Object[]{academy});
			}
		}
		return result;
	}
	
	

	
}
