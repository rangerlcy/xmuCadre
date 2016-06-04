package com.cadre.controller.skill;

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
import com.cadre.controller.convertor.SkillConvertor;
import com.cadre.controller.convertor.UserDelConvertor;
import com.cadre.controller.convertor.UserInfoConvertor;
import com.cadre.model.excel.ExcelImportorAdapter;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.pojo.Skill;
import com.cadre.pojo.User;
import com.cadre.service.skill.SkillService;
import com.cadre.service.sys.UserService;

@Controller
@RequestMapping(ViewLocation.FOLDER_SKILL)
public class SkillQuantityManagerController {
	private Logger logger = LogManager.getLogger(SkillQuantityManagerController.class);

	
	@Autowired
	UserService userService;
	@Autowired
	SkillService skillService;
	
	
	/**
	 * 批量增加专业技术职务经历
	 * @return
	 */
	@RequestMapping("importSkill")
	public String importSkillHistory(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<Skill> importor = new ExcelImportorAdapter<Skill>() {
				@Override
				protected String importBiz(List<Skill> successList,
						List<DataErrorMsg> errorList){
					skillService.saveSkillBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<User> list = userService.queryAll();
			SkillConvertor convertor = new SkillConvertor(list);
			
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_SKILL+"/skillBatchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 批量删除人员
	 * @return
	 */
	@RequestMapping("importDelSkillHistory")
	public String importDelSkill(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
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
			return ViewLocation.FOLDER_SKILL+"/batchActionList";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	/**
	 * 跳转人员批量操作选择
	 * @return
	 */
	@RequestMapping("skillBatchAction")
	public String batchAction(){
		return ViewLocation.FOLDER_SKILL+"/skillBatchActionList";
	}
	
}
