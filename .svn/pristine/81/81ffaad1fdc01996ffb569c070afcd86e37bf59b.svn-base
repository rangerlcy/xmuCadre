package com.cadre.controller.sys;

import java.util.List;

import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cadre.pojo.Organization;
import com.cadre.service.sys.OrganizationService;

@Controller
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;
	
	@RequestMapping("/sys/organization/queryChildByCode")
	@ResponseBody
	@Transactional
	List<Organization> queryChildByCode(String code) throws Exception{
		try {
			return organizationService.queryChildByCode(code);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
}
