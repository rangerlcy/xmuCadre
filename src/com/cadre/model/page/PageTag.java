package com.cadre.model.page;
import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
public class PageTag extends SimpleTagSupport {
	
	private Page page;
	private String searchForm = "searchForm";
	  /** 
     * 分页div,默认为paginationId 
     */  
    private String pageId = "pageId";  
    
    @Override
    public void doTag() throws JspException, IOException {
    	StringBuffer strBuf = new StringBuffer("");
    	strBuf.append("<div class='l_msg'>当前显示")
    	.append((page.getCurrPage()-1)*page.getPageSize()+1)
    	.append("-");
    	int end = page.getCurrPage()*page.getPageSize();
    	if(end > page.getTotalSize()) {
    		end = page.getTotalSize();
    	}
    	strBuf.append(end).append("条，每页")
    	.append(page.getPageSize()).append("条，共")
    	.append(page.getTotalSize())
    	.append("条记录</div>");
    	
    	strBuf.append("<div class='r_btn'>");    	
    	strBuf.append("<span class='ms'>")
    	.append("跳转到第 <input type='text' value='"+page.getCurrPage()+"' id='"+pageId+"PageNum'/>/"+page.getTotalPages()+"页</span>");
    	strBuf.append("<a href='javascript:").append(pageId).append("Go($(\"#"+pageId+"PageNum\").val());' title='跳转'>跳转</a>");
    	if(page.getCurrPage() != 1) {
    		strBuf.append("<a href='javascript:").append(pageId).append("Go(1);' title='首页'>首页</a>");
    	}
    	
    	if(page.isHasPre()) {
    		strBuf.append("<a href='javascript:").append(pageId).append("Go("+(page.getCurrPage()-1)+");' title='上一页'>上一页</a>");
    	}
    	if(page.isHasNext()) {
    		strBuf.append("<a href='javascript:").append(pageId).append("Go("+(page.getCurrPage()+1)+");'title='下一页'>下一页</a>");
    	}
    	
    	if(page.getCurrPage() != page.getTotalPages()) {
    		strBuf.append("<a href='javascript:").append(pageId).append("Go("+page.getTotalPages()+");' title='尾页'>尾页</a>");
    	}
    	strBuf.append("</div>");
    	
    	writeToJsp("<div class='pager'> "+strBuf.toString()+"</div>"+pageScript());
    }
    
    private String pageScript() {
    	StringBuffer sb = new StringBuffer("<script type='text/javascript'>");
    	sb.append("$(function(){");
    	sb.append("$('#"+searchForm+"').append(\"<input type='hidden' id='currPage' name='currPage' value='-1'/>\");");
    	sb.append("$('#"+searchForm+"').append(\"<input type='hidden' name='pageSize' value='"+page.getPageSize()+"' />\");");
    	sb.append("$('#"+pageId+"PageNum').keypress(function(e) {if(e.which == 13) {"+pageId+"Go();}});");
    	sb.append("});");
    	
    	sb.append("function "+pageId+"Go(page){");
    	sb.append("if(isNaN(page) || page > " + page.getTotalPages()+") {art.dialog.alert('请输入1-"+page.getTotalPages()+"之间的数字'); return;}");
    	sb.append("$('#"+searchForm+" #currPage').val(page);");
    	sb.append("$('#"+searchForm+"').submit();");
    	sb.append("}");
    	sb.append("</script>");
    	
    	return sb.toString();
    }
    
    /**
     * 写到JSP页面
     * @param str
     * @throws IOException
     */
    private void writeToJsp(String str) throws IOException{
    	JspContext ctx = getJspContext();
        JspWriter out = ctx.getOut();
		out.print(str);
    }
    
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getSearchForm() {
		return searchForm;
	}

	public void setSearchForm(String searchForm) {
		this.searchForm = searchForm;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
}
