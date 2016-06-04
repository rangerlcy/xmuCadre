package com.cadre.controller.position;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.Request;
import org.apache.commons.lang3.StringUtils;
import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.controller.pojo.PositionPaperItem;
import com.cadre.controller.pojo.PositionPaperParam;
import com.cadre.controller.pojo.PositionSimple;
import com.cadre.controller.pojo.UserSimple;
import com.cadre.controller.sys.UserController;
import com.cadre.model.page.Page;
import com.cadre.model.utils.DateUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.Organization;
import com.cadre.pojo.OrganizationHistory;
import com.cadre.pojo.Paper;
import com.cadre.pojo.PaperContent;
import com.cadre.pojo.Position;
import com.cadre.pojo.PositionHistory;
import com.cadre.pojo.PositionPostuser;
import com.cadre.pojo.User;
import com.cadre.service.dictionary.DictionaryValueService;
import com.cadre.service.position.PaperService;
import com.cadre.service.position.PositionHistoryService;
import com.cadre.service.position.PositionService;
import com.cadre.service.position.PostHistoryService;
import com.cadre.service.sys.AppointAndDismissService;
import com.cadre.service.sys.OrganizationHistoryService;
import com.cadre.service.sys.OrganizationService;

@Controller
@RequestMapping(ViewLocation.FOLDER_POSITION)
public class PositionController {
	private Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	PositionService positionService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	PaperService paperService;
	@Autowired
	DictionaryValueService dictionaryService;
	@Autowired
	PositionHistoryService positionHistoryService;
	@Autowired
	OrganizationHistoryService organizationHistoryService;
	@Autowired
	PostHistoryService postHistoryService;
	@Autowired
	AppointAndDismissService appointAndDismissService;
	int pageSize=14;
	
