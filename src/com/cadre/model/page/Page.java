
package com.cadre.model.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页对象
 * @author 
 *
 * @param <T>
 */
public class Page<T>{
	private static final long serialVersionUID = -7187672347136157933L;

	public static int defaultPageSise = 10;
	/** 当前页 */
	private int currPage = 1;
	/** 每页记录数 */
	private int pageSize = defaultPageSise;
	/** 总记录数 */
	private int totalSize = 0;
	/** 每页结果集 */
	private List<T> result;

	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private String sortBy = "";

	public int getCurrPage() {
		return currPage <= 0 ? 1 : currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize <= 0 ? 10 : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getResult() {
		return result == null ? new ArrayList<T>() : result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotalSize() {
		return totalSize < 0 ? 0 : totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getTotalPages() {
		return getTotalSize() / getPageSize()
				+ (getTotalSize() % getPageSize() > 0 ? 1 : 0);
	}

	public String getSortBy() {
		return StringUtils.isBlank(this.sortBy) ? "" : this.sortBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (currPage + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return currPage + 1;
		} else {
			return currPage;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (currPage - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return currPage - 1;
		} else {
			return currPage;
		}
	}

}
