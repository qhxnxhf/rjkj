package com.rjkj.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "cms_news_send")
public class NewsSend  extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6661795401766576489L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="newsId")
	private News news;//新闻
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="senderId")
	private User sender;//发件人
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="takerId")
	private User taker;//收件人
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;//接收日期
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receiveDate;//接收日期
	
	@Column(nullable=true, length=512)
	private String leavewd;//留言
	
	@Column(nullable=true, length=255)
	private String replywd;//回复
	
	@Column(nullable=true, length=16)
	private String status;//接收状态

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getTaker() {
		return taker;
	}

	public void setTaker(User taker) {
		this.taker = taker;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getLeavewd() {
		return leavewd;
	}

	public void setLeavewd(String leavewd) {
		this.leavewd = leavewd;
	}

	public String getReplywd() {
		return replywd;
	}

	public void setReplywd(String replywd) {
		this.replywd = replywd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	

}
