package com.cadre.controller.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cadre.common.ViewLocation;
import com.cadre.model.page.Page;
import com.cadre.pojo.Organization;
import com.cadre.pojo.Position;
import com.cadre.service.position.PositionService;
import com.cadre.service.sys.OrganizationService;


@Controller
@RequestMapping(ViewLocation.FOLDER_SYS_SEQUENCE)
public class SequenceController {
	private Logger logger = LogManager.getLogger(SequenceController.class);
	@Autowired
	OrganizationService organizationService;
	@Autowired
	PositionService positionService;
	
	int pageSize=18;
	/**
	 * 按顺序查询出所有单位（一级）
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sequenceList")
	public String sequenceList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<Position> queryList = positionService.findOrganizationBySeq(queryStr, currPage, pageSize);		//按顺序分页取出机构
			
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return ViewLocation.FOLDER_SYS_SEQUENCE+"/sequenceList";
	}

	/**
	 * 修改单位顺序
	 * @param id
	 * @param sq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("academySqEdit")
	@ResponseBody
	public String academySqEdit(String name, int sqold, String sq, String type)throws Exception {
		try {
			int seq = Integer.parseInt(sq);
			positionService.updateOtherSeq(name,sqold, seq, type);			//比如把1、2、3、4、5中的4更新到2，那么有4->2, 2->3, 3->4
			
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 打开院级（一级）单位下的所有岗位
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openDepartmentSequence")
	public String openDepartmentSequence(String academyName, Integer currPage,String queryStr, Model model)throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<Position> queryList = positionService.findDepartmentByAcademyNameOrQuery(academyName,queryStr, currPage, 8);
			model.addAttribute("queryList",queryList);//查询结果列表
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return ViewLocation.FOLDER_SYS_SEQUENCE+"/departmentSqList";
	}
	
	/**
	 * 打开系级（二级）单位下的所有岗位
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openPositionNameSequence")
	public String openPositionNameSequence(String departmentName, Integer currPage,String queryStr, Model model)throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<Position> queryList = positionService.findPositionNameByDepartmentNameOrQuery(departmentName,queryStr, currPage, 8);
			model.addAttribute("queryList",queryList);//查询结果列表
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return ViewLocation.FOLDER_SYS_SEQUENCE+"/PositionSqList";
	}
}
