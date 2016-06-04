package com.cadre.controller.sys;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.cadre.common.ViewLocation;
import com.cadre.controller.pojo.WorkingPaperShowView;
import com.cadre.controller.sys.UserController;
import com.cadre.model.page.Page;
import com.cadre.pojo.WorkingPaper;
import com.cadre.pojo.PostUser;
import com.cadre.service.sys.OrganizationService;
import com.cadre.service.sys.SysRoleService;
import com.cadre.service.sys.AppointAndDismissService;

@Controller
@RequestMapping(ViewLocation.FOLDER_SYS_ADD)
public class AadController {
private Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	AppointAndDismissService appointAndDismissService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	HttpSession session;
	int pageSize=10;
	
	
}
