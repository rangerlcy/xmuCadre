package com.cadre.controller.education;

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
import com.cadre.controller.convertor.EducationHistoryConvertor;
import com.cadre.model.excel.ExcelImportorAdapter;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.User;
import com.cadre.service.education.EducationService;
import com.cadre.service.sys.UserService;

@Controller
@RequestMapping(ViewLocation.FOLDER_EDUCATION)
public class EduQuantityManagerController {
	private Logger logger = LogManager.getLogger(EduQuantityManagerController.class);

	
	@Autowired
	UserService userService;
	@Autowired
	EducationService educationService;
	
	
	/**
	 * 批量增加教育经历
	 * @return
	 */
	@RequestMapping("importEducationHistory")
	public String importEducationHistory(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<EducationHistory> importor = new ExcelImportorAdapter<EducationHistory>() {
				@Override
				protected String importBiz(List<EducationHistory> successList,
						List<DataErrorMsg> errorList){
					educationService.saveEduBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			EducationHistoryConvertor convertor = new EducationHistoryConvertor(list);
			
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_EDUCATION+"/eduBatchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 批量删除人员
	 * @return
	 */
	@RequestMapping("delEducationHistory")
	public String delEducationHistory(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<EducationHistory> importor = new ExcelImportorAdapter<EducationHistory>() {
				@Override
				protected String importBiz(List<EducationHistory> successList,
						List<DataErrorMsg> errorList){
					
					educationService.delByNIB(successList);		//通过姓名、身份证、开始时间进行删除			
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			EducationHistoryConvertor convertor = new EducationHistoryConvertor(list);
			
			String result="";
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_EDUCATION+"/eduBatchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	/**
	 * 跳转人员批量操作选择
	 * @return
	 */
	@RequestMapping("eduBatchAction")
	public String batchAction(){
		return ViewLocation.FOLDER_EDUCATION+"/eduBatchActionList";
	}
	
}
