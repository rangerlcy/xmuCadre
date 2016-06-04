package com.cadre.controller.report;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cadre.common.ViewLocation;
import com.cadre.common.WebApplication;
import com.cadre.model.dictionary.DictionaryItem;
import com.cadre.model.page.Page;
import com.cadre.model.utils.StringUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.Report;
import com.cadre.pojo.ReportFile;
import com.cadre.pojo.User;
import com.cadre.service.infoManager.TableMappingService;
import com.cadre.service.report.ReportService;
import com.cadre.service.sys.UserService;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
 
 
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.io.IOException;


import java.io.FileNotFoundException;
import java.io.IOException;


import javax.servlet.ServletException;

@Controller
@RequestMapping(ViewLocation.FOLDER_REPORT)
public class ReportController {
		private Logger logger = LogManager.getLogger(ReportController.class);

		@Autowired
		UserService userService;
		@Autowired
		ReportService reportService;
		@Autowired
		TableMappingService tableMappingService;
		int pageSize = 15;
		@RequestMapping(value="/report")
		public String report(Integer currPage,String queryStr,Model model) throws Exception{
			try {
				if(currPage == null) currPage = 1;//默认显示第一页
				Page<Report> queryList = reportService.queryPage(queryStr, currPage, pageSize);
				model.addAttribute("queryStr", queryStr);//查询条件返回jsp页面
				model.addAttribute("queryList",queryList);//查询结果列表
				model.addAttribute("isSuperSearch", "0");
				if (WebApplication.getCurrUser().getUsername() != null && WebApplication.getCurrUser().getUsername().toLowerCase().equals("admin")){
					model.addAttribute("roleType", "admin");
				}else {
					model.addAttribute("roleType", "visitor");
				}
				return ViewLocation.FOLDER_REPORT+"/report";
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		/**
		 * 高级搜索功能
		 * @param currPage
		 * @param user
		 * @param informer
		 * @param reportedDayOperator
		 * @param reportedDay
		 * @param reportedWay
		 * @param reportedContent
		 * @param processingAndConclusion
		 * @param processingAndConclusionType
		 * @param remark
		 * @param model
		 * @return
		 * @throws Exception
		 */

		@RequestMapping("/SuperSearchReport")
		public String SuperSearchUserList(Integer currPage,ReportParam reportParam,Model model) throws Exception{
			
			
			Page<Report> queryList = reportService.findSearchReportByPage(reportParam, currPage, pageSize);
			
			model.addAttribute("queryList",queryList);//查询结果列表
			model.addAttribute("user",reportParam.getUser());
			model.addAttribute("informer",reportParam.getInformer());
			model.addAttribute("reportedDayOperator",reportParam.getReportedDayOperator());
			model.addAttribute("reportedDay",reportParam.getReportedDay());
			model.addAttribute("reportedWay",reportParam.getReportedWay());
			model.addAttribute("reportedType",reportParam.getReportedType());
			model.addAttribute("reportedContent",reportParam.getReportedContent());
			
			model.addAttribute("processingAndConclusion",reportParam.getProcessingAndConclusion());
			
			model.addAttribute("processingAndConclusionType",reportParam.getProcessingAndConclusionType());
			model.addAttribute("remark",reportParam.getRemark());
	
			model.addAttribute("isSuperSearch", "1");
			return ViewLocation.FOLDER_REPORT+"/report";
		}

		/**
		 * 检查被举报人工号是否已存在
		 * @param user
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/checkUser")
		@ResponseBody
		public String checkNumber (String user) throws Exception {
			try {
				int count = reportService.getCount("user",user);
				if(count == 0){
					return "success";
				}else{
					return "failure";
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}

		/**
		 * 修改资源信息前，查出对应的资源信息
		 * @param id
		 * @param model
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("/preReportUpdate")
		public String preReportUpdate(Integer id,Model model) throws Exception{
			try {
				if (null == id){
					return "fail";
				}
				Report report = reportService.findById(id);
				model.addAttribute("report", report);
				return ViewLocation.FOLDER_REPORT+"/reportEdit";
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		/**
		 * 打开新增页面
		 * @param model
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("/preAddReport")
		public String preAddReport(Model model) throws Exception {
			try {
				return ViewLocation.FOLDER_REPORT+"/reportAdd";
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		@RequestMapping("/updateReports")
		@ResponseBody
		public String updateReports(Report report,Model model) throws Exception {
			try {
				Report re = reportService.findById(report.getId());
				re.setProcessingAndConclusion(report.getProcessingAndConclusion());
				re.setProcessingAndConclusionType(report.getProcessingAndConclusionType());
				re.setReportedContent(report.getReportedContent());
				re.setReportedWay(report.getReportedWay());
				re.setReportedDay(report.getReportedDay());
				re.setReportedType(report.getReportedType());
				reportService.updateReport(re);
				return "success";
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		
		@RequestMapping("/addReports")
		@ResponseBody
		public String addReports(String userName,Integer userId,Report report,Model model) throws Exception {
			try {
				if(null == userId) return "fail";
				User ur = userService.findById(userId); 
				if ( null ==ur){
					return "fail";
				}
				report.setUser(ur);
				reportService.saveReport(report);
				return "success";
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		
		@RequestMapping("/delReports")
		@ResponseBody
		public String delReports(Integer id,Model model) throws Exception {
			try {
				reportService.delReport(id);
				return "success";
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}

		@RequestMapping(value="/exportReport")
		public void exportReport(String queryStr,String isSuperSearch,ReportParam reportParam,String titles,HttpServletResponse response) throws Exception{
//			System.out.println("queryStr~~~~~"+queryStr);
//			System.out.println("isSuperSearch~~~~~"+isSuperSearch);
//			System.out.println("reportParam~~~~~"+reportParam);
//			System.out.println("titles~~~~"+titles);
			if (StringUtil.isNotBlank(isSuperSearch)){
				List<Report> reports = null;
				if ("0".equals(isSuperSearch)){
					if (StringUtil.isNotBlank(queryStr)) {
						try {
							queryStr = java.net.URLDecoder.decode(queryStr, "UTF-8");

						} catch (Exception e) {
							logger.error("导出文件解码错误：" + e.getMessage());
							throw new Exception(e.getMessage());
						}
					}
					reports = reportService.findReportForExport(queryStr);
				}else if ("1".equals(isSuperSearch)){
					
					try {
						reportParam = decodeReportParam(reportParam);
					} catch (Exception e) {
						logger.error("导出文件解码错误：" + e.getMessage());
						throw new Exception(e.getMessage());
					}
					reports = reportService.findSuperReportForExport(reportParam);
				}
//				if (reports.size()==0){
//					return;
//				}
				String fileName = "";
				String dir = WebApplication.getSession().getServletContext().getRealPath("/resources/downloadFile/");
				String tempFileName = dir+"/user.xls";
				fileName= "report"+System.currentTimeMillis()+".xls";
				Workbook wb = Workbook.getWorkbook(new File(tempFileName));		//根据模板创建EXCEL文件
		    	WritableWorkbook wwb = Workbook.createWorkbook(new File(dir+"/"+fileName), wb); 	//创建临时文件
		        WritableSheet sheet =wwb.getSheet(0);
		       
		        //List<DictionaryItem> titleList = ViewFunction.getReportTitles();
		        List<DictionaryItem> titleList = new ArrayList<DictionaryItem>();
		        DictionaryItem e = new DictionaryItem("02", "被举报人"); 
		        titleList.add(e);
		         e = new DictionaryItem("03", "举报人"); 
		        titleList.add(e);
		         e = new DictionaryItem("04", "举报时间"); 
		        titleList.add(e);
		         e = new DictionaryItem("05", "举报途径"); 
		        titleList.add(e);
		         e = new DictionaryItem("06", "举报类型"); 
		        titleList.add(e);
		         e = new DictionaryItem("07", "举报内容"); 
		        titleList.add(e);
		         e = new DictionaryItem("08", "处理经过和结论"); 
		        titleList.add(e);
		         e = new DictionaryItem("09", "处理结论类型"); 
		        titleList.add(e);
		        e = new DictionaryItem("10", "备注"); 
		        titleList.add(e);
		        int n = 0;
		  		for (DictionaryItem item: titleList){
		  			//先添加标题
		  			Label titleLabel = new Label(n,0,item.getValue());
		  			n = n+1;
		  			sheet.addCell(titleLabel);
		  		}
		  		for(int i=0;i<reports.size();i++){//循环写数据，共report-size行
		      		for(n=0;n<titleList.size();n++){
		      			String value = tableMappingService.getReportColVal(reports.get(i), titleList.get(n).getKey());
		      			Label valueLabel = new Label(n,i+1,value);
		      			sheet.addCell(valueLabel);
		      		}
		      	}
		  		wwb.write();  
		    	wwb.close();
		    	BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					response.setContentType("video/mpeg4; charset=utf-8");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"report.xls\"");
					File file = new File(dir+"/"+fileName);
					int len = 0;    
					byte[] buf = new byte[1024];    
					bis = new BufferedInputStream(new FileInputStream(file));  
					bos = new BufferedOutputStream(response.getOutputStream());    
					while (-1 != (len = bis.read(buf, 0, buf.length))) {   
		                bos.write(buf, 0, len);   
		            }
				} catch (Exception ee) {
					logger.error("导出举报信息错误:" + ee.getMessage());
					throw new Exception("导出举报信息错误:" + ee.getMessage());
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
		private ReportParam decodeReportParam(ReportParam reportParam) throws Exception{
			
			try {
				reportParam.setInformer(StringUtil.decodeUTF8(reportParam.getInformer()));
				reportParam.setUser(StringUtil.decodeUTF8(reportParam.getUser()));
				reportParam.setProcessingAndConclusion(StringUtil.decodeUTF8(reportParam.getProcessingAndConclusion()));
				reportParam.setProcessingAndConclusionType(StringUtil.decodeUTF8(reportParam.getProcessingAndConclusionType()));
				reportParam.setRemark(StringUtil.decodeUTF8(reportParam.getRemark()));
				reportParam.setReportedContent(StringUtil.decodeUTF8(reportParam.getReportedContent()));
				reportParam.setReportedDayOperator(StringUtil.decodeUTF8(reportParam.getReportedDayOperator()));
				reportParam.setReportedType(StringUtil.decodeUTF8(reportParam.getReportedType()));
				reportParam.setReportedWay(StringUtil.decodeUTF8(reportParam.getReportedWay()));
				
				reportParam.setReportedDay(StringUtil.decodeUTF8(reportParam.getReportedDay()));
			return reportParam;
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
		/**
		 * 打开附件管理
		 * @param model
		 * @return
		 * @throws Exception 
		 */
		Integer clickId;		//记下被点击的id
		@RequestMapping("/afxReports")
		public String afxReports(String id,Model model) {
			clickId=Integer.parseInt(id);
			List<ReportFile> result=reportService.querybyReportid(id);
			model.addAttribute("result", result);
			return ViewLocation.FOLDER_REPORT+"/reportafx";			
		}

		/**
		 * 上传附件
		 * @return
		 */
	    @RequestMapping("reportafx")
		public String reportafx(@RequestParam("file") MultipartFile file,HttpServletRequest req, Model model) throws Exception{
			//为了找到运行工程的实际路径（现在在Tomcat服务器上运行：添加参数：HttpServletRequest req
			//TODO 将file保存到upload目标位置---将Request中的file对象取出来给此处的MultipartFile file参数
			
			//System.out.println("上传文件："+file);//客户端选中上传的文件
	    	
	    	String path = req.getSession().getServletContext().getRealPath("/resources/uploadFile");	//得到真实路径(带有盘符的物理路径)
			String timetag="file"+System.currentTimeMillis();
			String filename=file.getOriginalFilename();			//附件名字
			String[] ss=filename.split("\\.");
			String rear=ss[ss.length-1];
			String filePath = path +File.separatorChar+timetag+"."+rear;	//上传路径
			
			//注意区别服务器中的项目目录，和工作空间中的项目目录，上传文件是传到服务器的项目目录下
			String pp="/xmuCadre/resources/uploadFile/"+timetag+"."+rear;		//存到数据库的附件url,因为浏览器无法访问物理路径，人为的改成虚拟路径
			ReportFile repfile = new ReportFile(clickId,filename,pp); 
			Date date=new Date();	//操作时间，返回到前端
			String rr="";
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			InputStream fis = file.getInputStream();
			FileOutputStream fos = new FileOutputStream(new File(filePath));
		    try{
				//写文件操作
			
				byte[] bbs = new byte[1024];//不一定每次都读1024-->有返回值：真实读多少
				int len = -1;
				while(-1!=(len=fis.read(bbs))){//每次把真实读进来的数给len,判断是否为-1：文件读完
					fos.write(bbs,0,len);//把bbs数组中的字节写出去，读多少，写多少
				}
				//结束了读写操作
				
				//将clickId、filename、filepath存入数据库
				reportService.addfile(repfile);
				
				//返回到前端
				List<ReportFile> result=reportService.querybyReportid(clickId+"");
				model.addAttribute("result", result);
				rr=format.format(date);
				model.addAttribute("successTime", rr);
				return ViewLocation.FOLDER_REPORT+"/reportafx";
			 } catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				List<ReportFile> result=reportService.querybyReportid(clickId+"");
				model.addAttribute("result", result);
				rr=format.format(date);
				model.addAttribute("failTime", rr);
				return ViewLocation.FOLDER_REPORT+"/reportafx";
			 }finally{
				fos.close();
				fis.close();
			 }
		}
	    /**
		 * 删除附件
		 * @return
		 */
	    @RequestMapping("delReportFile")
	    @ResponseBody
	    public String delReportFile(Integer id){
			try {
				reportService.deletefileby(id);
				return "success";
			} catch (Exception e) {
				// TODO: handle exception
				return "fail";
			}
		} 
	    
	}















