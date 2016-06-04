//create by cjl 2015/08/8
package com.cadre.controller.skill;

import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.controller.pojo.PositionPaperItem;
import com.cadre.controller.pojo.PositionPaperParam;
import com.cadre.controller.sys.UserController;
import com.cadre.model.page.Page;
import com.cadre.model.utils.DateUtil;
import com.cadre.model.view.ViewFunction;

import com.cadre.pojo.Organization;
import com.cadre.pojo.Paper;
import com.cadre.pojo.Skill;
import com.cadre.pojo.Train;
import com.cadre.pojo.User;


import com.cadre.service.skill.SkillService;
import com.cadre.service.sys.UserService;


@Controller
@RequestMapping(ViewLocation.FOLDER_SKILL)
public class SkillController {
private Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	SkillService skillService;
	@Autowired
	UserService userService;

	int pageSize=15;
	
	
	/**
	 * 分页查询专业技术职务经历
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("skillList")
	public String skillList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<Skill> queryList = skillService.findSkillByPage(queryStr, currPage, pageSize);
						
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			
			model.addAttribute("today",new Date());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
			model.addAttribute("roleType", "admin");
		}else {
			model.addAttribute("roleType", "visitor");
		}
		return ViewLocation.FOLDER_SKILL+"/skillList";
	}
	
	
	/**
	 * 打开增加专业技术职务经历页面
	 * @param model
	 * @return
	 */
	@RequestMapping("openSkillAdd")
	public String openSkillAdd(Model model){
		return ViewLocation.FOLDER_SKILL +"/skillAdd";
	}
	
	/**
	 * 添加专业技术职务经历
	 * @param skill
	 * @return
	 */
	@RequestMapping("skillAdd")
	@ResponseBody
	public String skillAdd(Skill skill,String userName,Integer userId,Model model){
		if (null == skill) return "fail";
		try {
			if(null == userId) return "fail";
			
			User ur = userService.findById(userId); 		
			skill.setUser(ur);
			skillService.addSkill(skill);
			return "success";
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 打开修改专业技术职务经历页面
	 * @param model
	 * @return
	 */
	@RequestMapping("openSkillEdit")
	public String openSkillEdit(Integer id,Model model){
		if(id==null){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		Skill skill = skillService.findById(id);
		model.addAttribute("skill", skill);
		return ViewLocation.FOLDER_SKILL +"/skillEdit";
	}
	/**
	 * 修改专业技术职务经历
	 * @param skill
	 * @return
	 */
	@RequestMapping("skillEdit")
	@ResponseBody
	public String skillEdit(Skill skill,Model model){
		if (null == skill) return "fail";
		try {
			if(null == skill.getId()) return "fail";
			Skill sk = skillService.findById(skill.getId());	
			sk.setBeginDay(skill.getBeginDay());
			sk.setEndDay(skill.getEndDay());
			sk.setEmploymentLevel(skill.getEmploymentLevel());
			sk.setSkillName(skill.getSkillName());
			sk.setCheckCase(skill.getCheckCase());
			sk.setRemark(skill.getRemark());
			skillService.updateSkill(sk);
			return "success";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 打开批量删除专业技术职务经历
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openSkillMoreDel")
	public String opeSkillnMoreDel(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			int pagenum=15;
			Page<Skill> queryList = skillService.findSkillByPage(queryStr, currPage, pagenum);			
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			model.addAttribute("today",new Date());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return ViewLocation.FOLDER_SKILL+"/skillMoreDel";
	}
	
	/**
	 * 批量删除专业技术职务经历
	 * @param skillId
	 * @return
	 */
	@RequestMapping("skillManyDel")
	@ResponseBody
	public String skillManyDel(String str){
		String[] idSArray=str.split(";"); 
		try {
			for(int i=0; i<idSArray.length; i++){
				skillService.delete(Integer.parseInt(idSArray[i]));
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	} 
	
	/**
	 * 单个删除专业技术职务经历
	 * @param skillId
	 * @return
	 */
	@RequestMapping("skillDel")
	@ResponseBody
	public String skillDel(Integer id){
		try {
			skillService.delete(id);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	} 
}
