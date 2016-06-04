package com.cadre.controller.userTrain;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.chart.DatRecord;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.controller.pojo.BirthPlace;
import com.cadre.controller.pojo.OrigionPlace;
import com.cadre.controller.pojo.PositionSimple;
import com.cadre.controller.pojo.TrainUserSave;
import com.cadre.controller.pojo.TrainUserSimple;
import com.cadre.controller.pojo.UserSimple;
import com.cadre.controller.pojo.WorkingPaperShowView;
import com.cadre.controller.sys.UserController;
import com.cadre.model.page.Page;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.Country;
import com.cadre.pojo.Organization;
import com.cadre.pojo.Place;
import com.cadre.pojo.Position;
import com.cadre.pojo.PositionPostuser;
import com.cadre.pojo.PostHistory;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.Skill;
import com.cadre.pojo.User;
import com.cadre.pojo.Train;
import com.cadre.pojo.TrainUser;
import com.cadre.pojo.WorkingPaper;
import com.cadre.service.appointment.PositionPostUserService;
import com.cadre.service.appointment.PostUserService;
import com.cadre.service.position.PositionService;
import com.cadre.service.position.PostHistoryService;
import com.cadre.service.skill.SkillService;
import com.cadre.service.sys.OrganizationService;
import com.cadre.service.sys.SysRoleService;
import com.cadre.service.sys.UserService;
import com.cadre.service.sys.UserTrainService;
//import com.sun.org.apache.bcel.internal.generic.RETURN;

@Controller
@RequestMapping(ViewLocation.FOLDER_USERTRAIN_INFO)
public class UserTrainController {
	private Logger logger = LogManager.getLogger(UserController.class);
	@Autowired
	UserTrainService usertrainService;
	@Autowired
	PostHistoryService postHistoryService;
	@Autowired
	UserService userService;
	@Autowired
	PositionService positionService;
	@Autowired
	PositionPostUserService positionPostUserService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired		
	HttpSession session;
	@Autowired 
	SkillService skillService;
	int pageSize=10;
	
