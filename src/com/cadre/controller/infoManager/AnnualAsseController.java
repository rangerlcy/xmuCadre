package com.cadre.controller.infoManager;


import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.controller.sys.UserController;
import com.cadre.model.page.Page;
import com.cadre.model.utils.DateUtil;
import com.cadre.pojo.Assess;
import com.cadre.pojo.User;
import com.cadre.service.infoManager.AssessService;
import com.cadre.service.sys.UserService;

@Controller
@RequestMapping(ViewLocation.FOLDER_INFOMANAGER_ANNUALASSE)
public class AnnualAsseController {
	private Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	AssessService assessService;
	@Autowired
	UserService userService;
	int pageSize=10;
	
	
	/**
	 * 考核列表
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("annualAsseList")
	public String annualAsseList(Integer currPage,String queryStr,String year,Model model) throws Exception{
		try {
			if(currPage == null) currPage = 1;//默认显示第一页
			if (year == null) year = String.valueOf(DateUtil.currentTime().getYear());
			Page<Assess> queryList = assessService.findAssessByPage(queryStr, currPage,year, pageSize);
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			model.addAttribute("year", year);
			model.addAttribute("today",new Date());
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
			model.addAttribute("roleType", "admin");
		}else {
			model.addAttribute("roleType", "visitor");
		}
		return ViewLocation.FOLDER_INFOMANAGER_ANNUALASSE+"/annualAsseList";
	}
	/**
	 * 根据个人查询考核历史
	 * 
	 */
	@RequestMapping("openUserAsseDetail")
	public String openUserAsseDetail(Integer userId,Model model){
		if (userId == null){
			model.addAttribute("errMsg", "无此用户，请从正确的途径打开");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		User user = userService.findById(userId);
		if (null == user){
			model.addAttribute("errMsg", "无此用户，请从正确的途径打开");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		List<Assess> asseList = assessService.findByUser(user);
		model.addAttribute("asseList",asseList);
		if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
			model.addAttribute("roleType", "admin");
		}else {
			model.addAttribute("roleType", "visitor");
		}
		return ViewLocation.FOLDER_INFOMANAGER_ANNUALASSE +"/userAsseList";
	}
	/**
	 * 删除年度考核信息
	 * @param id
	 * @return
	 */
	@RequestMapping("asseDel")
	@ResponseBody
	public String asseDel(Integer id){
		if (null == id){
			return "invalidId";
		}
		Assess assess = assessService.findById(id);
		if (assess != null)
			assessService.delete(assess);
		return "success";
	}
	/**
	 * 打开编辑考核信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("openAsseEdit")
	public String openAsseEdit(Integer id,Model model){
		if (null == id){
			model.addAttribute("errMsg", "无此用户，请从正确的途径打开");
			return "fail";
		}
		Assess assess = assessService.findById(id);
		if (assess != null){
			model.addAttribute("asse", assess);
			return ViewLocation.FOLDER_INFOMANAGER_ANNUALASSE+"/asseEidt";
		}else {
			model.addAttribute("errMsg", "无此用户，请从正确的途径打开");
			return "fail";
		}
	}
	
	@RequestMapping("asseEdit")
	@ResponseBody
	public String asseEdit(Assess ass){
		if (null == ass || null == ass.getId()){
			return "invalidId";
		}
		Assess assess = assessService.findById(ass.getId());
		if (null == assess){
			return "invalidId";
		}
		assess.setAssessmentResult(ass.getAssessmentResult());
		assess.setYear(ass.getYear());
		assessService.update(assess);
		return "success";
	}
	/**
	 * 打开考核页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("openAsseAdd")
	public String openAsseAdd(Integer userId,Model model){
		if (null == userId){
			model.addAttribute("errMsg", "无此员工");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		User user  = userService.findById(userId);
		if (null == user){
			model.addAttribute("errMsg", "无此员工");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		model.addAttribute("user", user);
		return ViewLocation.FOLDER_INFOMANAGER_ANNUALASSE +"/asseAdd";
	}
	
	@RequestMapping("asseAdd")
	@ResponseBody
	public String asseAdd(Integer userId,Assess ass,Model model){
		if (null == userId){
			return "invalidId";
		}
		User user  = userService.findById(userId);
		if (null == user){
			return "invalidId";
		}
		ass.setUser(user);
		ass.setDelFlag(1);
		assessService.save(ass);
		return "success";
	}
}