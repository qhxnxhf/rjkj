package com.rjkj.util.web;

import java.util.List;


public class Page {
	public final static String ORDER_DIRECTION_ASC = "ASC"; 
	public final static String ORDER_DIRECTION_DESC = "DESC";
	
	
	private int totalRows; // 总行数
	private int pageSize = 5; // 每页显示的行数
	private int pageNo = 1; // 当前页号
	private int totalPages = 0; // 总页数
	private int startRow; // 当前页在数据库中的起始行
	
	private List dataRows;
	
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		totalPages = (totalRows - 1) / this.pageSize + 1;
		
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public List getDataRows() {
		return dataRows;
	}
	public void setDataRows(List dataRows) {
		this.dataRows = dataRows;
	}
	
	
	
}