	@RequestMapping("/UserTrainList")
	public String UserTrainList(Integer currPage,String queryStr,Model model) throws Exception{
		
		try {
			if(currPage == null) currPage = 1;//默认显示第一页
			Page<Train> queryList = usertrainService.findUserTrainByPage(queryStr, currPage, pageSize);
		//	model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			
			
			//boolean isAdmin = WebApplication.getCurrUser().isAdmin();
			//boolean hasAdminQx = sysUserService.findUserHasRole(WebApplication.getCurrUser().getId());
			//model.addAttribute("isAdmin", isAdmin||hasAdminQx);
			if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
				model.addAttribute("roleType", "admin");
			}else {
				model.addAttribute("roleType", "visitor");
			}
			return ViewLocation.FOLDER_USERTRAIN_INFO+"/UserTrainList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	@RequestMapping("/UserTrainDetail")
	public String UserTrainDetail(Integer id,Model model) throws Exception{	
		try {
			Train train = usertrainService.findById(id);
			model.addAttribute("ut", train);
			List<TrainUser> trainuserresult = usertrainService.findTrainUserByTrainId(id);
			model.addAttribute("tu", trainuserresult);
			//boolean isAdmin = WebApplication.getCurrUser().isAdmin();
			//boolean hasAdminQx = sysUserService.findUserHasRole(WebApplication.getCurrUser().getId());
			//model.addAttribute("isAdmin", isAdmin||hasAdminQx);
			return ViewLocation.FOLDER_USERTRAIN_INFO+"/UserTrainDetail";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 高级搜索功能
	 * @param currPage
	 * @param trainingName
	 * @param trainingPeriod
	 * @param organizer
	 * @param trainingPlace
	 * @param beginDay
	 * @param beginDayOperator
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("SuperSearchTrainUserList")
	public String SuperSearchTrainUserList(Integer currPage,String trainingName,String trainingPeriod,String organizer,String trainingPlace,Date beginDay,String beginDayOperator,Model model) throws Exception{
		Page<Train> queryList = usertrainService.findSearchTrainByPage(trainingName, trainingPeriod, organizer, trainingPlace, beginDay, beginDayOperator, currPage, pageSize);
		model.addAttribute("queryList",queryList);
		return ViewLocation.FOLDER_USERTRAIN_INFO+"/UserTrainList";
		
	}
	/**
	 * 跳转，培训项目详情页面
	 * @return
	 */
	@RequestMapping("/openTrainDetail")
	public String openTrainShow(Integer trainId,Model model){
		if (null == trainId){
			model.addAttribute("errMsg", "无此培训事件");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
		Train train = usertrainService.findById(trainId);
		if (null == train){
			model.addAttribute("errMsg", "无此培训事件");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
		
		model.addAttribute("train", train);
		
		List<TrainUser> trainUsers = usertrainService.findTrainUserByTrainId(trainId);
		if (null == trainUsers) trainUsers = new ArrayList<TrainUser>();
		List<TrainUserSimple> trainUserSimples = new ArrayList<TrainUserSimple>();
		TrainUserSimple trainUserSimple = new TrainUserSimple();
		TrainUser trainUser;
		for (int i=0; i<trainUsers.size(); ++i){
			trainUserSimple = new TrainUserSimple();
			trainUser = trainUsers.get(i);
			//用户名
			trainUserSimple.setUserName(userService.findById(trainUser.getUser().getId()).getName());
			//岗位
			PositionSimple positionSimple = null;
			positionSimple = new PositionSimple();
			
			Position position =	trainUser.getPosition();
			if (null == position){
				positionSimple.setAcadmyName("无");
				positionSimple.setDepartmentName("");
				positionSimple.setPositionLevelName("无");
				positionSimple.setPositionName("");	
			}
			else{
				List<Organization> organizations = organizationService.queryAll();
				Map<String, Organization> orgMap = this.getOrgMap(organizations);
				
				positionSimple.setAcadmyName(orgMap.get(position.getAcademy()).getName());
				positionSimple.setDepartmentName(orgMap.get(position.getDepartment()).getName());
				positionSimple.setPositionLevelName(ViewFunction.getPositionLevel(position.getPositionLevel()));
				positionSimple.setPositionName(position.getPositionName());
			}
			trainUserSimple.setPositionSimple(positionSimple);
			//技术职务
			Skill skill = trainUser.getSkill();
			if (null == skill){
				trainUserSimple.setSkillName("无");
			}
			else{
				trainUserSimple.setSkillName(skill.getSkillName());
			}
			//加入列表
			trainUserSimples.add(trainUserSimple);	
		}
		model.addAttribute("trainUsers",trainUserSimples);
		
		return ViewLocation.FOLDER_USERTRAIN_INFO + "/trainDetail";
	}
	/**
	 * 跳转，培训人员删除界面
	 * @param trainId
	 * @param model
	 * @return
	 */
	@RequestMapping("/openTrainUserDelete")
	public String openTrainUserDelete(Integer trainId, Model model){
		if (null == trainId){
			model.addAttribute("essMsg","无此培训事件");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
		
		List<TrainUser> trainUsers = usertrainService.findTrainUserByTrainId(trainId);
		List<TrainUserSimple> trainUserSimples = new ArrayList<TrainUserSimple>();
		TrainUserSimple trainUserSimple = new TrainUserSimple();
		TrainUser trainUser;
		for (int i=0; i<trainUsers.size(); ++i){
			trainUserSimple = new TrainUserSimple();
			trainUser = trainUsers.get(i);
			//用户名
			trainUserSimple.setUserName(userService.findById(trainUser.getUser().getId()).getName());
			trainUserSimple.setTrainUserId(trainUser.getId());
			//岗位
			PositionSimple positionSimple = null;
			positionSimple = new PositionSimple();
			
			Position position =	trainUser.getPosition();
			if (null == position){
				positionSimple.setAcadmyName("无");
				positionSimple.setDepartmentName("");
				positionSimple.setPositionLevelName("无");
				positionSimple.setPositionName("");	
			}
			else{
				List<Organization> organizations = organizationService.queryAll();
				Map<String, Organization> orgMap = this.getOrgMap(organizations);
				
				positionSimple.setAcadmyName(orgMap.get(position.getAcademy()).getName());
				positionSimple.setDepartmentName(orgMap.get(position.getDepartment()).getName());
				positionSimple.setPositionLevelName(ViewFunction.getPositionLevel(position.getPositionLevel()));
				positionSimple.setPositionName(position.getPositionName());
			}
			trainUserSimple.setPositionSimple(positionSimple);
			//技术职务
			Skill skill = trainUser.getSkill();
			if (null == skill){
				trainUserSimple.setSkillName("无");
			}
			else{
				trainUserSimple.setSkillName(skill.getSkillName());
			}
			//加入列表
			trainUserSimples.add(trainUserSimple);	
		}
		model.addAttribute("trainUsers",trainUserSimples);
		return ViewLocation.FOLDER_USERTRAIN_INFO + "/trainUserDelete";
		
	}
	/**
	 * 跳转，培训信息修改界面
	 * @param trainId
	 * @param model
	 * @return
	 */
	@RequestMapping("/openTrainEdit")
	public String openTrainEdit(Integer trainId, Model model){
		if (null == trainId){
			model.addAttribute("errMsg", "无此培训事件");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
		Train train = usertrainService.findById(trainId);
		if (null == train){
			model.addAttribute("errMsg", "无此培训事件");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
		model.addAttribute("train", train);
		return ViewLocation.FOLDER_USERTRAIN_INFO + "/trainEdit";
	}
	/**
	 * 跳转，培训项目添加界面
	 * @return
	 */
	@RequestMapping("/openTrainAdd")
	public String openTrainAdd(){
		return ViewLocation.FOLDER_USERTRAIN_INFO + "/trainAdd";
	}

	/**
	 * 跳转，培训项目批量添加界面
	 * @return
	 */
	@RequestMapping("/openTrainBatchAdd")
	public String openTrainBatchAdd(){
		return ViewLocation.FOLDER_USERTRAIN_INFO + "/batchAdd";
	}
	/**
	 * 跳转，培训项目批量删除界面
	 * @return
	 */
	@RequestMapping("/openTrainBatchDelete")
	public String openTrainBatchDelete(){
		return ViewLocation.FOLDER_USERTRAIN_INFO + "/batchDelete";
	}
	/**
	 * 跳转，培训人员添加界面
	 * @return
	 */
	@RequestMapping("/openTrainAddUser")
	public String openTrainAddUser(String trainId, Model model){
		model.addAttribute("trainId", trainId);
		return ViewLocation.FOLDER_USERTRAIN_INFO + "/trainUserAdd";
	}
	/**
	 * 存入新培训事件
	 * @param train
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(Train train){
		if (null == train) return "fail";
		train.getRemark().replaceAll(" ","");
		train.getTrainingName().replaceAll(" ", "");
		train.getTrainingPlace().replaceAll(" ", "");
		try {
			usertrainService.save(train);
			return "success";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
	}
	/**
	 * 修改培训事件
	 * @param train
	 * @return
	 */
	@RequestMapping("updateTrain")
	@ResponseBody
	public String updateTrain(Train train){
		if (null == train) return "fail";
		train.getRemark().replaceAll(" ","");
		train.getTrainingName().replaceAll(" ", "");
		train.getTrainingPlace().replaceAll(" ", "");
		try {
			usertrainService.update(train);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
		
	}
	/**
	 * 删除培训事件
	 * @param trainId
	 * @return
	 */
	@RequestMapping("deleteTrain")
	@ResponseBody
	public String deleteTrain(Integer id){
		try {
			usertrainService.delete(id);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	} 
	/**
	 * 组织机构形成map
	 * @param organizations
	 * @return
	 */
	private Map<String, Organization> getOrgMap(List<Organization> organizations) {
		// TODO Auto-generated method stub
		Map<String, Organization> orgMap = new HashMap<String, Organization>();
		for (Organization organization : organizations){
			orgMap.put(organization.getCode(), organization);
		}
		return orgMap;
	}
	/**
	 * 传出可能的名字
	 * @param userName
	 * @return
	 */
	@RequestMapping("existUserName")
	@ResponseBody
	public List<User> existUserName(String userName){
		List<User> sList = new ArrayList<User>();
		if (null == userName){
			return sList;
		}
		int count = userService.queryCountByName(userName);
		if(count <= 0){
			return sList;
		}
		else{
			int t;
			//通过名字确定UserId
			List<User> uList= userService.findUsersByName(userName);
			//防止数据过多
			if (uList.size()>9) t=9;
				else t=uList.size();
			//定义需要的数据类型
			User user = null;
			//TrainUserSimple trainUserSimple = null;
			//传出所有可能的名字
			for (int i = 0; i < t; i++){
				//trainUserSimple = new TrainUserSimple();
				//trainUserSimple.setUserId(uList.get(i).getId());
				//trainUserSimple.setUserName(uList.get(i).getName());
				//sList.add(trainUserSimple);
				user = new User();
				user.setId(uList.get(i).getId());
				user.setName(uList.get(i).getName());
				user.setIdentifyNum(uList.get(i).getIdentifyNum());
				sList.add(user);
			}
			//trainUserSimple = null;
			user = null;
		}	
		return sList;
	}
	/**d
	 * 
	 * 根据用户id返回职位技术岗位信息
	 * @param userId
	 * @param trainId
	 * @return
	 */
	@RequestMapping("showTrainUserSimple")
	@ResponseBody
	public TrainUserSimple showTrainUserSimple(Integer userId,Integer trainId){
		Date trainBeginDay = usertrainService.findById(trainId).getBeginDay();
		if (userId == null) return null;
		//定义、声明
		TrainUserSimple trainUserSimple = null;
		trainUserSimple = new TrainUserSimple();
		trainUserSimple.setUserName(userService.findById(userId).getName());
		trainUserSimple.setUserId(userId);
		
		PositionSimple positionSimple = null;
		positionSimple = new PositionSimple();
		//判断该人员是否已存在于培训事件中
		boolean checkRepeat = usertrainService.isRepeat(trainId, userId);
		if (!checkRepeat){
			positionSimple.setId(-1);
		}
		else{
			//转换时间类型
			java.sql.Date trainBeginDate=new java.sql.Date(trainBeginDay.getTime());
			//get skillName
			Skill skill = skillService.searchWhenPost(userId, trainBeginDate);
			if (null == skill){
				trainUserSimple.setSkillId(0);
				trainUserSimple.setSkillName("无");
			}
			else{
				trainUserSimple.setSkillId(skill.getId());
				trainUserSimple.setSkillName(skill.getSkillName());
			}
			//get 罢免历史
			PostHistory postHistory = postHistoryService.searchWhenPost(userId, trainBeginDate);
			if (null ==postHistory){
				positionSimple.setId(0);
				positionSimple.setAcadmyName("无");
				positionSimple.setDepartmentName("");
				positionSimple.setPositionLevelName("无");
				positionSimple.setPositionName("");
				
			}
			else{
			//建立职位编码与名称的映射
			List<Organization> organizations = organizationService.queryAll();
			Map<String, Organization> orgMap = this.getOrgMap(organizations);
		
			//将需要的值赋给传出变量
			Position position = postHistory.getPosition();
			
			positionSimple.setId(position.getId());
			//positionSimple.setAcademy(positionPostuser.getPosition().getAcademy());
			//positionSimple.setDepartment(positionPostuser.getPosition().getDepartment());
			positionSimple.setAcadmyName(orgMap.get(position.getAcademy()).getName());
			positionSimple.setDepartmentName(orgMap.get(position.getDepartment()).getName());
			//positionSimple.setPositionLevel(positionPostuser.getPosition().getPositionLevel());
			positionSimple.setPositionLevelName(ViewFunction.getPositionLevel(position.getPositionLevel()));
			positionSimple.setPositionName(position.getPositionName());
			//positionSimple.setPositionType(positionPostuser.getPosition().getPositionType());
			//positionSimple.setPositionTypeName(ViewFunction.getPositionType(positionPostuser.getPosition().getPositionType()));
			}
		}
		trainUserSimple.setPositionSimple(positionSimple);
		
		return trainUserSimple;
	}
	/**
	 * 删除培训人员
	 * @param trainUserDelete
	 * @return
	 */
	@RequestMapping("deleteTrainUsers")
	public String deleteTrainUsers(TrainUserSave trainUserDelete){
		TrainUserSimple trainUserSimple;
		if (0 == trainUserDelete.getTrainUserSimples().size()) return "fail";
		for (int i=0; i<trainUserDelete.getTrainUserSimples().size(); ++i){
			trainUserSimple = trainUserDelete.getTrainUserSimples().get(i);
			if (null == trainUserSimple.getTrainUserId()) continue;
			usertrainService.deleteTrainUser(trainUserSimple.getTrainUserId());
		}
		return ViewLocation.FOLDER_MSGCOMMON + "/success";
		
	}
	/**
	 * 存储培训人员
	 * @param trainUserSave
	 * @return
	 */
	@RequestMapping("saveTrainUsers")
	public String saveTrainUsers(TrainUserSave trainUserSave){
		TrainUserSimple trainUserSimple;
		TrainUser trainUser = new TrainUser();
		User user = new User();
		Train train = new Train();
		//判断是否非空
		if (0 == trainUserSave.getTrainUserSimples().size()) return "fail";
		//依次存入每个人员
		for (int i=0;i<trainUserSave.getTrainUserSimples().size();i++){
			trainUser = new TrainUser();
			if (null == trainUserSave.getTrainUserSimples().get(i).getUserId()) continue;
			trainUserSimple = trainUserSave.getTrainUserSimples().get(i);
			
			user.setId(trainUserSimple.getUserId());
			trainUser.setUser(user);
			
			train.setId(trainUserSimple.getTrainId());
			trainUser.setTrain(train);
			
			usertrainService.save(trainUser);
			trainUser = null;
		}
		return ViewLocation.FOLDER_MSGCOMMON+"/success";
	} 
}
