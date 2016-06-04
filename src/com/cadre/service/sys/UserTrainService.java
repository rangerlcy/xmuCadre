package com.cadre.service.sys;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.hql.ast.SqlASTFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//import sun.text.normalizer.IntTrie;


import com.cadre.common.ViewLocation;
import com.cadre.controller.pojo.PostUserShowView;
import com.cadre.controller.pojo.WorkingPaperShowView;
import com.cadre.dao.BaseDao;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.AdministrationLevelHistory;
import com.cadre.pojo.AdministrationWorkHistory;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.RoleResource;
import com.cadre.pojo.SysRole;
import com.cadre.pojo.User;
import com.cadre.pojo.Position;
import com.cadre.pojo.Train;
import com.cadre.pojo.TrainUser;
import com.cadre.pojo.UserRole;
import com.cadre.pojo.WorkingPaper;
import com.cadre.service.infoManager.AdministrationWorkService;
import com.cadre.service.infoManager.AdminstrationLevelService;
//import com.sun.org.apache.xpath.internal.operations.And;
//import com.sun.xml.internal.bind.v2.runtime.Name;
@Service
public class UserTrainService {
	private Logger logger = LogManager.getLogger(AppointAndDismissService.class);
	@Autowired
	BaseDao<Train> TrainDao;
	@Autowired
	BaseDao<TrainUser> TrainUserDao;
	@Autowired
	BaseDao<User> UserDao;
	@Autowired
	AdminstrationLevelService adminstrationLevelService;
	@Autowired
	AdministrationWorkService administrationWorkService;
	
	/**
	 * 查找所有培训信息
	 * @return
	 */
	public List<Train> queryAll(){
		String hql = "from Train where delFlag is null or delFlag = 1";
		return TrainDao.queryList(hql,null);
	}
	
	
	/**
	 * 分页查询任免数据
	 * @param queryStr
	 * @param queryDept
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public Page<Train> findUserTrainByPage(String queryStr,int currPage,int pageSize) throws Exception{
		String sql = "from  Train as tr   ";//查询对象
		String where = " (tr.delFlag is null or tr.delFlag=1) ";//查询条件
		Object[] args = null;
		if(StringUtils.isNotBlank(queryStr)){
			where += " and ( tr.trainingName like '%"+queryStr+"%' or tr.trainingPeriod like '%"+queryStr+"%'or tr.organizer like '%"+queryStr+"%'or tr.trainingPlace like '%"+queryStr+"%') ";
		}
		sql = sql + " where "+where + "order by tr.id asc";
		Page<Train> page = new Page<Train>();
		//List<WorkingPaper> WorkingPaperResult = WorkingPaperDao.findForPage(sql+" order by wp.id desc",args, currPage, pageSize);
		List<Train> result = TrainDao.findForPage(sql,args, currPage, pageSize);
		int totalSize = TrainDao.findforCount("select count(*) "+sql,args);
		
		/*
		 * 需要补充
		*/
		
