package com.communityhelper.api;

import java.util.List;

public class Page<E> {

	/** 开始记录行数FirstResult */
	private Integer pageIndex;
	/** 每页显示记录数maxResult */
	private Integer maxResult;
	/** 总记录数 */
	private Integer totalResult = 0;
	/** 查询结果集 */
	private List<E> list;
	
	public Page() {
		super();
	}

	public Page(int pageIndex,int maxResult) {
		super();
		this.pageIndex = pageIndex;
		this.maxResult = maxResult;
	}
	/**
	 * 当前页数
	 */
	public Integer getCurrentPageNo() {
		if (pageIndex == null || maxResult == null || maxResult == 0) {
			return 1;
		}
		return pageIndex/maxResult+1;
	}

	public Integer getPageIndex() {

		return null == pageIndex ? 0 : pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getTotalPage() {
		
		return totalResult % getMaxResult() == 0 ? totalResult / getMaxResult() : totalResult / getMaxResult() + 1;
	}

	public Integer getMaxResult() {
		return null == maxResult ? 10 : maxResult; 
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public List<E> getList() {
		return list;
	}
	
	public void setList(List<E> list) {
		this.list = list;
	}

	public Integer getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(Integer totalResult) {
		this.totalResult = totalResult;
	}
}
