package com.cadre.controller.administrationWork;

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
import com.cadre.controller.convertor.AdministrationWorkHistoryConvertor;
import com.cadre.controller.convertor.UserDelConvertor;
import com.cadre.controller.convertor.UserInfoConvertor;
import com.cadre.model.excel.ExcelImportorAdapter;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.pojo.AdministrationWorkHistory;
import com.cadre.pojo.User;
import com.cadre.service.administrationWork.AdministrationService;
import com.cadre.service.sys.UserService;

@Controller
@RequestMapping(ViewLocation.FOLDER_ADMINISTRATIONWORK)
public class AdmQuantityManagerController {
	private Logger logger = LogManager.getLogger(AdmQuantityManagerController.class);

	
	@Autowired
	UserService userService;
	@Autowired
	AdministrationService administrationWorkService;
	
	
	/**
	 * 批量增加行政经历
	 * @return
	 */
	@RequestMapping("importAdministrationWorkHistory")
	public String importAdministrationWorkHistory(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<AdministrationWorkHistory> importor = new ExcelImportorAdapter<AdministrationWorkHistory>() {
				@Override
				protected String importBiz(List<AdministrationWorkHistory> successList,
						List<DataErrorMsg> errorList){
					administrationWorkService.saveadmBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			AdministrationWorkHistoryConvertor convertor = new AdministrationWorkHistoryConvertor(list);
			
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_ADMINISTRATIONWORK+"/administrationWorkBatchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 批量删除行政经历
	 * @return
	 */
	@RequestMapping("importDelAdministrationWorkHistory")
	public String importDelAdministrationWorkHistory(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<AdministrationWorkHistory> importor = new ExcelImportorAdapter<AdministrationWorkHistory>() {
				@Override
				protected String importBiz(List<AdministrationWorkHistory> successList,
						List<DataErrorMsg> errorList){
					administrationWorkService.delByDetailInfo(successList);   //通过姓名、身份证、开始时间、结束时间、任职单位、单位类型、人员级别、职务名称、核对情况进行删除	
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			AdministrationWorkHistoryConvertor convertor = new AdministrationWorkHistoryConvertor(list);
			String result="";
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_ADMINISTRATIONWORK+"/administrationWorkBatchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	/**
	 * 跳转人员批量操作选择
	 * @return
	 */
	@RequestMapping("administrationWorkBatchAction")
	public String batchAction(){
		return ViewLocation.FOLDER_ADMINISTRATIONWORK+"/administrationWorkBatchActionList";
	}
	
}
