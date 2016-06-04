package com.cadre.controller.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.model.page.Page;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.Place;
import com.cadre.pojo.Position;
import com.cadre.service.sys.PlaceService;

@Controller
@RequestMapping(ViewLocation.FOLDER_SYS_PLACE)
public class PlaceController {
	private Logger logger = LogManager.getLogger(PlaceController.class);
	@Autowired
	PlaceService placeService;

	int pageSize=18;
	/**
	 * 按顺序查询出所有省份（一级）
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("placeList")
	public String placeList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null)
				currPage = 1;
			Page<Place> queryList = placeService.findProvByPage(queryStr, currPage, pageSize);		//按顺序分页取出省份信息
			
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return ViewLocation.FOLDER_SYS_PLACE+"/placeList";
	}

	/**
	 * 删除省份
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteProv")
	@ResponseBody
	public String deleteProv(String code)throws Exception {
		try {
			List<Place> places = new ArrayList<Place>();
			places = placeService.queryChildByCode(code);
			for(Place place : places){
				placeService.delete(place);
				placeService.delete(placeService.queryByCode(code));
			}
		    Place place = new Place();
		    place = placeService.queryByCode(code);
		    placeService.delete(place);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 删除省份下级区域
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String delete(String code)throws Exception {
		try {
			Place place = new Place();
			place = placeService.queryByCode(code);
			placeService.delete(place);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 打开省份下级区域
	 * @param parentCode
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openBellow")
	public String openBellow(String parentCode, Integer currPage,String queryStr, Model model)throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<Place> queryList = placeService.findBellowInfo(parentCode,queryStr, currPage, 8);
			Place place = placeService.queryByCode(parentCode);
			String parentName = place.getName();
			model.addAttribute("queryList",queryList);//查询结果列表
			model.addAttribute("parentName",parentName);//父级区划名称
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return ViewLocation.FOLDER_SYS_PLACE+"/bellowList";
	}
	
	/**
	 * 打开三级区域
	 * @param parentCode
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openBellowInfo")
	public String openBellowInfo(String parentCode, Integer currPage,String queryStr, Model model)throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<Place> queryList = placeService.findBellowInfo(parentCode,queryStr, currPage, 8);
			Place place = placeService.queryByCode(parentCode);
			String parentName = place.getName();
			model.addAttribute("queryList",queryList);//查询结果列表
			model.addAttribute("parentName",parentName);//父级区划名称
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return ViewLocation.FOLDER_SYS_PLACE+"/bellowInfoList";
	}
	
	/**
	 * 根据省份查城市
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("queryChildByCode")
	@ResponseBody
	public List<Place> queryChildByCode(String code) throws Exception {
		try {
			return placeService.queryChildByCode(code);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	@RequestMapping("queryByCode")
	@ResponseBody
	public Place queryByCode(String code){
		return placeService.queryByCode(code);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(String name){
		if (null == name || name.trim() == " ") return "fail";
		String MaxCode = placeService.queryMaxCode();
		System.out.println(MaxCode);
		String newCodeString = Integer.toString(Integer.parseInt(MaxCode) + 1);
		Place place = new Place();
		place.setName(name);
		place.setParentCode("AB");
		place.setSimple(name);
		place.setCode(newCodeString);
		try {
			placeService.save(place);
			return "success";
		}catch (Exception e){
			return "fail";
		}
	}

	@RequestMapping("saveBellowInfo")
	@ResponseBody
	public String saveBellowInfo(String code,String name){
		String newname,simple;
		if (null == code || name.trim() == " ") return "fail";
		Place place = placeService.queryByCode(code);
		String parentName = place.getName();
		if(name.contains(parentName)){
			newname = name;
			simple = name.replaceAll(parentName, "").trim();
		}else{
			newname = parentName+name;
			simple = name;
		}
		String MaxCode = placeService.queryMaxCodeByParentCode(code);
		System.out.println(MaxCode);
		String newCodeString = Integer.toString(Integer.parseInt(MaxCode) + 1);
		Place newplace = new Place();
		newplace.setName(newname);
		newplace.setParentCode(code);
		newplace.setSimple(simple);
		newplace.setCode(newCodeString);
		try {
			placeService.save(newplace);
			return "success";
		}catch (Exception e){
			return "fail";
		}
	}
	
	@RequestMapping("update")
	@ResponseBody
	public String update(String code,String name){
		if (null == code) return "fail";
		if(name.trim().equals("")) return "fail";
		Place prov = placeService.queryByCode(code);
		String oldName = prov.getName();
		List<Place> places1 = new ArrayList<Place>();
		places1 = placeService.queryChildByCode(code);
		List<Place> places2 = new ArrayList<Place>();
		for(Place place : places1){
			places2.addAll(placeService.queryChildByCode(place.getCode()));
		}
		places2.add(prov);
		places2.addAll(places1);
		for(Place place : places2){
			if (null == place) return "fail";
			String newName = place.getName().replaceAll(oldName, name);
			place.setName(newName.trim());
			place.setSimple(newName.trim());
			placeService.update(place);
		}
		return "success";
	}

	@RequestMapping("preOpenItemAdd")
	public String preOpenItemAdd(Model model){
		return ViewLocation.FOLDER_SYS_PLACE+"/itemAdd";	
	}
}