		page.setResult(result);
		page.setCurrPage(currPage);
		page.setPageSize(pageSize);
		page.setTotalSize(totalSize);
		return page;	
	}
	/**
	 * 根据hql分页查找
	 * @param trainingName
	 * @param trainingPeriod
	 * @param organizer
	 * @param trainingPlace
	 * @param beginDay
	 * @param beginDayOperator
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<Train> findSearchTrainByPage(String trainingName,String trainingPeriod,String organizer,String trainingPlace,Date beginDay,String beginDayOperator,int currPage,int pageSize) throws Exception{
		String hql = " from Train where (delFlag is null or delFlag = 1)";
		if (StringUtil.isNotBlank(trainingName)){
			trainingName = trainingName.replace(" ", "");
			hql += " and replace(trainingName,' ','') like '%" + trainingName +"%'";
		}
		if (StringUtil.isNotBlank(trainingPeriod)){
			//trainingPeriod = trainingPeriod.replace(" ", "");
			hql += " and trainingPeriod = " + trainingPeriod;
		}
		if (StringUtil.isNotBlank(organizer)){
			organizer = organizer.replace(" ", "");
			hql += " and replace(organizer,' ','') like '%" + organizer +"%'";
		}
		if (StringUtil.isNotBlank(trainingPlace)){
			trainingPlace = trainingPlace.replace(" ", "");
			hql += " and replace(trainingName,' ','') like '%" + trainingPlace +"%'";
		}
		if (beginDay!=null){
			hql += " and (beginDay " + this.convertOperator(beginDayOperator) + " ' "+DateFormat.getDateInstance().format(beginDay) + "' )";
		}
		hql += " order by id desc";
		Page<Train> page = new Page<Train>();
		Object[] args = null;
		List<Train> trainResult = TrainDao.findForPage(hql, args, currPage, pageSize);
		int totalSize = TrainDao.findforCount("select count(*)"+hql, args);
		
		page.setResult(trainResult);
		page.setPageSize(pageSize);
		page.setCurrPage(currPage);
		page.setTotalSize(totalSize);
		return page;
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
	 * 按照ID查找培训事件
	 * @return
	 */
	public Train findById(Integer id){
		return TrainDao.findOne(Train.class, id);
	}
	/**
	 * 按照培训ID查找培训人员
	 * @return
	 */
	public List<TrainUser> findTrainUserByTrainId(Integer id){
		String hql = "from TrainUser as tu where (tu.delFlag is null or tu.delFlag=1) and tu.train.id = ?";
		List<TrainUser> trainUsers = TrainUserDao.queryList(hql, new Object[]{id});
		if (null == trainUsers || 0 == trainUsers.size()) return null;
		return trainUsers;	
	}
	/**
	 * 根据userId 和  trainId 判断人员是否已经存在于培训中
	 * @param trainId
	 * @param userId
	 * @return
	 */
	public boolean isRepeat(Integer trainId, Integer userId){
		String hql = "from TrainUser where user.id = ? and train.id = ?";
		List<TrainUser> trainUsers = TrainUserDao.queryList(hql, new Object[]{userId, trainId});
		if (0 == trainUsers.size()) return true;
		else return false;
		
	}
	/**
	 * 存入新培训事件
	 * @param train
	 * @param trainuser
	 */
	public void save(Train train , TrainUser trainUser){
		TrainDao.save(train);
		String sql = " from Train where trainingName = ?";
		List<Train> trains = TrainDao.queryList(sql, new Object[]{train.getTrainingName()});
		Train tra = trains.get(trains.size() - 1);
		trainUser.setTrain(tra);
		TrainUserDao.save(trainUser);
		return;
	}
	
	public void save(Train train){
		if (train == null) return;
		TrainDao.save(train);
		return;
	}
	
	public void save(TrainUser trainUser){
		if (trainUser == null) return;
		TrainUserDao.save(trainUser);
		return;
	}
	
	/**
	 * 批量添加培训信息
	 * @param UserList
	 * @return
	 */
	public boolean saveTrainsBatch(List<Train> TrainList){
		for(Train train : TrainList){
			if(train.getId() != null){
				TrainDao.update(train);
			}else{
				TrainDao.save(train);
			}
		}
		return true;		
	}
	/**
	 * 删除培训事件
	 * @param train
	 */
	public void delete(Integer id){
		//（真）删除
		String sql = " from TrainUser where train.id = ?"; 
		List<TrainUser> trainUsers = TrainUserDao.queryList(sql, new Object[]{id}); 
		int tu;
		//删除培训人员
		for (int i = 0; i < trainUsers.size(); ++i){
			tu = trainUsers.get(i).getId();
			TrainUserDao.delete(TrainUser.class , tu);
		}
		//删除培训项目信息
		TrainDao.delete(Train.class , id);
		return;
	}
	public void deleteTrainUser(Integer id){
		//（真）删除
		TrainUserDao.delete(TrainUser.class, id);
	}
	
	/**
	 * 批量删除培训信息
	 * @param TrainList
	 * @return
	 */
	public boolean delTrainsBatch(List<Train> TrainList) {
		// TODO Auto-generated method stub
		for(Train train : TrainList){
			if(train.getId() != null){
				delete(train.getId());
			}
		}
		return true;	
	}

	/**
	 * 修改培训事件
	 * @param train
	 */
	public void update(Train train){
		if (null == train) return;
		if (null == train.getId()) return;
		TrainDao.update(train);
		return;
	}
	
	public Integer getTrainUserByPosition(Position position){
		String hql = " from TrainUser where position = ?";
		List<TrainUser> trainUsers = TrainUserDao.queryList(hql, new Object[]{position});
		if (null == position) return 0 ;
			else return trainUsers.size();
	}
	
}
