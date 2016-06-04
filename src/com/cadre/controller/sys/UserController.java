package com.cadre.controller.sys;

import java.util.Date;
import java.util.List;

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
import com.cadre.common.WebApplication;
import com.cadre.controller.convertor.UserConvertor;
import com.cadre.controller.pojo.UserShowView;
import com.cadre.model.page.Page;
import com.cadre.model.utils.DigestUtil;
import com.cadre.model.utils.StringUtil;
import com.cadre.pojo.SysRole;
import com.cadre.pojo.User;
import com.cadre.pojo.UserRole;
import com.cadre.service.sys.SysRoleService;
import com.cadre.service.sys.UserService;
import com.cadre.service.sys.UserRoleService;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.model.excel.ExcelImportorAdapter;

@Controller
@RequestMapping(ViewLocation.FOLDER_SYS_USER)
public class UserController {
	private Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	UserService userService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	UserRoleService userRoleService;
	int pageSize = 10;
	/**
	 * 查询用户列表
	 * @param currPage 当前页
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userList")
	public String userList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if(currPage == null) currPage = 1;//默认显示第一页
			Page<UserShowView> queryList = userService.findUserByPage(queryStr,currPage, pageSize);
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			
			List<SysRole> sysRoles = sysRoleService.queryAll();
			model.addAttribute("sysRoles",sysRoles);
			return ViewLocation.FOLDER_SYS_USER+"/userList";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 打开新增用户界面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openUserAdd")
	public String openUserAdd(Model model) throws Exception{
		try {
			List<SysRole> sysRoles = sysRoleService.queryAll();
			model.addAttribute("sysRoles", sysRoles);
			
			return ViewLocation.FOLDER_SYS_USER+"/userAdd";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 打开修改用户界面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openUserEdit")
	public String openUserEdit(Integer id,Model model) throws Exception{
		try {
			User user = userService.findById(id);
			List<SysRole> sysRoles = sysRoleService.queryAll();
			List<UserRole> userRoles = userRoleService.queryRoleById(id);
			model.addAttribute("sysUser", user);
			model.addAttribute("userRoles", userRoles);
			model.addAttribute("sysRoles", sysRoles);
			
			return ViewLocation.FOLDER_SYS_USER+"/userEdit";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	

	/**
	 * 检查用户名是否被占用
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkUsername")
	@ResponseBody
	public String checkUsername(String username) throws Exception {
		try {
			int count = userService.queryCount("username",username);
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
	 * 新增用户
	 * @param user
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userAdd")
	@ResponseBody
	public String userAdd(User user,String roleIds,Model model) throws Exception{
		try {
			user.setPassWord(DigestUtil.encryptPWD(user.getUserName()));
			userService.addUser(user,roleIds);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 修改用户
	 * @param user
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userEdit")
	@ResponseBody
	public String userEdit(User user,Integer userId,String roleIds,Model model) throws Exception{
		try {
			User user1 = userService.findById(userId);
			user.setName(user1.getName());
			userService.updateUserRole(user1,roleIds);		
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userDel")
	@ResponseBody
	public String userDel(Integer id,Model model) throws Exception{
		try {
			userService.deleteUser(id);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 打开导入新员工页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/preImport")
	public String preImport(Model model){
		return ViewLocation.FOLDER_SYS_USER+"/import";
	}
	/**
	 * 导入新员工操作
	 * @throws Exception 
	 */
	@RequestMapping("/importInfo")
	public String importInfo(@RequestParam("file") MultipartFile file, final Model model) throws Exception{
		try {
			ExcelImportorAdapter<User> importor = new ExcelImportorAdapter<User>() {
				@Override
				protected String importBiz(List<User> successList,
						List<DataErrorMsg> errorList){
					userService.saveUsersBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			UserConvertor convertor = new UserConvertor(list);
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
			return ViewLocation.FOLDER_SYS_USER+"/import";		
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 打开修改密码页面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openChangPwd")
	public String openChangPwd(boolean showTs,Model model) throws Exception{
		try {
			User user = WebApplication.getCurrUser().getUser();
			model.addAttribute("sysUser", user);
			return ViewLocation.FOLDER_SYS_USER+"/changePwd";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 打开重置密码页面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openResetPwd")
	public String openResetPwd(Integer id,Model model) throws Exception{
		try {
			User user = userService.findById(id);
			model.addAttribute("sysUser", user);
			
			return ViewLocation.FOLDER_SYS_USER+"/resetPwd";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 重置密码操作
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resetPwd")
	@ResponseBody
	public String resetPwd(User user,Model model) throws Exception{
		try {
			User user1 = userService.findById(user.getId());
			user.setPassWord(DigestUtil.encryptPWD(user1.getPassWord()));
			boolean ret = userService.updateUser(user1);
			if(ret){
				return "success";
			}else {
				return "failse";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 修改密码操作
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changePwd")
	@ResponseBody
	public String changePwd(User user,Model model) throws Exception{
		try {
			User user1 = userService.findById(user.getId());
			user.setPassWord(DigestUtil.encryptPWD(user1.getPassWord()));
			boolean ret = userService.updateUser(user1);
			if(ret){
				return "success";
			}else {
				return "failse";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	

}
