package com.cadre.service.secondment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cadre.controller.secondment.SecondmentParam;
import com.cadre.controller.secondment.SecondmentUserParam;
import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.DateUtil;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.Paper;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.Secondment;
import com.cadre.pojo.SecondmentUser;
import com.cadre.pojo.User;
import com.cadre.service.sys.UserService;

@Service

public class SecondmentService {
	@Autowired
	BaseDao<Secondment> secondmentDao;
	@Autowired
	BaseDao<Paper> paperDao;
	@Autowired
	UserService userService;
	@Autowired
	SecondmentUserService secondmentUserService;
	/**
	 * 查找所有用户
	 * @return
	 */
	//导出
	public List<Secondment> findSecondmentForExport(String queryStr) {
		// TODO Auto-generated method stub
		String sql = "from Secondment ";//查询对象
		String where = " (delFlag is null or delFlag=1) ";//查询条件
		Object[] args = null;
		if(StringUtils.isNotBlank(queryStr)){
			where += " and (paper.paperNumber like '%"+queryStr+"%' or paper.paperName like '%"+queryStr+"%'or paper.paperUnits like '%"+queryStr+"%'or temporaryProjectName like '%"+queryStr+"%'or temporaryBeginDay like '%"+queryStr+"%'or temporaryEndDate like '%"+queryStr+"%'or temporaryRequirement like '%"+queryStr+"%'or remark like '%"+queryStr+"%' ) ";
		}
		sql = sql + " where "+where;
		List<Secondment> result = secondmentDao.queryList(sql, args);
		
		return result;	
	}
	public List<Secondment> queryAll(){
		String hql = "from Secondment ";
		return secondmentDao.queryList(hql,null);
	}
	/*
	 * 操作符转换
	 */
	private String convertOperator(String op){
		if (op.equals("R"))
			return ">";
		else if (op.equals("E"))
			return "=";
		else
			return "<";
	}
	
