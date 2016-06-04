package com.cadre.service.report;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.controller.report.ReportParam;
import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.DateUtil;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.Report;
import com.cadre.pojo.ReportFile;
import com.cadre.pojo.User;
@Service

public class ReportService {
	@Autowired
	BaseDao<Report> reportDao;
	@Autowired
	BaseDao<ReportFile> reportFileDao;
	/**
	 * 查找所有用户
	 * @return
	 */
	public List<Report> queryAll(){
		String hql = "from Report ";
		return reportDao.queryList(hql,null);
	}
	public List<Report> findReportForExport(String queryStr) {
		// TODO Auto-generated method stub
		String sql = "from Report ";//查询对象
		String where = " (delFlag is null or delFlag=1) ";//查询条件
		Object[] args = null;
		if(StringUtils.isNotBlank(queryStr)){
			where += " and (user.name like binary('%"+queryStr+"%') or informer like binary('%"+queryStr+"%')or reportedDay like binary('%"+queryStr+"%')or reportedWay like binary('%"+queryStr+"%')or reportedType like binary('%"+queryStr+"%')or reportedContent like binary('%"+queryStr+"%')or processingAndConclusion like binary('%"+queryStr+"%')or processingAndConclusionType like binary('%"+queryStr+"%')or remark like binary('%"+queryStr+"%') ) ";
		}
		sql = sql + " where "+where;
		List<Report> result = reportDao.queryList(sql, args);
		
		return result;	
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
	public Page<Report> findSearchReportByPage(ReportParam reportParam,int currPage,int pageSize) throws Exception{
		String hql="from Report where (delFlag is null or delFlag = 1)";
		if (StringUtil.isNotBlank(reportParam.getUser())){
			 hql+= " and user.name like '%"+reportParam.getUser()+"%'";
		}
		if (StringUtil.isNotBlank(reportParam.getInformer())){
		
			hql+= " and informer like '%"+reportParam.getInformer()+"%'";
		}
		if (reportParam.getReportedDay()!=null && !reportParam.getReportedDay().equals("")){
		
		hql += " and reportedDay "+this.convertOperator(reportParam.getReportedDayOperator())+" '"+DateUtil.parseDate(reportParam.getReportedDay(),"yyyy-MM-dd")+"'";
	}
		if (StringUtil.isNotBlank(reportParam.getReportedWay())){
			hql+=" and reportedWay ='"+reportParam.getReportedWay()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getReportedType())){
			hql+=" and reportedType ='"+reportParam.getReportedType()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getReportedContent())){
			hql+=" and reportedContent ='"+reportParam.getReportedContent()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getProcessingAndConclusion())){
			hql+=" and processingAndConclusion ='"+reportParam.getProcessingAndConclusion()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getProcessingAndConclusionType())){
			hql+=" and processingAndConclusionType ='"+reportParam.getProcessingAndConclusionType()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getRemark())){
			hql+=" and remark ='"+reportParam.getRemark()+"'";
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
		Page<Report> page = new Page<Report>();
		Object[] args=null;
		List<Report> result =  reportDao.findForPage(hql, args, currPage, pageSize);
		int totalSize = reportDao.findforCount("select count(*) "+hql, args);
		page.setResult(result);
		page.setPageSize(pageSize);
		page.setCurrPage(currPage);
		page.setTotalSize(totalSize);
		return page;
	}
	public List<Report> findSuperReportForExport(ReportParam reportParam) {
		String hql="from Report where  (delFlag is null or delFlag = 1)";
		if (StringUtil.isNotBlank(reportParam.getUser())){
			 hql+= " and user.name like binary('%"+reportParam.getUser()+"%')";
		}
		if (StringUtil.isNotBlank(reportParam.getInformer())){
		
			hql+= " and informer ='"+reportParam.getInformer()+"'";
		}
		if (reportParam.getReportedDay()!=null){
		
		hql += " and reportedDay "+this.convertOperator(reportParam.getReportedDayOperator())+" '"+ DateUtil.parseDate(reportParam.getReportedDay(),"yyyy-MM-dd")+"'";
	}
		if (StringUtil.isNotBlank(reportParam.getReportedWay())){
			hql+=" and reportedWay ='"+reportParam.getReportedWay()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getReportedType())){
			hql+=" and reportedType ='"+reportParam.getReportedType()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getReportedContent())){
			hql+=" and reportedContent ='"+reportParam.getReportedContent()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getProcessingAndConclusion())){
			hql+=" and processingAndConclusion ='"+reportParam.getProcessingAndConclusion()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getProcessingAndConclusionType())){
			hql+=" and processingAndConclusionType ='"+reportParam.getProcessingAndConclusionType()+"'";
		}
		if (StringUtil.isNotBlank(reportParam.getRemark())){
			hql+=" and remark ='"+reportParam.getRemark()+"'";
		}
		return reportDao.queryList(hql, new Object[]{});
	}
	/**
	 * 根据属性进行统计
	 * @param propName
	 * @param value
	 * @return
	 */
	public int getCount(String propName, Object value){
		String sql = "select count(*) from User as model where (model.delFlag=1 or model.delFlag is null) and model."+propName+"= ?";
		return reportDao.findforCount(sql,new Object[]{value});
	}
	/**
	 * 根据id和属性进行统计
	 * @param propName
	 * @param value
	 * @param id
	 * @return
	 */
	public int getCount(String propName, Object value,Integer id){
		String sql = "select count(*) from Report as model where (model.isdel=0 or model.isdel is null) and model."+propName+"= ? and model.id<>?";
		return reportDao.findforCount(sql,new Object[]{value,id});
	}
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public Report findById(Integer id){
		return reportDao.findOne(Report.class,id);
	}
	/**
	 * 封装查询条件
	 * @param report
	 * @return
	 */
	private String generateQueryExample(String queryStr) {
		String hql = " from Report ";
		if(StringUtils.isNotBlank(queryStr)) {
			hql += " where user.name like binary('%"+queryStr+"%') or informer like binary('%"+queryStr+"%')or reportedDay like binary('%"+queryStr+"%')or reportedWay like binary('%"+queryStr+"%') or reportedType like binary('%"+queryStr+"%')or reportedContent like binary('%"+queryStr+"%')or processingAndConclusion like binary('%"+queryStr+"%')or processingAndConclusionType like binary('%"+queryStr+"%') or remark like binary('%"+queryStr+"%')";
		}
		return hql;
	}
	/**
	 * 分页查询列表
	 * @param report
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public Page<Report> queryPage(String queryStr, Integer currPage, int pageSize) throws Exception {
		String hql = generateQueryExample(queryStr);
		//返回查询结果
		Page<Report> page = new Page<Report>();
		List<Report> result = reportDao.findForPage(hql+" order by id desc",null, currPage, pageSize);
		int totalSize = reportDao.findforCount("select count(*) "+hql,null);	
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;
	}
	
	/**
	 * 保存对象
	 * @param report
	 */
	public void saveReport(Report report) {
			reportDao.save(report);
		}
	/**
	 * 修改对象
	 * @param report
	 */
	public void updateReport(Report report){
		
		reportDao.update(report);
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delReport(Integer id) {
		
		reportDao.delete(Report.class, id);
	}
	
	
	/**
	 * 附件表的相关操作
	 * @return 
	 */
	public void saveReportFile(ReportFile file){
		reportFileDao.save(file);
	}
	//添加附件
	public void addfile(ReportFile repfile) {
		// TODO Auto-generated method stub
		saveReportFile(repfile);
	}
	//根据reportid进行查询
	public List<ReportFile> querybyReportid(String reportid){
		Object[] args = null;
		String hqlString="from ReportFile rf";
		String where = " (rf.delFlag is null or rf.delFlag = 1) ";// 只查询没有删除的记录
		where = where + " and (rf.reportId = "+reportid+")";
		hqlString = hqlString + " where "+where;
		List<ReportFile> result = reportFileDao.queryList(hqlString,args);		
		return result;		
	}
	
	//根据id删除附件
	public void deletefileby(Integer id) {
		// TODO Auto-generated method stub
		ReportFile rf = reportFileDao.findOne(ReportFile.class, id);
		rf.setDelFlag(0);
		reportFileDao.update(rf);
	}
}