	/**
	 * 分页查询岗位信息
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("positionList")
	public String positionList(Integer currPage,String queryStr,Model model) throws Exception{
		try {
			if (currPage == null) currPage = 1;
			Page<Position> queryList = positionService.findPositionByPage(queryStr, currPage, pageSize);
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			/*
			List<Organization> organizations = organizationService.queryAll();
			List<DictionaryDb> dic = dictionaryService.queryAll();
			Map<String, Organization> orgMap = this.getOrgMap(organizations);
			Map<String, DictionaryDb> dicMap = new HashMap<String, DictionaryDb>();
			for(DictionaryDb dd:dic){
				if(dd==null) continue;
				dicMap.put(dd.getCode(), dd);
			}
			model.addAttribute("dicMap", dicMap);
			model.addAttribute("orgMap", orgMap);
			model.addAttribute("today",new Date());
			*/
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
			model.addAttribute("roleType", "admin");
		}else {
			model.addAttribute("roleType", "visitor");
		}
		return ViewLocation.FOLDER_POSITION+"/positionList";
	}
	
	/**
	 * 分页查询发文信息
	 * @param currPage
	 * @param queryStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("articleList")
	public String articleList(Integer currPage,String queryStr,Model model){
		System.out.println(1);
		try {
			if (currPage == null) currPage = 1;
			Page<Paper> queryList = positionService.findArticleByPage(queryStr, currPage, pageSize);
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			//throw new Exception(e.getMessage());
			e.printStackTrace();
		}
		if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
			model.addAttribute("roleType", "admin");
		}else {
			model.addAttribute("roleType", "visitor");
		}
		return ViewLocation.FOLDER_POSITION+"/positionListByArticle";
	}
	
	/**
	 * 组织机构形成map
	 * @param organizations
	 * @return
	 */
	private Map<String, Organization> getOrgMap(List<Organization> organizations) {
		// TODO Auto-generated method stub
		Map<String, Organization> orgMap = new HashMap<String, Organization>();
		for (Organization organization : organizations){
			orgMap.put(organization.getCode(), organization);
		}
		return orgMap;
	}
	
	/**
	 * 打开岗位详情页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("openPositionDetail")
	public String openPositionDetail(Integer id,Model model){
		if (id == null){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		Position position = positionService.findById(id);
		if (null == position){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		model.addAttribute("position", position);
		List<Organization> organizations = organizationService.queryAll();
		Map<String, Organization> orgMap = new HashMap<String, Organization>();
		for (Organization org : organizations){
			orgMap.put(org.getCode(), org);
		}
		model.addAttribute("orgMap", orgMap);
		return ViewLocation.FOLDER_POSITION+"/positionDetail";
	}
	/**
	 * 查看发文详情页面
	 * @param id
	 * @param model
	 * @return
	 */	
	@RequestMapping("AricleDetail")	
	public String AricleDetail(Integer id,Model model){
		if(id==null){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		List<PaperContent> pclist = paperService.findPaperContentById(id);
		model.addAttribute("pclist", pclist);
		return ViewLocation.FOLDER_POSITION+"/articleDetail";
	}
	/**
	 * 打开修改发文页面
	 * 发文文号
	 * 发文名称
	 * 发文日期
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("openAricleEdit")	
	public String openAricleEdit(Integer id,Model model){
		if(id==null){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		Paper paper = paperService.findById(id);
		model.addAttribute("paper", paper);
		return ViewLocation.FOLDER_POSITION+"/articleEdit";
	}
	/**
	 * 修改发文
	 * @param id
	 * @param model
	 * @return
	 */	
	
	@RequestMapping("articleEdit")
	@ResponseBody
	public String articleEdit(Paper paper,Model model){
		if(paper.getId()==null){
			return "fail";
		}
		Paper pa=paperService.findById(paper.getId());
		pa.setPaperNumber(paper.getPaperNumber());
		pa.setPaperName(paper.getPaperName());
		pa.setPaperDay(paper.getPaperDay());
		paperService.update(pa);
		return "success";
	}
	/**
	 * 删除发文
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("articleDel")
	@ResponseBody
	public String articleDel(Integer id,Model model){
		if (null == id) {
			return "fail";
		}
		paperService.deletePaper(id);
		return "success";
	}
	/**
	 * 下载发文
	 * @param id
	 * @param model
	 * @return
	 */	
	@RequestMapping("articleDownload")
	@ResponseBody	
	public String articleDownload(Integer id,Model model){
		if(id==null){
			return "fail";
		}
		Paper paper=paperService.findById(id);
		if(paper.getPaperFile()==null || paper.getPaperFile().trim()==""){
			return "fail";
		}
		String url=paper.getPaperFile();
		return url;
	}
	/**
	 * 打开岗位修改
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("openPositionEdit")
	public String openPositionEdit(Integer id,Model model){
		if (id == null){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		Position position = positionService.findById(id);
		if (null == position){
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		model.addAttribute("position", position);
		return ViewLocation.FOLDER_POSITION+"/positionEdit";
	}
	/**
	 * 岗位修改
	 * @param position
	 * @param model
	 * @return
	 */	
	@RequestMapping("positionEdit")
	@ResponseBody
	public String positionEdit(Position position, Model model){
		if (null == position || null == position.getId()){
			return "noPosition";
		}
		else
		{
			Position pos = positionService.findById(position.getId());
			pos.setAcademy(position.getAcademy());
			pos.setDepartment(position.getDepartment());
			pos.setPositionName(position.getPositionName());
			pos.setPositionType(position.getPositionType());
			pos.setPositionLevel(position.getPositionLevel());
			pos.setPositionDay(position.getPositionDay());
			pos.setPositionRemark(position.getPositionRemark());
			positionService.update(pos);
			return "success";
		}
	}
//	/**
//	 * 删除岗位
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping("positionDel")
//	@ResponseBody
//	public String positionDel(Integer id){
//		if (id == null){
//			return "fail";
//		}
//		Position position = positionService.findById(id);
//		if (null == position){
//			return "fail";
//		}
//		List<PostHistory> postHistories= positionService.delete(position);
//		if (null == postHistories)
//			return "{\"result\":\"成功\",\"names\":}";
//		else {
//			List<String> nameStrings = new ArrayList<String>();
//			for (PostHistory postHistory :postHistories){
//				nameStrings.add(postHistory.getUser().getName());
//			}
//			return "{\"result\":\"成功\",\"names\":\""+listToString(nameStrings) +"\"}";
//			
//		}
//	}
	
	/**
	 * 打开增加岗位
	 * @param model
	 * @return
	 */
	@RequestMapping("openPositionAdd")
	public String openPositionAdd(Model model){
		List<Organization> organizations = organizationService.queryAll();
		model.addAttribute("organizations", organizations);
		return ViewLocation.FOLDER_POSITION +"/positionAdd";
	}
	
	/**
	 * 按照发文增加岗位
	 * @param position
	 * @return
	 */
	/*
	@RequestMapping("positionAdd")
	@Transactional
	public String positionAdd(String paperName,String paperNumber,Date paperDay,@RequestParam(value = "file",required = false) MultipartFile file,PositionPaperParam positions,Model model ){
		List<String> errMsgList = new ArrayList<String>();
		if (null == paperName || 
			null == paperNumber || 
			null == positions || 
			null == positions.getPositionPaperItems()||
			0 == positions.getPositionPaperItems().size()){
			errMsgList.add("请检验输入的信息是否完整");
			model.addAttribute("errMsg",errMsgList.get(0));
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
		//添加发文
		Paper paper = new Paper();
		paper.setPaperDay(paperDay);
		paper.setPaperName(paperName);
		paper.setPaperNumber(paperNumber);
		paper.setPaperType(1);//岗位组织机构修改发文类型
		paper.setPaperUnits("");
		if (null != file && null != file.getOriginalFilename() && !"".equals(file.getOriginalFilename())){
			
			String fileName = file.getOriginalFilename();//上传的文件名
			String[] nameArr = fileName.split("\\.");
			String suff = nameArr[nameArr.length-1];
			fileName = paperNumber + UUID.randomUUID() +"."+suff;
			String path = WebApplication.getSession().getServletContext().getRealPath("../uploadFile/paper/");
			File targetFile = new File(path, fileName);  
			if(!targetFile.exists()){  
				targetFile.mkdirs();  
			} 
			//保存  
			paper.setPaperFile("../uploadFile/paper/" + fileName);
			try {  
				file.transferTo(targetFile);  
			} catch (Exception e) {  
				throw new RuntimeException("文件保存异常"+e.getMessage()); 
			}finally{
				
			}
			
		}
		
		
		paperService.addPaper(paper);
	
		List<Organization> organizations = organizationService.queryAll();
		
		List<PositionPaperItem> OldOrganizations = new ArrayList<PositionPaperItem>();
		List<PositionPaperItem> OldPositions = new ArrayList<PositionPaperItem>();
		Map<String,Organization> orgMap = this.getOrgMap(organizations);
		for (PositionPaperItem item : positions.getPositionPaperItems()){
			if (item.getActionType().equals("1")){//增岗或者组织机构
				if (item.getPositionName() != null){//岗位
					addPositonByPaper(paper,item);
				}else{//增加组织机构					
					if (null == item.getAcadmy()) {
						errMsgList.add("增、撤 栏目下序号为 "+item.getIndex()+" 的失败，原因是其中【院/单位】栏位不能为空");
						continue;
					}
					addOrgByPaper(paper, item, orgMap);				
				}
			}else if (item.getActionType().equals("2")) {//撤岗或者撤销组织机构
				if (null != item.getPositionName()){//撤岗
					errMsgList.addAll(delPostionByPaper(paper, item));	
					
				}else{//撤销组织机构
					delOrgByPaper(paper, item, orgMap);
				}
			}else if (item.getActionType().equals("4")){//修改增岗
				if (item.getPositionName() != null){//岗位（增岗）
					
				}else{//修改组织机构
					//* 再次遍历时候处理
				}
			}else if (item.getActionType().equals("3")){//修改撤岗
				if (item.getPositionName() != null){//岗位（撤岗）先取出
					OldPositions.add(item);
				}else { //待修改组织机构先取出
					OldOrganizations.add(item);
				}
				
			}
			
		}

		//* 单独处理组织机构修改
		for (PositionPaperItem item : positions.getPositionPaperItems()){
			if (item.getActionType().equals(3)){
				if (item.getPositionName() == null){
					for (PositionPaperItem item2 : OldOrganizations){
						if (item.getIndex().equals(item2.getIndex())){
							orgMap = updateOrgByPaper(item,item2,paper,orgMap);
						}
					}
				}
				else {
					for (PositionPaperItem item2 : OldPositions){
						if (item.getIndex().equals(item2.getIndex())){//岗位
							errMsgList.addAll(updatePositonByPaper(item,item2,paper));
						}
					}
				}
			}			
		}
		model.addAttribute("errList", errMsgList);
		return ViewLocation.FOLDER_POSITION + "/positionAddResult";
	}
	*/
	
	/**
	 * 根据发文修改岗位
	 * @param newItem
	 * @param oldItem
	 * @param paper
	 * @return
	 */
	private List<String> updatePositonByPaper(
			PositionPaperItem newItem, PositionPaperItem oldItem, Paper paper) {
		// TODO Auto-generated method stub
		newItem.setAcadmy(oldItem.getAcadmy());
		newItem.setDepartment(oldItem.getDepartment());
		addPositonByPaper(paper, newItem);
		return delPostionByPaper(paper, oldItem);
	}
	/**
	 * 根据发文修改组织机构
	 * @param item
	 * @param item2
	 * @param paper
	 * @param orgMap
	 */
	private Map<String, Organization> updateOrgByPaper(PositionPaperItem newItem,
			PositionPaperItem oldItem, Paper paper,
			Map<String, Organization> orgMap) {
		// TODO Auto-generated method stub
		Organization organization;
		if (null == newItem.getAcadmy()){
			return orgMap;
		}
		if (null == newItem.getDepartment()){//修改院级单位
			organization = orgMap.get(oldItem.getAcadmy());
			if (organization.getName().equals(newItem.getAcadmy()))
				return orgMap;
			organization.setName(newItem.getAcadmy());
			saveOrganizationHistory(paper, organization, 2, newItem.getActionDay());
			orgMap.put(oldItem.getAcadmy(), organization);
		}else {
			organization = orgMap.get(oldItem.getDepartment());
			if (organization.getName().equals(newItem.getDepartment()))
				return orgMap;
			organization.setName(newItem.getDepartment());
			saveOrganizationHistory(paper, organization, 2, newItem.getActionDay());
			orgMap.put(oldItem.getDepartment(), organization);
		}
		return orgMap;
	}
	/**
	 * 按照发文增岗(并记录历史)
	 * @param paper
	 * @param item
	 */
	@Transactional
	private void addPositonByPaper(Paper paper,PositionPaperItem item){
		Position position = new Position();
		position.setAcademy(item.getAcadmy() == null ? null:item.getAcadmy());
		position.setDepartment(item.getDepartment() == null ? null : item.getDepartment());
		position.setIsDelWithUser(item.getIsDelWithUser() == null ? 0 : item.getIsDelWithUser());
		position.setPaper(paper);
		position.setPositionLevel(item.getPositionLevel() == null ? null : item.getPositionLevel());
		position.setPositionName(item.getPositionName() == null ? null : item.getPositionName());
		position.setPositionType(item.getPositionType() == null ? null : item.getPositionType());
		position.setDelFlag(1);
		position.setPositionDay(DateUtil.currentTime().toDate());
		positionService.save(position);
		
		//历史岗位信息
		this.savePositonHistory(paper, position, 1, item.getActionDay());
		return;
	}
	/**
	 * 根据发文添加组织机构
	 * @param paper
	 * @param item
	 * @param orgMap
	 * @return
	 */
	@Transactional
	private Map<String, Organization> addOrgByPaper(Paper paper,PositionPaperItem item,Map<String, Organization> orgMap){
		Organization organizationAcadmy = new Organization();	
		if (!orgMap.containsKey(item.getAcadmy())){//不存在院级别单位 创建之
			organizationAcadmy.setName(item.getAcadmy());
			organizationAcadmy.setParentCode("01");
			organizationAcadmy.setCode(this.generateOrgCode("01", orgMap));
			organizationAcadmy.setDelFlag(1);
			organizationService.save(organizationAcadmy);
			orgMap.put(organizationAcadmy.getCode(), organizationAcadmy);
			//添加历史信息
			this.saveOrganizationHistory(paper, organizationAcadmy, 1, item.getActionDay());
		}else {
			organizationAcadmy = orgMap.get(item.getAcadmy());
		}
		if (null == item.getDepartment()) return orgMap;//如果系级别单位没填 结束
		
		Organization organizationDept = new Organization();
		if (!orgMap.containsKey(item.getAcadmy())) {//不存在此系级别单位 创建之
			organizationDept.setName(item.getDepartment() == null ? null : item.getDepartment());
			organizationDept.setParentCode(organizationAcadmy.getCode());
			organizationDept.setCode(this.generateOrgCode(organizationAcadmy.getCode(), orgMap));
			organizationDept.setDelFlag(1);
			organizationService.save(organizationDept);
			orgMap.put(organizationDept.getCode(), organizationDept);
			//添加历史信息
			this.saveOrganizationHistory(paper, organizationDept, 1,item.getActionDay());
		}
		return orgMap;
	}
	/**
	 * 按照发文撤岗
	 * @param paper
	 * @param item
	 */
	private List<String> delPostionByPaper(Paper paper,PositionPaperItem item){
		List<String> errMsgList = new ArrayList<String>();
		if (null == item.getPositionId()) {
			errMsgList.add("增、撤 栏目下序号为 "+item.getIndex()+" 的撤岗失败，原因是不存在此岗位");
			return errMsgList;
		}
		Position position = positionService.findById(item.getPositionId());
		if (null == position) {
			errMsgList.add("增、撤 栏目下序号为 "+item.getIndex()+" 的撤岗失败，原因是不存在此岗位");
			return errMsgList;
		}
		if (null == item.getIsDelWithUser() || item.getIsDelWithUser().equals(1)){
			position.setDelFlag(0);
		}else {
			 if (appointAndDismissService.getPostUserByPosition(position)>0){
				errMsgList.add("增、撤 栏目下序号为 "+item.getIndex()+" 的撤岗失败，原因是此岗位还有任职信息存在");
				return errMsgList;
			 }else {
				position.setDelFlag(0);
			}
		}					
		positionService.update(position);
		
		//历史岗位信息
		this.savePositonHistory(paper, position, 0,item.getActionDay());
		return errMsgList;
	}
	/**
	 * 按照发文撤销组织机构
	 * @param paper
	 * @param item
	 * @param orgMap
	 * @return
	 */
	private  Map<String, Organization> delOrgByPaper(Paper paper,PositionPaperItem item,Map<String, Organization> orgMap){
		if (null == item.getDepartment()){//撤销院级
			if (null == item.getAcadmy()){
				return orgMap;
			}else {//撤销院级单位
				if (!orgMap.containsKey(item.getAcadmy())){
					return orgMap;
				}else {
					Organization organizationAcadmy = orgMap.get(item.getAcadmy());
					organizationService.delete(organizationAcadmy);//伪删除					
					orgMap.remove(organizationAcadmy.getCode());
					//历史记录
					saveOrganizationHistory(paper, organizationAcadmy, 0,item.getActionDay());
				}
			}	
		}else {
			if (null == item.getAcadmy()){//逻辑错误，不处理了
				return orgMap;
			}else {
				if (!orgMap.containsKey(item.getDepartment())){//不存在这个组织
					return orgMap;
				}else {
					Organization organizationDept = orgMap.get(item.getDepartment());
					organizationService.delete(organizationDept);
					orgMap.remove(organizationDept.getCode());
					//历史记录
					saveOrganizationHistory(paper, organizationDept, 0,item.getActionDay());
					
				}
			}
		}
		return orgMap;
		
	}
	/**
	 * 添加岗位修改历史信息
	 * @param paper
	 * @param position
	 */
	private void savePositonHistory(Paper paper,Position position,Integer action,Date actionDay){
		PositionHistory positionHistory = new PositionHistory();
		positionHistory = new PositionHistory();
		positionHistory.setAcademy(position.getAcademy());
		positionHistory.setAction(action);
		positionHistory.setDepartment(position.getDepartment());
		positionHistory.setPaper(paper);
		positionHistory.setPositionLevel(position.getPositionLevel());
		positionHistory.setPositionName(position.getPositionName());
		positionHistory.setPositionType(position.getPositionType());
		positionHistory.setActionTime(new Timestamp(actionDay.getTime()));
		positionHistoryService.save(positionHistory);
	}
	/**
	 * 修改组织机构历史信息
	 * @param paper
	 * @param organization
	 * @param action
	 */
	 
	private void saveOrganizationHistory(Paper paper,Organization organization,Integer action,Date actionDay){
	
		OrganizationHistory organizationHistory = new OrganizationHistory();
		organizationHistory.setAction(action);
		organizationHistory.setCode(organization.getCode());
		organizationHistory.setName(organization.getName());
		organizationHistory.setPaper(paper);
		organizationHistory.setParentCode(organization.getParentCode());
		organizationHistory.setActionTime(new Timestamp(actionDay.getTime()));
		organizationHistoryService.save(organizationHistory);	
	}
	/**
	 * join 方法
	 * @param stringList
	 * @return
	 */
	private static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	/**
	 * 产生组织机构的代码
	 * @param parentCode
	 * @param orgMap
	 * @return
	 */
	private String generateOrgCode(String parentCode,Map<String,Organization> orgMap){
		Random rd = new Random();
		DecimalFormat dFormat = new DecimalFormat("00");
		while(true){
			int suggestNumber = rd.nextInt(101);			
			String suggestString  = dFormat.format(suggestNumber);
			if (!orgMap.containsKey(parentCode+suggestString)) 
				return parentCode+suggestString;
		}
	}
	/**
	 * 根据系名字获取下属所有岗位
	 * @return
	 */
	@RequestMapping("queryPositionByDept")
	@ResponseBody
	@Transactional
	public List<PositionSimple> queryPositionByDept(String code) throws Exception{
		try{
			List<Position> positions =  positionService.findByDept(code);
			List<PositionSimple> positionSimples = new ArrayList<PositionSimple>();
			PositionSimple positionSimple;
			for (Position position : positions){
				positionSimple = new PositionSimple();
				
				positionSimple.setId(position.getId());
				positionSimple.setAcademy(position.getAcademy());
				positionSimple.setPositionName(position.getPositionName());
				positionSimple.setPositionLevelName(ViewFunction.getPositionLevel(position.getPositionLevel()));
				positionSimple.setPositionLevel(position.getPositionLevel());
				positionSimple.setPositionTypeName(ViewFunction.getPositionType(position.getPositionType()));
				positionSimple.setPositionType(position.getPositionType());
				positionSimples.add(positionSimple);
			}
			return positionSimples;
		}catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	@RequestMapping("queryPositionById")
	@ResponseBody
	@Transactional
	public PositionSimple queryPositionById(Integer code) throws Exception{
		try {
			Position position = positionService.findById(code);
			PositionSimple positionSimple = new PositionSimple();
			positionSimple.setAcademy(position.getAcademy());
			positionSimple.setPositionName(position.getPositionName());
			positionSimple.setPositionLevelName(ViewFunction.getPositionLevel(position.getPositionLevel()));
			positionSimple.setPositionLevel(position.getPositionLevel());
			positionSimple.setPositionTypeName(ViewFunction.getPositionType(position.getPositionType()));
			positionSimple.setPositionType(position.getPositionType());
			
			// TODO judge if there are users taking this position
			
			
			return positionSimple;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
//	@RequestMapping("openPositionInvalid")
//	public String openPositionInvalid(Integer id,Model model){
//		if (id == null){
//			return "fail";
//		}
//		Position position = positionService.findById(id);
//		if (null == position){
//			return "fail";
//		}
//		model.addAttribute("position", position);
//		return ViewLocation.FOLDER_POSITION +"/positionInvalid";
//	}
	
//	
//	
//	/**
//	 * 撤岗
//	 * @param id
//	 * @return
//	 */
//	
//	@RequestMapping("positionInvalid")
//	@ResponseBody
//	public String positionInvalid(Integer id,String offPositionPaperName,String offPositionPaperNumber){
//		if (id == null){
//			return "fail";
//		}
//		Position position = positionService.findById(id);
//		if (null == position){
//			return "fail";
//		}
//		
//		if (position.getDelFlag() != null && position.getDelFlag().equals(0)){
//			return "invalid";
//		}
//		position.setOffPositionDay(new Date());
//		position.setOffPositionPaperName(offPositionPaperName);
//		position.setOffPositionPaperNumber(offPositionPaperNumber);
//		position.setDelFlag(0);
//		positionService.update(position);
//		
//		return "success";
//	}
//	/**
//	 *打开岗位修改 
//	 */
//	@RequestMapping("openPositionEdit")
//	public String openPositionEdit(Integer id,Model model){
//		if (id == null){
//			return "fail";
//		}
//		Position position = positionService.findById(id);
//		if (null == position){
//			return "fail";
//		}
//		model.addAttribute("position", position);
//		
//		List<Organization> organizations = organizationService.queryAll();
//		model.addAttribute("organizations", organizations);
//		return ViewLocation.FOLDER_POSITION +"/positionEdit";
//	}
//	
//	/**
//	 * 修改岗位
//	 * @param position
//	 * @return
//	 */
//	@RequestMapping("positionEdit")
//	@ResponseBody
//	public String positionEdit(Position position){
//		if (position == null){
//			return "fail";
//		}
//		if (position.getId() == null){
//			return "fail";
//		}
//		Position positionOld = positionService.findById(position.getId());
//		if (null == positionOld){
//			return "fail";
//		}		
//		if (positionOld.getDelFlag() != null && positionOld.getDelFlag().equals(0)){
//			return "invalid";
//		}
//		positionOld.setDelFlag(0);
//		positionOld.setOffPositionDay(new Date());
//		positionService.update(positionOld);//旧岗撤岗
//		
//		position.setId(null);//新岗位设立
//		position.setDelFlag(1);
//		position.setPositionDay(new Date());
//		positionService.save(position);
//		return "success";
//		
//	}
//	
//	/**
//	 * 打开批量岗位操作
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("batchPositionAction")
//	public String batchPositionAction(Model model){
//		List<Organization> organizations = organizationService.queryAll();
//		model.addAttribute("organizations", organizations);
//		return ViewLocation.FOLDER_POSITION+"/batchPositionAction";
//	}
//	
//	
//	@RequestMapping("importAddPosition")
//	public String importPosition(@RequestParam(value="file",required=true) MultipartFile file,final Model model) throws Exception{
//		try{
//			ExcelImportorAdapter<Position> importor = new ExcelImportorAdapter<Position>() {
//				@Override
//				protected String importBiz(List<Position> successList,
//						List<DataErrorMsg> errorList){
//					positionService.addUsersBatch(successList);
//					model.addAttribute("successSize", successList.size());
//					model.addAttribute("failSize", errorList.size());
//					model.addAttribute("errorList",errorList);
//					return "success";
//				}
//			};
//			List<Position> list = positionService.queryAll();
//			List<Organization> organizations = organizationService.queryAll();
//			PositionConvertor convertor = new PositionConvertor(list,organizations);
//			String result;
//			try {
//				result = importor.importData(file,convertor);
//			} catch (Exception e) {
//				logger.error(e.getMessage(),e);
//				result = "fail";
//				model.addAttribute("failDetail", e.getMessage());
//			}
//			
//			model.addAttribute("result", result);
//			return ViewLocation.FOLDER_POSITION+"/batchPositionAction";
//		}catch (Exception e) {
//			logger.error(e.getMessage());
//			throw new Exception(e.getMessage());
//		}
//	}
//	
//	
//	@RequestMapping("importDelPosition")
//	public String importDelPosition(@RequestParam(value="file",required=true) MultipartFile file,final Model model) throws Exception{
//		try{
//			ExcelImportorAdapter<Position> importor = new ExcelImportorAdapter<Position>() {
//				@Override
//				protected String importBiz(List<Position> successList,
//						List<DataErrorMsg> errorList){
//					positionService.delPositionsBatch(successList);
//					model.addAttribute("successSize", successList.size());
//					model.addAttribute("failSize", errorList.size());
//					model.addAttribute("errorList",errorList);
//					return "success";
//				}
//			};
//			List<Position> list = positionService.queryAll();
//			
//			PositionDelConvertor convertor = new PositionDelConvertor(list);
//			String result;
//			try {
//				result = importor.importData(file,convertor);
//			} catch (Exception e) {
//				logger.error(e.getMessage(),e);
//				result = "fail";
//				model.addAttribute("failDetail", e.getMessage());
//			}
//			
//			model.addAttribute("result", result);
//			return ViewLocation.FOLDER_POSITION+"/batchPositionAction";
//		}catch (Exception e) {
//			logger.error(e.getMessage());
//			throw new Exception(e.getMessage());
//		}
//	}
	
//	@RequestMapping("importInvalidPosition")
//	public String importInvalidPosition(@RequestParam(value="file",required=true) MultipartFile file,final Model model) throws Exception{
//		try{
//			ExcelImportorAdapter<Position> importor = new ExcelImportorAdapter<Position>() {
//				@Override
//				protected String importBiz(List<Position> successList,
//						List<DataErrorMsg> errorList){
//					positionService.updateInvalidPositionBatch(successList);
//					model.addAttribute("successSize", successList.size());
//					model.addAttribute("failSize", errorList.size());
//					model.addAttribute("errorList",errorList);
//					return "success";
//				}
//			};
//			List<Position> list = positionService.queryAll();
//			
//			PositionDelConvertor convertor = new PositionDelConvertor(list);
//			String result;
//			try {
//				result = importor.importData(file,convertor);
//			} catch (Exception e) {
//				logger.error(e.getMessage(),e);
//				result = "fail";
//				model.addAttribute("failDetail", e.getMessage());
//			}
//			
//			model.addAttribute("result", result);
//			return ViewLocation.FOLDER_POSITION+"/batchPositionAction";
//		}catch (Exception e) {
//			logger.error(e.getMessage());
//			throw new Exception(e.getMessage());
//		}
//	}
	/**
	 * 打开岗位更新
	 * @param model
	 * @return
	 */
	@RequestMapping("openPositionUpdate")
	public String openPositionUpdate(Model model){
		//List<Organization> organizations = organizationService.queryAll();
		//model.addAttribute("organizations", organizations);
		return ViewLocation.FOLDER_POSITION +"/positionUpdate";
	}
	
	/**
	 * 打开上传文件
	 */
	@RequestMapping("openPositionUploadPaper")
	public String openPositionUploadPaper(){
		return ViewLocation.FOLDER_POSITION +"/positionUploadPaper";
	}
	/**
	 * 检查前端送来的数据在数据库中是否存在
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("positionUpdate_check")
	@ResponseBody
	public String positionUpdate_check(HttpServletRequest req, String academy, String department , String position_name, 
					String position_type, String position_level, String update_time, Model model) throws UnsupportedEncodingException{	//在形参中获取数据,HttpServletRequest只是响应请求防止ajax进入error

		boolean bool1=academy.replace(" ","").equals("")||academy==null;
		boolean bool2=department.replace(" ","").equals("")||department==null;
		boolean bool3=position_name.replace(" ","").equals("")||position_name==null;
		boolean bool4=position_type.replace(" ","").equals("")||position_type==null;
		boolean bool5=position_level.replace(" ","").equals("")||position_level==null;
		boolean bool6=update_time.replace(" ","").equals("")||update_time==null;
		
		System.out.println(academy+department+position_name+position_type+position_level+update_time);
		if(bool1||bool2||bool3||bool4||bool5||bool6){
			String  text = URLEncoder.encode("请将数据填写完整再试",   "utf-8");  	//编码之后再传，解决前端乱码
			return text;
		}else{
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
			Date position_day;
			java.sql.Date sqlDate=null;
			try {
				position_day=sdf.parse(update_time);
				sqlDate=new java.sql.Date(position_day.getTime());
				//System.out.println(sqlDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				String text=URLEncoder.encode("日期格式错误，请修正",   "utf-8");
				e.printStackTrace();
				return text;
			}
			//去postion表中查询是否存在岗位，返回查询到所有岗位的id
			List<Position> pos =positionService.findByData(academy,department,position_name,position_type,position_level,sqlDate);
			if(pos.isEmpty()){
				String  text = URLEncoder.encode("检测结果:数据库中不存在这条数据，不允许操作&",   "utf-8");
				return text;
			}
			if(pos.size()==1){
				String id=pos.get(0).getId()+"";		//得到需要操作岗位的id，并且发送到前端去
				String text = URLEncoder.encode("检测结果:数据库中存在这条数据，且唯一，可以直接修改",   "utf-8");
				text=text+"&"+id;
				return text;
			}else{
				//有多个满足条件的岗位，查询position_postuser表，把这个岗位上的所有人员列出来
				ArrayList<String> name =new ArrayList<String>();
				ArrayList<String> pos_id = new ArrayList<String>();
				for(Position p:pos){
					pos_id.add(p.getId()+"");	//把查到符合条件的id全部添加进来
					List<PositionPostuser> pu=positionService.findUserbyPosId(p.getId());
					if(!pu.isEmpty())
						name.add(positionService.findUserbyPosId(p.getId()).get(0).getUser().getName());
				}
				String one_part="检测结果:数据库中存在这条数据，不唯一，有"+pos.size()+"个相同岗位，岗位上的人员情况为<select class='chos' onchange='getpid(this)'><option value=0>请选择其中一个</option>";
				String two_part="";

				for(int i=0,num=1; i<pos.size(); i++){
					if(i<name.size()){
						two_part=two_part+"<option value="+pos.get(i).getId()+">"+name.get(i)+"</option>";		//拼接html代码
					}else{
						two_part=two_part+"<option value="+pos.get(i).getId()+">空岗"+num+"</option>";
						num++;
					}
				}
				two_part=two_part+"</select>&many";
				String  text = URLEncoder.encode(one_part+two_part,   "utf-8");
				return text;
			}
			
		}				
		//System.out.println(academy+department+position_name+position_type+position_level+update_time);
	}
	
	/**
	 * 接收前端送来的数据后更新数据库
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	String filepath_temp;
	@RequestMapping("getPaperFile")
	@ResponseBody
	public String getPaperFile(MultipartHttpServletRequest multiRequest, HttpServletResponse res) throws IOException{
		//PrintWriter writer=res.getWriter();
		Iterator<String> iter = multiRequest.getFileNames();
		while(iter.hasNext()){
			MultipartFile fe=multiRequest.getFile(iter.next());
			//HttpFileCollection hfc = System.Web.HttpContext.Current.Request.Files;
			if (fe.isEmpty()){
				return "fail1";
			}else{
				String fileName = fe.getOriginalFilename();//上传的文件名
				String[] nameArr = fileName.split("\\.");
				String suff = nameArr[nameArr.length-1];
				if(!suff.equals("docx")&& !suff.equals("doc") && !suff.equals("pdf")){
					return "fail2";
				}
				fileName = "file" + UUID.randomUUID() +"."+suff;
				String path = WebApplication.getSession().getServletContext().getRealPath("../xmuCadreFile/paperFile/");
				File targetFile = new File(path, fileName);  
				if(!targetFile.exists()){  
					targetFile.mkdirs();  
				} 
				//glo_path=path;
				filepath_temp="../xmuCadreFile/paperFile/" + fileName;
				//fname=fileName;
			//保存  
			//paper.setPaperFile("../uploadFile/paper/" + fileName);
				try {  
					fe.transferTo(targetFile);  
					//writer.write("<script type='text/javascript' type='language'>parent.callback('文件正确，可以上传');</scrpt>");
				} catch (Exception e) {  
					//writer.write("<script type='text/javascript' type='language'>parent.callback('文件错误');</scrpt>");
					throw new RuntimeException("文件保存异常"+e.getMessage()); 
				}	
			}
		}
		return "success";
	}
	
	@RequestMapping("positionUpdate")
	@ResponseBody
	public String positionUpdate(HttpServletRequest req,String data,Model model) throws IOException{
		String original_data=URLDecoder.decode(data,"UTF-8");
		//ArrayList<String> data_split = new ArrayList<String>();
		String[] data_split =new String[100];
		
		data_split = original_data.split("amp;end_mark=table_end");	//分割之后每一串都是要添加到数据库中的一条数据
		
		Paper paper_data=null;
		List<PaperContent> paper_content = new ArrayList<PaperContent>();
		
		String paper_time="";
		for(int i=0; i<data_split.length; i++){
			//处理发文数据
			if(i==0){
				Pattern p = Pattern.compile("\\=(.*?)\\&");//正则表达式，取=和&之间的字符串，不包括=和&
				Matcher m = p.matcher(data_split[i]);
				int num=0;
				String paper_name="", paper_num="";
				while(m.find()){
					num++;
					if(num==1)
						paper_name=m.group(1);
					if(num==2)
						paper_num=m.group(1);
					if(num==3)
						paper_time=m.group(1);
				}
				SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
				//java.sql.Date sqlDate=null;
				//tt = sdf.parse(paper_time);
				//sqlDate=new java.sql.Date(tt.getTime());
				Date tt;
				try {
					tt = sdf.parse(paper_time);
					Paper paper =new Paper();
					paper.setPaperName(paper_name);
					paper.setPaperDay(tt);
					paper.setPaperNumber(paper_num);
					paper.setPaperType("岗位信息发文");
					//paperService.addPaper(paper);
					List<Paper> paperlist=paperService.findPaperByData(paper_name, tt, paper_num);	//检查是否有重复的paper
					if(paperlist.size()>0){
						return "fail4";
					}
					if(StringUtils.isNotBlank(filepath_temp))
						paper.setPaperFile(filepath_temp);
					paper_data=paper;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "fail0";				//由于spring的注解，代替了在HttpServletResponse中设置返回字符串
				}
			}
			
			Pattern pp = Pattern.compile("\\#(.*?)\\&");//正则表达式，取第一个mark的值
			Matcher mm = pp.matcher(data_split[i]);
			if(mm.find()){
				if(mm.group(1).equals("增加岗位")){
					System.out.println(data_split[i]);
					String sub_s=data_split[i].substring(20);
					System.out.println(sub_s);
					Pattern p = Pattern.compile("\\=(.*?)\\&");//正则表达式，取=和&之间的字符串
					Matcher m = p.matcher(sub_s);
					int num=0;
					String academy="", department="", position_name="",position_type="",position_level="",update_time="", positionRemark="";
					while(m.find()){
						num++;
						if(num==1)	academy=m.group(1);
						if(num==2)	department=m.group(1);
						if(num==3)	position_name=m.group(1);
						if(num==4)	position_type=m.group(1);
						if(num==5)  position_level=m.group(1);
						if(num==6)	update_time=m.group(1);
						if(num==7)  positionRemark=m.group(1);
					}
					//System.out.println("academy"+academy+"department"+department+"position_name"+position_name+"position_type"+position_type+"position_level"+position_level+"update_time"+update_time);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date tt;
					try {
						tt = sdf.parse(update_time);
						PaperContent pc = new PaperContent();
						pc.setPaper(paper_data);
						pc.setAcademy1(academy);
						pc.setDepartment1(department);
						pc.setPositionLevel1(position_level);
						pc.setPositionName1(position_name);
						pc.setPositionType1(position_type);
						pc.setPositionDay1(tt);
						pc.setPositionRemark1(positionRemark);
						pc.setEditType(1+"");			//1是增加岗位
						pc.setPaper(paper_data);
						paper_content.add(pc);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "fail1";
					}
					
				}
				String delPositionDay="";
				if(mm.group(1).equals("撤除岗位")){
					System.out.println(data_split[i]);
					String sub_s=data_split[i].substring(20);
					System.out.println(sub_s);
					Pattern p = Pattern.compile("\\=(.*?)\\&");//正则表达式，取=和&之间的字符串
					Matcher m = p.matcher(sub_s);
					int num=0;
					String academy="", department="", position_name="",position_type="",position_level="",update_time="",position_id="";
					
					while(m.find()){
						num++;
						if(num==1)	academy=m.group(1);
						if(num==2)	department=m.group(1);
						if(num==3)	position_name=m.group(1);
						if(num==4)	position_type=m.group(1);
						if(num==5)  position_level=m.group(1);
						if(num==6)	update_time=m.group(1);
						if(num==7)  delPositionDay=m.group(1);
						if(num==8)  position_id=m.group(1);
					}
					//System.out.println("academy"+academy+"department"+department+"position_name"+position_name+"position_type"+position_type+"position_level"+position_level+"update_time"+update_time+"position_id"+position_id);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date tt;
					try {
						tt = sdf.parse(update_time);
						PaperContent pc = new PaperContent();
						pc.setPaper(paper_data);
						pc.setAcademy1(academy);
						pc.setDepartment1(department);
						pc.setPositionLevel1(position_level);
						pc.setPositionName1(position_name);
						pc.setPositionType1(position_type);
						pc.setPositionDay1(tt);
						if(StringUtils.isNotBlank(delPositionDay)){
							tt = sdf.parse(delPositionDay);
							pc.setDelPositionDay1(tt);
						}else{
							tt = sdf.parse(paper_time);
							pc.setDelPositionDay1(tt);
						}
						pc.setEditType(2+"");			//2是删除岗位
						pc.setPaper(paper_data);
						try{
							int id = Integer.parseInt(position_id);		//旧岗id
							pc.setPositionId(id);
							paper_content.add(pc);
						} catch(NumberFormatException e){
							e.printStackTrace();
							return "fail7";
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "fail5";
					}
				}
				String delPosDay1="", new_position_remark="";
				if(mm.group(1).equals("撤除从")){			//先撤后增
					System.out.println(data_split[i]);
					String sub_s=data_split[i].substring(20);
					System.out.println(sub_s);
					Pattern p = Pattern.compile("\\=(.*?)\\&");//正则表达式，取=和&之间的字符串
					Matcher m = p.matcher(sub_s);
					int num=0;
					String academy="", department="", position_name="",position_type="",position_level="",update_time="",position_id="";
					String new_academy="", new_department="", new_position_name="",new_position_type="",new_position_level="",new_update_time="";
					
					while(m.find()){
						num++;
						if(num==1)	academy=m.group(1);
						if(num==2)	department=m.group(1);
						if(num==3)	position_name=m.group(1);
						if(num==4)	position_type=m.group(1);
						if(num==5)  position_level=m.group(1);
						if(num==6)	update_time=m.group(1);
						if(num==7)  delPosDay1 = m.group(1);			
						if(num==8)  position_id=m.group(1);
						
						if(num==10)	new_academy=m.group(1);
						if(num==11)	new_department=m.group(1);
						if(num==12)	new_position_name=m.group(1);
						if(num==13)	new_position_type=m.group(1);
						if(num==14)	new_position_level=m.group(1);
						if(num==15)	new_update_time=m.group(1);
						if(num==16) new_position_remark=m.group(1);
					}
					//System.out.println("academy"+academy+"department"+department+"position_name"+position_name+"position_type"+position_type+"position_level"+position_level+"update_time"+update_time+"position_id"+position_id);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date tt;
					Date tt1;		//旧岗时间
					Date tt2;		//新岗时间
					try {
						tt2 = sdf.parse(new_update_time);
						tt1 = sdf.parse(update_time);
						PaperContent pc = new PaperContent();
						pc.setPaper(paper_data);
						//旧岗信息
						pc.setAcademy1(academy);
						pc.setDepartment1(department);
						pc.setPositionLevel1(position_level);
						pc.setPositionName1(position_name);
						pc.setPositionType1(position_type);
						pc.setPositionDay1(tt1);
						if(StringUtils.isNotBlank(delPosDay1)){
							tt = sdf.parse(delPosDay1);
							pc.setDelPositionDay1(tt);
						}else{
							tt = sdf.parse(paper_time);
							pc.setDelPositionDay1(tt);
						}
						//新岗信息
						pc.setAcademy2(new_academy);
						pc.setDepartment2(new_department);
						pc.setPositionLevel2(new_position_level);
						pc.setPositionDay2(tt2);
						pc.setPositionName2(new_position_name);
						pc.setPositionType2(new_position_type);
						pc.setPositionRemark2(new_position_remark);
						pc.setEditType(3+"");			//3先删后增
						pc.setPaper(paper_data);
						try{
							int id = Integer.parseInt(position_id);		//旧岗id
							pc.setPositionId(id);
							paper_content.add(pc);
						} catch(NumberFormatException e){
							e.printStackTrace();
							return "fail7";
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "fail2";
					}
				}
				if(mm.group(1).equals("修改前")){			//岗位部分属性修改
					System.out.println(data_split[i]);
					String sub_s=data_split[i].substring(20);
					System.out.println(sub_s);
					Pattern p = Pattern.compile("\\=(.*?)\\&");//正则表达式，取=和&之间的字符串
					Matcher m = p.matcher(sub_s);
					int num=0;
					String academy="", department="", position_name="",position_type="",position_level="",update_time="",position_id="";
					String new_position_name="" ,new_position_type="", new_update_time="";
					while(m.find()){
						num++;
						if(num==1)	academy=m.group(1);
						if(num==2)	department=m.group(1);
						if(num==3)	position_name=m.group(1);
						if(num==4)	position_type=m.group(1);
						if(num==5)  position_level=m.group(1);
						if(num==6)	update_time=m.group(1);
						if(num==7)  position_id=m.group(1);
						if(num==9)  new_position_name=m.group(1);
						if(num==10)  new_position_type=m.group(1);
						if(num==11)  new_update_time=m.group(1);
					}
					//System.out.println("academy"+academy+"department"+department+"position_name"+position_name+"position_type"+position_type+"position_level"+position_level+"update_time"+update_time);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date tt1;
					Date tt2;
					try {
						tt2 = sdf.parse(new_update_time);
						tt1 = sdf.parse(update_time);
						PaperContent pc = new PaperContent();
						pc.setPaper(paper_data);
						//旧岗信息
						pc.setAcademy1(academy);
						pc.setDepartment1(department);
						pc.setPositionLevel1(position_level);
						pc.setPositionName1(position_name);
						pc.setPositionType1(position_type);
						pc.setPositionDay1(tt1);
						//新岗信息
						pc.setAcademy2(academy);
						pc.setDepartment2(department);
						pc.setPositionLevel2(position_level);
						pc.setPositionDay2(tt2);					//只有时间，名字，类型可以修改
						pc.setPositionName2(new_position_name);		
						pc.setPositionType2(new_position_type);
						pc.setEditType(4+"");						//4岗位信息部分修改
						pc.setPaper(paper_data);
						try{
							int id = Integer.parseInt(position_id);		//旧岗id
							pc.setPositionId(id);
							paper_content.add(pc);
						} catch (NumberFormatException e){
							e.printStackTrace();
							return "fail7";
						}
						//positionService.editById(pid, new_position_name, new_position_type, tt);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "fail3";
					}
					
				}
			}
		}
		//循环完毕，且没有出错，将数据添加到数据库
		paperService.addPaper(paper_data);
		if(paper_content.size()==0){
			return "fail6";
		}else{
			for(PaperContent pc:paper_content){
				paperService.savePaperContent(pc);
				if(pc.getEditType().equals("1")){		//添加岗位
					Position p = new Position();
					p.setAcademy(pc.getAcademy1());
					p.setDepartment(pc.getDepartment1());
					p.setPositionName(pc.getPositionName1());
					p.setPositionLevel(pc.getPositionLevel1());
					p.setPositionType(pc.getPositionType1());
					p.setPositionDay(pc.getPositionDay1());
					p.setPositionRemark(pc.getPositionRemark1());
					p.setPaper(paper_data);
					positionService.save(p);
				}
				if(pc.getEditType().equals("2")){		//删除岗位
					positionService.deleteById(pc.getPositionId());
					Position pos=positionService.findById(pc.getPositionId());
					pos.setDelPositionDay(pc.getDelPositionDay1());
					positionService.update(pos);
				}
				if(pc.getEditType().equals("3")){		//撤旧岗添新岗
					positionService.deleteById(pc.getPositionId());
					Position pos=positionService.findById(pc.getPositionId());
					pos.setDelPositionDay(pc.getDelPositionDay1());
					positionService.update(pos);
					
					Position p = new Position();
					p.setAcademy(pc.getAcademy2());
					p.setDepartment(pc.getDepartment2());
					p.setPositionName(pc.getPositionName2());
					p.setPositionLevel(pc.getPositionLevel2());
					p.setPositionType(pc.getPositionType2());
					p.setPositionDay(pc.getPositionDay2());
					p.setPositionRemark(pc.getPositionRemark2());
					p.setPaper(paper_data);
					positionService.save(p);
				}
				if(pc.getEditType().equals("4")){
					Position pos=positionService.findById(pc.getPositionId());
					pos.setPositionName(pc.getPositionName2());
					pos.setPositionType(pc.getPositionType2());
					pos.setPositionDay(pc.getPositionDay2());
					positionService.update(pos);
				}
			}
		}
		filepath_temp="";
		return "success";
	}
	
	/**
	 * 判断该名字是否有对应岗位存在
	 * @param insName
	 * @return
	 */
	@RequestMapping("existInsName")
	@ResponseBody
	@Transactional
	public List<String> existInsName(String insName, Integer flag){
		List<String> sList = new ArrayList<String>();
		if (null == insName){
			return sList;
		}	
		
		List<Position> pList= positionService.queryVague(insName, flag);
		//System.out.println(pList.size());
		if(pList.size()!=0){
		for (Position p : pList){
			if(flag==1){
				sList.add(p.getAcademy());
			}
			if(flag==2){
				sList.add(p.getDepartment());
			}
			if(flag==3){
				sList.add(p.getPositionName());
			}
			if(flag==4){
				sList.add(p.getPositionType());
			}
		}
		}
		return sList;
	}
	
	
	/**
	 * 检查岗位正确性
	 * @param academy
	 * @param department
	 * @param posName
	 * @param posType
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("checkPositionCorrect")
	@ResponseBody
	@Transactional
	public String checkPositionCorrect(String academy, String department, String posName, String posType, String appointOrDismiss, String userId ) throws UnsupportedEncodingException{
		
		if(academy.length()==0 ||department.length()==0 || posName.length()==0 || posType.length()==0){
			
			return "invalidData";
		}
		int iid=Integer.parseInt(userId);
		if(appointOrDismiss.equals("0")){		//任职岗位正确性
			List<Position> pList= positionService.hasEmptyPositionByAttr(academy, department,posName,posType);
			if(pList.size()>0){
				int id=pList.get(0).getId();
				String  text = URLEncoder.encode("岗位可用"+id,   "utf-8");
				return text;
			}else{
				String  text = URLEncoder.encode("岗位不可用",   "utf-8");
				return text;
			}
		}else{			//免职岗位正确性
			System.out.println("!!!~~~~@@");
			List<Position> pList= positionService.hasDoingPositionByAttrAndUserId(academy, department,posName,posType,iid);
			if(pList.size()>0){
				int id=pList.get(0).getId();
				String  text = URLEncoder.encode("岗位可用"+id,   "utf-8");
				return text;
			}else{
				String  text = URLEncoder.encode("岗位不可用",   "utf-8");
				return text;
			}
		}
		
	}
}
