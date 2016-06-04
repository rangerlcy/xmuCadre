package com.cadre.controller.sys;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cadre.common.ViewLocation;
import com.cadre.pojo.SysResource;
import com.cadre.service.sys.SysResourceService;

@Controller
@RequestMapping(ViewLocation.FOLDER_SYS_RESOURCE)
public class ResourceController {
	@Autowired
	SysResourceService sysResourceService;
	private Logger logger = LogManager.getLogger(ResourceController.class);
	/**
	 * 打开资源树
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/resourceTree")
	public String resourceTree(Model model) throws Exception{
		try {
			List<SysResource> allResources = sysResourceService.queryAll();
			model.addAttribute("allResources", allResources);
 			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return ViewLocation.FOLDER_SYS_RESOURCE+"/resourceTree";
	}
	/**
	 * 修改资源信息前，查出对应的资源信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("preSysResourcesUpdate")
	public String preSysResourcesUpdate(Integer id,Model model) throws Exception{
		try {
			SysResource resource = sysResourceService.queryById(id);
			model.addAttribute("resources", resource);
			return ViewLocation.FOLDER_SYS_RESOURCE+"/resourcesUpdate";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@RequestMapping("/updateSysResources")
	@ResponseBody
	public String updateSysResources(SysResource resource,Model model) throws Exception {
		try {
			sysResourceService.saveSysResources(resource);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@RequestMapping("/addSysResources")
	@ResponseBody
	public String addSysResources(SysResource resource,Model model) throws Exception {
		try {
			int saveSysResources = sysResourceService.saveSysResources(resource);
			return "success#"+saveSysResources;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@RequestMapping("/delSysResources")
	@ResponseBody
	public String delSysResources(Integer id,Model model) throws Exception {
		try {
			sysResourceService.delSysResourcesById(id);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
}
