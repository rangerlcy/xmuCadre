package com.cadre.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cadre.common.ViewLocation;
import com.cadre.controller.convertor.UserRoleConvertor;
import com.cadre.controller.pojo.TreeNodeVO;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.model.excel.ExcelImportorAdapter;
import com.cadre.model.page.Page;
import com.cadre.pojo.RoleResource;
import com.cadre.pojo.SysResource;
import com.cadre.pojo.SysRole;
import com.cadre.pojo.User;
import com.cadre.pojo.UserRole;
import com.cadre.service.sys.OrganizationService;
import com.cadre.service.sys.RoleResourceService;
import com.cadre.service.sys.SysResourceService;
import com.cadre.service.sys.SysRoleService;
import com.cadre.service.sys.UserService;
import com.cadre.service.sys.UserRoleService;

@Controller
@RequestMapping(ViewLocation.FOLDER_SYS_ROLE)
public class SysRoleController {
	private Logger logger = LogManager.getLogger(SysRoleController.class);
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	SysResourceService sysResourceService;
	@Autowired
	RoleResourceService roleResourceService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	UserService userService;
	int pageSize = 10;
	@RequestMapping(value="/sysRoleList")
	public String sysRoleList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if(currPage == null) currPage = 1;//默认显示第一页
			Page<SysRole> queryList = sysRoleService.queryPage(queryStr, currPage, pageSize);
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			return ViewLocation.FOLDER_SYS_ROLE+"/sysRoleList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addSysRole" )
	@ResponseBody
	public String addSysRole(SysRole role) throws Exception {
		try {
			sysRoleService.saveSysRole(role);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 检查角色是否已存在
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public String checkCode (String code) throws Exception {
		try {
			int count = sysRoleService.queryCountByProp("code",code);
			if(count == 0){
				return "success";
			}else{
				return "failure";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 根据id检查角色是否已经存在
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/checkCodeById")
	@ResponseBody
	public String checkCodeById (String code,Integer id) throws Exception {
		try {
			int count = sysRoleService.queryCountByIdAndCode(id,code);
			if(count == 0){
				return "success";
			}else{
				return "failure";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delSysRole")
	@ResponseBody
	public String delSysRole (Integer roleId) throws Exception {
		try {
			sysRoleService.delSysRole(roleId);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 修改角色信息前，先把角色信息查询出来
	 * @param roleId
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/preUpdateSysRole")
	public String preUpdateSysRole(Integer roleId,Model model) throws Exception {
		try {
			model.addAttribute("role", sysRoleService.queryById(roleId));
			return ViewLocation.FOLDER_SYS_ROLE+"/sysRoleUpdate";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 打开新增角色页面
	 * @param roleId
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/preAddSysRole")
	public String preAddSysRole(Model model) throws Exception {
		try {
			return ViewLocation.FOLDER_SYS_ROLE+"/sysRoleAdd";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 更新角色名称
	 * @param role
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateSysRole.do")
	@ResponseBody
	public String updateSysRole(SysRole role,Model model) throws Exception {
		try {
			sysRoleService.saveSysRole(role);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	/**
	 * 给角色分配资源
	 * @param roleId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/preAddRoleResouces.do")
	public String preAddRoleResouces(int roleId,Model model) throws Exception {
		try {
			//选出哪些资源是之前选过的
			Map<Integer,Boolean> checkedMap = new HashMap<Integer,Boolean> ();
			List<RoleResource> roleResourceList = roleResourceService.queryRoleResources(roleId);
			for(RoleResource roleResource : roleResourceList) {
				checkedMap.put(roleResource.getSysResource().getId(), true);
			}
			List<SysResource> allResources = sysResourceService.queryAll();
			for(SysResource sysResource : allResources) {
				if(checkedMap.get(sysResource.getId()) == null) {
					checkedMap.put(sysResource.getId(), false);
				}
			}
			
			model.addAttribute("allResources", allResources);
			model.addAttribute("checkedMap", checkedMap);
			model.addAttribute("roleId", roleId);
			return ViewLocation.FOLDER_SYS_ROLE+"/sysRoleAddResources";
		}  catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 给角色分配用户
	 * @param roleId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/preAddRoleUser.do")
	public String preAddRoleUser(Integer roleId,Model model) throws Exception {
		try {
			//组织树显示数据
			List<TreeNodeVO> treeNodeList = new ArrayList<TreeNodeVO>();
			
			//选出哪些用户是之前选过的
			Map<String,Boolean> checkedMap = new HashMap<String,Boolean> ();
			
			//查出所有用户
			List<User> userList = userService.queryAll();
			for(User user: userList) {
				if(checkedMap.get(user.getId()) == null) {
					checkedMap.put(String.valueOf(user.getId()), false);
				}
				TreeNodeVO tnVo = new TreeNodeVO();
				tnVo.setId(String.valueOf(user.getId()));
				tnVo.setpId("0");
				tnVo.setName(user.getName());
				tnVo.setNocheck(false);
				treeNodeList.add(tnVo);
			}
			
			List<UserRole> userRoleList = userRoleService.queryUserByRoleId(roleId);
			for(UserRole ur : userRoleList) {
				checkedMap.put(String.valueOf(ur.getUser().getId()), true);
			}
			model.addAttribute("treeNodeList", treeNodeList);
			model.addAttribute("checkedMap", checkedMap);
			model.addAttribute("roleId", roleId);
			return ViewLocation.FOLDER_SYS_ROLE+"/sysRoleAddUser";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 给角色分配资源，从本质上来说都是更新，就算是从没给角色分配过资源，也是从已经分配0个资源给角色更新成分配多个资源给角色
	 * @param roleId
	 * @param resourcesIdArr
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateRoleResouces.do")
	@ResponseBody
	public String updateRoleResouces(int roleId,String resourcsIds) throws Exception {
		try {
			List<Integer> resoucesIdList = new ArrayList<Integer>();
			if(!StringUtils.isBlank(resourcsIds)){
				String[] split = resourcsIds.split(",");
				for(String str : split) {
					if(StringUtils.isNotBlank(str)){
						resoucesIdList.add(Integer.valueOf(str));
					}
				}
			}
			roleResourceService.updateRoleResource(roleId, resoucesIdList);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 给角色分配用户，从本质上来说都是更新
	 * @param roleId
	 * @param resourcesIdArr
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateRoleUsers.do")
	@ResponseBody
	public String updateRoleUsers(int roleId,String userIds) throws Exception {
		try {
			List<Integer> userIdList = new ArrayList<Integer>();
			if(!StringUtils.isBlank(userIds)){
				String[] split = userIds.split(",");
				for(String str : split) {
					if(StringUtils.isNotBlank(str)){
						userIdList.add(Integer.valueOf(str));
					}
				}
			}
			userRoleService.updateRoleUser(roleId, userIdList);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 打开角色分配导入页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/preImportRoleUser")
	public String preImportRoleUser(Integer roleId,Model model){
		SysRole sysRole = sysRoleService.queryById(roleId);
		model.addAttribute("sysRole", sysRole);
		return ViewLocation.FOLDER_SYS_ROLE+"/importRoleUser";
	}
	
	/**
	 * 导入新员工操作
	 * @throws Exception 
	 */
	@RequestMapping("/importRoleUser")
	public String importRoleUser(@RequestParam("file") MultipartFile file,final Integer roleId, final Model model) throws Exception{
		try {
			ExcelImportorAdapter<User> importor = new ExcelImportorAdapter<User>() {
				@Override
				protected String importBiz(List<User> successList,
						List<DataErrorMsg> errorList){
					userRoleService.saveRoleUsersBatch(successList,roleId);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			SysRole sysRole = sysRoleService.queryById(roleId);
			UserRoleConvertor convertor = new UserRoleConvertor(list);
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			
			//返回导入页面
			model.addAttribute("sysRole", sysRole);
			return ViewLocation.FOLDER_SYS_ROLE+"/importRoleUser";		
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
}
