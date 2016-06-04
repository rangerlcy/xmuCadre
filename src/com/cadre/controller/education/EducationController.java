//create by lcy 2015/07/30
package com.cadre.controller.education;

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
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.Organization;
import com.cadre.pojo.Paper;
import com.cadre.pojo.Train;
import com.cadre.pojo.User;

import com.cadre.service.education.EducationService;
import com.cadre.service.sys.UserService;


@Controller
@RequestMapping(ViewLocation.FOLDER_EDUCATION)
public class EducationController {
private Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	EducationService educationService;
	@Autowired
	UserService userService;

	int pageSize=15;
	
	
	/**
	 * 分页查询教育经历
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("educationList")
	public String educationList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<EducationHistory> queryList = educationService.findEducationByPage(queryStr, currPage, pageSize);
						
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
		return ViewLocation.FOLDER_EDUCATION+"/educationList";
	}
	
	
	/**
	 * 打开增加教育经历页面
	 * @param model
	 * @return
	 */
	@RequestMapping("openEducationAdd")
	public String openEducationAdd(Model model){
		return ViewLocation.FOLDER_EDUCATION +"/educationAdd";
	}
	
	/**
	 * 添加教育经历
	 * @param edu
	 * @return
	 */
	@RequestMapping("educationAdd")
	@ResponseBody
	public String educationAdd(EducationHistory edu,String userName,Integer userId,Model model){
		if (null == edu) return "fail";
		try {
			if(null == userId) return "fail";
			
			User ur = userService.findById(userId); 		
			edu.setUser(ur);
			educationService.add(edu);
			return "success";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 打开修改教育经历页面
	 * @param model
	 * @return
	 */
	@RequestMapping("openEducationEdit")
	public String openEducationEdit(Integer id,Model model){
		if(id==null){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		EducationHistory edu = educationService.findById(id);
		model.addAttribute("edu", edu);
		return ViewLocation.FOLDER_EDUCATION +"/educationEdit";
	}
	/**
	 * 修改教育经历
	 * @param edu
	 * @return
	 */
	@RequestMapping("educationEdit")
	@ResponseBody
	public String educationEdit(EducationHistory edu,Model model){
		if (null == edu) return "fail";
		try {
			if(null == edu.getId()) return "fail";
			EducationHistory eh = educationService.findById(edu.getId());	
			eh.setBeginDay(edu.getBeginDay());
			eh.setEndDay(edu.getEndDay());
			eh.setSchool(edu.getSchool());
			eh.setLearningForm(edu.getLearningForm());
			eh.setLearningState(edu.getLearningState());
			eh.setLearningPhase(edu.getLearningPhase());
			eh.setDegree(edu.getDegree());
			eh.setDegreeType(edu.getDegreeType());
			eh.setCheckCase(edu.getCheckCase());
			eh.setRemark(edu.getRemark());
			educationService.update(eh);
			return "success";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 打开批量删除教育经历
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openEducationMoreDel")
	public String openEducationMoreDel(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			int pagenum=15;
			Page<EducationHistory> queryList = educationService.findEducationByPage(queryStr, currPage, pagenum);
						
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			
			model.addAttribute("today",new Date());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return ViewLocation.FOLDER_EDUCATION+"/educationMoreDel";
	}
	
	/**
	 * 批量删除教育经历
	 * @param educationId
	 * @return
	 */
	@RequestMapping("educationManyDel")
	@ResponseBody
	public String educationManyDel(String str){
		String[] idSArray=str.split(";"); 
		try {
			for(int i=0; i<idSArray.length; i++){
				educationService.delete(Integer.parseInt(idSArray[i]));
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	} 
	
	/**
	 * 单个删除教育经历
	 * @param educationId
	 * @return
	 */
	@RequestMapping("educationDel")
	@ResponseBody
	public String educationDel(Integer id){
		try {
			educationService.delete(id);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	} 
}