	/**
	 * 根据hql分页查找
	 * @param hql
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<Secondment> findSearchSecondmentByPage(SecondmentParam secondmentParam,int currPage,int pageSize) throws Exception{
		String hql="from Secondment as s where (delFlag is null or delFlag = 1)";
		if (StringUtil.isNotBlank(secondmentParam.getPostingNumber())){
			 hql+= " and s.paper.paperNumber like '%"+secondmentParam.getPostingNumber()+"%'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getPostingTitle())){
			System.out.println("!!!~~~~@@@");
			hql+= " and s.paper.paperName ='"+secondmentParam.getPostingTitle()+"'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getPostingUnit())){
			
			hql+= " and s.paper.paperUnits ='"+secondmentParam.getPostingUnit()+"'";
		}
         if (StringUtil.isNotBlank(secondmentParam.getTemporaryProjectName())){
			
			hql+= " and s.temporaryProjectName ='"+secondmentParam.getTemporaryProjectName()+"'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getTemporaryBeginDay())){
		
		hql += " and s.temporaryBeginDay "+this.convertOperator(secondmentParam.getTemporaryBeginDayOperator())+" '"+DateUtil.parseDate(secondmentParam.getTemporaryBeginDay(),"yyyy-MM-dd")+"'";
	}
		if (StringUtil.isNotBlank(secondmentParam.getTemporaryEndDate())){
			
			hql += " and s.temporaryEndDate "+this.convertOperator(secondmentParam.getTemporaryEndDateOperator())+" '"+DateUtil.parseDate(secondmentParam.getTemporaryEndDate(),"yyyy-MM-dd")+"'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getTemporaryRequirement())){
			hql+=" and s.temporaryRequirement ='"+secondmentParam.getTemporaryRequirement()+"'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getRemark())){
			hql+=" and s.remark ='"+secondmentParam.getRemark()+"'";
		}
		/*if (birthday!=null){
			
			hql += " and birthday "+this.convertOperator(birthdayOperator)+" '"+DateFormat.getDateInstance().format(birthday)+"'";
		}
		if (StringUtil.isNotBlank(age)){
			int ageInt = Integer.valueOf(age);
			hql+=" and YEAR(NOW())-YEAR(birthday)+1"+this.convertOperator(ageOperator)+age.toString();
		}
		*/
		hql += " order by id desc";
		Page<Secondment> page = new Page<Secondment>();
		Object[] args=null;
		//System.out.println(hql);
		List<Secondment> result =  secondmentDao.findForPage(hql, args, currPage, pageSize);
		//System.out.println(result.get(0).getPaper().getPaperName());
		int totalSize = secondmentDao.findforCount("select count(*) "+hql, args);
		page.setResult(result);
		page.setPageSize(pageSize);
		page.setCurrPage(currPage);
		page.setTotalSize(totalSize);
		return page;
	}
	//高级查找导出
	public List<Secondment> findSuperSecondmentForExport(SecondmentParam secondmentParam) {
		String hql="from Secondment where  (delFlag is null or delFlag = 1)";
		if (StringUtil.isNotBlank(secondmentParam.getPostingNumber())){
			 hql+= " and postingNumber like '%"+secondmentParam.getPostingNumber()+"%'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getPostingTitle())){
		
			hql+= " and postingTitle ='"+secondmentParam.getPostingTitle()+"'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getPostingUnit())){
			
			hql+= " and postingUnit ='"+secondmentParam.getPostingUnit()+"'";
		}
         if (StringUtil.isNotBlank(secondmentParam.getTemporaryProjectName())){
			
			hql+= " and temporaryProjectName ='"+secondmentParam.getTemporaryProjectName()+"'";
		}
		if (secondmentParam.getTemporaryBeginDay()!=null){
		
		hql += " and temporaryBeginDay "+this.convertOperator(secondmentParam.getTemporaryBeginDayOperator())+" '"+DateUtil.parseDate(secondmentParam.getTemporaryBeginDay(),"yyyy-MM-dd")+"'";
	}
		if (secondmentParam.getTemporaryEndDate()!=null){
			
			hql += " and temporaryEndDate "+this.convertOperator(secondmentParam.getTemporaryEndDateOperator())+" '"+DateUtil.parseDate(secondmentParam.getTemporaryEndDate(),"yyyy-MM-dd")+"'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getTemporaryRequirement())){
			hql+=" and temporaryRequirement ='"+secondmentParam.getTemporaryRequirement()+"'";
		}
		if (StringUtil.isNotBlank(secondmentParam.getRemark())){
			hql+=" and remark ='"+secondmentParam.getRemark()+"'";
		}
		return secondmentDao.queryList(hql, new Object[]{});
	}
	/**
	 * 根据属性进行统计
	 * @param propName
	 * @param value
	 * @return
	 */
	public int getCount(String propName, Object value){
		String sql = "select count(*) from Paper as model where model."+propName+"= ?";
		return paperDao.findforCount(sql,new Object[]{value});
	}
	/**
	 * 根据id和属性进行统计
	 * @param propName
	 * @param value
	 * @param id
	 * @return
	 */
	public int getCount(String propName, Object value,Integer id){
		String sql = "select count(*) from Secondment as model where (model.isdel=0 or model.isdel is null) and model."+propName+"= ? and model.id<>?";
		return secondmentDao.findforCount(sql,new Object[]{value,id});
	}
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public Secondment findById(Integer id){
		return secondmentDao.findOne(Secondment.class,id);
	}
	
	/**
	 * 封装查询条件
	 * @param secondment
	 * @return
	 */
	private String generateQueryExample(String queryStr) {
		String hql = " from Secondment ";
		if(StringUtils.isNotBlank(queryStr)) {
			hql += " where paper.paperNumber like binary('%"+queryStr+"%') or paper.paperName like binary('%"+queryStr+"%')or paper.paperUnits like binary('%"+queryStr+"%')or temporaryProjectName like binary('%"+queryStr+"%')or temporaryBeginDay like binary('%"+queryStr+"%')or temporaryEndDate like binary('%"+queryStr+"%')or temporaryRequirement like binary('%"+queryStr+"%')or remark like binary('%"+queryStr+"%')";
		}
		return hql;
	}
	/**
	 * 分页查询列表
	 * @param secondment
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public Page<Secondment> queryPage(String queryStr, Integer currPage, int pageSize) throws Exception {
		String hql = generateQueryExample(queryStr);
		//返回查询结果
		Page<Secondment> page = new Page<Secondment>();
		List<Secondment> result = secondmentDao.findForPage(hql+" order by id desc",null, currPage, pageSize);
		int totalSize = secondmentDao.findforCount("select count(*) "+hql,null);	
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	/**
	 * 按挂职项目查询列表
	 * @param secondment
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public Page<Secondment> queryPageByProject(String queryStr, Integer currPage, int pageSize) throws Exception {
		String hql = generateQueryExample(queryStr);
		//返回查询结果
		Page<Secondment> page = new Page<Secondment>();
		List<Secondment> result = secondmentDao.findForPage(hql+" order by temporaryProjectName desc",null, currPage, pageSize);
		int totalSize = secondmentDao.findforCount("select count(*) "+hql,null);	
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	/**
	 * 批量插入挂职信息
	 * @param SecondmentList
	 * @return
	 */
	public boolean saveSecondmentsBatch(List<Secondment> SecondmentList){
		for(Secondment secondment : SecondmentList){
			if(secondment.getId() != null){
				secondmentDao.update(secondment);
			}else{
				secondmentDao.save(secondment);
			}
		}
		return true;		
	}
	
	/**
	 * 保存对象
	 * @param role
	 */
	public void saveSecondment(Secondment secondment) {	
		secondmentDao.save(secondment);
		}
	/**
	 * 修改对象
	 * @param fm
	 */
	public void updateSecondment(Secondment secondment){
		
		secondmentDao.update(secondment);
	}
	/**
	 * 删除
	 * @param id
	 */
	@Transactional
	public void delSecondment(Integer id) {
		if (null == id) {
			return;
		}		
		Secondment secondment = null;
		secondment = this.findById(id);
		if (null == secondment) return;		
		List<SecondmentUser> secondmentUsers  = null;
		secondmentUsers = secondmentUserService.findBySecondment(secondment);
		for (SecondmentUser secondmentUser : secondmentUsers) {
			secondmentUserService.delSecondmentUser(secondmentUser.getId());
		}
		secondmentDao.delete(Secondment.class, id);
	}	
	public Paper findByPaperNumber(String paperNumber){
		String sql = "from Paper where paperNumber=? ";
		List<Paper> result = paperDao.queryList(sql,new Object[]{paperNumber});
		if(result.size() == 0){
			return null;				
		}else{
			return result.get(0); 
		}
	}
	
	/**
	 * 按照paper ID查找挂职详情
	 * @return
	 */
	public List<Secondment> findSeconsmentByPaperId(Integer id){
		String hql = "from Secondment as pu where (pu.delFlag is null or pu.delFlag=1) and pu.paper.id = ?";
		List<Secondment> secondment = secondmentDao.queryList(hql, new Object[]{id});
		return secondment;	
	}
	
	@Transactional
	public void saveSecondmentUsers(Secondment secondment,
			List<SecondmentUserParam> secondmentUserList) {
		// TODO Auto-generated method stub
		SecondmentUser secondmentUser = null;
		User user = null;
		List<SecondmentUser> secondmentUsers = new ArrayList<SecondmentUser>();
		for (SecondmentUserParam secondmentUserParam : secondmentUserList) {
			secondmentUser = new SecondmentUser();
			if (null == secondmentUserParam.getUserId()) continue;
			user = userService.findById(secondmentUserParam.getUserId());
			if (null == user) continue;
			secondmentUser.setAssessementSitutation(secondmentUserParam.getAssessementSitutation() == null ? null : secondmentUserParam.getAssessementSitutation());
			secondmentUser.setRemark(secondmentUserParam.getRemark());
			secondmentUser.setSecondment(secondment);
			secondmentUser.setTemporaryJob(secondmentUserParam.getTemporaryJob() == null ? null : secondmentUserParam.getTemporaryJob());
			secondmentUser.setTemporaryPlace(secondmentUserParam.getTemporaryPlace() == null ? null : secondmentUserParam.getTemporaryPlace());
			secondmentUser.setTemporaryUnit(secondmentUserParam.getTemporaryUnit() == null ? null : secondmentUserParam.getTemporaryUnit());
			secondmentUser.setUser(user);
			if (null == secondmentUserService.findBySecondmentAndUser(secondment,user)) {
				secondmentUsers.add(secondmentUser);
			}
			user = null;
			secondmentUser = null;
		}
		secondmentUserService.saveSecondmentUsersBatch(secondmentUsers);
	}
}