package com.cadre.controller.infoManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.controller.pojo.BirthPlace;
import com.cadre.controller.pojo.OrigionPlace;
import com.cadre.controller.pojo.UserShowView;
import com.cadre.controller.pojo.UserSimple;
import com.cadre.controller.sys.UserController;
import com.cadre.model.page.Page;
import com.cadre.model.utils.DateUtil;
import com.cadre.model.utils.DigestUtil;
import com.cadre.model.utils.StringUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.AdministrationLevelHistory;
import com.cadre.pojo.AdministrationWorkHistory;
import com.cadre.pojo.Country;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.EducationHistory;
import com.cadre.pojo.ExportUserExcel;
import com.cadre.pojo.Organization;
import com.cadre.pojo.Place;
import com.cadre.pojo.Position;
import com.cadre.pojo.PositionPostuser;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.Relation;
import com.cadre.pojo.Secondment;
import com.cadre.pojo.SecondmentUser;//sm
import com.cadre.pojo.Skill;
import com.cadre.pojo.Train;//Train
import com.cadre.pojo.TrainUser;
import com.cadre.pojo.User;
import com.cadre.service.dictionary.DictionaryValueService;
import com.cadre.service.education.EducationService;
import com.cadre.service.infoManager.AdministrationWorkService;
import com.cadre.service.infoManager.AdminstrationLevelService;
import com.cadre.service.infoManager.DictionaryDbDegreeService;
import com.cadre.service.infoManager.EducationHistoryService;
import com.cadre.service.infoManager.PositionPostuserService;
import com.cadre.service.infoManager.PostUsersService;
import com.cadre.service.infoManager.RelationService;
import com.cadre.service.infoManager.SecondmentUsersService;
import com.cadre.service.infoManager.SecondmentsService;//sm
import com.cadre.service.infoManager.TableMappingService;//sm
import com.cadre.service.infoManager.TrainService;//Train
import com.cadre.service.infoManager.TrainUserService;//Train
import com.cadre.service.position.PositionService;
import com.cadre.service.skill.SkillService;
import com.cadre.service.sys.CountryService;
import com.cadre.service.sys.OrganizationService;
import com.cadre.service.sys.PlaceService;
import com.cadre.service.sys.SysRoleService;
import com.cadre.service.sys.UserService;
//import com.sun.org.apache.bcel.internal.generic.NEW;
//import com.sun.org.apache.regexp.internal.recompile;
//import java.util.Date; 
import java.util.Calendar; 

import java.text.DateFormat;
import java.text.SimpleDateFormat; 

@Controller
@RequestMapping(ViewLocation.FOLDER_INFOMANAGER_USERINFO)
public class UserInfoController {
	private Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	PositionService positionService;
	@Autowired
	PositionPostuserService positionPostuserService;
	@Autowired
	SkillService skillService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	HttpSession session;
	@Autowired
	
	TableMappingService tableMappingService;
	@Autowired
	PlaceService placeService;
	@Autowired
	CountryService countryService;
	@Autowired
	AdminstrationLevelService adminstrationLevelService;
	@Autowired
	AdministrationWorkService administrationWorkService;
	@Autowired
	EducationHistoryService educationHistoryService;
	@Autowired
	RelationService relationService;
	@Autowired
	TrainService trainService;
	@Autowired
	TrainUserService trainUserService;
	@Autowired
	SecondmentUsersService secondmentUsersService;
	@Autowired
	SecondmentsService secondmentsService;
	@Autowired
	PostUsersService postUsersService;
	@Autowired
	DictionaryDbDegreeService dictionaryDbDegreeService;
	@Autowired
	DictionaryValueService dictionaryService;
	@Autowired
	EducationService educationService;
	
	int pageSize=15; 
	List<User> lists = null;
	
