package com.cadre.controller.infoManager;

import java.util.List;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cadre.common.ViewLocation;
import com.cadre.controller.convertor.AsseConvertor;
import com.cadre.controller.convertor.AsseDelConvertor;
import com.cadre.controller.convertor.UserDelConvertor;
import com.cadre.controller.convertor.UserInfoConvertor;
import com.cadre.model.excel.ExcelImportorAdapter;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.pojo.Assess;
import com.cadre.pojo.User;
import com.cadre.service.infoManager.AssessService;
import com.cadre.service.sys.UserService;

@Controller
@RequestMapping(ViewLocation.FOLDER_INFOMANAGER_QUANTITYMANAGER)
public class QuantityManagerController {
	private Logger logger = LogManager.getLogger(QuantityManagerController.class);

	
	@Autowired
	UserService userService;
	@Autowired
	AssessService assessService;
	
	/**
	 * 批量增加人员
	 * @return
	 */
	@RequestMapping("importAddUser")
	public String importAddUser(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
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
			UserInfoConvertor convertor = new UserInfoConvertor(list);
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_INFOMANAGER_QUANTITYMANAGER+"/batchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 批量删除人员
	 * @return
	 */
	@RequestMapping("importDelUser")
	public String importDelUser(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<User> importor = new ExcelImportorAdapter<User>() {
				@Override
				protected String importBiz(List<User> successList,
						List<DataErrorMsg> errorList){
					userService.delUsersBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			UserDelConvertor convertor = new UserDelConvertor(list);
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_INFOMANAGER_QUANTITYMANAGER+"/batchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	/**
	 * 跳转人员批量操作选择
	 * @return
	 */
	@RequestMapping("batchAction")
	public String batchAction(){
		return ViewLocation.FOLDER_INFOMANAGER_QUANTITYMANAGER+"/batchActionList";
	}
	
	/**
	 * 跳转考核批量操作选择
	 * @return
	 */
	@RequestMapping("batchAsseAction")
	public String batchAsseAction(){
		return ViewLocation.FOLDER_INFOMANAGER_QUANTITYMANAGER+"/batchAsseAction";
	}
	
	/**
	 * 批量增加或者修改考核结果
	 */
	@RequestMapping("importAddAsse")
	public String importAddAsse(@RequestParam(value="file",required=true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<Assess> importor = new ExcelImportorAdapter<Assess>() {
				@Override
				protected String importBiz(List<Assess> successList,
						List<DataErrorMsg> errorList){
					assessService.addAsseBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> userList = userService.queryAll();
			List<Assess> assessList = assessService.queryAll();
			AsseConvertor convertor = new AsseConvertor(assessList, userList);
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_INFOMANAGER_QUANTITYMANAGER+"/batchAsseAction";
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	@RequestMapping("importDelAsse")
	public String importDelAsse(@RequestParam(value="file",required=true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<Assess> importor = new ExcelImportorAdapter<Assess>() {
				@Override
				protected String importBiz(List<Assess> successList,
						List<DataErrorMsg> errorList){
					assessService.delAsseBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> userList = userService.queryAll();
			List<Assess> assessList = assessService.queryAll();
			AsseDelConvertor convertor = new AsseDelConvertor(assessList, userList);
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_INFOMANAGER_QUANTITYMANAGER+"/batchAsseAction";
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
}
