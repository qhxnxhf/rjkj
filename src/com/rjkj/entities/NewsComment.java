package com.rjkj.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "cms_news_comment")
public class NewsComment extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4764946469886357605L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="newsId")
	private News news;//新闻
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	private User commenter;//评论人
	
	@Column(nullable=false, length=128)
	private String userName;
	
	@Column(length=256)
	private String fromIp;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date replyDate;//回复日期
	
	@Column(nullable=true, length=512)
	private String replywd;//回复
	
	@Column(nullable=true, length=16)
	private Integer status;//状态，支持、反对等

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public User getCommenter() {
		return commenter;
	}

	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getReplywd() {
		return replywd;
	}

	public void setReplywd(String replywd) {
		this.replywd = replywd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
