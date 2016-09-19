package com.rjkj.entities;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "cms_news_attach")
public class NewsAttach{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -498277844032466322L;
	
	@Id
	@Column(nullable=false, length=32)
	private String id;
	
	@Column(nullable=true)
	private Integer orderNum;
	
	@Column(nullable=true, length=255)
	private String title;
	
	@Column(nullable=true, length=2048)
	private String brief;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="newsId")
	private News news;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(nullable=true, length=255)
	private String fileOrigName;
	
	@Column(nullable=true, length=512)
	private String filePath;
	
	@Column(nullable=true, length=64)
	private String fileType;
	
	@Column(nullable=true, length=64)
	private String mimeType;
	
	@Column(nullable=true, length=32)
	private String suffix;
	
	@Column(nullable=true)
	private Long fileSize;
	
	@Column(nullable=true, length=16)
	private String status;

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFileOrigName() {
		return fileOrigName;
	}

	public void setFileOrigName(String fileOrigName) {
		this.fileOrigName = fileOrigName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
