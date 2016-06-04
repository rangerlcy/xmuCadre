package com.cadre.controller.userTrain;

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
import com.cadre.controller.convertor.UserTrainConvertor;
import com.cadre.model.excel.ExcelImportorAdapter;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.pojo.Train;
import com.cadre.service.sys.UserTrainService;

@Controller
@RequestMapping(ViewLocation.FOLDER_USERTRAIN_QUANTITYACTION)
public class UtQuantityActionController {
	private Logger logger = LogManager.getLogger(UtQuantityActionController.class);

	
	@Autowired
	UserTrainService userTrainService;
	
	/**
	 * 批量增加教育培训信息
	 * @return
	 */
	@RequestMapping("importAddUserTrainInfo")
	public String importAddUserTrainInfo(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<Train> importor = new ExcelImportorAdapter<Train>() {
				@Override
				protected String importBiz(List<Train> successList,
						List<DataErrorMsg> errorList){
					userTrainService.saveTrainsBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<Train> list = userTrainService.queryAll();
			UserTrainConvertor convertor = new UserTrainConvertor(list);
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_USERTRAIN_INFO+"/batchAdd";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 批量删除教育培训信息
	 * @return
	 */
	@RequestMapping("importDelUserTrainInfo")
	public String importDelUserTrainInfo(@RequestParam(value = "file",required = true) MultipartFile file,final Model model) throws Exception{
		try{
			ExcelImportorAdapter<Train> importor = new ExcelImportorAdapter<Train>() {
				@Override
				protected String importBiz(List<Train> successList,
						List<DataErrorMsg> errorList){
					userTrainService.delTrainsBatch(successList);
					model.addAttribute("successSize", successList.size());
					model.addAttribute("failSize", errorList.size());
					model.addAttribute("errorList",errorList);
					return "success";
				}
			};
			List<Train> list = userTrainService.queryAll();
			UserTrainConvertor convertor = new UserTrainConvertor(list);
			String result;
			try {
				result = importor.importData(file,convertor);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				result = "fail";
				model.addAttribute("failDetail", e.getMessage());
			}
			
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_USERTRAIN_INFO+"/batchDelete";
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
}
