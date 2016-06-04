package com.cadre.controller.administrationWork;

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
import com.cadre.pojo.AdministrationWorkHistory;
import com.cadre.pojo.User;

import com.cadre.service.administrationWork.AdministrationService;
import com.cadre.service.sys.UserService;


@Controller
@RequestMapping(ViewLocation.FOLDER_ADMINISTRATIONWORK)
public class AdministrationWorkController {
private Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	AdministrationService administrationWorkService;
	@Autowired
	UserService userService;

	int pageSize=15;
	
	
	/**
	 * 分页查询行政工作经历
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("administrationWorkList")
	public String administrationWorkList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<AdministrationWorkHistory> queryList = administrationWorkService.findAdministrationWorkByPage(queryStr, currPage, pageSize);
						
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
		return ViewLocation.FOLDER_ADMINISTRATIONWORK+"/administrationWorkList";
	}
	
	
	/**
	 * 打开增加行政工作经历页面
	 * @param model
	 * @return
	 */
	@RequestMapping("openAdministrationWorkAdd")
	public String openAdministrationWorkAdd(Model model){
		return ViewLocation.FOLDER_ADMINISTRATIONWORK+"/administrationWorkAdd";
	}
	
	/**
	 * 添加行政工作经历
	 * @param adm
	 * @return
	 */
	@RequestMapping("administrationWorkAdd")
	@ResponseBody
	public String administrationWorkAdd(AdministrationWorkHistory adm, String units, String jobName,Integer userId,Model model){
		if (null == adm) return "fail";
		try {
			if(null == userId) return "fail";
			
			User ur = userService.findById(userId); 		
			adm.setUser(ur);
			adm.setUnits(units);
			adm.setJobName(jobName);
			administrationWorkService.add(adm);
			return "success";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 打开修改行政工作经历页面
	 * @param model
	 * @return
	 */
	@RequestMapping("openAdministrationWorkEdit")
	public String openAdministrationWorkEdit(Integer id,Model model){
		if(id==null){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		AdministrationWorkHistory adm = administrationWorkService.findById(id);
		model.addAttribute("adm",adm);
		return ViewLocation.FOLDER_ADMINISTRATIONWORK +"/administrationWorkEdit";
	}
	/**
	 * 修改行政工作经历
	 * @param adm
	 * @return
	 */
	@RequestMapping("administrationWorkEdit")
	@ResponseBody
	public String administrationWorkEdit(AdministrationWorkHistory adm,Model model){
		if (null == adm) return "fail";
		try {
			if(null == adm.getId()) return "fail";
			AdministrationWorkHistory ah = administrationWorkService.findById(adm.getId());	
			ah.setBeginDay(adm.getBeginDay());
			ah.setEndDay(adm.getEndDay());
			ah.setUnits(adm.getUnits());
			ah.setJobName(adm.getJobName());
			ah.setJobType(adm.getJobType());
			ah.setUserLevel(adm.getUserLevel());
			ah.setCheckCase(adm.getCheckCase());
			ah.setRemark(adm.getRemark());
			administrationWorkService.update(ah);
			return "success";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 打开批量删除行政工作经历
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openAdministrationWorkMoreDel")
	public String openAdministrationWorkMoreDel(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			int pagenum=15;
			Page<AdministrationWorkHistory> queryList = administrationWorkService.findAdministrationWorkByPage(queryStr, currPage, pagenum);
						
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			
			model.addAttribute("today",new Date());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return ViewLocation.FOLDER_ADMINISTRATIONWORK +"/administrationWorkMoreDel";
	}
	
	/**
	 * 批量删除行政工作经历
	 * @param administrationWorkId
	 */
	@RequestMapping("administrationWorkManyDel")
	@ResponseBody
	public String administrationWorkManyDel(String str){
		String[] idSArray=str.split(";"); 
		try {
			for(int i=0; i<idSArray.length; i++){
				administrationWorkService.delete(Integer.parseInt(idSArray[i]));
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	} 
	
	/**
	 * 单个删除行政工作经历
	 * @param administrationWorkId
	 * @return
	 */
	@RequestMapping("administrationWorkDel")
	@ResponseBody
	public String administrationWorkDel(Integer id){
		try {
			administrationWorkService.delete(id);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	} 
}
