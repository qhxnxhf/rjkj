package com.rjkj.util;

import java.util.List;
import java.util.Map;

public class Page {
	private int pageSize = 10;
	private int curPageNo=1;
	private String orderBy;
	private String order;
	
	//transfer from server to client
	private int totalPages=0;
	private long totalRecords=0;
	private List dataRows;
	
	//the 2 member variables below only used when multi associated table query.
	private String[][] orderByColumnRelations; 
	private Map<String,String> paramMap;

	@Override
	public String toString() {
		return pageSize + "_" + curPageNo + "_" + orderBy + "_" + order;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurPageNo() {
		return curPageNo;
	}

	public void setCurPageNo(int curPageNo) {
		this.curPageNo = curPageNo;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public int getTotalPages() {
		if(pageSize != 0)
			return (int)(totalRecords-1)/pageSize + 1 ;
		return 0;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List getDataRows() {
		return dataRows;
	}

	public void setDataRows(List dataRows) {
		this.dataRows = dataRows;
	}
	/**
	 * @description 
	 * 根据curPageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 * @author Eric (Oct 17, 2011)
	 * 
	 * @version Change History:
	 * @version  <1> Oct 17, 2011 Eric  Modification purpose. 
	 * @version  <2> Nov 14, 2011 Eric  Modification purpose:序号从1开始 ===> change to 序号从0开始
	 */
	public int getFirst() {
//		return ((curPageNo - 1) * pageSize) + 1;
		return ((curPageNo - 1) * pageSize);
	}
	/**
	 * @description 是否还有下一页.
	 * @author Eric (Oct 17, 2011)
	 * 
	 * @version Change History:
	 * @version  <1> Oct 17, 2011 Eric  Modification purpose. 
	 */
	public boolean isHasNext() {
		return (curPageNo + 1 <= getTotalPages());
	}
	/**
	 * @description 
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 * @author Eric (Oct 17, 2011)
	 * 
	 * @version Change History:
	 * @version  <1> Oct 17, 2011 Eric  Modification purpose. 
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return curPageNo + 1;
		} else {
			return curPageNo;
		}
	}
	/**
	 * @description 是否还有上一页.
	 * @author Eric (Oct 17, 2011)
	 * 
	 * @version Change History:
	 * @version  <1> Oct 17, 2011 Eric  Modification purpose. 
	 */
	public boolean isHasPre() {
		return (curPageNo - 1 >= 1);
	}
	/**
	 * @description 
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 * @author Eric (Oct 17, 2011)
	 * 
	 * @version Change History:
	 * @version  <1> Oct 17, 2011 Eric  Modification purpose. 
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return curPageNo - 1;
		} else {
			return curPageNo;
		}
	}
	
	public String[][] getOrderByColumnRelations() {return orderByColumnRelations;}
	public void setOrderByColumnRelations(String[][] orderByColumnRelations) {this.orderByColumnRelations = orderByColumnRelations;}

	public Map getParamMap() {return paramMap;}
	public void setParamMap(Map paramMap) {this.paramMap = paramMap;}
}