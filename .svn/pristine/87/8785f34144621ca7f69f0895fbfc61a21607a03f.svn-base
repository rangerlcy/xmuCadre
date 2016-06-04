package com.cadre.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cadre.pojo.SysResource;

public class ResourceFunction {
	/**
	 * 得到顶级菜单
	 * @return
	 */
	public static List<SysResource> getTopResource(){
		List<SysResource> list = new ArrayList<SysResource>();
		List<SysResource> resourceList = WebApplication.getCurrUser().getResources();
		for(SysResource r : resourceList) {
			if(r.getParentId() == 1 && r.getResourceType() == 1){
				list.add(r);
			}
		}
		return list;
	}
	
	/**
	 * 得到所下一级菜单
	 * @param resource
	 * @return
	 */
	public static List<SysResource> getChildrenMenu(SysResource resource) {
		List<SysResource> list = new ArrayList<SysResource>();
		if(resource != null) {
			List<SysResource> resourceList = WebApplication.getCurrUser().getResources();
			for(SysResource r : resourceList) {
				if(r.getParentId() == resource.getId() && r.getResourceType() == 1){
					list.add(r);
				}
			}
		}
		return list;
	}
	
	/**
	 * 是否还有子菜单
	 * @param resouce
	 * @return
	 */
	public static boolean haveChildrenMenu(SysResource resouce) {
		List<SysResource> list = getChildrenMenu(resouce);
		if(list != null && list.size() > 0){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean haveSecurity(String securityCode) {
		return false;
	}
	
	/**
	 * 是否有资源权限
	 * @param permissionCode
	 * @return
	 */
	public static boolean havePermission(String permissionCode) {
		if(StringUtils.isBlank(permissionCode)) return false;
		
		List<SysResource> resourceList = WebApplication.getCurrUser().getResources();
		for(SysResource r : resourceList) {
			if(StringUtils.equals(StringUtils.trim(r.getSecurityCode()), StringUtils.trim(permissionCode))){
				return true;
			}
		}
		
		return false;
	}
	
}
