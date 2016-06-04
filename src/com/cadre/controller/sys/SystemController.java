package com.cadre.controller.sys;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cadre.security.pojo.LoginUserDetails;
import com.cadre.service.sys.OrganizationService;
import com.cadre.service.sys.PlaceService;
import com.cadre.service.sys.SysResourceService;
import com.cadre.service.sys.SysRoleService;
import com.cadre.service.sys.UserService;
import com.cadre.common.WebApplication;
import com.cadre.controller.sys.SystemController;

@Controller
public class SystemController{
	private Logger logger = LogManager.getLogger(SystemController.class);
	@Autowired
	UserService userService;
	@Autowired
	SysResourceService sysResourceService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	PlaceService placeService;
	@Autowired
	OrganizationService organizationService;
	/**
	 * 打开首页
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/index")
	public String index(Model model) throws Exception{
		try {
			LoginUserDetails loginUserDetails = WebApplication.getCurrUser();
			if(loginUserDetails != null){
				model.addAttribute("sysUser", loginUserDetails.getUser());
			}
			return "index";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}	
}