	@RequestMapping("userList")
	public String userList(Integer currPage,String queryStr,Model model) throws Exception{	
		try {
			if(currPage == null || currPage == -1) currPage = 1;//默认显示第一页
			Page<UserShowView> queryList = userService.findUserByPage(queryStr, currPage, pageSize);
			if(StringUtil.isNotBlank(queryStr))
				lists = userService.findUser(queryStr);
			model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
			model.addAttribute("queryList",queryList);//查询结果列表
			model.addAttribute("isSuperSearch", "0");
			model.addAttribute("today",new Date());
			//boolean isAdmin = WebApplication.getCurrUser().isAdmin();
			//boolean hasAdminQx = sysUserService.findUserHasRole(WebApplication.getCurrUser().getId());
			//model.addAttribute("isAdmin", isAdmin||hasAdminQx);
			if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
				model.addAttribute("roleType", "admin");
				return ViewLocation.FOLDER_INFOMANAGER_USERINFO+"/userList";
			}else {
				model.addAttribute("roleType", "visitor");
				return ViewLocation.FOLDER_INFOMANAGER_USERINFO+"/userList";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 高级搜索功能
	 * @param currPage
	 * @param name
	 * @param sex
	 * @param IdNumber
	 * @param birthdayOperator
	 * @param birthday
	 * @param ageOperator
	 * @param age
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/SuperSearchUserList")
	public String SuperSearchUserList(Integer currPage,String name,String sex,
			String idNumber,String birthdayOperator,Date birthday,
			String ageOperator,String age,
			String positionName,String positionNameOperator,Model model) throws Exception{
		
		lists = userService.advanceSearchUser(name,sex,idNumber,birthday,birthdayOperator,
				age,ageOperator,positionName,positionNameOperator);
		if(currPage == null || currPage == -1) currPage = 1;//默认显示第一页
		Page<UserShowView> queryList = userService.findSearchUserByPage(name,sex,idNumber,birthday,birthdayOperator,
				age,ageOperator,positionName,positionNameOperator, currPage, pageSize);
		
		model.addAttribute("queryList",queryList);//查询结果列表
		model.addAttribute("sex",sex);
		model.addAttribute("name", name);
		model.addAttribute("idNumber",idNumber);
		model.addAttribute("birthdayOperator", birthdayOperator);
		model.addAttribute("birthday",birthday);
		model.addAttribute("today",new Date());
		model.addAttribute("ageOperator", ageOperator);
		model.addAttribute("age", age);
		model.addAttribute("isSuperSearch", "1");
		model.addAttribute("positionName", positionName);
		model.addAttribute("positionNameOperator", positionNameOperator);
		return ViewLocation.FOLDER_INFOMANAGER_USERINFO+"/userList";
	}
	/**
	 * 打开表头选取界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/openTitleCheck")
	public String openTitleCheck(Model model){
		return ViewLocation.FOLDER_INFOMANAGER_USERINFO+"/openTitleCheck";
	}
	
	/**
	 * 打开导出花名册界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/openExportRoster")
	public String openExportRoster(Model model){
		return ViewLocation.FOLDER_INFOMANAGER_USERINFO+"/exportRoster";
	}
	
	/**
	 * 根据查询条件，做一个List 'ExportUserExcel'
	 */
	List<ExportUserExcel> eue = new ArrayList<ExportUserExcel>();
	@RequestMapping("queryExportRoster")
	@ResponseBody
	public String queryExportRoster(HttpServletRequest req,String academy,String age1, String age2, String level, Model model){
//		System.out.println("academy"+academy);
//		System.out.println("age1"+age1);
//		System.out.println("age2"+age2);
//		System.out.println("level"+level);
		List<User> userlist = userService.queryUserByageAndLevel(age1, age2, level);
		//System.out.println("usersize:"+userlist.size());
		List<Position> positions = positionService.queryByAcademy(academy);
		//此时顺序已经确定好了
		//System.out.println("positionsize:"+positions.size());
		if(userlist.size()==0 && positions.size()==0){
			return "nodata";
		}

		//按照组织机构的指定顺序，构造ExportUserExcel
		for(Position p: positions){
			//System.out.println(p.getAcademy()+"!!"+p.getDepartment()+"!!"+p.getPositionName());
			ExportUserExcel e = new ExportUserExcel();
			//设置职务
			e.setAcademy(p.getAcademy());
			e.setDepartment(p.getDepartment());
			e.setPositionName(p.getPositionName());
			e.setJobName(p.getPositionRemark());		//职务备注
			e.setPositionLevel(p.getPositionLevel());
			if(p.getUser()!=null){
				//遍历userlist把有岗位的人从中踢出
				List<User> uuList = new ArrayList<User>(); 
				//System.out.println(p.getUser().getName()+"!!!!"+userlist.get(0).getName());
				for(User u: userlist){
					if(u.getId()==p.getUser().getId()){
						uuList.add(u);
					}
				}
				userlist.removeAll(uuList);
				
				e.setBeginSchoolWorkDay(p.getUser().getBeginSchoolWorkDay());
				e.setBeginWorkDay(p.getUser().getBeginWorkDay());
				e.setBirthDay(p.getUser().getBirthDay());
				e.setGender(p.getUser().getGender());
				e.setLevelDay(p.getUser().getLevelTime());
				e.setLevelName(p.getUser().getLevel());
				e.setName(p.getUser().getName());
				e.setNation(p.getUser().getNation());
				e.setOriginPlace(p.getUser().getOriginPlace());
				e.setParty(p.getUser().getParty());
				//设置最近的专业技术职务
				List<Skill> sklist = skillService.findByUserId(p.getUser().getId());
				if(sklist==null || sklist.size()==0){
					e.setSkillName("");
				}else{
					e.setSkillName(sklist.get(0).getSkillName());
				}
				//设置最近的岗位变动时间
				List<PostUser> pu = postUsersService.getUserLastActionDayByUser(p.getUser());
				if(pu==null || pu.size()==0){
					
				}else{
					e.setNowWorkDay(pu.get(0).getActionDay());
				}
				
				//设置学历学位
				List<EducationHistory> edulist = educationService.findByUserId(p.getUser().getId());
				if(edulist==null || edulist.size()==0){
					e.setSchool("");
					e.setDegree("");
				}else{
					String lh="", deg="";
					if(StringUtil.isNotBlank(edulist.get(0).getSchool())){
						e.setSchool(edulist.get(0).getSchool());
					}
					if(StringUtil.isNotBlank(edulist.get(0).getLearningPhase())){	//学历
						lh=edulist.get(0).getLearningPhase();
					}
					if(StringUtil.isNotBlank(edulist.get(0).getDegree())){			//学位
						deg=edulist.get(0).getLearningPhase();
					}
					e.setDegree(lh+deg);
				}
				//设置年龄
				if(p.getUser().getBirthDay()==null){
					
				}else{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String str = sdf.format(p.getUser().getBirthDay());
					int yy = Integer.parseInt(str.substring(0, 4));
					int mm = Integer.parseInt(str.substring(5, 7));			
					Calendar now = Calendar.getInstance();
					int year = now.get(Calendar.YEAR);
					int month = now.get(Calendar.MONTH);
					if(month>=mm){
						e.setAge(year-yy);
					}
					else{
						e.setAge(year-yy);
					}
				}
			}
			eue.add(e);
		}
		
		//此时userlist中是  "不在任何服役中的岗位上的，但没有退休，且有级别的人" ,继续构造ExportUserExcel
		for(User user :userlist){
			ExportUserExcel e = new ExportUserExcel();
			e.setBeginSchoolWorkDay(user.getBeginSchoolWorkDay());
			e.setBeginWorkDay(user.getBeginWorkDay());
			e.setBirthDay(user.getBirthDay());
			e.setGender(user.getGender());
			e.setLevelDay(user.getLevelTime());
			e.setLevelName(user.getLevel());
			e.setName(user.getName());
			e.setNation(user.getNation());
			e.setOriginPlace(user.getOriginPlace());					//这些需要从字典表中间接查询,先查询一次字典表构造一个键--值得map然后再从map中取
			e.setParty(user.getParty());
			//设置最近的专业技术职务
			List<Skill> sklist = skillService.findByUserId(user.getId());
			if(sklist==null || sklist.size()==0){
				e.setSkillName("");
			}else{
				e.setSkillName(sklist.get(0).getSkillName());
			}
			//设置最近的岗位变动时间
			List<PostUser> pu = postUsersService.getUserLastActionDayByUser(user);
			if(pu==null || pu.size()==0){
				
			}else{
				e.setNowWorkDay(pu.get(0).getActionDay());
			}
			
			//设置学历学位
			List<EducationHistory> edulist = educationService.findByUserId(user.getId());
			if(edulist==null || edulist.size()==0){
				e.setSchool("");
				e.setDegree("");
			}else{
				String lh="", deg="";
				if(StringUtil.isNotBlank(edulist.get(0).getSchool())){
					e.setSchool(edulist.get(0).getSchool());
				}
				if(StringUtil.isNotBlank(edulist.get(0).getLearningPhase()) && edulist.get(0).getLearningPhase()!=null){	//学历
					lh=edulist.get(0).getLearningPhase();
				}
				if(StringUtil.isNotBlank(edulist.get(0).getDegree()) && edulist.get(0).getDegree()!=null){			//学位
					deg=edulist.get(0).getLearningPhase();
				}
				System.out.println("degree:"+lh+deg);
				e.setDegree(lh+deg);
			}
			//设置年龄
			if(user.getBirthDay()==null){
			
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str = sdf.format(user.getBirthDay());
				int yy = Integer.parseInt(str.substring(0, 4));
				int mm = Integer.parseInt(str.substring(5, 7));			
				Calendar now = Calendar.getInstance();
				int year = now.get(Calendar.YEAR);
				int month = now.get(Calendar.MONTH);
				if(month>=mm){
					e.setAge(year-yy);
				}
				else{
					e.setAge(year-yy);
				}
			}
			
			eue.add(e);
		}
		return "success";
	}
	/**
	 * 导出花名册Excel，对应信息已经在ExportUserExcel————eue里了
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("exportUserList")
	@ResponseBody
	public void exportUserList(Model model, HttpServletResponse response) throws Exception{
		//文件流
		System.out.println("!!!@@"+eue.size());
		String fileName = "";
		String dir = WebApplication.getSession().getServletContext().getRealPath("/resources/downloadFile/");
		String tempFileName = dir+"/user_model.xls";
		fileName= "user"+System.currentTimeMillis()+".xls";
		Workbook wb = Workbook.getWorkbook(new File(tempFileName));			//获取模板
    	WritableWorkbook wwb = Workbook.createWorkbook(new File(dir+"/"+fileName), wb); 	//在服务器上创建被写的临时文件
    	WritableFont   wf   =   new   WritableFont(WritableFont.createFont("宋体"),10,WritableFont.NO_BOLD,false); 	//设置字体
    	WritableCellFormat   wcf   =   new   WritableCellFormat(wf); 
    	wcf.setWrap(true);									//自动换行
    	wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 		//设置单元格边框
        WritableSheet sheet =wwb.getSheet(0);
        //加制表时间
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy年MM月dd");
		String time=format.format(date);
		Label titleLabel = new Label(18,1,"制表时间"+time, wcf);
		sheet.addCell(titleLabel);
        String[] titleArr = new String[21];		//总共有21列数据
        for(int i=0;i<21;i++){
        	titleArr[i]=i+"";
        }
        Label valueLabel = null;
        
        int row=3;
	    //现在所有需要导出数据都已经保存于exportUser
	    for(ExportUserExcel u:eue){
	    	//export field	    	
			String name, degree;
			name=u.getName();
			degree=u.getDegree();
			if(name!=null && name.length()==2){
				StringBuffer ss = new StringBuffer(name);
				ss.insert(1, "  ");
				name=ss.toString();
			}
			
			//按列写数据到Excel
			for(int n=0;n<titleArr.length;n++){			
				if(n==0){
					valueLabel = new Label(n,row,row-2+"",wcf);	//n是列，row是行
					sheet.addCell(valueLabel);
				}
				if(n==1){
					valueLabel = new Label(n,row,name,wcf);
					sheet.addCell(valueLabel);
				}
				if(n==2){
					valueLabel = new Label(n,row,u.getAcademy(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==3){
					valueLabel = new Label(n,row,u.getDepartment(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==4){
					//String value = tableMappingService.getDbName(u.getPositionName(),5);	//职位名称
					valueLabel = new Label(n,row,u.getPositionName(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==5){
					valueLabel = new Label(n,row,u.getSkillName(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==6){
					valueLabel = new Label(n,row,u.getGender(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==7){
					//String value = tableMappingService.getDbName(u.getNation(),1);
					valueLabel = new Label(n,row,u.getNation(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==8){
					String value = tableMappingService.Date2Str(u.getBirthDay());
					valueLabel = new Label(n,row,value,wcf);
					sheet.addCell(valueLabel);
				}
				if(n==9){
					valueLabel = new Label(n,row,u.getAge()+"",wcf);
					sheet.addCell(valueLabel);
				}
				if(n==10){
					//valueLabel = new Label(n,row,placeMap.get(u.getOriginPlace()),wcf);
					valueLabel = new Label(n,row,u.getOriginPlace(),wcf);
					sheet.addCell(valueLabel);				
				}
				if(n==11){
					String value = tableMappingService.Date2Str(u.getBeginWorkDay());
					valueLabel = new Label(n,row,value,wcf);
					sheet.addCell(valueLabel);
				}
				if(n==12){
					String value = tableMappingService.Date2Str(u.getBeginSchoolWorkDay());
					valueLabel = new Label(n,row,value,wcf);
					sheet.addCell(valueLabel);
				}
				if(n==13){
					//String value = tableMappingService.getDbName(u.getParty(),2);
					valueLabel = new Label(n,row,u.getParty(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==14){
					valueLabel = new Label(n,row,degree,wcf);
					sheet.addCell(valueLabel);
				}
				if(n==15){
					valueLabel = new Label(n,row,u.getSchool(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==16){
					String value = tableMappingService.Date2Str(u.getNowWorkDay());
					valueLabel = new Label(n,row,value,wcf);
					sheet.addCell(valueLabel);
				}
				if(n==17){
					//String value = tableMappingService.getDbName(u.getLevelName(),4);
					valueLabel = new Label(n,row,u.getLevelName(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==18){
					String value = tableMappingService.Date2Str(u.getLevelDay());
					valueLabel = new Label(n,row,value,wcf);
					sheet.addCell(valueLabel);
				}
				if(n==19){
					valueLabel = new Label(n,row,u.getPositionLevel(),wcf);
					sheet.addCell(valueLabel);
				}
				if(n==20){
					//String value = tableMappingService.getDbName(u.getJobName(),5);		//职位名称(有些特殊要求)
					valueLabel = new Label(n,row,u.getJobName(),wcf);
					sheet.addCell(valueLabel);
				}
      		}
			row=row+1;
	    }
	    
		wwb.write();  
    	wwb.close();
    	//placeMap =null;
    	valueLabel = null;
    	titleLabel = null;
    	BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setContentType("video/mpeg4; charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=\"user.xls\"");
			File file = new File(dir+"/"+fileName);
			int len = 0;    
			byte[] buf = new byte[1024];    
			bis = new BufferedInputStream(new FileInputStream(file));  
			bos = new BufferedOutputStream(response.getOutputStream());    
			while (-1 != (len = bis.read(buf, 0, buf.length))) {   
                bos.write(buf, 0, len);   
            }
			if (file.exists()){
				file.delete(); 
			}
			
			eue.clear();
		} catch (Exception e) {
			logger.error("导出员工信息错误:" + e.getMessage());
			eue.clear();
			throw new Exception("导出员工信息错误:" + e.getMessage());
		} finally {
			try {
				if (bis != null)   
	                bis.close();   
	            if (bos != null)   
	                bos.close();
	            eue.clear();
			} catch (Exception e2) {
				logger.error(e2.getMessage());
				eue.clear();
			}
			//删除临时文件
			File f = new File(dir+"/"+fileName); 
			if (f.exists()){
				f.delete(); 
			}
			eue.clear();
		}
	}

	/**
	 * 导出员工信息
	 * @param queryStr
	 * @param queryDept
	 * @param isNew
	 * @param titles
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping("/exportUser")
	public void exportUser(String queryStr,String isSuperSearch,String name,String sex,
			String idNumber,String birthdayOperator,Date birthday,
			String ageOperator,String age,String positionName,
			String positionNameOperator,String titles,HttpServletResponse response) throws Exception{
		if (StringUtil.isNotBlank(isSuperSearch)){
			List<UserShowView> userList = null;
			if ("0".equals(isSuperSearch)){
				if (StringUtil.isNotBlank(queryStr)) {
					try {
						queryStr = java.net.URLDecoder.decode(queryStr, "UTF-8");

					} catch (Exception e) {
						logger.error("导出文件解码错误：" + e.getMessage());
						throw new Exception(e.getMessage());
					}
				}
				userList = userService.findUserForExport(queryStr);
			}else if ("1".equals(isSuperSearch)){
				if (StringUtil.isNotBlank(name)) {
					try {
						name = java.net.URLDecoder.decode(name, "UTF-8");
					} catch (Exception e) {
						logger.error("导出文件解码错误：" + e.getMessage());
						throw new Exception(e.getMessage());
					}
				}
				if (StringUtil.isNotBlank(positionName)) {
					try {
						positionName = java.net.URLDecoder.decode(positionName, "UTF-8");
					} catch (Exception e) {
						logger.error("导出文件解码错误：" + e.getMessage());
						throw new Exception(e.getMessage());
					}
				}
				userList = userService.findSuperUserForExport(name,sex,idNumber,birthday,birthdayOperator,ageOperator,age,positionName,positionNameOperator);
			}
			if (userList.size()==0){
				return ;
			}
			String fileName = "";
			String dir = WebApplication.getSession().getServletContext().getRealPath("/resources/downloadFile/");
			String tempFileName = dir+"/user.xls";
			fileName= "user"+System.currentTimeMillis()+".xls";
			Workbook wb = Workbook.getWorkbook(new File(tempFileName));			//获取模板
	    	WritableWorkbook wwb = Workbook.createWorkbook(new File(dir+"/"+fileName), wb); 	//在服务器上创建被写的临时文件	
	        WritableSheet sheet =wwb.getSheet(0);
	        String[] titleArr = titles.split(",");
	        Label titleLabel = null;
	        Label valueLabel = null;
	        
	        //加表头	        
	        titleLabel = new Label(8,0,"厦门大学干部花名册");
  			sheet.addCell(titleLabel);
  			//加制表时间
  			Date date=new Date();
  			DateFormat format=new SimpleDateFormat("yyyy年MM月dd");
  			String time=format.format(date);
  			titleLabel = new Label(15,1,"制表时间"+time);
  			sheet.addCell(titleLabel);
  			
	  		for(int n=0;n<titleArr.length;n++){
	  			//先添加标题
	  			titleLabel = new Label(n,2,ViewFunction.getExportTitleName(titleArr[n]));
	  			sheet.addCell(titleLabel);
	  		}
	  		titleLabel = null;
		    valueLabel = null;
		    Map<String, String> placeMap = this.getPlaceMap();
		    Map<String, String> countryMap = this.getCountryMap();
	  		for(int i=0;i<userList.size();i++){//循环写数据
	      		for(int n=0;n<titleArr.length;n++){
	      			String value = tableMappingService.getUserColVal(userList.get(i), titleArr[n] ,placeMap, countryMap);
	      			valueLabel = new Label(n,i+4,value);
	      			sheet.addCell(valueLabel);
	      		}
	  			
	      	}
	  	
	  		wwb.write();  
	    	wwb.close();
	    	placeMap =null;
	  		countryMap = null;
	  		titleLabel = null;
		    valueLabel = null;
	    	BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				response.setContentType("video/mpeg4; charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"user.xls\"");
				File file = new File(dir+"/"+fileName);
				int len = 0;    
				byte[] buf = new byte[1024];    
				bis = new BufferedInputStream(new FileInputStream(file));  
				bos = new BufferedOutputStream(response.getOutputStream());    
				while (-1 != (len = bis.read(buf, 0, buf.length))) {   
	                bos.write(buf, 0, len);   
	            }
			} catch (Exception e) {
				logger.error("导出员工信息错误:" + e.getMessage());
				throw new Exception("导出员工信息错误:" + e.getMessage());
			} finally {
				try {
					if (bis != null)   
		                bis.close();   
		            if (bos != null)   
		                bos.close();
				} catch (Exception e2) {
					logger.error(e2.getMessage());
				}
				//删除临时文件
				File f = new File(dir+"/"+fileName); 
				if (f.exists()){
					f.delete(); 
				}
			}			
		}
	}
	/**
	 * 跳转 添加员工
	 * @return
	 */
	@RequestMapping("/openUserAdd")
	public String openUserAdd(Model model){
		List<Place> provList = placeService.queryAllProv();
		model.addAttribute("provList", provList);
		
		//查询国籍
		List<Country> countryList = countryService.queryAllCountrys();
		model.addAttribute("countryList", countryList);
		return ViewLocation.FOLDER_INFOMANAGER_USERINFO+"/userAdd";
	}
	
	
	
	/**
	 * 添加员工
	 * @param user
	 * @return
	 */

	@RequestMapping("/userAdd")
	public String userAdd(@RequestParam(value="photo",required = false) MultipartFile[] photos,User user,BirthPlace birthPlace,OrigionPlace origionPlace,Model model) throws Exception{
		if (null == user){
			
			model.addAttribute("errMsg", "未知的原因");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";
		}
		if (StringUtil.isBlank(user.getName()) || StringUtil.isBlank(user.getGender()) || StringUtil.isBlank(user.getIdentifyNum())){
			model.addAttribute("errMsg", "名字，性别，证件号码不能为空");
			return ViewLocation.FOLDER_MSGCOMMON + "/fail";//入参数检验
		}
		try{
			User ur = userService.findByIdentifyNumber(user.getIdentifyNum());
			if (null != ur){
				model.addAttribute("errMsg", "证件号"+user.getIdentifyNum()+"重复，此证件号已被注册，请检查输入的证件号");
				return ViewLocation.FOLDER_MSGCOMMON + "/fail";
			}
			if (user.getIdentifyNum().length()<6){
				model.addAttribute("errMsg", "证件号"+user.getIdentifyNum()+"不符合规格，请检查输入的证件号");
				return ViewLocation.FOLDER_MSGCOMMON + "/fail";
			}
			String birthPlaceStr = birthPlace.toCodeString();
			String origionPlaceStr = origionPlace.toCodeString();
			user.setBirthPlace(birthPlaceStr);
			user.setOriginPlace(origionPlaceStr);
			user.setDelFlag(1);
			user.setUserName(user.getIdentifyNum());
			/* 密码为证件号后六位*/
			user.setPassWord(DigestUtil.encryptPWD(DigestUtil.encryptPWD(user.getIdentifyNum().substring(user.getIdentifyNum().length()-6,user.getIdentifyNum().length()))));
			user.setNumber(createNumber());
			String roleIds = "2";//普通员工角色
			String PhotoUrl = "";
			for (MultipartFile photoFile : photos){
				if(photoFile.getOriginalFilename() != null && !"".equals(photoFile.getOriginalFilename())){
					String fileName = photoFile.getOriginalFilename();//上传的文件名
					String[] nameArr = fileName.split("\\.");
					String suff = nameArr[nameArr.length-1];
					fileName = user.getNumber().toString()+"T"+UUID.randomUUID().toString()+"."+suff;
					String path = WebApplication.getSession().getServletContext().getRealPath("/resources/uploadFile/user/");
					File targetFile = new File(path, fileName);  
					if(!targetFile.exists()){  
						targetFile.mkdirs();  
					}
					if (PhotoUrl.equals(""))
						PhotoUrl ="/resources/uploadFile/user/"+fileName;
					else
						PhotoUrl = PhotoUrl+","+ "/resources/uploadFile/user/"+fileName;
					//保存  
				
					try {  
						photoFile.transferTo(targetFile);  
					} catch (Exception e) {  
						throw new RuntimeException("照片保存异常"+e.getMessage()); 
					} 			
				}
			}
			user.setPhotoUrl(PhotoUrl);
			userService.addUser(user, roleIds);
			return ViewLocation.FOLDER_MSGCOMMON + "/success";
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 删除用户
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("userDel")
	@ResponseBody
	public String userDel(Integer id,Model model){
		if (id == null){
			model.addAttribute("errMsg", "请不要擅自修改前台页面");
			return "请不要擅自修改前台页面";
		}
		User user = userService.findById(id);
		if (null == user){
			model.addAttribute("errMsg","无此用户");
			return "无此用户";
		}
		if (userService.isInOtherMK(user)){
			model.addAttribute("errMsg","不能删除，与其他模块有关联");
			return "不能删除，与其他模块有关联";
		}
		userService.deleteUser(id);
		return "success";
		
		
	}
	/**
	 * 员工详情页面
	 * @return
	 */
	@RequestMapping("openUserDetail")
	public String openUserDetail(Integer id,Model model){
		if (null == id){
			model.addAttribute("errMsg", "无此员工");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		User user  = userService.findById(id);
		if (null == user){
			model.addAttribute("errMsg", "无此员工");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		UserShowView usv = null;
		AdministrationLevelHistory alh = null;
		AdministrationWorkHistory awh = null;
		usv = new UserShowView();
		usv.setUser(user);
		alh = adminstrationLevelService.findLastByUser(user);
		if (alh != null)
			usv.setAdministrationLevel(alh.getLevelName());
		awh = administrationWorkService.findLastByUser(user);
		if (awh != null){
			usv.setAdministrationWorkName(awh.getJobName());
			usv.setAdministrationWorkUnits(awh.getUnits());
		}
		
		if(null != user.getBirthPlace() ){
			if (user.getBirthPlace().length() == 6){
				String bproc = placeService.queryNameByCode(user.getBirthPlace());
//				String bcity = placeService.queryNameByCode(user.getBirthPlace().substring(0,4));
//				String bdist = placeService.queryNameByCode(user.getBirthPlace());
				model.addAttribute("bproc",bproc);
//				model.addAttribute("bcity",bcity);
//				model.addAttribute("bdist", bdist);
			}
		}
		if(null != user.getOriginPlace()){
			if (user.getOriginPlace().length() == 6){
				String oproc = placeService.queryNameByCode(user.getOriginPlace());
//				String ocity = placeService.queryNameByCode(user.getOriginPlace().substring(0,4));
//				String odist = placeService.queryNameByCode(user.getOriginPlace());
				model.addAttribute("oproc",oproc);
//				model.addAttribute("ocity",ocity);
//				model.addAttribute("odist",odist);
			}
		}
	
		model.addAttribute("userDetail", usv);
		List<Place> provList = placeService.queryAllProv();
		model.addAttribute("provList", provList);
		List<Country> countryList = countryService.queryAllCountrys();
		model.addAttribute("countryList", countryList);
		
		//学历学位
		List<EducationHistory> educationHistories = educationHistoryService.findByUser(user);
		model.addAttribute("educations", educationHistories);
		List<Relation> relations = relationService.findByUser(user);
		model.addAttribute("relations",relations);
		//培训
		List<TrainUser> trainUsers = trainUserService.findByUser(user);
		model.addAttribute("trainUsers",trainUsers);
		List<Train> trains =null; 
		List<Train> trainsHistories =null; 		
		for(int i=0;i<trainUsers.size();i++){
			if(i==0){
				trains = trainService.findById(trainUsers.get(i).getTrain().getId());
			}
			else{
				trainsHistories = trainService.findById(trainUsers.get(i).getTrain().getId());
				trains.add(trainsHistories.get(0));
				}
		}
			model.addAttribute("trains",trains);
			//挂职
			List<SecondmentUser> secondmentsUsers = secondmentUsersService.findByUser(user);
			model.addAttribute("secondmentsUsers",secondmentsUsers);
			List<Secondment> secondments =null; 
			List<Secondment> secondmentsHistories =null; 
			for(int i=0;i<secondmentsUsers.size();i++){
				if(i==0){
					secondments = secondmentsService.findById(secondmentsUsers.get(i).getSecondment().getId());
				}
				else{
					secondmentsHistories = secondmentsService.findById(secondmentsUsers.get(i).getSecondment().getId());
					secondments.add(secondmentsHistories.get(0));
					}
			}
				model.addAttribute("secondments",secondments);
				
			//任免信息
				List<PostUser> postUsers = postUsersService.findByUser(user);
				model.addAttribute("postUsers", postUsers);
		return ViewLocation.FOLDER_INFOMANAGER_USERINFO +"/userInfo";
	
		
		
		
		
	}
	/**
	 * 打开员工修改
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("openUserEdit")
	public String openUserEdit(Integer id,Model model){
		
		
		if (null == id){
			model.addAttribute("errMsg", "无此员工");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		User user  = userService.findById(id);
		if (null == user){
			model.addAttribute("errMsg", "无此员工");
			return ViewLocation.FOLDER_MSGCOMMON+"/fail";
		}
		UserShowView usv = null;
		AdministrationLevelHistory alh = null;
		AdministrationWorkHistory awh = null;
		usv = new UserShowView();
		usv.setUser(user);
		alh = adminstrationLevelService.findLastByUser(user);
		if (alh != null)
			usv.setAdministrationLevel(alh.getLevelName());
		awh = administrationWorkService.findLastByUser(user);
		if (awh != null){
			usv.setAdministrationWorkName(awh.getJobName());
			usv.setAdministrationWorkUnits(awh.getUnits());
		}
		
		if(null != user.getBirthPlace() && StringUtil.isNotBlank(user.getBirthPlace())){
		
			List<Place> bcityList = placeService.queryChildByCode(user.getBirthPlace().substring(0, 2));
			List<Place> bdistList = placeService.queryChildByCode(user.getBirthPlace().substring(0,4));
			model.addAttribute("bcityList",bcityList);
			model.addAttribute("bdistList",bdistList);
		}
		if(null != user.getOriginPlace() && StringUtil.isNotBlank(user.getOriginPlace())){
			List<Place> ocityList = placeService.queryChildByCode(user.getOriginPlace().substring(0, 2));
			List<Place> odistList = placeService.queryChildByCode(user.getOriginPlace().substring(0,4));
			model.addAttribute("ocityList",ocityList);
			model.addAttribute("odistList",odistList);
		}
		
		
		
		model.addAttribute("user", usv);
		List<Place> provList = placeService.queryAllProv();
		model.addAttribute("provList", provList);
		List<Country> countryList = countryService.queryAllCountrys();
		model.addAttribute("countryList", countryList);
		//学历学位
				List<EducationHistory> educationHistories = educationHistoryService.findByUser(user);
				model.addAttribute("educations", educationHistories);
				List<Relation> relations = relationService.findByUser(user);
				model.addAttribute("relations",relations);
				return ViewLocation.FOLDER_INFOMANAGER_USERINFO +"/userEdit";
		
		
	}
	/**
	 * 员工修改
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("userEdit")
	public String userEdit(@RequestParam(value="photo",required = false) MultipartFile[] photos,User user,BirthPlace birthPlace,OrigionPlace origionPlace,EducationHistory educations, Relation relations,Model model){
		if (null == user || null == user.getId()){
			return "noSuchUser";
		}
		User userEdit  = userService.findById(user.getId());
		userEdit.setBeginSchoolWorkDay(user.getBeginSchoolWorkDay());
		userEdit.setBeginWorkDay(user.getBeginWorkDay());
		userEdit.setBirthPlace(birthPlace.toCodeString());
		userEdit.setBirthDay(user.getBirthDay());
	//	userEdit.setCheckCase(user.getCheckCase());
		userEdit.setContectEmail(user.getContectEmail());
		userEdit.setContectMobilePhone(user.getContectMobilePhone());
		userEdit.setContectWorkPhone(user.getContectWorkPhone());
		userEdit.setGender(user.getGender());
		userEdit.setHealthStatus(user.getHealthStatus());
		userEdit.setJoinPartyDay(user.getJoinPartyDay());
		userEdit.setName(user.getName().split(",")[0]);
		userEdit.setNation(user.getNation());
		userEdit.setOriginPlace(origionPlace.toCodeString());
		userEdit.setParty(user.getParty());
		userEdit.setRemark(user.getRemark());
		userEdit.setRetireDay(user.getRetireDay());
		userEdit.setResearchDirection(user.getResearchDirection());
		userEdit.setIdentifyNum(user.getIdentifyNum());
		List<DictionaryDb>  degreeEdit=null;
		
		List<EducationHistory> educationHistoryEdit = educationHistoryService.findByUser(user);
		for(int i=0;i<educationHistoryEdit.size();i++){
			degreeEdit=dictionaryDbDegreeService.findByValue(educations.getDegree().split(",")[i]);
			
			//educationHistoryEdit.get(i).setBeginDay(educations.getBeginDay());//有问题 要改正
			//educationHistoryEdit.get(i).setEndDay(educations.getEndDay());
			educationHistoryEdit.get(i).setSchool(educations.getSchool().split(",")[i]);
			educationHistoryEdit.get(i).setDegree(degreeEdit.get(0).getKey());
		}
		
		
		List<Relation> relationEdit = relationService.findByUser(user);
		System.out.println(relationEdit.size());
		for(int i=0;i<relationEdit.size();i++){
			
			relationEdit.get(i).setName(relations.getName().split(",")[i+1]);
			relationEdit.get(i).setAppellation(relations.getAppellation().split(",")[i]);
			//relationEdit.get(i).getBirthDay();
			relationEdit.get(i).setUnits(relations.getUnits().split(",")[i]);
			relationEdit.get(i).setJob(relations.getJob().split(",")[i]);		
		}
		String PhotoUrl = "";
		for (MultipartFile photoFile : photos){
			if(photoFile.getOriginalFilename() != null && !"".equals(photoFile.getOriginalFilename())){
				String fileName = photoFile.getOriginalFilename();//上传的文件名
				String[] nameArr = fileName.split("\\.");
				String suff = nameArr[nameArr.length-1];
				fileName = userEdit.getNumber().toString()+"T"+UUID.randomUUID().toString()+"."+suff;
				String path = WebApplication.getSession().getServletContext().getRealPath("/resources/uploadFile/user/");
				File targetFile = new File(path, fileName);  
				if(!targetFile.exists()){  
					targetFile.mkdirs();  
				}
				if (PhotoUrl.equals(""))
					PhotoUrl ="/resources/uploadFile/user/"+fileName;
				else
					PhotoUrl = PhotoUrl+","+ "/resources/uploadFile/user/"+fileName;
				//保存  
			
				try {  
					photoFile.transferTo(targetFile);  
				} catch (Exception e) {  
					throw new RuntimeException("照片保存异常"+e.getMessage()); 
				} 			
			}
		}
		userEdit.setPhotoUrl(PhotoUrl);
		
		userService.updateUser(userEdit);
		userService.updateEducationHistory(educationHistoryEdit);
		userService.updateRelation(relationEdit);
		return ViewLocation.FOLDER_MSGCOMMON+"/success";
	}
	/**
	 * 重置密码为证件号后六位
	 * @param id
	 * @return
	 */
	@RequestMapping("setPwd")
	@ResponseBody
	public String setPwd(Integer id){
		if (null == id){
			return "fail";
		}
		User user  = userService.findById(id);
		if (null == user){
			return "fail";
		}
		try {
			user.setPassWord(DigestUtil.encryptPWD(user.getIdentifyNum().substring(user.getIdentifyNum().length() - 6,user.getIdentifyNum().length())));
			userService.updateUser(user);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
		}
		
	}
	/**
	 * 判断员工号是否有人
	 * @param number
	 * @return
	 */
	@RequestMapping("existNumber")
	@ResponseBody
	public String existNumber(String number){
		if (null == number){
			return "fail";
		}
		User user  = userService.findByNumber(number);
		
		if (null == user){
			return "fail";
		}else {
			return "exist";
		}
	}
	/**
	 * 判断该名字是否有对应用户存在
	 * @param user
	 * @return
	 */
	@RequestMapping("existUserName")
	@ResponseBody
	@Transactional
	public List<UserSimple> existUserName(String userName){
		List<UserSimple> sList = new ArrayList<UserSimple>();
		if (null == userName){
			return sList;
		}
		
		int count = userService.queryCountByName(userName);
		if(count <= 0){
			return sList;
		}
		else{
			
			List<User> uList= userService.findUsersByName(userName);
			UserSimple userSimple =null;
			for (User user : uList){
				userSimple = new UserSimple();
				userSimple.setName(user.getName());
				userSimple.setId(user.getId());
				sList.add(userSimple);
			}
			userSimple = null;
			return sList;
		}
		
	}
	
	/**
	 * 生成员工号
	 * 生成规则 年份+序号
	 * @return
	 * @throws Exception
	 */
	
	private String createNumber() throws Exception{
		try {
			String year = DateUtil.nowDate().substring(0,4);
			Integer number = userService.queryMaxNumber(year);
			if(number == null){
				return year+"100001";
			}else{
				return number.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
//	private Map<String,String> getOrgMap(){
//		//组织机构
//		
//  		List<Organization> orgList = organizationService.queryAll();
//  		Map<String,String> orgMap = new HashMap<String, String>();
//		for(Organization org : orgList){
//			orgMap.put(org.getCode(), org.getName());
//		}
//		return orgMap;
//	}
	private Map<String,String> getCountryMap(){
		//国籍

  		List<Country> countryList = countryService.queryAllCountrys();
  		Map<String,String> countryMap = new HashMap<String, String>();
		for(Country ct : countryList){
			countryMap.put(ct.getCode(), ct.getName());
		}
		return countryMap;
	}
	
	private Map<String,String> getPlaceMap(){
		//籍贯

  		List<Place> placeList = placeService.queryAll();
  		Map<String,String> placeMap = new HashMap<String, String>();
		for(Place pc : placeList){
			placeMap.put(pc.getCode(), pc.getName());
		}
		return placeMap;
	}
	
	private Map<String, String> getOrgMap() {
		// TODO Auto-generated method stub
		List<Organization> orgList = organizationService.queryAll();
		Map<String,String> orgMap = new HashMap<String,String>();
		for(Organization org : orgList){
			orgMap.put(org.getCode(), org.getName());
		}
		return orgMap;
	}
}
