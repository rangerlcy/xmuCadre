package com.cadre.controller.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.Place;
import com.cadre.service.sys.DictionaryService;
import com.cadre.service.sys.PlaceService;

@Controller
@RequestMapping(ViewLocation.FOLDER_SYS_DICTIONARY)
public class DictionaryController {

	@Autowired
	DictionaryService dictionaryService;
	@Autowired
	PlaceService placeService;
	
	@RequestMapping("/dictionaryList")
	public String dictionaryList(Model model){
		List<DictionaryDb> dictionaries = new ArrayList<DictionaryDb>();
		List<Place> place = new ArrayList<Place>();
		dictionaries = dictionaryService.queryAll();
		place = placeService.queryAllProv();
		if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
			model.addAttribute("dictionaries", dictionaries);
			model.addAttribute("place",place);
			return ViewLocation.FOLDER_SYS_DICTIONARY + "/dictionaryList";
		}else {
			model.addAttribute("errMsg", "没有权限");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
	}
	
	@RequestMapping("queryChildByCode")
	@ResponseBody
	public List<DictionaryDb> queryChildByCode(String code){
		return dictionaryService.queryChildByCode(code);
	}
	
	@RequestMapping("queryByCode")
	@ResponseBody
	public DictionaryDb queryByCode(String code){
		return dictionaryService.queryByCode(code);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(DictionaryDb dictionaryDb){
		if (null == dictionaryDb || null == dictionaryDb.getValue()) return "fail";
		String val=dictionaryDb.getValue().replaceAll(" ","");
		if ("".equals(val)) return "fail";
		String MaxCode = dictionaryService.queryMaxCodeByParentCode(dictionaryDb.getParentCode());
		String MaxKey = dictionaryService.queryMaxKeyByParentCode(dictionaryDb.getParentCode());
		String newCodeString = Integer.toString(Integer.parseInt(MaxCode) + 1);
		String newKeyString = Integer.toString(Integer.parseInt(MaxKey) + 1);
		if (newCodeString.length()<5) newCodeString = '0' + newCodeString;
		if (newKeyString.length()<2) newKeyString = '0' + newKeyString;
		dictionaryDb.setKey(newKeyString);
		dictionaryDb.setCode(newCodeString);
		try {
			dictionaryService.save(dictionaryDb);
			return "success";
		}catch (Exception e){
			return "fail";
		}
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String update(DictionaryDb dictionaryDb){
		if (null == dictionaryDb || null == dictionaryDb.getValue()) return "fail";
		if (null == dictionaryDb.getId()) return "fail";
		DictionaryDb toBeUpdatedDictionaryDb = dictionaryService.queryById(dictionaryDb.getId());
		if (null == toBeUpdatedDictionaryDb) return "fail";
		toBeUpdatedDictionaryDb.setValue(dictionaryDb.getValue().replaceAll(" ", ""));
		if("".equals(toBeUpdatedDictionaryDb.getValue())) return "fail";
		try{
			dictionaryService.update(toBeUpdatedDictionaryDb);
			return "success";
		}catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(DictionaryDb dictionaryDb){
		if (dictionaryDb == null) return "fail";
		if (dictionaryDb.getId() == null) return "fail";
		DictionaryDb toBeDeleteDictionaryDb = dictionaryService.queryById(dictionaryDb.getId());
		if (toBeDeleteDictionaryDb == null) return null;
		try{
			dictionaryService.delete(toBeDeleteDictionaryDb);
			return "success";
		}catch (Exception e){
			return "fail";
		}
	}
	
	@RequestMapping("preOpenItemEdit")
	public String preOpenItemEdit(String pid,Model model){
		model.addAttribute("pid", pid);
		return ViewLocation.FOLDER_SYS_DICTIONARY+"/itemAdd";	
	}
}
